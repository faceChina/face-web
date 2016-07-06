package com.zjlp.face.web.server.user.security.producer;

public interface RoleProducer {

	/**
	 * 注册时添加普通用户权限
	 * @Title: addUserRoleRelation 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @throws SecurityException
	 * @date 2015年3月24日 上午11:35:14  
	 * @author fjx
	 */
	void addUserRoleRelation(Long userId) throws SecurityException;
	
	/**
	 * 商铺激活添加对应权限
	 * @Title: addShopRoleByUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param type
	 * @throws SecurityException
	 * @date 2015年3月24日 下午1:31:47  
	 * @author fjx
	 */
	void addShopRoleByUserId(Long userId,Integer type) throws SecurityException;
	
	
}
