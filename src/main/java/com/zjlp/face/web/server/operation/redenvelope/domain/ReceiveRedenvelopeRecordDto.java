package com.zjlp.face.web.server.operation.redenvelope.domain;


public class ReceiveRedenvelopeRecordDto extends ReceiveRedenvelopeRecord {

	private static final long serialVersionUID = 1423939673782411829L;
	
	private String userName;
	private String headImgUrl;
	private long reciveTime;

	public ReceiveRedenvelopeRecordDto(){}
	
	public ReceiveRedenvelopeRecordDto(Long redenvelopeId, Long amount){
		super.setRedenvelopeId(redenvelopeId);
		super.setAmount(amount);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public long getReciveTime() {
		return reciveTime;
	}
	public void setReciveTime(long reciveTime) {
		this.reciveTime = reciveTime;
	}
}
