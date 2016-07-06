package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset.PluAgencyRateSet;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;
import com.zjlp.face.web.server.trade.order.calculate.CalculatePurchase;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.util.PrintLog;

public class PurcharseItemDetail {

	private PrintLog log = new PrintLog("OrderInfoLog");
	// 采购单
	private PurchaseOrder purchaseOrder;
	// 采购细项
	private List<PurchaseOrderItem> purchaseOrderItems;
	//上一个级别
	private PluAgencyRateSet upSet;
	// 当前级别
	private PluAgencyRateSet currentSet;
	// 下一个级别
	private PluAgencyRateSet nextSet;
	private Boolean isBf = true;
	private List<OrderItemDetail> itemDetails;
	private SalesOrder salesOrder;

	public PurcharseItemDetail(SalesOrder salesOrder, List<OrderItemDetail> itemDetails, 
			PluAgencyRateSet upSet, PluAgencyRateSet currentSet, PluAgencyRateSet nextSet, Boolean isBf) {
		Assert.notNull(currentSet);
		this.salesOrder = salesOrder;
		this.itemDetails = itemDetails;
		this.currentSet = currentSet;
		this.upSet = upSet;
		this.nextSet = nextSet;
		this.isBf = isBf;
		this.purchaseOrderItems = new ArrayList<PurchaseOrderItem>();
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public List<PurchaseOrderItem> getPurchaseOrderItems() {
		return purchaseOrderItems;
	}

	/**
	 * 采购细项生成
	 * 
	 * @Title: generate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @date 2015年9月26日 上午11:28:38
	 * @author lys
	 */
	public void generate() {
		// 参数初始化
		Long totalPurchaseQuantity = 0L;
		Long totalSalesPrice = 0L;
		Long totalPurchasePrice = 0L;
		Long totalProfitPrice = 0L;
		Long goodId;
		Long quantity;
		Long totalPrice;
		for (OrderItemDetail item : itemDetails) {
			goodId = item.getGoodSku().getGoodId();
			quantity = item.getQuantity();
			totalPrice = item.getCachePrice();
			
			CalculatePurchase calculatePurchase = new CalculatePurchase(BigDecimal.valueOf(totalPrice),
					this.getRate(currentSet, goodId), this.getRate(nextSet, goodId));
			
			calculatePurchase.calculate();

			PurchaseOrderItem poi = this.initPurchaseOrderItem(item.getOrderItem(), quantity,
					calculatePurchase.getPurchasePrice(), calculatePurchase.getSalesPrice(),
					calculatePurchase.getProfit());
			
			purchaseOrderItems.add(poi);

			/** 总计利益计算 */
			totalPurchaseQuantity = CalculateUtils.getSum(totalPurchaseQuantity, item.getQuantity());// 总计采购数量
			totalPurchasePrice = CalculateUtils.getSum(totalPurchasePrice, poi.getSubPurchasePrice());//总计采购总价格
			totalSalesPrice =  CalculateUtils.getSum(totalSalesPrice, poi.getSubSalesPrice());//总计售出价格
			totalProfitPrice =  CalculateUtils.getSum(totalProfitPrice, poi.getSubProfitPrice());//总计佣金
		}
		log.writeLog("分项利益计算完成");

		// 处理采购单信息
		this.initPurchaseOrder(totalPurchaseQuantity, totalSalesPrice,
				totalPurchasePrice, totalProfitPrice);
		log.writeLog("采购单生成完成");
	}
	
	private BigDecimal getRate(PluAgencyRateSet rateSet, Long skuId) {
		if (null == rateSet) {
			return BigDecimal.ZERO;
		}
		return rateSet.getRateBy(skuId);
	}
	
	private PurchaseOrderItem initPurchaseOrderItem(OrderItem item, Long quantity,
			long purchasePrice, long salesPrice, long profit) {
		
		PurchaseOrderItem poi = new PurchaseOrderItem();
		poi.setGoodId(item.getGoodId());// 采购商品ID
		poi.setGoodName(item.getGoodName());// 采购商品名称
		poi.setGoodSkuId(item.getGoodSkuId());// 采购商品SKU
		poi.setSkuPropertyName(item.getSkuPropertiesName());// SKU属性名称
		poi.setSkuPicturePath(item.getSkuPicturePath());// SKU图片路径
		poi.setQuantity(item.getQuantity());// 采购数量
		poi.setPurchasePrice(purchasePrice);// 采购单价
		poi.setSubPurchasePrice(purchasePrice);// 分项采购小计
		poi.setSalesPrice(salesPrice);// 销售单价
		poi.setSubSalesPrice(salesPrice);// 分项销售小计
		poi.setSubProfitPrice(profit);// 预计销售利润小计
		log.writeLog("生成采购单细项：quantity= ", quantity, "， 销售价=", salesPrice, 
				"，采购价=", purchasePrice, "，利润=", profit);
		return poi;
	}

	private void initPurchaseOrder(Long totalPurchaseQuantity,
			Long totalSalesPrice, Long totalPurchasePrice, Long totalProfitPrice) {

		Subbranch subbranch = currentSet.getRule();
		purchaseOrder = new PurchaseOrder();
		purchaseOrder.setOrderNo(salesOrder.getOrderNo());// 订单号
		purchaseOrder.setSubbranchId(subbranch.getId());
		if (null == upSet) {
			purchaseOrder.setSupplierNo(subbranch.getSupplierShopNo());// 供应商店铺号
			purchaseOrder.setSupplierNick(subbranch.getSuperiorUserId().toString());// 供应商昵称
		} else {
			purchaseOrder.setSupplierNo(upSet.getRule().getUserCell());// 供应商店铺号
			purchaseOrder.setSupplierNick(upSet.getRule().getUserName());// 供应商昵称
		}
		purchaseOrder.setSupplierType(!isBf ? Constants.AGENCY_ROLE_SUPPLIER
						: Constants.AGENCY_ROLE_PURCHASER);
		purchaseOrder.setPurchaserNo(subbranch.getUserCell());
		purchaseOrder.setPurchaserNick(subbranch.getUserName());
		purchaseOrder.setTotalPurchaseQuantity(totalPurchaseQuantity);
		purchaseOrder.setTotalPurchasePrice(totalPurchasePrice);
		purchaseOrder.setTotalProfitPrice(totalProfitPrice);
		purchaseOrder.setPicturePath(salesOrder.getPicturePath());
		purchaseOrder.setCooperationWay(1);
		purchaseOrder.setTotalSalesPrice(totalSalesPrice);
		
		log.writeLog("生成采购单细项：quantity= ", totalPurchaseQuantity, "， 销售价=", totalSalesPrice,
				"，采购价=", totalPurchasePrice, "，总利润=", totalProfitPrice);
	}
	
	public static void main(String[] args) {
		CalculatePurchase calculatePurchase = new CalculatePurchase(BigDecimal.valueOf(14802L),
				BigDecimal.valueOf(30), BigDecimal.ZERO);
		calculatePurchase.calculate();
		System.out.println(calculatePurchase.getSalesPrice());
		System.out.println(calculatePurchase.getPurchasePrice());
		System.out.println(calculatePurchase.getProfit());
		
	}
}
