package com.zjlp.face.web.server.trade.account.business.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.domain.AccountOperationRecord;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.data.TranscodeUtil;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.account.domain.dto.AccountOperationRecordDto;
import com.zjlp.face.web.server.trade.order.producer.OperateDataProducer;
import com.zjlp.face.web.server.trade.payment.producer.PaymentProducer;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;

@Repository("accountBusiness")
public class AccountBusinessImpl implements AccountBusiness {

	// private Logger log = Logger.getLogger(getClass());
	@Autowired(required = false)
	private AccountService accountService;
	@Autowired
	private UserProducer userProducer;
	@Autowired
	private PaymentProducer paymentProducer;
	@Autowired
	private OperateDataProducer operateDataProducer;

	@Override
	@Transactional
	public boolean completInformation(Long userId, String paymentCode,
			String confirmCode, String userName, String identity)
			throws AccountException {
		try {
			// 参数验证
			AssertUtil.notNull(userId, "Param[userId] can not null.");
			AssertUtil.hasLength(paymentCode, "Param[paymentCode] can not null.");
			AssertUtil.hasLength(confirmCode, "Param[confirmCode] can not null.");
			// 支付密码验证
			paymentCode = paymentCode.trim();
			confirmCode = confirmCode.trim();
			AssertUtil.isTrue(paymentCode.equals(confirmCode),
					"Set user[userId={}]'s paymentcode, code[{}] != confirmcode[{}]",
					paymentCode, confirmCode);
			// 身份信息验证
			User user = userProducer.getUserById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			// 用户信息设置
			if (StringUtils.isBlank(user.getContacts())
					|| StringUtils.isBlank(user.getIdentity())) {
				AssertUtil.hasLength(userName, "Param[userName] can not null.");
				AssertUtil.hasLength(identity, "Param[identity] can not null.");
				userProducer.editNameAndIdentity(userId, userName, identity);
			}
			// 支付密码设置
			accountService.editPaymentCodeByUserId(userId,
					DigestUtils.jzShaEncrypt(paymentCode));
			return true;
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
			e.printStackTrace();
			throw new AccountException(e);
		}
	}

