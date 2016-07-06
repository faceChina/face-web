package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate;

import java.util.Date;

import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.util.PrintLog;

public class OrderItemDetail {
	private PrintLog log = new PrintLog(getClass());
	protected OrderItem orderItem;
	protected Good good;
	protected GoodSku goodSku;
	protected Classification classification;
	protected Long quantity = 0L;
	protected Long discountPrice = 0L;
	protected String shareId;
	//商品价格
	protected Long goodPrice;
	//打折减免的价格
	protected Long memberDiscountPrice;
	//优惠券抵价的价格
	protected Long couponPrice;
	//抵价的积分个数
	protected Long integral;
	//积分抵价的价格
	protected Long integralPrice;
	protected Long integralRate;
	
	//计算中间价格
	protected Long cachePrice = 0L;
	
	public OrderItem generate(Date createTime){
		
		//折扣值没有设置的时候处理价格
		this.dealNullPrice();
		
		if (null == orderItem) {
			log.writeLog("开始生成订单细项.");
			orderItem = new OrderItem();
			orderItem.setGoodId(goodSku.getGoodId());
			orderItem.setGoodSkuId(goodSku.getId());
			orderItem.setGoodName(goodSku.getName());
			orderItem.setClassificationId(classification.getId());
			orderItem.setSkuPicturePath(goodSku.getPicturePath());
			orderItem.setAdjustPrice(0l);
			orderItem.setPrice(goodSku.getSalesPrice());
			orderItem.setSkuSalesPrice(goodSku.getSalesPrice());
			orderItem.setDiscountPrice(discountPrice);
			orderItem.setQuantity(quantity);
			orderItem.setTotalPrice(cachePrice);
			orderItem.setSkuPropertiesName(goodSku.getSkuPropertiesName());
			orderItem.setCreateTime(createTime);
			orderItem.setUpdateTime(createTime);
			orderItem.setShareId(shareId);//设置推广号
			orderItem.setStatus(Constants.STATUS_WAIT);
			log.writeLog("订单细项完成：", String.valueOf(orderItem));
		}
		return orderItem;
	}
	
	/**
	 * 折扣值没有设置的时候处理价格
	 * @Title: dealNullPrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年10月9日 下午5:08:05  
	 * @author lys
	 */
	private void dealNullPrice(){
		if (null == discountPrice || discountPrice.equals(0L)) {
			discountPrice = goodSku.getSalesPrice();
		}
	}
	
	public OrderItemDetail(Long quantity){
		this.quantity = quantity;
	}
	
	public OrderItemDetail withGood(Good good, GoodSku goodSku){
		Assert.notNull(good);
		Assert.notNull(goodSku);
		this.good = good;
		this.goodSku = goodSku;
		this.quantity = quantity < goodSku.getStock() ? quantity : goodSku.getStock();
		if(0 == quantity || Constants.GOOD_STATUS_DEFAULT.intValue() != goodSku.getStatus()){
			throw new OrderException("商品已售罄");
		}
		//订单细项的商品价格计算
		goodPrice = CalculateUtils.get(goodSku.getSalesPrice(), quantity);
		return this;
	}
	
	public OrderItemDetail withClassification(Classification classification){
		Assert.notNull(classification);
		this.classification = classification;
		return this;
	}
	
	public Long getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(Long goodPrice) {
		this.goodPrice = goodPrice;
	}
	public OrderItemDetail withShareId(String shareId){
		this.shareId = shareId;
		return this;
	}
	public OrderItemDetail withDiscountPrice(Long discountPrice){
		this.discountPrice = discountPrice;
		return this;
	}
	public Long getDiscountPrice() {
		return discountPrice;
	}
	public GoodSku getGoodSku() {
		return goodSku;
	}
	public void setGoodSku(GoodSku goodSku) {
		this.goodSku = goodSku;
	}
	public Long getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(Long couponPrice) {
		this.couponPrice = couponPrice;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Long getIntegralPrice() {
		return integralPrice;
	}

	public void setIntegralPrice(Long integralPrice) {
		this.integralPrice = integralPrice;
	}

	public Long getIntegralRate() {
		return integralRate;
	}

	public void setIntegralRate(Long integralRate) {
		this.integralRate = integralRate;
	}
	public Long getMemberDiscountPrice() {
		return memberDiscountPrice;
	}
	public void setMemberDiscountPrice(Long memberDiscountPrice) {
		this.memberDiscountPrice = memberDiscountPrice;
	}
	public Long getQuantity() {
		return quantity;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public Long getCachePrice() {
		if (null == cachePrice || cachePrice.equals(0L)) {
			cachePrice = CalculateUtils.get(goodSku.getSalesPrice(), quantity);
		}
		return cachePrice;
	}
	public void setCachePrice(Long cachePrice) {
		this.cachePrice = cachePrice;
	}
	
}
