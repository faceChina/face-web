package com.zjlp.face.web.server.trade.order.dao;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;


public interface OrderItemDao {
	
	void addOrderItem(OrderItem orderItem);
	
	void editOrderItem(OrderItem orderItem);
	
	OrderItem getOrderItemById(Long id);
	
	List<OrderItem> findOrderItemListByOrderNo(String orderNo);
	
}
