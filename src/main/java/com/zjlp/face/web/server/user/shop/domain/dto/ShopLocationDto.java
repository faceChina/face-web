package com.zjlp.face.web.server.user.shop.domain.dto;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.user.shop.domain.ShopLocation;

public class ShopLocationDto extends ShopLocation {

	private static final long serialVersionUID = 3493250290157531242L;
    public static final Integer NULL_CODE = 0;
	//商家地址
	private String area;
	//市名称
	private String cityName;
	// 店铺背景图
	private String shopPictureUrl;
	// 类型：1官网 2 商城
	private Integer type;
	// 店铺浏览器url
	private String shopUrl;
	//商家联系方式
	private String cell;
	//省份编码
    private Integer peCode;
    //城市编码
    private Integer cityCode;
    public ShopLocationDto(){}
    public ShopLocationDto(ShopLocation loction){
    	this.setId(loction.getId());
    	this.setShopNo(loction.getShopNo());
    	this.setShopName(loction.getShopName());
    	this.setLat(loction.getLat());
    	this.setLng(loction.getLng());
    	this.setAreaCode(loction.getAreaCode());
    	this.setAddress(loction.getAddress());
    	this.setCreateTime(loction.getCreateTime());
    	this.setUpdateTime(loction.getUpdateTime());
    	this.setAreaName(loction.getAreaName());
    }
    
    public static enum Mode {
    	PROVINCE, CITY, AREA
    }
    public void resetCode(Mode mode){
    	if (null == mode) {
    		mode = Mode.AREA;
    	}
    	boolean isNotNullCode = this.isNotNullCode(super.getAreaCode());
    	if (mode.equals(Mode.AREA)) {
    		Assert.isTrue(isNotNullCode);
    	} else if (mode.equals(Mode.CITY) && !isNotNullCode) {
			Assert.notNull(this.getCityCode());
			super.setAreaCode(this.getCityCode());
    	}
    	Assert.notNull(super.getAreaCode(), "PROVINCE's Mode is not support!");
    }
    
    public boolean isNotNullCode(Integer code){
    	return null != code && !NULL_CODE.equals(code);
    }

	public Integer getPeCode() {
		return peCode;
	}

	public void setPeCode(Integer peCode) {
		this.peCode = peCode;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getShopPictureUrl() {
		return shopPictureUrl;
	}

	public void setShopPictureUrl(String shopPictureUrl) {
		this.shopPictureUrl = shopPictureUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	@Override
	public String toString() {
		return super.toString() + "  ShopLocationDto [cell=" + cell + "]";
	}
}
