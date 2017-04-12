package com.xpand.system.core.shiro;

import com.xpand.common.core.config.Global;
import com.xpand.common.core.utils.JwtHelper;
import com.xpand.common.core.utils.RedisUtil;
import com.xpand.system.modules.sys.model.SysPermission;
import com.xpand.system.modules.sys.model.SysUser;
import com.xpand.system.modules.sys.service.SysPermissionService;
import com.xpand.system.modules.sys.service.SysRoleService;
import com.xpand.system.modules.sys.service.SysUserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Code.Ai on 16/8/10.
 * Description:
 */
public class StatelessRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Principal principal = (Principal) getAvailablePrincipal(principals);
        if (principal != null) {
            SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) RedisUtil.get(principal.getId() + "info");
            if (info != null) return info;
            info = new SimpleAuthorizationInfo();
            List<String> roleIds = sysRoleService.findRoleIdsByUserId(principal.getId());
            List<SysPermission> permissionsList = sysPermissionService.findPermissionsByRoleIds(roleIds);

            for (SysPermission permission : permissionsList) {
                info.addStringPermission(permission.getCode());
            }

            info.addRoles(roleIds);
            RedisUtil.set(principal.getId() + "info", info);
            return info;
        } else {
            return null;
        }
    }

    //登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String uniqueToken = statelessToken.getUniqueToken();
        if (uniqueToken == null) {
            throw new AuthenticationException("token is null!");
        }
        // 解析 token
        Claims claims = JwtHelper.parseJWT(uniqueToken, Global.getConfig("xpand.api.base64Secret"));
        if (claims != null) {
            String userName = (String) claims.get("userName");
            String userId = (String) claims.get("userId");
            String name = (String) claims.get("name");
            String mobile = (String) claims.get("mobile");
            // 如果 redis 没有此 token 登录失败
            Object tokenValidate = RedisUtil.get(userId + "pc-token");
            if (tokenValidate == null) {
                return null;
            }
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            sysUser.setUsername(userName);
            sysUser.setName(name);
            sysUser.setMobile(mobile);
            AuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(sysUser, false),
                    tokenValidate, getName());
            return info;
        }
        return null;
    }

    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String mobile; // 手机号码
        private String name; // 姓名
        private String username;//登錄名
        private boolean mobileLogin; // 是否手机登录


        public Principal(SysUser SysUser, boolean mobileLogin) {
            this.id = SysUser.getId();
            this.mobile = SysUser.getMobile();
            this.name = SysUser.getName();
            this.username = SysUser.getUsername();
            this.mobileLogin = mobileLogin;
        }

        public String getId() {
            return id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getUsername() {
            return username;
        }

        public String getName() {
            return name;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

        @Override
        public String toString() {
            return id;
        }

    }
}
