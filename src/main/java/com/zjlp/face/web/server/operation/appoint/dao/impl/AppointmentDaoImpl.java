package com.zjlp.face.web.server.operation.appoint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.AppointmentMapper;
import com.zjlp.face.web.server.operation.appoint.dao.AppointmentDao;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;
@Repository
public class AppointmentDaoImpl implements AppointmentDao{
	@Autowired
	private SqlSession sqlSession;
	@Override
	public int deleteByPrimaryKey(Long id) {
		return sqlSession.getMapper(AppointmentMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(Appointment record) {
		sqlSession.getMapper(AppointmentMapper.class).insertSelective(record);
		return record.getId();
	}

	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"appointment"})
	public Appointment selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(AppointmentMapper.class).selectByPrimaryKey(id);
	}
	
	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"appointment:id"})
	public int updateAllById(Appointment record) {
		return sqlSession.getMapper(AppointmentMapper.class).updateByPrimaryKey(record);
	}
	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"appointment:id"})
	public int updateByPrimaryKeySelective(Appointment record) {
		return sqlSession.getMapper(AppointmentMapper.class).updateByPrimaryKeySelective(record);
	}
	@Override
	public Pagination<AppointmentDto> findAppointmentPage(Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo) {
		Integer count=sqlSession.getMapper(AppointmentMapper.class).getCount(appointmentVo);
		pagination.setTotalRow(count);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appointmentVo", appointmentVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		pagination.setDatas(sqlSession.getMapper(AppointmentMapper.class).findAppointmentPage(map));
		return pagination;
	}

	@Override
	public List<AppointmentDto> findAllAppointmentByShopNo(String shopNo) {
		return sqlSession.getMapper(AppointmentMapper.class).findAllAppointmentByShopNo(shopNo);
	}

}
