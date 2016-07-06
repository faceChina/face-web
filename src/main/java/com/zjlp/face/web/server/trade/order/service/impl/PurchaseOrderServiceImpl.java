package com.zjlp.face.web.server.trade.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.component.sms.Sms.SwitchType;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderDao;
import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderItemDao;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;
import com.zjlp.face.web.server.trade.order.service.PurchaseOrderService;
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private PurchaseOrderItemDao purchaseOrderItemDao;

	@Override
	public void add(PurchaseOrder purchaseOrder,
			List<PurchaseOrderItem> purchaseOrderItemList) throws Exception {
		
		//Checking argument
		AssertUtil.notNull(purchaseOrder, EC.prtNull("purchaseOrder"));
		
		AssertUtil.notEmpty(purchaseOrderItemList, EC.prtNull("purchaseOrderItemList"));
		
		Date date = new Date();
		
		//Set base data,Insert  purchase Order info to db.
		if (null == purchaseOrder.getCreateTime()) {
			
			purchaseOrder.setCreateTime(date);
			
			purchaseOrder.setUpdateTime(date);
		}
		purchaseOrderDao.insertSelective(purchaseOrder);
		
		//Set base data,Insert  purchase Order item info to db.
		for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
			
			purchaseOrderItem.setPurchaseOrderId(purchaseOrder.getId());
			
			if (null == purchaseOrderItem.getCreateTime()) {
				
				purchaseOrderItem.setCreateTime(date);
				
				purchaseOrderItem.setUpdateTime(date);
			}
			
			purchaseOrderItemDao.insertSelective(purchaseOrderItem);
		}
		
	}

	@Override
	public List<PurchaseOrder> findPurchaseOrderList(String orderNo)  {

		// Call this method
		return this.findPurchaseOrderList(orderNo, null);
	}

	@Override
	public List<PurchaseOrder> findPurchaseOrderList(String orderNo,
			Integer cooperationWay){
		
		//Checking argument
		AssertUtil.hasLength(orderNo, EC.prtNull("orderNo"));
		
		//find data to DBMS
		return purchaseOrderDao.findPurchaseOrderList(orderNo,cooperationWay);
	}

	@Override
	public List<PurchaseOrderItemDto> findPurchaseOrderItemList(
			Long purchaseOrderId) {
		
		//Checking argument
		AssertUtil.notNull(purchaseOrderId, EC.prtNull("purchaseOrderId"));
		
		//find data to DBMS
		return purchaseOrderItemDao.findPurchaseOrderItemList(purchaseOrderId);
	}

	@Override
	public PurchaseOrderDto getPurchaseOrder(String orderNo, String purchaserNo)
			throws Exception {
		
		//Checking argument
		AssertUtil.hasLength(orderNo, EC.prtNull("orderNo"));
		AssertUtil.hasLength(purchaserNo, EC.prtNull("purchaserNo"));
		
		//find data to DBMS
		return purchaseOrderDao.getPurchaseOrder(orderNo, purchaserNo);
	}

	@Override
	public void editPurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
		
		//Checking argument
		AssertUtil.notNull(purchaseOrder, EC.prtNull("purchaseOrder"));
		AssertUtil.notNull(purchaseOrder.getId(), EC.prtNull("purchaseOrder.id"));
		
		purchaseOrder.setUpdateTime(new Date());
		
		purchaseOrderDao.edit(purchaseOrder);
	}

	@Override
	public List<Long> findPopularizeSkuIdList(String orderNo,
			List<Long> skuIdList) throws Exception {
		
		//Checking argument
		AssertUtil.hasLength(orderNo, EC.prtNull("orderNo"));
		AssertUtil.notEmpty(skuIdList, EC.prtNull("skuIdList"));
		
		return purchaseOrderDao.findPopularizeSkuIdList(orderNo, skuIdList);
	}

	@Override
	public Integer getPurchaseOrderCountByOrderNOAndCooperationWay(
			String orderNo, Integer cooperationWay) {
		return purchaseOrderDao.getPurchaseOrderCountByOrderNOAndCooperationWay(orderNo,cooperationWay);
	}

	
	@Override
	public void sendPopularizeSms(String orderNo) {
		
		AssertUtil.notNull(orderNo, EC.prtNull("orderNo"));
		//查询分销推广订单
		List<PurchaseOrder> list  = this.findPurchaseOrderList(orderNo,2);
		if (null == list || list.isEmpty()) {
			return ;
		}
		
		for (PurchaseOrder purchaseOrder : list) {
			String purchaserNoPhone = purchaseOrder.getPurchaserNo();
			Long totalProfitPrice = purchaseOrder.getTotalProfitPrice();
			Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_POPULARIZE_SWITCH, SmsContent.UMS_305,purchaserNoPhone,
					"商品",CalculateUtils.converPennytoYuan(totalProfitPrice),PropertiesUtil.getContexrtParam("WGJ_URL"));
		}
	}

	@Override
	public List<PurchaseOrderDto> findPromoteOrdersByOrderNo(String orderNo)
			throws Exception {
		AssertUtil.hasLength(orderNo, "param[orderNo] can't be null.");
		return purchaseOrderDao.findPromoteOrdersByOrderNo(orderNo);
	}

	@Override
	public PurchaseOrder getSupplierOrder(String orderNo, String supplierNo) {
		AssertUtil.hasLength(orderNo, "param[orderNo] can't be null.");
		AssertUtil.hasLength(supplierNo, "param[supplierNo] can't be null.");
		return purchaseOrderDao.getSupplierOrder(orderNo, supplierNo);
	}
	
	@Override
	public Integer getSubbranchOrderTDPCount(String cell) {
		AssertUtil.hasLength(cell, "param[cell] can't be null.");
		Date today=DateUtil.getZeroPoint(new Date());
		Integer count = purchaseOrderDao.getSubbranchOrderTDPCount(cell, today);
		return null == count ? 0 : count;
	}

	@Override
	public String getOrderSourceByPrimaryKey(Long id) {
		AssertUtil.notNull(id, "param[id] can't be null.");
		return purchaseOrderDao.getOrderSourceByPrimaryKey(id);
	}

	@Override
	public Long getCommissionByTime(Long subbranchId, Integer time) {
		Date now = new Date();
		Date zeroPoint = DateUtil.getZeroPoint(now);
		Date countDay = null;
		if (time == 1) {
			countDay = DateUtil.addDay(zeroPoint, -1);
		} else if (time == 2) {
			countDay = DateUtil.addDay(zeroPoint, -7);
		} else if (time == 3) {
			countDay = DateUtil.addDay(zeroPoint, -30);
		}
		return purchaseOrderDao.getCommissionByTime(subbranchId, countDay, zeroPoint);
	}

	@Override
	public Long getOrderPayCommission(String orderNo, String supplierNo,
			Integer supplierType) {
		return purchaseOrderDao.getOrderPayCommission(orderNo, supplierNo, supplierType);
	}
	
	
}
