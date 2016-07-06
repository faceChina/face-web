package com.zjlp.face.web.server.trade.coupon.dao;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

public interface CouponSetDao {

	Long addCouponSet(CouponSet couponSet);

	CouponSet getCouponSetById(Long id);

	Integer countCouponSetByTimeStatus(Integer timeStauts, String shopNo);

	List<CouponSetDto> findCouponSetPage(Pagination<CouponSetDto> pagination,
			Integer timeStauts, String shopNo);

	Integer countAllCouponSetByUserId(Long userId);

	void editCouponSet(CouponSet couponSet);

	void endCouponSet(CouponSet newCouponSet);

	void deleteConponSet(CouponSet newCouponSet);

	Integer countCouponSetForBuyer(String shopNo);

	List<CouponSetDto> findCouponSetForBuyer(
			Pagination<CouponSetDto> pagination, String shopNo, Long buyerUserId);

	List<CouponSet> findShopAllValidCoupon(String shopNo);

	Integer countShopValidCouponSet(String shopNo);

	List<CouponSetDto> findAllCouponSetForBuyer(String shopNo, Long userId);

}
