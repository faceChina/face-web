package com.zjlp.face.web.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

@SuppressWarnings("deprecation")
public class MyRememberMeAuthenticationFilter extends RememberMeAuthenticationFilter {

	private List<String> interceptorsUrl;
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		boolean notInterceptors = interceptors(request);
		
		if (notInterceptors) {
			super.doFilter(request, response, chain);
		}else{
			chain.doFilter(request, response);
		}

	}

	/**
	 * Called if a remember-me token is presented and successfully authenticated
	 * by the {@code RememberMeServices} {@code autoLogin} method and the
	 * {@code AuthenticationManager}.
	 */
	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult) {

		super.onSuccessfulAuthentication(request, response, authResult);
	}

	private boolean interceptors(HttpServletRequest request) {
		
		if (null != interceptorsUrl && !interceptorsUrl.isEmpty()) {
			
			String uri = request.getRequestURI();
			
			for (String prefix : interceptorsUrl) {
				
				if (uri.startsWith(prefix)) {
					
					return false;
				}
			}
		}
		return true;
	}

	public List<String> getInterceptorsUrl() {
		return interceptorsUrl;
	}

	public void setInterceptorsUrl(List<String> interceptorsUrl) {
		this.interceptorsUrl = interceptorsUrl;
	}
}
