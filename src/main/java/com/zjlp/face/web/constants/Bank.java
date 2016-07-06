package com.zjlp.face.web.constants;

import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

/**
 * 银行类型
 * @ClassName: Bank 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月12日 下午5:04:00
 */
public enum Bank {
	
	/** 邮政储蓄银行 */
	POSTAL_SAVINGS_BANK("邮政储蓄银行", true, true, "01000000"),
	/** 中国工商银行 */
	COMMERCIAL_BANK("工商银行", true, false, "01020000"),
	/** 农业银行 */
	AGRICULTURAL_BANK("农业银行", true, false, "01030000"),
	/** 中国银行 */
	CHINA_BANK("中国银行", false, true, "01040000"),
	/** 建设银行 */
	CONSTRUCTION_BANK("建设银行", true, false, "01050000"),
	/** 交通银行 */
	COMMUNICATIONS_BANK("交通银行", true, true, "03010000"),
	/** 中信银行 */
	CITIC_BANK("中信银行", true, false, "03020000"),
	/** 光大银行 */
	EVERBRIGHT_BANK("光大银行", true, true, "03030000"),
	/** 华夏银行 */
	HUAXIA_BANK("华夏银行", false, true, "03040000"),
	/** 民生银行 */
	MINSHENG_BANK("民生银行", false, true, "03050000"),
//	/** 招商银行 */
//	CHINAMERCHANTS_BANK("招商银行", true, true, "03080000"),  TODO 捷蓝不支持
	/** 兴业银行 */
	INDUSTRIAL_BANK("兴业银行", true, true, "03090000"),
	/** 平安银行 */
	PINGAN_BANK("平安银行", true, false, "03070000"),
	/** 浦发银行 */
	PUDONG_DEVELOPMENT_BANK("浦发银行", true, true, "03100000"),
	/** 北京银行 */
	BEIJING_BANK("北京银行", true, false, "04031000"),
	/** 上海银行 */
	SHANGHAI_BANK("上海银行", true, true, "04012900"),
//	/** 宁波银行 */
//	NINGBO_BANK("宁波银行", false, true, "04083320"),  	TODO 捷蓝不支持宁波银行
	;
	
	private String name;
	private boolean isDebit;
	private boolean isCredit;
	private String bankCode;

	private Bank(String name, boolean isDebit, boolean isCredit,
			String bankCode) {
		this.name = name;
		this.isCredit = isCredit;
		this.isDebit = isDebit;
		this.bankCode = bankCode;
	}
	
	/**
	 * 通过银行编号取得银行对象
	 * 
	 * @Title: getBankByCode 银行编号
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param bankCode
	 * @return
	 * @date 2014年4月24日 下午2:04:52
	 * @author lys
	 */
	public static Bank getBankByCode(String bankCode) {
		if (null == bankCode || "".equals(bankCode)) {
			return null;
		}
		Bank retBk = null;
		for (Bank bank : Bank.values()) {
			if (bankCode.equals(bank.getBankCode())) {
				retBk = bank;
				break;
			}
		}
		return retBk;
	}
	
	private static List<BankCard> creditCardList;
	
	private static List<BankCard> debitCardList;
	
	public static List<BankCard> withdrawCardList = initWithdrawCardList();
	
	/**
	 * 获取借记卡列表
	 * @return
	 */
	public static List<BankCard> getDebitCardList() {
		if (null == debitCardList) {
			debitCardList = new ArrayList<BankCard>();
			for (Bank bank : Bank.values()) {
				if (bank.isDebit()) {
					BankCard bankCard = new BankCard();
					bankCard.setBankCode(bank.getBankCode());
					bankCard.setBankName(bank.getName());
					debitCardList.add(bankCard);
				}
			}
		}
		return debitCardList;
	}

	private static List<BankCard> initWithdrawCardList() {
		List<BankCard> list = new ArrayList<BankCard>();
		list.add(coverToBankCard(COMMERCIAL_BANK));//中国工商银行 
		list.add(coverToBankCard(AGRICULTURAL_BANK));//农业银行
//		list.add(coverToBankCard(CHINA_BANK));//中国银行
		list.add(coverToBankCard(CONSTRUCTION_BANK));//建设银行
		list.add(coverToBankCard(COMMUNICATIONS_BANK));//交通银行
		list.add(coverToBankCard(POSTAL_SAVINGS_BANK));//邮政储蓄银行
		list.add(coverToBankCard(CITIC_BANK));//中信银行 
		list.add(coverToBankCard(EVERBRIGHT_BANK));//光大银行
//		list.add(coverToBankCard(HUAXIA_BANK));//华夏银行
//		list.add(coverToBankCard(MINSHENG_BANK));//民生银行
//		list.add(coverToBankCard(CHINAMERCHANTS_BANK));//招商银行
		list.add(coverToBankCard(INDUSTRIAL_BANK));//兴业银行
		list.add(coverToBankCard(PINGAN_BANK));//平安银行
		list.add(coverToBankCard(PUDONG_DEVELOPMENT_BANK));//浦发银行
		list.add(coverToBankCard(BEIJING_BANK));//北京银行
		list.add(coverToBankCard(SHANGHAI_BANK));//上海银行
//		list.add(coverToBankCard(NINGBO_BANK));//宁波银行
		return list;
	}
	
	public static boolean containWithdrawBank(String bankCode) {
		if (null == bankCode) {
			return false;
		}
		for (BankCard bankCard : withdrawCardList) {
			if (bankCard.getBankCode().equals(bankCode)) {
				return true;
			}
		}
		return false;
	}
	
	private static BankCard coverToBankCard(Bank bank) {
		BankCard bankCard = new BankCard();
		bankCard.setBankCode(bank.getBankCode());
		bankCard.setBankName(bank.getName());
		return bankCard;
	}

	/**
	 * 获取信用卡列表
	 * @return
	 */
	public static List<BankCard> getCreditCardList() {
		if (null == creditCardList) {
			creditCardList = new ArrayList<BankCard>();
			for (Bank bank : Bank.values()) {
				if (bank.isCredit()) {
					BankCard bankCard = new BankCard();
					bankCard.setBankCode(bank.getBankCode());
					bankCard.setBankName(bank.getName());
					creditCardList.add(bankCard);
				}
			}
		}
		return creditCardList;
	}
	
	public String getName() {
		return name;
	}

	public boolean isDebit() {
		return isDebit;
	}

	public boolean isCredit() {
		return isCredit;
	}

	public String getBankCode() {
		return bankCode;
	}
	
	public static void main(String[] args) {
		Bank bank = Bank.getBankByCode("01020000");
		if (null != bank) {
			System.out.println(bank.getName());
		}
	}

}
