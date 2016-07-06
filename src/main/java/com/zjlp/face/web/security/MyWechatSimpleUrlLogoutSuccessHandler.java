//package com.zjlp.face.web.security;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.util.Assert;
//
//import com.zjlp.face.util.http.HttpClientUtils;
//
//public class MyWechatSimpleUrlLogoutSuccessHandler  extends AbstractAuthenticationTargetUrlRequestHandler 
//	implements LogoutSuccessHandler {
//	
//	protected final Log logger = LogFactory.getLog(this.getClass());
//	
//	private String redirectUrl = "/";
//	
//	private boolean casLogout = false;
//	
//	private String casLogoutUrl = null;
//	
//    
//
//	@Override
//	public void onLogoutSuccess(HttpServletRequest request,
//			HttpServletResponse response, Authentication authentication)
//			throws IOException, ServletException {
//		if (casLogout && null != casLogoutUrl) {
//		    try {
//		    	Map<String,String> map = new HashMap<String, String>();
//		    	map.put("service", redirectUrl);
//				String result = HttpClientUtils.getInstances().doPost(casLogoutUrl, "utf-8", map);
////				JSONObject jsonObj = JSONObject.fromObject(result);
////				String success = jsonObj.getString("success");
//				logger.info(result);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//
//		// String redirectUrl = this.getRedirectUrl(request.getRequestURI());
//
//		// redirectStrategy.sendRedirect(request, response, redirectUrl);
//	}
//	
//	public void setRedirectUrl(String redirectUrl) {
//		Assert.notNull(redirectUrl,"redirectUrl can not be null!");
//		this.redirectUrl = redirectUrl;
//	}
//	
//	public void setCasLogout(boolean casLogout) {
//		this.casLogout = casLogout;
//	}
//
//	public void setCasLogoutUrl(String casLogoutUrl) {
//		this.casLogoutUrl = casLogoutUrl;
//	}
//
//}
