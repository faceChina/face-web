package com.zjlp.face.web.server.trade.order.bussiness.salesorder;

import java.util.Date;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.trade.order.domain.PromotionDsetail;

public class PromotionDsetailFactory {

	public static PromotionDsetail createXfSong(String orderNo, MarketingActivityDetail detail, Date date){
		PromotionDsetail promotionDsetail = new PromotionDsetail();
		promotionDsetail.setOrderNo(orderNo);
		promotionDsetail.setDiscountFee(0L);
		promotionDsetail.setToolCode(detail.getToolId().toString());
		promotionDsetail.setActivityId(detail.getActivityId());
		promotionDsetail.setDetailId(detail.getId());
		promotionDsetail.setCreateTime(date);
		return promotionDsetail;
	}
	
	public static PromotionDsetail createDk(String orderNo, MarketingActivityDetail detail, Long discountFee, Date date){
		PromotionDsetail promotionDsetail = new PromotionDsetail();
		promotionDsetail.setOrderNo(orderNo);
		promotionDsetail.setDiscountFee(discountFee);
		promotionDsetail.setToolCode(String.valueOf(detail.getToolId()));
		promotionDsetail.setActivityId(detail.getActivityId());
		promotionDsetail.setDetailId(detail.getId());
		promotionDsetail.setCreateTime(date);
		return promotionDsetail;
	}
}
