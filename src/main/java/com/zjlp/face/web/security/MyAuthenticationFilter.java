package com.zjlp.face.web.security;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.web.constants.ConstantsSession;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.user.user.business.UserBusiness;

public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	
	protected MyAuthenticationFilter() {
        super("/j_spring_security_check");
    }

	private Logger _log = Logger.getLogger("userLoginLog");
	
	private static final String USERNAME = "username";
	
	private static final String PASSWORD = "password";
	
	
	@Autowired
	private UserBusiness userBussiness;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException {
		
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException utfe) {
			_log.debug("编码格式异常" + utfe);
		}
		
		// 1.获取用户名密码
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		String _spring_security_remember_me=request.getParameter("_spring_security_remember_me");
		_log.info("_spring_security_remember_me is :" + _spring_security_remember_me);
		
		// 2.验证用户账号与密码是否正确
		UserInfo userInfo = userBussiness.getUserInfo(username, password);
		
		//UserInfo userInfo = null;
		request.getSession().setAttribute(ConstantsSession.SESSION_LOGIN_TYPE, request.getParameter("loginType"));
		// 3.验证失败用户名或者密码错误抛出异常
		if(null == userInfo) _userException(request,username);

		// 6.session的操作
		_operateSession(request,userInfo);
		
		// 7.实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		// 8.允许子类设置详细属性
		setDetails(request, authRequest);
		
		// 9.运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	private void _operateSession(HttpServletRequest request,UserInfo userInfo){
		request.getSession().removeAttribute(ConstantsSession.SESSION_ERROR_MSG);
		request.getSession().removeAttribute(ConstantsSession.SESSION_CODE_ERROR_MSG);
		request.getSession().setAttribute(ConstantsSession.SESSION_USER_INFO_KEY, userInfo);
		
		if(0 <= request.getRequestURI().indexOf("j_spring_security_check")){
			request.getSession().setAttribute("jzsession", "rms");
			return;
		}
	}
	
	
	private void _userException(HttpServletRequest request,String username){
		HttpSession session = request.getSession(false); 
        if (session != null ) {
        	request.getSession().setAttribute(ConstantsSession.SESSION_ERROR_MSG, true);
        }
        request.getSession().setAttribute(ConstantsSession.SESSION_REMEBER_USERNAME, username);
        throw new AuthenticationServiceException("用户名或者密码错误！");
	}
	

	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainPassword(HttpServletRequest request) {
		String loginPassword = this._getPassWord(request);
		return DigestUtils.jzShaEncrypt(loginPassword);
	}
	
	private String _getPassWord(HttpServletRequest request){
		Object obj = request.getParameter(PASSWORD);
		String loginPassword = (null == obj ? "" : obj.toString());
		return loginPassword;
	}
	
	
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
}
