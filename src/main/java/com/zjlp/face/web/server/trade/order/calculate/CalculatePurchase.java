package com.zjlp.face.web.server.trade.order.calculate;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.util.PrintLog;


/**
 * 分销佣金分成计算
 * 
 * <p>
 * 
 * 公式：<br>
 * 
 * 采购价 = 实付款   * （1 - 采购折扣率）<br>
 * 
 * 销售价 = 实付款   * （1 - 下级折扣率）<br>
 * 
 * 利    润 = 销售价 - 采购价<br>
 * 
 * @ClassName: CalculatePurchase 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月28日 下午1:38:15
 */
public final class CalculatePurchase {
	
	private final static BigDecimal DEFAULT_LESS_PRICE = new BigDecimal(10);
	private final static BigDecimal B_100 = new BigDecimal(100);
	private PrintLog log = new PrintLog("OrderInfoLog");
	//折扣率
	private BigDecimal rate;
	//下级折扣率
	private BigDecimal nextRate;
	//实付款
	private BigDecimal payPrice;
	//利润
	private long profit;
	//采购价
	private long purchasePrice;
	//销售价
	private long salesPrice;
	
	public CalculatePurchase(BigDecimal payPrice, BigDecimal rate, BigDecimal nextRate) {
		String minPriceString = PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE");
		BigDecimal minPrice = StringUtils.isBlank(minPriceString) ? DEFAULT_LESS_PRICE : new BigDecimal(minPriceString);
		AssertUtil.isTrue(null != rate && rate.compareTo(BigDecimal.ZERO) >= 0 && rate.compareTo(B_100) <= 0, "折扣率设置不合法");
		AssertUtil.isTrue(null != nextRate && nextRate.compareTo(BigDecimal.ZERO) >= 0 && nextRate.compareTo(B_100) <= 0 && nextRate.compareTo(rate) <= 0, "下级折扣率设置不合法");
		AssertUtil.isTrue(null != payPrice && payPrice.compareTo(minPrice) >= 0, "实付款设置不合法");
		this.payPrice = payPrice;
		this.rate = rate;
		this.nextRate = nextRate;
	}
	
	public CalculatePurchase(BigDecimal payPrice, BigDecimal rate) {
		this(payPrice, rate, BigDecimal.ZERO);
	}
	
	public void calculate(){
//		System.out.println("【计算采购单价格开始】实付款："+this.payPrice+"， 折扣率："+this.rate+ "， 下级折扣率："+this.nextRate);
		log.writeLog("【计算采购单价格开始】实付款：", this.payPrice, "， 折扣率：", this.rate, "， 下级折扣率：", this.nextRate);
		BigDecimal purchasePrice = this.calPurchasePrice(payPrice, rate);
		BigDecimal salesPrice = this.calSalesPrice(payPrice, nextRate);
		BigDecimal profit = this.calProfit(purchasePrice, salesPrice);
		this.purchasePrice = purchasePrice.longValue();
		this.salesPrice = salesPrice.longValue();
		this.profit = profit.longValue();
		log.writeLog("【计算采购单价格结束】采购价：", this.purchasePrice, "， 销售价：", this.salesPrice, "， 利润小计：", this.profit);
//		System.out.println("【计算采购单价格结束】采购价："+this.purchasePrice+"， 销售价："+this.salesPrice+"， 利润小计："+this.profit);
	}
	
	//采购价 = 实付款   * （1 - 采购折扣率）
	private BigDecimal calPurchasePrice(BigDecimal payPrice, BigDecimal rate){
		return payPrice.multiply(B_100.subtract(rate).divide(B_100))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
	}
	
	//销售价 = 实付款   * （1 - 下级折扣率）
	private BigDecimal calSalesPrice(BigDecimal payPrice, BigDecimal nextRate){
		return payPrice.multiply(B_100.subtract(nextRate).divide(B_100))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
	}
	
	//利    润 = 销售价 - 采购价
	private BigDecimal calProfit(BigDecimal purchasePrice, BigDecimal salesPrice){
		AssertUtil.isTrue(salesPrice.compareTo(purchasePrice) >= 0, "销售价 < 采购价，产生负利润，结算失败.");
		return salesPrice.subtract(purchasePrice).setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public long getProfit() {
		return profit;
	}

	public long getPurchasePrice() {
		return purchasePrice;
	}

	public long getSalesPrice() {
		return salesPrice;
	}
	
	public static void main(String[] args) {
		CalculatePurchase t = new CalculatePurchase(BigDecimal.valueOf(6800), BigDecimal.valueOf(50), BigDecimal.valueOf(25));
		t.calculate();
		System.out.println(t.getPurchasePrice());
		System.out.println(t.getSalesPrice());
		System.out.println(t.getProfit());
	}
	
}
