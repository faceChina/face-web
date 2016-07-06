package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;

public interface AppointmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Appointment record);

    int insertSelective(Appointment record);

    Appointment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Appointment record);

    int updateByPrimaryKeyWithBLOBs(Appointment record);

    int updateByPrimaryKey(Appointment record);

	List<AppointmentDto> findAppointmentPage(Map<String, Object> map);

	Integer getCount(AppointmentVo appointmentVo);

	List<AppointmentDto> findAllAppointmentByShopNo(String shopNo);
}