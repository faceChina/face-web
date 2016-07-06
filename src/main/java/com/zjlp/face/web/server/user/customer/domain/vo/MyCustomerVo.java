package com.zjlp.face.web.server.user.customer.domain.vo;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.server.user.customer.domain.AppCustomer;

public class MyCustomerVo extends AppCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Mapping 客户头像 **/
	private String hearderPicture;

	/** Mapping 交易笔数 **/
	private int tradeNo;

	/** Mapping 商品应付总金额(单位：分) */
	private Long payPrice;

	/** Mapping 邮费总金额(单位：分) */
	private Long postFree;

	/** Json 店铺类型 1总店，2分店，3历史分店 */
	private Integer role;

	/** Mapping 历史分店ID */
	private Long subbranchId;

	/** Mapping 总店ID */
	private String shopNo;

	/** Mapping 分店状态 */
	private Integer status;

	/** Json 店铺名 */
	private String shopName;

	/** Json 店铺Logo */
	private String shopLogoUrl;

	/** Json 最近成交时间 */
	private Date latestTime;

	/** Json 商品总额(单位：元) = payPrice + postFree **/
	private String totalPriceStr;

	/** Parameter 排序: 0-交易次数DESC,1-交易次数DESC,2-交易金额DESC,3-交易金额ASC,4-活跃度 **/
	private String orderBy;
	/** 客户数 **/
	private Integer customerQuantity;
	/** 手机号码 **/
	private String cell;

	public String getHearderPicture() {
		return hearderPicture;
	}

	public void setHearderPicture(String hearderPicture) {
		this.hearderPicture = hearderPicture;
	}

	public int getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(int tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Long getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Long payPrice) {
		this.payPrice = payPrice;
	}

	public Long getPostFree() {
		return postFree;
	}

	public void setPostFree(Long postFree) {
		this.postFree = postFree;
	}

	public Date getLatestTime() {
		return latestTime;
	}

	public void setLatestTime(Date latestTime) {
		this.latestTime = latestTime;
	}

	public String getTotalPriceStr() {
		return totalPriceStr;
	}

	public void setTotalPriceStr(String totalPriceStr) {
		this.totalPriceStr = totalPriceStr;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getCustomerQuantity() {
		return customerQuantity;
	}

	public void setCustomerQuantity(Integer customerQuantity) {
		this.customerQuantity = customerQuantity;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopLogoUrl() {
		return shopLogoUrl;
	}

	public void setShopLogoUrl(String shopLogoUrl) {
		this.shopLogoUrl = shopLogoUrl;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
