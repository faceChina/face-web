package com.zjlp.face.web.ctl.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCardDto;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

@Controller
@RequestMapping("/wap/{shopNo}/buy/bankcard/")
public class BankCardWapCtl extends WapCtl {

	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private BankCardBusiness bankCardBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	@Autowired
	private AccountBusiness accountBusiness;

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
	public String list(@RequestParam(required = true) Integer type, String errorMsg, Model model) {
		Long userId = super.getUserId();
		List<BankCard> cardList = null;
		if (BankCard.USERFOR_PAY.equals(type)) {
			cardList = bankCardBusiness.findPayCardList(userId);
		} else {
			cardList = bankCardBusiness.findSettleCardList(userId);
		}
		model.addAttribute("cardList", BankCardDto.coverCardsForView(cardList));
		model.addAttribute("type", type);
		model.addAttribute("errorMsg", errorMsg);
		return "/wap/user/bankcard/list";
	}
	@RequestMapping("listV2")
	public String listV2(Model model) {
		Long userId = super.getUserId();
		List<BankCard> cardList=bankCardBusiness.findBankCardListByUserId(userId);
		List<BankCardVo> list=new ArrayList<BankCardVo>();
		for(BankCard bc:cardList){
			BankCardVo vo=new BankCardVo(bc);
			list.add(vo);
		}
		model.addAttribute("bankCardList", list);
		/*model.addAttribute("cardList", BankCardDto.coverCardsForView(cardList));
		model.addAttribute("type", type);
		model.addAttribute("errorMsg", errorMsg);*/
		return "/wap/user/bankcard/listV2";
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

	/**
	 * 添加银行卡首页
	 * 
	 * @Title: addIndex
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param url
	 *            完成跳转页面
	 * @param model
	 * @return
	 * @date 2015年3月20日 下午2:20:46
	 * @author lys
	 */
	@Token(save=true)
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addIndex(String errorUrl, String retUrl, String mobilecode, String bankCard,String paymentCode,
			String endTime, String cvv, String cell, String errorMsg, Model model) {
		Long userId = getUserId();
		boolean istrue = accountBusiness.checkPaymentCode(userId, paymentCode);
		if (!istrue) {
			model.addAttribute("type", 1);
			model.addAttribute("errorMsg", "支付密码错误，请重试");
			return super.getRedirectUrl(errorUrl);
		}
		User user = userBusiness.getUserById(userId);
		UserVo userVo = new UserVo(user.getContacts(), user.getIdentity());
		model.addAttribute("retUrl", retUrl);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("cvv", cvv);
		model.addAttribute("cell", cell);
		model.addAttribute("endTime", endTime);
		model.addAttribute("bankCard", bankCard);
		model.addAttribute("mobilecode", mobilecode);
		model.addAttribute("user", userVo);
		return "/wap/user/bankcard/add";
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
	@RequestMapping(value = "toadd", method = RequestMethod.POST)
	public String add(@RequestParam(required = true) String retUrl,
			@RequestParam(required = true) String bankCode,
			@RequestParam(required = true) String bankCard,
			@RequestParam(required = true) String cell,
			@RequestParam String mobilecode, String endTime, String cvv,
			Model model) {
		Long bankCardId = bankCardBusiness.addBindPayBankCard(getUserId(), bankCode, bankCard, cell,
				endTime, cvv, mobilecode);
		model.addAttribute("type", BankCard.USERFOR_PAY);
		if (retUrl.contains(".htm")) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("bankCardId", bankCardId);
			return "redirect:"+super.appendUrl(retUrl, paramMap);
		} else {
			return super.getRedirectUrl(retUrl);
		}
		
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
			BankCard card = bankCardBusiness.getCardInfo(bankCard);
			if (null == card) {
				return super.getReqJson(false, "请输入正确的银行卡号");
			}
			return super.getReqJson(true,
					JSONObject.fromObject(new BankCardVo(card)).toString());
		} catch (BankCardException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	/**
	 * 发送手机验证码
	 * @Title: sendMobilecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cell 手机号码
	 * @return
	 * @date 2015年5月29日 下午3:54:51  
	 * @author lys
	 */
	@RequestMapping(value = "mobilecode", method = RequestMethod.POST)
	@ResponseBody
	public String sendMobilecode(@RequestParam String cell) {
		boolean flag = bankCardBusiness.sendBankMobilecode(cell);
		return super.getReqJson(flag, null);
	}

}
