package com.zjlp.face.web.http.filter.abstracted;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public abstract class AbstractCasLoginFilter extends GenericFilterBean {
	
	/** 默认字符集 */
    protected static final String DEFAULT_CHARACTER_ENCODING = "utf-8";
	/** 用户名参数 */
    protected static final String LOGIN_PARAM_USERNAME = "username";
	/** 密码参数 */
    protected static final String LOGIN_PARAM_PASSWORD = "password";
    
    protected static final String LOGIN_PARAM_CAPTCHA = "j_captcha";
    
    /** 拦截请求URL */
    protected String filterProcessesUrl;
    /** CAS登录URL */
    protected String casLoginUrl;
    /** CAS返回确认URL */
    protected String service;
    
	public AbstractCasLoginFilter(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
    }
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		Assert.notNull(this.filterProcessesUrl);
		Assert.notNull(this.casLoginUrl);
		Assert.notNull(this.service);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rsp;
		
		request.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
		
		supportPostMehod(request);
		
		if (!requiresAuthentication(request, response)) {
        	filterChain.doFilter(request, response);
            return;
        }
		
		// captcha Filter
		validateCaptchaFilter(request, response, filterChain);
		
		//Login 
		loginFilter(request, response, filterChain);
		
		//default doFilter
		filterChain.doFilter(request, response);
	}
	
	protected boolean validateCaptchaFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)  throws IOException{
		return true;
	}
	
	protected void loginFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)  throws IOException{
	}
	
	
	protected void supportPostMehod(HttpServletRequest request){
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }
	
	
	public String getCasLoginUrl() {
		return casLoginUrl;
	}

	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	

}
