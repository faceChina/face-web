package com.zjlp.face.web.server.trade.coupon.domain;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

public class CouponSet implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1120299819286781864L;
	
	private Long id;
    //商户
    private Long sellerId;
    //店铺编号
    private String shopNo;
    //优惠券面值
    private Long faceValue;
    //面值类型（1.随机 2.固定值）
    private Integer faceValueType;
    //最低面值
    private Long minValue;
    //最高面值
    private Long maxValue;
    //发行量 
    private Long circulation;
    //发行量类型(1.张数 2.总面额)
    private Integer circulationType;
    //订单金额
    private Long orderPrice;
    //限领张数
    private Integer limitNumber;
    //是否可以合并使用(0.不可合并 1.可以合并)
    private Integer canJoin;
    //流通类型（1.指向性流通 2.任意性流通）
    private Integer currencyType;
    //状态:1已删除 2未删除
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

    public Long getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Long faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getFaceValueType() {
        return faceValueType;
    }

    public void setFaceValueType(Integer faceValueType) {
        this.faceValueType = faceValueType;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Long getCirculation() {
        return circulation;
    }

    public void setCirculation(Long circulation) {
        this.circulation = circulation;
    }

    public Integer getCirculationType() {
        return circulationType;
    }

    public void setCirculationType(Integer circulationType) {
        this.circulationType = circulationType;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }

    public Integer getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Integer canJoin) {
        this.canJoin = canJoin;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
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
    
    /**
	 * 
	 * @Title: getTimeStatus 
	 * @Description: 获取优惠状态
	 * @return
	 * @date 2015年9月24日 上午9:38:42  
	 * @author cbc
	 */
	public Integer getTimeStatus() {
		long currentTime = new Date().getTime();
		long beginTime = getEffectiveTime().getTime();
		long endTime = getEndTime().getTime();
		if (beginTime > currentTime && CouponSetDto.STATUS_NOT_DELETE.equals(getStatus())) {
			return CouponSetDto.NOT_BEGIN;
		}
		if (currentTime >= beginTime && currentTime <= endTime && CouponSetDto.STATUS_NOT_DELETE.equals(getStatus())) {
			return CouponSetDto.ON_GOING;
		}
		if (endTime < currentTime || CouponSetDto.STATUS_END.equals(getStatus())) {
			return CouponSetDto.INVALID;
		}
		return null;
	}

	/**
	 * 发行量数量必须要大于等于每人限领数量。发行数量最多不超过10000张<br>
	 * @Title: checkCirculation 
	 * @Description: 验证发行量合法性 
	 * @return
	 * @date 2015年9月24日 上午10:20:27  
	 * @author cbc
	 */
	public boolean checkCirculation() {
		return (getCirculation().longValue() >= getLimitNumber().longValue() && getCirculation().longValue() <= 10000L);
	}
	
	/**
	 * 
	 * @Title: checkFaceValue 
	 * @Description: 满减金额校验 满减金额一定要大于等于优惠券的面值 
	 * @return
	 * @date 2015年9月24日 上午10:22:27  
	 * @author cbc
	 */
	public boolean checkOrderPrice() {
		return getOrderPrice().compareTo(getFaceValue()) >= 0;
	}
	
	/**
	 * 
	 * @Title: checkLimitNumber 
	 * @Description: 每人限领优惠券校验
	 * @return
	 * @date 2015年9月24日 上午10:25:12  
	 * @author cbc
	 */
	public boolean checkLimitNumber() {
		return getLimitNumber().intValue() >= 1 && getLimitNumber().intValue() <= 10;
	}

	/**
	 * 
	 * @Title: checkFaceValue 
	 * @Description: 优惠券面额只能1到1000
	 * @return
	 * @date 2015年10月5日 下午4:38:42  
	 * @author cbc
	 */
	public boolean checkFaceValue() {
		return getFaceValue().longValue() >= 100 && getFaceValue().longValue() <= 100000;
	}
}