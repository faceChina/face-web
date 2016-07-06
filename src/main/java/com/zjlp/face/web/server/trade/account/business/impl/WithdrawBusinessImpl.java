package com.zjlp.face.web.server.trade.account.business.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.domain.WithdrawRecord;
import com.zjlp.face.account.dto.WithdrawRecordDto;
import com.zjlp.face.account.dto.WithdrawReq;
import com.zjlp.face.account.dto.WithdrawRsp;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.account.service.WithdrawService;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.metaq.producer.WithdrawJobProducer;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.component.sms.Sms.SwitchType;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.component.sms.SmsProccessor;
import com.zjlp.face.web.constants.Bank;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.trade.account.business.WithdrawBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.producer.BankCardProducer;

@Repository("withdrawBusiness")
public class WithdrawBusinessImpl implements WithdrawBusiness {
	private Logger _withdrawLogger = Logger.getLogger("withdrawClientLog");
	@Autowired(required=false)
	private WithdrawService withdrawService;
	@Autowired(required=false)
	private AccountService accountService;
//	@Autowired  //提现定时任务
//	private WithdrawInfoQueryJobManager withdrawInfoQueryJobManager;
	@Autowired
	private WithdrawJobProducer withdrawJobProducer;
	@Autowired
	private BankCardProducer bankCardProducer;
	
