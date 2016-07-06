package com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.AbstractOIDiscountHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderGenerate;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice.SupportRule;

/**
 * 优惠券活动处理类（不可合并）
 * @ClassName: CouponHandler 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月23日 上午10:03:22
 */
public class CouponHandler extends AbstractOIDiscountHandler {

	//优惠券
	private Coupon coupon;
	//资金分配规则
	private SupportRule supportRule;
	
	public CouponHandler(Coupon coupon, SupportRule supportRule) {
		this.coupon = coupon;
		this.supportRule = supportRule;
	}
	
	@Override
	protected Object excute(Object param) {
		
		log.writeLog("开始处理优惠券活动处理类（不可合并）：优惠券面额为：", coupon.getFaceValue());
		
		Assert.isTrue(param instanceof OrderGenerate, "param must be an OrderGenerate.");
		
		OrderGenerate order = (OrderGenerate) param;
		List<OrderItemDetail> detailList = order.getItemDetailList();
		
		//初始化抵价规则实例
		this.initSupportRule(detailList, this.coupon);
		
		//抵价计算
		supportRule.excute();
		
		//处理订单价格以及优惠券抵价情况
		this.dealOrderPrice(detailList, supportRule.getPriceMap(), supportRule.getSupportMap());
		
		log.writeLog("处理优惠券活动处理类（不可合并）结束。");
		
		return param;
	}
	
	private void dealOrderPrice(List<OrderItemDetail> detailList,
			Map<Long, BigDecimal> priceMap, Map<Long, BigDecimal> supportMap) {
		
		Long skuId = null;
		BigDecimal discount = null;
		BigDecimal couponPrice = null;
		for (OrderItemDetail detail : detailList) {
			
			if (null == detail) continue;
			log.writeLog("Sku[Id=", skuId, "] 优惠券抵价前的价格为：", detail.getCachePrice());
			skuId = detail.getGoodSku().getId();
			discount = priceMap.get(skuId);
			couponPrice = supportMap.get(skuId);
			
//			detail.withDiscountPrice(discount.longValue());
			detail.setCachePrice(discount.longValue());
			detail.setCouponPrice(couponPrice.longValue());
			log.writeLog("Sku[Id=", skuId, "] 优惠券抵价后的价格为：", detail.getCachePrice(),
					", 使用优惠券价格为：", detail.getCouponPrice());
		}
		
	}

	private void initSupportRule(List<OrderItemDetail> detailList,
			Coupon coupon) {
		
		this.supportRule.withSupportPrice(coupon.getFaceValue());
		
		Long subDiscount = null;
		for (OrderItemDetail detail : detailList) {
			if (null == detail) continue;
			Assert.notNull(detail.getGoodSku());
			Assert.notNull(detail.getCachePrice());
			
			Long discountPrice = detail.getCachePrice();
			if(discountPrice.equals(0L)){
				discountPrice = detail.getGoodSku().getSalesPrice();
				subDiscount = CalculateUtils.get(discountPrice, detail.getQuantity());
			} else {
				subDiscount = discountPrice;
			}
			
			supportRule.addPriceById(detail.getGoodSku().getId(), subDiscount);
		}
	}

	@Override
	protected boolean check(Object param) {
		boolean cando = super.check(param);
		//资金分配规则
		if (null == coupon) {
			cando = false;
			log.writeLog("没有优惠券，该单不享受优惠券抵价活动。");
		}
		if (null == supportRule) {
			cando = false;
			log.writeLog("没有设置抵价规则，该单不享受优惠券抵价活动。");
		}
		return cando;
	}

	@Override
	public String toString() {
		return "CouponHandler [优惠券活动处理类]";
	}

}
