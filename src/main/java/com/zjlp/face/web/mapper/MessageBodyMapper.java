package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.weixin.domain.MessageBody;

public interface MessageBodyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageBody record);

    int insertSelective(MessageBody record);

    MessageBody selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageBody record);

    int updateByPrimaryKeyWithBLOBs(MessageBody record);

    int updateByPrimaryKey(MessageBody record);
}