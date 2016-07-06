package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.trade.order.domain.OrderOperateRecord;

public interface OrderOperateRecordMapper {

    int insertSelective(OrderOperateRecord record);
}