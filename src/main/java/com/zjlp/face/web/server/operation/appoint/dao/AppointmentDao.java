package com.zjlp.face.web.server.operation.appoint.dao;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;

public interface AppointmentDao {
	int deleteByPrimaryKey(Long id);

    Long insert(Appointment record);

    Appointment selectByPrimaryKey(Long id);

    int updateAllById(Appointment record);
    
    int updateByPrimaryKeySelective(Appointment record);

	Pagination<AppointmentDto> findAppointmentPage(Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo);

	List<AppointmentDto> findAllAppointmentByShopNo(String shopNo);
}
