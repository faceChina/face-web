package com.zjlp.face.web.server.trade.order.domain.vo;


import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

public class SubBranchSalesOrderVo extends SalesOrder{
	private static final long serialVersionUID = -7121834012981049703L;
	//商品数量
	private Integer items;
	//销售采购单主键
	private Long purchaseID;
	//供应商店铺号
	private String supplierShopNo;
    //供应商店铺昵称
    private String supplierShopNick;
    //供应商类型 1.p 2.bf
    private Integer supplierShopType;
    //分店绑定id
    private Long subbranchId;
    //采购商（注：分销时为店铺号，推广时为手机号）
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
    //private String picturePath;
    //合作方式（1分销 2推广）
    private Integer cooperationWay;
    //支出佣金
    private Long payCommssion;
    //上级分店的利润
    private Long preLeveCommssion;
    //订单来源
    private String orderSource;
    
    private List<OrderItem> orderItems; 
    //检索关键字
    private String searchKey;
    
	public Integer getItems() {
		return items;
	}
	public void setItems(Integer items) {
		this.items = items;
	}
	public String getSupplierShopNo() {
		return supplierShopNo;
	}
	public void setSupplierShopNo(String supplierShopNo) {
		this.supplierShopNo = supplierShopNo;
	}
	public String getSupplierShopNick() {
		return supplierShopNick;
	}
	public void setSupplierShopNick(String supplierShopNick) {
		this.supplierShopNick = supplierShopNick;
	}
	public Integer getSupplierShopType() {
		return supplierShopType;
	}
	public void setSupplierShopType(Integer supplierShopType) {
		this.supplierShopType = supplierShopType;
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
	public Integer getCooperationWay() {
		return cooperationWay;
	}
	public void setCooperationWay(Integer cooperationWay) {
		this.cooperationWay = cooperationWay;
	}
	public Long getPurchaseID() {
		return purchaseID;
	}
	public void setPurchaseID(Long purchaseID) {
		this.purchaseID = purchaseID;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Long getPayCommssion() {
		return payCommssion;
	}
	public void setPayCommssion(Long payCommssion) {
		this.payCommssion = payCommssion;
	}
	public Long getPreLeveCommssion() {
		return preLeveCommssion;
	}
	public void setPreLeveCommssion(Long preLeveCommssion) {
		this.preLeveCommssion = preLeveCommssion;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	
	public Long getSubbranchId() {
		return subbranchId;
	}
	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}
	
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/*******************************
	 * @Title setPurchaseOrder
	 * @Description (统一设置 PurchaseOrder)
	 * @param purchaseOrder
	 * @Return void
	 * @date 2015年6月30日
	 * @author Xilei Huang
	 *******************************/
	public void setPurchaseOrder(PurchaseOrder purchaseOrder){
		if(purchaseOrder==null){return;}
		this.setPurchaseID(purchaseOrder.getId());
		this.setSupplierShopNo(purchaseOrder.getSupplierNo());
		this.setSupplierShopType(purchaseOrder.getSupplierType());
		this.setSupplierShopNick(purchaseOrder.getSupplierNick());
		this.setPurchaserNo(purchaseOrder.getPurchaserNo());
		this.setPurchaserNick(purchaseOrder.getPurchaserNick());
		this.setTotalPurchaseQuantity(purchaseOrder.getTotalPurchaseQuantity());
		this.setTotalPurchasePrice(purchaseOrder.getTotalPurchasePrice());
		this.setTotalSalesPrice(purchaseOrder.getTotalSalesPrice());
		this.setTotalProfitPrice(purchaseOrder.getTotalProfitPrice());
		this.setCooperationWay(purchaseOrder.getCooperationWay());
	}
	public void SetSalesOrder(SalesOrder salesOrder){
		if(salesOrder==null){return;}
		this.setAdjustPrice(salesOrder.getAdjustPrice());
		this.setAppointmentAddress(salesOrder.getAppointmentAddress());
		this.setAppointmentId(salesOrder.getAppointmentId());
		this.setAppointmentName(salesOrder.getAppointmentName());
		this.setAppointmentTime(salesOrder.getAppointmentTime());
		this.setBankCard(salesOrder.getBankCard());
		this.setBuyerAccount(salesOrder.getBuyerAccount());
		this.setBuyerMemo(salesOrder.getBuyerMemo());
		this.setBuyerMessage(salesOrder.getBuyerMessage());
		this.setBuyerNick(salesOrder.getBuyerNick());
		this.setCancelTime(salesOrder.getCancelTime());
		this.setClosingTime(salesOrder.getClosingTime());
		this.setConfirmTime(salesOrder.getConfirmTime());
		this.setCreateTime(salesOrder.getCreateTime());
		this.setDeleteTime(salesOrder.getDeleteTime());
		this.setDeliveryCompany(salesOrder.getDeliveryCompany());
		this.setDeliveryRange(salesOrder.getDeliveryRange());
		this.setDeliveryRemoteId(salesOrder.getDeliveryRemoteId());
		this.setDeliveryRemoteType(salesOrder.getDeliveryRemoteType());
		this.setDeliverySn(salesOrder.getDeliverySn());
		this.setDeliveryTime(salesOrder.getDeliveryTime());
		this.setDeliveryWay(salesOrder.getDeliveryWay());
		this.setDiscountPrice(salesOrder.getDiscountPrice());
		this.setFromInner(salesOrder.getFromInner());
		this.setHasPostFee(salesOrder.getHasPostFee());
		this.setIntegral(salesOrder.getIntegral());
		this.setCouponPrice(salesOrder.getCouponPrice());//TODO
		this.setObtainIntegral(salesOrder.getObtainIntegral());
		this.setOpenId(salesOrder.getOpenId());
		this.setOrderCategory(salesOrder.getOrderCategory());
		this.setOrderNo(salesOrder.getOrderNo());
		this.setOrderTime(salesOrder.getOrderTime());
		this.setPayChannel(salesOrder.getPayChannel());
		this.setPaymentTime(salesOrder.getPaymentTime());
		this.setPayPrice(salesOrder.getPayPrice());
		this.setPayStatus(salesOrder.getPayStatus());
		this.setPayType(salesOrder.getPayType());
		this.setPayWay(salesOrder.getPayWay());
		this.setPickUpAddress(salesOrder.getPickUpAddress());
		this.setPickUpPhone(salesOrder.getPickUpPhone());
		this.setPicturePath(salesOrder.getPicturePath());
		this.setPostFee(salesOrder.getPostFee());
		this.setPrice(salesOrder.getPrice());
		this.setPromoteSpend(salesOrder.getPromoteSpend());
		this.setQuantity(salesOrder.getQuantity());
		this.setRealIntegral(salesOrder.getRealIntegral());
		this.setReceiverAddress(salesOrder.getReceiverAddress());
		this.setReceiverCity(salesOrder.getReceiverCity());
		this.setReceiverDistrict(salesOrder.getReceiverDistrict());
		this.setReceiverName(salesOrder.getReceiverName());
		this.setReceiverPhone(salesOrder.getReceiverPhone());
		this.setReceiverState(salesOrder.getReceiverState());
		this.setRefuseReason(salesOrder.getRefuseReason());
		this.setRefuseTime(salesOrder.getRefuseTime());
		this.setSellerMemo(salesOrder.getSellerMemo());
		this.setSellerPhone(salesOrder.getSellerPhone());
		this.setSellerRemoteId(salesOrder.getSellerRemoteId());
		this.setSellerRemoteType(salesOrder.getSellerRemoteType());
		this.setSenderCell(salesOrder.getSenderCell());
		this.setSenderName(salesOrder.getSenderName());
		this.setShopName(salesOrder.getShopName());
		this.setShopNo(salesOrder.getShopNo());
		this.setSolrTime(salesOrder.getSolrTime());
		this.setStatus(salesOrder.getStatus());
		this.setTimeoutTime(salesOrder.getTimeoutTime());
		this.setTitle(salesOrder.getTitle());
		this.setTotalPrice(salesOrder.getTotalPrice());
		this.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
		this.setUpdateTime(salesOrder.getUpdateTime());
		this.setUserId(salesOrder.getUserId());
		this.setvAreaCode(salesOrder.getvAreaCode());
	}
	@Override
	public String toString() {
		return super.toString() + "  SubBranchSalesOrderVo [items=" + items + ", purchaseID="
				+ purchaseID + ", supplierShopNo=" + supplierShopNo
				+ ", supplierShopNick=" + supplierShopNick
				+ ", subbranchId=" + subbranchId
				+ ", supplierShopType=" + supplierShopType + ", purchaserNo="
				+ purchaserNo + ", purchaserNick=" + purchaserNick
				+ ", totalPurchaseQuantity=" + totalPurchaseQuantity
				+ ", totalPurchasePrice=" + totalPurchasePrice
				+ ", totalSalesPrice=" + totalSalesPrice
				+ ", totalProfitPrice=" + totalProfitPrice 
				+ ", preLeveCommssion=" + preLeveCommssion
				+ ", payCommssion=" + payCommssion
				+ ", orderSource=" + orderSource
				+ ", cooperationWay=" + cooperationWay + ", orderItems="
				+ orderItems + "]";
	}
	
	
}
