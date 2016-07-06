package com.zjlp.face.web.ctl.wap;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.domain.vo.BalanceVo;
import com.zjlp.face.web.server.operation.member.domain.vo.ConsumeVo;
import com.zjlp.face.web.server.operation.member.domain.vo.IntegralVo;
import com.zjlp.face.web.server.operation.member.domain.vo.MemberAttendanceRecordVo;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.ShualianUtil;
import com.zjlp.face.web.util.WeixinUtil;

@Controller
@RequestMapping("/wap/{shopNo}/")
public class MemberWapCtl extends WapCtl {

	@Autowired
	private MemberBusiness memberBusiness;
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	
	@Autowired
	private ShopBusiness shopBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired(required=false)
	private ShopExternalService shopExternalService;
	
	WeixinUtil wt = new WeixinUtil();
	
	private Log log = LogFactory.getLog(MemberWapCtl.class);

	/*
	 * 查询sellerID
	 */
	private Long findSellerId() {
		String shopNo = super.getShopNo();
		//通过shopNO查询sellerID
		Long sellerId = this.shopBusiness.findSellerIdByShopNo(shopNo);
		return sellerId;
	}
	
	/*
	 * 查询会员卡ID
	 */
	private Long findMemberCardId() {
		Long userId = super.getUserId();
		//通过userID和shopID查询会员卡ID
		Long memberCardId = this.memberBusiness.findMemberCardIdByUserIdAndShopNo(userId, super.getShopNo());
		return memberCardId;
	}
	
	/**
	 * 跳转完善会员资料页面
	 * @param model
	 * @return
	 */
	@RequestMapping("buy/member/imformation/add")
	public String addInfomation(Model model) {
		Long memberCardId = this.findMemberCardId();
		MemberCard memberCard = this.memberBusiness.findMemberCardById(memberCardId);
		model.addAttribute("memberCard", memberCard);
		return "/wap/operation/member/member-information";
	}
	
	/**
	 * 
	 * @Title: saveInfomation 
	 * @Description: 保存会员详细资料 
	 * @param model
	 * @return
	 * @date 2015年4月15日 下午7:29:29  
	 * @author cbc
	 */
	@RequestMapping("buy/member/imformation/save")
	public String saveInfomation(Model model, MemberCardDto memberCardDto) {
		try {
			Long memberCardId = this.findMemberCardId();
			memberCardDto.setId(memberCardId);
			this.memberBusiness.saveInfomation(memberCardDto);
		} catch (Exception e) {
			throw new MemberException();
		}
		return "redirect:/wap/"+this.getShopNo()+"/buy/member/index.htm";
	}
	
	/**
	 * 获取会员消费记录
	 * @param model
	 * @return
	 */
	@RequestMapping("buy/member/consume/list")
	public String listConsume(Model model) {
		Long userId = super.getUserId();
		Long sellerId = this.findSellerId();
		Long memberCardId = this.findMemberCardId();
		List<ConsumeVo> consumeList = memberBusiness.findOrderListByUserIdAndSellerIdForWap(userId, sellerId, null, null, memberCardId);
		model.addAttribute("consumeList", consumeList);
		return "/wap/operation/member/purchase-history";
	}
	
	/**
	 * 
	 * @Title: listConsumeByTime 
	 * @Description: 通过时间查询会员消费记录   
	 * @param model
	 * @param year
	 * @param month
	 * @return
	 * @date 2015年4月15日 上午9:52:25  
	 * @author cbc
	 */
	@RequestMapping(value="buy/member/consume/listByTime")
	@ResponseBody
	public String listConsumeByTime(Model model, @RequestParam("year")String year, @RequestParam("month")String month) {
		Long userId = super.getUserId();
		Long sellerId = this.findSellerId();
		Long memberCardId = this.findMemberCardId();
		List<ConsumeVo> consumeList = this.memberBusiness.findOrderListByUserIdAndSellerIdForWap(userId, sellerId, year, month,memberCardId);
		return JSONArray.fromObject(consumeList).toString();
	}
	
