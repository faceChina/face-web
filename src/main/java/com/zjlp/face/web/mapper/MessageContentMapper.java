package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.weixin.domain.MessageContent;

public interface MessageContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageContent record);

    int insertSelective(MessageContent record);

    MessageContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageContent record);

    int updateByPrimaryKeyWithBLOBs(MessageContent record);

    int updateByPrimaryKey(MessageContent record);
    
    List<MessageContent> selectByMessageBodyId(Long messageBodyId);

	void deleteByMessageBodyId(Long messageBodyId);

	void updateMessageContent(MessageContent messageContent);
}