package com.zjlp.face.web.http.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.zjlp.face.web.constants.ConstantsLogin;
import com.zjlp.face.web.constants.ConstantsSession;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
/**
 * 已废弃
 * @ClassName: UserLoginFilter 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月25日 下午4:22:11
 */
@Deprecated
public class UserLoginFilter implements AuthenticationEntryPoint {

	private Logger _log = Logger.getLogger(this.getClass());

	private ShopBusiness shopBusiness;


	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String targetUrl = null;
		
		String url = request.getRequestURI();
		
		if ((-1 != url.indexOf(ConstantsLogin.WAP_URL_PREFIX) && url.startsWith(ConstantsLogin.WAP_URL_PREFIX))
			|| -1 != url.indexOf(ConstantsLogin.PAY_URL_PREFIX) && url.startsWith(ConstantsLogin.PAY_URL_PREFIX)) {
			
			Shop shop = null;
			
			shop = (Shop) request.getSession().getAttribute(ConstantsSession.SESSION_SHOP_KEY);
			
			if (null == shop) {
				
				String shopNo = this._getPathShopNo(url);
				
				shop = shopBusiness.getShopByNo(shopNo);
				
				if (null == shop) {
					
					response.sendRedirect(request.getContextPath()
							+ "/accessDenied.htm");
				}
				
				request.getSession().setAttribute(ConstantsSession.SESSION_SHOP_KEY, shop);
			}

			request.getSession().setAttribute("redirUrl", url);
			
			targetUrl = ConstantsLogin.WAP_LOGIN_URL;
			
		} else if (-1 != url.indexOf(ConstantsLogin.FREE_URL_PREFIX) && url.startsWith(ConstantsLogin.FREE_URL_PREFIX)) {
			
			targetUrl = ConstantsLogin.FREE_LOGIN_URL;
			
		} else {
			
			targetUrl = ConstantsLogin.ADMIN_LOGIN_URL;
			
		}
		
		targetUrl = request.getContextPath() + targetUrl;
		_log.debug("重定向" + targetUrl);
		response.sendRedirect(targetUrl);
	}

	private String _getPathShopNo(String path) {
		String[] bbb = path.split("/");
		for (int i = 0; i < bbb.length; i++) {
			if (bbb[i] == ConstantsLogin.WAP_URL_PREFIX_TAG || bbb[i].equals(ConstantsLogin.WAP_URL_PREFIX_TAG)) {
				return bbb[i + 1];
			}
		}
		return "";
	}
	
	
	public ShopBusiness getShopBusiness() {
		return shopBusiness;
	}

	public void setShopBusiness(ShopBusiness shopBusiness) {
		this.shopBusiness = shopBusiness;
	}

}
