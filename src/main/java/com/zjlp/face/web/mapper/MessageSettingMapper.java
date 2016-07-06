package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;

public interface MessageSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageSetting record);

    int insertSelective(MessageSetting record);

    MessageSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageSetting record);

    int updateByPrimaryKey(MessageSetting record);
    
    List<MessageSettingDto> selectList(Map<String, Object> map);

	Integer getCount(MessageSettingDto dto);

	void updateMessageSetting(MessageSetting messageSetting);

	MessageSetting getMessageSetting(MessageSetting messageSetting);

	void editMessageSettingStatus(MessageSetting messageSetting);
}