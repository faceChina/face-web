package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.operation.subbranch.domain.OweRecord;

public interface OweRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OweRecord record);

    int insertSelective(OweRecord record);

    OweRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OweRecord record);

    int updateByPrimaryKey(OweRecord record);
}