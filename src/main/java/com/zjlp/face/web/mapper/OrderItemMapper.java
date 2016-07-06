package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
	
	List<OrderItem> selectOrderItemListByOrderNo(String orderNo);
	
}