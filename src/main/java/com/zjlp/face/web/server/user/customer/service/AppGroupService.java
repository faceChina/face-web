package com.zjlp.face.web.server.user.customer.service;

import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppGroup;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:53:58
 *
 */
public interface AppGroupService {

	/**
	 * @Title: addAppGroup
	 * @Description: (添加一个分组)
	 * @param appGroup
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:55:40
	 */
	Long addAppGroup(AppGroup appGroup);

	/**
	 * @Title: editAppGroup
	 * @Description: (编辑一个分组)
	 * @param appGroup
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:55:54
	 */
	void editAppGroup(AppGroup appGroup);

	/**
	 * @Title: findAppGroupById
	 * @Description: (查询分组列表)
	 * @param id
	 * @return
	 * @return AppGroup 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:56:06
	 */
	AppGroup findAppGroupById(Long id);

	/**
	 * @Title: findAppGroupListByUserId
	 * @Description: (查询分组列表)
	 * @param userId
	 * @param type
	 * @return
	 * @return List<AppGroup> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:56:16
	 */
	List<AppGroup> findAppGroupListByUserId(Long userId, Integer type);

	/**
	 * @Title: removeAppGroup
	 * @Description: (删除分组)
	 * @param appGroupId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:56:26
	 */
	void removeAppGroup(Long appGroupId);

	/**
	 * @Title: findAppGroupByUserIdAndGroupName
	 * @Description: (检查一个一个用户下分组是否重复)
	 * @param appGroup
	 * @return
	 * @return AppGroup 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:56:43
	 */
	AppGroup findAppGroupByUserIdAndGroupName(AppGroup appGroup);

	/**
	 * @Title: removeAppGroupByGroupId
	 * @Description: (通过主键移除分组)
	 * @param groupId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月10日 下午5:57:00
	 */
	void removeAppGroupByGroupId(Long groupId);

	/**
	 * @Title: getMaxGroupSort
	 * @Description: (查询用户的分组小优先级排序)
	 * @param userId
	 * @param type
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午2:30:11
	 */
	Integer getMaxGroupSort(Long userId, Integer type);
	
	/**
	 * @Title: getUngroups
	 * @Description: (查找未命名分组)
	 * @param userId
	 * @param type
	 * @return
	 * @return List<AppGroup> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月27日 下午4:38:02
	 */
	List<AppGroup> getUngroups(Long userId, Integer type);

	/**
	 * @Title: removeUngroups
	 * @Description: (删除用户下未分组)
	 * @param userId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月27日 下午5:10:12
	 */
	void removeUngroups(Long userId);

}
