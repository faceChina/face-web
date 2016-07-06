package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;

public interface CouponMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

	Integer countHasReveivedCoupon(Long couponSetId);

	Integer countCouponByUserIdAndCouponSetId(@Param("couponSetId")Long couponSetId, @Param("userId")Long userId, @Param("status")Integer status);

	Integer countCouponByUserId(CouponDto couponDto);

	List<CouponDto> findCouponPage(Map<String, Object> map);

	List<Coupon> findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(
			@Param("userId")Long userId, @Param("shopNo")String shopNo, @Param("orderPrice")Long orderPrice);

	List<Coupon> findValidCouponsInThisShop(@Param("userId")Long userId, @Param("shopNo")String shopNo);

	Integer countUserAllCoupon(Long userId);

	void deleteCouponBatch(@Param("ids")String[] ids, @Param("userId")Long userId);

	Integer countUserReceiveNumber(@Param("couponSetId")Long couponSetId, @Param("userId")Long userId);

	void deleteAllCoupon(Long userId);
}