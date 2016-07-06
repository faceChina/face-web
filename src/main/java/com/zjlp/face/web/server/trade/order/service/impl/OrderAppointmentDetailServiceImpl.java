package com.zjlp.face.web.server.trade.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.dao.OrderAppointmentDetailDao;
import com.zjlp.face.web.server.trade.order.service.OrderAppointmentDetailService;
@Service
public class OrderAppointmentDetailServiceImpl implements OrderAppointmentDetailService {
	@Autowired
	private OrderAppointmentDetailDao orderAppointmentDetailDao;
	@Override
	public void insert(OrderAppointmentDetail record) {
		orderAppointmentDetailDao.insertSelective(record);
	}

	@Override
	public List<OrderAppointmentDetail> findOrderAppointmentDetailListByOrderNo(String orderNo) {
		return orderAppointmentDetailDao.findOrderAppointmentDetailListByOrderNo(orderNo);
	}

}
