package com.zjlp.face.web.server.trade.order.domain;

import java.io.Serializable;
import java.util.Date;

public class PurchaseOrder implements Serializable {
	private static final long serialVersionUID = -8376390639704880917L;
	//主键
	private Long id;
	//订单的交易编号
    private String orderNo;
    //供应商店铺号
    private String supplierNo;
    //供应商店铺昵称
    private String supplierNick;
    //供应商类型
    private Integer supplierType;
    //分店绑定id
    private Long subbranchId;
    //采购商手机号
    private String purchaserNo;
    //采购商昵称
    private String purchaserNick;
    //采购总数
    private Long totalPurchaseQuantity;
    //采购商采购总价 单位：分
    private Long totalPurchasePrice;
    //采购商的出货总价  单位：分
    private Long totalSalesPrice;
    //销售总利润/佣金 单位：分
    private Long totalProfitPrice;
    //采购商品主图
    private String picturePath;
    //合作方式（1分销 2推广）
    private Integer cooperationWay;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getSupplierNick() {
		return supplierNick;
	}
	public void setSupplierNick(String supplierNick) {
		this.supplierNick = supplierNick;
	}
	public Integer getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}
	public String getPurchaserNo() {
		return purchaserNo;
	}
	public void setPurchaserNo(String purchaserNo) {
		this.purchaserNo = purchaserNo;
	}
	public String getPurchaserNick() {
		return purchaserNick;
	}
	public void setPurchaserNick(String purchaserNick) {
		this.purchaserNick = purchaserNick;
	}
	public Long getTotalPurchaseQuantity() {
		return totalPurchaseQuantity;
	}
	public void setTotalPurchaseQuantity(Long totalPurchaseQuantity) {
		this.totalPurchaseQuantity = totalPurchaseQuantity;
	}
	public Long getTotalPurchasePrice() {
		return totalPurchasePrice;
	}
	public void setTotalPurchasePrice(Long totalPurchasePrice) {
		this.totalPurchasePrice = totalPurchasePrice;
	}
	public Long getTotalSalesPrice() {
		return totalSalesPrice;
	}
	public void setTotalSalesPrice(Long totalSalesPrice) {
		this.totalSalesPrice = totalSalesPrice;
	}
	public Long getTotalProfitPrice() {
		return totalProfitPrice;
	}
	public void setTotalProfitPrice(Long totalProfitPrice) {
		this.totalProfitPrice = totalProfitPrice;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Integer getCooperationWay() {
		return cooperationWay;
	}
	public void setCooperationWay(Integer cooperationWay) {
		this.cooperationWay = cooperationWay;
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
	public Long getSubbranchId() {
		return subbranchId;
	}
	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}
	@Override
	public String toString() {
		return new StringBuilder("PurchaseOrder [id=").append(id)
				.append(", orderNo=").append(orderNo).append(", supplierNo=")
				.append(supplierNo)
				.append(", supplierNick=").append(supplierNick).append(", supplierType=").append(supplierType)
				.append(", purchaserNo=")
				.append(purchaserNo).append(", purchaserNick=")
				.append(purchaserNick).append(", totalPurchaseQuantity=")
				.append(totalPurchaseQuantity).append(", totalPurchasePrice=")
				.append(totalPurchasePrice).append(", totalSalesPrice=")
				.append(totalSalesPrice).append(", totalProfitPrice=")
				.append(totalProfitPrice)
				.append(", picturePath=").append(picturePath)
				.append(", cooperationWay=")
				.append(cooperationWay).append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime).append("]").toString();
	}
}