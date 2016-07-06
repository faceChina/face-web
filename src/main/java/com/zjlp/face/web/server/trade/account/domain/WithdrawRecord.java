package com.zjlp.face.web.server.trade.account.domain;

import java.io.Serializable;
import java.util.Date;

public class WithdrawRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2187375818765569580L;

	private Long id;

    private String remoteId;

    private Integer remoteType;

    private String seriNumber;

    private String bankSeriNumber;

    private String elecBkNo;

    private Long withdrawPrice;

    private Integer status;

    private String payBankCard;

    private String reciveBankCard;

    private String userName;

    private String reciveBankName;

    private String province;

    private String city;

    private String currencyType;

    private Integer serviceType;

    private String withdrawType;

    private Integer emergencyDegree;

    private String purpose;

    private String bankCode;

    private Long amountAfter;

    private String settleDate;

    private String withdrawInfo;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId == null ? null : remoteId.trim();
    }

    public Integer getRemoteType() {
        return remoteType;
    }

    public void setRemoteType(Integer remoteType) {
        this.remoteType = remoteType;
    }

    public String getSeriNumber() {
        return seriNumber;
    }

    public void setSeriNumber(String seriNumber) {
        this.seriNumber = seriNumber == null ? null : seriNumber.trim();
    }

    public String getBankSeriNumber() {
        return bankSeriNumber;
    }

    public void setBankSeriNumber(String bankSeriNumber) {
        this.bankSeriNumber = bankSeriNumber == null ? null : bankSeriNumber.trim();
    }

    public String getElecBkNo() {
        return elecBkNo;
    }

    public void setElecBkNo(String elecBkNo) {
        this.elecBkNo = elecBkNo == null ? null : elecBkNo.trim();
    }

    public Long getWithdrawPrice() {
        return withdrawPrice;
    }

    public void setWithdrawPrice(Long withdrawPrice) {
        this.withdrawPrice = withdrawPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayBankCard() {
        return payBankCard;
    }

    public void setPayBankCard(String payBankCard) {
        this.payBankCard = payBankCard == null ? null : payBankCard.trim();
    }

    public String getReciveBankCard() {
        return reciveBankCard;
    }

    public void setReciveBankCard(String reciveBankCard) {
        this.reciveBankCard = reciveBankCard == null ? null : reciveBankCard.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getReciveBankName() {
        return reciveBankName;
    }

    public void setReciveBankName(String reciveBankName) {
        this.reciveBankName = reciveBankName == null ? null : reciveBankName.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType == null ? null : currencyType.trim();
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType == null ? null : withdrawType.trim();
    }

    public Integer getEmergencyDegree() {
        return emergencyDegree;
    }

    public void setEmergencyDegree(Integer emergencyDegree) {
        this.emergencyDegree = emergencyDegree;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public Long getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(Long amountAfter) {
        this.amountAfter = amountAfter;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public String getWithdrawInfo() {
        return withdrawInfo;
    }

    public void setWithdrawInfo(String withdrawInfo) {
        this.withdrawInfo = withdrawInfo == null ? null : withdrawInfo.trim();
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