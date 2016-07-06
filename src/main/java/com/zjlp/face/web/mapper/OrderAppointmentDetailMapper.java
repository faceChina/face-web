package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;

public interface OrderAppointmentDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderAppointmentDetail record);

    int insertSelective(OrderAppointmentDetail record);

    OrderAppointmentDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderAppointmentDetail record);

    int updateByPrimaryKey(OrderAppointmentDetail record);

	List<OrderAppointmentDetail> findOrderAppointmentDetailListByOrderNo(String orderNo);
}