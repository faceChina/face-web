package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;

public class PurchaseOrderItemVo implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -3785586689835808806L;

	//采购明细ID（PK）
	private Long id;
	//采购单ID
    private Long purchaseOrderId;
    //采购商品ID
    private Long goodId;
    //采购商品名称
    private String goodName;
    //采购商品SKU
    private Long goodSkuId;
    //SKU属性名称 格式：gp:name;gp:value; 如：颜色:红色;尺寸:M
    private String skuPropertyName;
    //SKU图片路径
    private String skuPicturePath;
    //采购数量
    private Long quantity;
    //采购单价
    private Long purchasePrice;
    //分项采购小计 单位：分
    private Long subPurchasePrice;
    //销售单价
    private Long salesPrice;
    //分项销售小计 单位：分
    private Long subSalesPrice;
    //预计销售利润小计 单位：分
    private Long subProfitPrice;
    //创建时间
    private Long createTimeStamp;
    //修改时间
    private Long updateTimeStamp;
    
	private boolean isPopularize = false;
	
	private Long skuPrice;
	
	private String skuPriceStr;
    
	public PurchaseOrderItemVo() {
		super();
	}
	public PurchaseOrderItemVo(PurchaseOrderItemDto dto) {
		super();
		this.id = dto.getId();
		this.purchaseOrderId = dto.getPurchaseOrderId();
		this.goodId = dto.getGoodId();
	    this.setGoodName(dto.getGoodName());
	    this.setGoodSkuId(dto.getGoodSkuId());
	    this.setSkuPropertyName(dto.getSkuPropertyName());
	    this.setSkuPicturePath(dto.getSkuPicturePath());
	    this.setQuantity(dto.getQuantity());
	    this.setPurchasePrice(dto.getPurchasePrice());
	    this.setSubPurchasePrice(dto.getSubPurchasePrice());
	    this.setSalesPrice(dto.getSalesPrice());
	    this.setSubSalesPrice(dto.getSubSalesPrice());
	    this.setSubProfitPrice(dto.getSubProfitPrice());
	    this.setCreateTimeStamp(dto.getCreateTime().getTime());
	    this.setUpdateTimeStamp(dto.getUpdateTime().getTime());
	    this.setPopularize(dto.isPopularize());
	    this.setSkuPrice(dto.getSkuPrice());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}
	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
	public Long getGoodId() {
		return goodId;
	}
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public Long getGoodSkuId() {
		return goodSkuId;
	}
	public void setGoodSkuId(Long goodSkuId) {
		this.goodSkuId = goodSkuId;
	}
	public String getSkuPropertyName() {
		return skuPropertyName;
	}
	public void setSkuPropertyName(String skuPropertyName) {
		this.skuPropertyName = skuPropertyName;
	}
	public String getSkuPicturePath() {
		return skuPicturePath;
	}
	public void setSkuPicturePath(String skuPicturePath) {
		this.skuPicturePath = skuPicturePath;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Long purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Long getSubPurchasePrice() {
		return subPurchasePrice;
	}
	public void setSubPurchasePrice(Long subPurchasePrice) {
		this.subPurchasePrice = subPurchasePrice;
	}
	public Long getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Long getSubSalesPrice() {
		return subSalesPrice;
	}
	public void setSubSalesPrice(Long subSalesPrice) {
		this.subSalesPrice = subSalesPrice;
	}
	public Long getSubProfitPrice() {
		return subProfitPrice;
	}
	public void setSubProfitPrice(Long subProfitPrice) {
		this.subProfitPrice = subProfitPrice;
	}
	public Long getCreateTimeStamp() {
		return createTimeStamp;
	}
	public void setCreateTimeStamp(Long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	public Long getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	public void setUpdateTimeStamp(Long updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	public boolean isPopularize() {
		return isPopularize;
	}
	public void setPopularize(boolean isPopularize) {
		this.isPopularize = isPopularize;
	}
	public Long getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(Long skuPrice) {
		this.skuPrice = skuPrice;
		if (null != skuPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.skuPriceStr = df.format(skuPrice/100.0);
		}else{
			this.skuPriceStr = "0.00";
		}
	}
	public String getSkuPriceStr() {
		return skuPriceStr;
	}
	public void setSkuPriceStr(String skuPriceStr) {
		this.skuPriceStr = skuPriceStr;
	}
	
}
