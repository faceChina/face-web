package com.zjlp.face.web.server.operation.member.domain.dto;


import java.util.Date;

import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;

public class SendIntegralRecodeDto extends SendIntegralRecode {

	private static final long serialVersionUID = -8052679681501238503L;

	private Integer oldStatus;
	
	//辅助分页信息等
	private Aide aide = new Aide();
	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}

	public SendIntegralRecodeDto() {
		super();
	}
	public SendIntegralRecodeDto(Long sellerId, Long userId) {
		super(sellerId, userId, null);
	}
	public SendIntegralRecodeDto(Long sellerId, Long userId, Long integral) {
		super(sellerId, userId, integral);
	}
	public Integer getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	public static Date changeTime(SendIntegralRecode record, Integer changeStatus){
		Date date = new Date();
		if (GETED.equals(changeStatus)) {
			record.setClaimTime(date);
		} else if (CANCEL.equals(changeStatus)) {
			//TODO
		}
		return date;
	}
	
	public static boolean validCgStatus(Integer oldStatus, Integer newStatus) {
		if (null == oldStatus || !UN_GET.equals(oldStatus)) {
			return false;
		}
		if (null == newStatus || UN_GET.equals(newStatus)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
