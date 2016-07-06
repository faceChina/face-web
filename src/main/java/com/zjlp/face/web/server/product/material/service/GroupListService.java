package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.GroupList;

public interface GroupListService {

	/**
	 * 根据GroupId查询集合
	 * @Title: findByGroupId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param GroupId
	 * @return
	 * @date 2014年6月23日 下午5:10:12  
	 * @author Administrator
	 */
	List<GroupList> findByGroupId(Long groupId);

	/**
	 * 添加grouplist集合
	 * @Title: addGroupList 
	 * @Description: (添加grouplist集合) 
	 * @param groupList
	 * @return
	 * @date 2015年3月21日 下午4:36:29  
	 * @author ah
	 */
	Long addGroupList(List<GroupList> groupList);
	
}
