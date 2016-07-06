package com.zjlp.face.web.server.user.bankcard.business;

import java.util.List;

import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;

public interface BankCardBusiness {

	/**
	 * 添加支付银行卡
	 * 
	 * @Title: addBankCard
	 * 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id（必须）
	 * @param bankCode
	 *            银行编号（必须）
	 * @param cardNo
	 *            银行卡号（必须）
	 * @param cell
	 *            手机号码（必须）
	 * @param endTime
	 *            终止时间（信用卡必须）
	 * @param cvv
	 *            卡后三位数（信用卡必须）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月18日 下午4:59:48
	 * @author lys
	 */
	@Deprecated //不要直接调用
	Long addPayBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv) throws BankCardException;
	
	/**
	 * 添加支付银行卡
	 * 
	 * @Title: addBindPayBankCard
	 * 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id（必须）
	 * @param bankCode
	 *            银行编号（必须）
	 * @param cardNo
	 *            银行卡号（必须）
	 * @param cell
	 *            手机号码（必须）
	 * @param endTime
	 *            终止时间（信用卡必须）
	 * @param cvv
	 *            卡后三位数（信用卡必须）
	 * @param code
	 *            验证码
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月18日 下午4:59:48
	 * @author lys
	 */
	Long addBindPayBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv, String code) throws BankCardException;

	/**
	 * 添加提现银行卡
	 * 
	 * @Title: addSettleBankCard
	 * 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id（必须）
	 * @param bankCode
	 *            银行编号（必须）
	 * @param cardNo
	 *            银行卡号（必须）
	 * @param cell
	 *            手机号码（必须）
	 * @param endTime
	 *            终止时间（信用卡必须）
	 * @param cvv
	 *            卡后三位数（信用卡必须）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月18日 下午4:59:48
	 * @author lys
	 */
	//不要直接调用
	@Deprecated
	Long addSettleBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv) throws BankCardException;

	/**
	 * 
	 * 添加提现银行卡
	 * 
	 * @Title: addSettleBankCard
	 * 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id（必须）
	 * @param bankCode
	 *            银行编号（必须）
	 * @param cardNo
	 *            银行卡号（必须）
	 * @param cell
	 *            手机号码（必须）
	 * @param endTime
	 *            终止时间（信用卡必须）
	 * @param cvv
	 *            卡后三位数（信用卡必须）
	 * @param code
	 *            验证码
	 * @return
	 * @throws BankCardException
	 * @date 2015年5月29日 下午2:03:47
	 * @author lys
	 */
	Long addBindSettleBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv, String code) throws BankCardException;
	
	/**
	 * 卡bin获取
	 * 
	 * @Title: getCardBin
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param cardNo
	 *            卡号
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月18日 下午5:14:40
	 * @author lys
	 * @see getCardInfo()
	 */
	@Deprecated
	BankCard getCardBin(String cardNo) throws BankCardException;
	
	/**
	 * 捷蓝银行卡信息获取
	 * @Title: getCardInfo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardNo 银行卡号
	 * @return
	 * @throws BankCardException
	 * @date 2015年5月29日 上午10:28:14  
	 * @author lys
	 */
	BankCard getCardInfo(String cardNo) throws BankCardException;

	/**
	 * 查询数据库是否有相同的银行卡
	 * 
	 * @Title: getIdenticalValidCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param cardNo
	 *            卡号
	 * @param type
	 *            卡类型（支付/提现）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:02:30
	 * @author lys
	 */
	BankCard getIdenticalValidCard(Long userId, String cardNo, Integer type)
			throws BankCardException;

	/**
	 * 查询可用的支付银行卡
	 * 
	 * @Title: findPayCardList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:03:41
	 * @author lys
	 */
	List<BankCard> findPayCardList(Long userId) throws BankCardException;

	/**
	 * 查询可用的提现银行卡
	 * 
	 * @Title: findSettleCardList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:04:01
	 * @author lys
	 */
	List<BankCard> findSettleCardList(Long userId) throws BankCardException;

	/**
	 * 删除银行卡
	 * 
	 * @Title: removeBankCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param cardId
	 *            卡id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:33:57
	 * @author lys
	 */
	boolean removeBankCard(Long userId, Long cardId) throws BankCardException;

	/**
	 * 获取银行卡的数量
	 * 
	 * @Title: getCardNumber
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param type
	 *            银行卡类型（支付卡/提现卡）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月20日 上午9:46:11
	 * @author lys
	 */
	int getCardNumber(Long userId, Integer type) throws BankCardException;
	
	/**
	 * 根据主键获取银行卡
	 * @Title: getBankCardById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月24日 下午3:45:22  
	 * @author lys
	 */
	BankCard getBankCardById(Long id) throws BankCardException;

	/**
	 * 设置默认银行卡
	 * @Title: setDefaultCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户
	 * @param cardId 银行卡id
	 * @throws BankCardException
	 * @date 2015年3月24日 下午9:33:36  
	 * @author lys
	 */
	void setDefaultCard(Long userId, Long cardId) throws BankCardException;
	
	/**
	 * 绑定银行卡
	 * 
	 * @Title: checkBankMobilecode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param cardNo
	 *            银行卡号
	 * @param mobile
	 *            手机号码
	 * @param cardType
	 *            银行卡类型
	 * @param cvn
	 *            安全码(信用卡时必须)
	 * @param expire
	 *            有效期(信用卡时必须)
	 * @param code
	 *            验证码
	 * @return 是否绑定成功
	 * @throws BankCardException
	 * @date 2015年5月29日 上午11:22:11
	 * @author lys
	 */
	String bindBankcard(Long userId, String cardNo, String mobile,
			Integer cardType, String cvn, String expire, String code,
			String serialNumber) throws BankCardException;
	
	/**
	 * 发送银行手机验证码
	 * 
	 * @Title: sendBankMobilecode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param cell
	 *            银行预留手机号
	 * @return
	 * @throws BankCardException
	 * @date 2015年5月29日 上午11:29:56
	 * @author lys
	 */
	boolean sendBankMobilecode(String cell) throws BankCardException;
	
	/**
	 * @Description: 查询银行卡列表
	 * @date: 2015年9月24日 下午5:56:50
	 * @author: zyl
	 */
	List<BankCard> findBankCardListByUserId(Long userId);
	
	/**
	 * @Description: 查询用户提现银行卡列表
	 * @param userId
	 * @return
	 * @date: 2015年10月6日 下午3:11:54
	 * @author: zyl
	 */
	List<BankCardVo> findWithdrawCardListByUserId(Long userId);
}
