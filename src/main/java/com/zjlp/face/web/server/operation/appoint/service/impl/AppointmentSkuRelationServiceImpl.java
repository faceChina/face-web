package com.zjlp.face.web.server.operation.appoint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.appoint.dao.AppointmentSkuRelationDao;
import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;
import com.zjlp.face.web.server.operation.appoint.service.AppointmentSkuRelationService;
@Service
public class AppointmentSkuRelationServiceImpl implements AppointmentSkuRelationService {
	@Autowired
	private AppointmentSkuRelationDao appointmentSkuRelationDao;
	@Override
	public Long addAppointmentSKuRelation(AppointmentSkuRelation appointmentSkuRelation) {
		return appointmentSkuRelationDao.addAppointmentSKuRelation(appointmentSkuRelation);
	}

	@Override
	public List<AppointmentSkuRelation> findAppointmentSkuRelationList(Long appointmentId) {
		return appointmentSkuRelationDao.findAppointmentSkuRelationList(appointmentId);
	}

	@Override
	public void deleteAppointmentSkuRelationById(Long id) {
		appointmentSkuRelationDao.deleteAppointmentSkuRelationById(id);
	}

	@Override
	public void deleteAppointmentSkuRelationBySkuId(Long skuId) {
		appointmentSkuRelationDao.deleteAppointmentSkuRelationBySkuId(skuId);
	}
	
}
