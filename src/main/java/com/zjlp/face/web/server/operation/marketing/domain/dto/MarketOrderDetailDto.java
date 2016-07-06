package com.zjlp.face.web.server.operation.marketing.domain.dto;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;

public class MarketOrderDetailDto extends MarketingActivityDetail {

	private static final long serialVersionUID = 1L;
		
	//营销工具ID
    private Long toolId;
    //活动ID
    private Long activityId;
    //营销明细
    private Long detailId;
    //优惠价格
    private Long discountFee = 0L;
    //使用积分
    private Long integral = 0L;
    //赠送的积分数
    private Long giftIntegral = 0L;
    
	public Long getGiftIntegral() {
		return giftIntegral;
	}
	public void setGiftIntegral(Long giftIntegral) {
		this.giftIntegral = giftIntegral;
	}
	public Long getToolId() {
		return toolId;
	}
	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public Long getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(Long discountFee) {
		this.discountFee = discountFee;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	

}
