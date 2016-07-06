package com.zjlp.face.web.server.trade.order.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.zjlp.face.web.exception.ext.MoneyException;
/**
 * 分销订单利益计算类
 * @ClassName: CalculateDistribution 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月15日 下午12:58:44
 */
public class CalculateDistribution {
	
	private static final BigDecimal BIGDECIMAL_100 = new BigDecimal(100);
	//用户(销售/购买)单价
	private BigDecimal salesPrice = new BigDecimal(0);
	//商品SKU单价
	private BigDecimal skuSalesPrice =  new BigDecimal(0);
	//采购折扣率
	private BigDecimal purchaseRate = new BigDecimal(0);
	//采购单价
	private BigDecimal purchasePrice = new BigDecimal(0); 
	//购买数量
	private BigDecimal quantity = new BigDecimal(0);
	//订单调整价格
	private BigDecimal adjustPrice = new BigDecimal(0);
	//小计销售价格
	private BigDecimal subSalesPrice = new BigDecimal(0);
	//采购价格小计
	private BigDecimal subPurchasePrice = new BigDecimal(0);
	//(利润/佣金)小计
	private BigDecimal subProfitPrice =  new BigDecimal(0);
	
	private boolean isBuyShop = false;
	//下级采购折扣率
	private BigDecimal lastPurchaseRate = new BigDecimal(0);
	
	//订单总金额
	private BigDecimal totalItemPrice = BigDecimal.ZERO;
	//订单实际(销售)金额
	private BigDecimal realPrice = BigDecimal.ZERO;
	//销售单价
	private BigDecimal itemPrice = BigDecimal.ZERO;
	
	
	/**
	 * 有单品佣金率和积分抵扣
	 */
	public CalculateDistribution(Long salesPrice,Long skuSalesPrice,BigDecimal lastPurchaseRate,BigDecimal purchaseRate,
			Long quantity,Long totalItemPrice,Long realPrice,Long itemPrice) {
		this.salesPrice =  new BigDecimal(salesPrice);
		this.skuSalesPrice = new BigDecimal(skuSalesPrice);
		this.lastPurchaseRate = lastPurchaseRate;
		this.purchaseRate = purchaseRate;
		this.quantity = new BigDecimal(quantity);
		this.totalItemPrice = new BigDecimal(totalItemPrice);
		this.realPrice = new BigDecimal(realPrice);
		this.itemPrice = new BigDecimal(itemPrice);
		//验证
		checkedResVal(this.salesPrice, "销售单价 ");
		checkedResVal(this.skuSalesPrice, "商品SKU单价");
		checkedResVal(this.lastPurchaseRate, "下级采购折扣率");
		checkedResVal(this.purchaseRate, "采购折扣率 ");
		checkedResVal(this.quantity, "购买数量 ");
		checkedResVal(this.totalItemPrice, "订单总金额 ");
		checkedResVal(this.realPrice, "订单实际金额 ");
		checkedResVal(this.itemPrice, "销售单价 ");
	}
	
	/**
	 * 没有单品佣金率(常规情况)
	 */
	public CalculateDistribution(boolean isBuyShop,Long  salesPrice,Long skuSalesPrice,BigDecimal lastPurchaseRate,
			BigDecimal purchaseRate, Long quantity) {
		this.isBuyShop = isBuyShop;
		this.salesPrice =  new BigDecimal(salesPrice);
		this.skuSalesPrice = new BigDecimal(skuSalesPrice);
		this.lastPurchaseRate = lastPurchaseRate;
		this.purchaseRate = purchaseRate;
		this.quantity = new BigDecimal(quantity);
		//验证
		checkedResVal(this.salesPrice, "销售单价 ");
		checkedResVal(this.skuSalesPrice, "商品SKU单价");
		checkedResVal(this.lastPurchaseRate, "下级采购折扣率");
		checkedResVal(this.purchaseRate, "采购折扣率 ");
		checkedResVal(this.quantity, "购买数量 ");
	}
	
	
	/**
	 * 构造
	 * @param salesPrice 销售单价
	 * @param purchaseRate 分销商采购折扣率
	 * @param quantity 用户购买数量
	 */
	public CalculateDistribution(boolean isBuyShop,Long  salesPrice,Long skuSalesPrice,Integer lastPurchaseRate,
			Integer purchaseRate, Long quantity) {
		this.isBuyShop = isBuyShop;
		this.salesPrice =  new BigDecimal(salesPrice);
		this.skuSalesPrice = new BigDecimal(skuSalesPrice);
		this.lastPurchaseRate = new BigDecimal(lastPurchaseRate);
		this.purchaseRate = new BigDecimal(purchaseRate);
		this.quantity = new BigDecimal(quantity);
		//验证
		checkedResVal(this.salesPrice, "销售单价 ");
		checkedResVal(this.skuSalesPrice, "商品SKU单价");
		checkedResVal(this.lastPurchaseRate, "下级采购折扣率");
		checkedResVal(this.purchaseRate, "采购折扣率 ");
		checkedResVal(this.quantity, "购买数量 ");
	}
	
