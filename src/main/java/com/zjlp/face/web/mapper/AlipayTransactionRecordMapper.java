package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;

public interface AlipayTransactionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AlipayTransactionRecord record);

    int insertSelective(AlipayTransactionRecord record);

    AlipayTransactionRecord selectByPrimaryKey(Long id);
    
    AlipayTransactionRecord selectByTransactionSerialNumber(String transactionSerialNumber);

    int updateByPrimaryKeySelective(AlipayTransactionRecord record);

    int updateByPrimaryKey(AlipayTransactionRecord record);
}