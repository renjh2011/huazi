package com.xpand.system.modules.sys.web;

import com.xpand.common.core.base.BaseController;
import com.xpand.common.core.base.ResponseObject;
import com.xpand.common.core.config.Global;
import com.xpand.common.core.utils.JwtHelper;
import com.xpand.common.core.utils.RedisUtil;
import com.xpand.common.core.utils.StringUtils;
import com.xpand.system.core.shiro.StatelessToken;
import com.xpand.system.modules.sys.model.SysPermission;
import com.xpand.system.modules.sys.model.SysUser;
import com.xpand.system.modules.sys.service.SysPermissionService;
import com.xpand.system.modules.sys.service.SysRoleService;
import com.xpand.system.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-4-12.
 */
@Api("登录接口")
@Controller
@RequestMapping(value = "/{adminPath}/system")
public class LoginController extends BaseController{
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    private Logger logger= LogManager.getLogger(LoginController.class.getName());

    @PostMapping("/login")
    @ResponseBody
    public ResponseObject login(SysUser sysUser) throws Exception {

        String username=sysUser.getUsername();
        String password=sysUser.getPassword();

        //密码为空或帐号与手机号都为空 登录失败
        if((StringUtils.isEmpty(password) || StringUtils.isEmpty(username))){
            //throw new Exception("登录名或密码为空！");
            return failure(1,"登录名或密码为空");
        }

        Map<String, Object> params = new HashMap<String, Object>();


        SysUser dbUser=sysUserService.findUser(sysUser);
        String accessToken = "";
        if(dbUser!=null){
            String account=StringUtils.isEmpty(username) ? dbUser.getMobile() : dbUser.getUsername();
            accessToken = JwtHelper.createJWT(dbUser.getUsername(),
                    dbUser.getId(),
                    dbUser.getMobile(),
                    dbUser.getName(),
                    Long.parseLong(Global.getConfig("xpand.api.expiresSecond")) * 1000,
                    Global.getConfig("xpand.api.base64Secret"));
            RedisUtil.set(dbUser.getId() + "pc-token", accessToken);
            //获取权限
            SimpleAuthorizationInfo Info=getPermission(dbUser);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token",accessToken);
            map.put("info",Info.getStringPermissions());
            logger.debug("login success");
            return success(map);
        }else{
            return failure(1,"登录名或密码错误");
        }
    }

    private SimpleAuthorizationInfo getPermission(SysUser sysUser){
        List<String> roleIds = sysRoleService.findRoleIdsByUserId(sysUser.getId());
        List<SysPermission> permissionsList = sysPermissionService.findPermissionsByRoleIds(roleIds);

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        for (SysPermission permission:permissionsList) {
            info.addStringPermission(permission.getCode());
        }

        info.addRoles(roleIds);
        RedisUtil.set(sysUser.getId()+"info",info);
        return info;
    }
    @PostMapping("/login1")
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    public ResponseObject test(){
        return success();
    }
}
