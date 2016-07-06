package com.zjlp.face.web.server.user.customer.dao;

import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppGroup;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:53:16
 *
 */
public interface AppGroupDao {

	Long add(AppGroup record);

	void removeById(Long id);

	void edit(AppGroup record);

	AppGroup getByPrimaryKey(Long id);
	
	List<AppGroup> getByUserId(Long userId, Integer type);

	AppGroup getByUserIdAndGroupName(AppGroup appGroup);

	void removeAppGroupByGroupId(Long groupId);

	Integer getMaxGroupSort(Long userId, Integer type);

	List<AppGroup> selectUngroups(Long userId, Integer type);

	void removeUngroups(Long userId);

}
