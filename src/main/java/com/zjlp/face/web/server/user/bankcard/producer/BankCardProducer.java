package com.zjlp.face.web.server.user.bankcard.producer;

import java.util.List;

import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;


public interface BankCardProducer {
	
	/**
	 * 根据主键查询银行卡
	 * @Title: getBankCardById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws BankCardException 如果银行卡不存在
	 * @date 2015年3月19日 上午10:54:59  
	 * @author lys
	 */
	BankCard getBankCardById(Long id) throws BankCardException;

	/**
	 * 激活银行卡
	 * @Title: activationBankCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:55:20  
	 * @author lys
	 */
	boolean activationBankCard(Long id) throws BankCardException;

	/**
	 * 查询银行卡（如果不存在，返回空）
	 * @Title: getBankCardByPk 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:55:35  
	 * @author lys
	 */
	BankCard getBankCardByPk(Long cardId) throws BankCardException;
	
	/**
	 * 查询支付银行卡列表
	 * @Title: findPayCardList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @return
	 * @throws BankCardException
	 * @date 2015年3月19日 上午10:56:22  
	 * @author lys
	 */
	List<BankCardVo> findPayCardList(Long userId) throws BankCardException;
	
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
	List<BankCardVo> findSettleCardList(Long userId) throws BankCardException;

	BankCard getBankCardByCardNoAndUserId(String bankCard, Long userId);

	void addBankCard(BankCard bankCard);

	void editBankCard(BankCard bankCard);
	
	List<BankCard> findBankCardListByUserId(Long userId);
}