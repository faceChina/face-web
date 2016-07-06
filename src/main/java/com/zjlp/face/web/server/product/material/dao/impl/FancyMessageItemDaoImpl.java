package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.web.mapper.FancyMessageItemMapper;
import com.zjlp.face.web.server.product.material.dao.FancyMessageItemDao;
import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;
@Repository
public class FancyMessageItemDaoImpl implements FancyMessageItemDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long save(FancyMessageItem fancyMessageItem) {
		 sqlSession.getMapper(FancyMessageItemMapper.class).insert(fancyMessageItem);
		 return fancyMessageItem.getId();
	}

	@Override
	public List<FancyMessageItem> selectByFancyMessageId(Long fancyMessageId) {
		return  sqlSession.getMapper(FancyMessageItemMapper.class).selectByFancyMessageId(fancyMessageId);
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"fancyMessageItem:id"})
	public void update(FancyMessageItem fancyMessageItem) {
		sqlSession.getMapper(FancyMessageItemMapper.class).updateByPrimaryKeySelective(fancyMessageItem);
	}

	@Override
	public int deleteByFancyMessageId(Long fancyMessageId) {
		return sqlSession.getMapper(FancyMessageItemMapper.class).deleteByFancyMessageId(fancyMessageId);
	}

	@Override
	public void removeByFancyMessageId(Long fancyMessageId) {
		sqlSession.getMapper(FancyMessageItemMapper.class).removeByFancyMessageId(fancyMessageId);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,key={"fancyMessageItem"})
	public FancyMessageItem selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(FancyMessageItemMapper.class).selectByPrimaryKey(id);
	}
}