	@Override
	@Deprecated
	public void withdraw(Long cardId, String price, Long accountId, Long userId, String paymentCode, String cellPhone) throws AccountException {
		
		Assert.notNull(cardId, "Param[cardId] can not be null.");
		Assert.notNull(price, "Param[price] can not be null.");
		Assert.notNull(accountId, "Param[accountId] can not be null.");
		Assert.notNull(userId, "Param[userId] can not be null.");
		Assert.notNull(paymentCode, "Param[paymentCode] can not be null.");
		try {
			//提现开关
			String switchWithdraw = PropertiesUtil.getContexrtParam("SWITCH_WITHDRAW");    
    		if (StringUtils.isBlank(switchWithdraw) || !"1".equals(switchWithdraw)) {
    			_withdrawLogger.warn("[提现日志]the  SWITCH_WITHDRAW is "+switchWithdraw);
    			return;
    		}
    		_withdrawLogger.info("[提现日志]提现开始.");
			StringBuilder info = new StringBuilder();
			_withdrawLogger.info(info.append("[提现日志]参数：[cardId=").append(cardId)
					.append("], [price=").append(price).append("], [account(id=")
					.append(accountId).append("], [paymentCode=******]"));
			//参数验证
			//提现请求数据
			BankCard bankCard = bankCardProducer.getBankCardByPk(cardId);
			this.checkWithdrawDataValid(bankCard, price, userId, paymentCode);
			
			WithdrawReq withdrawReq = this.withdrawProductor(bankCard, price, accountId, userId);
			_withdrawLogger.info("[提现日志]"+"请求参数："+String.valueOf(withdrawReq));
			
			WithdrawRsp withdrawRsp = this.excuteWithdraw(withdrawReq, cardId, accountId, cellPhone);
			
			try {
				//更新提现余额
				Account newAccount = accountService.getAccountById(accountId);
				withdrawService.editAmountAfterWithdraw(withdrawReq.getTransferSerino(), 
						newAccount.getWithdrawAmount());
			} catch (Exception e) {
				_withdrawLogger.error("[提现日志]更新提现余额发生异常", e);
			}
			
			try {
				if (null == withdrawRsp || !Constants.WD_STATE_FAIL.equals(withdrawRsp.getStates())) {
					accountService.addLastWithdrawCount(userId); //提现次数修正
					this.warnForBalance(withdrawReq.getTransferSerino());  //最低值提醒
				}
			} catch (Exception e) {
				_withdrawLogger.error("[提现日志]最低值提醒发生异常", e);
			}
			//提现短信
			SmsProccessor.sendWithdrawMessage((null == withdrawRsp) ? Constants.WD_STATE_DEAL : withdrawRsp.getStates(), 
					cellPhone, bankCard.getBankCard(), bankCard.getBankName(), price);
			
		} catch (Exception e) {
			_withdrawLogger.info("[提现日志]提现发生异常："+e.getMessage(), e);
			throw new AccountException(e);
		}
	}
	
	
	@Override
	public void withdraw(BankCard bankCard, String price, Long accountId, Long userId, String paymentCode, String cellPhone) throws AccountException {
		
		Assert.notNull(bankCard, "Param[bankCard] can not be null.");
		Assert.notNull(bankCard.getBankCard(), "Param[bankCard.bankCard] can not be null.");
		Assert.notNull(bankCard.getName(), "Param[bankCard.name] can not be null.");
		Assert.notNull(bankCard.getBankCode(), "Param[bankCard.bankCode] can not be null.");
		Assert.notNull(price, "Param[price] can not be null.");
		Assert.notNull(accountId, "Param[accountId] can not be null.");
		Assert.notNull(userId, "Param[userId] can not be null.");
		Assert.notNull(paymentCode, "Param[paymentCode] can not be null.");
		try {
			//提现开关
			String switchWithdraw = PropertiesUtil.getContexrtParam("SWITCH_WITHDRAW");    
    		if (StringUtils.isBlank(switchWithdraw) || !"1".equals(switchWithdraw)) {
    			_withdrawLogger.warn("[提现日志]the  SWITCH_WITHDRAW is "+switchWithdraw);
    			return;
    		}
    		_withdrawLogger.info("[提现日志]提现开始.");
			StringBuilder info = new StringBuilder();
			_withdrawLogger.info(info.append("[提现日志]参数：[bankCard=").append(bankCard.getBankCard())
					.append("], [bankName=").append(bankCard.getBankName())
					.append("], [price=").append(price).append("], [account(id=")
					.append(accountId).append("], [paymentCode=******]"));
			//参数验证
			this.checkWithdrawDataValid(bankCard, price, userId, paymentCode);
			//提现请求数据
			WithdrawReq withdrawReq = this.withdrawProductor(bankCard, price, accountId, userId);
			_withdrawLogger.info("[提现日志]"+"请求参数："+String.valueOf(withdrawReq));
			
			WithdrawRsp withdrawRsp = this.excuteWithdraw(withdrawReq, null, accountId, cellPhone);
			
			try {
				//更新提现余额
				Account newAccount = accountService.getAccountById(accountId);
				withdrawService.editAmountAfterWithdraw(withdrawReq.getTransferSerino(), 
						newAccount.getWithdrawAmount());
			} catch (Exception e) {
				_withdrawLogger.error("[提现日志]更新提现余额发生异常", e);
			}
			
			try {
				if (null == withdrawRsp || !Constants.WD_STATE_FAIL.equals(withdrawRsp.getStates())) {
					accountService.addLastWithdrawCount(userId); //提现次数修正
					this.warnForBalance(withdrawReq.getTransferSerino());  //最低值提醒
				}
			} catch (Exception e) {
				_withdrawLogger.error("[提现日志]最低值提醒发生异常", e);
			}
			//提现短信
			SmsProccessor.sendWithdrawMessage((null == withdrawRsp) ? Constants.WD_STATE_DEAL : withdrawRsp.getStates(), 
					cellPhone, bankCard.getBankCard(), bankCard.getBankName(), price);
			
		} catch (Exception e) {
			_withdrawLogger.info("[提现日志]提现发生异常："+e.getMessage(), e);
			throw new AccountException(e);
		}
	}
	
