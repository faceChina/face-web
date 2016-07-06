package com.zjlp.face.web.server.trade.account.producer;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.exception.AccountException;

public interface AccountProducer {

	/**
	 * 生成微信免登陆用户钱包
	 * 
	 * @Title: addUserAccount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param openId
	 *            openId（必须,验证是否微信用户）
	 * @param userId
	 *            用户id（必须）
	 * @param remoteType
	 *            remoteType（必须）
	 *            
	 * @return
	 * @throws AccountException
	 * @date 2015年9月22日 下午19:16:08
	 * @author talo
	 */
	Long addUserAccount(String openId,Long userId, Integer remoteType) throws AccountException;
	
	
	/**
	 * 生成用户钱包
	 * 
	 * @Title: addUserAccount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id（必须）
	 * @param phone
	 *            手机号码（必须）
	 * @param email
	 *            邮箱（可选）
	 * @param margin
	 *            保证金（可选）
	 * @return
	 * @throws AccountException
	 * @date 2015年3月18日 上午11:13:08
	 * @author lys
	 */
	Long addUserAccount(Long userId, String phone, String email, Long margin)
			throws AccountException;

	/**
	 * 店铺钱包生成
	 * 
	 * @Title: addShopAccount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号（必须）
	 * @param phone
	 *            手机号码（必须）
	 * @param invitationCode
	 *            邀请码（必须）
	 * @param email
	 *            邮箱（可选）
	 * @param margin
	 *            保证金（可选）
	 * @return
	 * @throws AccountException
	 * @date 2015年3月18日 上午11:17:07
	 * @author lys
	 */
	Long addShopAccount(String shopNo, String phone, String invitationCode,
			String email, Long margin) throws AccountException;

	/**
	 * 根据主键查询钱包记录
	 * 
	 * @Title: getAccountById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws AccountException
	 * @date 2015年3月17日 下午2:10:21
	 * @author lys
	 */
	Account getAccountById(Long id) throws AccountException;

	/**
	 * 根据用户id获取账户信息
	 * 
	 * @Title: getAccountByUserId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws AccountException
	 * @date 2015年3月17日 上午11:30:01
	 * @author lys
	 */
	Account getAccountByUserId(Long userId) throws AccountException;
	
	
	/**
	 * 指定店铺的余额转入指定的用户账户
	 * 
	 * @Title: extractAmount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param shopNo
	 *            店铺编号
	 * @param amount
	 *            转入金额
	 * @param serialNo
	 *            流水号
	 * @return
	 * @throws AccountException
	 * @date 2015年4月3日 下午3:27:58  
	 * @author lys
	 */
	boolean extractAmount(Long userId, String shopNo, Long amount,
			String serialNo,String remark) throws AccountException;

	
	/**
	 * 支付密码验证
	 * @Title: checkPaymentCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param paymentCode
	 * @return
	 * @throws AccountException
	 * @date 2015年3月25日 下午2:18:30  
	 * @author lys
	 */
	boolean checkPaymentCode(Long userId, String paymentCode) throws AccountException;
	
	/**
	 * 根据RemoteId查询
	 * @Title: getAccountByRemoteId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 * @param type
	 * @return
	 * @throws AccountException
	 * @return Account
	 * @author phb
	 * @date 2015年4月15日 下午5:49:41
	 */
	Account getAccountByRemoteId(String remoteId,Integer type) throws AccountException;
}
