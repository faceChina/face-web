package com.zjlp.face.web.server.trade.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.order.dao.OrderItemDao;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	@Override
	public void addOrderItem(OrderItem orderItem){
		orderItemDao.addOrderItem(orderItem);
	}
	
	@Override
	public void editOrderItem(OrderItem orderItem){
		orderItemDao.editOrderItem(orderItem);
	}
	
	@Override
	public OrderItem getOrderItemById(Long id){
		return orderItemDao.getOrderItemById(id);
	}

	@Override
	public List<OrderItem> findOrderItemListByOrderNo(String orderNo){
		return orderItemDao.findOrderItemListByOrderNo(orderNo);
	}

}
