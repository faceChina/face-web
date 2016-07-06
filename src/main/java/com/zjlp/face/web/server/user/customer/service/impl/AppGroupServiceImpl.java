package com.zjlp.face.web.server.user.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.exception.ext.AppGroupException;
import com.zjlp.face.web.server.user.customer.dao.AppGroupDao;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;
import com.zjlp.face.web.server.user.customer.service.AppGroupService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午6:11:01
 *
 */
@Service
public class AppGroupServiceImpl implements AppGroupService {

	@Autowired
	private AppGroupDao appGroupDao;

	@Override
	public Long addAppGroup(AppGroup appGroup) {
		return this.appGroupDao.add(appGroup);
	}

	@Override
	public void editAppGroup(AppGroup appGroup) {
		this.appGroupDao.edit(appGroup);
	}

	@Override
	public void removeAppGroup(Long appGroupId) throws AppGroupException {
		this.appGroupDao.removeById(appGroupId);
	}

	@Override
	public AppGroup findAppGroupById(Long id) throws AppGroupException {
		return this.appGroupDao.getByPrimaryKey(id);
	}

	@Override
	public List<AppGroup> findAppGroupListByUserId(Long userId, Integer type) throws AppGroupException {
		return this.appGroupDao.getByUserId(userId, type);
	}

	@Override
	public AppGroup findAppGroupByUserIdAndGroupName(AppGroup appGroup) throws AppGroupException {
		return this.appGroupDao.getByUserIdAndGroupName(appGroup);
	}

	@Override
	public void removeAppGroupByGroupId(Long groupId) {
		this.appGroupDao.removeAppGroupByGroupId(groupId);
	}

	@Override
	public Integer getMaxGroupSort(Long userId, Integer type) {
		return this.appGroupDao.getMaxGroupSort(userId, type);
	}

	@Override
	public List<AppGroup> getUngroups(Long userId, Integer type) {
		return this.appGroupDao.selectUngroups(userId, type);
	}

	@Override
	public void removeUngroups(Long userId) {
		this.appGroupDao.removeUngroups(userId);

	}

}
