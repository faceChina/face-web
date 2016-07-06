package com.zjlp.face.web.server.trade.coupon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.CouponMapper;
import com.zjlp.face.web.server.trade.coupon.dao.CouponDao;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponDto;

@Repository
public class CouponDaoImpl implements CouponDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long addCoupon(Coupon coupon) {
		sqlSession.getMapper(CouponMapper.class).insertSelective(coupon);
		return coupon.getId();
	}

	@Override
	public Coupon getCouponById(Long id) {
		return sqlSession.getMapper(CouponMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void updateCoupon(Coupon newCoupon) {
		sqlSession.getMapper(CouponMapper.class).updateByPrimaryKeySelective(newCoupon);
	}

	@Override
	public Integer countHasReveivedCoupon(Long couponSetId) {
		return sqlSession.getMapper(CouponMapper.class).countHasReveivedCoupon(couponSetId);
	}

	@Override
	public Integer countCouponByUserIdAndCouponSetId(Long couponSetId,
			Long userId, Integer status) {
		return sqlSession.getMapper(CouponMapper.class).countCouponByUserIdAndCouponSetId(couponSetId, userId, status);
	}

	@Override
	public Integer countCouponByUserId(CouponDto couponDto) {
		return sqlSession.getMapper(CouponMapper.class).countCouponByUserId(couponDto);
	}

	@Override
	public List<CouponDto> findCouponPage(Pagination<CouponDto> pagination,
			CouponDto couponDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("couponDto", couponDto);
		return sqlSession.getMapper(CouponMapper.class).findCouponPage(map);
	}

	@Override
	public List<Coupon> findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(
			Long userId, String shopNo, Long orderPrice) {
		return sqlSession.getMapper(CouponMapper.class).findAllCouponWhenValidByUserIdAndShopNoAndOrderPrice(userId, shopNo, orderPrice);
	}

	@Override
	public void deleteCoupon(Coupon newCoupon) {
		sqlSession.getMapper(CouponMapper.class).updateByPrimaryKeySelective(newCoupon);
	}

	@Override
	public List<Coupon> findValidCouponsInThisShop(Long userId, String shopNo) {
		return sqlSession.getMapper(CouponMapper.class).findValidCouponsInThisShop(userId, shopNo);
	}

	@Override
	public Integer countUserAllCoupon(Long userId) {
		return sqlSession.getMapper(CouponMapper.class).countUserAllCoupon(userId);
	}

	@Override
	public void deleteCouponBatch(String[] ids, Long userId) {
		sqlSession.getMapper(CouponMapper.class).deleteCouponBatch(ids, userId);
	}

	@Override
	public Integer countUserReceiveNumber(Long couponSetId, Long userId) {
		return sqlSession.getMapper(CouponMapper.class).countUserReceiveNumber(couponSetId, userId);
	}

	@Override
	public void deleteAllCoupon(Long userId) {
		sqlSession.getMapper(CouponMapper.class).deleteAllCoupon(userId);
	}
	
	
}
