package com.zjlp.face.web.server.trade.coupon.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.coupon.dao.CouponDao;
import com.zjlp.face.web.server.trade.coupon.dao.CouponSetDao;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;
import com.zjlp.face.web.server.trade.coupon.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponSetDao couponSetDao;
	@Autowired
	private CouponDao couponDao;

	@Override
	public Long addCouponSet(CouponSet couponSet) {
		if (couponSet.getFaceValueType() == null) {
			couponSet.setFaceValueType(CouponSetDto.FACE_VALUE_TYPE_REGULAR);
		}
		if (couponSet.getCirculationType() == null) {
			couponSet.setCirculationType(CouponSetDto.CIRCULATION_TYPE_NUMBER);
		}
		if (couponSet.getCanJoin() == null) {
			couponSet.setCanJoin(CouponSetDto.CAN_NOT_JOIN);
		}
		if (couponSet.getStatus() == null) {
			couponSet.setStatus(CouponSetDto.STATUS_NOT_DELETE);
		}
		
		couponSet.setEffectiveTime(DateUtil.getZeroPoint(couponSet.getEffectiveTime()));//取生效日期的00:00:00
		couponSet.setEndTime(DateUtil.getEndPoint(couponSet.getEndTime()));//取失效日期的23:59:59
		
		Date date = new Date();
		couponSet.setCreateTime(date);
		couponSet.setUpdateTime(date);
		return couponSetDao.addCouponSet(couponSet);
	}

	@Override
	public CouponSet getCouponSetById(Long id) {
		return couponSetDao.getCouponSetById(id);
	}

	@Override
	public Long reciveCoupon(Coupon coupon) {
		if (coupon.getStatus() == null) {
			coupon.setStatus(CouponDto.STATUS_UNUSED);
		}
		if (coupon.getDrawRemoteType() == CouponDto.DRAW_TYPE_SHOP) {
			coupon.setDrawRemoteId(coupon.getShopNo());
		}
		Date date = new Date();
		coupon.setCreateTime(date);
		coupon.setUpdateTime(date);
		return couponDao.addCoupon(coupon);
	}

	@Override
	public Pagination<CouponSetDto> findCouponSet(
			Pagination<CouponSetDto> pagination, Integer timeStauts, String shopNo) {
		Integer totalRow = couponSetDao.countCouponSetByTimeStatus(timeStauts, shopNo);
		List<CouponSetDto> datas = couponSetDao.findCouponSetPage(pagination, timeStauts, shopNo);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public Integer countCouponSetByTimeStatus(Integer timeStatus, String shopNo) {
		return couponSetDao.countCouponSetByTimeStatus(timeStatus, shopNo);
	}

	@Override
	public Coupon getCouponById(Long id) {
		return couponDao.getCouponById(id);
	}

	@Override
	public void updateCoupon(Coupon newCoupon) {
		newCoupon.setUpdateTime(new Date());
		couponDao.updateCoupon(newCoupon);
	}

	@Override
	public Integer countAllCouponSetByUserId(Long userId) {
		return couponSetDao.countAllCouponSetByUserId(userId);
	}

	@Override
	public Integer countHasReveivedCoupon(Long couponSetId) {
		return couponDao.countHasReveivedCoupon(couponSetId);
	}

	@Override
	public void editCouponSet(CouponSet couponSet) {
		couponSet.setUpdateTime(new Date());
		couponSetDao.editCouponSet(couponSet);
	}

	@Override
	public void endCouponSet(CouponSet newCouponSet) {
		newCouponSet.setUpdateTime(new Date());
		couponSetDao.endCouponSet(newCouponSet);
	}

	@Override
	public void deleteConponSet(CouponSet newCouponSet) {
		newCouponSet.setUpdateTime(new Date());
		couponSetDao.deleteConponSet(newCouponSet);
	}

	@Override
	public Integer countCouponByUserIdAndCouponSetId(Long couponSetId,
			Long userId, Integer status) {
		return couponDao.countCouponByUserIdAndCouponSetId(couponSetId, userId, status);
	}

	@Override
	public Pagination<CouponDto> findCouponPage(
			Pagination<CouponDto> pagination, CouponDto couponDto) {
		Integer tatolRow = couponDao.countCouponByUserId(couponDto);
		List<CouponDto> datas = couponDao.findCouponPage(pagination, couponDto);
		pagination.setTotalRow(tatolRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public List<Coupon> findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(
			Long userId, String shopNo, Long orderPrice) {
		return couponDao.findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(userId, shopNo, orderPrice);
	}

	@Override
	public Pagination<CouponSetDto> findCouponSetForBuyer(
			Pagination<CouponSetDto> pagination, String shopNo, Long buyerUserId) {
		Integer totalRow = couponSetDao.countCouponSetForBuyer(shopNo);
		List<CouponSetDto> list = couponSetDao.findCouponSetForBuyer(pagination, shopNo, buyerUserId);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public void deleteCoupon(Coupon newCoupon) {
		couponDao.deleteCoupon(newCoupon);
	}

	@Override
	public List<Coupon> findValidCouponsInThisShop(Long userId, String shopNo) {
		return couponDao.findValidCouponsInThisShop(userId, shopNo);
	}

	@Override
	public List<CouponSet> findShopAllValidCoupon(String shopNo) {
		return couponSetDao.findShopAllValidCoupon(shopNo);
	}

	@Override
	public Integer countUserAllCoupon(Long userId) {
		return couponDao.countUserAllCoupon(userId);
	}

	@Override
	public void deleteCouponBatch(String[] ids, Long userId) {
		couponDao.deleteCouponBatch(ids, userId);
	}

	@Override
	public Integer countShopValidCouponSet(String shopNo) {
		return couponSetDao.countShopValidCouponSet(shopNo);
	}

	@Override
	public List<CouponSetDto> findAllCouponSetForBuyer(String shopNo,
			Long userId) {
		return couponSetDao.findAllCouponSetForBuyer(shopNo, userId);
	}

	@Override
	public Integer countUserReceiveNumber(Long couponSetId, Long userId) {
		return couponDao.countUserReceiveNumber(couponSetId, userId);
	}

	@Override
	public void deleteAllCoupon(Long userId) {
		couponDao.deleteAllCoupon(userId);
	}
	
	
}
