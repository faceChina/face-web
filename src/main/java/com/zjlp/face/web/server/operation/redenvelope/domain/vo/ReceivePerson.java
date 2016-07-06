package com.zjlp.face.web.server.operation.redenvelope.domain.vo;

import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;

public class ReceivePerson extends ReceiveRedenvelopeRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9204084385014694362L;

	public ReceivePerson(SendRedenvelopeRecord sendRedenvelopeRecord) {
		super(sendRedenvelopeRecord);
	}
	
	public ReceivePerson() {
		super();
	}

	//头像
	public String headUrl;
	//昵称
	public String nickname;
	//是否是最佳手气
	public boolean isBestLuck;

	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isBestLuck() {
		return isBestLuck;
	}

	public void setBestLuck(boolean isBestLuck) {
		this.isBestLuck = isBestLuck;
	}
	
	
}
