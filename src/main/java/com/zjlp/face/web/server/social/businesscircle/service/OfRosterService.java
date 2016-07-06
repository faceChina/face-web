package com.zjlp.face.web.server.social.businesscircle.service;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;



public interface OfRosterService {
	/**
	 * 查询我的好友
	* @Title: queryRosterByUserName
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userName
	* @return
	* @return List<OfRoster>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月11日 上午11:50:58
	 */
	List<OfRoster> queryRosterByUserName(String userName,String excludeName);

}
