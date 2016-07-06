package com.zjlp.face.web.server.operation.subbranch.domain.vo;

import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;

/**
 * 
 * @author 熊斌
 * 2015年8月19日下午2:35:58
 * @version 1.0
 */
public class SubbranchGoodRelationVo extends SubbranchGoodRelation {

	private static final long serialVersionUID = 1L;
	
	//是否设置分店佣金(0:未设置;1:已设置)
	private Integer type;
	//商品名称
    private String name;
    //市场价（单位：分）
    private Long marketPrice;
	//销售价（单位：分）
    private Long salesPrice;
    //店铺No
    private String shopNo;
    //商品主图路径
    private String picUrl;
    //商品sku最小销售值
    private Long priceMin;
    //商品sku最大销售值
    private Long priceMax;
    
	public Long getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(Long priceMin) {
		this.priceMin = priceMin;
	}
	public Long getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(Long priceMax) {
		this.priceMax = priceMax;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Long getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	@Override
	public String toString() {
		return super.toString() + "   SubbranchGoodRelationVo [type=" + type + ", name=" + name
				+ ", marketPrice=" + marketPrice + ", salesPrice=" + salesPrice
				+ ", shopNo=" + shopNo + "]";
	}
}
