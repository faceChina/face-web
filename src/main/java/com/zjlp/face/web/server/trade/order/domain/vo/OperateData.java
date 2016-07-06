package com.zjlp.face.web.server.trade.order.domain.vo;

import java.io.Serializable;
import java.util.Date;

public class OperateData implements Serializable {

	private static final long serialVersionUID = -1063244414881086587L;
	public static final long L_ZERO = 0L;
	public static final int I_ZERO = 0;
	//时间戳  yyyy-MM-dd
	private String timeStamp;
	//时间
	private Date time;
	//价格
	private Long price;
	//数目
	private Integer count;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "OperateData [timeStamp=" + timeStamp + ", time=" + time
				+ ", price=" + price + ", count=" + count + "]";
	}
	
}
