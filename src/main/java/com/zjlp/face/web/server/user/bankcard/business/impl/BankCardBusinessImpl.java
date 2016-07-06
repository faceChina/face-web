package com.zjlp.face.web.server.user.bankcard.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.account.dto.Receivables;
import com.zjlp.face.account.dto.WapPayRsp;
import com.zjlp.face.account.service.BindPayService;
import com.zjlp.face.account.service.PaymentService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Bank;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCardDto;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.bankcard.service.BankCardService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;

@Repository("bankCardBusiness")
public class BankCardBusinessImpl implements BankCardBusiness {

//	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private BankCardService bankCardService;
	@Autowired(required=false)
	private PaymentService paymentService;
	@Autowired
	private UserService userService;
	@Autowired
	private BindPayService bindPayService;

	@Deprecated  //不要直接调用
	@Transactional
	@Override
	public Long addPayBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv) throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(bankCode, "Param[bankCode] can not be null.");
			AssertUtil.notNull(cardNo, "Param[cardNo] can not be null.");
			AssertUtil.notNull(cell, "Param[cell] can not be null.");
			//卡bin验证
			BankCard cardBin = this.getCardInfo(cardNo);
			//用户验证
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			AssertUtil.notNull(user.getIdentity(), "User[id={}]'s identity is null.", userId);
			AssertUtil.notNull(user.getContacts(), "User[id={}]'s contacts is null.", userId);
			return this.addBankCard(userId, user.getContacts(), user.getIdentity(), BankCardDto.USERFOR_PAY, 
					bankCode, cardBin.getBankType(), cardNo, cardBin.getBankName(), cell, endTime, cvv);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	@Transactional
	public Long addBindPayBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv, String code)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(bankCode, "Param[bankCode] can not be null.");
			AssertUtil.notNull(cardNo, "Param[cardNo] can not be null.");
			AssertUtil.notNull(cell, "Param[cell] can not be null.");
			AssertUtil.notNull(code, "Param[code] can not be null.");
			//卡bin验证
			BankCard cardBin = this.getCardInfo(cardNo);
			//用户验证
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			AssertUtil.notNull(user.getIdentity(), "User[id={}]'s identity is null.", userId);
			AssertUtil.notNull(user.getContacts(), "User[id={}]'s contacts is null.", userId);
			//添加银行卡
			Long cardId = this.addBankCard(userId, user.getContacts(), user.getIdentity(), BankCardDto.USERFOR_PAY, 
					bankCode, cardBin.getBankType(), cardNo, cardBin.getBankName(), cell, endTime, cvv);
			//银行卡绑定
			this.bind(userId, cardId, cardNo, cell, cardBin.getBankType(), cvv, endTime, code);
			return cardId;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	@Transactional
	private void bind(Long userId, Long cardId, String cardNo, String cell, 
			Integer bankType, String cvv, String endTime, String code) {
		//银行卡绑定开关
		String bindSwitch = PropertiesUtil.getContexrtParam("SWITCH_BANKCARD_BIND");
		if (null == bindSwitch || !"1".equals(bindSwitch)) { //1以外表示关闭
			return;
		}
		//绑定银行卡
		String serialNumber = paymentService.getTransactionSerialNumber(String.valueOf(userId), 6);
		String bindId = this.bindBankcard(userId, cardNo, cell, bankType, cvv, endTime, code, serialNumber);
		//更新银行卡
		bankCardService.editBindId(cardId, bindId);
		//钱转出接口
		Receivables param = new Receivables();
		param.setFee(Long.valueOf(PropertiesUtil.getContexrtParam("FEE_BIND_CARD"))); //手续费5毛
		param.setChannel(2); //渠道：捷蓝支付
		param.setTransactionSerialNumber(serialNumber);
//		param.setTransactionSerialNumber("10568646468464855123");
		paymentService.bindFeeReceivables(param);
	}

	@Deprecated  //不要直接调用
	@Transactional
	@Override
	public Long addSettleBankCard(Long userId, String bankCode, String cardNo,
			String cell, String endTime, String cvv) throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(bankCode, "Param[bankCode] can not be null.");
			AssertUtil.notNull(cardNo, "Param[cardNo] can not be null.");
