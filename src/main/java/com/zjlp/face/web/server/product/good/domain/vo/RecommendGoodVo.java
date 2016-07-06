package com.zjlp.face.web.server.product.good.domain.vo;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * 推荐商品(使用)
 * @author talo
 *
 */
public class RecommendGoodVo implements Serializable  {
	private static final long serialVersionUID = 2617473136964109814L;
	
	private Long id;				//商品id
	private String name;			//商品名称
	private String picUrl;			//商品图片地址
	private String wapUrl;			//商品链接地址
	private String shopNo;			//店铺No
	private Integer minPrice;		//最低价
	private String minPriceStr;
	private Integer maxPrice;		//最低价
	private String maxPriceStr;
	private Integer inventory;		//商品库存
	private Integer salesCount;		//商品销售数量
	private boolean isSubbranch; 	//是否是代理
	private String subbranchId;  	//代理id
	
	
	public boolean getIsSubbranch() {
		return isSubbranch;
	}
	public void setSubbranch(boolean isSubbranch) {
		this.isSubbranch = isSubbranch;
	}
	
	public String getSubbranchId() {
		return subbranchId;
	}
	public void setSubbranchId(String subbranchId) {
		this.subbranchId = subbranchId;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public String getWapUrl() {
		StringBuffer rul = new StringBuffer(wapUrl);
		if (this.isSubbranch) {
			rul.append("?subbranchId=").append(this.getSubbranchId());
		}
		return rul.toString();
	}
	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	public Integer getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	public Integer getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}
	
	public String getMinPriceStr() {
		if (null != minPrice) {
			DecimalFormat df = new DecimalFormat("##0.00");
			return df.format(this.minPrice/100.0);
		} else {
			return "0.00";
		}
	}
	public void setMinPriceStr(String minPriceStr) {
		this.minPriceStr = minPriceStr;
	}
	
	public String getMaxPriceStr() {
		if (null != maxPrice) {
			DecimalFormat df = new DecimalFormat("##0.00");
			return df.format(this.maxPrice/100.0);
		} else {
			return "0.00";
		}
	}
	public void setMaxPriceStr(String maxPriceStr) {
		this.maxPriceStr = maxPriceStr;
	}
	
}
