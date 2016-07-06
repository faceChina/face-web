package com.zjlp.face.web.server.trade.order.service;

import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.order.domain.OrderCouponRelation;


public interface OrderCouponRelationService {
	
	void add(OrderCouponRelation orderCouponRelation);
	
	/**
	 * 根据订单号查询优惠劵
	 * @param orderNo	订单号
	 * @return
	 */
	Coupon getCouponByOrderNo(String orderNo);
	
	/**
	 * 根据订单号删除关联关系
	 * @param orderNo	订单号
	 */
	void deleteByOrderNo(String orderNo);
}
