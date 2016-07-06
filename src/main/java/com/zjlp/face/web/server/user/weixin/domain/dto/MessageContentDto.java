package com.zjlp.face.web.server.user.weixin.domain.dto;

import com.zjlp.face.web.server.user.weixin.domain.MessageContent;

public class MessageContentDto extends MessageContent {

	private static final long serialVersionUID = 5008062404655182272L;
	//产品编号
	private String shopNo;
	//事件类型：1：关注时回复 2：消息时回复 3：关键词回复
    private Integer eventType;
    //回复模式：1：文本 2：单图文 3：多图文 
    private Integer recoveryMode;

    public String getShopNo() {
		return shopNo;
	}
	
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	public Integer getEventType() {
		return eventType;
	}
	
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	
	public Integer getRecoveryMode() {
		return recoveryMode;
	}
	
	public void setRecoveryMode(Integer recoveryMode) {
		this.recoveryMode = recoveryMode;
	}
}
