package com.zjlp.face.web.server.trade.payment.domain;

import java.util.Date;

public class CommissionRecord {
	public static final Integer TYPE_4 = 4; // 总店分成
	public static final Integer TYPE_7 = 7;  //分店分成
	public static final Integer TOPAYTYPE_USER_PURSE = 1; //用户钱包
	public static final Integer TOPAYTYPE_WECHAT_PURSE = 2; //微信钱包
	public static final Integer IS_TO_ACCOUNT = 0;
	//主键
    private Long id;
    //订单交易流水号
    private String transactionSerialNumber;
    //订单号
    private String orderNo;
    //待转入金额的钱包编号
    private Long accountId;
    //金额（佣金，利益，手续费）分
    private Long commission;
    //是否到账 0未到账 1已到账
    private Integer isToAccount;
    //佣金到账类型:1到用户钱包 2到微信钱包
    private Integer isToType;
    //交易状态: 0 回退 1 正常
    private Integer orderStates;
    //1 商户利益
    private Integer type;
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

    public String getTransactionSerialNumber() {
        return transactionSerialNumber;
    }

    public void setTransactionSerialNumber(String transactionSerialNumber) {
        this.transactionSerialNumber = transactionSerialNumber == null ? null : transactionSerialNumber.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Integer getIsToAccount() {
        return isToAccount;
    }

    public void setIsToAccount(Integer isToAccount) {
        this.isToAccount = isToAccount;
    }

    public Integer getIsToType() {
        return isToType;
    }

    public void setIsToType(Integer isToType) {
        this.isToType = isToType;
    }

    public Integer getOrderStates() {
        return orderStates;
    }

    public void setOrderStates(Integer orderStates) {
        this.orderStates = orderStates;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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