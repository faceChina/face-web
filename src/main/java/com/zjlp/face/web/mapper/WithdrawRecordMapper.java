package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.account.domain.WithdrawRecord;

public interface WithdrawRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WithdrawRecord record);

    int insertSelective(WithdrawRecord record);

    WithdrawRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WithdrawRecord record);

    int updateByPrimaryKey(WithdrawRecord record);
}