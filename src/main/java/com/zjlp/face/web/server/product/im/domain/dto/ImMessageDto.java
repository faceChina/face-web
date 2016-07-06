package com.zjlp.face.web.server.product.im.domain.dto;

import java.util.Date;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.server.product.im.domain.ImMessage;

public class ImMessageDto extends ImMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -518202332566806790L;
	//查询的时间戳
	private Long currentTime;
	
	//查询时间
	private Date currentDateTime;

	public Long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
		this.setCurrentDateTime(DateUtil.LongToDate(currentTime));
	}

	public Date getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(Date currentDateTime) {
		this.currentDateTime = currentDateTime;
	}

	
	
}
