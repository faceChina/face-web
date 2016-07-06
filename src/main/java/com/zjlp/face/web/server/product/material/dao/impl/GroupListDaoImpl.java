package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.GroupListMapper;
import com.zjlp.face.web.server.product.material.dao.GroupListDao;
import com.zjlp.face.web.server.product.material.domain.GroupList;
@Repository("groupListDao")
public class GroupListDaoImpl implements GroupListDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<GroupList> findByGroupId(Long groupId) {
		return sqlSession.getMapper(GroupListMapper.class).selectByGroupId(groupId);
	}

	@Override
	public Long findMaxGroupId() {
		return sqlSession.getMapper(GroupListMapper.class).selectMaxGroupId();
	}

	@Override
	public void addGroupList(GroupList groupList) {
		sqlSession.getMapper(GroupListMapper.class).insertSelective(groupList);
	}

	@Override
	public List<GroupList> findGroupList(GroupList groupList) {
		return sqlSession.getMapper(GroupListMapper.class).selectList(groupList);
	}

	@Override
	public void deleteGroupListByGroupId(GroupList groupList) {
		sqlSession.getMapper(GroupListMapper.class).deleteByGroupId(groupList);
	}


}
