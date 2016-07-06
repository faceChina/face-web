package com.zjlp.face.web.server.trade.coupon.domain;

import java.io.Serializable;
import java.util.Date;

public class Coupon implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4044584005116117917L;

	/** 优惠劵未使用 */
	public static final Integer COUPON_NOT_HAS_STATUS = 1;
	/** 优惠劵已使用 */
	public static final Integer COUPON_HAS_STATUS = 2;
	
	private Long id;
    //优惠券规则明细表主键
    private Long couponSetId;
    //用户
    private Long userId;
    //商户
    private Long sellerId;
    //店铺编码
    private String shopNo;
    //优惠券编码
    private String code;
    //领取id
    private String drawRemoteId;
    //领取处类型:1总店 2分店
    private Integer drawRemoteType;
    //优惠券面值
    private Long faceValue;
    //流通类型（1.指向性流通 2.任意性流通）
    private Integer currencyType;
    //订单金额
    private Long orderPrice;
    //是否可以合并使用(0.不可合并 1.可以合并)
    private Integer canJoin;
    //状态（1.未使用 2.已使用）
    private Integer status;
    //生效时间
    private Date effectiveTime;
    //失效时间
    private Date endTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponSetId() {
        return couponSetId;
    }

    public void setCouponSetId(Long couponSetId) {
        this.couponSetId = couponSetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDrawRemoteId() {
        return drawRemoteId;
    }

    public void setDrawRemoteId(String drawRemoteId) {
        this.drawRemoteId = drawRemoteId == null ? null : drawRemoteId.trim();
    }

    public Integer getDrawRemoteType() {
        return drawRemoteType;
    }

    public void setDrawRemoteType(Integer drawRemoteType) {
        this.drawRemoteType = drawRemoteType;
    }

    public Long getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Long faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Integer canJoin) {
        this.canJoin = canJoin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Coupon withSet(CouponSet couponSet) {
    	this.setCanJoin(couponSet.getCanJoin());
    	this.setCouponSetId(couponSet.getId());
    	this.setCurrencyType(couponSet.getCurrencyType());
    	this.setEffectiveTime(couponSet.getEffectiveTime());
    	this.setEndTime(couponSet.getEndTime());
    	this.setFaceValue(couponSet.getFaceValue());
    	this.setOrderPrice(couponSet.getOrderPrice());
    	this.setSellerId(couponSet.getSellerId());
    	this.setShopNo(couponSet.getShopNo());
    	return this;
    }
    
    /**
     * 
     * @Title: getValid 
     * @Description: 获取优惠券有效性 1为进行中 2为未开始 3已失效
     * @return
     * @date 2015年9月24日 下午5:22:11  
     * @author cbc
     */
    public Integer getValid() {
    	long currentTime = new Date().getTime();
		long beginTime = getEffectiveTime().getTime();
		long endTime = getEndTime().getTime();
    	if (beginTime > currentTime) {
			return 2;
		}
    	if (currentTime >= beginTime && currentTime <= endTime) {
			return 1;
		}
    	if (endTime < currentTime) {
			return 3;
		}
    	return null;
    }
}