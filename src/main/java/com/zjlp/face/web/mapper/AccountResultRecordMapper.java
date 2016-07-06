package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.account.domain.AccountResultRecord;

public interface AccountResultRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountResultRecord record);

    int insertSelective(AccountResultRecord record);

    AccountResultRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountResultRecord record);

    int updateByPrimaryKey(AccountResultRecord record);
}