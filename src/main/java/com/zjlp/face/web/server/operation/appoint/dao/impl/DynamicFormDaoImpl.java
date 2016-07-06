package com.zjlp.face.web.server.operation.appoint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.DynamicFormMapper;
import com.zjlp.face.web.server.operation.appoint.dao.DynamicFormDao;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;
@Repository
public class DynamicFormDaoImpl implements DynamicFormDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<DynamicForm> findDynamicFormByRemoteIdAndCode(Long remoteId, Integer remoteCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("remoteId", remoteId);
		map.put("remoteCode", remoteCode);
		return sqlSession.getMapper(DynamicFormMapper.class).findDynamicFormList(map);
	}

	@Override
	public void insert(DynamicForm dynamicForm) {
		sqlSession.getMapper(DynamicFormMapper.class).insertSelective(dynamicForm);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(DynamicFormMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void update(DynamicForm dynamicForm) {
		sqlSession.getMapper(DynamicFormMapper.class).updateByPrimaryKeySelective(dynamicForm);
	}

}
