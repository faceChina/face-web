package com.zjlp.face.web.server.trade.order.domain.dto;

import java.text.DecimalFormat;
import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

public class SalesOrderDto extends SalesOrder {
	private static final long serialVersionUID = 8103618821317153542L;
	private List<OrderItem> orderItemList;
	
	private Integer payChannel;
	
	public String getPayChanel() {
		if (null != payChannel) {
			switch (payChannel) {
			case 1:
				return "银行卡付款";
			case 2:
				return "钱包付款";
			case 3:
				return "微信付款";
			case 4:
				return "会员卡付款";
			default:
				break;
			}
		}
		return null;
	}
	
	public String getPayChannelName() {
		if (null != getPayChannel()) {
			switch (getPayChannel()) {
			case 1:
				return "银行卡支付";
			case 2:
				return "余额支付";
			case 3:
				return "微信支付";
			case 4:
				return "会员卡支付";
			case 5:
				return "支付宝支付";
			default:
				break;
			}
		}
		return null;
	}
	public void setPayChanel(String payChanel) {
		this.payChannel = super.getPayChannel();
	}

	/**
	 * 预约订单相关属性
	 */
	private List<OrderAppointmentDetail> orderAppointmentDetails;
	public List<OrderItem> getOrderItemList(){
		return orderItemList;
	}
	
	public void setOrderItemList(List<OrderItem> orderItemList){
		this.orderItemList = orderItemList;
	}

	public List<OrderAppointmentDetail> getOrderAppointmentDetails() {
		return orderAppointmentDetails;
	}

	public void setOrderAppointmentDetails(List<OrderAppointmentDetail> orderAppointmentDetails) {
		this.orderAppointmentDetails = orderAppointmentDetails;
	}

	public Integer getOrderItemSize(){
		if(null != orderItemList){
			return orderItemList.size();
		}
		return null;
	}

	public Integer getDetailSize() {
		if (orderAppointmentDetails != null) {
			return orderAppointmentDetails.size();
		}
		return null;
	}
	
	public String getTotalPriceStr() {
		if (super.getTotalPrice() != null) {
			DecimalFormat df = new DecimalFormat("##0.00");
			return df.format(super.getTotalPrice()/100.0);
		}
		return null;
	}
	
	public Boolean getIsSubbranchOrder() {
		if (null != super.getOrderCategory() && 4 == super.getOrderCategory()) {
			return true;
		} 
		return false;
	}
}
