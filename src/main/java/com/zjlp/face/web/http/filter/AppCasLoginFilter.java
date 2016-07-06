package com.zjlp.face.web.http.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.appvo.JSONInfo;
import com.zjlp.face.web.http.filter.abstracted.AbstractCasLoginFilter;
import com.zjlp.face.web.security.util.CasLoginHttpUtil;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.ConvertUtil;

public class AppCasLoginFilter extends AbstractCasLoginFilter {

	private Logger _log = Logger.getLogger(this.getClass());
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
	}
	
	public AppCasLoginFilter(String filterProcessesUrl) {
		super(filterProcessesUrl);
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
		
		super.supportPostMehod(request);
		
		request.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);

        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }
		
		try {
			String  s = ConvertUtil.getBodyString(request.getReader());
			JSONObject jsonObj = ConvertUtil.formatJson(s);
			
			final String username = obtainUsername(jsonObj);
			final String password = obtainPassword(jsonObj);
			
			String jsonInfo = CasLoginHttpUtil.login(casLoginUrl,service,username, password);
			_log.debug("jsonInfo : "+ jsonInfo);
			JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
			if(null != jsonObject && jsonObject.get("success").equals(true)){
				String backUrl = (String) jsonObject.get("backUrl");
				String forwardUri = backUrl.replace(PropertiesUtil.getContexrtParam("SYSTEM.HOME.URL"), "");
				if (!forwardUri.startsWith("/")) {
					forwardUri="/"+forwardUri;
				}
				String ticketId = (String) jsonObject.get("ticketId");
				String serviceTicket = backUrl.substring(backUrl.indexOf("ticket") + "ticket".length()+ 1,backUrl.length());
				_log.debug("ticketId:" + ticketId + ",serviceTicket:"+ serviceTicket + ",backUrl = "+ backUrl);
				request.setAttribute("ticketId", ticketId);
				request.getRequestDispatcher(forwardUri).forward(request,response);  
				return;
			}else{
				_log.error("登录失败:" + jsonObject.get("errmsg"));
			 	JSONInfo<Object> jsoninfo = new JSONInfo<Object>();
		    	jsoninfo.setCode(AssConstantsUtil.UserCode.LOGIN_ERROR_CODE);
		    	jsoninfo.setMsg("");
		    	String reqStr =  jsoninfo.toFailureJsonString();
		    	response.setHeader("Content-Type", "application/json;charset=UTF-8");
				PrintWriter out = null;
				try {
					 out = response.getWriter();
					 out.println(reqStr);
					 out.flush();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}finally{
					if(null != out)out.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage(), e);
		}
	}
	
	
	
	protected String obtainUsername(JSONObject jsonObj) {
		Object obj = jsonObj.get(LOGIN_PARAM_USERNAME);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainPassword(JSONObject jsonObj) {
		Object obj =jsonObj.get(LOGIN_PARAM_PASSWORD);
		String loginPassword = (null == obj ? "" : obj.toString());
		return loginPassword;
	}
	
}
