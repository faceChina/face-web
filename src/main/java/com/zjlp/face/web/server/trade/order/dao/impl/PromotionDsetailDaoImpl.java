package com.zjlp.face.web.server.trade.order.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PromotionDsetailMapper;
import com.zjlp.face.web.server.trade.order.dao.PromotionDsetailDao;
import com.zjlp.face.web.server.trade.order.domain.PromotionDsetail;
@Repository
public class PromotionDsetailDaoImpl implements PromotionDsetailDao{
	
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(PromotionDsetail promotionDsetail) {
		sqlSession.getMapper(PromotionDsetailMapper.class).insertSelective(promotionDsetail);
	}

	@Override
	public List<PromotionDsetail> getByOrderNo(String orderNo) {
		return sqlSession.getMapper(PromotionDsetailMapper.class).selectByOrderNo(orderNo);
	}

}
