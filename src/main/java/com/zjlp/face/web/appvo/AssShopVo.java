package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AssShopVo implements Serializable {
	
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	/**店铺名称**/
	private String name;
	/**店铺编号**/
	private String shopNo;
	/**店铺背景图片**/
	private String picturePath;
	/**出售商品**/	
	private Long  good_SellCount;
	/**仓库中商品**/	
	private Long  good_StorageCount;
	/**已售完商品**/
	private Long  good_SellOutCount;
	/**订单个数**/
	private Integer order_TodayCount;
	/**订单已付款个数**/
	private Integer order_PayCount;
	/**订单全部金额**/
	private Long order_TotalAmount;
	/**订单全部金额格式化**/
	private String order_TotalAmountStr;
	/**待发送订单**/
	private Integer order_sendOrderNumber;
	
	/**店铺url**/
	private String shopUrl;
	/** 店铺地址 **/
	private String shopAddress;
	/**店铺角色
	 * 1.内部供应商
	 * 2.外部供应商
	 * 3.内部分销商
	 * 4.外部分销商
	 */
	private int role;
	
	private boolean isInner; 
    /**分店ID*/
	private Long subbranchId;

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public Long getGood_SellCount() {
		return good_SellCount;
	}
	public void setGood_SellCount(Long good_SellCount) {
		if (null ==  good_SellCount){
			this.good_SellCount = 0L;
		}else{
			this.good_SellCount = good_SellCount;
		}
		
	}
	public Long getGood_StorageCount() {
		return good_StorageCount;
	}
	public void setGood_StorageCount(Long good_StorageCount) {
		if (null ==  good_StorageCount){
			this.good_StorageCount = 0L;
		}else{
			this.good_StorageCount = good_StorageCount;
		}
		
	}
	public Long getGood_SellOutCount() {
		return good_SellOutCount;
	}
	public void setGood_SellOutCount(Long good_SellOutCount) {
		
		if (null ==  good_SellOutCount){
			this.good_SellOutCount = 0L;
		}else{
			this.good_SellOutCount = good_SellOutCount;
		}
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Integer getOrder_TodayCount() {
		return order_TodayCount;
	}
	public void setOrder_TodayCount(Integer order_TodayCount) {
		if (null ==  order_TodayCount){
			this.order_TodayCount = 0;
		}else{
			this.order_TodayCount = order_TodayCount;
		}
	}
	public Integer getOrder_PayCount() {
		return order_PayCount;
	}
	public void setOrder_PayCount(Integer order_PayCount) {
		if (null ==  order_PayCount){
			this.order_PayCount = 0;
		}else{
			this.order_PayCount = order_PayCount;
		}
	}
	public Long getOrder_TotalAmount() {
		return order_TotalAmount;
	}
	public void setOrder_TotalAmount(Long order_TotalAmount) {
		if (null == order_TotalAmount){
			this.order_TotalAmount = 0L;
			this.order_TotalAmountStr = "0.00";
		}else{
			this.order_TotalAmount = order_TotalAmount;
			DecimalFormat df = new DecimalFormat("##0.00");
			this.order_TotalAmountStr = df.format(order_TotalAmount/100.0);
		}
	}
	public String getOrder_TotalAmountStr() {
		return order_TotalAmountStr;
	}
	public void setOrder_TotalAmountStr(String order_TotalAmountStr) {
		this.order_TotalAmountStr = order_TotalAmountStr;
	}
	public String getShopUrl() {
		return shopUrl;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
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

	public Integer getOrder_sendOrderNumber() {
		return order_sendOrderNumber;
	}

	public void setOrder_sendOrderNumber(Integer order_sendOrderNumber) {
		this.order_sendOrderNumber = order_sendOrderNumber;
	}

	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

}
