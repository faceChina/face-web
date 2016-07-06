package com.zjlp.face.web.server.trade.order.domain;

import java.util.Date;
/**
 * 订单优惠详情表
 * @ClassName: PromotionDsetail 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年4月17日 下午5:22:58
 */
public class PromotionDsetail {
    private Long id;
    //订单号
    private String orderNo;
    //优惠金额
    private Long discountFee;
    //营销工具主键
    private String toolCode;
    //营销明细ID
    private Long detailId;
    //营销活动ID
    private Long activityId;
    //创建时间
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode == null ? null : toolCode.trim();
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}