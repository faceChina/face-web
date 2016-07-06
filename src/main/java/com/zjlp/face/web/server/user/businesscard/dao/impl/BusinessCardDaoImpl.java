package com.zjlp.face.web.server.user.businesscard.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.web.mapper.BusinessCardMapper;
import com.zjlp.face.web.server.user.businesscard.dao.BusinessCardDao;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;

@Repository("businessCardDao")
public class BusinessCardDaoImpl implements BusinessCardDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addBusinessCard(BusinessCard record) {
		sqlSession.getMapper(BusinessCardMapper.class).insertSelective(record);
		return record.getId();
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"businessCard_userId:userId","businessCard:id"})
	public void updateBusinessCard(BusinessCard businessCard) {
		sqlSession.getMapper(BusinessCardMapper.class).updateBusinessCard(businessCard);
	}

	@Override
	public BusinessCard getBusinessCardByUserId(Long userId) {
		return sqlSession.getMapper(BusinessCardMapper.class).getBusinessCardByUserId(userId);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"businessCard"})
	public BusinessCard getById(Long id) {
		return sqlSession.getMapper(BusinessCardMapper.class).selectByPrimaryKey(id);
	}

}
