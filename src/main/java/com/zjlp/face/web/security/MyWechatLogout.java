//package com.zjlp.face.web.security;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.util.Assert;
//
//public class MyWechatLogout {
//
//	protected final Log logger = LogFactory.getLog(this.getClass());
//
//	private final List<LogoutHandler> handlers;
//	private final LogoutSuccessHandler logoutSuccessHandler;
//
//	public MyWechatLogout(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
//		Assert.notEmpty(handlers, "LogoutHandlers are required");
//		this.handlers = Arrays.asList(handlers);
//		Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
//		this.logoutSuccessHandler = logoutSuccessHandler;
//	}
//
//	public void logout(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		logger.info("Logging out user '" + auth + "' and transferring to logout destination");
//
//		for (LogoutHandler handler : handlers) {
//			handler.logout(request, response, auth);
//		}
//
//		logoutSuccessHandler.onLogoutSuccess(request, response, auth);
//
//		return;
//	}
//}
