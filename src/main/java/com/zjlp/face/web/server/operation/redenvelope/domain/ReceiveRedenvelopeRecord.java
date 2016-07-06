package com.zjlp.face.web.server.operation.redenvelope.domain;

import java.io.Serializable;
import java.util.Date;

public class ReceiveRedenvelopeRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7378107307134106255L;

	private Long id;
	//红包ID
    private Long redenvelopeId;
    //红包发布者
    private Long sendUserId;
    //红包领取者
    private Long receiveUserId;
    //红包金额
    private Long amount;
    //状态:1未领取 2已领取
    private Integer status;
    //领取时间
    private Date receiveTime;

    private Date createTime;

    private Date updateTime;
    
    public ReceiveRedenvelopeRecord() {
	}

    public ReceiveRedenvelopeRecord(SendRedenvelopeRecord sendRedenvelopeRecord) {
    	setRedenvelopeId(sendRedenvelopeRecord.getId());
    	setSendUserId(sendRedenvelopeRecord.getSendUserId());
    	setStatus(1);
    	setCreateTime(sendRedenvelopeRecord.getCreateTime());
    	setUpdateTime(sendRedenvelopeRecord.getUpdateTime());
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedenvelopeId() {
        return redenvelopeId;
    }

    public void setRedenvelopeId(Long redenvelopeId) {
        this.redenvelopeId = redenvelopeId;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
}