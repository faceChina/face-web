package com.zjlp.face.web.server.operation.appoint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.appoint.dao.AppointmentDao;
import com.zjlp.face.web.server.operation.appoint.dao.DynamicFormDao;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;
import com.zjlp.face.web.server.operation.appoint.service.AppointmentService;
@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	private AppointmentDao appointmentDao;
	
	@Autowired
	private DynamicFormDao dynamicFormDao;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return appointmentDao.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(Appointment record) {
		return appointmentDao.insert(record);
	}

	@Override
	public Appointment selectByPrimaryKey(Long id) {
		return appointmentDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Appointment record) {
		return appointmentDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Pagination<AppointmentDto> findAppointmentPage(Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo) {
		return appointmentDao.findAppointmentPage(pagination,appointmentVo);
	}

	@Override
	public List<AppointmentDto> findAllAppointmentByShopNo(String shopNo) {
		return appointmentDao.findAllAppointmentByShopNo(shopNo);
	}

	@Override
	public void updateAllById(Appointment edit) {
		appointmentDao.updateAllById(edit);
	}

}
