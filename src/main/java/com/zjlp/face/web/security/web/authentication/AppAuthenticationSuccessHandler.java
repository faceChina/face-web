package com.zjlp.face.web.security.web.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.zjlp.face.web.ctl.app.LoginAssistantCtl;
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private LoginAssistantCtl loginAssistantCtl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		String result = loginAssistantCtl.index(request,response);
//		String result = "success";
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		return;

	}

}
