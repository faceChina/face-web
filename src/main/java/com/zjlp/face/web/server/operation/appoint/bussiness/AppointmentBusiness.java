package com.zjlp.face.web.server.operation.appoint.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;

public interface AppointmentBusiness {

	Pagination<AppointmentDto> findAppointmentPage(Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo);

	void switchStatus(Long id, Integer status);

	void removeAppointmentById(Long id);

	Long addAppointment(AppointmentVo appointmentVo, Long userId);

	Appointment getAppointmentById(Long id);

	void editAppointment(AppointmentVo appointmentVo, Long userId);
	
	Long addAppointmentSKuRelation(Long appointmentId,Long goodId);
	
	List<AppointmentSkuRelation> findAppointmentSkuRelationList(Long appointmentId);
	
	void deleteAppointmentSkuRelationById(Long id);
	
	void deleteAppointmentSkuRelationBySkuId(Long skuId);

	void saveGood(List<String> goodIdList, Long id);

	/**
	 * 根据店铺编号查询所有预约
	 * @Title: findAllAppointmentByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年4月17日 下午4:44:40  
	 * @author ah
	 */
	List<AppointmentDto> findAllAppointmentByShopNo(String shopNo);

	List<DynamicForm> findDynamicFormByAppointmentId(Long id);
}
