package com.zjlp.face.web.server.product.im.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ImGroupMapper;
import com.zjlp.face.web.server.product.im.dao.ImGroupDao;
import com.zjlp.face.web.server.product.im.domain.ImGroup;
@Repository
public class ImGroupDaoImpl implements ImGroupDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(ImGroup imGroup) {
		sqlSession.getMapper(ImGroupMapper.class).insertSelective(imGroup);
	}

	@Override
	public void edit(ImGroup imGroup) {
		sqlSession.getMapper(ImGroupMapper.class).updateByPrimaryKeySelective(imGroup);
	}

	@Override
	public void deleteById(Long id) {
		sqlSession.getMapper(ImGroupMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByUserId(Long userId) {
		sqlSession.getMapper(ImGroupMapper.class).deleteByUserId(userId);
	}

	@Override
	public ImGroup getById(Long id) {
		return sqlSession.getMapper(ImGroupMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<ImGroup> findList(ImGroup imGroup) {
		return sqlSession.getMapper(ImGroupMapper.class).selectList(imGroup);
	}

}
