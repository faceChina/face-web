package com.zjlp.face.web.ctl.wap;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.web.constants.Bank;
import com.zjlp.face.web.constants.MobilecodeType;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.account.business.WithdrawBusiness;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCardDto;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

@Controller
@RequestMapping("/wap/{shopNo}/buy/account/withdraw/")
public class WithdrawWapCtl extends WapCtl {

	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private WithdrawBusiness withdrawBusiness;
	@Autowired
	private BankCardBusiness bankCardBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	private static final String[] froms = {"list", "add"};
	
	/**
	 * 提现首页
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param retUrl
	 * @param model
	 * @return
	 * @date 2015年3月24日 上午11:02:03  
	 * @author lys
	 */
//	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(String retUrl, String errorMsg, Model model) {
		Long userId = super.getUserId();
		List<BankCard> cardList = bankCardBusiness.findSettleCardList(userId);
		model.addAttribute("errorMsg", errorMsg);
		if (null == cardList || cardList.isEmpty()) {
			return "/wap/trade/account/withdraw/withdraw-no-card";
		}
		model.addAttribute("from", froms[0]);
		return super.getRedirectUrl("withdraw");
	}
	
	/**
	 * 添加提现卡
	 * @Title: add 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月24日 上午11:08:43  
	 * @author lys
	 */
//	@Token(save=true)
//	@RequestMapping(value="add", method=RequestMethod.POST)
	public String add(String retUrl, Long id, String price, String paymentCode, Model model) {
		boolean istrue = accountBusiness.checkPaymentCode(super.getUserId(), paymentCode);
		if (!istrue) {
			model.addAttribute("from", froms[1]);
			model.addAttribute("id", id);
			model.addAttribute("price", price);
			model.addAttribute("errorMsg", "支付密码错误，请重试");
			return super.getRedirectUrl(retUrl);
		} else {
			model.addAttribute("errorMsg", null);
		}
		User user = userBusiness.getUserById(getUserId());
		UserVo vo = new UserVo(user);
		model.addAttribute("bankTypes", Bank.withdrawCardList);
		model.addAttribute("user", vo);
		return "/wap/trade/account/withdraw/add";
	}
	
	/**
	 * 添加提现卡
	 * @Title: addCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param bankCode 银行卡编号
	 * @param cardNo 卡号
	 * @param endTime 信用卡年限
	 * @param cvv 信用卡后三位数
	 * @return
	 * @date 2015年3月24日 上午11:14:43  
	 * @author lys
	 */
