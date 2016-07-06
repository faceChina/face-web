package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;

public interface TemplateMessageSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TemplateMessageSetting record);

    int insertSelective(TemplateMessageSetting record);

    TemplateMessageSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TemplateMessageSetting record);

    int updateByPrimaryKey(TemplateMessageSetting record);

	TemplateMessageSetting getTemplateMessageSettingByShopNo(String shopNo);
}