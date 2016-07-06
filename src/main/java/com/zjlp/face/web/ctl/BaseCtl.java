package com.zjlp.face.web.ctl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.server.user.shop.domain.Shop;
@Controller
@SessionAttributes({"reurl"})
public class BaseCtl {
	
	
	
	protected static final String EXT = ".htm";
	
	/** 重定向前缀 */
	protected static final String REDIRECT_URL_PREFIX = "redirect:";
	
	/** 重定向后缀 */
	protected static final String REDIRECT_URL_SUFFIX = ".htm";
	
	/** 跳转(默认) */
	protected static final String FORWARD_URL_PREFIX = "forward:";
	
	protected static String getRedirectUrl(String url){
		return getRedirectUrl(null,url);
	}
	
	protected static String getForwardUrl(String url){
		return getForwardUrl(null,url);
	}	
	
	protected static Long getUserId(){
		return UserSessionManager.getSessionUserId();
	}
	
    private Shop _getShop(){
	       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	       Shop shop = (Shop) request.getSession().getAttribute("shopInfo");
		   return shop;
	}
		
	protected Shop getShop() {
		   return this._getShop();
	}
		
	protected String getShopNo() {
		if(null == this._getShop()){
			return null;
		}
		return this._getShop().getNo();
//		return "HZJZ0001140724232mHd";

		
	}
	
	protected static String getRedirectUrl(String reurl, String url){
		StringBuilder urlBuilder = new StringBuilder(InternalResourceViewResolver.REDIRECT_URL_PREFIX);
		if (StringUtils.isNotBlank(reurl)) {
			urlBuilder.append("/").append(reurl);
		}
		urlBuilder.append(url).append(REDIRECT_URL_SUFFIX);
		return urlBuilder.toString();
	}
	
	protected static String getForwardUrl(String reurl, String url){
		StringBuilder urlBuilder = new StringBuilder(InternalResourceViewResolver.FORWARD_URL_PREFIX);
		if (StringUtils.isNotBlank(reurl)) {
			urlBuilder.append("/").append(reurl);
		}
		urlBuilder.append(url).append(REDIRECT_URL_SUFFIX);
		return urlBuilder.toString();
	}
	
	protected static String getRedirectUrl(String reurl,String url,String paramsUrl){
		StringBuilder urlBuilder = new StringBuilder(InternalResourceViewResolver.REDIRECT_URL_PREFIX);
		if (StringUtils.isNotBlank(reurl)) {
			urlBuilder.append("/").append(reurl);
		}
		urlBuilder.append(url).append(REDIRECT_URL_SUFFIX).append(paramsUrl);
		return urlBuilder.toString();
	}
	
	protected static String getReqJson(boolean isTure,String info){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", isTure);   
		resultMap.put("info", info);
		return JSONObject.fromObject(resultMap).toString();
	}
	
}
