package com.zjlp.face.web.server.user.customer.domain.dto;

import java.io.Serializable;

public class CustomerOrder implements Serializable{

	private static final long serialVersionUID = 1478373097995576121L;

	/** 用户ID **/
	private Long userId;

	/** 客户ID **/
	private Long customerId;

	/** 总店铺ID **/
	private String shopNo;

	/** 分店铺ID **/
	private Long subId;

	/** 分店铺对用账户号 **/
	private String deliveryRemoteId;

	/** 订单中区分总店和分店订单type 发货者类型（1.店铺号，2.手机号） **/
	private Integer deliveryRemoteType;

	/** 类型：1官网 2 商城 **/
	private Integer shopType;

	/** 店铺名称 **/
	private String shopName;

	/** 店铺图片 **/
	private String shopPicUrl;

	/** 成交笔数 **/
	private int tradeNo;

	/** 成交总额 **/
	private Long totalPayPrice;

	/** 邮费总额 **/
	private Long totalPostFree;

	/** 成交总额 **/
	private String totalPayPriceStr;
	
	/**店铺角色
	 * 1.内部供应商
	 * 2.外部供应商
	 * 3.内部分销商
	 * 4.外部分销商
	 */
	private int role;
	
	private boolean isInner;
	

	public String getTotalPayPriceStr() {
		return totalPayPriceStr;
	}

	public void setTotalPayPriceStr(String totalPayPriceStr) {
		this.totalPayPriceStr = totalPayPriceStr;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(int tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Long getTotalPayPrice() {
		return totalPayPrice;
	}

	public void setTotalPayPrice(Long totalPayPrice) {
		this.totalPayPrice = totalPayPrice;
	}

	public Long getTotalPostFree() {
		return totalPostFree;
	}

	public void setTotalPostFree(Long totalPostFree) {
		this.totalPostFree = totalPostFree;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public Integer getType() {
		return shopType;
	}

	public void setType(Integer shopType) {
		this.shopType = shopType;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public String getShopPicUrl() {
		return shopPicUrl;
	}

	public void setShopPicUrl(String shopPicUrl) {
		this.shopPicUrl = shopPicUrl;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean isInner() {
		return isInner;
	}

	public void setInner(boolean isInner) {
		this.isInner = isInner;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getDeliveryRemoteId() {
		return deliveryRemoteId;
	}

	public void setDeliveryRemoteId(String deliveryRemoteId) {
		this.deliveryRemoteId = deliveryRemoteId;
	}

	public Integer getDeliveryRemoteType() {
		return deliveryRemoteType;
	}

	public void setDeliveryRemoteType(Integer deliveryRemoteType) {
		this.deliveryRemoteType = deliveryRemoteType;
	}

}
