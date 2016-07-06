package com.zjlp.face.web.http.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import com.zjlp.face.web.constants.ConstantsLogin;
import com.zjlp.face.web.constants.ConstantsSession;
import com.zjlp.face.web.http.filter.abstracted.AbstractCasLoginFilter;
import com.zjlp.face.web.security.cached.RedirectLoginCached;
import com.zjlp.face.web.security.util.CasLoginHttpUtil;

public class CasLoginFilter extends AbstractCasLoginFilter {

	private Logger logger = Logger.getLogger(this.getClass());
	
    @Autowired
    private AuthenticationEntryPoint myCasAuthenticationEntryPoint;
	
    
	public CasLoginFilter(String filterProcessesUrl) {
		super(filterProcessesUrl);
	}
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
	}
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,FilterChain filterChain) 
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rsp;
		
		if (!requiresAuthentication(request, response)) {
        	filterChain.doFilter(request, response);
            return;
        }
		
		request.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
		
		supportPostMehod(request);
        
		//验证码验证
        boolean isValid = validateCaptchaFilter(request, response, filterChain);
        if (!isValid) {
        	return;
        }
		
		try {
			// 1. get user name and password 
			final String username = obtainUsername(request);
			final String password = obtainPassword(request);
			String jsonInfo = CasLoginHttpUtil.login(casLoginUrl,service,username, password);
			logger.debug("cas login result Json info : "+ jsonInfo);
			
			JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
			if(null != jsonObject && jsonObject.get("success").equals(true)){
				
				RedirectLoginCached.getInstance().put(RedirectLoginCached.LOGIN_KEY, request.getRequestURI());
				String redirectUrl = (String) jsonObject.get("backUrl");
				String ticketId = (String) jsonObject.get("ticketId");
				logger.debug("ticketId:" + ticketId + ",redirectUrl = "+ redirectUrl);
				response.sendRedirect(redirectUrl);
				return;
			}
			logger.error("登录失败:" + jsonObject.get("errmsg"));
			request.getSession().setAttribute(ConstantsSession.SESSION_CODE_ERROR_MSG,jsonObject.get("errmsg"));
			request.getSession().setAttribute(ConstantsSession.SESSION_ERROR_MSG,"用户名不存在或密码错误");
			myCasAuthenticationEntryPoint.commence(request, response, new BadCredentialsException("用户名不存在或密码错误"));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.getSession().setAttribute(ConstantsSession.SESSION_ERROR_MSG,"服务器异常，请稍后再试");
			myCasAuthenticationEntryPoint.commence(request, response, new AuthenticationServiceException("服务器异常，请稍后再试"));
		}
	}
	
	
	@Override
	protected boolean validateCaptchaFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws IOException {
		boolean isValid = true;
		if (isNeedCaptcha(request)) {
			
			String j_captcha = request.getParameter(LOGIN_PARAM_CAPTCHA);
			String s_captcha = (String) request.getSession(true).getAttribute("yzkeyword");
			
			if (logger.isDebugEnabled()) {
				logger.debug("request param j_captcha is :" + j_captcha +" s_captcha is :" + s_captcha);
			}
			
			if (StringUtils.isBlank(j_captcha) || StringUtils.isBlank(s_captcha)|| !j_captcha.equals(s_captcha)) {
				
				logger.info("Checking j_captcha is failure; Now redirect to "+ConstantsLogin.ADMIN_LOGIN_URL);
				response.sendRedirect(ConstantsLogin.ADMIN_LOGIN_URL);
				isValid = false;
			}
			logger.debug("Checking j_captcha is success ！");
		}
		request.getSession().setAttribute(ConstantsSession.SESSION_CAPTCHA_FLAG, isValid);
		return isValid;
	}
	
	private boolean isNeedCaptcha(HttpServletRequest request) {
		
		String url = request.getHeader("referer");
		logger.debug(" Request referer url is "+ url);
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
	
	
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(LOGIN_PARAM_USERNAME);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(LOGIN_PARAM_PASSWORD);
		String loginPassword = (null == obj ? "" : obj.toString());
		return loginPassword;
	}
}
