package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;

public interface ToolSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ToolSetting record);

    int insertSelective(ToolSetting record);

    ToolSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ToolSetting record);

    int updateByPrimaryKey(ToolSetting record);

    ToolSetting getByShopNo(String shopNo);
}