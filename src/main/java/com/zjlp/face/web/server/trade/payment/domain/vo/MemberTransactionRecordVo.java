package com.zjlp.face.web.server.trade.payment.domain.vo;

import java.io.Serializable;

import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

public class MemberTransactionRecordVo implements Serializable {

	private static final long serialVersionUID = -5840932044918538364L;
	//交易记录
	private MemberTransactionRecordDto transactionRecord;
	//消费内容
	private String consumContent;
	public MemberTransactionRecord getTransactionRecord() {
		return transactionRecord;
	}
	public void setTransactionRecord(MemberTransactionRecordDto transactionRecord) {
		this.transactionRecord = transactionRecord;
	}
	public String getConsumContent() {
		return consumContent;
	}
	public void setConsumContent(String consumContent) {
		this.consumContent = consumContent;
	}
	
}