	@Override
	public Account getAccountByShopNo(String shopNo) throws AccountException {
		try {
			AssertUtil.hasLength(shopNo, "Param[shopNo] can not be null.");
			return accountService.getValidAccountByRemoteId(
					shopNo, Account.REMOTE_TYPE_SHOP);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Long getWithdrawAmount(Long userId) throws AccountException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			Long amount = accountService.getWithdrawAmount(String.valueOf(userId), Account.REMOTE_TYPE_USER);
			return amount;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Long getWithdrawAmount(String shopNo) throws AccountException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			Long amount = accountService.getWithdrawAmount(shopNo, Account.REMOTE_TYPE_SHOP);
			return amount;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Pagination<AccountOperationRecordDto> findUserOperationPage(
			AccountOperationRecordDto operationRecordDto,
			Pagination<AccountOperationRecordDto> pagination, Aide aide)
			throws AccountException {
		try {
			AssertUtil.notNull(operationRecordDto, "Param[operationRecordDto] can not be null.");
			AssertUtil.notNull(operationRecordDto.getUserId(), "Param[dto.userId] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			AssertUtil.notNull(aide, "Param[aide] can not be null.");
			pagination = findOPerationPage(pagination, String.valueOf(operationRecordDto.getUserId()),
					Account.REMOTE_TYPE_USER, aide);
			if (null != pagination.getDatas() && !pagination.getDatas().isEmpty()) {
				for (AccountOperationRecordDto dto : pagination.getDatas()) {
					dto.setUserId(operationRecordDto.getUserId());
				}
			}
			return pagination;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Pagination<AccountOperationRecordDto> findShopOperationPage(
			AccountOperationRecordDto operationRecordDto,
			Pagination<AccountOperationRecordDto> pagination, Aide aide)
			throws AccountException {
		try {
			AssertUtil.notNull(operationRecordDto, "Param[operationRecordDto] can not be null.");
			AssertUtil.notNull(operationRecordDto.getShopNo(), "Param[dto.shopNo] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			AssertUtil.notNull(aide, "Param[aide] can not be null.");
			return findOPerationPage(pagination, operationRecordDto.getShopNo(),
					Account.REMOTE_TYPE_SHOP, aide);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	/**
	 * 查询明细记录列表
	 * @Title: findOPerationPage
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param pagination 分页信息
	 * @param remoteId 外键id
	 * @param remoteType 外键类型
	 * @param aide 辅助信息
	 * @return
	 * @date 2015年3月18日 下午4:46:03
	 * @author lys
	 */
	private Pagination<AccountOperationRecordDto> findOPerationPage (
			Pagination<AccountOperationRecordDto> pagination, 
			String remoteId, Integer remoteType, Aide aide) {
		
		Account account = accountService.getValidAccountByRemoteId(remoteId,
				remoteType);
		aide.setStartNum(pagination.getStart());
		aide.setPageSizeNum(pagination.getPageSize());

		List<Integer> fromTypes = Account.REMOTE_TYPE_USER.equals(remoteType) ? AccountOperationRecordDto.USRFROMTYPES
				: AccountOperationRecordDto.SPFROMTYPES;
		List<Integer> toTypes = Account.REMOTE_TYPE_USER.equals(remoteType) ? AccountOperationRecordDto.USRTOTYPES
				: AccountOperationRecordDto.SPTOTYPES;
		List<AccountOperationRecord> list = accountService
				.findOperationRecordPage(account.getId(), fromTypes, toTypes,
						aide);
		List<AccountOperationRecordDto> dtoList = TranscodeUtil
				.transParentToChildList(list, AccountOperationRecordDto.class);
		int totalRow = accountService.getCount(account.getId(), fromTypes,
				toTypes, aide);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(dtoList);
		return pagination;
	}
	
	

	@Override
	public Pagination<AccountOperationRecordDto> findUserOperationPageByType(
			String userId,
			Pagination<AccountOperationRecordDto> pagination, Aide aide,
			Integer type) throws AccountException {
		try {
			AssertUtil.notNull(pagination, "参数pagination不能为空");
			AssertUtil.notNull(aide, "参数aide不能为空");
			Account account = accountService.getValidAccountByRemoteId(userId, Account.REMOTE_TYPE_USER);
			aide.setStartNum(pagination.getStart());
			aide.setPageSizeNum(pagination.getPageSize());
			List<Integer> fromTypes = AccountOperationRecordDto.USRFROMTYPES;
			List<Integer> toTypes = AccountOperationRecordDto.USRTOTYPES;
			List<Integer> status = null;
			if (type != null) {
				if (1 == type) {
					status = AccountOperationRecordDto.RECIEVE_TYPES;
				} else if (2 == type) {
					status = AccountOperationRecordDto.PAY_TYPES;
				} else if (3 == type) {
					status = AccountOperationRecordDto.WITHDRAW_TYPES;
				}
			}
			List<AccountOperationRecord> list = accountService.findUserOperationRecord(account.getId(), fromTypes, toTypes, aide, status, type);
			List<AccountOperationRecordDto> dtoList = TranscodeUtil
					.transParentToChildList(list, AccountOperationRecordDto.class);
			int totalRow = accountService.getCountWithStatus(account.getId(), fromTypes,
					toTypes, aide, status, type);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(dtoList);
			
			return pagination;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public AccountOperationRecordDto getOperationRecordById(Long id, Long userId)
			throws AccountException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AccountOperationRecord record = accountService.getOperationRecordById(id);
			AssertUtil.notNull(record, "AccountOperation[id={}] is not exists.", "没有这条记录哦！", id);
			Account account = accountService.getAccountByUserId(userId);
			AssertUtil.isTrue(account.getId().equals(record.getFromAccountId()) || account.getId().equals(record.getToAccountId()),
					"AccountOperation[id={}] is not belong to user[id={}]", "亲，这条记录不是你的！", account.getId(), userId);
			return TranscodeUtil.transParentToChild(record, AccountOperationRecordDto.class);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	public Integer getWithdrawCount(Long userId) throws AccountException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			Integer count = accountService.getLastWithdrawCount(userId);
			return count;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	@Override
	public boolean existPaymentCode(Long userId){
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			Account account = this.getAccountByUserId(userId);
			return StringUtils.isNotBlank(account.getPaymentCode());
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	@Override
	public boolean checkPaymentCode(Long userId, String paymentCode) throws AccountException{
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

	@Deprecated
	@Override
	public Long getUserFreezeAmount(Long userId) {
		//获取用户和用户所有店铺的冻结金额
		Account account = accountService.getAccountByUserId(userId);
		Long userFreezeAmount = paymentProducer.getUserFreezeAmount(account.getId());
		Long usersShopsFreezeAmount = paymentProducer.getUsersShopsFreezeAmount(userId);
		//获取用户推广的冻结金额
		Long usersPopularizeAmount = paymentProducer.getUsersPopularizeAmount(userId);
		return userFreezeAmount+usersShopsFreezeAmount+usersPopularizeAmount;
	}

	@Override
	public Long getShopFreezeIncome(String shopNo) {
		return paymentProducer.getShopFreezeIncome(shopNo);
	}

	@Override
	public Long getShopYesterdayIncome(String shopNo) {
		Date date = new Date();
		Date endTime = DateUtil.getZeroPoint(date);
		Date startTime = DateUtil.addDay(endTime, -1);
		return paymentProducer.getShopIncomeByTime(shopNo, startTime, endTime);
	}

	@Override
	public Long getShopTotalIncome(String shopNo) {
		Date endTime = new Date();
		return paymentProducer.getShopIncomeByTime(shopNo, null, endTime);
	}

	@Override
	public Long getFreezeAmount(Long userId) throws AccountException {
		try {
			AssertUtil.notNull(userId, "参数userId不能为空");
			User user = userProducer.getUserById(userId);
			AssertUtil.notNull(user, "查无此用户");
			return operateDataProducer.getFreezeAmount(user);
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	
}