	/**
	 * 构造
	 * @param salesPrice 销售单价
	 * @param purchaseRate 分销商采购折扣率
	 * @param quantity 用户购买数量
	 */
	public CalculateDistribution(Long  salesPrice,Long skuSalesPrice,
			Integer purchaseRate, Long quantity,Long adjustPrice) {
		this.salesPrice =  new BigDecimal(salesPrice);
		this.skuSalesPrice = new BigDecimal(skuSalesPrice);
		this.purchaseRate = new BigDecimal(purchaseRate);
		this.quantity = new BigDecimal(quantity);
		this.adjustPrice =  new BigDecimal(adjustPrice);
		//验证
		checkedResVal(this.salesPrice, "销售单价 ");
		checkedResVal(this.skuSalesPrice, "商品SKU单价");
		checkedResVal(this.purchaseRate, "采购折扣率 ");
		checkedResVal(this.quantity, "购买数量 ");
		checkedResVal(this.adjustPrice, "调整金额 ");
	}
	
	
	
	/**
	 * 计算采购单价  
	 * 公式 ： 采购单价 = 销售价格*采购折扣率
	 * @Title: calculatePurchasePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月15日 上午11:49:20  
	 * @author dzq
	 */
	public Long calculatePurchasePrice() {
		if (1 == this.purchasePrice.compareTo(BigDecimal.ZERO)) {
			return this.purchasePrice.longValue();
		}
		this.purchasePrice = this.skuSalesPrice.multiply(BIGDECIMAL_100.subtract(this.purchaseRate).divide(BIGDECIMAL_100))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.purchasePrice, "采购单价 ");
		return this.purchasePrice.longValue();
	}
	
	/**
	 * 计算采购价格小计
	 * 公式 ：采购价格小计 = 采购单价*购买数量
	 * @Title: calculateSubPurchasePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月15日 上午11:56:33  
	 * @author dzq
	 */
	public Long calculateSubPurchasePrice(){
		this.subPurchasePrice = this.purchasePrice.multiply(this.quantity)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subPurchasePrice, "采购价格小计");
		return this.subPurchasePrice.longValue();
	}
	
	/**
	 * 计算销售价格小计
	 * 公式 ： 销售单价*购买数量
	 * @Title: calculateSubSalesPrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月15日 下午12:02:01  
	 * @author dzq
	 */
	public Long calculateSubSalesPrice(){
//		if (!isBuyShop && this.lastPurchaseRate.compareTo(BIGDECIMAL_100) != 0) {
//			this.salesPrice = this.skuSalesPrice.multiply(BIGDECIMAL_100.subtract(this.lastPurchaseRate).divide(BIGDECIMAL_100))
//					.setScale(0, BigDecimal.ROUND_HALF_UP);
//		}
		this.salesPrice = this.skuSalesPrice.multiply(BIGDECIMAL_100.subtract(this.lastPurchaseRate).divide(BIGDECIMAL_100))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		this.subSalesPrice = this.salesPrice.multiply(this.quantity)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subSalesPrice, "销售价格小计");
		return this.subSalesPrice.longValue();
	}
	
	/**
	 * 计算佣金小计
	 * 公式   
	 *    普通 ： 销售小计 - 采购小计  
	 * @Title: calculateSubPurchasePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月15日 上午11:56:33  
	 * @author dzq
	 * @param promotionCommission 
	 */
	public Long calculateSubProfitPricePrice(){
		this.subProfitPrice = this.subSalesPrice.subtract(this.subPurchasePrice).subtract(this.adjustPrice).setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subProfitPrice, "佣金小计");
		return this.subProfitPrice.longValue();
	}
	
	/**
	 * 分销商佣金 = 代理部分- 推广佣金
	 * @Title: calculateSubProfitPricePriceCommission 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param commission
	 * @return
	 * @date 2015年5月15日 下午3:02:40  
	 * @author dzq
	 */
	public Long calculateSubProfitPricePriceCommission(Long commission){
		BigDecimal promotionCommission  = new BigDecimal(commission);
		checkedResVal(promotionCommission, "推广佣金");
		this.subProfitPrice=this.subProfitPrice.subtract(promotionCommission)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subProfitPrice, "佣金小计");
		return this.subProfitPrice.longValue();
	}
	
	
	/**
	 * 单品佣金率:采购价格小计
	 * @author talo
	 */
	public Long calculateSubPurchasePriceReal () {
		this.subPurchasePrice = this.getRatePrice().multiply(BIGDECIMAL_100.subtract(this.purchaseRate).divide(BIGDECIMAL_100)).setScale(0, BigDecimal.ROUND_HALF_UP);;
		checkedResVal(this.subPurchasePrice, "采购价格小计");
		return this.subPurchasePrice.longValue();
	}
	
	/**
	 * 单品佣金率:销售价格小计
	 * @author talo
	 */
	public Long calculateSubSalesPriceReal () {
		this.subSalesPrice = this.getRatePrice().multiply(BIGDECIMAL_100.subtract(this.lastPurchaseRate).divide(BIGDECIMAL_100)).setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subSalesPrice, "销售价格小计");
		return this.subSalesPrice.longValue();
	}
	
	/**
	 * 
	 * 计算单品佣率商品在订单中占有的比重金额</br>
	 * 公式：</br>
	 * 		P(比重率) = (销售单价*购买数量)/订单总金额</br>
	 * 		M(比重金额) = P*订单实际金额</br>
	 * 
	 * @param totalItemPrice	
	 * 				订单总金额(不包含运费,打折和抵扣之前)
	 * @param realPrice		
	 * 				订单实际金额(不包含运费,打折和抵扣之后)
	 * @param itemPrice
	 * 				销售单价		
	 * @return
	 * @author talo
	 */
	public BigDecimal getRatePrice () { 
		//先乘法最后除法，确保精度正确
		return itemPrice.multiply(this.quantity).multiply(this.realPrice).divide(this.totalItemPrice,0, RoundingMode.HALF_UP);
	}
	
	/**
	 * 验证不能小于0
	 * @Title: _checkedResVal 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param checkVal
	 * @param name
	 * @date 2015年5月15日 下午12:55:40  
	 * @author dzq
	 */
	private static void checkedResVal(BigDecimal checkVal,String name){
		if(-1 == checkVal.compareTo(BigDecimal.ZERO)){
			StringBuilder exBuilder = new StringBuilder();
			exBuilder.append("不能小于0,当前").append(name).append(":").append(checkVal.longValue());
			throw new MoneyException(exBuilder.toString());
		}
	}
	
	@Override
	public String toString() {
		return "CalculateDistribution [销售单价=" + salesPrice
				+ ", sku单价=" + skuSalesPrice
				+ ", 采购折扣率=" + purchaseRate + "%, 采购单价="
				+ purchasePrice + ", 购买数量=" + quantity + ", 调整价格="
				+ adjustPrice + ", 销售价格小计=" + subSalesPrice
				+ ", 采购价格小计=" + subPurchasePrice
				+ ", 佣金小计=" + subProfitPrice + "]";
	}
	
	
	public static void main(String[] args) {
		checkedResVal(new BigDecimal(15000), "test");
		BigDecimal a =  new BigDecimal(2);
		BigDecimal b =  new BigDecimal(3);
		BigDecimal c =  new BigDecimal(3);
//		System.out.println(a.divide(b).multiply(c)); //error
		System.out.println(a.multiply(c).divide(b)); //先乘再除
		System.out.println(a.divide(b, 0, RoundingMode.HALF_UP).multiply(c));
		System.out.println(a.divide(b, 2, RoundingMode.HALF_UP).multiply(c));
		System.out.println(a.multiply(c).divide(b, 0, RoundingMode.HALF_UP));
		System.out.println(a.multiply(c).divide(b, 2, RoundingMode.HALF_UP));
		System.out.println(a.divide(b, 2, RoundingMode.HALF_UP).multiply(c).longValue()); 
	}
	
}
