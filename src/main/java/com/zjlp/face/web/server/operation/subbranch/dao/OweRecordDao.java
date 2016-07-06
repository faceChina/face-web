package com.zjlp.face.web.server.operation.subbranch.dao;

import com.zjlp.face.web.server.operation.subbranch.domain.OweRecord;

public interface OweRecordDao {
	int deleteByPrimaryKey(Long id);

    Long insertSelective(OweRecord record);

    OweRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OweRecord record);

}
