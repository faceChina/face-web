package com.zjlp.face.web.server.trade.payment.domain;

import java.util.Date;

public class AlipayTransactionRecord {
    private Long id;

    private Long userId;

    private String transactionSerialNumber;

    private String aliSerialNumber;

    private Long cash;

    private Long fee;

    private Long trueFee;

    private Integer type;

    private Integer status;

    private Integer accountType;

    private String settleDate;

    private Date transactionTime;

    private String orderInfo;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTransactionSerialNumber() {
        return transactionSerialNumber;
    }

    public void setTransactionSerialNumber(String transactionSerialNumber) {
        this.transactionSerialNumber = transactionSerialNumber == null ? null : transactionSerialNumber.trim();
    }

    public String getAliSerialNumber() {
        return aliSerialNumber;
    }

    public void setAliSerialNumber(String aliSerialNumber) {
        this.aliSerialNumber = aliSerialNumber == null ? null : aliSerialNumber.trim();
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getTrueFee() {
        return trueFee;
    }

    public void setTrueFee(Long trueFee) {
        this.trueFee = trueFee;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo == null ? null : orderInfo.trim();
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