package com.zjlp.face.web.server.operation.redenvelope.domain;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.constants.Constants;

public class SendRedenvelopeRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4224279642101898549L;

	private Long id;
	//发包者userId
    private Long sendUserId;
    //支付通道（1钱包支付 2支付宝 3微信支付）
    private Integer payChannel;
    //红包金额（单位 分）
    private Long amount;
    //红包类型（1个人红包 2群拼手气红包 3群普通红包）
    private Integer type;
    //发送目标ID（用户ID或是群组ID）
    private String targetId;
    //红包个数
    private Integer number;
    //红包状态（0未支付 1已支付 2已过期）
    private Integer status;
    //反退金额（单位 分）
    private Long refundAmount;
    //支付时间
    private Date payTime;
    //回退时间
    private Date refundTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
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

	public boolean checkType() {
		return (type.equals(Constants.TYPE_PERSON) || type.equals(Constants.TYPE_GROUP_LUCK) || type.equals(Constants.TYPE_GROUP_NORMAL));
	}
}