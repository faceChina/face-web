package com.zjlp.face.web.server.trade.payment.domain.dto;

import java.io.Serializable;

public class FeeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3348741648495149918L;

	//支付手续费
	private Long payFee=0L;
	//平台手续费
	private Long platformFee=0L;
	
	public Long getPayFee() {
		return payFee;
	}
	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}
	public Long getPlatformFee() {
		return platformFee;
	}
	public void setPlatformFee(Long platformFee) {
		this.platformFee = platformFee;
	}
	
}
