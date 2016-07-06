package com.zjlp.face.web.constants;
/**
 * Session常量类
 * @ClassName: ConstantsSession 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月25日 上午10:58:22
 */
public class ConstantsSession {

	/** Session 中存储的店铺信息 */
	public static final String SESSION_SHOP_KEY = "shop";
	
	/** Session 中存储的用户信息 (已废弃)*/
	@Deprecated
	public static final String SESSION_USER_INFO_KEY="userInfo";
	
	public static final String SESSION_ERROR_MSG = "errorMsg";
	
	/** 登陆验证码验证信息 */
	public static final String SESSION_CAPTCHA_FLAG = "captchaFlag";
	
	public static final String SESSION_CODE_ERROR_MSG = "codeErrorMsg";
	
	public static final String SESSION_REMEBER_USERNAME ="remeberUsername";
	
	public static final String SESSION_LOGIN_TYPE = "loginType";
	/** Session 中存储的店铺OpenI后缀*/
	public static final String SESSION_SHOP_OPENID_SUFFIX= "shopOpenId";
	
	
	public static final String SEESION_ANONYMOUS_OPENID = "anonymousOpenId";
	
	public static final String SEESION_SUBBRANCH_ID = "subbranchId";
	
	
	
	
	
	
	public static String getSessionShopOpenId(String shopNo){
		StringBuffer sessionShopOpenId = new StringBuffer(shopNo);
		sessionShopOpenId.append(SESSION_SHOP_OPENID_SUFFIX);
		return sessionShopOpenId.toString();
	}
}
