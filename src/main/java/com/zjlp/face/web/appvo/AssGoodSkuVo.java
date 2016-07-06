package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AssGoodSkuVo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long goodId;
    // 商品明细名称
    private String name;
    // 格式化单价
    private String priceStr;
    
    // 颜色代码
    private String colorValueCode;

    // 颜色名称
    private String colorValue;

    // 尺寸代码
    private String sizeValueCode;

    // 尺寸名称
    private String sizeValue;
    
    // 图片路径
    private String picturePath;
    
    // 商品库存
    private Long stock;
    // 商品条码
    private String barCode;
    
    //市场价格
    private Long marketPrice;
    
    private String marketPriceStr;
    //销售价格
    private Long salesPrice;
    
    private String salesPriceStr;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoodId() {
		return goodId;
	}
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public String getColorValueCode() {
		return colorValueCode;
	}
	public void setColorValueCode(String colorValueCode) {
		this.colorValueCode = colorValueCode;
	}
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	public String getSizeValueCode() {
		return sizeValueCode;
	}
	public void setSizeValueCode(String sizeValueCode) {
		this.sizeValueCode = sizeValueCode;
	}
	public String getSizeValue() {
		return sizeValue;
	}
	public void setSizeValue(String sizeValue) {
		this.sizeValue = sizeValue;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
		if (null != marketPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.marketPriceStr = df.format(marketPrice/100.0);
		}else{
			this.marketPriceStr = "0.00";
		}
	}
	public Long getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
		if (null != salesPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.salesPriceStr = df.format(salesPrice/100.0);
		}else{
			this.salesPriceStr = "0.00";
		}
	}
	public String getMarketPriceStr() {
		return marketPriceStr;
	}
	public void setMarketPriceStr(String marketPriceStr) {
		this.marketPriceStr = marketPriceStr;
	}
	public String getSalesPriceStr() {
		return salesPriceStr;
	}
	public void setSalesPriceStr(String salesPriceStr) {
		this.salesPriceStr = salesPriceStr;
	}
}
