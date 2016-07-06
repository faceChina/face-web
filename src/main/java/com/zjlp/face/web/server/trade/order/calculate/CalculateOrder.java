package com.zjlp.face.web.server.trade.order.calculate;

import java.math.BigDecimal;
/**
 * 订单价格计算类
 * @ClassName: CalculateOrder 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月15日 下午5:55:26
 */
public class CalculateOrder {
	
	/** 邮费价格(单位：分) */ 
	private BigDecimal postPrice = new BigDecimal(0);
	
	/** 优惠价格(单位：分)  */ 
	private BigDecimal discountPrice = new BigDecimal(0);
	
	/** 人工调整价格(单位：分) */
	private BigDecimal adjustPrice = new BigDecimal(0);
	
	/** 订单子项总价格(单位：分)
	 *  公式 [
	 *    	P = 单价*折扣*数量 
	 *    	totalItemPrice = (P1+P2+....Pn) 
	 *  ]
	 */
	private BigDecimal totalItemPrice;
	
	/** 订单结算总价格(单位：分)  */
	private BigDecimal totalPrice = new BigDecimal(0);
	
	/** 订单价格调整时用于获取数据库中已存在的调整价格 */
	private BigDecimal oldAdjustPrice = new BigDecimal(0);
	
	/**
	 * 构造：只有子项价格
	 * @Title:  
	 * @Description:
	 * @param totalItemPrice
	 */
	public CalculateOrder(Long totalItemPrice) {
		if ( null ==totalItemPrice || 0L> totalItemPrice.longValue() ) {
			totalItemPrice = 0L;
		}
		this.totalItemPrice = new BigDecimal(totalItemPrice);
	}
	
	/**
	 * 构造：有子项价格同时有邮费
	 * @Title:  
	 * @Description:
	 * @param post
	 * @param totalItemPrice
	 */
	public CalculateOrder(Long totalItemPrice,Long postPrice) {
		if ( null ==totalItemPrice || 0L> totalItemPrice.longValue() ) {
			totalItemPrice = 0L;
		}
		if (null ==postPrice || 0L > postPrice.longValue() ) {
			postPrice = 0L;
		}
		this.postPrice = new BigDecimal(postPrice);
		this.totalItemPrice = new BigDecimal(totalItemPrice);
	}
	
	

	/**
	 * 构造：有子项价格同时有邮费及优惠价格
	 * @Title:  
	 * @Description:
	 * @param post
	 * @param totalItemPrice
	 */
	public CalculateOrder(Long totalItemPrice,Long postPrice, Long discountPrice) {
		if ( null ==totalItemPrice || 0L> totalItemPrice.longValue() ) {
			totalItemPrice = 0L;
		}
		if (null ==postPrice || 0L > postPrice.longValue() ) {
			postPrice = 0L;
		}
		if (null == discountPrice || 0L > discountPrice.longValue() ) {
			discountPrice = 0L;
		}
		this.discountPrice = new BigDecimal(discountPrice);
		this.postPrice = new BigDecimal(postPrice);
		this.totalItemPrice = new BigDecimal(totalItemPrice);
	}
	
	

	public CalculateOrder(Long totalItemPrice, Long postPrice,
			Long discountPrice , Long adjustPrice) {
		if ( null ==totalItemPrice || 0L> totalItemPrice.longValue() ) {
			totalItemPrice = 0L;
		}
		if (null ==postPrice || 0L > postPrice.longValue() ) {
			postPrice = 0L;
		}
		if (null == discountPrice || 0L > discountPrice.longValue() ) {
			discountPrice = 0L;
		}
		if (null == adjustPrice) {
			adjustPrice = 0L;
		}
		this.discountPrice = new BigDecimal(discountPrice);
		this.postPrice = new BigDecimal(postPrice);
		this.totalItemPrice = new BigDecimal(totalItemPrice);
		this.adjustPrice = new BigDecimal(adjustPrice);
	}

	/**
	 * 计算订单价格
	 * 公式 ： 订单子项总价格+邮费-优惠价格+人工调整金额
	 * @Title: calculate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月16日 下午6:02:45  
	 * @author dzq
	 * @throws Exception 
	 */
	public Long calculate() throws Exception{
		this.totalPrice = this.totalItemPrice
					.add(this.postPrice)
					.subtract(this.discountPrice)
					.add(this.adjustPrice);
		if(-1 == totalPrice.compareTo(BigDecimal.ZERO)) throw new Exception("订单价格不能小于0!"+this.toString());//利润小于0报错
		return totalPrice.longValue();
	}

	@Override
	public String toString() {
		return new StringBuffer().append(" 订单细项总价[").append(totalItemPrice).append("]").append(" + ")
					.append(" 邮费[").append(postPrice).append("]").append("-")
					.append(" 优惠价格[").append(discountPrice).append("]").append(" + ")
					.append(" 手工调整金额[").append(adjustPrice).append("]").append(" = ")
					.append(" 应付金额[").append(totalPrice).append("]").toString();
	}
	
	public static void main(String[] args) {
		try {
			CalculateOrder calculateOrder = new CalculateOrder(10000L, 1500L, 1000L);
			calculateOrder.calculate();
			System.out.println(calculateOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
