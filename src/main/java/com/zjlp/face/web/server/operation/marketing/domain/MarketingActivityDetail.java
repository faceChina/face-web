package com.zjlp.face.web.server.operation.marketing.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 营销详情表
 * @ClassName: MarketingActivityDetail 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月13日 下午4:47:25
 */
public class MarketingActivityDetail implements Serializable {
	private static final long serialVersionUID = 8224414199444337906L;
	//主键
	private Long id;
	//营销工具
    private Long toolId;
    //活动ID
    private Long activityId;
    //名称
    private String name;
    //操作类型  1.折扣型  2.减价型  3.抵扣型  4.赠送
    private Integer type;
    //限制值：最小值
    private Integer maxVal;
    //限制值：最大值
    private Integer minVal;
    //条件值
    private Integer premiseVal;
    //步值（默认值 1）
    private Integer step;
    //累加值
    private Integer stepAccumulate;
    //结果值
    private Integer resultVal;
    //基础值
    private Integer baseVal;
    //抵扣单位  1.消费额  2.积分
    private Integer fromUnit;
    //转化单位  1.消费额  2.积分
    private Integer toUnit;
    //状态
    private Integer status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(Integer maxVal) {
		this.maxVal = maxVal;
	}
	public Integer getMinVal() {
		return minVal;
	}
	public void setMinVal(Integer minVal) {
		this.minVal = minVal;
	}
	public Integer getPremiseVal() {
		return premiseVal;
	}
	public void setPremiseVal(Integer premiseVal) {
		this.premiseVal = premiseVal;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getStepAccumulate() {
		return stepAccumulate;
	}
	public void setStepAccumulate(Integer stepAccumulate) {
		this.stepAccumulate = stepAccumulate;
	}
	public Integer getResultVal() {
		return resultVal;
	}
	public void setResultVal(Integer resultVal) {
		this.resultVal = resultVal;
	}
	public Integer getBaseVal() {
		return baseVal;
	}
	public void setBaseVal(Integer baseVal) {
		this.baseVal = baseVal;
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
	public Integer getFromUnit() {
		return fromUnit;
	}
	public void setFromUnit(Integer fromUnit) {
		this.fromUnit = fromUnit;
	}
	public Integer getToUnit() {
		return toUnit;
	}
	public void setToUnit(Integer toUnit) {
		this.toUnit = toUnit;
	}
	@Override
	public String toString() {
		return new StringBuilder("MarketingActivityDetail [id=").append(id)
				.append(", toolId=").append(toolId).append(", activityId=")
				.append(activityId).append(", name=").append(name)
				.append(", type=").append(type).append(", maxVal=").append(maxVal)
				.append(", minVal=").append(minVal).append(", premiseVal=")
				.append(premiseVal).append(", step=")
				.append(step).append(", stepAccumulate=").append(stepAccumulate)
				.append(", resultVal=").append(resultVal).append(", baseVal=")
				.append(baseVal).append(", status=").append(", fromUnit=").append(fromUnit)
				.append(", toUnit=").append(toUnit)
				.append(status).append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime).append("]").toString();
	}
	
}