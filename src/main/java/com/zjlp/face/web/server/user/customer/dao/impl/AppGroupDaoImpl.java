package com.zjlp.face.web.server.user.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppGroupMapper;
import com.zjlp.face.web.server.user.customer.dao.AppGroupDao;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:53:19
 *
 */
@Repository("appGroupDao")
public class AppGroupDaoImpl implements AppGroupDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public Long add(AppGroup record) {
		this.sqlSession.getMapper(AppGroupMapper.class).insert(record);
		return record.getId();
	}

	@Override
	public void removeById(Long id) {
		this.sqlSession.getMapper(AppGroupMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void edit(AppGroup record) {
		this.sqlSession.getMapper(AppGroupMapper.class).updateByPrimaryKey(record);
	}

	@Override
	public AppGroup getByPrimaryKey(Long id) {
		return this.sqlSession.getMapper(AppGroupMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<AppGroup> getByUserId(Long userId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		return this.sqlSession.getMapper(AppGroupMapper.class).selectByUserId(map);
	}

	@Override
	public AppGroup getByUserIdAndGroupName(AppGroup appGroup) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", appGroup.getUserId());
		map.put("name", appGroup.getName());
		map.put("type", appGroup.getType());
		return this.sqlSession.getMapper(AppGroupMapper.class).selectByUserIdAndGroupName(map);
	}

	@Override
	public void removeAppGroupByGroupId(Long groupId) {
		this.sqlSession.getMapper(AppGroupMapper.class).deleteByPrimaryKey(groupId);
	}

	@Override
	public Integer getMaxGroupSort(Long userId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		return this.sqlSession.getMapper(AppGroupMapper.class).getMaxGroupSort(map);
	}

	@Override
	public List<AppGroup> selectUngroups(Long userId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		return this.sqlSession.getMapper(AppGroupMapper.class).selectUngroups(map);
	}

	@Override
	public void removeUngroups(Long userId) {
		this.sqlSession.getMapper(AppGroupMapper.class).deleteUngroupByUserId(userId);
	}
}
