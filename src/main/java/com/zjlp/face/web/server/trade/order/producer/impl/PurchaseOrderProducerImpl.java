package com.zjlp.face.web.server.trade.order.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.order.service.PurchaseOrderService;
@Service
public class PurchaseOrderProducerImpl implements PurchaseOrderProducer {

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Override
	public List<PurchaseOrder> findPurchaseOrderList(String orderNo,
			Integer cooperationWay) throws Exception {
		return purchaseOrderService.findPurchaseOrderList(orderNo, cooperationWay);
	}

	@Override
	public boolean hasPopularizeForSalesOrder(String orderNo) {
		AssertUtil.hasLength(orderNo, EC.prtNull("orderNo"));
		Integer count = purchaseOrderService.getPurchaseOrderCountByOrderNOAndCooperationWay(orderNo,2);
		if (0 < count.intValue()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasDistributeForSalesOrder(String orderNo) {
		AssertUtil.hasLength(orderNo, EC.prtNull("orderNo"));
		Integer count = purchaseOrderService.getPurchaseOrderCountByOrderNOAndCooperationWay(orderNo,1);
		if (0 < count.intValue()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasEmployeeForSalesOrder(String orderNo) {
		AssertUtil.hasLength(orderNo, EC.prtNull("orderNo"));
		Integer count = purchaseOrderService.getPurchaseOrderCountByOrderNOAndCooperationWay(orderNo,3);
		if (0 < count.intValue()) {
			return true;
		}
		return false;
	}

	@Override
	public PurchaseOrderDto getPurchaseOrder(String orderNo, String purchaserNo)
			throws Exception {
		return purchaseOrderService.getPurchaseOrder(orderNo, purchaserNo);
	}

}
