package com.zjlp.face.web.server.user.weixin.dao;

import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;

public interface TemplateMessageDao {

	
	TemplateMessage getTemplateMessage(TemplateMessage templateMessage);

	void addTemplateMessage(TemplateMessage templateMessage);

	void editTemplateMessage(TemplateMessage templateMessage);

	Integer getTemplateMessageCountByShopNo(String shopNo);

}
