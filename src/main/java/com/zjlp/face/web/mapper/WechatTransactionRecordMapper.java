package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;

public interface WechatTransactionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatTransactionRecord record);

    int insertSelective(WechatTransactionRecord record);

    WechatTransactionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatTransactionRecord record);

    int updateByPrimaryKey(WechatTransactionRecord record);
    
    WechatTransactionRecord selectByTSN(String record);
}