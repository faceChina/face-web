package com.zjlp.face.web.server.trade.coupon.dao;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;


public interface CouponDao {

	Long addCoupon(Coupon coupon);

	Coupon getCouponById(Long id);

	void updateCoupon(Coupon newCoupon);

	Integer countHasReveivedCoupon(Long couponSetId);

	Integer countCouponByUserIdAndCouponSetId(Long couponSetId, Long userId, Integer status);

	Integer countCouponByUserId(CouponDto couponDto);

	List<CouponDto> findCouponPage(Pagination<CouponDto> pagination,
			CouponDto couponDao);

	List<Coupon> findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(
			Long userId, String shopNo, Long orderPrice);

	void deleteCoupon(Coupon newCoupon);

	List<Coupon> findValidCouponsInThisShop(Long userId, String shopNo);

	Integer countUserAllCoupon(Long userId);

	void deleteCouponBatch(String[] ids, Long userId);

	Integer countUserReceiveNumber(Long couponSetId, Long userId);

	void deleteAllCoupon(Long userId);


}
