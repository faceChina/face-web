package com.zjlp.face.web.server.trade.order.domain.vo;

import java.util.List;

public class OrdersCountDto {
	// 生成订单数
	private Integer genareteCount;
	// 待付款订单数
	private Integer waitCount;
	// 已付款订单数
	private Integer payCount;
	// 已发货订单数
	private Integer sendCount;
	// 已收货订单数
	private Integer receiveCount;
	// 交易成功订单数
	private Integer compileCount;
	// 已取消订单数
	private Integer cancelCount;
	// 已删除订单数
	private Integer deleteCount;
	// 退订订单数
	private Integer refundCount;
	// 超时关闭订单数
	private Integer closeCount;
	
	public OrdersCountDto(){
	}
	public OrdersCountDto(List<SingleStatuCountDto> dataList){
		this.setDataList(dataList);
	}
	public Integer getGenareteCount() {
		return nullToZero(genareteCount);
	}
	public void setGenareteCount(Integer genareteCount) {
		this.genareteCount = genareteCount;
	}
	public Integer getWaitCount() {
		return nullToZero(waitCount);
	}
	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}
	public Integer getPayCount() {
		return nullToZero(payCount);
	}
	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}
	public Integer getSendCount() {
		return nullToZero(sendCount);
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getReceiveCount() {
		return nullToZero(receiveCount);
	}
	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Integer getCompileCount() {
		return nullToZero(compileCount);
	}
	public void setCompileCount(Integer compileCount) {
		this.compileCount = compileCount;
	}
	public Integer getCancelCount() {
		return nullToZero(cancelCount);
	}
	public void setCancelCount(Integer cancelCount) {
		this.cancelCount = cancelCount;
	}
	public Integer getDeleteCount() {
		return nullToZero(deleteCount);
	}
	public void setDeleteCount(Integer deleteCount) {
		this.deleteCount = deleteCount;
	}
	public Integer getRefundCount() {
		return nullToZero(refundCount);
	}
	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}
	public Integer getCloseCount() {
		return nullToZero(closeCount);
	}
	public void setCloseCount(Integer closeCount) {
		this.closeCount = closeCount;
	}
	public Integer getTotalCount() {
		return nullToZero(genareteCount) 
				+ nullToZero(waitCount)
				+ nullToZero(payCount)
				+ nullToZero(sendCount)
				+ nullToZero(receiveCount)
				+ nullToZero(compileCount)
				+ nullToZero(cancelCount)
				+ nullToZero(deleteCount)
				+ nullToZero(refundCount)
				+ nullToZero(closeCount);
	}
	public void setDataList(List<SingleStatuCountDto> dataList) {
		if (null == dataList || dataList.isEmpty()) {
			return;
		}
		for (SingleStatuCountDto count : dataList) {
			if (null == count || null == count.getStates()) {
				continue;
			}
			//分记
			Integer states = count.getStates();
			Integer counts = count.getCount();
			if (0 == states.intValue()) {  //0生成
				this.setGenareteCount(counts);
			} else if (1 == states.intValue()) { //01、待付款
				this.setWaitCount(counts);
		    } else if (2 == states.intValue()) { //02.已付款
		    	this.setPayCount(counts);
		    } else if (3 == states.intValue()) { //03.已发货
		    	this.setSendCount(counts);
		    } else if (4 == states.intValue()) { //04.已收货
		    	this.setReceiveCount(counts);
		    } else if (5 == states.intValue()) { //05.交易成功
		    	this.setCompileCount(counts);
		    } else if (10 == states.intValue()) { //10.已取消
		    	this.setCancelCount(counts);
		    } else if (11 == states.intValue()) { //11.已删除
		    	this.setDeleteCount(counts);
		    } else if (20 == states.intValue()) { //20.退订
		    	this.setRefundCount(counts);
		    } else if (30 == states.intValue()) { //30.超时关闭
		    	this.setCloseCount(counts);
		    }
		}
	}
	private Integer nullToZero(Integer i) {
		if (null == i) {
			i = 0;
		}
		return i;
	}
}
