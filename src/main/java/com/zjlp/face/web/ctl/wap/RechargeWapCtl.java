package com.zjlp.face.web.ctl.wap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.recharge.business.RechargeBusiness;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.trade.recharge.domain.dto.RechargeDto;
import com.zjlp.face.web.util.WeixinUtil;

@Controller
@RequestMapping("/wap/{shopNo}/buy/recharge/")
public class RechargeWapCtl extends WapCtl {
	
	@Autowired
	private RechargeBusiness rechargeBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private MemberBusiness memberBusiness;

	@Token(save=true)
	@RequestMapping("list")
	public String list(RechargeDto rechargeDto, Model model) {
		// 查询会员充值活动列表
		rechargeDto.setSellerId(super.getShop().getUserId());
		List<MarketingActivityDetailDto> marketingActivityDetails = rechargeBusiness.getRechargeList(rechargeDto);
		model.addAttribute("marketingActivityDetails", marketingActivityDetails);
		return "/wap/trade/recharge/member-recharge";
	}
	
	@Token(remove=true)
	@RequestMapping("payWay")
	public String payWay(HttpServletRequest request, Recharge recharge, Model model) {
		recharge.setSellerId(super.getShop().getUserId());
		recharge.setUserId(super.getUserId());
		recharge.setShopNo(super.getShopNo());
		
		//判断活动状态
		MarketingTool cz =  memberBusiness.getMarketingTool(getShop().getUserId(), 
				MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY);
		if(null == cz || cz.getStatus().intValue() != Constants.VALID){
			return "";
		}
		//新增充值订单
		rechargeBusiness.addRecharge(recharge);
		boolean existPaymentCode=accountBusiness.existPaymentCode(getUserId());
		model.addAttribute("existPaymentCode", existPaymentCode);
		model.addAttribute("recharge", recharge);
		boolean isWechatPay = WeixinUtil.isWechatBrowser(request);
		model.addAttribute("isWechatPay", isWechatPay);
		return "/wap/trade/recharge/pay";
	}
	
	
}
