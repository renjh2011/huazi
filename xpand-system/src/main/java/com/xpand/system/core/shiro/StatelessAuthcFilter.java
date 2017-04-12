package com.xpand.system.core.shiro;

import com.alibaba.fastjson.JSONObject;
import com.xpand.common.core.base.ResponseObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessAuthcFilter extends AccessControlFilter {
    private final Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletRequest res = (HttpServletRequest) request;
        String uniqueToken = res.getHeader("token");
        // 生成无状态Token
        StatelessToken token = new StatelessToken(uniqueToken);
        try {
            // 委托给Realm进行登录
            getSubject(request, response).login(token);
        } catch (AuthenticationException e) {
            logger.debug(e.getStackTrace().toString());
        } catch (Exception e) {
            logger.debug(e.getStackTrace().toString());
        }
        return true;
    }

    // 登录失败时默认返回401状态码
    private void onLoginFail(ServletRequest request, ServletResponse response, String e) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response.setContentType("application/json;charset=UTF-8");
        httpResponse.getWriter().write(JSONObject.toJSON(ResponseObject.failure(e)).toString());
    }
}

