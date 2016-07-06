package com.zjlp.face.web.server.user.weixin.dao;

import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;

public interface TemplateMessageSettingDao {

	void addTemplateMessageSetting(TemplateMessageSetting templateMessageSetting);

	void editTemplateMessageSetting(
			TemplateMessageSetting templateMessageSetting);

	TemplateMessageSetting getTemplateMessageSettingByShopNo(String shopNo);

}
