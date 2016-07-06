package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 消费额调整记录
 * 
 * @ClassName: ConsumptionAdjustmentRecord
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年4月9日 下午5:46:44
 */
public class ConsumptionAdjustmentRecord implements Serializable {

	private static final long serialVersionUID = 5129524105734894139L;
	// 主键
	private Long id;
	// 会员卡主键
	private Long memberCardId;
	// 调整额
	private Long adjustmentAmount;
	// 调增或调减 1:增 2:减
	private Integer type;
	// 积分变更
	private Long integralAmout;
	// 创建时间
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(Long memberCardId) {
		this.memberCardId = memberCardId;
	}

	public Long getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(Long adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getIntegralAmout() {
		return integralAmout;
	}

	public void setIntegralAmout(Long integralAmout) {
		this.integralAmout = integralAmout;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return new StringBuilder("ConsumptionAdjustmentRecord [id=").append(id)
				.append(", memberCardId=").append(memberCardId)
				.append(", adjustmentAmount=").append(adjustmentAmount)
				.append(", type=").append(type).append(", integralAmout=")
				.append(integralAmout).append(", createTime=")
				.append(createTime).append("]").toString();
	}

}