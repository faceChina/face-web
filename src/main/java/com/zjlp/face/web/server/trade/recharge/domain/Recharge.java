package com.zjlp.face.web.server.trade.recharge.domain;

import java.io.Serializable;
import java.util.Date;

public class Recharge implements Serializable{
    
	private static final long serialVersionUID = -6098759980759697452L;

	//主键(充值号)
	private String rechargeNo;
	//充值用户帐号 （会员卡充值取会员卡ID）
    private String userAccount;
    //店铺编号
    private String shopNo;
    //卖家用户id
    private Long sellerId;
    //卖家用户id
    private Long userId;
    //充值账号类型  （1会员卡充值）
    private Integer accountType;
	//充值渠道（1.WAP 2.Android  3 IOS）
    private Integer rechargeChannel;
    //充值方式 (1.银行卡付款 2.钱包付款 3  微信支付 )
    private Integer rechargeWay;
    //付款银行卡号
    private Long bankCard;
    //付款微信OPENID
    private String openId;
    //交易流水号
    private String transactionSerialNumber;
    //充值金额(单位：分)
    private Long price;
    //赠送金额(单位：分)  
    private Long giftPrice;
    //优惠金额(单位：分)   价格使用优惠金额支付
    private Long discountPrice;
    //记录类型（1 正常充值 2 人工冲正(预留)）
    private Integer recordType;
    //充值状态（1 生成/未支付 2 正在充值/支付中 3 充值成功）
    private Integer status;
    //充值时间
    private Date rechargeTime;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //买家将获得的积分(单位：个)返回的积分，交易成功后才能获得
    private Long obtainIntegral;
    

    public Long getObtainIntegral() {
		return obtainIntegral;
	}

	public void setObtainIntegral(Long obtainIntegral) {
		this.obtainIntegral = obtainIntegral;
	}

	public String getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo == null ? null : rechargeNo.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo == null ? null : shopNo.trim();
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getRechargeChannel() {
        return rechargeChannel;
    }

    public void setRechargeChannel(Integer rechargeChannel) {
        this.rechargeChannel = rechargeChannel;
    }

    public Integer getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(Integer rechargeWay) {
        this.rechargeWay = rechargeWay;
    }

    public Long getBankCard() {
        return bankCard;
    }

    public void setBankCard(Long bankCard) {
        this.bankCard = bankCard;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(Long giftPrice) {
        this.giftPrice = giftPrice;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}