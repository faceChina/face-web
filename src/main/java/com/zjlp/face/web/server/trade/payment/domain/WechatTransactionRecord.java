package com.zjlp.face.web.server.trade.payment.domain;

import java.util.Date;

public class WechatTransactionRecord {
    //主键
	private Long id;
	//用户ID
    private Long userId;
    //微信OPEN_ID
    private String openId;
    //交易流水号
    private String transactionSerialNumber;
    //微信流水号
    private String wechatSerialNumber;
    //金额
    private Long cash;
    //类型
    private Integer type;
    //0.回退 1.正常
    private Integer status;
    //商品归属类型1 实物  
    private Integer accountType;
    //交易时间
    private Date transactionTime;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getTransactionSerialNumber() {
        return transactionSerialNumber;
    }

    public void setTransactionSerialNumber(String transactionSerialNumber) {
        this.transactionSerialNumber = transactionSerialNumber == null ? null : transactionSerialNumber.trim();
    }

    public String getWechatSerialNumber() {
        return wechatSerialNumber;
    }

    public void setWechatSerialNumber(String wechatSerialNumber) {
        this.wechatSerialNumber = wechatSerialNumber == null ? null : wechatSerialNumber.trim();
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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