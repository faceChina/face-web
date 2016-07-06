package com.zjlp.face.web.appvo;

import java.text.DecimalFormat;
import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

public class ShopOrderVo extends AssSalesOrderVo {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 768017886877235753L;

	private List<AssOrderItem> orderItemList;
	
	/**
	 * 人工调整金额
	 */
	private String adjustPriceStr;
	/**
	 * 积分抵价
	 */
	private String integralStr; 
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
	
	//采购者昵称
	private String purchaserNick;

	public ShopOrderVo() {
		super();
	}

	public ShopOrderVo(SalesOrder order) {
		super(order);
	}

	public List<AssOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<AssOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
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
}
