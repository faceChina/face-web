package com.zjlp.face.web.server.product.im.service;

import com.zjlp.face.web.server.product.im.domain.ImUser;
/**
 * 聊天用户服务
 * @ClassName: ImUserService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2014年10月9日 上午10:55:11
 */
public interface ImUserService {
	
	/**
	 * 新增聊天账户
	 * @Title: addImUser 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imUser
	 * @date 2014年10月9日 上午10:55:26  
	 * @author dzq
	 */
	void addImUser(ImUser imUser);
	
	/**
	 * 修改聊天账户
	 * @Title: editImUser 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imUser
	 * @date 2014年10月10日 上午11:31:24  
	 * @author dzq
	 */
	void editImUser(ImUser imUser);
	
	/**
	 * 根据主键获得用户
	 * @Title: getImUserById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2014年10月10日 上午11:27:52  
	 * @author dzq
	 */
	ImUser getImUserById(Long id);
	
	/**
	 * 根据外部用户主键获得聊天账户
	 * @Title: getImUserByRemoteId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param remoteId
	 * @param type
	 * @return
	 * @date 2014年10月10日 上午11:28:49  
	 * @author dzq
	 */
	ImUser getImUserByRemoteId(String remoteId,Integer type);
	
	/**
	 * 根据登陆账号获取聊天账户
	 * @Title: getImUserByUserName 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userName
	 * @return
	 * @date 2014年10月14日 下午2:29:27  
	 * @author Administrator
	 */
	ImUser getImUserByUserName(String userName);
	
	/**
	 * 根据登陆账号获取聊天账户
	 * @Title: getImUserByUserName 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userName
	 * @return
	 * @date 2014年10月14日 下午2:29:27  
	 * @author Administrator
	 */
	ImUser getImUserByUserName(String userName,Integer type);
	
}
