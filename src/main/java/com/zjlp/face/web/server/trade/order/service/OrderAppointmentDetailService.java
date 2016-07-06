package com.zjlp.face.web.server.trade.order.service;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;

public interface OrderAppointmentDetailService {
	void insert(OrderAppointmentDetail record);

    List<OrderAppointmentDetail> findOrderAppointmentDetailListByOrderNo(String orderNo);
}
