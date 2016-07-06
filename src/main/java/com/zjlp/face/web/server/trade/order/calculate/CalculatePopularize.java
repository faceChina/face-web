package com.zjlp.face.web.server.trade.order.calculate;

import java.math.BigDecimal;

import com.zjlp.face.web.exception.ext.MoneyException;
/**
 * 推广采购单利益计算
 * @ClassName: CalculatePopularize 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月15日 下午2:18:41
 */
public class CalculatePopularize {

	// 佣金计算模式  1.利润小计=小计销售价格*佣金折扣率  2.利润小计=分销商佣金*佣金折扣率
	private Integer profitModel;
	//(销售/购买)单价
	private BigDecimal salesPrice = new BigDecimal(0);
	//佣金折扣率
	private BigDecimal commissionRate = new BigDecimal(0);
	//采购单价
	private BigDecimal purchasePrice = new BigDecimal(0); 
	//购买数量
	private BigDecimal quantity = new BigDecimal(0);
	//小计销售价格
	private BigDecimal subSalesPrice = new BigDecimal(0);
	//采购价格小计
	private BigDecimal subPurchasePrice = new BigDecimal(0);
	//(利润/佣金)小计
	private BigDecimal subProfitPrice =  new BigDecimal(0);
	//分销商利润
	private BigDecimal distributionProfitPrice =  new BigDecimal(0);
	
	
	public CalculatePopularize(Long salesPrice,
			Integer commissionRate, Long quantity) {
		this.salesPrice = new BigDecimal(salesPrice);
		this.commissionRate = new BigDecimal(commissionRate);
		this.quantity = new BigDecimal(quantity);
		this.profitModel = 1;//默认使用模式1
		//验证
		checkedResVal(this.salesPrice, "销售单价 ");
		checkedResVal(this.commissionRate, "采购折扣率 ");
		checkedResVal(this.quantity, "购买数量 ");
	}
	
	public CalculatePopularize(Long salesPrice,
			Integer commissionRate, Long quantity,Long distributionProfitPrice) {
		this.salesPrice = new BigDecimal(salesPrice);
		this.commissionRate = new BigDecimal(commissionRate);
		this.quantity = new BigDecimal(quantity);
		this.distributionProfitPrice = new BigDecimal(distributionProfitPrice);
		this.profitModel = 2;//传入分销商利润时，模式2
		//验证
		checkedResVal(this.salesPrice, "销售单价 ");
		checkedResVal(this.commissionRate, "采购折扣率 ");
		checkedResVal(this.quantity, "购买数量 ");
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
		this.subSalesPrice = this.salesPrice.multiply(this.quantity)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subSalesPrice, "销售价格小计");
		return this.subSalesPrice.longValue();
	}
	
	/**
	 * 计算佣金小计
	 * 公式   
	 *    1.利润小计=小计销售价格*佣金折扣率  
	 *    2.利润小计=分销商佣金*佣金折扣率
	 * @Title: calculateSubPurchasePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月15日 上午11:56:33  
	 * @author dzq
	 * @param promotionCommission 
	 */
	public Long calculateSubProfitPricePrice(){
		if (2 == this.profitModel.intValue()) {
			this.subProfitPrice = this.distributionProfitPrice.multiply(this.commissionRate.divide(new BigDecimal(100)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
		}else{
			this.subProfitPrice = this.subSalesPrice.multiply(this.commissionRate.divide(new BigDecimal(100)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
		}
		checkedResVal(this.subProfitPrice, "佣金小计");
		return this.subProfitPrice.longValue();
	}
	
	/**
	 * 计算采购价格小计
	 * 公式 ：采购价格小计 = 销售价格-利润
	 * @Title: calculateSubPurchasePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年5月15日 上午11:56:33  
	 * @author dzq
	 */
	public Long calculateSubPurchasePrice(){
		this.subPurchasePrice = this.subSalesPrice.subtract(subProfitPrice)
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		checkedResVal(this.subPurchasePrice, "采购价格小计");
		return this.subPurchasePrice.longValue();
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
	public static void checkedResVal(BigDecimal checkVal,String name){
		
		if(-1 == checkVal.compareTo(BigDecimal.ZERO)){
			StringBuilder exBuilder = new StringBuilder();
			exBuilder.append("不能小于0,当前").append(name).append(":").append(checkVal.longValue());
			throw new MoneyException(exBuilder.toString());
		}
	}
	
	@Override
	public String toString() {
		return "CalculatePopularize [佣金计算模式=" + profitModel
				+ ", 销售单价=" + salesPrice + ", 推广计算率="
				+ commissionRate + "%, 采购单价=" + purchasePrice
				+ ", 购买数量=" + quantity + ", 销售小计=" + subSalesPrice
				+ ", 采购小计=" + subPurchasePrice
				+ ", 利润小计=" + subProfitPrice
				+ ", 分销商推广前利润=" + distributionProfitPrice + "]";
	}
}
