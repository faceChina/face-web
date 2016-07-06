package com.zjlp.face.web.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.payment.bussiness.PaymentBusiness;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCardDto;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

@Controller
@RequestMapping(value = "/u/account/bankcard/")
public class BankCardCtl extends BaseCtl {

	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private BankCardBusiness bankCardBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private PaymentBusiness paymentBusiness;

	/**
	 * 银行卡列表
	 * 
	 * @Title: list
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 * @date 2015年3月20日 上午11:56:08
	 * @author lys
	 */
	@RequestMapping("list")
	public String list(String errorMsg, String bankCard,
			String cell, String mobilecode, String endTime, String cvv, Model model) {
		Long userId = super.getUserId();
		List<BankCard> cardList = bankCardBusiness.findBankCardListByUserId(userId);
		User user = userBusiness.getUserById(userId);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("bankCard", bankCard);
		model.addAttribute("cell", cell);
		model.addAttribute("mobilecode", mobilecode);
		model.addAttribute("endTime", endTime);
		model.addAttribute("cvv", cvv);
		model.addAttribute("user", new UserVo(user));
		model.addAttribute("cardList", BankCardDto.coverCardsForView(cardList));
		return "/m/user/bankcard/list";
	}

	/**
	 * 删除银行卡
	 * 
	 * @Title: del
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            银行卡id
	 * @return
	 * @date 2015年3月20日 下午2:11:34
	 * @author lys
	 */
	@RequestMapping("del")
	@ResponseBody
	public String del(@RequestParam(required = true) Long id) {
		try {
			bankCardBusiness.removeBankCard(getUserId(), id);
			return super.getReqJson(true, null);
		} catch (BankCardException e) {
			return super.getReqJson(true, e.getExternalMsg());
		}
	}
	
	@Token(save=true)
	@RequestMapping(value="pcck", method=RequestMethod.POST)
	@ResponseBody
	public String pcck(@RequestParam String paymentCode, HttpServletRequest request) {
		try {
			Long userId = super.getUserId();
			boolean isexists = accountBusiness.existPaymentCode(userId);
			if (!isexists) {
				return super.getReqJson(false, "未设置支付密码，请先设置支付密码");
			}
			boolean istrue= accountBusiness.checkPaymentCode(userId, paymentCode);
			if (!istrue) {
				return super.getReqJson(false, "支付密码错误，请重试");
			}
			return super.getReqJson(true, (String)request.getSession(false).getAttribute("validateToken"));
		} catch (AccountException e) {
			return super.getReqJson(false, "支付密码错误，请重试");
		}
	}

	/**
	 * 添加银行卡（支付）
	 * 
	 * @Title: add
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param bankCode
	 *            银行编号
	 * @param bankCard
	 *            银行卡号
	 * @param cell
	 *            手机号码
	 * @param endTime
	 *            终止时间
	 * @param cvv
	 *            卡后数字
	 * @param model
	 * @return
	 * @date 2015年3月20日 下午2:14:44
	 * @author lys
	 */
	@Token(remove=true)
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestParam(required = true) String bankCode,
			@RequestParam(required = true) String bankCard,
			@RequestParam(required = true) String cell,
			@RequestParam(required = true) String mobilecode, String endTime,
			String cvv, Model model) {
		try {
			bankCardBusiness.addBindPayBankCard(getUserId(), bankCode, bankCard,
					cell, endTime, cvv, mobilecode);
		} catch (BankCardException e) {
			model.addAttribute("type", BankCard.USERFOR_PAY); // 支付卡页面
			model.addAttribute("bankCard", bankCard);
			model.addAttribute("cell", cell);
			model.addAttribute("mobilecode", mobilecode);
			model.addAttribute("endTime", endTime);
			model.addAttribute("cvv", cvv);
			model.addAttribute("errorMsg", e.getExternalMsg());
		}
		return super.getRedirectUrl("list");
	}

	/**
	 * 卡bin验证
	 * 
	 * @Title: cardbin
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param bankCard
	 *            银行卡号
	 * @return
	 * @date 2015年3月20日 下午4:30:40
	 * @author lys
	 */
	@RequestMapping(value = "cardbin", method = RequestMethod.POST)
	@ResponseBody
	public String cardbin(@RequestParam String bankCard) {
		try {
			BankCard old = bankCardBusiness.getIdenticalValidCard(super.getUserId(),
					bankCard, BankCardDto.USERFOR_PAY);
			if (null != old) {
				return super.getReqJson(false, "该卡已存在");
			}
			BankCardVo bankCardVo=paymentBusiness.getBankCardMsgByCardNo(bankCard);
			if (null == bankCardVo) {
				return super.getReqJson(false, "不支持的银行卡类型");
			}
			return super.getReqJson(true,
					JSONObject.fromObject(bankCardVo).toString());
		} catch (BankCardException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}

	/**
	 * 设置默认银行卡
	 * 
	 * @Title: setDefault
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param cardId
	 * @param type
	 * @return
	 * @date 2015年3月24日 下午9:37:29
	 * @author lys
	 */
	@RequestMapping(value = "setDefault", method = RequestMethod.POST)
	@ResponseBody
	public String setDefault(@RequestParam Long cardId) {
		try {
			Long userId = super.getUserId();
			bankCardBusiness.setDefaultCard(userId, cardId);
			return super.getReqJson(true, null);
		} catch (BankCardException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	@RequestMapping(value="mobilecode", method=RequestMethod.POST)
	@ResponseBody
	public String sendModbilecode(@RequestParam String cell) {
		try {
			boolean istrue = bankCardBusiness.sendBankMobilecode(cell);
			if (istrue) {
				return super.getReqJson(istrue, "发送验证码失败");
			} else {
				return super.getReqJson(istrue, null);
			}
		} catch (BankCardException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
}
