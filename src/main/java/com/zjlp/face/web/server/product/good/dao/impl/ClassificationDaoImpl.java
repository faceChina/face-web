package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ClassificationMapper;
import com.zjlp.face.web.server.product.good.dao.ClassificationDao;
import com.zjlp.face.web.server.product.good.domain.Classification;
@Repository
public class ClassificationDaoImpl implements ClassificationDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(Classification classification) {
		sqlSession.getMapper(ClassificationMapper.class).insertSelective(classification);
	}

	@Override
	public void edit(Classification classification) {
		sqlSession.getMapper(ClassificationMapper.class).updateByPrimaryKeySelective(classification);
	}

	public Classification getById(Long id) {
		return sqlSession.getMapper(ClassificationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(ClassificationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<Classification> findClassificationByPid(Long pid,Integer level) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("level", level);
		return sqlSession.getMapper(ClassificationMapper.class).selectByPid(pid);
	}

}
