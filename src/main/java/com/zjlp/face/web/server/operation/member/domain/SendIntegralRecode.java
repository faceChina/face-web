package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 店家赠送积分记录
 * @ClassName: SendIntegralRecode 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月8日 上午10:52:44
 */
public class SendIntegralRecode implements Serializable {
	private static final long serialVersionUID = -8127751934050194046L;
	public static final Integer CANCEL = -1;
	public static final Integer UN_GET = 1;
	public static final Integer GETED = 2;
	//主键
    private Long id;
    //赠送者id
    private Long sellerId;
    //用户id
    private Long userId;
    //赠送积分个数
    private Long integral;
    //状态（-1.取消，1.未领取，2.已领取）
    private Integer status;
    //领取时间
    private Date claimTime;
    //累计赠送
    private Long statisticsIntegral;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    public SendIntegralRecode(){}
    
    public SendIntegralRecode(Long sellerId, Long userId){
    	this(sellerId, userId, null);
    }
    public SendIntegralRecode(Long sellerId, Long userId, Long integral){
    	this.sellerId = sellerId;
    	this.userId = userId;
    	this.integral = integral;
    }
    
    public static SendIntegralRecode newInstance(Long sellerId, Long userId, Long integral, Long statisticsIntegral, Integer status){
    	SendIntegralRecode instance = new SendIntegralRecode(sellerId, userId, integral);
    	instance.setStatus(1);
    	Date date = new Date();
    	instance.setStatus(status);
    	instance.setStatisticsIntegral(statisticsIntegral);
    	instance.setCreateTime(date);
    	instance.setUpdateTime(date);
    	return instance;
    }
    
    public static boolean isMustInput(SendIntegralRecode record){
    	return null != record && null != record.sellerId 
    			&& null != record.userId && null != record.integral;
    }
    
    public static SendIntegralRecode newInstance(Long sellerId, Long userId, Long integral){
    	return newInstance(sellerId, userId, integral, null, UN_GET);
    }
    
    public static SendIntegralRecode newInstance(Long sellerId, Long userId, Long integral, Long statisticsIntegral){
    	return newInstance(sellerId, userId, integral, statisticsIntegral, UN_GET);
    }
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Date getClaimTime() {
		return claimTime;
	}
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	public Long getStatisticsIntegral() {
		return statisticsIntegral;
	}
	public void setStatisticsIntegral(Long statisticsIntegral) {
		this.statisticsIntegral = statisticsIntegral;
	}
	@Override
	public String toString() {
		return new StringBuilder("SendIntegralRecode [id=").append(id)
				.append(", sellerId=").append(sellerId).append(", userId=")
				.append(userId).append(", integral=").append(integral)
				.append(", status=").append(status)
				.append(", claimTime=").append(claimTime).append(", createTime=")
				.append(createTime).append(", updateTime=").append(updateTime)
				.append("]").toString();
	}
	
   
}