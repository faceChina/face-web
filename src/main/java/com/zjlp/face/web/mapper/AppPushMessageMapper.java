package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.push.domain.AppPushMessage;

public interface AppPushMessageMapper {

	int insert(AppPushMessage appPushMessage);

	List<AppPushMessage> selectByUserId(Map<String, Object> map);

	void update(AppPushMessage appPushMessage);

	void deleteByPrimarykey(Long id);

	AppPushMessage findReadMsgById(Map<String, Object> map);

	List<AppPushMessage> selectByIdAndMsgType(Map<String, Object> map);

	void deleteMsgMonthAgo(Map<String, Object> map);
}
