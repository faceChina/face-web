package com.zjlp.face.web.server.operation.appoint.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;

public interface AppointmentService {
	int deleteByPrimaryKey(Long id);

    Long insert(Appointment record);

    Appointment selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Appointment record);

	Pagination<AppointmentDto> findAppointmentPage(Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo);

	/**
	 * 根据店铺编号查询预约列表
	 * @Title: findAllAppointmentByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年4月17日 下午4:50:35  
	 * @author ah
	 */
	List<AppointmentDto> findAllAppointmentByShopNo(String shopNo);
	/**
	 * 修改预约信息,需传入预约的全部信息
	 * @param edit
	 */
	void updateAllById(Appointment edit);
	
}
