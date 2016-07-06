package com.zjlp.face.web.server.trade.order.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.mapper.OrderAppointmentDetailMapper;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.dao.OrderAppointmentDetailDao;
@Repository
public class OrderAppointmentDetailDaoImpl implements OrderAppointmentDetailDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertSelective(OrderAppointmentDetail record) {
		sqlSession.getMapper(OrderAppointmentDetailMapper.class).insertSelective(record);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<OrderAppointmentDetail> findOrderAppointmentDetailListByOrderNo(String orderNo) {
		return sqlSession.getMapper(OrderAppointmentDetailMapper.class).findOrderAppointmentDetailListByOrderNo(orderNo);
	}
	
}
