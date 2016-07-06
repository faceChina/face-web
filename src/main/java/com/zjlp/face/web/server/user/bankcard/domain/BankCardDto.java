package com.zjlp.face.web.server.user.bankcard.domain;

import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;

public class BankCardDto extends BankCard {

	private static final long serialVersionUID = 8275756245610931497L;

	/**
	 * 初始化借记卡（支付用）
	 * 
	 * @Title: InitDebitCardForPay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param bankCode
	 *            银行编号
	 * @param bankName
	 *            银行名
	 * @param cardNo
	 *            卡号
	 * @param cell
	 *            手机号
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @return
	 * @date 2015年3月12日 下午5:36:02
	 * @author lys
	 */
	public static BankCard InitDebitCardForPay(String remoteId,
			Integer remoteType, String bankCode, String bankName,
			String cardNo, String cell, String userName, String identity) {
		// 采集数据
		BankCard bankCard = new BankCard(remoteId, remoteType);
		bankCard.setBankCode(bankCode);
		bankCard.setBankName(bankName);
		bankCard.setBankCard(cardNo);
		bankCard.setCell(cell);
		bankCard.setName(userName);
		bankCard.setIdentity(identity);
		// 默认数据
		bankCard.setBankType(BankCard.TYPE_DEBIT);
		bankCard.setType(BankCard.USERFOR_PAY);
		bankCard.setStatus(Constants.VALID);
		bankCard.setPayType(BankCard.TYPE_DEBIT);
		bankCard.setIsDefault(Constants.NOTDEFAULT);
		return bankCard;
	}

	/**
	 * 初始化借记卡（提现用）
	 * 
	 * @Title: InitDebitCardForSettle
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param bankCode
	 *            银行编号
	 * @param bankName
	 *            银行名
	 * @param cardNo
	 *            卡号
	 * @param cell
	 *            手机号
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @return
	 * @date 2015年3月12日 下午5:36:07
	 * @author lys
	 */
	public static BankCard InitDebitCardForSettle(String remoteId,
			Integer remoteType, String bankCode, String bankName,
			String cardNo, String cell, String userName, String identity) {
		BankCard bankCard = InitDebitCardForPay(remoteId, remoteType, bankCode,
				bankName, cardNo, cell, userName, identity);
		bankCard.setType(BankCard.USERFOR_SETTLE); // 提现用
		bankCard.setStatus(Constants.VALID);
		return bankCard;
	}

	/**
	 * 初始化信用卡（支付用）
	 * 
	 * @Title: InitCreditCardForPay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param bankCode
	 *            银行编号
	 * @param bankName
	 *            银行名
	 * @param cardNo
	 *            卡号
	 * @param cell
	 *            手机号
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @param endTime
	 *            截止日期
	 * @param cvv
	 *            有效数字
	 * @return
	 * @date 2015年3月12日 下午5:36:11
	 * @author lys
	 */
	public static BankCard InitCreditCardForPay(String remoteId,
			Integer remoteType, String bankCode, String bankName,
			String cardNo, String cell, String userName, String identity,
			String endTime, String cvv) {
		// 采集数据
		BankCard bankCard = new BankCard(remoteId, remoteType);
		bankCard.setBankCode(bankCode);
		bankCard.setBankName(bankName);
		bankCard.setBankCard(cardNo);
		bankCard.setCell(cell);
		bankCard.setName(userName);
		bankCard.setIdentity(identity);
		bankCard.setEndTime(endTime);
		bankCard.setCvv(cvv);
		// 默认数据
		bankCard.setBankType(BankCard.TYPE_CREDIT);
		bankCard.setType(BankCard.USERFOR_PAY);
		bankCard.setStatus(Constants.VALID);
		bankCard.setPayType(BankCard.TYPE_CREDIT);
		bankCard.setIsDefault(Constants.NOTDEFAULT);
		return bankCard;
	}

	/**
	 * 初始化信用卡（提现用）
	 * 
	 * @Title: InitCreditCardForSettle
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param bankCode
	 *            银行编号
	 * @param bankName
	 *            银行名
	 * @param cardNo
	 *            卡号
	 * @param cell
	 *            手机号
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @param endTime
	 *            截止日期
	 * @param cvv
	 *            有效数字
	 * @return
	 * @date 2015年3月12日 下午5:36:16
	 * @author lys
	 */
	public static BankCard InitCreditCardForSettle(String remoteId,
			Integer remoteType, String bankCode, String bankName,
			String cardNo, String cell, String userName, String identity,
			String endTime, String cvv) {
		BankCard bankCard = InitCreditCardForPay(remoteId, remoteType,
				bankCode, bankName, cardNo, cell, userName, identity, endTime,
				cvv);
		bankCard.setType(BankCard.USERFOR_SETTLE); // 提现用
		bankCard.setStatus(Constants.FREEZE); // 冻结
		return bankCard;
	}
	
	
	public static boolean isEqualWith(BankCard one, BankCard other) {
		if (null != one.getRemoteId() && null != other.getRemoteId()
				&& !one.getRemoteId().equals(other.getRemoteId())) {
			return false;
		}
		if (null != one.getRemoteType() && null != other.getRemoteType()
				&& !one.getRemoteType().equals(other.getRemoteType())) {
			return false;
		}
		if (null != one.getBankCode() && null != other.getBankCode()
				&& !one.getBankCode().equals(other.getBankCode())) {
			return false;
		}
		if (null != one.getBankType() && null != other.getBankType()
				&& !one.getBankType().equals(other.getBankType())) {
			return false;
		}
		if (null != one.getType() && null != other.getType()
				&& !one.getType().equals(other.getType())) {
			return false;
		}
		return true;
	}
	
	public static String supplementZero(String userId) {
		if (userId.length() < 8) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = userId.length(); i <= 8; i++) {
				stringBuilder.append("0");
			}
			return userId = stringBuilder.append(userId).toString();
		}
		return userId;
	}
	
	
	/**
	 * 银行卡数据类型切换
	 * @Title: coverCardsForView 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param list 银行卡列表
	 * @return
	 * @date 2015年3月19日 上午10:28:03  
	 * @author lys
	 */
	public static List<BankCardVo> coverCardsForView(List<BankCard> list) {
		List<BankCardVo> result = new ArrayList<BankCardVo>();
		if (null == list || list.isEmpty()) {
			return result;
		}
		for (BankCard bankCard : list) {
			if (null == bankCard) continue;
			result.add(new BankCardVo(bankCard));
		}
		return result;
	}
	
	/**
	 * 获取默认卡
	 * @Title: getDefaultCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardList
	 * @return
	 * @date 2015年3月24日 下午2:03:43  
	 * @author lys
	 */
	public static BankCardVo getDefaultCard(List<BankCardVo> cardList) {
		if (null == cardList || cardList.isEmpty()) {
			return null;
		}
		BankCardVo defaultCard = cardList.get(0);
		for (BankCardVo bankCard : cardList) {
			if (null == bankCard) {
				continue;
			}
			if (null != bankCard.getIsDefault() 
					&& 1 == bankCard.getIsDefault()) {
				defaultCard = bankCard;
				break;
			}
		}
		defaultCard.setIsDefault(1);
		return defaultCard;
	}
}
