package com.zjlp.face.web.ctl.wap;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zjlp.face.web.constants.ConstantsSession;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.domain.User;

@Controller
public class WapCtl {
	
	protected static final String EXT = ".htm";
	
	/** 重定向前缀 */
	protected static final String REDIRECT_URL_PREFIX = "redirect:";
	
	/** 重定向后缀 */
	protected static final String REDIRECT_URL_SUFFIX = ".htm";
	
	/** 跳转(默认) */
	protected static final String FORWARD_URL_PREFIX = "forward:";
	
	
	//判断是否是微信用户
	protected static boolean getIsWechat(User user) {
		if (null != user  && null != user.getRegisterSourceType() && user.getRegisterSourceType().equals(1) && null != user.getRegisterSourceUserId()) {
//		if (null != user && null != user.getSource() && user.getSource().equals(new Integer(1)) && null != user.getOpenId()) {
			return true;
		} else {
			return false;
		}
	}
	
	protected static String getRedirectUrl(String url){
		return getRedirectUrl(null,url);
	}
	
	protected static String getRedirectUrlNoHtm(String url){
		StringBuilder urlBuilder = new StringBuilder(InternalResourceViewResolver.REDIRECT_URL_PREFIX);
		urlBuilder.append(url);
		return urlBuilder.toString();
	}
	
	protected static String getForwardUrl(String url){
		return getForwardUrl(null,url);
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
	
	public static WgjUser getLoginUser() {  
		return UserSessionManager.getLoginUser();
	} 
	
	protected UserInfo getUser() {
		return UserSessionManager.getSessionUserInfo();
	}
	
	protected Long getUserId() {
		return UserSessionManager.getSessionUserId();
	}
	
	private Shop _getShop(){
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       Shop shop = (Shop) request.getSession().getAttribute("shop");
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
	}
	protected Long getSubbranchId(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    Long subbranchId = (Long) request.getSession().getAttribute(ConstantsSession.SEESION_SUBBRANCH_ID);
		return subbranchId;
	}

    protected static String getReqJson(boolean isTure, String info){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", isTure);
        resultMap.put("info", info);
        return JSONObject.fromObject(resultMap).toString();
    }
    
    protected static String appendUrl(String url, Map<String, Object> paramMap) {
    	if (!url.contains(".htm")) {
    		return url;
    	}
    	String[] arr = url.split("\\?");
    	StringBuilder sb = new StringBuilder(arr[0]);
    	sb.append("?");
    	for (String key : paramMap.keySet()) {
    		sb.append(key).append("=").append(paramMap.get(key)).append("&");
		}
    	sb.append(arr[1]);
    	return sb.toString();
    }
}
