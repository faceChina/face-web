package com.zjlp.face.web.server.user.bankcard.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

public interface BankCardService {

	/**
	 * 添加银行卡
	 * 
	 * @Title: addBankCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param bankCard
	 *            银行卡
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月12日 下午7:04:11
	 * @author lys
	 */
	Long addBankCard(BankCard bankCard) throws BankCardException;

	/**
	 * 根据主键查找银行卡
	 * 
	 * @Title: getValidBankCardById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws BankCardException
	 *             : when card with id not exists.
	 * @date 2015年3月12日 下午7:05:56
	 * @author lys
	 */
	BankCard getValidBankCardById(Long id) throws BankCardException;

	/**
	 * 逻辑删除指定银行卡（失效）
	 * 
	 * @Title: invalidateBankCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月12日 下午7:14:18
	 * @author lys
	 */
	boolean invalidateBankCard(Long id) throws BankCardException;

	/**
	 * 查询用户支付卡列表
	 * 
	 * @Title: findPayCardList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月12日 下午7:30:19
	 * @author lys
	 */
	List<BankCard> findPayCardList(Long userId) throws BankCardException;

	/**
	 * 查询用户提现银行卡列表
	 * 
	 * @Title: findSettleCardList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月12日 下午7:31:39
	 * @author lys
	 */
	List<BankCard> findSettleCardList(Long userId) throws BankCardException;

	/**
	 * 根据主键查询银行卡（无状态）
	 * 
	 * @Title: getBankCardByPk
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月12日 下午7:42:00
	 * @author lys
	 */
	BankCard getBankCardByPk(Long id) throws BankCardException;

	/**
	 * 查询默认银行卡
	 * 
	 * @Title: getDefaultBankCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param type
	 *            银行卡使用类型（支付，结算）
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @return 默认银行卡
	 * @throws BankCardException
	 * @date 2015年3月13日 上午9:25:15
	 * @author lys
	 */
	BankCard getDefaultBankCard(String remoteId, Integer remoteType,
			Integer type) throws BankCardException;

	/**
	 * 查询默认银行卡
	 * 
	 * @Title: getDefaultBankCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param type
	 *            银行卡使用类型（支付，结算）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月13日 上午9:38:54
	 * @author lys
	 */
	BankCard getDefaultBankCard(Long userId, Integer type)
			throws BankCardException;

	/**
	 * 设置默认银行卡
	 * 
	 * @Title: setDefaultBankCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            银行卡id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月13日 上午9:25:35
	 * @author lys
	 */
	boolean setDefaultBankCard(Long id, Long userId)
			throws BankCardException;

	/**
	 * 编辑银行的状态
	 * 
	 * @Title: editCardStatus
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            银行卡id
	 * @param status
	 *            状态
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月13日 上午10:19:00
	 * @author lys
	 */
	boolean editCardStatus(Long id, Integer status) throws BankCardException;

	/**
	 * 设置银行卡的签约卡号
	 * 
	 * @Title: setCardNoAgree
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            银行卡id
	 * @param noAgree
	 *            签约卡号
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月13日 上午10:20:40
	 * @author lys
	 */
	boolean setCardNoAgree(Long id, String noAgree) throws BankCardException;

	/**
	 * 查找对应卡号，唯一有效的银行卡
	 * 
	 * <p>
	 * 
	 * 注：查找银行的状态为正常的，或是未激活的（失效的不进行查询）
	 * 
	 * @Title: getIdenticalValidCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param cardNo
	 *            银行卡号
	 * @param bankCode
	 *            银行编码
	 * @param bankType
	 *            银行卡类型
	 * @param type
	 *            用途（支付|结算）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月13日 上午10:42:00
	 * @author lys
	 */
	BankCard getIdenticalValidCard(String remoteId, Integer remoteType,
			String cardNo, String bankCode, Integer bankType, Integer type)
			throws BankCardException;

	/**
	 * 根据银行卡号&银行卡使用类型查找唯一有效的银行卡(包括未激活)
	 * 
	 * <p>
	 * 
	 * 注：一般情况下，固定的卡号对应唯一的银行卡类型和银行卡编号
	 * 
	 * @Title: getIdenticalValidCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param cardNo
	 *            银行卡号
	 * @param type
	 *            用途（支付|结算）
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月13日 下午4:20:20
	 * @author lys
	 */
	BankCard getIdenticalValidCard(String remoteId, Integer remoteType,
			String cardNo, Integer type) throws BankCardException;

	/**
	 * 获取银行个数
	 * 
	 * @Title: getCardNumber
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param type
	 *            银行卡类型
	 * @return
	 * @date 2015年3月20日 上午9:53:26
	 * @author lys
	 */
	int getCardNumber(String remoteId, Integer remoteType, Integer type);

	/**
	 * 更新银行卡绑定id
	 * @Title: editBindId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 银行卡id
	 * @param bindId 绑定id
	 * @date 2015年5月29日 下午2:32:13  
	 * @author lys
	 */
	void editBindId(Long cardId, String bindId);

	BankCard getBankCardByCardNoAndUserId(String bankCard, Long userId);

	void editBankCard(BankCard bankCard);

	List<BankCard> findBankCardListByUserId(Long userId);

	BankCard getDefaultBankCardByUserId(Long userId);

}
