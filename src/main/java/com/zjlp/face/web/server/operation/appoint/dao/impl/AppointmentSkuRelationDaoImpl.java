package com.zjlp.face.web.server.operation.appoint.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppointmentSkuRelationMapper;
import com.zjlp.face.web.server.operation.appoint.dao.AppointmentSkuRelationDao;
import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;
@Repository
public class AppointmentSkuRelationDaoImpl implements AppointmentSkuRelationDao {
	@Autowired
	private SqlSession sqlSession;
	@Override
	public Long addAppointmentSKuRelation(AppointmentSkuRelation appointmentSkuRelation) {
		sqlSession.getMapper(AppointmentSkuRelationMapper.class).insertSelective(appointmentSkuRelation);
		return appointmentSkuRelation.getId();
	}

	@Override
	public List<AppointmentSkuRelation> findAppointmentSkuRelationList(Long appointmentId) {
		return sqlSession.getMapper(AppointmentSkuRelationMapper.class).findAppointmentSkuRelationList(appointmentId);
	}

	@Override
	public void deleteAppointmentSkuRelationById(Long id) {
		sqlSession.getMapper(AppointmentSkuRelationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void deleteAppointmentSkuRelationBySkuId(Long skuId) {
		sqlSession.getMapper(AppointmentSkuRelationMapper.class).deleteBySkuId(skuId);
	}

}
