package com.zjlp.face.web.server.trade.order.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;

@Component
public class SalesOrderProducerImpl implements SalesOrderProducer {
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Override
	public SalesOrder getSalesOrderByOrderNo(String orderNo){
		return salesOrderService.getSalesOrderByOrderNo(orderNo);
	}

	@Override
	public List<OrderItem> getOrderItemListByOrderNo(String orderNo){
		return salesOrderService.getOrderItemListByOrderNo(orderNo);
	}
	
	@Override
	public void editSalesOrderStatus(String orderNo, Integer status){
		salesOrderService.editSalesOrderStatus(orderNo, status);
	}
	
	@Override
	public void editSalesOrder(SalesOrder salesOrder){
		salesOrder.setStatus(null);
		salesOrderService.editSalesOrder(salesOrder);
	}
	
	@Override
	public List<SalesOrder> findSalesOrderListByTSN(String tsn){
		return salesOrderService.findSalesOrderListByTSN(tsn);
	}

	@Override
	public Long getTotalIncome(String shopNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getYesterdayIncome(String shopNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getFreezeIncome(String shopNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUserFreezeAmount(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