//			AssertUtil.notNull(cell, "Param[cell] can not be null.");
			//判断是否已经存在此卡
			BankCard card = bankCardService.getIdenticalValidCard(String.valueOf(userId), 
					BankCard.REMOTE_TYPE_USER, cardNo, BankCard.USERFOR_SETTLE);
			if (null != card) {
				return card.getId();
			}
			//卡bin验证
			BankCard cardBin = this.getCardInfo(cardNo);
			//用户验证
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			AssertUtil.notNull(user.getIdentity(), "User[id={}]'s identity is null.", userId);
			AssertUtil.notNull(user.getContacts(), "User[id={}]'s contacts is null.", userId);
			return this.addBankCard(userId, user.getContacts(), user.getIdentity(), BankCardDto.USERFOR_SETTLE, 
					bankCode, cardBin.getBankType(), cardNo, cardBin.getBankName(), cell, endTime, cvv);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	@Override
	@Transactional
	public Long addBindSettleBankCard(Long userId, String bankCode,
			String cardNo, String cell, String endTime, String cvv, String code)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(bankCode, "Param[bankCode] can not be null.");
			AssertUtil.notNull(cardNo, "Param[cardNo] can not be null.");
			//判断是否已经存在此卡
//			BankCard card = bankCardService.getIdenticalValidCard(String.valueOf(userId), 
//					BankCard.REMOTE_TYPE_USER, cardNo, BankCard.USERFOR_SETTLE);
//			if (null != card) {
//				return card.getId();
//			}
			//卡bin验证
			BankCard cardBin = this.getCardInfo(cardNo);
			//提现卡只能为借记卡
			this.filterCreditCard(cardBin.getBankType());
			//用户验证
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			AssertUtil.notNull(user.getIdentity(), "User[id={}]'s identity is null.", userId);
			AssertUtil.notNull(user.getContacts(), "User[id={}]'s contacts is null.", userId);
			//添加银行卡
			Long cardId = this.addBankCard(userId, user.getContacts(), user.getIdentity(), BankCardDto.USERFOR_SETTLE, 
					bankCode, cardBin.getBankType(), cardNo, cardBin.getBankName(), cell, endTime, cvv);
			//银行卡绑定
			this.bind(userId, cardId, cardNo, cell, cardBin.getBankType(), cvv, endTime, code);
			return cardId;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	private void filterCreditCard(Integer bankType) {
		//银行卡绑定开关
		String withdrawCreditSwitch = PropertiesUtil.getContexrtParam("SWITCH_BANKCARD_WITHDRAW_CREDIT");
		if (null != withdrawCreditSwitch && "1".equals(withdrawCreditSwitch)) { //1以外表示关闭
			return;
		}
		AssertUtil.isTrue(BankCardDto.TYPE_DEBIT.equals(bankType), 
				"withdraw card can't be credit card.", "请务必添加借记卡");
	}
	
	@Transactional
	private Long addBankCard(Long userId, String userName, String identity, Integer type, String bankCode, 
			Integer bankType, String cardNo, String bankName,
			String cell, String endTime, String cvv) {
		try {
			AssertUtil.notNull(type, "Param[type] can not be null.");
			//支持银行验证
			this.checkBank(bankCode, bankName, type);
			//银行卡初始化
			String remoteId = String.valueOf(userId);
			Integer remoteType = BankCard.REMOTE_TYPE_USER;
			BankCard bankCard = this.initBankCard(remoteId, remoteType, bankType, 
					type, cardNo, bankCode, bankName, endTime, cvv, userId, 
					cell, identity, userName);
			return bankCardService.addBankCard(bankCard);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	/**
	 * 添加银行卡是否为平台支持的银行卡（提现/支付）
	 * @Title: checkBank 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param bankCode 银行编号
	 * @param bankName 银行名称
	 * @param type
	 * @date 2015年3月19日 上午9:35:24  
	 * @author lys
	 */
	private void checkBank(String bankCode, String bankName, Integer type) {
		if (BankCard.USERFOR_PAY.equals(type)) {
			Bank bank = Bank.getBankByCode(bankCode);
			AssertUtil.notNull(bank, "Platform do not support the bank[code={}, name={}]", "不支持【{}】的银行卡",
					bankCode, bankName, bankName);
		} else {
			boolean istrue = Bank.containWithdrawBank(bankCode);
			AssertUtil.isTrue(istrue, "Platform do not support the withdraw bank[code={}, name={}]", 
					"不支持【{}】的银行卡", bankCode, bankName, bankName);
		}
	}
	
	/**
	 * 四种银行卡（支付借记卡，支付信用卡，提现借记卡，提现信用卡）的初始化
	 * @Title: initBankCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param remoteId 外键
	 * @param remoteType 外键类型
	 * @param bankType 银行卡类型（借记卡/信用卡）
	 * @param type 使用类型（提现/支付）
	 * @param cardNo 卡号
	 * @param bankCode 银行编号
	 * @param bankName 银行名称
	 * @param endTime 期限
	 * @param cvv 卡后三位数
	 * @param userId 用户id
	 * @param cell 银行预留手机号（无法确认）
	 * @param identity（身份证号）
	 * @param userName 用户名
	 * @return
	 * @date 2015年3月19日 上午9:32:38  
	 * @author lys
	 */
	private BankCard initBankCard(String remoteId, Integer remoteType,
			Integer bankType, Integer type, String cardNo, String bankCode,
			String bankName, String endTime, String cvv, Long userId,
			String cell, String identity, String userName) {
		BankCard bankCard = null;
		if (BankCard.TYPE_CREDIT.equals(bankType)) {
			AssertUtil.notNull(endTime, "Param[endTime] can not be null.");
			AssertUtil.notNull(cvv, "Param[cvv] can not be null.");
			if (BankCard.USERFOR_PAY.equals(type)) {
				bankCard = BankCardDto.InitCreditCardForPay(remoteId, remoteType, bankCode, 
						bankName, cardNo, cell, userName, identity, endTime, cvv);
			} else {
				bankCard = BankCardDto.InitCreditCardForSettle(remoteId, remoteType, bankCode,
						bankName, cardNo, cell, userName, identity, endTime, cvv);
			}
		} else if (BankCard.TYPE_DEBIT.equals(bankType)) {
			if (BankCard.USERFOR_PAY.equals(type)) {
				bankCard = BankCardDto.InitDebitCardForPay(remoteId, remoteType, bankCode, 
						bankName, cardNo, cell, userName, identity);
			} else {
				bankCard = BankCardDto.InitDebitCardForSettle(remoteId, remoteType, bankCode, 
						bankName, cardNo, cell, userName, identity);
			}
		}
		return bankCard;
	}


	@Deprecated
	@Override
	public BankCard getCardBin(String cardNo) throws BankCardException {
		try {
			WapPayRsp rsp = paymentService.getCardBin(cardNo);
			if (!"0000".equals(rsp.getRet_code())) {
				return null;
			}
			AssertUtil.notNull(rsp, "Get card bin faild.", "请输入正确的银行卡号");
			AssertUtil.isTrue("0000".equals(rsp.getRet_code()), "Get card bin's result is {}, faild.", 
					"请输入正确的银行卡号", rsp.getRet_code());
			BankCard bankCard = new BankCard();
			bankCard.setBankCard(cardNo);
			bankCard.setBankCode(rsp.getBank_code());
			bankCard.setBankType(Integer.valueOf(rsp.getCard_type()));
			bankCard.setBankName(rsp.getBank_name());
			return bankCard;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getCardInfo(String cardNo) throws BankCardException {
		try {
			AssertUtil.hasLength(cardNo, "param[cardNo] can't be null.", "请输入正确的银行卡号");
			String jsonStr = bindPayService.cardInfo(cardNo);
			AssertUtil.hasLength(jsonStr, "Can't obtain bank card info by bankcard[cardno={}]", 
					"获取银行卡信息失败", cardNo);
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.get("flag")), "Get card bin's result is {}, faild.", 
					"请输入正确的银行卡号", jsonObject.get("flag"));
			AssertUtil.hasLength(jsonObject.getString("bankName"), "Bank card[cardNo={}] is not exists.", 
					"请输入正确的银行卡号", cardNo);
			BankCard bankCard = new BankCard();
			bankCard.setBankCard(cardNo);
			bankCard.setBankCode(jsonObject.getString("bankId")+"0000");
			bankCard.setBankType(Integer.valueOf(jsonObject.getString("cardType")));
			bankCard.setBankName(jsonObject.getString("bankName"));
			return bankCard;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public List<BankCard> findPayCardList(Long userId) throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<BankCard> list = bankCardService.findPayCardList(userId);
			return list;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public List<BankCard> findSettleCardList(Long userId)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<BankCard> list = bankCardService.findSettleCardList(userId);
			return list;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	@Override
	public BankCard getIdenticalValidCard(Long userId, String cardNo,
			Integer type) throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.hasLength(cardNo, "Param[cardNo] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			BankCard identicalCard = bankCardService.getIdenticalValidCard(String.valueOf(userId),
					BankCard.REMOTE_TYPE_USER, cardNo, type);
			return identicalCard;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public boolean removeBankCard(Long userId, Long cardId)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(cardId, "Param[cardId] can not be null.");
			BankCard card = bankCardService.getValidBankCardById(cardId);
			//数据权限验证
			AssertUtil.isTrue(userId.equals(Long.valueOf(card.getRemoteId())), 
					"The bankCard[id={}, remoteId={}] is not belong to user[id={}], can't remove.",
					cardId, card.getRemoteId(), userId);
			//解约
			bankCardService.editCardStatus(cardId, Constants.UNVALID);
			if (StringUtils.isNotBlank(card.getBindId())) {
				bindPayService.unbind(card.getBindId());
			}
			return true;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public int getCardNumber(Long userId, Integer type)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			int number = bankCardService.getCardNumber(String.valueOf(userId),
					BankCard.REMOTE_TYPE_USER, type);
			return number;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getBankCardById(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			return bankCardService.getBankCardByPk(id);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public void setDefaultCard(Long userId, Long cardId)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(cardId, "Param[cardId] can not be null.");
			bankCardService.setDefaultBankCard(cardId, userId);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public String bindBankcard(Long userId, String cardNo, String mobile,
			Integer cardType, String cvn, String expire, String code, String serialNumber)
			throws BankCardException {
		try {
			//参数验证
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(cardNo, "Param[cardNo] can not be null.");
			AssertUtil.notNull(mobile, "Param[mobile] can not be null.");
			AssertUtil.notNull(cardType, "Param[cardType] can not be null.");
			AssertUtil.notNull(code, "Param[code] can not be null.");
			AssertUtil.isTrue(BankCardDto.TYPE_DEBIT.equals(cardType) || BankCardDto.TYPE_CREDIT.equals(cardType), 
					"param[cardType] is not in (2, 3)");
			//用户信息获取
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] is not exists.", userId);
			AssertUtil.notNull(user.getContacts(), "User[id={}]'s contacts is null.", userId);
			//绑定接口调用
			String bindParam = getBindParam(user.getContacts(), cardNo, mobile, cardType, cvn, expire, code, serialNumber);
			String jsonStr = bindPayService.mobileCBindNA(bindParam);
			//绑定结果
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.get("flag")), "bind bank card faild.", jsonObject.get("desc").toString());
			return jsonObject.getString("bindId");
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	//绑定参数组装
	private String getBindParam(String accName, String cardNo, String mobile,
			Integer cardType, String cvn, String expire, String code, String serialNumber) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (BankCardDto.TYPE_CREDIT.equals(cardType)) {
			AssertUtil.notNull(cvn, "Param[cvn] can not be null.");
			AssertUtil.notNull(expire, "Param[expire] can not be null.");
			param.put("cvn", cvn);
			param.put("expire", expire);
		}
		param.put("accName", accName);
		param.put("cardNo", cardNo);
		param.put("mobile", mobile);
		param.put("cardType", cardType);
		param.put("code", code);
		param.put("transactionSerialNumber", serialNumber);
		return JSONObject.fromObject(param).toString();
	}

	@Override
	public boolean sendBankMobilecode(String cell) throws BankCardException {
		try {
			AssertUtil.notNull(cell, "Param[cell] can not be null.");
			String jsonStr = bindPayService.sendCode(cell);
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			return "SUCCESS".equals(jsonObject.get("flag"));
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public List<BankCard> findBankCardListByUserId(Long userId) {
		return bankCardService.findBankCardListByUserId(userId);
	}

	@Override
	public List<BankCardVo> findWithdrawCardListByUserId(Long userId) {
		List<BankCard> bankCardList=bankCardService.findBankCardListByUserId(userId);
		List<BankCardVo> withdrawCardList=new ArrayList<BankCardVo>();
		for(BankCard bc:bankCardList){
			BankCardVo vo=new BankCardVo(bc);
			if(vo.getIsWithdrawCard()){
				withdrawCardList.add(vo);
			}
		}
		return withdrawCardList;
	}
	
}
