package com.zjlp.face.web.server.operation.subbranch.domain;

import java.io.Serializable;
import java.util.Date;

public class OweRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3398208880186052614L;

	private Long id;
    
    private String transactionSerialNumber;

    private String orderNo;

    private Long purchaseOrderId;

    private Long userId;

    private Long oweUserId;

    private Long price;

    private Long payPrice;

    private Long owePrice;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOweUserId() {
        return oweUserId;
    }

    public void setOweUserId(Long oweUserId) {
        this.oweUserId = oweUserId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
    }

    public Long getOwePrice() {
        return owePrice;
    }

    public void setOwePrice(Long owePrice) {
        this.owePrice = owePrice;
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
}