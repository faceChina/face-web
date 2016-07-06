package com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.Assert;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.AbstractOIDiscountHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderGenerate;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;

/**
 * 消费送积分优惠
 * @ClassName: IntegralHandler 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月23日 上午10:04:22
 */
public class IntegralSendHandler extends AbstractOIDiscountHandler {
	
	//消费送积分的规则
	private BigDecimal premiseVal;
	private BigDecimal resultVal;
	public IntegralSendHandler(MarketingActivityDetail deatil) {
		if (null != deatil) {
			log.writeLog("消费送积分优惠活动开启！");
			this.premiseVal = BigDecimal.valueOf(deatil.getPremiseVal());
			this.resultVal = BigDecimal.valueOf(deatil.getResultVal());
		}
	}
	
	@Override
	protected Object excute(Object param) {
		
		log.writeLog("开始消费送积分优惠活动！");
		log.writeLog("当前匹配的消费送积分规则为消费 ：", premiseVal, "金额（分）赠送", resultVal, "个积分");
		
		Assert.isTrue(param instanceof OrderGenerate, "param must be an OrderGenerate.");
		
		OrderGenerate order = (OrderGenerate) param;
		List<OrderItemDetail> detailList = order.getItemDetailList();
		
		BigDecimal consumPrice = BigDecimal.ZERO;
		BigDecimal discountPrice = null;
		for (OrderItemDetail detail : detailList) {
			if (null == detail) continue;
			discountPrice = BigDecimal.valueOf(detail.getCachePrice());
			consumPrice = consumPrice.add(discountPrice);
		}
		
		Long giftIntegral = consumPrice.divide(premiseVal).multiply(resultVal).longValue();
		log.writeLog("当前消费金额为：", consumPrice, "分，赠送积分", giftIntegral, "个");
		
		order.getSalesOrder().setObtainIntegral(giftIntegral);
//		order.getSalesOrder().setRealIntegral(L_ZERO);
		
		log.writeLog("消费送积分优惠活动已结束！");
		
		return param;
	}
	
	@Override
	protected boolean check(Object param) {
		boolean cando = super.check(param);
		if (null == premiseVal || null == resultVal) {
			cando = false;
			log.writeLog("满多少值未设置 || 送多少值未设置 ，该订单不进行消费送积分活动。");
		}
		if (null == premiseVal || 0 > premiseVal.intValue()
				|| null == resultVal || 0 > resultVal.intValue()) {
			cando = false;
			log.writeLog("该店铺消费送积分规则不完整");
		}
		return cando;
	}

	@Override
	public String toString() {
		return "IntegralSendHandler [消费送积分优惠]";
	}
	
}
