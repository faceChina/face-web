package com.zjlp.face.web.server.trade.coupon.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

public interface CouponService {

	Long addCouponSet(CouponSet couponSet);

	CouponSet getCouponSetById(Long id);

	Long reciveCoupon(Coupon coupon);

	Pagination<CouponSetDto> findCouponSet(Pagination<CouponSetDto> pagination,
			Integer timeStauts, String shopNo);

	Integer countCouponSetByTimeStatus(Integer timeStatus, String shopNo);

	Coupon getCouponById(Long id);

	void updateCoupon(Coupon newCoupon);

	Integer countAllCouponSetByUserId(Long userId);

	Integer countHasReveivedCoupon(Long couponSetId);

	void editCouponSet(CouponSet couponSet);

	void endCouponSet(CouponSet newCouponSet);

	void deleteConponSet(CouponSet newCouponSet);

	/**
	 * 
	 * @Title: countCouponByUserIdAndCouponSetId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param couponSetId
	 * @param userId
	 * @param status 不传就查询全部状态
	 * @return
	 * @date 2015年9月24日 下午3:49:12  
	 * @author cbc
	 */
	Integer countCouponByUserIdAndCouponSetId(Long couponSetId, Long userId, Integer status);

	Pagination<CouponDto> findCouponPage(Pagination<CouponDto> pagination,
			CouponDto couponDto);

	/**
	 * 1.获取匹配订单金额的所有优惠券<br>
	 * 2.排序: faceValue(desc), orderPrice(desc), endTime(asc)
	 * @Title: findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param shopNo
	 * @param orderPrice
	 * @return
	 * @date 2015年9月25日 上午9:28:55  
	 * @author cbc
	 */
	List<Coupon> findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(
			Long userId, String shopNo, Long orderPrice);

	Pagination<CouponSetDto> findCouponSetForBuyer(
			Pagination<CouponSetDto> pagination, String shopNo, Long buyerUserId);

	/**
	 * 买家删除优惠券
	 * @Title: deleteCoupon 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param newCoupon
	 * @date 2015年10月4日 上午11:48:39  
	 * @author cbc
	 */
	void deleteCoupon(Coupon newCoupon);

	List<Coupon> findValidCouponsInThisShop(Long userId, String shopNo);

	List<CouponSet> findShopAllValidCoupon(String shopNo);

	Integer countUserAllCoupon(Long userId);

	void deleteCouponBatch(String[] ids, Long userId);

	Integer countShopValidCouponSet(String shopNo);

	List<CouponSetDto> findAllCouponSetForBuyer(String shopNo, Long userId);

	Integer countUserReceiveNumber(Long couponSetId, Long userId);

	void deleteAllCoupon(Long userId);

}
