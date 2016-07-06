package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.payment.domain.PaymentMemo;

public interface PaymentMemoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentMemo record);

    int insertSelective(PaymentMemo record);

    PaymentMemo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentMemo record);

    int updateByPrimaryKey(PaymentMemo record);
}