package com.zjlp.face.web.server.trade.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.order.dao.OrderCouponRelationDao;
import com.zjlp.face.web.server.trade.order.domain.OrderCouponRelation;
import com.zjlp.face.web.server.trade.order.service.OrderCouponRelationService;

@Service
public class OrderCouponRelationServiceImpl implements OrderCouponRelationService {
	
	@Autowired
	private OrderCouponRelationDao orderCouponRelationDao;
	
	@Override
	public void add(OrderCouponRelation orderCouponRelation) {
		orderCouponRelationDao.add(orderCouponRelation);
	}

	@Override
	public Coupon getCouponByOrderNo(String orderNo) {
		return orderCouponRelationDao.getCouponByOrderNo(orderNo);
	}

	@Override
	public void deleteByOrderNo(String orderNo) {
		orderCouponRelationDao.deleteByOrderNo(orderNo);
	}
}
