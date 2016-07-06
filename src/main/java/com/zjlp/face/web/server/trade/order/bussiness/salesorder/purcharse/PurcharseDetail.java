package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.trade.order.calculate.CalculateDistribution;
import com.zjlp.face.web.server.trade.order.calculate.CalculatePurchase;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.util.PrintLog;

@Deprecated
public class PurcharseDetail {
	private PrintLog log = new PrintLog(getClass());
	//采购明细
	private List<PurcharseDetail.PurcharseItemDetail> details;
	//代理关系
	private AgencySet agencySet;
	//订单
	private SalesOrder salesOrder;
	//订单细项
	private List<OrderItem> orderItems;
	
	public PurcharseDetail(AgencySet agencySet, SalesOrder salesOrder, List<OrderItem> orderItems){
		this.agencySet = agencySet;
		this.salesOrder = salesOrder;
		this.orderItems = orderItems;
		details = new ArrayList<PurcharseDetail.PurcharseItemDetail>();
	}
	public void generate(){
		log.writeLog("开始生成采购单");
		Assert.notNull(agencySet);
		Assert.notEmpty(orderItems);
		PurcharseItemDetail itemDetail;
		AgencySet currentSet = agencySet;
		do {
			log.writeLog("配置信息：", currentSet);
			itemDetail = new PurcharseItemDetail(currentSet);
			itemDetail.generate();
			details.add(itemDetail);
			//下一级
			currentSet = currentSet.hasChilds() ? currentSet.getChilds().get(0) : null;
		} while (null != currentSet);
		log.writeLog("采购单生成完成");
	}
	
	public List<PurcharseItemDetail> getDetails() {
		return details;
	}
	
	public class PurcharseItemDetail {
		// 采购单
		private PurchaseOrder purchaseOrder;
		// 采购细项
		private List<PurchaseOrderItem> purchaseOrderItems;
		// 当前级别
		private AgencySet currentSet;
		
		PurcharseItemDetail(AgencySet currentSet) {
			Assert.notNull(currentSet);
			this.currentSet = currentSet;
			this.purchaseOrderItems = new ArrayList<PurchaseOrderItem>();
		}
		public PurchaseOrder getPurchaseOrder() {
			return purchaseOrder;
		}
		public List<PurchaseOrderItem> getPurchaseOrderItems() {
			return purchaseOrderItems;
		}
		public void generate(){
			//参数初始化
			boolean isBuyShop = this.isBushop();
			BigDecimal lastPurchaseRate = this.getLastPurchaseRate();
			Long totalPurchaseQuantity = 0L;//本次采购数量总计
//			Long totalSalesPrice = 0L;//本次采购售出价总计
//			Long totalPurchasePrice = 0L;//本次采购价总计
//			Long totalProfitPrice = 0L;//本次采购佣金总计
			for (OrderItem item : orderItems) {
				Long salesPrice = item.getDiscountPrice();//单项销售单价
				Long skuSalesPrice = item.getSkuSalesPrice();
				Long quantity = item.getQuantity();//购买数量
				/** 小计利益计算 */
				CalculateDistribution calculateD = new CalculateDistribution(isBuyShop,salesPrice,skuSalesPrice,lastPurchaseRate,
						currentSet.getRealRate(), quantity);
				Long purchasePrice = calculateD.calculatePurchasePrice();//计算采购单价
				Long subPurchasePrice = calculateD.calculateSubPurchasePrice();	//计算采购小计	
				Long subSalesPrice = calculateD.calculateSubSalesPrice();//计算销售小计
				Long subProfitPrice = calculateD.calculateSubProfitPricePrice();//计算佣金小计
				log.writeLog("小计利益：", calculateD);
				
				PurchaseOrderItem poi = this.initPurchaseOrderItem(item, purchasePrice,
						subPurchasePrice, salesPrice, subSalesPrice, subProfitPrice);
				purchaseOrderItems.add(poi);
				
				/** 总计利益计算*/
				totalPurchaseQuantity = CalculateUtils.getSum(totalPurchaseQuantity, item.getQuantity());//总计采购数量
//				totalPurchasePrice = CalculateUtils.getSum(totalPurchasePrice,subPurchasePrice);//总计采购总价格
//				totalSalesPrice =  CalculateUtils.getSum(totalSalesPrice,subSalesPrice);//总计售出价格
//				totalProfitPrice =  CalculateUtils.getSum(totalProfitPrice,subProfitPrice);//总计佣金
			}
			log.writeLog("分项利益计算完成");
			
			//处理采购单信息
			CalculatePurchase calculatePurchase = new CalculatePurchase(new BigDecimal(salesOrder.getPrice()), 
					currentSet.getRealRate(), lastPurchaseRate);
			calculatePurchase.calculate();
			this.initPurchaseOrder(totalPurchaseQuantity, calculatePurchase.getSalesPrice(),
					calculatePurchase.getPurchasePrice(), calculatePurchase.getProfit());
			log.writeLog("采购单生成完成");
		}
		
