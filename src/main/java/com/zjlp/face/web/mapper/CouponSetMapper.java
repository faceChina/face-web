package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

public interface CouponSetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CouponSet record);

    int insertSelective(CouponSet record);

    CouponSet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponSet record);

    int updateByPrimaryKey(CouponSet record);

	Integer countCouponSetByTimeStatus(@Param("timeStatus") Integer timeStatus, @Param("shopNo") String shopNo);

	List<CouponSetDto> findCouponSetPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize,
			@Param("timeStauts") Integer timeStauts, @Param("shopNo") String shopNo);

	Integer countAllCouponSetByUserId(Long userId);

	Integer countCouponSetForBuyer(String shopNo);

	List<CouponSetDto> findCouponSetForBuyer(Map<String, Object> map);

	List<CouponSet> findShopAllValidCoupon(String shopNo);

	Integer countShopValidCouponSet(String shopNo);

	List<CouponSetDto> findAllCouponSetForBuyer(@Param("shopNo")String shopNo, @Param("userId")Long userId);
}