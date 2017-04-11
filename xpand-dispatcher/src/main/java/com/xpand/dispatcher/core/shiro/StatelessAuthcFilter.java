package com.xpand.dispatcher.core.shiro;

import com.alibaba.fastjson.JSONObject;
import com.xpand.common.core.base.ResponseObject;
import com.xpand.common.core.config.Resources;
import com.xpand.common.core.exception.LoginException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Code.Ai on 16/8/10.
 * Description: 自定义拦截器,拦截需要登录验证的请求
 * 获取 header 中的 token 委托给 StatelessRealm 进行请求验证
 */
public class StatelessAuthcFilter extends AccessControlFilter {
//    private final Logger logger = LogManager.getLogger(StatelessAuthcFilter.class);
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
//        logger.error("进入StatelessAuthcFilter.onAccessDenied");
        HttpServletRequest res         = (HttpServletRequest) request;
        String             uniqueToken = res.getHeader("token");
        // 生成无状态Token
        StatelessToken token = new StatelessToken(uniqueToken);
        try {
            // 委托给Realm进行登录
            getSubject(request, response).login(token);
        } catch (LockedAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_LOCKED", token.getPrincipal()));
        } catch (DisabledAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_DISABLED", token.getPrincipal()));
        } catch (ExpiredCredentialsException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_EXPIRED", token.getPrincipal()));
        } catch (Exception e) {
            onLoginFail(request,response);
            throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
        }
        return true;
    }

    // 登录失败时默认返回401状态码
    private void onLoginFail(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response.setContentType("application/json;charset=UTF-8");
        //返回用户没有登录错误
        Map<String, Object> map = new HashMap<>();
//        map.put("msg",ApiMsgConstants.TOKEN_ILLEGAL);
//        map.put("httpCode",ApiMsgConstants.TOKEN_ILLEGAL_CODE);
        map.put("timestamp",new Date());
//        httpResponse.getWriter().write(JSONObject.toJSON(map).toString());
        httpResponse.getWriter().write(JSONObject.toJSON(ResponseObject.failure()).toString());
    }
}

