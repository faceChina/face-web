package com.zjlp.face.web.server.trade.payment.domain;

import java.io.Serializable;
import java.util.Date;

public class PaymentMemo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7951494187953215543L;

	private Long id;

    private Long userId;

    private Integer payType;

    private Integer type;

    private String transactionSerialNumber;

    private String shopNo;

    private Long totalPrice;

    private Long preferentialPrice;

    private Long bankCardPay;

    private Long walletPay;

    private Long commissionReferee;

    private Long commissionOnPerson;

    private Long commissionPlatform;

    private Long feePlatform;

    private Long feePayment;

    private Long businessesInterest;

    private Integer status;

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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTransactionSerialNumber() {
        return transactionSerialNumber;
    }

    public void setTransactionSerialNumber(String transactionSerialNumber) {
        this.transactionSerialNumber = transactionSerialNumber == null ? null : transactionSerialNumber.trim();
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(Long preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public Long getBankCardPay() {
        return bankCardPay;
    }

    public void setBankCardPay(Long bankCardPay) {
        this.bankCardPay = bankCardPay;
    }

    public Long getWalletPay() {
        return walletPay;
    }

    public void setWalletPay(Long walletPay) {
        this.walletPay = walletPay;
    }

    public Long getCommissionReferee() {
        return commissionReferee;
    }

    public void setCommissionReferee(Long commissionReferee) {
        this.commissionReferee = commissionReferee;
    }

    public Long getCommissionOnPerson() {
        return commissionOnPerson;
    }

    public void setCommissionOnPerson(Long commissionOnPerson) {
        this.commissionOnPerson = commissionOnPerson;
    }

    public Long getCommissionPlatform() {
        return commissionPlatform;
    }

    public void setCommissionPlatform(Long commissionPlatform) {
        this.commissionPlatform = commissionPlatform;
    }

    public Long getFeePlatform() {
        return feePlatform;
    }

    public void setFeePlatform(Long feePlatform) {
        this.feePlatform = feePlatform;
    }

    public Long getFeePayment() {
        return feePayment;
    }

    public void setFeePayment(Long feePayment) {
        this.feePayment = feePayment;
    }

    public Long getBusinessesInterest() {
        return businessesInterest;
    }

    public void setBusinessesInterest(Long businessesInterest) {
        this.businessesInterest = businessesInterest;
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