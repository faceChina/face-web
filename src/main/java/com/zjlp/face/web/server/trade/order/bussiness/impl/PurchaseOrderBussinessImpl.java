package com.zjlp.face.web.server.trade.order.bussiness.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.operation.popularize.producer.PopularizeProducer;
import com.zjlp.face.web.server.trade.order.bussiness.PurchaseOrderBussiness;
import com.zjlp.face.web.server.trade.order.calculate.CalculatePopularize;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.service.PurchaseOrderService;

@Service
public class PurchaseOrderBussinessImpl implements PurchaseOrderBussiness {
	
	private Log _log = LogFactory.getLog(getClass());
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private PopularizeProducer popularizeProducer;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addPopularizePurchaseOrder(String shopNo,Integer type,Integer commissionRate,
			SalesOrder salesOrder, List<OrderItem> orderItemList) {
		try {
			AssertUtil.hasLength(shopNo, EC.prtNull("shopNo"));
			AssertUtil.notNull(type, EC.prtNull("type"));
			AssertUtil.notNull(salesOrder, EC.prtNull("salesOrder"));
			AssertUtil.notEmpty(orderItemList, EC.prtNull("orderItemList"));
			AssertUtil.hasLength(salesOrder.getOrderNo(), EC.prtNull("orderNo"));
			if (0 >= type.intValue() && 3 <= type.intValue() ) {
				throw new OrderException("未知的店铺推广类型！");
			}
			String orderNo = salesOrder.getOrderNo();
			Long allProfitPrice = 0L;
			if (null == commissionRate) {
				//获取店铺配置的折扣率
				commissionRate = popularizeProducer.getShopPopularizeRate(shopNo, type);
			}
			//根据用户分采购单
			List<OrderItem> orderItemtemp = null;
			Map<String,List<OrderItem>> map = new HashMap<String,List<OrderItem>>();
			List<OrderItem> customerOrderItemList = new ArrayList<OrderItem>();
			for (OrderItem orderItem : orderItemList) {
				if (StringUtils.isBlank(orderItem.getShareId())) {
					customerOrderItemList.add(orderItem);
					continue;
				}
				if (map.containsKey(orderItem.getShareId())) {
					map.get(orderItem.getShareId()).add(orderItem);
				}else{
					orderItemtemp = new ArrayList<OrderItem>();
					orderItemtemp.add(orderItem);
					map.put(orderItem.getShareId(), orderItemtemp);
				}
			}
			boolean isNotShare= !map.isEmpty() && 0 < map.size();
			if (isNotShare) {
				_log.info("==================================开始处理商品推广关系==================================");
				String typeName = 1==type.intValue()?"普通推广":"代理推广";
				if(null == commissionRate || 0 > commissionRate){
					//店铺配置或关闭时不使用
					_log.info("订单[" +orderNo + "] 推广店铺["+shopNo+"]设置类型["+typeName+"]未配置，不生成推广佣金！");
					return ;
				}else{
					_log.info("订单[" +orderNo + "] 推广店铺["+shopNo+"]设置类型["+typeName+"]为折扣率为："+commissionRate+"%");
				}
				for (String shareId :map.keySet()) {
					Long totalPurchaseQuantity = 0L;//本次采购数量总计
					Long totalSalesPrice = 0L;//本次采购售出价总计
					Long totalPurchasePrice = 0L;//本次采购价总计
					Long totalProfitPrice = 0L;//本次采购佣金总计
					List<PurchaseOrderItem> purchaseOrderItemList = new ArrayList<PurchaseOrderItem>();
					for (OrderItem orderItem : map.get(shareId)) {
						//细项处理
						PurchaseOrderItem poi = this._proccessItem(type,orderItem,commissionRate);
						/** 总计部分 */
						//总计采购数量
						totalPurchaseQuantity = CalculateUtils.getSum(totalPurchaseQuantity, orderItem.getQuantity());
						//总计采购总价格
						totalPurchasePrice = CalculateUtils.getSum(totalPurchasePrice,poi.getSubPurchasePrice());
						//总计售出价格
						totalSalesPrice =  CalculateUtils.getSum(totalSalesPrice,poi.getSubSalesPrice());
						//总计佣金
						totalProfitPrice =  CalculateUtils.getSum(totalProfitPrice,poi.getSubProfitPrice());
						purchaseOrderItemList.add(poi);
					}
					//处理采购单信息
					PurchaseOrder po = new PurchaseOrder();
					po.setOrderNo(salesOrder.getOrderNo());//订单号
					po.setSupplierNo(shopNo);//供应商店铺号
					po.setSupplierNick(salesOrder.getShopName());//供应商昵称
					po.setSupplierType(1);
					po.setPurchaserNo(shareId);
					po.setPurchaserNick(shareId);
					po.setTotalPurchaseQuantity(totalPurchaseQuantity);
					po.setPicturePath(salesOrder.getPicturePath());
					po.setTotalPurchasePrice(totalPurchasePrice);
					po.setTotalProfitPrice(totalProfitPrice);
					po.setPicturePath(salesOrder.getPicturePath());
					po.setCooperationWay(2);//合作方式：2 推广
					po.setTotalSalesPrice(totalSalesPrice);
					//数据库事务操作
					purchaseOrderService.add(po, purchaseOrderItemList);
					allProfitPrice = CalculateUtils.getSum(allProfitPrice, po.getTotalProfitPrice());
					_log.info("订单[" +orderNo + "] 推广用户【"+shareId+"】-采购总价【"+totalPurchasePrice+"】-销售总价【"+totalSalesPrice+"】-利润总计【"+totalProfitPrice+"】");
				}
			}
			_log.info("订单[" +orderNo + "] 当前没有推广的商品，不处理商品推广关系！");
		} catch (Exception e) {
			throw new OrderException(e.getMessage(),e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private PurchaseOrderItem _proccessItem(Integer type,OrderItem orderItem,Integer commissionRate){
		/** 基本参数获取 */
		Long purchasePrice = 0L;//推广时不记录
		Long salesPrice = orderItem.getDiscountPrice();//单项销售单价
		Long quantity = orderItem.getQuantity();//购买数量
		/** 小计利益计算 */
		CalculatePopularize calculateP = null;
		if (2 == type.intValue()) {
			calculateP = new CalculatePopularize(salesPrice,commissionRate,quantity,orderItem.getProfitPrice());
		}else{
			calculateP = new CalculatePopularize(salesPrice,commissionRate,quantity);
		}
		//小计出货总价格
		Long subSalesPrice = calculateP.calculateSubSalesPrice();
		//小计佣金
		Long subProfitPrice =  calculateP.calculateSubProfitPricePrice();
		//小计采购价格
		Long subPurchasePrice =calculateP.calculateSubPurchasePrice();
		_log.info("商品["+orderItem.getId()+"] 推广计算结果"+calculateP);
		
		/** 设置入库参数 */
		PurchaseOrderItem poi = new PurchaseOrderItem();
		poi.setGoodId(orderItem.getGoodId());//采购商品ID
		poi.setGoodName(orderItem.getGoodName());//采购商品名称
		poi.setGoodSkuId(orderItem.getGoodSkuId());//采购商品SKU
		poi.setSkuPropertyName(orderItem.getSkuPropertiesName());//SKU属性名称
		poi.setSkuPicturePath(orderItem.getSkuPicturePath());//SKU图片路径
		poi.setQuantity(orderItem.getQuantity());//采购数量
		poi.setPurchasePrice(purchasePrice);//采购单价
		poi.setSubPurchasePrice(subPurchasePrice);//分项采购小计
		poi.setSalesPrice(salesPrice);//销售单价
		poi.setSubSalesPrice(subSalesPrice);//分项销售小计
		poi.setSubProfitPrice(subProfitPrice);//预计销售利润小计
		_log.info("商品["+orderItem.getId()+"]本次共支出佣金总计【"+subProfitPrice+"】");
		return poi;

	}

	@Override
	public Long getOrderPayCommission(String orderNo, String supplierNo,
			Integer supplierType) {
		try {
			AssertUtil.hasLength(orderNo, "参数orderNo不能为空");
			AssertUtil.hasLength(supplierNo, "参数supplierNo不能为空");
			AssertUtil.isTrue(supplierType != null && (supplierType == 1 || supplierType == 2), "参数supplierType只能为1或2");
			return purchaseOrderService.getOrderPayCommission(orderNo, supplierNo, supplierType);
		} catch (Exception e) {
			throw new OrderException(e.getMessage(),e);
		}
	}
	
	


}