	/**
	 * 获取会员在这家店的积分记录
	 * @param model
	 * @return
	 */
	@RequestMapping("buy/member/integral/list")
	public String listIntegral(Model model) {
		Long userId = super.getUserId();
		//通过userID和shopID查询会员卡ID
		Long memberCardId = this.findMemberCardId();
		
		//获得用户总积分
		Long integralCount = memberBusiness.findIntegralCountForWap(userId, super.getShopNo());
		if (null == integralCount) {
			//如果为空说明积分为0
			integralCount = 0L;
		}
		
		List<IntegralVo> integralVoList = null;
		if (null != memberCardId) {
			//如果会员卡ID不为空，通过会员卡ID获取用户的积分列表
			integralVoList = memberBusiness.findIntegralListForWap(memberCardId, null, null);
		}
		
		model.addAttribute("integralCount", integralCount);
		model.addAttribute("integralVoList", integralVoList);
		return "/wap/operation/member/member-integral";
	}
	
	/**
	 * 
	 * @Title: listIntegralByTime 
	 * @Description: 通过时间来查询用户的积分记录
	 * @param model
	 * @param year
	 * @param month
	 * @return
	 * @date 2015年4月15日 上午10:00:44  
	 * @author cbc
	 */
	@RequestMapping("buy/member/integral/listByTime")
	@ResponseBody
	public String listIntegralByTime(Model model, @RequestParam("year")String year, @RequestParam("month")String month) {
		
		Long memberCardId = this.findMemberCardId();
		
		List<IntegralVo> integralVoList = new ArrayList<IntegralVo>();
		if (null != memberCardId) {
			//如果会员卡ID不为空，通过会员卡ID获取用户的积分列表
			integralVoList = memberBusiness.findIntegralListForWap(memberCardId, year, month);
		} 
		return JSONArray.fromObject(integralVoList).toString();
	}
	
	/**
	 * 
	 * @Title: listBalance 
	 * @Description: 查询用户的余额记录
	 * @param model
	 * @return
	 * @date 2015年4月15日 上午10:01:07  
	 * @author cbc
	 */
	@RequestMapping("buy/member/balance/list")
	public String listBalance(Model model) {
		Long memberCardId = this.findMemberCardId();
		//查询余额
		MemberCard memberCard = this.memberBusiness.findMemberCardById(memberCardId);
		
		Double balance = Math.round(memberCard.getAmount())/100.0;
		DecimalFormat df = new DecimalFormat("0.00");
		String decimalBalance = null;
		if (null == memberCard.getAmount()|| memberCard.getAmount().intValue() == 0) {
			decimalBalance = "0";
		} else {
			decimalBalance = df.format(balance);
		}
		
		//是否开启充值功能
		MarketingTool cz =  memberBusiness.getMarketingTool(getShop().getUserId(), 
				MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY);
		if(null != cz){
			model.addAttribute("isRecharge", cz.getStatus());
		}
		//查询所有用户的账单
		List<BalanceVo> balanceVoList = this.memberBusiness.findBalanceListByMemberCardId(memberCardId, null, null);
		
		model.addAttribute("balance", decimalBalance);
		model.addAttribute("balanceVoList", balanceVoList);
		return "/wap/operation/member/member-balance";
	}
	
	/**
	 * 
	 * @Title: listBalanceByTime 
	 * @Description: 通过时间获取用户的账单 
	 * @param model
	 * @param year
	 * @param month
	 * @return
	 * @date 2015年4月15日 下午12:01:09  
	 * @author cbc
	 */
	@RequestMapping("buy/member/balance/listByTime")
	@ResponseBody
	public String listBalanceByTime(Model model, @RequestParam("year")String year, @RequestParam("month")String month) {
		Long memberCardId = this.findMemberCardId();
		//查询所有用户的账单
		List<BalanceVo> balanceVoList = this.memberBusiness.findBalanceListByMemberCardId(memberCardId, year, month);
		
		return JSONArray.fromObject(balanceVoList).toString();
	}
	
	/**
	 * 分享时跳转的页面
	 * @Title: subscribe 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年4月17日 下午3:55:39  
	 * @author ah
	 */
	@RequestMapping("any/member/subscribe")
	public String subscribe(Model model) {
		//获取店铺会员卡设置
		Shop shop = super.getShop();
//		Long userId = super.getUserId();
//		Long sellerid = findSellerId();
//		MemberCard memberCard = memberBusiness.isReceiveMemberCard(userId, sellerid);
//		if(memberCard != null){
//			return super.getRedirectUrl("/wap/"+super.getShopNo()+"/buy/member/index");
//		}
		MemberEnactment memberEnactment = memberBusiness.getMemberEnactment(shop.getUserId());
		model.addAttribute("memberEnactment", memberEnactment);
		return "/wap/operation/member/member";
	}
	
