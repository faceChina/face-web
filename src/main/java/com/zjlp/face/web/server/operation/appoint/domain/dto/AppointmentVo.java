package com.zjlp.face.web.server.operation.appoint.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;

public class AppointmentVo extends Appointment {
	
	private List<DynamicForm> dynamicFormList;

	public List<DynamicForm> getDynamicFormList() {
		return dynamicFormList;
	}

	public void setDynamicFormList(List<DynamicForm> dynamicFormList) {
		this.dynamicFormList = dynamicFormList;
	}
	
}
