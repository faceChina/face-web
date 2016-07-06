package com.zjlp.face.web.appvo;

import java.io.Serializable;

public class AssBankCardVo implements Serializable{
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -828120358049677182L;
	//主键
	private Long id;
	//是否为默认银行卡 （0 否 1是）
    private Integer isDefault;
    //银行名称
    private String bankName;
    //银行卡号(尾号)
    private String bankCard;
    //类型： 2.借记卡 3.信用卡
    private Integer bankType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		if (bankCard.length() > 4) {
			this.bankCard =  bankCard.substring(bankCard.length()-4);
		}else{
			this.bankCard = bankCard;
		}
		
	}
	public Integer getBankType() {
		return bankType;
	}
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}
}
