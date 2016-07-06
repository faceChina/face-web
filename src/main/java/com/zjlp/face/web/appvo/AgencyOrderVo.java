package com.zjlp.face.web.appvo;

import java.text.DecimalFormat;
import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;

public class AgencyOrderVo extends AssSalesOrderVo {
	
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 8468038882756627255L;
	
	//采购订单细项列表
	private List<AssOrderItem> orderItemList;
	//模糊查询条件
	private String condition;
	//采购订单id
	private Long purchaseOrderId;
	//采购者昵称
	private String purchaserNick;
	//采购者店铺编号
	private String purchaserNo;
	//供货商昵称
	private String supplierShopNick;
	//总利润/佣金
	private Long totalProfitPrice;
	private String totalProfitPriceStr;
	//总佣金
	private String totalCommissionStr;
	//供应商类型 1.p 2.bf
	private Integer supplierShopType;
    //采购总数
    private Long totalPurchaseQuantity;
    //采购商采购总价 单位：分
    private Long totalPurchasePrice;
    //采购商的出货总价  单位：分
    private Long totalSalesPrice;
    //上级分店的利润
    private Long preLeveCommssion;
    //上级分店的利润
    private String preLeveCommssionStr;
	
    /**
	 * 人工调整金额
	 */
	private String adjustPriceStr;
	/**
	 * 积分抵价
	 */
	private String integralStr; 
	/**
	 * 优惠券
	 */
	private Long couponPrice;
	
	private String couponPriceStr;
	
	public String getCouponPriceStr() {
		Long couponPrice = this.getCouponPrice();
		if (null != couponPrice) {
			DecimalFormat df = new DecimalFormat("##0.00");
			this.couponPriceStr = df.format(couponPrice/100.0);
		} else {
			this.couponPriceStr = "0.00";
		}
		return couponPriceStr;
	}

	
	public void setCouponPriceStr(String couponPriceStr) {
		this.couponPriceStr = couponPriceStr;
	}


	/**
	 * 用于标记是积分低价还是优惠券低价
	 */
	private String flag;
	
	public String getFlag(){
		if (!this.getIntegral().equals(0L)) {
			return "0";
		} else if (couponPrice != null) {
			this.setIntegral(couponPrice);
			return "1";
		} else {
			return "2";
		}
	}
	
	public Long getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(Long couponPrice) {
		this.couponPrice = couponPrice;
	}


	/**
	 * 推广支出
	 */
	private String promoteSpendStr;
	/**
	 * 快递费
	 */
	private String postFeeStr;
	/**
	 * 收入合计
	 */
	private String totalIncomeStr;
	
    //支出佣金
    private Long payCommssion;
    private String payCommssionStr;
	// 订单来源:成为分店时提交的用户名
	private String orderSource;

	public AgencyOrderVo() {
		super();
	}

	public AgencyOrderVo(SubBranchSalesOrderVo order) {
		super(order);
		this.purchaseOrderId = order.getPurchaseID();
		this.setPurchaserNick(order.getPurchaserNick());
		this.purchaserNo  = order.getPurchaserNo();
		this.setSupplierShopNick(order.getSupplierShopNick());
		this.setTotalProfitPrice(order.getTotalProfitPrice());
		this.supplierShopType = order.getSupplierShopType();
		this.setCreateTime(order.getOrderTime());
		this.totalPurchaseQuantity = order.getTotalPurchasePrice();
		this.totalPurchasePrice = order.getTotalPurchasePrice();
		this.totalSalesPrice = order.getTotalSalesPrice();
		this.payCommssion = order.getPayCommssion();
		this.setPreLeveCommssion(order.getPreLeveCommssion());
		this.orderSource = order.getOrderSource();
		this.couponPrice = order.getCouponPrice();
	}


	public List<AssOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<AssOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getPurchaserNick() {
		return purchaserNick;
	}

	public void setPurchaserNick(String purchaserNick) {
		if (null == purchaserNick){
			this.purchaserNick = "";
		}else{
			this.purchaserNick = purchaserNick;
		}
	}

	public String getPurchaserNo() {
		return purchaserNo;
	}

	public void setPurchaserNo(String purchaserNo) {
		this.purchaserNo = purchaserNo;
	}

	public String getSupplierShopNick() {
		return supplierShopNick;
	}

