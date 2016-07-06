package com.zjlp.face.web.server.operation.marketing.domain.vo;

public class MarketingActivityDetailVo {

	//卖家ID
    private Long sellerId;
    
    //买家在该店铺当前积分个数(单位：个)
    private Long userIntegral;
    //消费送积分条件 (单位：分)
    private Long giftCondition;
    //消费送积分结果 (单位：个)
    private Long giftResult;
    //积分抵价条件 (单位：个)
    private Long deductionCondition;
    //积分抵价结果(单位：分)
    private Long deductionResult;
    //积分占价格的最大比例(单位：百分比)
    private Integer deductionMaxrete;
    
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getUserIntegral() {
		return userIntegral;
	}
	public void setUserIntegral(Long userIntegral) {
		this.userIntegral = userIntegral;
	}
	public Long getGiftCondition() {
		return giftCondition;
	}
	public void setGiftCondition(Long giftCondition) {
		this.giftCondition = giftCondition;
	}
	public Long getGiftResult() {
		return giftResult;
	}
	public void setGiftResult(Long giftResult) {
		this.giftResult = giftResult;
	}
	public Long getDeductionCondition() {
		return deductionCondition;
	}
	public void setDeductionCondition(Long deductionCondition) {
		this.deductionCondition = deductionCondition;
	}
	public Long getDeductionResult() {
		return deductionResult;
	}
	public void setDeductionResult(Long deductionResult) {
		this.deductionResult = deductionResult;
	}
	public Integer getDeductionMaxrete() {
		return deductionMaxrete;
	}
	public void setDeductionMaxrete(Integer deductionMaxrete) {
		this.deductionMaxrete = deductionMaxrete;
	}
  
}
