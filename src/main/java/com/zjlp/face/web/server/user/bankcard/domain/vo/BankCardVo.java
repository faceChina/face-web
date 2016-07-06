package com.zjlp.face.web.server.user.bankcard.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.web.constants.Bank;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

public class BankCardVo implements Serializable {

	private static final long serialVersionUID = -5060512380526579218L;

	//主键
	private Long id;
	//用户id
	private Long userId;
	// 银行编码
	private String bankCode;
	// 银行卡号
	private String bankCard;
	// 银行名称
	private String bankName;
	// 手机
	private String cell;
	// 用户名
	private String name;
	// 类型： 3、贷记卡 2、储值卡
	private Integer bankType;
	// 用途：1、支付 2、结算
	private Integer type;
	// 贷记卡有效期
	private String endTime;
	// 贷记卡安全码
	private String cvv;
	// 身份证
	private String identity;
	// 是否为默认银行卡 （0 否 1是）
	private Integer isDefault;
	//自身的集合对象
	private List<BankCardVo> cardList;

	public BankCardVo() {
	}

	public BankCardVo(BankCard bankCard) {
		this.setId(bankCard.getId());
		if (null != bankCard.getRemoteId()) {
			this.setUserId(Long.valueOf(bankCard.getRemoteId()));
		}
		this.setBankCode(bankCard.getBankCode());
		this.setBankCard(bankCard.getBankCard());
		this.setBankName(bankCard.getBankName());
		this.setBankType(bankCard.getBankType());
		this.setCell(bankCard.getCell());
		this.setName(bankCard.getName());
		this.setType(bankCard.getType());
		this.setEndTime(bankCard.getEndTime());
		this.setCvv(bankCard.getCvv());
		this.setIdentity(bankCard.getIdentity());
		this.setIsDefault(bankCard.getIsDefault());
	}

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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		if (null == bankCard)
			return;
		this.bankCard = ConstantsMethod.replaceToHide(bankCard, 3, 4);
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		if (null == cell)
			return;
		this.cell = ConstantsMethod.replaceToHide(cell, 3, 3);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		if (null == identity)
			return;
		this.identity = ConstantsMethod.replaceToHide(identity, 3, 3);
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getBankTypeString() {
		if (null == this.bankType) {
			return null;
		}
		return BankCard.TYPE_DEBIT.equals(this.bankType) ? "借记卡" : "贷记卡";
	}

	public String getAbbreviation() {
		if (null == this.bankCard) {
			return null;
		}
		return new StringBuilder("尾号")
				.append(this.getLastFourChar()).append(" ")
				.append(this.getBankTypeString()).toString();
	}
	public String getLastFourChar() {
		if(bankCard!=null && bankCard.length()>4){
			return bankCard.substring(bankCard.length()-4);
		}
		return bankCard;
	}
	public List<BankCardVo> getCardList() {
		if (null == cardList) {
			cardList = new ArrayList<BankCardVo>();
		}
		return cardList;
	}
	public void setCardList(List<BankCardVo> cardList) {
		this.cardList = cardList;
	}
	public boolean getIsWithdrawCard(){
		return Bank.containWithdrawBank(bankCode) && bankType != null && bankType == 2;
	}
	@Override
	public String toString() {
		return "BankCardVo [id=" + id + ", userId=" + userId + ", bankCode=" + bankCode + ", bankCard=" + bankCard + ", bankName=" + bankName + ", cell=" + cell + ", name=" + name + ", bankType=" + bankType + ", type=" + type + ", endTime=" + endTime + ", cvv=" + cvv + ", identity=" + identity + ", isDefault=" + isDefault + ", cardList=" + cardList + "]";
	}
	
}
