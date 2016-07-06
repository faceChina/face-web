package com.zjlp.face.web.server.trade.coupon.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.CouponSetMapper;
import com.zjlp.face.web.server.trade.coupon.dao.CouponSetDao;
import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

@Repository
public class CouponSetDaoImpl implements CouponSetDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addCouponSet(CouponSet couponSet) {
		sqlSession.getMapper(CouponSetMapper.class).insertSelective(couponSet);
		return couponSet.getId();
	}

	@Override
	public CouponSet getCouponSetById(Long id) {
		return sqlSession.getMapper(CouponSetMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public Integer countCouponSetByTimeStatus(Integer timeStatus, String shopNo) {
		return sqlSession.getMapper(CouponSetMapper.class).countCouponSetByTimeStatus(timeStatus, shopNo);
	}

	@Override
	public List<CouponSetDto> findCouponSetPage(
			Pagination<CouponSetDto> pagination, Integer timeStatus,
			String shopNo) {
		return sqlSession.getMapper(CouponSetMapper.class).findCouponSetPage(pagination.getStart(), pagination.getPageSize(), timeStatus, shopNo);
	}

	@Override
	public Integer countAllCouponSetByUserId(Long userId) {
		return sqlSession.getMapper(CouponSetMapper.class).countAllCouponSetByUserId(userId);
	}

	@Override
	public void editCouponSet(CouponSet couponSet) {
		sqlSession.getMapper(CouponSetMapper.class).updateByPrimaryKeySelective(couponSet);
	}

	@Override
	public void endCouponSet(CouponSet newCouponSet) {
		sqlSession.getMapper(CouponSetMapper.class).updateByPrimaryKeySelective(newCouponSet);
	}

	@Override
	public void deleteConponSet(CouponSet newCouponSet) {
		sqlSession.getMapper(CouponSetMapper.class).updateByPrimaryKeySelective(newCouponSet);
	}

	@Override
	public Integer countCouponSetForBuyer(String shopNo) {
		return sqlSession.getMapper(CouponSetMapper.class).countCouponSetForBuyer(shopNo);
	}

	@Override
	public List<CouponSetDto> findCouponSetForBuyer(
			Pagination<CouponSetDto> pagination, String shopNo, Long buyerUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("shopNo", shopNo);
		map.put("buyerUserId", buyerUserId);
		return sqlSession.getMapper(CouponSetMapper.class).findCouponSetForBuyer(map);
	}

	@Override
	public List<CouponSet> findShopAllValidCoupon(String shopNo) {
		return sqlSession.getMapper(CouponSetMapper.class).findShopAllValidCoupon(shopNo);
	}

	@Override
	public Integer countShopValidCouponSet(String shopNo) {
		return sqlSession.getMapper(CouponSetMapper.class).countShopValidCouponSet(shopNo);
	}

	@Override
	public List<CouponSetDto> findAllCouponSetForBuyer(String shopNo,
			Long userId) {
		return sqlSession.getMapper(CouponSetMapper.class).findAllCouponSetForBuyer(shopNo, userId);
	}
	
	
}
