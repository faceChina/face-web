package com.zjlp.face.web.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zjlp.face.web.constants.ConstantsLogin;
import com.zjlp.face.web.constants.ConstantsSession;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.util.redis.RedisFactory;

public class WapFilter implements Filter {

	
	private Logger _log = Logger.getLogger(this.getClass());
	
	private ShopBusiness shopBusiness;
	
//	private MemberBusiness memberBusiness;
	
	private SubbranchBusiness subbranchBusiness;
	@Override
	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Shop shop = null;
		String path = req.getServletPath();
		String subbranchId=req.getParameter(ConstantsSession.SEESION_SUBBRANCH_ID);
		String shopNo = _getPathShopNo(path);
		if(StringUtils.isNotEmpty(subbranchId)){
			Subbranch subbranch = subbranchBusiness.findSubbranchById(Long.valueOf(subbranchId));
			if(null != subbranch && subbranch.getSupplierShopNo().equals(shopNo)){
				req.getSession().setAttribute(ConstantsSession.SEESION_SUBBRANCH_ID, Long.valueOf(subbranchId));
			}
		}
		_log.debug("Wap Filter Url ： "+path);
		if(ConstantsLogin.WAP_LOGIN_URL.equals(path)) {
			filterChain.doFilter(request, response);
			return;
		}
		shop = shopBusiness.getShopByNo(shopNo);
		Long  userId=UserSessionManager.getSessionUserId();
		if(null == shop){
			resp.sendRedirect("/accessDenied.htm");
		}else{
			if(shop.getStatus() == -1){
				resp.sendRedirect("/accessDenied.htm");
				filterChain.doFilter(request, response);
				return;
			}else if(shop.getStatus() == 3&&!shop.getUserId().equals(userId)){
				resp.sendRedirect("/any/freeze-shop.htm");
				return;
			}
//			UserInfo userInfo = UserSessionManager.getSessionUserInfo();
			
//			if(WeixinUtil.isWechatBrowser(req) && null != userInfo && Constants.AUTHENTICATE_TYPE_CERTIFIED.equals(shop.getAuthenticate())){
//				String openId = (String) req.getSession().getAttribute(ConstantsSession.getSessionShopOpenId(shopNo));
//				// 绑定会员卡
//				User user = new User();
//				user.setId(userInfo.getUserId());
//				memberBusiness.bindMemberCard(openId, user, shop);
//			}
			
			_log.debug("Welcome to "+shop.getNo());
			req.getSession().setAttribute(ConstantsSession.SESSION_SHOP_KEY, shop);
			
			/**
			 * 用户最后登录的店铺,放入Redis
			 */
			if(null == request.getParameter("show")){
				final Long shopUserId = shop.getUserId();
				if (null != RedisFactory.getWgjStringHelper() && null != userId) {
					String key = "user_last_login_shop_" + userId;
					try{
						RedisFactory.getWgjStringHelper().set(key, shopUserId);
					}catch(Exception e){
					}
				}
			}
			/**
			 * 
			 */
			
			filterChain.doFilter(request, response);
			return;
		}
		
		filterChain.doFilter(request, response);
		return;
	}
	
	private String _getPathShopNo(String path){
		String[] bbb = path.split("/");
		for(int i=0;i<bbb.length;i++){
			if(bbb[i] == ConstantsLogin.WAP_URL_PREFIX_TAG|| bbb[i].equals(ConstantsLogin.WAP_URL_PREFIX_TAG)){
				return bbb[i+1];
			}
		}
		return "";
	}
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		shopBusiness =  wac.getBean(ShopBusiness.class);
//		memberBusiness = wac.getBean(MemberBusiness.class);
		subbranchBusiness = wac.getBean(SubbranchBusiness.class);
	}
}
