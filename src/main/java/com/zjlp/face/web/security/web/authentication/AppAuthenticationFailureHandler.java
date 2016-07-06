package com.zjlp.face.web.security.web.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.zjlp.face.web.appvo.JSONInfo;
import com.zjlp.face.web.util.AssConstantsUtil;

public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
	 	JSONInfo<Object> jsoninfo = new JSONInfo<Object>();
    	jsoninfo.setCode(AssConstantsUtil.UserCode.LOGIN_ERROR_CODE);
    	jsoninfo.setMsg("");
    	String reqStr =  jsoninfo.toFailureJsonString();
    	response.setHeader("Content-Type", "application/json;charset=UTF-8");
		 PrintWriter out;
			try {
				 out = response.getWriter();
				 out.println(reqStr);
				 out.flush();
				 out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
