//package com.zjlp.face.web.security;
//
//import java.io.IOException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.util.Assert;
//
//import com.zjlp.face.web.constants.ConstantsLogin;
//import com.zjlp.face.web.constants.ConstantsSession;
///**
// * 未通过验证的用户的登录页面指定
// * @ClassName: MyAuthenticationEntryPoint 
// * @Description: (这里用一句话描述这个类的作用) 
// * @author dzq
// * @date 2015年5月25日 上午11:31:17
// */
//public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint  {
//	
//	private Logger _log = Logger.getLogger(this.getClass());
//	
//	/** 是否使用https访问  暂时未使用 */
//	private boolean forceHttps = false;
//	
//	/** 是否使用Forward 否则使用重定向 */
//    private boolean useForward = false;
//    
//    /** 默认登录页面*/
//    private String defaultLoginFormUrl;
//    
//    /** 多个登录页面集合*/
//	private Map<String,String> entryPointMap = new LinkedHashMap<String, String>(); 
//    
//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//	
//	@Override
//	public void commence(HttpServletRequest request,
//			HttpServletResponse response, AuthenticationException authException)
//			throws IOException, ServletException {
//		
//		request.getSession().removeAttribute(ConstantsSession.SESSION_ERROR_MSG);
//		request.getSession().removeAttribute(ConstantsSession.SESSION_CODE_ERROR_MSG);
//		
//		String redirectUrl = this.getRedirectUrl(request);
//		Assert.hasLength(redirectUrl,"request URI can not be null");
//		_log.info("Authentication commence ; Entry Point redirectUrl is : "+ redirectUrl);
//		if (useForward) {
//            request.getRequestDispatcher(redirectUrl).forward(request,response);  
//			return;
//		}
//		redirectStrategy.sendRedirect(request, response, redirectUrl);
//		
//	}
//	
//	private String _getPathShopNo(String path) {
//		String[] bbb = path.split("/");
//		for (int i = 0; i < bbb.length; i++) {
//			if (bbb[i] == ConstantsLogin.WAP_URL_PREFIX_TAG || bbb[i].equals(ConstantsLogin.WAP_URL_PREFIX_TAG)) {
//				return bbb[i + 1];
//			}
//			if (bbb[i] == ConstantsLogin.FREE_URL_PREFIX_TAG || bbb[i].equals(ConstantsLogin.FREE_URL_PREFIX_TAG)	) {
//				return bbb[i + 1];
//			}
//		}
//		return "";
//	}
//	
//	private String getRedirectUrl(HttpServletRequest request){
//		
//		String uri = request.getRequestURI();
//		Assert.hasLength(uri,"request URI must be specified");
//		
//		String shopNo = _getPathShopNo(uri);
//		
//		Assert.hasLength(defaultLoginFormUrl,"defaultLoginFormUrl must be specified; in xml configuration");
//		
//		if (null != entryPointMap &&  !entryPointMap.isEmpty()) {
//			for (Entry<String, String> entry : entryPointMap.entrySet()) {
//				String key = entry.getKey();
//				if (-1 != uri.indexOf(key) && uri.startsWith(key)) {
//					String value = entry.getValue();
//					if (null != shopNo && !"".equals(shopNo) && -1 != value.indexOf("/*")) {
//						value = value.replace("/*", "/"+shopNo);
//					}
//					return value;
//				}
//			}
//		}
//		return defaultLoginFormUrl;
//	}
//
//	public boolean isForceHttps() {
//		return forceHttps;
//	}
//
//	public void setForceHttps(boolean forceHttps) {
//		this.forceHttps = forceHttps;
//	}
//
//	public boolean isUseForward() {
//		return useForward;
//	}
//
//	public void setUseForward(boolean useForward) {
//		this.useForward = useForward;
//	}
//
//	public Map<String, String> getEntryPointMap() {
//		return entryPointMap;
//	}
//
//	public void setEntryPointMap(Map<String, String> entryPointMap) {
//		this.entryPointMap = entryPointMap;
//	}
//	
//	
//    public String getDefaultLoginFormUrl() {
//		return defaultLoginFormUrl;
//	}
//
//	public void setDefaultLoginFormUrl(String defaultLoginFormUrl) {
//		this.defaultLoginFormUrl = defaultLoginFormUrl;
//	}
//
//
//}