	@Override
	public boolean checkWithdrawDataValid(BankCard bankCard, String price, Long userId, 
			String paymentCode) throws AccountException {
		
		try {
			//参数验证
			AssertUtil.notNull(bankCard, "Param[cardId] can not be null.");
			AssertUtil.notNull(price, "Param[price] can not be null.");
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(paymentCode, "Param[paymentCode] can not be null.");
			Account account = accountService.getValidAccountByRemoteId(String.valueOf(userId), Account.REMOTE_TYPE_USER);
			AssertUtil.isTrue(account.getPaymentCode().equals(DigestUtils.jzShaEncrypt(paymentCode)), 
					"The paymentcode{} is not equel with {}", "支付密码输入错误", paymentCode, account.getPaymentCode());
			Long withdrawPrice = CalculateUtils.converYuantoPenny(price);
			AssertUtil.isTrue(withdrawPrice > 0, "");
			AssertUtil.isTrue(withdrawPrice <= account.getWithdrawAmount(),
					"Amount[{}] is less than input value[{}], can't withdraw.",
					account.getWithdrawAmount(), withdrawPrice);
			//提现次数验证
			Integer count = this.getLastWithdrawCount(userId);
			AssertUtil.isTrue(0!=ConstantsMethod.nvlInteger(count), "ACCT_40010_The count of day's withdraw is reach the biggest." , "超过每日最大提现次数");
			//银行卡信息验证
			AssertUtil.notNull(bankCard, "The bankcard is not exists.");
		    return true;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	@Override
	public Integer getLastWithdrawCount(Long userId) throws AccountException {
		try {
			Integer count = accountService.getLastWithdrawCount(userId);
			return count;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	//!!!!不能添加事务  !!!!!!不能添加事务  !!!!!!!不能添加事务
	private WithdrawRsp excuteWithdraw(WithdrawReq withdrawReq, Long cardId, Long accountId, String cellPhone) {
		
		try {
			/** 冻结金额&&冻结金额纪录 */
			try {
				accountService.freeze(withdrawReq,accountId, withdrawReq.getTransferAmount(), withdrawReq.getTransferSerino());
			} catch (Exception e) {
				this.afterFail(withdrawReq, "冻结金额时发生异常", accountId);  //提现失败处理
				return null;
			}
			
			/** 提现申请 */
			WithdrawRsp withdrawRsp = null;
			try {
				withdrawRsp = withdrawService.withdrawFromJz(withdrawReq);
			} catch(Exception e){
				//忽略
				_withdrawLogger.info("[提现服务端]提现处理发生异常", e);
			}
			_withdrawLogger.info("[提现日志]提现结果参数："+String.valueOf(withdrawRsp));
			
			/** 提现结果处理    */
			if (null == withdrawRsp || Constants.WD_STATE_DEAL.equals(withdrawRsp.getStates())) {
				this.afterDeal(withdrawReq, withdrawRsp, accountId, cellPhone, cardId);  //提现中处理
			} else if (Constants.WD_STATE_SUCC.equals(withdrawRsp.getStates())) {
				this.afterSuccess(withdrawReq, withdrawRsp, cardId); //提现成功处理
			} else {
				this.afterFail(withdrawReq, withdrawRsp.getErrorMsg(), accountId);  //提现失败处理
			}
			return withdrawRsp;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	
	/**
	 * 提现参数生产
	 * 
	 * @Title: withdrawProductor 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param bankCardDto
	 * @param price
	 * @param account
	 * @return
	 * @date 2015年1月6日 上午9:44:44  
	 * @author Administrator
	 */
	private WithdrawReq withdrawProductor(BankCard bankCard, String price, Long accountId, Long userId) 
			throws AccountException {
		
		Assert.notNull(bankCard, "参数不能为空");
		Assert.hasLength(price, "参数不能为空");
//		Assert.notNull(account, "参数不能为空");
		try {
			String transferSerino = withdrawService.getWithdrawSN();
			WithdrawReq withdrawReq = new WithdrawReq();
			withdrawReq.setAccountId(accountId);
			withdrawReq.setUserId(userId);
//			withdrawReq.setShopNo(account.getShopNo());
			withdrawReq.setUserName(bankCard.getName());  //用户名
			withdrawReq.setReciveAccountNo(bankCard.getBankCard());  //账号
			Bank bank = Bank.getBankByCode(bankCard.getBankCode());
			withdrawReq.setBankName(bank.getName());  //开户银行名称
			withdrawReq.setBankCode(bankCard.getBankCode());  //开户银行编号
			withdrawReq.setProvince(null);  //所在省
			withdrawReq.setCity(null);   //所在市
			withdrawReq.setTransferAmount(CalculateUtils.converYuantoPenny(price));
			withdrawReq.setTransferSerino(transferSerino);
//			if (Bank.NINGBO_BANK.getBankCode().equals(bankCard.getBankCode())) {
//				withdrawReq.setServiceType(Constants.SERVICE_PEER_WD);   //服务编号:内部转账
//			} else {
				withdrawReq.setServiceType(Constants.SERVICE_OUTER_WD);   //服务编号:外部转账
//			}
			return withdrawReq;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "AccountException"})
	public void afterSuccess(WithdrawReq withdrawReq, WithdrawRsp withdrawRsp,
			 Long cardId) throws AccountException {
		
		/**成功处理*/
		try {
			String serialNo = withdrawReq.getTransferSerino();
			//成功资金流转
			accountService.transferredFreeze(withdrawReq.getAccountId(), withdrawReq.getTransferAmount(), serialNo);
			_withdrawLogger.info("[提现日志]提现资金流转成功。");
			
			//提现记录更新
			withdrawService.editWithdrawRecordBySerilNo(serialNo, Constants.WD_STATE_SUCC, null);
			_withdrawLogger.info("[提现日志]提现记录更新，状态：成功");
		} catch (Exception e) {
			_withdrawLogger.error("[提现日志]提现成功，处理资金流转发生异常。", e);
			throw new AccountException(e);
		}
//		try {
//			//激活提现银行卡
//			bankCardProducer.activationBankCard(cardId);
//			_withdrawLogger.info("[提现日志]激活提现银行卡成功");
//		} catch (Exception e){
//			_withdrawLogger.error("[提现日志]提现成功，激活提现银行卡发生异常。", e);
//		}
		_withdrawLogger.info("[提现日志]提现成功");
	}
	
	/**
	 * 提现中处理
	 * 
	 * @Title: afterDeal 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param withdrawReq
	 * @param withdrawRsp
	 * @param account
	 * @throws AccountException
	 * @date 2015年1月6日 上午10:30:58  
	 * @author Administrator
	 * @param cardId 
	 */
	private void afterDeal(WithdrawReq withdrawReq, WithdrawRsp withdrawRsp, Long accountId, String cellPhone, 
			Long cardId) {
		
		try {
			Date date = new Date();
			//提现处理中，查询任务开启
//			withdrawInfoQueryJobManager.queryWithdrawJob(withdrawRsp, withdrawReq, cardId, accountId, cellPhone, date);
			withdrawJobProducer.queryWithdrawJob(withdrawRsp, withdrawReq, cardId, accountId, cellPhone, date);
			_withdrawLogger.info("[提现日志]提现结果：提现中， 开启提现任务查询");
		} catch (Exception e) {
			_withdrawLogger.error("[提现日志]开启提现查询任务发生异常",e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "AccountException"})
	public void afterFail(WithdrawReq withdrawReq, String errorMsg, Long accountId) 
			throws AccountException {
		
		try {
			String serialNo = withdrawReq.getTransferSerino();
			//提现金额解冻
			accountService.returnFreeze(accountId, withdrawReq.getTransferAmount(), serialNo);
			StringBuilder sb = new StringBuilder();
			sb.append("[提现日志]提现金额解冻成功，流水号：").append(serialNo).append("，金额：").append(withdrawReq.getTransferAmount());
			_withdrawLogger.info(sb.toString());
			
			//提现记录更新
			WithdrawRecord record = withdrawService.getWithdrawRecordByNo(serialNo);
			if (null != record) {
				withdrawService.editWithdrawRecordBySerilNo(serialNo, Constants.WD_STATE_FAIL, errorMsg);
				sb.delete(0, sb.length()).append("[提现日志]提现记录更新成功, 流水号=").append(serialNo).append("，状态：失败， 错误信息：").append(errorMsg);
				_withdrawLogger.info(sb.toString());
			} else {
				sb.delete(0, sb.length()).append("[提现日志]提现记录不存在, 流水号=").append(withdrawReq.getTransferSerino());
				_withdrawLogger.info(sb.toString());
			}
		} catch (Exception e) {
			_withdrawLogger.error("[提现日志]提现失败处理发生异常", e);
			throw new AccountException(e);
		}
		_withdrawLogger.info("[提现日志]提现结果：提现失败");
	}
	
	/**
	 * 可用余额不足提醒(单位：元)
	 * 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2014年10月15日 下午3:06:33  
	 * @author Administrator
	 * @param jzBalanceBfAf 
	 */
	private void warnForBalance(String serialNo) {
		try {
			if (!"1".equals((String) PropertiesUtil.getContextProperty("SWITCH_BALANCE_WARN"))) {
				_withdrawLogger.info("提现余额提醒开关关闭.");
			}
			String balanceParam = withdrawService.balanceQuery(serialNo);
			if(StringUtils.isBlank(balanceParam)) {
				_withdrawLogger.error("提现后可用提现余额查询失败:"+balanceParam);
				return;
			}
			_withdrawLogger.info("可用提现余额："+balanceParam+"元");
			Long balance = CalculateUtils.converYuantoPenny(balanceParam);
			String configBalance = (String) PropertiesUtil.getContextProperty("BALACE_TO_WARN");
			if(StringUtils.isBlank(configBalance) || !StringUtils.isNumeric(configBalance)) {
				_withdrawLogger.info("最低可提现金额配置错误："+configBalance);
			}
			String phone = (String) PropertiesUtil.getContextProperty("PHONE_TO_WARN");
			Long jzBalance = CalculateUtils.converYuantoPenny(configBalance);
			if (jzBalance.compareTo(balance) >= 0) {
				//短信提醒
				String dateString = DateUtil.DateToString(new Date(), DateStyle.MM_DD_HH_MM_SS_CN);
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_WITHDRAW_SWITCH, SmsContent.UMS_108, phone, dateString, balanceParam+"元");
			}
		} catch (Exception e) {
			_withdrawLogger.error("最低可提现余额警告发生异常："+e.getMessage(), e);
		}
	}

	@Override
	public Pagination<WithdrawRecordDto> findRecordPageForUser(
			Pagination<WithdrawRecordDto> pagination,
			WithdrawRecordDto recordDto) throws AccountException {
		
		Assert.notNull(pagination, "Param [pagination] can not be null.");
		Assert.notNull(recordDto, "Param [recordDto] can not be null.");
		Assert.notNull(recordDto.getRemoteId(), "Param [recordDto.remoteId] can not be null.");
		Assert.notNull(recordDto.getRemoteType(), "Param [recordDto.remoteType] must be null.");
		try {
			pagination = withdrawService.findRecordPage(pagination, recordDto);
			return pagination;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}

	@Override
	@Deprecated
	public Pagination<WithdrawRecordDto> findRecordPageForShop(
			Pagination<WithdrawRecordDto> pagination,
			WithdrawRecordDto recordDto) throws AccountException {
		
		Assert.notNull(pagination, "Param [pagination] can not be null.");
		Assert.notNull(recordDto, "Param [recordDto] can not be null.");
		Assert.notNull(recordDto.getRemoteId(), "Param [recordDto.remoteId] can not be null.");
		Assert.isNull(recordDto.getRemoteType(), "Param [recordDto.remoteType] must be null.");
		try {
//			pagination = withdrawService.findRecordPage(pagination, recordDto);
			return pagination;
		} catch (Exception e) {
			throw new AccountException(e);
		}
	}
	
}
