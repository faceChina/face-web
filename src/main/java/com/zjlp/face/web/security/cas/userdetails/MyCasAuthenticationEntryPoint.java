package com.zjlp.face.web.security.cas.userdetails;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.util.Assert;

import com.zjlp.face.web.constants.ConstantsLogin;
import com.zjlp.face.web.http.cookies.Cookies;
/**
 * 未通过验证的用户的登录页面指定
 * @ClassName: MyAuthenticationEntryPoint 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月25日 上午11:31:17
 */
public class MyCasAuthenticationEntryPoint implements AuthenticationEntryPoint  {
	
	private Logger _log = Logger.getLogger(this.getClass());
	
    private ServiceProperties serviceProperties;
    
    private String casLoginUrl;
    
	/** 是否使用https访问  暂时未使用 */
	private boolean forceHttps = false;
	
	/** 是否使用Forward 否则使用重定向 */
    private boolean useForward = false;
    
    /** 默认登录页面*/
    private String defaultLoginFormUrl;
    
    /** 多个登录页面集合*/
	private Map<String,String> entryPointMap = new LinkedHashMap<String, String>(); 
    
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    private RequestCache requestCache ;
    
	@Deprecated
    private boolean encodeServiceUrlWithSessionId = true;
	
	private boolean isDebug = false;
	
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		Cookie tgtCookie = null;
		if(isDebug){
			String tgt = request.getParameter("TGT");
			if (redirectToCasTest(request, response, tgt)) {
				return;
			}
		}else{
			tgtCookie = getTgtCookie(request);
			
			if (redirectToCas(request, response, tgtCookie)) {
				return;
			}
		}
		redirectToLoginPage(request, response);
	}
	
	private Cookie getTgtCookie(HttpServletRequest request){
		Cookie tgtCookie = Cookies.get(request, "CASTGC");
		_log.info("Remote native App TGT Cookie is :" + tgtCookie);
		return tgtCookie;
	}
	
	/** Redirect to cas */
	private boolean redirectToCasTest(HttpServletRequest request,
			HttpServletResponse response,String tgt) throws IOException{
		if ( StringUtils.isNotBlank(tgt)) {
			
			final String urlEncodedService = createServiceUrl(request, response);
			 _log.info("urlEncodedService is ["+urlEncodedService+"] ");
			Assert.hasLength(urlEncodedService,"urlEncodedService can not be null"); 
			 
	        String redirectUrl = createRedirectUrl(urlEncodedService);
			Assert.hasLength(redirectUrl,"redirectUrl can not be null");
			redirectUrl = redirectUrl+(redirectUrl.indexOf("?") != -1 ? "&" : "?")+"ticketGrantingTicketId="+tgt;
			
			redirectStrategy.sendRedirect(request, response, redirectUrl);
	        _log.info("Redirect to ["+redirectUrl+"]  success");
	      
			return true;
		}
		return false;
	}
	
	/** Redirect to cas */
	private boolean redirectToCas(HttpServletRequest request,
			HttpServletResponse response,Cookie tgtCookie) throws IOException{
		if (null != tgtCookie && StringUtils.isNotBlank(tgtCookie.getValue())) {
			
			_log.info(" now redirect To Cas,tgtCookie is "+tgtCookie.getValue());
			
			final String urlEncodedService = createServiceUrl(request, response);
			if (_log.isDebugEnabled()) {
				 _log.debug("urlEncodedService is ["+urlEncodedService+"] ");
			}
			
			Assert.hasLength(urlEncodedService,"urlEncodedService can not be null"); 
			 
	        String redirectUrl = createRedirectUrl(urlEncodedService);
			Assert.hasLength(redirectUrl,"redirectUrl can not be null");
			redirectUrl = redirectUrl+(redirectUrl.indexOf("?") != -1 ? "&" : "?")+"ticketGrantingTicketId="+tgtCookie.getValue();
			if (_log.isDebugEnabled()) {
				 _log.debug("redirectUrl cas url is ["+urlEncodedService+"] ");
			}
			redirectStrategy.sendRedirect(request, response, redirectUrl);
	        _log.info("Redirect to ["+redirectUrl+"]  success");
	      
			return true;
		}
		return false;
	}
	
	/** Redirect to login page */
	private void redirectToLoginPage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		
		//TODO 补丁,商品详细页面购物登录和流程更改
		String iscallback = request.getParameter("iscallback");
		if(StringUtils.isNotBlank(iscallback) && "true".equals(iscallback)){
			String callbackurl = request.getParameter("callbackurl");
			_log.info("商品详细页面购物流程调整");
			request.getRequestDispatcher(callbackurl).forward(request,response);  
			return;
		}else if("password".equals(iscallback)){
			String callbackurl = request.getParameter("callbackurl");
			_log.info("商品详细页面购物流程调整-忘记密码");
			request.getRequestDispatcher(callbackurl).forward(request,response);  
			return;
		}
		
		
		_log.info(" now redirect To login page ");
		String redirectlocalUrl = this.getRedirectUrl(request);
		Assert.hasLength(redirectlocalUrl,"request URI can not be null");
		_log.info("Authentication commence ; Entry Point redirectUrl is : "+ redirectlocalUrl);
		if (useForward) {
            request.getRequestDispatcher(redirectlocalUrl).forward(request,response);  
		}else{
			redirectStrategy.sendRedirect(request, response, redirectlocalUrl);
		}
	}
	
	
	
    /**
     * Constructs a new Service Url.  The default implementation relies on the CAS client to do the bulk of the work.
     * @param request the HttpServletRequest
     * @param response the HttpServlet Response
     * @return the constructed service url.  CANNOT be NULL.
     */
    protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
        return CommonUtils.constructServiceUrl(null, response, this.serviceProperties.getService(), null, this.serviceProperties.getArtifactParameter(), this.encodeServiceUrlWithSessionId);
    }

    /**
     * Constructs the Url for Redirection to the CAS server.  Default implementation relies on the CAS client to do the bulk of the work.
     *
     * @param serviceUrl the service url that should be included.
     * @return the redirect url.  CANNOT be NULL.
     */
    protected String createRedirectUrl(final String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.casLoginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }
	
	
	private String _getPathShopNo(String path) {
		String[] bbb = path.split("/");
		for (int i = 0; i < bbb.length; i++) {
			if (bbb[i] == ConstantsLogin.WAP_URL_PREFIX_TAG || bbb[i].equals(ConstantsLogin.WAP_URL_PREFIX_TAG)) {
				return bbb[i + 1];
			}
			if (bbb[i] == ConstantsLogin.FREE_URL_PREFIX_TAG || bbb[i].equals(ConstantsLogin.FREE_URL_PREFIX_TAG)	) {
				return bbb[i + 1];
			}
		}
		return "";
	}
	
	private String getRedirectUrl(HttpServletRequest request){
		
		String uri = request.getRequestURI();
		Assert.hasLength(uri,"request URI must be specified");
		
		if (uri.equals("/j_spring_security_check")) {
	    	String targetUrl = request.getHeader("Referer");
	    	if(org.apache.commons.lang3.StringUtils.isNotBlank(targetUrl)){
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
				String localhostPath = request.getScheme()+"://localhost:"+request.getServerPort();
				String baseDomainPath = request.getScheme()+"://127.0.0.1:"+request.getServerPort();
		
				targetUrl = targetUrl.replace(basePath, "");
		    	if (targetUrl.startsWith(ConstantsLogin.WAP_URL_PREFIX)) {
		    		return targetUrl;
				}
				targetUrl = targetUrl.replace(localhostPath, "");
		    	if (targetUrl.startsWith(ConstantsLogin.WAP_URL_PREFIX)) {
		    		return targetUrl;
				}
				targetUrl = targetUrl.replace(baseDomainPath, "");
		    	if (targetUrl.startsWith(ConstantsLogin.WAP_URL_PREFIX)) {
		    		return targetUrl;
				}
			}
		}
		
		String shopNo = _getPathShopNo(uri);
		
		Assert.hasLength(defaultLoginFormUrl,"defaultLoginFormUrl must be specified; in xml configuration");
		
		if (null != entryPointMap &&  !entryPointMap.isEmpty()) {
			for (Entry<String, String> entry : entryPointMap.entrySet()) {
				String key = entry.getKey();
				if (-1 != uri.indexOf(key) && uri.startsWith(key)) {
					String value = entry.getValue();
					if (null != shopNo && !"".equals(shopNo) && -1 != value.indexOf("/*")) {
						value = value.replace("/*", "/"+shopNo);
					}
					return value;
				}
			}
		}
		return defaultLoginFormUrl;
	}

    public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	public boolean isForceHttps() {
		return forceHttps;
	}

	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	public boolean isUseForward() {
		return useForward;
	}

	public void setUseForward(boolean useForward) {
		this.useForward = useForward;
	}

	public void setEntryPointMap(Map<String, String> entryPointMap) {
		this.entryPointMap = entryPointMap;
	}

	public void setDefaultLoginFormUrl(String defaultLoginFormUrl) {
		this.defaultLoginFormUrl = defaultLoginFormUrl;
	}

    /**
     * The enterprise-wide CAS login URL. Usually something like
     * <code>https://www.mycompany.com/cas/login</code>.
     *
     * @return the enterprise-wide CAS login URL
     */
	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}

	public final void setServiceProperties(final ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }
}
