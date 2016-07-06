package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;

public interface TemplateMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TemplateMessage record);

    int insertSelective(TemplateMessage record);

    TemplateMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TemplateMessage record);

    int updateByPrimaryKey(TemplateMessage record);

	TemplateMessage getTemplateMessage(TemplateMessage templateMessage);

	Integer getTemplateMessageCountByShopNo(String shopNo);
}