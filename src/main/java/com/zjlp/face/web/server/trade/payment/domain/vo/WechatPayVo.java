package com.zjlp.face.web.server.trade.payment.domain.vo;

import java.io.Serializable;

public class WechatPayVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String transactionSerialNumber;
	
	private String prepayId;

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	
}
