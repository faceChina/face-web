package com.zjlp.face.web.http.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.zjlp.face.web.constants.ConstantsLogin;

public class LoginCaptchaFilter implements Filter {

	private Logger _log = Logger.getLogger(this.getClass());

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rsp;

//		boolean isNeedCaptcha = this.isNeedCaptcha(request);
//		_log.info("login param isNeedCaptcha is "+ isNeedCaptcha);
//		
//		if (isNeedCaptcha) {
//			
//			String j_captcha = request.getParameter("j_captcha");
//			String s_captcha = (String) request.getSession(true).getAttribute("yzkeyword");
//			_log.info("request param j_captcha is :" + j_captcha +" s_captcha is :" + s_captcha);
//			
//			if (StringUtils.isBlank(j_captcha) 
//					|| StringUtils.isBlank(s_captcha)
//					|| !j_captcha.equals(s_captcha)) {
//				
//				_log.info("Checking j_captcha is failure; Now redirect to "+ConstantsLogin.ADMIN_LOGIN_URL);
//				request.getSession().setAttribute(ConstantsSession.SESSION_CODE_ERROR_MSG, true);
//				response.sendRedirect(ConstantsLogin.ADMIN_LOGIN_URL);
//				return;
//			}
//			
//			_log.info("Checking j_captcha is success ！");
//		}
//		
//		request.getSession().removeAttribute(ConstantsSession.SESSION_CODE_ERROR_MSG);
		filterChain.doFilter(request, response);

	}

	private boolean isNeedCaptcha(HttpServletRequest request) {
		
		String url = request.getHeader("referer");
		_log.debug(" Request referer url is "+ url);
		Assert.hasLength(url, "Error,request referer url  null");
		Set<String> unwantedCaptchaSet = this.getUnwantedCaptchaSet(request);
		Assert.notEmpty(unwantedCaptchaSet, "Error,notNeedCaptchaSet is null");
		
		for (String unwantedCaptcha : unwantedCaptchaSet) {
			if (-1 != url.indexOf(unwantedCaptcha) && url.equals(unwantedCaptcha)) {
				return true;
			}
		}
		return false;
	}
	
	private Set<String> getUnwantedCaptchaSet(HttpServletRequest request){
		
		StringBuilder baseurl = new StringBuilder();
		baseurl.append(request.getScheme())
			   .append("://")
			   .append(request.getServerName());
		if (80 != request.getServerPort()) {
			baseurl.append(":").append(request.getServerPort());
		}
		Set<String> unwantedCaptchaSet = new HashSet<String>(2);
		unwantedCaptchaSet.add(new StringBuilder(baseurl).append(ConstantsLogin.ADMIN_URL_PREFIX).toString());
		unwantedCaptchaSet.add(new StringBuilder(baseurl).append(ConstantsLogin.ADMIN_LOGIN_URL).toString());
		
		return unwantedCaptchaSet;
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

}
