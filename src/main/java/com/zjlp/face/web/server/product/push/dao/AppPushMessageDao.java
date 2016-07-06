package com.zjlp.face.web.server.product.push.dao;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.product.push.domain.AppPushMessage;

public interface AppPushMessageDao {

	int add(AppPushMessage appPushMessage);

	List<AppPushMessage> findByUserId(AppPushMessage appPushMessage);

	void updateAppPushMessage(AppPushMessage appPushMessage);

	void removeAppPushMessage(Long id);

	AppPushMessage findReadMsgById(AppPushMessage appPushMessage);
	
	List<AppPushMessage> findAppPushMessage(AppPushMessage appPushMessage);

	void deleteMsgMonthAgo(Long userId, Date createTime);

}
