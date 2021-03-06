/*
package com.xpand.common.core.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LocaleInterceptor extends BaseInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		// 设置客户端语言
		Locale locale = (Locale) session.getAttribute("LOCALE");
		if (locale == null) {
			String language = request.getParameter("locale");
			if (StringUtils.isNotBlank(language)) {
				locale = new Locale(language);
				session.setAttribute("LOCALE", locale);
			} else {
				locale = request.getLocale();
			}
		}
		session.setAttribute("HOST", WebUtil.getHost(request));
		LocaleContextHolder.setLocale(locale);
		return super.preHandle(request, response, handler);
	}
}
*/
