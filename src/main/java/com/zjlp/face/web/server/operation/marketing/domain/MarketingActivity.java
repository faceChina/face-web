package com.zjlp.face.web.server.operation.marketing.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 营销活动表
 * @ClassName: MarketingActivity 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月13日 下午4:45:23
 */
public class MarketingActivity implements Serializable {
	private static final long serialVersionUID = -8622801261817904433L;
	//主键
	private Long id;
	//归属类型
    private Integer remoteType;
    //归属
    private String remoteId;
    //营销工具ID
    private Long toolId;
    //活动状态:0:未开始 1.进行中 -1.已结束
    private Integer status;
    //是否时间限定 -1 没有， 1 有
    private Integer isTimeLimit;
    //开始时间
    private Date startTime;
    //结束时间: 标准模型结束时间9999年
    private Date endTime;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    public MarketingActivity(){}
    public MarketingActivity(Long id, Date startTime, Date endTime){
    	this.id = id;
    	this.startTime = startTime;
    	this.endTime = endTime;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRemoteType() {
		return remoteType;
	}
	public void setRemoteType(Integer remoteType) {
		this.remoteType = remoteType;
	}
	public String getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}
	public Long getToolId() {
		return toolId;
	}
	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsTimeLimit() {
		return isTimeLimit;
	}
	public void setIsTimeLimit(Integer isTimeLimit) {
		this.isTimeLimit = isTimeLimit;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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
	@Override
	public String toString() {
		return new StringBuilder("MarketingActivity [id=").append(id)
				.append(", remoteType=").append(remoteType).append(", remoteId=")
				.append(remoteId).append(", toolId=").append(toolId)
				.append(", status=").append(status).append(", isTimeLimit=")
				.append(isTimeLimit).append(", startTime=").append(startTime)
				.append(", endTime=").append(endTime)
				.append(", createTime=").append(createTime).append(", updateTime=")
				.append(updateTime).append("]").toString();
	}
	
}