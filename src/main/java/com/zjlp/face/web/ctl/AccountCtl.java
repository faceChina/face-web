package com.zjlp.face.web.ctl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.domain.WithdrawRecord;
import com.zjlp.face.account.dto.WithdrawRecordDto;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.account.business.WithdrawBusiness;
import com.zjlp.face.web.server.trade.account.domain.dto.AccountOperationRecordDto;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Controller
@RequestMapping(value="/u/account/")
public class AccountCtl extends BaseCtl {
	
	private static final Integer[] LIST_TYPE = {1, 2};
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private WithdrawBusiness withdrawBusiness;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private SalesOrderProducer salesOrderProducer;

	/**
	 * 支付密码判断
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午9:10:57  
	 * @author lys
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(Model model) {
		Long userId = super.getUserId();
		Account account = accountBusiness.getAccountByUserId(userId);
		if (StringUtils.isBlank(account.getPaymentCode())) {
			model.addAttribute("amount", account.getWithdrawAmount());
			return "/m/trade/account/no-paymentcode";
		}
		return super.getRedirectUrl("inout");
	}
	
	/**
	 * 收支明细
	 * @Title: inout 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param dto
	 * @param aide
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午9:16:44  
	 * @author lys
	 */
	@RequestMapping(value="inout")
	public String inout(Pagination<AccountOperationRecordDto> pagination, 
			AccountOperationRecordDto dto, Aide aide, Model model) {
		Long userId = super.getUserId();
		Account account = accountBusiness.getAccountByUserId(userId);
		dto.setUserId(userId);
		pagination = accountBusiness.findUserOperationPageByType(String.valueOf(userId), pagination, aide, null);
		model.addAttribute("amount", account.getWithdrawAmount());
		model.addAttribute("pagination", pagination);
		model.addAttribute("dto", dto);
		model.addAttribute("aide", aide);
		model.addAttribute("type", LIST_TYPE[0]);
		model.addAttribute("aide", aide);
		Long freezeAmount = accountBusiness.getFreezeAmount(userId);
		model.addAttribute("freezeAmount", freezeAmount);
		return "/m/trade/account/list";
	}
	
	/**
	 * 提现记录
	 * @Title: withdrawList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param recordDto
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午9:24:56  
	 * @author lys
	 */
	@RequestMapping(value="witdrawList")
	public String withdrawList(Pagination<WithdrawRecordDto> pagination, WithdrawRecordDto recordDto, Model model) {
		Long userId = super.getUserId();
		Account account = accountBusiness.getAccountByUserId(userId);
		recordDto.setRemoteId(String.valueOf(userId));
		recordDto.setRemoteType(WithdrawRecord.REMOTE_TYPE_USER);
		pagination = withdrawBusiness.findRecordPageForUser(pagination, recordDto);
		model.addAttribute("pagination", pagination);
		model.addAttribute("recordDto", recordDto);
		model.addAttribute("amount", account.getWithdrawAmount());
		model.addAttribute("status", recordDto.getStatus());
		model.addAttribute("type", LIST_TYPE[1]);
		model.addAttribute("aide", recordDto.getAide());
		Long freezeAmount = accountBusiness.getFreezeAmount(userId);
		model.addAttribute("freezeAmount", freezeAmount);
		return "/m/trade/account/list";
	}
	
	/**
	 * 是否存在支付密码验证
	 * @Title: checkCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月25日 下午2:29:09  
	 * @author lys
	 */
	@RequestMapping(value="check", method=RequestMethod.POST)
	@ResponseBody
	public String checkCode() {
		try {
			boolean is = accountBusiness.existPaymentCode(getUserId());
			return super.getReqJson(true, String.valueOf(is));
		} catch (Exception e) {
			return super.getReqJson(false, null);
		}
	}
	
	//--------------------以下为店铺--------------------
	
	/**
	 * 店铺收支
	 * @Title: shopInout 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param dto
	 * @param aide
	 * @param model
	 * @return
	 * @date 2015年3月25日 下午10:09:19  
	 * @author lys
	 */
	@RequestMapping(value="shopInout")
	public String shopInout(Pagination<AccountOperationRecordDto> pagination, 
			AccountOperationRecordDto dto, Aide aide, Model model) {
		String shopNo = super.getShopNo();
		dto.setShopNo(shopNo);
		pagination = accountBusiness.findShopOperationPage(dto, pagination, aide);
		//订单冻结金额
		Long freezeAmount = accountBusiness.getShopFreezeIncome(shopNo);
		//昨日收益
		Long yesterdayAmount = accountBusiness.getShopYesterdayIncome(shopNo);
		//累计收益
		Long consumeAmount = accountBusiness.getShopTotalIncome(shopNo);
		model.addAttribute("consumeAmount", consumeAmount);
		model.addAttribute("pagination", pagination);
		model.addAttribute("freezeAmount", freezeAmount);
		model.addAttribute("yesterdayAmount", yesterdayAmount);
		model.addAttribute("dto", dto);
		model.addAttribute("aide", aide);
		return "/m/trade/account/shop-inout";
	}
	
	/**
	 * 店铺账户中心
	 * @Title: shopAccount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年3月26日 上午11:01:37  
	 * @author lys
	 */
	@RequestMapping(value="shopAccount")
	public String shopAccount(Model model) {
		Shop shop = shopProducer.getShopByNo(super.getShopNo());
		ShopVo vo = new ShopVo(shop);
		String path = shop.getShopLogoUrl();
		String zoomSizes = PropertiesUtil.getContexrtParam("shopLogoFile");
    	AssertUtil.hasLength(zoomSizes, "imgConfig.properties还未配置shopLogoFile字段");
		String zoomPath = ImageConstants.getCloudstZoomPath(path, zoomSizes);
		vo.setShopLogoUrl(zoomPath);
		model.addAttribute("shop", vo);
		return "/m/trade/account/shop-accounts";
	}
	
	/**
	 * 
	 * @Title: saveInfo 
	 * @Description: 保存店铺的账户信息 
	 * @param model
	 * @param shop
	 * @return
	 * @date 2015年5月3日 上午10:27:17  
	 * @author cbc
	 */
	@RequestMapping(value="saveInfo", method=RequestMethod.POST)
	@ResponseBody
	public String saveInfo(Model model, Shop shop) {
		String shopNo = super.getShopNo();
		Long userId = super.getUserId();
		shop.setUserId(userId);
		shop.setNo(shopNo);
		boolean boo = this.shopProducer.updateShopInfo(shop);
		if (boo) {
			return super.getReqJson(true, "保存成功");
		} else {
			return super.getReqJson(false, "保存失败");
		}
	}
	
	/**
	 * 
	 * @Title: toTutorial 
	 * @Description: 引导关注公众号页面教程页面
	 * @param model
	 * @return
	 * @date 2015年5月6日 上午10:02:31  
	 * @author cbc
	 */
	@RequestMapping("tutorial")
	public String toTutorial(Model model) {
		return "/m/index/tutorial-view";
	}
	
}
