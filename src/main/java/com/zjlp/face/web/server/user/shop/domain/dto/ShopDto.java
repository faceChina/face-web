package com.zjlp.face.web.server.user.shop.domain.dto;

import com.zjlp.face.web.server.user.shop.domain.Shop;



public class ShopDto extends Shop {
	private static final long serialVersionUID = -4310786097939086607L;
	public static final Integer NOTFREE = 1;
	public static final Integer FREE = 2;
	public static final Integer PROXY_TYPE_INNER = 1;  //内部代理类型
	public static final Integer PROXY_TYPE_OUTER = 2;  //外部代理类型
	/** 用户账号 */
	private String loginAccount;
	/** 分类id */
	private Long classificationId;
	/** 微信url */
	private String url;
	/** 代理授权码 */
	private String proxyAuthorizationCode;
	/** 授权码类型*/
	private Integer authorizationCodeType;
	
	public String getLoginAccount(){
		return loginAccount;
	}
	
	public void setLoginAccount(String loginAccount){
		this.loginAccount = loginAccount;
	}
	
	public Integer getAuthorizationCodeType() {
		return authorizationCodeType;
	}

	public void setAuthorizationCodeType(Integer authorizationCodeType) {
		this.authorizationCodeType = authorizationCodeType;
	}

	public Long getClassificationId(){
		return classificationId;
	}
	
	public void setClassificationId(Long classificationId){
		this.classificationId = classificationId;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}

	public String getProxyAuthorizationCode() {
		return proxyAuthorizationCode;
	}

	public void setProxyAuthorizationCode(String proxyAuthorizationCode) {
		this.proxyAuthorizationCode = proxyAuthorizationCode;
	}

	
}
