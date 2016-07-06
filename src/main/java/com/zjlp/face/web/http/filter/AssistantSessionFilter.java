package com.zjlp.face.web.http.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;

import com.zjlp.face.web.appvo.JSONInfo;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.util.AssConstantsUtil;

/**
 * Session 过滤器
 * @ClassName: AssistantSessionFilter 
 * @Description: (App访问过滤没有登录的用户) 
 * @author wxn
 * @date 2014年10月11日 上午10:24:33
 */
public class AssistantSessionFilter implements Filter {
	
	Logger _logger = Logger.getLogger(this.getClass());
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException utfe) {
			_logger.debug("编码格式异常" + utfe);
		}
		
		try {
			WgjUser wgjUser = UserSessionManager.getLoginUser();
			if (null == wgjUser ) {
				this.writer(AssConstantsUtil.System.SESSION_INVALID_CODE, req.getSession(), rep);
				return;
			}
			UserInfo userInfo = wgjUser.getUserInfo();
			if(null == userInfo){
				userInfo = new UserInfo();
			}
			//判断用户是否登录
			if("".equals(userInfo.getLoginAccount()) || null == userInfo.getLoginAccount()){
				this.writer(AssConstantsUtil.System.SESSION_INVALID_CODE, req.getSession(), rep);
				return;
			}
		} catch (Exception e1) {
			_logger.error("应用服务错误",e1);
			this.writer(AssConstantsUtil.System.SESSION_INVALID_CODE, req.getSession(), rep);
			return;
		}
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			_logger.error("应用服务错误",e);
			this.writer(AssConstantsUtil.System.SERVER_ERROR_CODE, req.getSession(), rep);
		}
	}
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	private void writer(int code,HttpSession session,HttpServletResponse rep) throws IOException{
		PrintWriter out = null;
		try {
	    	JSONInfo<Object> jsoninfo = new JSONInfo<Object>();
	    	jsoninfo.setCode(code);
	    	jsoninfo.setMsg("");
	    	String reqStr =  jsoninfo.toFailureJsonString();
			rep.setHeader("Content-Type", "application/json;charset=UTF-8");
			out = rep.getWriter();
			out.println(reqStr);
			out.flush();
		} catch (Exception e) {
			_logger.error("返回参数异常",e);
		}finally{
			if(null != out) out.close();
		}
		
	}
}