//	@Token(remove=true)
//	@RequestMapping(value="toadd", method=RequestMethod.POST)
	@ResponseBody
	public String addCard(@RequestParam String bankCode, @RequestParam String cardNo, 
			@RequestParam String cell, @RequestParam String mobilecode,
			String endTime, String cvv, Model model) {
		try {
			Long id = bankCardBusiness.addBindSettleBankCard(super.getUserId(), 
					bankCode, cardNo, cell, endTime, cvv, mobilecode);
			return super.getReqJson(true, String.valueOf(id));
		} catch (BankCardException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	/**
	 * 提现
	 * @Title: withdraw 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardList 银行卡列表
	 * @param model
	 * @return
	 * @date 2015年3月24日 上午11:20:20  
	 * @author lys
	 */
	@Token(save=true)
//	@RequestMapping(value="withdraw", method=RequestMethod.GET)
	public String withdraw(@RequestParam String from, Long id, String price, String errorMsg, Model model) {
		Long userId = super.getUserId();
		Long amount = accountBusiness.getWithdrawAmount(userId);
		Integer withdrawCount = accountBusiness.getWithdrawCount(userId);
		User user = userBusiness.getUserById(userId);
		model.addAttribute("amount", amount);
		model.addAttribute("withdrawCount", withdrawCount);
		model.addAttribute("phone", ConstantsMethod.replaceToHide(user.getCell(), 3, 4));
		//银行卡信息
		List<BankCardVo> cardList = null;
		if (from.equals(froms[0])) {
			List<BankCard> list = bankCardBusiness.findSettleCardList(userId);
			cardList = BankCardDto.coverCardsForView(list);
		} else {
			BankCard card = bankCardBusiness.getBankCardById(id);
			cardList = new ArrayList<BankCardVo>();
			cardList.add(new BankCardVo(card));
		}
		model.addAttribute("from", from);
		model.addAttribute("defaultCard", BankCardDto.getDefaultCard(cardList));
		model.addAttribute("cardList", cardList);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("price", price);
		return "/wap/trade/account/withdraw/withdraw";
	}
	
	/**
	 * 提现
	 * @Title: toWithdraw 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 卡id
	 * @param price 金额（元）
	 * @param paymentCode 支付密码
	 * @param mobilecode 手机验证码
	 * @return
	 * @date 2015年3月24日 上午11:26:36  
	 * @author lys
	 */
//	@Token(remove=true)
//	@RequestMapping(value="toWithdraw", method=RequestMethod.POST)
	public String toWithdraw(@RequestParam Long cardId, @RequestParam String price,
			@RequestParam String paymentCode, @RequestParam String mobilecode, 
			@RequestParam String from, Model model) {
		model.addAttribute("from", from);
		model.addAttribute("id", cardId);
		model.addAttribute("price", price);
		Long userId = super.getUserId();
		//手机验证
		User user = userBusiness.getUserById(userId);
		try {
			phoneBusiness.checkMobilecode(user.getCell(), MobilecodeType.mobile_tx_0.getScene(), mobilecode);
		} catch (BaseException e) {
			model.addAttribute("errorMsg", "验证码输入错误");
			return super.getRedirectUrl("withdraw");
		}
		try {
			Account account = accountBusiness.getAccountByUserId(userId);
			withdrawBusiness.withdraw(cardId, price, account.getId(), userId, paymentCode, user.getCell());
			return "/wap/trade/account/withdraw/success";
		} catch (BaseException e) {
			model.addAttribute("errorMsg", e.getExternalMsg());
			return super.getRedirectUrl("withdraw");
		}
		
	}
	
	/**
	 * 卡bin验证
	 * 
	 * @Title: cardbin
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param cardNo
	 *            验证卡号
	 * @return
	 * @date 2015年3月24日 下午2:41:15
	 * @author lys
	 */
//	@RequestMapping(value="cardbin", method=RequestMethod.POST)
//	@ResponseBody
	public String cardbin(@RequestParam String cardNo) {
		try {
			BankCard old = bankCardBusiness.getIdenticalValidCard(super.getUserId(),
					cardNo, BankCardDto.USERFOR_SETTLE);
			if (null != old) {
				return super.getReqJson(false, "该卡已存在，请直接选择该卡");
			}
			BankCard card = bankCardBusiness.getCardInfo(cardNo);
			if (null == card) {
				return super.getReqJson(false, "请输入正确的银行卡号");
			} else if (BankCardDto.TYPE_CREDIT.equals(card.getBankType())) {
				return super.getReqJson(false, "暂不支持信用卡提现");
			}
			return super.getReqJson(true, JSONObject.fromObject(card).toString());
		} catch (BaseException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	//卡bin移除    ===》  修改
	/**
	 * 提现
	 * @Title: withdraw 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardList 银行卡列表
	 * @param model
	 * @return
	 * @date 2015年3月24日 上午11:20:20  
	 * @author lys
	 */
	@Token(save=true)
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String withdraw2(String price, String bankCard, String bankCode, String errorMsg, Model model) {
		Long userId = super.getUserId();
		Long amount = accountBusiness.getWithdrawAmount(userId);
		Integer withdrawCount = accountBusiness.getWithdrawCount(userId);
		User user = userBusiness.getUserById(userId);
		model.addAttribute("amount", amount);
		model.addAttribute("withdrawCount", withdrawCount);
		model.addAttribute("user", new UserVo(user));
		model.addAttribute("bankList", Bank.withdrawCardList);
		model.addAttribute("price", price);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("bankCard", bankCard);
		model.addAttribute("bankCode", bankCode);
		return "/wap/trade/account/withdraw/alt/withdraw";
	}
	
	/**
	 * 提现
	 * @Title: toWithdraw 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 卡id
	 * @param price 金额（元）
	 * @param paymentCode 支付密码
	 * @param mobilecode 手机验证码
	 * @return
	 * @date 2015年3月24日 上午11:26:36  
	 * @author lys
	 */
	@Token(remove=true)
	@RequestMapping(value="toWithdraw", method=RequestMethod.POST)
	public String toWithdraw2(BankCard bankCard, @RequestParam String price,
			@RequestParam String paymentCode, @RequestParam String mobilecode, 
			Model model) {
		model.addAttribute("price", price);
		model.addAttribute("bankCard", bankCard.getBankCard());
		model.addAttribute("bankCode", bankCard.getBankCode());
		Long userId = super.getUserId();
		//手机验证
		User user = userBusiness.getUserById(userId);
		try {
			phoneBusiness.checkMobilecode(user.getCell(), MobilecodeType.mobile_tx_0.getScene(), mobilecode);
		} catch (BaseException e) {
			model.addAttribute("errorMsg", "验证码输入错误");
			return super.getRedirectUrl("index");
		}
		try {
			Account account = accountBusiness.getAccountByUserId(userId);
			bankCard.setName(user.getContacts());
			withdrawBusiness.withdraw(bankCard, price, account.getId(), userId, paymentCode, user.getCell());
			return "/wap/trade/account/withdraw/success";
		} catch (BaseException e) {
			model.addAttribute("errorMsg", e.getExternalMsg());
			return super.getRedirectUrl("index");
		}
		
	}
	
}
