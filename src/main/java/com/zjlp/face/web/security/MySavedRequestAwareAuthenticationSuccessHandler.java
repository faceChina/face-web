package com.zjlp.face.web.security;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.zjlp.face.web.constants.ConstantsLogin;
import com.zjlp.face.web.security.cached.RedirectLoginCached;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();
    
	private Map<String,String> indexPointMap =  new LinkedHashMap<String, String>();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        
        logger.info("request server is " + request.getServerName()+":"+request.getServerPort());
    	
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (logger.isDebugEnabled()) {
        	 logger.debug("AuthenticationSuccess savedRequest  is " + savedRequest);
		}
        
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        
        String reUrl = savedRequest.getRedirectUrl();
        if (logger.isDebugEnabled()) {
       	 logger.debug("AuthenticationSuccess savedRequest getRedirectUrl  is " + reUrl);
		}
        if(-1 == reUrl.indexOf(ConstantsLogin.WAP_URL_PREFIX) && -1 == reUrl.indexOf(ConstantsLogin.PAY_URL_PREFIX)){
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        
        String targetUrlParameter = getTargetUrlParameter();
        if (logger.isDebugEnabled()) {
          	 logger.debug("AuthenticationSuccess savedRequest targetUrlParameter  is " + targetUrlParameter);
   		}
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        
        clearAuthenticationAttributes(request);
        
        String targetUrl = savedRequest.getRedirectUrl();
        logger.info("AuthenticationSuccess Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
    
    
	@Override
	protected String determineTargetUrl(HttpServletRequest request,
			HttpServletResponse response) {
		
		RedirectLoginCached redirectCached = RedirectLoginCached.getInstance();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(redirectCached.get(RedirectLoginCached.LOGIN_KEY))) {
			String boforeLogoutCacheUrl = getRedirectUrl(redirectCached.get(RedirectLoginCached.LOGIN_KEY));
			redirectCached.remove(RedirectLoginCached.LOGIN_KEY);
        	return boforeLogoutCacheUrl;
		}
		
		return super.determineTargetUrl(request, response);
	}
    
    private String getRedirectUrl(String uri){
		
		Assert.hasLength(uri,"request URI must be specified");
		
		String shopNo = _getPathShopNo(uri);
		
		Assert.hasLength(getDefaultTargetUrl(),"redirectUrl must be specified; in xml configuration");
		if (null != indexPointMap &&  !indexPointMap.isEmpty()) {
			for (Entry<String, String> entry : indexPointMap.entrySet()) {
				String key = entry.getKey();
				
				if (-1 != uri.indexOf(key) && uri.startsWith(key)) {
					String value = entry.getValue();
					value = value.replace("/*", "/"+shopNo);
					return value;
				}
			}
		}
		return getDefaultTargetUrl();
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
	
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
    

    public void setIndexPointMap(Map<String, String> indexPointMap) {
		this.indexPointMap = indexPointMap;
	}
    
}
