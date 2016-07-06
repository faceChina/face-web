package com.zjlp.face.web.server.product.push.business;

import java.util.List;

import com.zjlp.face.web.server.product.push.domain.AppPushMessage;

public interface AppPushMessageBusiness {

	/**
	 * 新增一条push消息
	 * 
	 * @param appPushMessage
	 * @return
	 */
	int addAppPushMessage(AppPushMessage appPushMessage);

	/**
	 * 查询最近一周的PUSH,最近一周没有查询最近一个月的 通过用户ID查找推送消息,isRead=1是未读取，0是已读取
	 * 
	 * @param appPushMessage
	 * @return
	 */
	List<AppPushMessage> findAppPushMessageByUserId(AppPushMessage appPushMessage);

	/**
	 * 更新推送消息 ，主要更新是读取状态，即1置0
	 * 
	 * @param appPushMessage
	 */
	void updateAppPushMessage(AppPushMessage appPushMessage);

	/**
	 * 删除消息
	 * 
	 * @param id
	 */
	void removeAppPushMessage(Long id);

	/**
	 * 根据主键查找未读取消息
	 * 
	 * @param appPushMessage
	 */
	AppPushMessage findReadMsgById(AppPushMessage appPushMessage);
	
	List<AppPushMessage> findAppPushMessage(AppPushMessage appPushMessage);

}
