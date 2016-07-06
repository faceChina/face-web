package com.zjlp.face.web.security.cas.userdetails;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.zjlp.face.web.appvo.JSONInfo;
import com.zjlp.face.web.util.AssConstantsUtil;

public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
    	JSONInfo<Object> jsoninfo = new JSONInfo<Object>();
    	jsoninfo.setCode(AssConstantsUtil.UserCode.LOGIN_ERROR_CODE);
    	jsoninfo.setMsg("");
    	String reqStr =  jsoninfo.toFailureJsonString();
    	response.setHeader("Content-Type", "application/json;charset=UTF-8");
		 PrintWriter out;
		 out = response.getWriter();
		 out.println(reqStr);
		 out.flush();
		 out.close();
		 return;
	}
}
