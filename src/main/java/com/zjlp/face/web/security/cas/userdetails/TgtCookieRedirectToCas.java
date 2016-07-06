package com.zjlp.face.web.security.cas.userdetails;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.util.Assert;

import com.zjlp.face.web.http.cookies.Cookies;
/**
 * 自定义重定向CAS
 * @author 熊斌
 * 2015年8月21日下午7:51:18
 * @version 1.0
 */
public class TgtCookieRedirectToCas {

	private Log log = LogFactory.getLog(getClass());
	
    private ServiceProperties serviceProperties;
    
    private RequestCache requestCache = new HttpSessionRequestCache();
    
    private String casLoginUrl;
    

	public Cookie getTgtCookie(HttpServletRequest request){
		Cookie tgtCookie = Cookies.get(request, "CASTGC");
		log.info("Remote native App TGT Cookie is :" + tgtCookie);
		return tgtCookie;
	}

	/** Redirect to cas */
	public boolean redirectToCas(HttpServletRequest request,
			HttpServletResponse response,Cookie tgtCookie) throws IOException{
		if (null != tgtCookie && StringUtils.isNotBlank(tgtCookie.getValue())) {
			
			log.info(" now redirect To Cas,tgtCookie is "+tgtCookie.getValue());
			
			final String urlEncodedService = createServiceUrl(request, response);
			if (log.isDebugEnabled()) {
				 log.debug("urlEncodedService is ["+urlEncodedService+"] ");
			}
			
			Assert.hasLength(urlEncodedService,"urlEncodedService can not be null"); 
			 
	        String redirectUrl = createRedirectUrl(urlEncodedService);
			Assert.hasLength(redirectUrl,"redirectUrl can not be null");
			redirectUrl = redirectUrl+(redirectUrl.indexOf("?") != -1 ? "&" : "?")+"ticketGrantingTicketId="+tgtCookie.getValue();
			if (log.isDebugEnabled()) {
				 log.debug("redirectUrl cas url is ["+urlEncodedService+"] ");
			}
			requestCache.saveRequest(request, response);
			response.sendRedirect(redirectUrl);
	        log.info("Redirect to ["+redirectUrl+"]  success");
	      
			return true;
		}
		return false;
	}
	
	public String createRedirectUrl(final String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.casLoginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }
	
	public String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
        return CommonUtils.constructServiceUrl(null, response, this.serviceProperties.getService(), null, this.serviceProperties.getArtifactParameter(), true);
    }

    
	public ServiceProperties getServiceProperties() {
		return serviceProperties;
	}

	public void setServiceProperties(ServiceProperties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	public String getCasLoginUrl() {
		return casLoginUrl;
	}

	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}

	public RequestCache getRequestCache() {
		return requestCache;
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	
	
	
}
