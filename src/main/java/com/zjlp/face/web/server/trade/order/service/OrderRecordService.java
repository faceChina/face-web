package com.zjlp.face.web.server.trade.order.service;

import com.zjlp.face.web.server.trade.order.domain.OrderOperateRecord;

public interface OrderRecordService {
	
	void addOrderRecord(String orderNo, Long userId);
	
	void deliveryOrderRecord(String orderNo, Long userId);
	
	void deleteOrderRecord(String orderNo, Long userId);
	
	void receiveOrderRecord(String orderNo, Long userId);
	
	void cancleOrderRecord(String orderNo, Long userId);

	void saveOrderOperateRecord(OrderOperateRecord operateRecord);
	
	void adjustPriceRecord(String orderNo, int type,Long totalPrice, Long adjustPrice, Long userId);
	
	void successOrderRecord (String orderNo, Long userId);
	
}
