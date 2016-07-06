package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PropMapper;
import com.zjlp.face.web.server.product.good.dao.PropDao;
import com.zjlp.face.web.server.product.good.domain.Prop;

@Repository
public class PropDaoImpl implements PropDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(Prop prop) {
		sqlSession.getMapper(PropMapper.class).insertSelective(prop);
	}

	@Override
	public void edit(Prop prop) {
		sqlSession.getMapper(PropMapper.class).updateByPrimaryKeySelective(prop);
	}

	@Override
	public Prop getById(Long id) {
		return sqlSession.getMapper(PropMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(PropMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<Prop> findPropsByClassificationId(Long classificationId) {
		return sqlSession.getMapper(PropMapper.class).selectPropsByClassificationId(classificationId);
	}

	@Override
	public Integer hasSalesProp(Long classificationId) {
		return sqlSession.getMapper(PropMapper.class).hasSalesProp(classificationId);
	}

}
