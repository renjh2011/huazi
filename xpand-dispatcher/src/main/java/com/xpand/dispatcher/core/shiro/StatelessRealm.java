package com.xpand.dispatcher.core.shiro;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.xpand.common.core.config.Global;
import com.xpand.common.core.config.Resources;
import com.xpand.common.core.exception.LoginException;
import com.xpand.common.core.utils.JwtHelper;
import com.xpand.common.core.utils.RedisUtil;
import com.xpand.dispatcher.modules.sys.model.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.io.Serializable;

/**
 * Created by Code.Ai on 16/8/10.
 * Description:
 */
public class StatelessRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }


    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }

        AuthorizationInfo info = null;

       /* info = (AuthorizationInfo) UserUtils.getCache(UserUtils.CACHE_AUTH_INFO);

        if (info == null) {
            info = doGetAuthorizationInfo(principals);
            if (info != null) {
                UserUtils.putCache(UserUtils.CACHE_AUTH_INFO, info);
            }
        }*/

        return info;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info    = new SimpleAuthorizationInfo();
        String                  userId  = "1";
       /* SystemService systemService=new SystemService();
        User sysUser = systemService.getUser(userId);
        *//*if (sysUser.getUserType() != 1) {
            userId = null;
        }*//*
        List<Menu> list = UserUtils.getMenuList();
        for (Menu menu : list) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                // 添加基于Permission的权限信息
                for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                    info.addStringPermission(permission);
                }
            }
        }*/
//        List<String> list = sysAuthorizeService.queryPermissionByUserId(userId);
        /*for (String permission : list) {getPrincipal
            if (org.apache.commons.lang3.StringUtils.isNotBlank(permission)) {
                // 添加基于Permission的权限信息
                info.addStringPermission(permission);
            }
        }*/
        // 添加用户权限
        info.addStringPermission("user");
        return info;
    }

    //登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String         uniqueToken    = statelessToken.getUniqueToken();
        if(uniqueToken == null){
            throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
        }
        // 解析 token
        Claims claims = JwtHelper.parseJWT(uniqueToken, Global.getConfig("sofn.api.base64Secret"));
        if (claims != null) {
            String account = (String) claims.get("userName");
            String userId = (String) claims.get("userId");
            String role    = (String) claims.get("role");
            String userName = (String) token.getPrincipal();
            String password = (String) token.getCredentials();
            // 如果 redis 没有此 token 登录失败
            Object token1= RedisUtil.get(account + userId+"pc-token");
            User user=new User();
            user.setId(userId);
            AuthenticationInfo info=new SimpleAuthenticationInfo(new Principal(user,false),
                    token1, getName());
            return  info;
        }
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }

    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String mobile; // 手机号码
        private String name; // 姓名
        private boolean mobileLogin; // 是否手机登录

//		private Map<String, Object> cacheMap;

        public Principal(User user, boolean mobileLogin) {
            this.id = user.getId();
            this.mobile = user.getMobile();
            this.name = user.getName();
            this.mobileLogin = mobileLogin;
        }

        public String getId() {
            return id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getName() {
            return name;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}


        @Override
        public String toString() {
            return id;
        }

    }
}
