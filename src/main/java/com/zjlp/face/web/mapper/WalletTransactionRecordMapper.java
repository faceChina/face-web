package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;

public interface WalletTransactionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletTransactionRecord record);

    int insertSelective(WalletTransactionRecord record);

    WalletTransactionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WalletTransactionRecord record);

    int updateByPrimaryKey(WalletTransactionRecord record);
}