package com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PurcharseDetailNew {
	private static final BigDecimal BIGDECIMAL_100 = new BigDecimal(100);
	private PrintLog log = new PrintLog(getClass());
	//采购明细
	private List<PurcharseDetailNew.PurcharseItemDetailNew> details;
	//代理关系
	private AgencySetNew agencySet;
	//订单
	private SalesOrder salesOrder;
	//订单细项
	private List<OrderItem> orderItems;
	
	public PurcharseDetailNew(AgencySetNew agencySet, SalesOrder salesOrder, List<OrderItem> orderItems){
		this.agencySet = agencySet;
		this.salesOrder = salesOrder;
		this.orderItems = orderItems;
		details = new ArrayList<PurcharseDetailNew.PurcharseItemDetailNew>();
	}
	
	public void generate(){
		log.writeLog("开始生成采购单");
		Assert.notNull(agencySet);
		Assert.notEmpty(orderItems);
		PurcharseItemDetailNew itemDetail;
		AgencySetNew currentSet = agencySet;
		BigDecimal rootRate = currentSet.getRate(); //bf1初始佣金率
		Map<Long,AgencySetNew> agencyMap = new HashMap<Long, AgencySetNew>(); //保存当前商品的AgencySetNew,用于下级使用
		do {
			log.writeLog("配置信息：", currentSet);
			itemDetail = new PurcharseItemDetailNew(currentSet,rootRate,agencyMap);
			itemDetail.generate();
			details.add(itemDetail);
			//下一级
			currentSet = currentSet.hasChilds() ? currentSet.getChilds().get(0) : null;
		} while (null != currentSet);
		log.writeLog("采购单生成完成");
	}
	
	public List<PurcharseItemDetailNew> getDetails() {
		return details;
	}
	
	
	public class PurcharseItemDetailNew {
		// 采购单
		private PurchaseOrder purchaseOrder;
		// 采购细项
		private List<PurchaseOrderItem> purchaseOrderItems;
		// 当前级别
		private AgencySetNew currentSet;
		// bf1佣金率
		private BigDecimal rootRate;
		// 保存当前商品的AgencySetNew,用于下级使用
		Map<Long,AgencySetNew> agencyMap; 
		
		PurcharseItemDetailNew(AgencySetNew currentSet,BigDecimal rootRate,Map<Long,AgencySetNew> agencyMap) {
			Assert.notNull(currentSet);
			this.currentSet = currentSet;
			this.rootRate = rootRate;
			this.agencyMap = agencyMap;
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
			BigDecimal lastPurchaseRate = BigDecimal.ZERO;
			Long totalPurchaseQuantity = 0L;//本次采购数量总计
			Long totalSalesPrice = 0L;//本次采购售出价总计
			Long totalPurchasePrice = 0L;//本次采购价总计
			Long totalProfitPrice = 0L;//本次采购佣金总计
			AgencySetNew tmepSet = currentSet.clone();
			for (OrderItem item : orderItems) {
				AgencySetNew initSet = agencyMap.get(item.getGoodSkuId()); //获取当前商品的AgencySetNew //同商品,不同SKU价格,存skuID
				if (null == initSet) { //不存在
					BigDecimal currentRate = new BigDecimal(currentSet.getMapRate().get(item.getGoodId())); //当前单品佣金率
					if (orderItems.size() > 1) {
						currentSet.setRate(tmepSet.getRate());
						currentSet.setRealRate(tmepSet.getRealRate());
					}
					if (!rootRate.equals(currentRate)) { //与bf1佣金率比较
						AgencySetNew iterationSet = null;
						iterationSet = currentSet.clone();
						if (null != iterationSet.getParent()) { //是否还有上级
							BigDecimal realRate = iterationSet.getParent().getRealRate().multiply(iterationSet.getRate()).divide(BIGDECIMAL_100); //重新计算realRate
							currentSet.setRealRate(realRate);
							agencyMap.put(item.getGoodSkuId(), currentSet.clone());
						}else {
							currentSet.setRate(currentRate);
							currentSet.setRealRate(currentRate);
							agencyMap.put(item.getGoodSkuId(), currentSet.clone());
						}
					}
				} else { //已经存在
					currentSet.setRealRate(initSet.getRealRate().multiply(currentSet.getRate()).divide(BIGDECIMAL_100));
					agencyMap.put(item.getGoodSkuId(), currentSet.clone());
				}
				
				lastPurchaseRate = this.getLastPurchaseRate();
				
				/** 小计利益计算 */
				Long salesPrice = item.getDiscountPrice();//单项销售单价
				Long skuSalesPrice = item.getDiscountPrice();//优惠(会员)价格 item.getSkuSalesPrice();
				Long quantity = item.getQuantity();//购买数量
				Long itemPrice = item.getPrice(); //销售单价
				CalculateDistribution calculateD = null;
				Long purchasePrice = null;
				Long subPurchasePrice = null;
				Long subSalesPrice = null;
				Long subProfitPrice = null;
				//有单品佣金率的时候，每个商品分开计算(并且处理积分抵扣)
				if (currentSet.getMapRate().get("isRate").equals(1)) {
					calculateD = new CalculateDistribution(salesPrice,skuSalesPrice,lastPurchaseRate,currentSet.getRealRate(), 
							quantity,this.getTotalItemPrice(),this.getRealPrice(),itemPrice);
					purchasePrice = calculateD.calculatePurchasePrice(); //计算采购单价
					subPurchasePrice = calculateD.calculateSubPurchasePriceReal(); //计算采购小计	
					subSalesPrice = calculateD.calculateSubSalesPriceReal(); //计算销售小计
					subProfitPrice = calculateD.calculateSubProfitPricePrice(); //计算佣金小计
				} else {
					calculateD = new CalculateDistribution(isBuyShop,salesPrice,skuSalesPrice,lastPurchaseRate,currentSet.getRealRate(), quantity);
					purchasePrice = calculateD.calculatePurchasePrice(); //计算采购单价
					subPurchasePrice = calculateD.calculateSubPurchasePrice(); //计算采购小计	
					subSalesPrice = calculateD.calculateSubSalesPrice(); //计算销售小计
					subProfitPrice = calculateD.calculateSubProfitPricePrice(); //计算佣金小计
				}
				log.writeLog("小计利益：", calculateD);
				PurchaseOrderItem poi = this.initPurchaseOrderItem(item, purchasePrice,subPurchasePrice, salesPrice, subSalesPrice, subProfitPrice);
				purchaseOrderItems.add(poi);
				
				/** 总计利益计算*/
				totalPurchaseQuantity = CalculateUtils.getSum(totalPurchaseQuantity, item.getQuantity());//总计采购数量
				//有单品佣金率的时候，每个商品分开计算(并且处理积分抵扣)
				if (currentSet.getMapRate().get("isRate").equals(1)) {
					totalPurchasePrice = CalculateUtils.getSum(totalPurchasePrice,subPurchasePrice);//总计采购总价格
					totalSalesPrice =  CalculateUtils.getSum(totalSalesPrice,subSalesPrice);//总计售出价格
					totalProfitPrice =  CalculateUtils.getSum(totalProfitPrice,subProfitPrice);//总计佣金
				}
			}
			log.writeLog("分项利益计算完成");
			
			//处理采购单信息
			//没有单品佣金率的时候，所有商品一起(同佣金率)计算(并且处理积分抵扣)
			if (currentSet.getMapRate().get("isRate").equals(0)) {
				Long realPrice = this.getRealPrice();
				CalculatePurchase calculatePurchase = new CalculatePurchase(new BigDecimal(realPrice), currentSet.getRealRate(), lastPurchaseRate);
				calculatePurchase.calculate();
				this.initPurchaseOrder(totalPurchaseQuantity, calculatePurchase.getSalesPrice(),calculatePurchase.getPurchasePrice(), calculatePurchase.getProfit());
			} else {
				this.initPurchaseOrder(totalPurchaseQuantity, totalSalesPrice,totalPurchasePrice, totalProfitPrice);
			}
			log.writeLog("采购单生成完成");
		}
		
		
		/**
		 * 获取订单销售总金额(不包含运费，打折和抵扣之前)
		 * @author talo
		 */
		public Long getTotalItemPrice () {
			BigDecimal price = null;
			BigDecimal quantity = null;
			BigDecimal totalPrice = BigDecimal.ZERO;
			for(OrderItem item:orderItems){
				price = new BigDecimal(item.getPrice());
				quantity = new BigDecimal(item.getQuantity());
				totalPrice = totalPrice.add(price.multiply(quantity));
			}
			return totalPrice.longValue();
		}
		
		/**
		 * 获取订单的实际总金额(不包含运费，打折和抵扣之后)
		 * @author talo
		 */
		public Long getRealPrice () {
			Long price = salesOrder.getPrice(); //获得订单的总金额
			Long integral = salesOrder.getIntegral(); //获得订单使用了多少个积分
			return price - integral;
		}
		
		private boolean isBushop() {
			return !currentSet.hasChilds();
		}
		
		private BigDecimal getLastPurchaseRate() {
			if (currentSet.hasChilds()) {
				AgencySetNew child = currentSet.getChilds().get(0);
				return currentSet.getRealRate().multiply(child.getRate()).divide(BIGDECIMAL_100);
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
