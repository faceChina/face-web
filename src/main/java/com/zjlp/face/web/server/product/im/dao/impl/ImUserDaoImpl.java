package com.zjlp.face.web.server.product.im.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ImUserMapper;
import com.zjlp.face.web.server.product.im.dao.ImUserDao;
import com.zjlp.face.web.server.product.im.domain.ImUser;
@Repository
public class ImUserDaoImpl implements ImUserDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(ImUser imUser) {
		sqlSession.getMapper(ImUserMapper.class).insertSelective(imUser);
		
	}

	@Override
	public void edit(ImUser imUser) {
		sqlSession.getMapper(ImUserMapper.class).updateByPrimaryKeySelective(imUser);
		
	}
	
	@Override
	public void deleteById(Long id) {
		sqlSession.getMapper(ImUserMapper.class).deleteByPrimaryKey(id);
		
	}

	@Override
	public ImUser getById(Long id) {
		return sqlSession.getMapper(ImUserMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public ImUser getByRemoteId(String remoteId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("remoteId", remoteId);
		map.put("type", type);
		return sqlSession.getMapper(ImUserMapper.class).selectByRemoteId(map);
	}

	@Override
	public ImUser getByUserName(String userName) {
		return sqlSession.getMapper(ImUserMapper.class).selectByUserName(userName);
	}

	@Override
	public ImUser getByUserName(String userName, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("type", type);
		return sqlSession.getMapper(ImUserMapper.class).selectByUserNameAndType(map);
	}

}
