package com.zjlp.face.web.server.trade.order.domain.dto;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

public class SalesOrderVo extends SalesOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2061638322762176593L;

	private Date startTime;
	
	private Date endTime;
	
	private Integer timeStates;
	
	private List<OrderAppointmentDetail> appointmentDetailList;
	
	/**
	 * 开始预约时间
	 */
	private Date startAppointmentTime;

	/**
	 * 结束预约时间
	 */
	private Date endAppointmentTime;

	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public Integer getTimeStates(){
		return timeStates;
	}
	
	public void setTimeStates(Integer timeStates){
		this.timeStates = timeStates;
	}

	public Date getStartAppointmentTime() {
		return startAppointmentTime;
	}

	public void setStartAppointmentTime(Date startAppointmentTime) {
		this.startAppointmentTime = startAppointmentTime;
	}

	public Date getEndAppointmentTime() {
		return endAppointmentTime;
	}

	public void setEndAppointmentTime(Date endAppointmentTime) {
		this.endAppointmentTime = endAppointmentTime;
	}

	public List<OrderAppointmentDetail> getAppointmentDetailList() {
		return appointmentDetailList;
	}

	public void setAppointmentDetailList(List<OrderAppointmentDetail> appointmentDetailList) {
		this.appointmentDetailList = appointmentDetailList;
	}
	
}
