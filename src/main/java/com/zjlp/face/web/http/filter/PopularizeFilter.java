package com.zjlp.face.web.http.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.http.cookies.Cookies;
import com.zjlp.face.web.security.bean.UserInfo;

public class PopularizeFilter implements Filter {
	
	public static final String POPULARIZE_SHARE_COOKIE="POPULARIZE_SHARE_COOKIE"; 
	
	public static final String POPULARIZE_SHARE_SESSION="POPULARIZE_SHARE_SESSION_";
	
	public static final String POPULARIZE_SHARE_COOKIE_MAXAGE ="POPULARIZE_SHARE_COOKIE_MAXAGE";
	
	private Log _log = LogFactory.getLog(PopularizeFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String shareId = req.getParameter("shareId");
		if (StringUtils.isNotBlank(shareId) && !Pattern.matches(Sms.SMS_PHONE_REG,shareId)) {
			_log.info("shareId "+shareId+" is not matches!");
			filterChain.doFilter(req, resp);
			return;
		}
		Cookie cookie = Cookies.get(req, POPULARIZE_SHARE_COOKIE);
		
		UserInfo user = (UserInfo) req.getSession().getAttribute("userInfo");
		_log.debug("shareId :"+shareId+" cookie :"+cookie+" user: "+user);
		
		if (null!=shareId && !"".equals(shareId)) {
			
			String maxAge = PropertiesUtil.getContexrtParam(POPULARIZE_SHARE_COOKIE_MAXAGE);
			if (null == maxAge || "".equals(maxAge)) {
				//默认最大时间-1 浏览器关闭时无效
				maxAge = String.valueOf(-1);
			}
			
			cookie = Cookies.add(cookie, resp, POPULARIZE_SHARE_COOKIE, shareId,Integer.valueOf(maxAge));
			
			if (null != user) {
				
				//将shareId存放至Session
		    	StringBuffer sessionIdbBuffer = new StringBuffer(POPULARIZE_SHARE_SESSION);
				sessionIdbBuffer.append(user.getUserId());
				
				req.getSession().setAttribute(sessionIdbBuffer.toString(), shareId);
				
				//存在Session完成后，删除Cookie
				Cookies.remove(req, resp, POPULARIZE_SHARE_COOKIE);
			}
		}
		
		if (null != cookie && 0 != cookie.getMaxAge()) {
			
			shareId = cookie.getValue();
			
			if (null != user && null != cookie.getValue()) {
				
		    	StringBuffer sessionIdbBuffer = new StringBuffer(POPULARIZE_SHARE_SESSION);
				sessionIdbBuffer.append(user.getUserId());
				
				Object obj = req.getSession().getAttribute(sessionIdbBuffer.toString());
				
				if (null == obj) {
					//将shareId存放至Session
					req.getSession().setAttribute(sessionIdbBuffer.toString(), shareId);
					
					//存在Session完成后，删除Cookie
					Cookies.remove(req, resp, POPULARIZE_SHARE_COOKIE);
				}
			}
		}
		
		filterChain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public static String getShareId(HttpSession httpSession,Long userId){
		return (String) httpSession.getAttribute(POPULARIZE_SHARE_SESSION+userId);
	}
	
	
	public static String getCookiesShareId(HttpServletRequest request){
		Cookie cookie = Cookies.get(request, POPULARIZE_SHARE_COOKIE);
		if (null == cookie || 0 == cookie.getMaxAge()) {
			return null;
		}
		return cookie.getValue();
	}

}