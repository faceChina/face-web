package com.zjlp.face.web.server.trade.payment.domain;

import java.io.Serializable;
import java.util.Date;

public class LakalaTransactionRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7326497239593135129L;
	//主键
	private Long id;
	//用户ID
    private Long userId;
    //交易流水号
    private String transactionSerialNumber;
    private String lakalaSerialNumber;
    //银行卡号
    private String bankCard;
    //银行名称
    private String bankName;
    //银行流水号
    private String bankSerialNumber;
    //金额
    private Long cash;
    //手续费
    private Long fee;
    //类型
    private Integer type;
    //状态：0.回退1.正常
    private Integer status;
    //商品归属类型：1.实物 2.话费（历史数据）3.彩票（历史数据）
    private Integer accountType;
    //清算日期
    private String settleDate;
    //系统编号
    private String systemCode;
    //交易时间
    private Date transactionTime;
    //手机
    private String cell;
    //银行代码
    private String bankCode;
    //贷记卡安全码
    private String cvv;
    //贷记卡有效期
    private String endTime;
    //支付类型
    private Integer payType;
    //订单描述
    private String orderInfo;
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

    public String getTransactionSerialNumber() {
        return transactionSerialNumber;
    }

    public void setTransactionSerialNumber(String transactionSerialNumber) {
        this.transactionSerialNumber = transactionSerialNumber == null ? null : transactionSerialNumber.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankSerialNumber() {
        return bankSerialNumber;
    }

    public void setBankSerialNumber(String bankSerialNumber) {
        this.bankSerialNumber = bankSerialNumber == null ? null : bankSerialNumber.trim();
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public String getLakalaSerialNumber() {
		return lakalaSerialNumber;
	}

	public void setLakalaSerialNumber(String lakalaSerialNumber) {
		this.lakalaSerialNumber = lakalaSerialNumber;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
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

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell == null ? null : cell.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv == null ? null : cvv.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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