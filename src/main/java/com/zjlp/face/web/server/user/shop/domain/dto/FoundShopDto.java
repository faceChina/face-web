package com.zjlp.face.web.server.user.shop.domain.dto;

import java.math.BigDecimal;

import com.zjlp.face.web.server.user.shop.domain.Shop;

public class FoundShopDto extends Shop{

	private static final long serialVersionUID = 1L;
	
	/** 店铺背景URL */
	private String shopPictureUrl;

	/** 店铺浏览器url **/
	private String shopUrl;

	/** 排序 **/
	private Integer orderBy;

	/** 经度 **/
	private BigDecimal longitude;
	/** 纬度 **/
	private BigDecimal latitude;

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public String getShopPictureUrl() {
		return shopPictureUrl;
	}

	public void setShopPictureUrl(String shopPictureUrl) {
		this.shopPictureUrl = shopPictureUrl;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

}
