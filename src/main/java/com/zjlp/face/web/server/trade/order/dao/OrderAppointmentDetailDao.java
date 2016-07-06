package com.zjlp.face.web.server.trade.order.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;

public interface OrderAppointmentDetailDao {

    void insertSelective(OrderAppointmentDetail record);

    List<OrderAppointmentDetail> findOrderAppointmentDetailListByOrderNo(String orderNo);
}
