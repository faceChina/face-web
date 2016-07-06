package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.order.domain.OrderPriceRecord;

public interface OrderPriceRecordMapper {

    int insertSelective(OrderPriceRecord record);
}