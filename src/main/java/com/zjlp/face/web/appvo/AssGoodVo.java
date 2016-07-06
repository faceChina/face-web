package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AssGoodVo implements Serializable{
	
    /**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 8499391553204030101L;
	// 主键
    private Long id;
    // 店铺
	private String shopNo;
    // 商品名称
    private String name;
    
    //格式化 销售价
    private String salesPriceStr;
    
    // 销售价
    private Long salesPrice;
    
    // 库存
    private Long inventory;
    // 商品图片路径
    private String path;
    // 商品详情路径
    private String goodInfopath;
	// 商品详情路径卖家自己看
	private String goodInfopathForSeller;
    // 商品编号
    private String productNumber;

    // 商务业务类型ID
    private Long classificationId;
    // 电脑端 商品
    private String isPcGoods;
    // 简介
    private String goodContent;
    // 市场价
    private Long marketPrice;
    // 市场价格式化
    private String marketPriceStr;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSalesPriceStr() {
		return salesPriceStr;
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
	public Long getInventory() {
		return inventory;
	}
	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		if (null == path){
			this.path = "";
		}else{
			this.path = path;
		}
	}
	public void setSalesPriceStr(String salesPriceStr) {
		this.salesPriceStr = salesPriceStr;
	}
	public String getGoodInfopath() {
		StringBuilder builder = new StringBuilder("/wap/");
		builder.append(this.getShopNo()).append("/any/item/").append(this.getId()).append(".htm");
		return builder.toString();
	}

	public String getGoodInfopathForSeller() {
		StringBuilder builder = new StringBuilder("/wap/");
		builder.append(this.getShopNo()).append("/any/item/seller/").append(this.getId()).append(".htm");
		return builder.toString();
	}

	public void setGoodInfopathForSeller(String goodInfopathForSeller) {
		this.goodInfopathForSeller = goodInfopathForSeller;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public Long getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(Long classificationId) {
		this.classificationId = classificationId;
	}
	public String getGoodContent() {
		return goodContent;
	}
	public void setGoodContent(String goodContent) {
		this.goodContent = goodContent;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setGoodInfopath(String goodInfopath) {
		this.goodInfopath = goodInfopath;
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
	public String getMarketPriceStr() {
		return marketPriceStr;
	}
	public String getIsPcGoods() {
		return isPcGoods;
	}
	public void setIsPcGoods(String isPcGoods) {
		this.isPcGoods = isPcGoods;
	}
	
}
