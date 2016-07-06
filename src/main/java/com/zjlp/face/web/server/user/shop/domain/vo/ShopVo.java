package com.zjlp.face.web.server.user.shop.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.shop.domain.Shop;

public class ShopVo implements Serializable {

	private static final long serialVersionUID = 8533845576132854079L;
	public static final Integer KD = 1;//快递
	public static final Integer PS = 2;//配送
	public static final Integer ZT = 4;//自提
	public static final Integer IN_PROXY_TYPE = 1;//内部代理商
	public static final Integer OUT_PROXY_TYPE = 2;//外部代理商
	private String no;
	
	private String buyShopNo;
	// 用户ID
	private Long userId;
	// 店铺名称
	private String name;
	// 绑定时间
	private Date bindingTime;
	// 签约时间
	private Date signingTime;
	// 有效时间
	private Date effectiveTime;
	//类型：1官网 2 商城
    private Integer type;
    //配送方式
    private Integer logisticsTypes;
    //邮费
	private Long postFee ;
	//代理类型
	private Integer proxyType;
	//微信
	private String wechat;
	//店铺邀请码
	private String invitationCode;
	//是否免费（判断是否是供货商）
	private Integer isFree;
	//引导关注公众号页面
	private String guideUrl;
	//店铺授权码
	private String authorizationCode;
	//店铺logo
	private String shopLogoUrl;
	
	
	public String getShopLogoUrl() {
		return shopLogoUrl;
	}

	public void setShopLogoUrl(String shopLogoUrl) {
		this.shopLogoUrl = shopLogoUrl;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getBuyShopNo() {
		return buyShopNo;
	}

	public void setBuyShopNo(String buyShopNo) {
		this.buyShopNo = buyShopNo;
	}

	public String getGuideUrl() {
		return guideUrl;
	}

	public void setGuideUrl(String guideUrl) {
		this.guideUrl = guideUrl;
	}

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getProxyType() {
		if (null != proxyType) {
			if (ShopVo.IN_PROXY_TYPE == proxyType) {
				return "内部代理商";
			}
			if (ShopVo.OUT_PROXY_TYPE == proxyType) {
				return "外部代理商";
			}
		} else {
			return null;
		}
		return null;
	}

	public void setProxyType(Integer proxyType) {
		this.proxyType = proxyType;
	}

	public Long getPostFee() {
		return postFee;
	}

	public void setPostFee(Long postFee) {
		this.postFee = postFee;
	}

	public ShopVo() {}
    
    public ShopVo(Integer logisticsTypes) {
    	this.logisticsTypes = logisticsTypes;
    }
    
    public ShopVo(Shop shop) {
    	this.no = shop.getNo();
    	this.name = shop.getName();
    	this.userId = shop.getUserId();
    	this.bindingTime = shop.getBindingTime();
    	this.signingTime = shop.getSigningTime();
    	this.effectiveTime = shop.getEffectiveTime();
    	this.type = shop.getType();
    	this.logisticsTypes = shop.getLogisticsTypes();
    	this.wechat = shop.getWechat();
    	this.proxyType = shop.getProxyType();
    	this.invitationCode = shop.getInvitationCode();
    	this.isFree = shop.getIsFree();
    	this.guideUrl = shop.getGuideUrl();
    	this.authorizationCode = shop.getAuthorizationCode();
    	this.shopLogoUrl = shop.getShopLogoUrl();
    }
    
	public Integer getLogisticsTypes() {
		return logisticsTypes;
	}
	public void setLogisticsTypes(Integer logisticsTypes) {
		this.logisticsTypes = logisticsTypes;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBindingTime() {
		return bindingTime;
	}
	public void setBindingTime(Date bindingTime) {
		this.bindingTime = bindingTime;
	}
	public Date getSigningTime() {
		return signingTime;
	}
	public void setSigningTime(Date signingTime) {
		this.signingTime = signingTime;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeString() {
		if (null == type) {
			return null;
		}
		if (Constants.SHOP_GW_TYPE.equals(type)) {
			return "官网";
		} else if (Constants.SHOP_SC_TYPE.equals(type)) {
			return "商城";
		}
		return null;
	}
	
	public static List<ShopVo> getVoList(List<Shop> shopList) {
		if (null == shopList || shopList.isEmpty()) {
			return null;
		}
		List<ShopVo> list = new ArrayList<ShopVo>();
		for (Shop shop : shopList) {
			if (null == shop) continue;
			list.add(new ShopVo(shop));
		}
		return list;
	}
	
	public static Integer getLogisticsTypesByArr(Integer[] logisticsTypeArr) {
		if (null == logisticsTypeArr || 0 >= logisticsTypeArr.length) {
			return 0;
		}
		Integer sumType = 0;
		for (Integer type : logisticsTypeArr) {
			sumType += type;
		}
		return sumType;
	}
	public Integer getKdType() {
		if (null == logisticsTypes) return 0;
		return KD & logisticsTypes;
	}
	public Integer getPsType() {
		if (null == logisticsTypes) return 0;
		return PS & logisticsTypes;
	}
	public Integer getZtType() {
		if (null == logisticsTypes) return 0;
		return ZT & logisticsTypes;
	}
	

}
