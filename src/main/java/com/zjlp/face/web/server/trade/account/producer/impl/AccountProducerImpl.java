package com.zjlp.face.web.server.trade.account.producer.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.util.Logs;

/**
 * 钱包对外接口
 * 
 * @ClassName: AccountProducerImpl
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月17日 下午5:15:30
 */
@Repository("accountProducer")
public class AccountProducerImpl implements AccountProducer {

	@Autowired(required = false)
	private AccountService accountService;

	
	@Override
	public Long addUserAccount(String openId,Long userId,Integer remoteType) throws AccountException {
		try {
			AssertUtil.notNull(openId, "Param[openId] can not be null.");
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(remoteType, "Param[remoteType] can not be null.");
			return accountService.addAccount(String.valueOf(userId), remoteType);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	@Override
	public Account getAccountById(Long id) throws AccountException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			return accountService.getValidAccountById(id);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Account getAccountByUserId(Long userId) throws AccountException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			return accountService.getValidAccountByRemoteId(
					String.valueOf(userId), Account.REMOTE_TYPE_USER);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Long addUserAccount(Long userId, String phone, String email,
			Long margin) throws AccountException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			Long id = accountService.addAccount(userId, phone, email, margin);
			return id;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Long addShopAccount(String shopNo, String phone,
			String invitationCode, String email, Long margin) throws AccountException {
		try {
			AssertUtil.hasLength(shopNo, "Param[shopNo] can not be null.");
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			AssertUtil.hasLength(invitationCode, "Param[invitationCode] can not be null.");
			Long id = accountService.addAccount(shopNo, phone, invitationCode, email, margin);
			return id;
		} catch (Exception e) {
			Logs.error(e);
			throw new AccountException(e);
		}
	}

	@Override
	public boolean extractAmount(Long userId, String shopNo, Long amount,
			String serialNo,String remark) throws AccountException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.hasLength(shopNo, "Param[shopNo] can not be null.");
			AssertUtil.notNull(amount, "Param[amount] can not be null.");
			AssertUtil.hasLength(serialNo, "Param[serialNo] can not be null.");
			accountService.extractAmount(userId, shopNo, amount, serialNo,remark);
			return true;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public boolean checkPaymentCode(Long userId, String paymentCode)
			throws AccountException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.hasLength(paymentCode, "Param[paymentCode] can not be null.");
			Account account = this.getAccountByUserId(userId);
			if (StringUtils.isBlank(account.getPaymentCode())) {
				return false;
			}
			return DigestUtils.jzShaEncrypt(paymentCode.trim()).equals(account.getPaymentCode());
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Account getAccountByRemoteId(String remoteId, Integer type)
			throws AccountException {
		try {
			AssertUtil.notNull(type, "Param[type] can not be null.");
			AssertUtil.hasLength(remoteId, "Param[remoteId] can not be null.");
			return accountService.getAccountByRemoteId(remoteId, type);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

}
