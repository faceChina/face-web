package com.zjlp.face.web.server.trade.coupon.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.CouponException;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.producer.CouponProcucer;
import com.zjlp.face.web.server.trade.coupon.service.CouponService;

@Component
public class CouponProducerImpl implements CouponProcucer {
	
	@Autowired
	private CouponService couponService;

	@Override
	public Coupon matchBestCoupon(Long userId, String shopNo, Long orderPrice)
			throws CouponException {
		List<Coupon> list = couponService.findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(userId, shopNo, orderPrice);
		return (list != null && !list.isEmpty()) ? list.get(0) : null;
	}
	
	@Override
	public List<Coupon> findValidCouponsInThisShop(Long userId, String shopNo)
			throws CouponException {
		AssertUtil.notNull(userId, "userId不能为空");
		AssertUtil.hasLength(shopNo, "shopNo不能为空");
		return couponService.findValidCouponsInThisShop(userId, shopNo);
	}
}
