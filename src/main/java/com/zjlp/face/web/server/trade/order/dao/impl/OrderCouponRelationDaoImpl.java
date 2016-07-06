package com.zjlp.face.web.server.trade.order.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.OrderCouponRelationMapper;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.order.dao.OrderCouponRelationDao;
import com.zjlp.face.web.server.trade.order.domain.OrderCouponRelation;

@Repository
public class OrderCouponRelationDaoImpl implements OrderCouponRelationDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(OrderCouponRelation orderCouponRelation) {
		sqlSession.getMapper(OrderCouponRelationMapper.class).insertSelective(orderCouponRelation);
	}

	@Override
	public Coupon getCouponByOrderNo(String orderNo) {
		return sqlSession.getMapper(OrderCouponRelationMapper.class).getCouponByOrderNo(orderNo);
	}

	@Override
	public void deleteByOrderNo(String orderNo) {
		sqlSession.getMapper(OrderCouponRelationMapper.class).deleteByOrderNo(orderNo);
	}
}
