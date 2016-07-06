package com.zjlp.face.web.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.constants.Bank;
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
import com.zjlp.face.web.util.Logs;

@Controller
@RequestMapping("/u/account/withdraw/")
public class WithdrawCtl extends BaseCtl {

	@Autowired
	private WithdrawBusiness withdrawBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private BankCardBusiness bankCardBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	
	/**
	 * 
	 * @Title: withdrawNew 
	 * @Description: 新的提现页面
	 * @param errorMsg 错误信息
	 * @param cardNo 银行卡号
	 * @param price 提现金额
	 * @param model 
	 * @return
	 * @date 2015年7月3日 上午9:20:30  
	 * @author cbc
	 */
	@Token(save=true)
	@RequestMapping(value="index", method = RequestMethod.GET)
	public String withdrawNew(String errMsg, String cardNo, String price, Model model, String bankCode) {
		boolean isExists = accountBusiness.existPaymentCode(getUserId());
		if (!isExists) {
			model.addAttribute("retUrl", "/u/account/withdraw/index");
			return super.getRedirectUrl("/u/account/security/paymentCode");
		}
		Long userId = super.getUserId();
		List<BankCardVo> cardList = bankCardBusiness.findWithdrawCardListByUserId(userId);
		Account account = accountBusiness.getAccountByUserId(userId);
		Integer withdrawCount = withdrawBusiness.getLastWithdrawCount(userId);
		model.addAttribute("withdrawAmount", account.getWithdrawAmount());
		model.addAttribute("cardList", cardList);
		model.addAttribute("withdrawCount", withdrawCount);
		model.addAttribute("errMsg", errMsg);
		if(cardList.size()==0){
			return "/m/trade/account/withdraw/no-bankcard";
		}else{
			return "/m/trade/account/withdraw/withdraw";
		}
		/*User user = userBusiness.getUserById(userId);
		UserVo userVo = new UserVo(user);
		model.addAttribute("userInfo", userVo);
		model.addAttribute("bankTypes", Bank.withdrawCardList);
		model.addAttribute("bankCode", bankCode);
		//验证码输入错误时，带入信息
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("price", price);
		return "/m/trade/account/withdraw/new/index";*/
	}
	
	
	/**
	 * 
	 * @Title: withdrawConfirm 
	 * @Description: 提交提现
	 * @param cardId 卡ID
	 * @param bankCode 
	 * @param cardNo 银行卡号
	 * @param price 提现金额
	 * @param paymentCode 支付密码
	 * @param mobilecode 手机验证码
	 * @param model
	 * @return
	 * @date 2015年7月3日 上午9:22:15  
	 * @author cbc
	 */
	@Token(remove=true)
	@RequestMapping(value="withdraw", method = RequestMethod.POST)
	public String withdrawConfirm(Long cardId,String bankCode, String cardNo, String price, @RequestParam String paymentCode,
			@RequestParam String mobilecode, Model model) {
		try {
			Long userId = getUserId();
			User user = userBusiness.getUserById(userId);
			BankCard card = new BankCard();
			card.setName(user.getContacts());
			card.setBankCard(cardNo);
			card.setBankCode(bankCode);
			Account account = accountBusiness.getAccountByUserId(userId);
			AssertUtil.notNull(account, "查无此账号");
			withdrawBusiness.withdraw(card, price, account.getId(), userId, paymentCode, user.getCell());
		} catch (BaseException e) {
			model.addAttribute("errorMsg", e.getExternalMsg());
			model.addAttribute("cardNo", cardNo);
			model.addAttribute("price", price);
			model.addAttribute("bankCode", bankCode);
			return super.getRedirectUrl("/u/account/withdraw/index");
		}
		return "/m/trade/account/withdraw/success";
	}
	@Token(remove=true)
	@RequestMapping(value="withdrawV2", method = RequestMethod.POST)
	public String withdrawConfirmV2(Long cardId,String bankCode, String cardNo, String price, @RequestParam String paymentCode,
			@RequestParam String mobilecode, Model model) {
		try {
			Long userId = getUserId();
			User user = userBusiness.getUserById(userId);
			BankCard bc=bankCardBusiness.getBankCardById(cardId);
			BankCard card = new BankCard();
			card.setName(user.getContacts());
			card.setBankCard(bc.getBankCard());
			card.setBankCode(bc.getBankCode());
			Account account = accountBusiness.getAccountByUserId(userId);
			AssertUtil.notNull(account, "查无此账号");
			Logs.print(card.getId()+","+price+","+account.getId()+","+userId+","+paymentCode+","+user.getCell());
			withdrawBusiness.withdraw(card, price, account.getId(), userId, paymentCode, user.getCell());
		} catch (BaseException e) {
			model.addAttribute("errMsg", e.getExternalMsg());
			return super.getRedirectUrl("/u/account/withdraw/index");
		}
		return "/m/trade/account/withdraw/success";
	}
	
	/**
	 * 银行卡提现
	 * @Title: toWithdrawal 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 卡id
	 * @param withdrawPrice 提现金额
	 * @param paymentCode 支付密码
	 * @return
	 * @date 2015年3月25日 上午10:31:35  
	 * @author lys
	 */
//	@RequestMapping(value="toWithdrawal", method = RequestMethod.POST)
//	@Token(remove=true)
	public String toWithdrawal(@RequestParam(required=true) Long cardId, 
			@RequestParam(required=true) String withdrawPrice,
			@RequestParam(required=true)String paymentCode, 
			Model model) {
		//验证码验证
		Long userId = getUserId();
		User user = userBusiness.getUserById(userId);
		Account account = accountBusiness.getAccountByUserId(userId);
		withdrawBusiness.withdraw(cardId, withdrawPrice, account.getId(), 
				userId, paymentCode, user.getCell());
	    return "/m/trade/account/withdraw/success";
	}
	
	
	/**
	 * 卡bin获取
	 * 
	 * @Title: getCardbin 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param bankCard
	 * @return
	 * @date 2015年1月12日 下午2:48:59  
	 * @author lys
	 */
//	@RequestMapping(value = "cardbin", method = RequestMethod.POST)
//	@ResponseBody
	public String getCardbin(@RequestParam(required=true) String bankCard) {
		try {
			BankCard old = bankCardBusiness.getIdenticalValidCard(super.getUserId(),
					bankCard, BankCardDto.USERFOR_SETTLE);
			if (null != old) {
				return super.getReqJson(false, "该卡已存在，请直接选择该卡");
			}
			BankCard card = bankCardBusiness.getCardInfo(bankCard);
			if (null == card) {
				return super.getReqJson(false, "卡号输入错误");
			}
			if (null == Bank.getBankByCode(card.getBankCode())) {
				return super.getReqJson(false, "交易平台不支持【"+card.getBankName()+"】的银行卡");
			}
			return super.getReqJson(true, JsonUtil.fromObject(card, false, 
					new String[]{"bankType", "bankCode", "bankName"}));
		} catch (Exception e) {
			return super.getReqJson(false, "卡号输入错误");
		}
	}
	
	/**
	 * 支付密码验证
	 * @Title: valid 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param paymentCode
	 * @return
	 * @date 2015年4月2日 上午11:35:02  
	 * @author lys
	 */
//	@RequestMapping(value="valid", method=RequestMethod.POST)
//	@ResponseBody
	public String valid(@RequestParam String paymentCode) {
		try {
			boolean valid = accountBusiness.checkPaymentCode(getUserId(), paymentCode);
			if (valid) {
				return super.getReqJson(true, null);
			}
			return super.getReqJson(false, "支付密码输入错误");
		} catch (AccountException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
}