		private boolean isBushop() {
			return !currentSet.hasChilds();
		}
		
		private BigDecimal getLastPurchaseRate() {
			if (currentSet.hasChilds()) {
				return currentSet.getChilds().get(0).getRealRate();
			}
			return BigDecimal.ZERO;
		}
		
		private PurchaseOrderItem initPurchaseOrderItem(OrderItem item, Long purchasePrice, 
				Long subPurchasePrice, Long salesPrice, Long subSalesPrice, Long subProfitPrice) {
			PurchaseOrderItem poi = new PurchaseOrderItem();
			poi.setGoodId(item.getGoodId());//采购商品ID
			poi.setGoodName(item.getGoodName());//采购商品名称
			poi.setGoodSkuId(item.getGoodSkuId());//采购商品SKU
			poi.setSkuPropertyName(item.getSkuPropertiesName());//SKU属性名称
			poi.setSkuPicturePath(item.getSkuPicturePath());//SKU图片路径
			poi.setQuantity(item.getQuantity());//采购数量
			poi.setPurchasePrice(purchasePrice);//采购单价
			poi.setSubPurchasePrice(subPurchasePrice);//分项采购小计
			poi.setSalesPrice(salesPrice);//销售单价
			poi.setSubSalesPrice(subSalesPrice);//分项销售小计
			poi.setSubProfitPrice(subProfitPrice);//预计销售利润小计
			return poi;
		}
		
		private void initPurchaseOrder(Long totalPurchaseQuantity,
				Long totalSalesPrice, Long totalPurchasePrice,
				Long totalProfitPrice) {
			purchaseOrder = new PurchaseOrder();
			purchaseOrder.setOrderNo(salesOrder.getOrderNo());//订单号
			purchaseOrder.setSubbranchId(currentSet.getSubbranchId());
			purchaseOrder.setSupplierNo(currentSet.getSupplier());//供应商店铺号
			purchaseOrder.setSupplierNick(currentSet.getSupplierNick());//供应商昵称
			purchaseOrder.setSupplierType(currentSet.isRoot() 
					? Constants.AGENCY_ROLE_SUPPLIER : Constants.AGENCY_ROLE_PURCHASER);
			purchaseOrder.setPurchaserNo(currentSet.getPurchaser());
			purchaseOrder.setPurchaserNick(currentSet.getPurchaserNick());
			purchaseOrder.setTotalPurchaseQuantity(totalPurchaseQuantity);
			purchaseOrder.setTotalPurchasePrice(totalPurchasePrice);
			purchaseOrder.setTotalProfitPrice(totalProfitPrice);
			purchaseOrder.setPicturePath(salesOrder.getPicturePath());
			purchaseOrder.setCooperationWay(1);
			purchaseOrder.setTotalSalesPrice(totalSalesPrice);
		}
	}
}
