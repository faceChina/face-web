package com.zjlp.face.web.server.trade.order.service;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;

/**
 * @ClassName: com.jzwgj.server.trade.order.service.OrderItemService
 * @Description: 描述
 * @date: 2015年3月16日 下午12:04:52
 * @author: zyl
 */
public interface OrderItemService {
	
	void addOrderItem(OrderItem orderItem);
	
	void editOrderItem(OrderItem orderItem);
	
	OrderItem getOrderItemById(Long id);
	
	List<OrderItem> findOrderItemListByOrderNo(String orderNo);
	
}
