package com.zjlp.face.web.server.operation.subbranch.service;

import com.zjlp.face.web.server.operation.subbranch.domain.OweRecord;

public interface OweRecordService {

    Long insertSelective(OweRecord record);

    OweRecord selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(OweRecord record);
}