	public void setSupplierShopNick(String supplierShopNick) {
		if (null == supplierShopNick){
			this.supplierShopNick = "";
		}else{
			this.supplierShopNick = supplierShopNick;
		}
	}

	public Long getTotalProfitPrice() {
		return totalProfitPrice;
	}

	public void setTotalProfitPrice(Long totalProfitPrice) {
		this.totalProfitPrice = totalProfitPrice;
		if (null != totalProfitPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.totalProfitPriceStr = df.format(totalProfitPrice/100.0);
		}else{
			this.totalProfitPriceStr = "0.00";
		}
	}

	public String getTotalProfitPriceStr() {
		return totalProfitPriceStr;
	}

	public void setTotalProfitPriceStr(String totalProfitPriceStr) {
		this.totalProfitPriceStr = totalProfitPriceStr;
	}
	public String getTotalCommissionStr() {
		if (null == orderItemList || orderItemList.isEmpty()) {
			return null;
		}
		Long commission = 0L;
	/*	for (AssOrderItem item : orderItemList) {
			Long price = item.getSubProfitPrice();
			commission = CalculateUtils.getSum(commission, null == price ? 0L : price);
		}*/
		DecimalFormat df = new DecimalFormat("##0.00");
		this.totalCommissionStr = df.format(commission/100.0);
		return totalCommissionStr;
	}

	public Integer getSupplierShopType() {
		return supplierShopType;
	}

	public void setSupplierShopType(Integer supplierShopType) {
		this.supplierShopType = supplierShopType;
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
	
	public String getAdjustPriceStr() {
		Long adjustPrice = this.getAdjustPrice();
		if (null != adjustPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.adjustPriceStr = df.format(adjustPrice/100.0);
		}else{
			this.adjustPriceStr = "0.00";
		}
		return adjustPriceStr;
	}

	public String getIntegralStr() {
		Long integral = this.getIntegral();
		if (null != integral){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.integralStr = df.format(integral/100.0);
		}else{
			this.integralStr = "0.00";
		}
		return integralStr;
	}

	public String getPromoteSpendStr() {
		Long promoteSpend = this.getPromoteSpend();
		if (null != promoteSpend){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.promoteSpendStr = df.format(promoteSpend/100.0);
		}else{
			this.promoteSpendStr = "0.00";
		}
		return promoteSpendStr;
	}

	public String getPostFeeStr() {
		Long postFee = this.getPostFee();
		if (null != postFee){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.postFeeStr = df.format(postFee/100.0);
		}else{
			this.postFeeStr = "0.00";
		}
		return postFeeStr;
	}

	public String getTotalIncomeStr() {
		Long totalPrice  = this.getTotalPrice();
		Long promoteSpend = this.getPromoteSpend();
		Long totalIncome = 0L;
		if (null != totalPrice && null != promoteSpend) {
			totalIncome = totalPrice - promoteSpend;
		}
		DecimalFormat df = new DecimalFormat("##0.00");
		totalIncomeStr = df.format(totalIncome/100.0);
		return totalIncomeStr;
		
	}

	public Long getPayCommssion() {
		return payCommssion;
	}

	public void setPayCommssion(Long payCommssion) {
		this.payCommssion = payCommssion;
	}

	public String getPayCommssionStr() {
		Long payCommssion = this.getPayCommssion();
		if (null != payCommssion){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.payCommssionStr = df.format(payCommssion/100.0);
		}else{
			this.payCommssionStr = "0.00";
		}
		return payCommssionStr;
	}

	public void setPayCommssionStr(String payCommssionStr) {
		this.payCommssionStr = payCommssionStr;
	}

	public Long getPreLeveCommssion() {
		return preLeveCommssion;
	}

	public void setPreLeveCommssion(Long preLeveCommssion) {
		this.preLeveCommssion = preLeveCommssion;
		if (null != preLeveCommssion){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.preLeveCommssionStr = df.format(preLeveCommssion/100.0);
		}else{
			this.preLeveCommssionStr = "0.00";
		}
		
	}

	public String getPreLeveCommssionStr() {
		return preLeveCommssionStr;
	}

	public void setPreLeveCommssionStr(String preLeveCommssionStr) {
		this.preLeveCommssionStr = preLeveCommssionStr;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	
}
