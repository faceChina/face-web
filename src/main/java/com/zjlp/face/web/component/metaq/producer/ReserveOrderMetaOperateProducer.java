package com.zjlp.face.web.component.metaq.producer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.ReserveDeatil;
import com.jzwgj.metaq.vo.ReserveItem;
import com.jzwgj.metaq.vo.ReserveMessage;
import com.jzwgj.metaq.vo.Topic;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
import com.zjlp.face.web.component.metaq.log.MetaLog;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.operation.appoint.service.AppointmentService;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.service.OrderAppointmentDetailService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;

@Component
public class ReserveOrderMetaOperateProducer {
	private Logger _logger = Logger.getLogger("metaqInfoLog");

	@Autowired
	private UserService userService;
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private GoodProducer goodProducer;
	@Autowired
	private OrderAppointmentDetailService srderAppointmentDetailService;

	/**
	 * 发送预定订单异步消息
	 * 
	 * @Title: senderAnsy
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @date 2015年2月10日 上午10:31:56
	 * @author Administrator
	 * @throws IOException
	 */
	public void senderAnsy(Shop shop, SalesOrder orderAddress, SalesOrder reserveOrder, List<OrderItem> reserveOrderItemList,
			Long buyerId) {
		_logger.info("预约商品消息调用的线程" + Thread.currentThread().getName());
		// 推送开关
		String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_ORDER_RESERVE");
		if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
			_logger.warn(MetaLog.getString("Switch.Order.Reserve"));
			return;
		}
		if (null == shop) {
			_logger.error(MetaLog.getString("Error.Order.NullShop"));
			return;
		}
		if (null == orderAddress || StringUtils.isBlank(orderAddress.getReceiverName())
				|| null == orderAddress.getReceiverPhone()) {
			_logger.error(MetaLog.getString("Error.Order.NullOrder"));
			return;
		}
		if (null == reserveOrder || StringUtils.isBlank(reserveOrder.getOrderNo())) {
			_logger.error(MetaLog.getString("Error.Order.NullOrder"));
			return;
		}
		if (null == reserveOrderItemList || reserveOrderItemList.isEmpty()) {
			_logger.error(MetaLog.getString("Error.Order.NullOrder"));
			// 服务类预约没有细项
			// return;
		}
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			// 卖家
			Long adminId = shop.getUserId();
			// 买家
			if (buyerId != null) {
				adminId = buyerId;
			}
			Integer orderStatus = reserveOrder.getStatus();
			_logger.info(MetaLog.getString("Reserve.Admin.Begin", adminId.toString(),reserveOrder.getOrderNo()));
			ReserveMessage reserveMessage = new ReserveMessage();
			reserveMessage.setType(4);// 订单消息类型
			reserveMessage.setStatus(orderStatus);// 订单状态
			reserveMessage.setUserType(ConstantsUtil.U_ADMIN);
			reserveMessage.setUserId(adminId);// 超管用户ID
			reserveMessage.setOrderNo(reserveOrder.getOrderNo());// 订单号
			reserveMessage.setShopType(shop.getType());// 店铺类型
			reserveMessage.setShopName(shop.getName());// 店铺名称
			// reserveMessage.setName(reserveOrder.getReserveName());
			reserveMessage.setContactName(orderAddress.getReceiverName());// 姓名
			reserveMessage.setContactPhone(orderAddress.getReceiverPhone());// 电话
			reserveMessage.setContactAddress(orderAddress.getReceiverAddress());// 收货地址
			reserveMessage.setRemark(reserveOrder.getBuyerMessage());// 买家备注
			reserveMessage.setDate(null != reserveOrder.getAppointmentTime() ? reserveOrder.getAppointmentTime().getTime() : null);// 预定时间
			reserveMessage.setName(reserveOrder.getAppointmentName());// 预定活动名称
			// 根据预约活动ID appointmentId 到预约表 appointment 中查 picturePath
			if (reserveOrder.getAppointmentId() != null) {
				Appointment appointment = this.appointmentService.selectByPrimaryKey(reserveOrder.getAppointmentId());
				reserveMessage.setPicturePath(appointment.getPicturePath());// 预约活动图片路径
			}

			// @Author Baojiang Yang, 预约表 appointment中 picturePath存空，重取订单主图
			if (StringUtils.isBlank(reserveMessage.getPicturePath()) && CollectionUtils.isNotEmpty(reserveOrderItemList)) {
				Good good = this.goodProducer.getGoodById(reserveOrderItemList.get(0).getGoodId());
				if (good != null) {
					reserveMessage.setPicturePath(good.getPicUrl());
//					reserveMessage.setPicturePath(reserveOrderItemList.get(0).getSkuPicturePath());
				}
			}
			List<OrderAppointmentDetail> appointmentList = this.srderAppointmentDetailService.findOrderAppointmentDetailListByOrderNo(reserveOrder.getOrderNo());
			if (CollectionUtils.isNotEmpty(appointmentList)) {
				// 取第一个为预约时间
				reserveMessage.setDateStr(appointmentList.get(0).getAttrValue());
			}
			// 预约属性细项
			List<ReserveDeatil> deatilList = new ArrayList<ReserveDeatil>();
			for (OrderAppointmentDetail current : appointmentList) {
				ReserveDeatil detail = new ReserveDeatil();
				detail.setId(current.getId());
				detail.setAttrName(current.getAttrName());
				detail.setAttrValue(current.getAttrValue());
				deatilList.add(detail);
			}
			reserveMessage.setDeatilList(deatilList);

			List<ReserveItem> list = new ArrayList<ReserveItem>();
			ReserveItem reserveItem = null;
			for (OrderItem reserveOrderItem : reserveOrderItemList) {
				reserveItem = new ReserveItem();
				reserveItem.setName(reserveOrderItem.getGoodName());
				reserveItem.setNumber(Long.valueOf(reserveOrderItem.getQuantity()));
				list.add(reserveItem);
			}
			reserveMessage.setItemList(list);
			// 40 待确认 41 卖家已确认 42 卖家已拒绝 43 用户已取消
			if (orderStatus == 40) {
				reserveMessage.setDescription("您有新的预约订单啦！");
			} else if (orderStatus == 41) {
				reserveMessage.setDescription("您的预约订单已经确认啦！");
			} else if (orderStatus == 42) {
				reserveMessage.setDescription("很抱歉，您的预约订单未通过审核喔。");
			} else if (orderStatus == 43) {
				reserveMessage.setDescription("您有预约订单被取消。");
			}
			executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.RESERVETOPIC, reserveMessage.toJson()));
			_logger.info(MetaLog.getString("Reserve.Admin.End", adminId.toString(), reserveOrder.getOrderNo()));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