	@RequestMapping("buy/member/getcard")
	public String getCard(Model model) {
		//获取店铺会员卡设置
		Shop shop = super.getShop();
		//如果已经领取会员卡，跳转到http://192.168.1.198/wap/HZJZ1509041634vqbeL1/buy/member/index.htm页面
		Long userId = super.getUserId();
		Long sellerid = findSellerId();
		MemberCard memberCard = memberBusiness.isReceiveMemberCard(userId, sellerid);
		if(memberCard != null){
			return super.getRedirectUrl("/wap/"+super.getShopNo()+"/buy/member/index");
		}
		MemberCard card = memberBusiness.generateMemberCard(shop.getUserId(), shop.getNo(), super.getUserId());
		model.addAttribute("sendIntegral", card.getAvailableIntegral());
		return super.getRedirectUrl("/wap/"+super.getShopNo()+"/buy/member/index");
	}
	
	/**
	 * 会员卡手机端主页
	 * @Title: index 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @param model
	 * @return
	 * @date 2015年4月17日 下午3:56:03  
	 * @author ah
	 */
	@RequestMapping("buy/member/index")
	public String index(HttpServletRequest request,MemberCardDto memberCardDto, Model model) {
		//赠送积分
		model.addAttribute("sendIntegral", memberCardDto.getSendIntegral());
		// 查询用户店铺会员卡
		Shop shop = super.getShop();
		memberCardDto.setSellerId(shop.getUserId());
		memberCardDto.setUserId(super.getUserId());
		memberCardDto = memberBusiness.getMemberCardForWap(memberCardDto);
		if(null != memberCardDto) {
			//是否签到
			boolean hasRegistratRule = memberBusiness.hasRegistratRule(shop.getNo());
			if (hasRegistratRule == true) {
				boolean isRegist = memberBusiness.isTodayAttendance(memberCardDto.getId());
				model.addAttribute("isRegist", isRegist);
			}
			model.addAttribute("hasRegistratRule", hasRegistratRule);
			//是否开启充值功能
			MarketingTool cz =  memberBusiness.getMarketingTool(getShop().getUserId(), 
					MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY);
			if(null != cz){
				model.addAttribute("isRecharge", cz.getStatus());
			}
		}else{
			return super.getRedirectUrl("/wap/"+super.getShopNo()+"/any/member/subscribe");
		}
		model.addAttribute("memberCardDto", memberCardDto);
		MemberEnactment memberEnactment=memberBusiness.getMemberEnactment(memberCardDto.getSellerId());
		model.addAttribute("memberEnactment", memberEnactment);
		
		/**
		 * APP进入会员中心,显示底部框
		 */
		model.addAttribute("appShowBottom", ShualianUtil.isShualianBrowser(request) ? "true" : "false");
		/**
		 * 
		 */
		
		return "/wap/operation/member/index";
	}
	
	/**
	 * 会员卡登录页面
	 * @Title: share 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param model
	 * @return
	 * @date 2015年4月17日 下午3:56:32  
	 * @author ah
	 */
	@RequestMapping(value="any/member/share")
	public String share(HttpSession session, Model model) {
//		Long userId = super.getUserId();
//		Shop shop = super.getShop();
//		// 用户登录状态
//		if(null != userId && StringUtils.isNotBlank(shop.getNo())) {
//			return super.getRedirectUrl("/wap/"+super.getShopNo()+"/buy/member/index");
//		}
		return super.getRedirectUrl("/wap/"+super.getShopNo()+"/buy/member/index");
	}
	
