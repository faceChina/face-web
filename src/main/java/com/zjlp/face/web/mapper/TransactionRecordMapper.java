package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;

public interface TransactionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransactionRecord record);

    int insertSelective(TransactionRecord record);

    TransactionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransactionRecord record);

    int updateByPrimaryKey(TransactionRecord record);
    
    TransactionRecord selectOneByTSN(String transactionSerialNumber);
}