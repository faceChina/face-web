package com.zjlp.face.web.server.trade.order.domain;

import java.io.Serializable;
import java.util.Date;

public class PurchaseOrderItem implements Serializable {
	private static final long serialVersionUID = -3112634841514936709L;
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
    private Date createTime;
    //修改时间
    private Date updateTime;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return new StringBuilder("PurchaseOrderItem [id=").append(id)
				.append(", purchaseOrderId=").append(purchaseOrderId)
				.append(", goodId=").append(goodId)
				.append(", goodName=").append(goodName).append(", goodSkuId=")
				.append(goodSkuId)
				.append(", skuPropertyName=").append(skuPropertyName)
				.append(", skuPicturePath=")
				.append(skuPicturePath).append(", quantity=").append(quantity)
				.append(", purchasePrice=")
				.append(purchasePrice).append(", subPurchasePrice=")
				.append(subPurchasePrice).append(", salesPrice=").append(salesPrice)
				.append(", subSalesPrice=")
				.append(subSalesPrice).append(", subProfitPrice=")
				.append(subProfitPrice).append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime).append("]")
				.toString();
	}
}