	/**
	 * 会员卡登录时验证用户信息
	 * @Title: checkUser 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param request
	 * @param user
	 * @param model
	 * @return
	 * @date 2015年4月17日 下午3:56:53  
	 * @author ah
	 */
	@RequestMapping("any/member/checkUser")
	public String checkUser(HttpServletRequest request, User user, Model model) {
		// 根据用户名跟密码查询用户
		User newUser = userBusiness.getUserByLoginAccountAndPasswd(user.getCell(), user.getPasswd());
		if(null == newUser) {
			request.getSession().setAttribute("errorMsg", "用户名或密码错误");
			return getRedirectUrl("/wap/"+ super.getShopNo() +"/any/member/share");
		} else {
			request.getSession().removeAttribute("errorMsg");
			return getRedirectUrl("/wap/" + super.getShopNo() +"/any/member/login/"+user.getCell()+"/"+user.getPasswd());
		}
	}
	
	/**
	 * 会员卡登录
	 * @Title: login 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param request
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 * @date 2015年4月17日 下午3:57:15  
	 * @author ah
	 */
	@RequestMapping(value="any/member/login/{username}/{password}")
	public String login(HttpServletRequest request, @PathVariable String username, @PathVariable String password, Model model) {
		
		//设置登录后的跳转页面
		StringBuffer indexUrl = new StringBuffer();
		String path = PropertiesUtil.getContexrtParam("WGJ_URL");
		indexUrl = indexUrl.append(path).append("/wap/").append(super.getShopNo()).append("/buy/member/index").append(Constants.URL_SUFIX);
		request.getSession().setAttribute("redirUrl", indexUrl.toString());
		request.getSession().setAttribute("loginType", "wap");
		
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		return "/wap/operation/member/userLogin";
	}
	
	@RequestMapping(value="any/member/memberPath")
	public String memberPath(Model model) {
		Long userId = super.getUserId();
		// 用户登录时，跳转到会员卡主页
		if(null == userId) {
			return super.getRedirectUrl("/wap/"+ super.getShopNo() +"/any/member/share");
		// 用户未登录时，跳转到会员卡登录页面
		} else {
			return super.getRedirectUrl("/wap/"+ super.getShopNo() +"/buy/member/index");
		}
	}
	
	/**
	 * 用户会员签到
	 * @Title: regist 
	 * @Description: (这里用一句话描述这个方法的作用 ) 
	 * @param cardId 用户会员卡
	 * @return
	 * @date 2015年4月18日 下午3:12:55  
	 * @author lys
	 */
	@RequestMapping(value="buy/member/regist/regist", method=RequestMethod.POST)
	@ResponseBody
	public String regist(@RequestParam Long cardId) {
		try {
			MemberAttendanceRecordVo vo = memberBusiness.registrat(super.getShopNo(), getUserId(), cardId);
			return super.getReqJson(true, JSONObject.fromObject(vo).toString());
		} catch (BaseException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	/**
	 * 会员卡列表页
	 * @Title: list 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年4月18日 下午3:40:46  
	 * @author ah
	 */
	@RequestMapping("buy/member/cardList")
	public String list(HttpSession session, MemberCardDto memberCardDto, Pagination<MemberCardDto> pagination, Model model,HttpServletRequest request) {
		
		memberCardDto.setUserId(getUserId());
//		String openId = (String) session.getAttribute(super.getShopNo()+"shopOpenId");
//		//绑定会员卡
//		this.bingMemberCard(openId);
		//查询会员卡列表
		pagination = memberBusiness.findMemberCardDtoByUserId(memberCardDto, pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("memberCardDto", memberCardDto);
		//是否显示全部会员卡
		model.addAttribute("show", request.getParameter("show"));
		
		return "/wap/operation/member/member-list";
	}
	
	/**
	 * 会员卡动态加载页面
	 * @Title: append 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年4月18日 下午3:41:04  
	 * @author ah
	 */
	@RequestMapping(value="buy/member/append", method = RequestMethod.POST)
	public String append(MemberCardDto memberCardDto, Pagination<MemberCardDto> pagination, Model model) {
		memberCardDto.setUserId(getUserId());
		//查询会员卡列表
		pagination = memberBusiness.findMemberCardDtoByUserId(memberCardDto, pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("memberCardDto", memberCardDto);
		return "/wap/operation/member/member-list-data";
	}
	
//	public void bingMemberCard(String openId) {
//		User user = userBusiness.getUserById(super.getUserId());
//		memberBusiness.bindMemberCard(openId, user, super.getShop());
//	}
}
