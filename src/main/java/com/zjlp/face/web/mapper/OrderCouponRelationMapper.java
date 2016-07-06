package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.order.domain.OrderCouponRelation;

public interface OrderCouponRelationMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(OrderCouponRelation record);

    int insertSelective(OrderCouponRelation record);

    OrderCouponRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderCouponRelation record);

    int updateByPrimaryKey(OrderCouponRelation record);

	Coupon getCouponByOrderNo(String orderNo);

	void deleteByOrderNo(String orderNo);
}