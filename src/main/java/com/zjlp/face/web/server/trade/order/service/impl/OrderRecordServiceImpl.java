package com.zjlp.face.web.server.trade.order.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.order.dao.OrderOperateRecordDao;
import com.zjlp.face.web.server.trade.order.dao.OrderPriceRecordDao;
import com.zjlp.face.web.server.trade.order.domain.OrderOperateRecord;
import com.zjlp.face.web.server.trade.order.domain.OrderPriceRecord;
import com.zjlp.face.web.server.trade.order.service.OrderRecordService;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {
	
	@Autowired
	private OrderOperateRecordDao orderOperateRecordDao;
	@Autowired
	private OrderPriceRecordDao orderPriceRecordDao;
	@Override
	public void addOrderRecord(String orderNo, Long userId){
		OrderOperateRecord orderOperateRecord=new OrderOperateRecord();
		orderOperateRecord.setOrderNo(orderNo);
		orderOperateRecord.setUserId(userId);
		orderOperateRecord.setOperationType(1);
		orderOperateRecord.setOperationDesc("新增订单");
		orderOperateRecord.setCreateTime(new Date());
		orderOperateRecordDao.addOrderOperateRecord(orderOperateRecord);
	}
	@Override
	public void deliveryOrderRecord(String orderNo, Long userId){
		OrderOperateRecord orderOperateRecord=new OrderOperateRecord();
		orderOperateRecord.setOrderNo(orderNo);
		orderOperateRecord.setUserId(userId);
		orderOperateRecord.setOperationType(3);
		orderOperateRecord.setOperationDesc("发货");
		orderOperateRecord.setCreateTime(new Date());
		orderOperateRecordDao.addOrderOperateRecord(orderOperateRecord);
	}
	@Override
	public void deleteOrderRecord(String orderNo, Long userId){
		OrderOperateRecord orderOperateRecord=new OrderOperateRecord();
		orderOperateRecord.setOrderNo(orderNo);
		orderOperateRecord.setUserId(userId);
		orderOperateRecord.setOperationType(6);
		orderOperateRecord.setOperationDesc("删除订单");
		orderOperateRecord.setCreateTime(new Date());
		orderOperateRecordDao.addOrderOperateRecord(orderOperateRecord);
	}
	@Override
	public void receiveOrderRecord(String orderNo, Long userId){
		OrderOperateRecord orderOperateRecord=new OrderOperateRecord();
		orderOperateRecord.setOrderNo(orderNo);
		orderOperateRecord.setUserId(userId);
		orderOperateRecord.setOperationType(4);
		orderOperateRecord.setOperationDesc("收货");
		orderOperateRecord.setCreateTime(new Date());
		orderOperateRecordDao.addOrderOperateRecord(orderOperateRecord);
	}
	@Override
	public void cancleOrderRecord(String orderNo, Long userId){
		OrderOperateRecord orderOperateRecord=new OrderOperateRecord();
		orderOperateRecord.setOrderNo(orderNo);
		orderOperateRecord.setUserId(userId);
		orderOperateRecord.setOperationType(5);
		orderOperateRecord.setOperationDesc("取消订单");
		orderOperateRecord.setCreateTime(new Date());
		orderOperateRecordDao.addOrderOperateRecord(orderOperateRecord);
	}

	@Override
	public void saveOrderOperateRecord(OrderOperateRecord operateRecord) {
		orderOperateRecordDao.addOrderOperateRecord(operateRecord);
	}

//	@Override
//	public void adjustPriceRecord(String orderNo, Long orderItemId, int type, Long quantity, Long discountPrice, Long adjustPrice, Long userId){
//		OrderPriceRecord orderPriceRecord=new OrderPriceRecord();
//		orderPriceRecord.setOrderNo(orderNo);
//		orderPriceRecord.setOrderItemId(orderItemId);
//		orderPriceRecord.setOrderItemId(orderItemId);
//		orderPriceRecord.setType(type);
//		orderPriceRecord.setQuantity(quantity.intValue());
//		orderPriceRecord.setUnitPrice(discountPrice);
//		orderPriceRecord.setAlterType(adjustPrice>=0?1:-1);
//		orderPriceRecord.setAlterAmout(adjustPrice);
//		orderPriceRecord.setCreateAuthor(userId);
//		orderPriceRecord.setCreateTime(new Date());
//		orderPriceRecordDao.addOrderPriceRecord(orderPriceRecord);
//	}
	
	@Override
	public void adjustPriceRecord(String orderNo,int type, Long totalPrice, Long adjustPrice, Long userId){
		OrderPriceRecord orderPriceRecord=new OrderPriceRecord();
		orderPriceRecord.setOrderNo(orderNo);
		orderPriceRecord.setType(type);
		orderPriceRecord.setSubTotal(totalPrice);
		orderPriceRecord.setAlterType(adjustPrice>=0?1:-1);
		orderPriceRecord.setAlterAmout(adjustPrice);
		orderPriceRecord.setCreateAuthor(userId);
		orderPriceRecord.setCreateTime(new Date());
		orderPriceRecordDao.addOrderPriceRecord(orderPriceRecord);
	}
	
	@Override
	public void successOrderRecord(String orderNo, Long userId) {
		OrderOperateRecord orderOperateRecord=new OrderOperateRecord();
		orderOperateRecord.setOrderNo(orderNo);
		orderOperateRecord.setUserId(userId);
		orderOperateRecord.setOperationType(OrderOperateRecord.success);
		orderOperateRecord.setOperationDesc("交易成功");
		orderOperateRecord.setCreateTime(new Date());
		orderOperateRecordDao.addOrderOperateRecord(orderOperateRecord);
	}
	
}
