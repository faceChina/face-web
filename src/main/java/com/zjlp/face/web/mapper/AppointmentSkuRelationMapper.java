package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;

public interface AppointmentSkuRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppointmentSkuRelation record);

    int insertSelective(AppointmentSkuRelation record);

    AppointmentSkuRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppointmentSkuRelation record);

    int updateByPrimaryKey(AppointmentSkuRelation record);

	List<AppointmentSkuRelation> findAppointmentSkuRelationList(Long appointmentId);

	void deleteBySkuId(Long skuId);
}