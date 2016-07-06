package com.zjlp.face.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

import com.zjlp.face.util.http.HttpClientUtils;
import com.zjlp.face.web.constants.ConstantsLogin;

public class MySimpleUrlLogoutSuccessHandler  extends AbstractAuthenticationTargetUrlRequestHandler 
	implements LogoutSuccessHandler {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private Map<String,String> entryPointMap=  new LinkedHashMap<String, String>();
	
	private String redirectUrl = "/";
	
	private boolean casLogout = false;
	
	private String casLogoutUrl = null;
	
    

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (casLogout && null != casLogoutUrl) {
		    try {
				String result = HttpClientUtils.getInstances().doPost(casLogoutUrl, "utf-8", 
						new HashMap<String, String>());
//				JSONObject jsonObj = JSONObject.fromObject(result);
//				String success = jsonObj.getString("success");
				logger.info(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		String redirectUrl = this.getRedirectUrl(request.getRequestURI());

		redirectStrategy.sendRedirect(request, response, redirectUrl);
	}
	
	private String getRedirectUrl(String uri){
		
		Assert.hasLength(uri,"request URI must be specified");
		
		String shopNo = _getPathShopNo(uri);
		

		
		Assert.hasLength(redirectUrl,"redirectUrl must be specified; in xml configuration");
		
		if (null != entryPointMap &&  !entryPointMap.isEmpty()) {
			for (Entry<String, String> entry : entryPointMap.entrySet()) {
				String key = entry.getKey();
				if (-1 != uri.indexOf(key) && uri.startsWith(key)) {
					String value = entry.getValue();
					value = value.replace("/*", "/"+shopNo);
					return value;
				}
			}
		}
		return redirectUrl;
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
	
	public void setRedirectUrl(String redirectUrl) {
		Assert.notNull(redirectUrl,"redirectUrl can not be null!");
		this.redirectUrl = redirectUrl;
	}
	
	public void setEntryPointMap(Map<String, String> entryPointMap) {
		this.entryPointMap = entryPointMap;
	}

	public void setCasLogout(boolean casLogout) {
		this.casLogout = casLogout;
	}

	public void setCasLogoutUrl(String casLogoutUrl) {
		this.casLogoutUrl = casLogoutUrl;
	}

}
