package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PropValueMapper;
import com.zjlp.face.web.server.product.good.dao.PropValueDao;
import com.zjlp.face.web.server.product.good.domain.PropValue;
@Repository
public class PropValueDaoImpl implements PropValueDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(PropValue propValue) {
		sqlSession.getMapper(PropValueMapper.class).insertSelective(propValue);
	}

	@Override
	public void edit(PropValue propValue) {
		sqlSession.getMapper(PropValueMapper.class).updateByPrimaryKeySelective(propValue);
	}

	@Override
	public PropValue getById(Long id) {
		return sqlSession.getMapper(PropValueMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(PropValueMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<PropValue> findPropValuesByPropId(Long propId) {
		return sqlSession.getMapper(PropValueMapper.class).selectPropValuesByPropId(propId);
	}

}
