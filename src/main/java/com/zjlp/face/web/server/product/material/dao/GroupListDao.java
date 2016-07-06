package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.GroupList;

public interface GroupListDao {
	
	/**
	 * 根据GroupId查询集合
	 * @Title: findByGroupId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param groupId
	 * @return
	 * @date 2014年6月23日 下午5:01:48  
	 * @author Administrator
	 */
	List<GroupList> findByGroupId(Long groupId);

	/**
	 * 查询最大的GroupId
	 * @Title: findMaxGroupId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2014年6月23日 下午5:02:25  
	 * @author Administrator
	 */
	Long findMaxGroupId();
	
	/**
	 * 添加
	 * @Title: addGroupList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param groupList
	 * @date 2014年6月23日 下午5:03:11  
	 * @author Administrator
	 */
	void addGroupList(GroupList groupList);

	/**
	 * 查询分组列表
	 * @Title: findGroupList 
	 * @Description: (查询分组列表) 
	 * @param groupList
	 * @return
	 * @date 2014年8月14日 下午9:46:28  
	 * @author ah
	 */
	List<GroupList> findGroupList(GroupList groupList);

	/**
	 * 根据GroupId删除分组
	 * @Title: deleteGroupListByGroupId 
	 * @Description: (根据GroupId删除分组) 
	 * @param groupList
	 * @date 2014年8月15日 上午11:49:32  
	 * @author ah
	 */
	void deleteGroupListByGroupId(GroupList groupList);

}
