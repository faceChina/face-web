package com.zjlp.face.web.ctl.wap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.account.domain.dto.AccountOperationRecordDto;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

@Controller
@RequestMapping("/wap/{shopNo}/buy/account/")
public class AccountWapCtl extends WapCtl {

	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private BankCardBusiness bankCardBusiness;
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	
	/**
	 * 钱包首页
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月20日 上午10:02:39  
	 * @author lys
	 */
	@RequestMapping("index")
	public String index(Model model) {
		Long userId = super.getUserId();
		String shopNo = super.getShopNo();
		Account account = accountBusiness.getAccountByUserId(userId);
		if (StringUtils.isBlank(account.getPaymentCode())) {
			// 页面跳转
			model.addAttribute("retUrl", "/wap/"+shopNo+"/buy/account/index");
			return super.getRedirectUrl("/wap/"+shopNo+"/buy/account/security/paymentCode");
		} else {
			// 钱包首页
			int number = bankCardBusiness.getCardNumber(userId,
					BankCard.REMOTE_TYPE_USER);
			model.addAttribute("number", number);
			model.addAttribute("amount", account.getWithdrawAmount());
			return "/wap/trade/account/purse";
		}
	}
	
	/**
	 * 操作明细列表
	 * @Title: list 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination 分页信息
	 * @param model
	 * @return
	 * @date 2015年3月23日 上午11:53:51  
	 * @author lys
	 */
	@RequestMapping("list")
	public String list(Pagination<AccountOperationRecordDto> pagination, Model model) {
		Long userId = super.getUserId();
		Account account = accountBusiness.getAccountByUserId(userId);
		Long freezeAmount = salesOrderProducer.getUserFreezeAmount(userId);
		model.addAttribute("withdrawAmount", account.getWithdrawAmount());
		model.addAttribute("freezeAmount", freezeAmount);
		return "/wap/trade/account/operation-record-list";
	}
	
	
	@RequestMapping("balancelist")
	public String balanceList(Pagination<AccountOperationRecordDto> pagination, Model model) {
		AccountOperationRecordDto dto = new AccountOperationRecordDto();
		dto.setUserId(getUserId());
		pagination = accountBusiness.findUserOperationPage(dto, pagination, new Aide());
		model.addAttribute("pagination", pagination);
		return "/wap/trade/account/balance-list";
	}
	
	/**
	 * 追加明细
	 * @Title: append 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午3:04:27  
	 * @author lys
	 */
	@RequestMapping(value="append", method=RequestMethod.POST)
	public String append(Pagination<AccountOperationRecordDto> pagination, Model model) {
		AccountOperationRecordDto dto = new AccountOperationRecordDto();
		dto.setUserId(getUserId());
		pagination = accountBusiness.findUserOperationPage(dto, pagination, new Aide());
		model.addAttribute("pagination", pagination);
		return "/wap/trade/account/account-balance-data";
	}
	
	/**
	 * 查询交易明细详情
	 * @Title: detail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午3:36:54  
	 * @author lys
	 */
	@RequestMapping(value="detail" , method=RequestMethod.GET)
	public String detail(@RequestParam Long id, Model model) {
		AccountOperationRecordDto record = accountBusiness.getOperationRecordById(id, super.getUserId());
		record.setUserId(getUserId());
		model.addAttribute("detail", record);
		return "/wap/trade/account/detail";
	}
}
