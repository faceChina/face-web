package com.zjlp.face.web.server.operation.appoint.service;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;

public interface AppointmentSkuRelationService {
	Long addAppointmentSKuRelation(AppointmentSkuRelation appointmentSkuRelation);
	
	List<AppointmentSkuRelation> findAppointmentSkuRelationList(Long appointmentId);
	
	void deleteAppointmentSkuRelationById(Long id);
	
	void deleteAppointmentSkuRelationBySkuId(Long skuId);
}
