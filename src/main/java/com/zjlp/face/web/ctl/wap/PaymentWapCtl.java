package com.zjlp.face.web.ctl.wap;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.dto.LakalaReq;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.shop.domain.GrandInfo;
import com.zjlp.face.shop.domain.WechatDevInfo;
import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.file.xml.XmlHelper;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.operation.redenvelope.business.RedenvelopeBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.trade.payment.bussiness.PaymentBusiness;
import com.zjlp.face.web.server.trade.payment.bussiness.RechargePayBusiness;
import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.ReturnInfo;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXNotice;
import com.zjlp.face.web.server.trade.payment.domain.vo.WechatPayVo;
import com.zjlp.face.web.server.trade.payment.util.WXPayUtil;
import com.zjlp.face.web.server.trade.recharge.business.RechargeBusiness;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.bankcard.producer.BankCardProducer;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.util.card.QRCodeUtil;


@Controller
public class PaymentWapCtl extends WapCtl {
	private org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(getClass());
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private PaymentBusiness paymentBusiness;
	
	@Autowired
	private RechargePayBusiness rechargePayBusiness;
	
	@Autowired
	private ShopExternalService shopExternalService;
	
	@Autowired
	private AccountBusiness accountBusiness;
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;

	@Autowired
	private RechargeBusiness rechargeBusiness;
	
	@Autowired
	private MemberProducer memberProducer;
	
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	
	@Autowired
	private BankCardProducer bankCardProducer;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserProducer userProducer;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private RedenvelopeBusiness redenvelopeBusiness;
	
	
	/**
	 * 微信支付实物订单-微信下单
	 * @Title: wechatPay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param request
	 * @param orderNos
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午7:45:53
	 */
	@RequestMapping(value = "/pay/wechatPay")
	public String wechatPay(HttpServletRequest request, String orderNos, Model model){
		List<String> orderNoList = JsonUtil.toArray(orderNos);
		// 获取当前微信用户id
		Long userId = getUserId();
		
		String openId = (String) request.getSession().getAttribute("openId");
		String appId = PropertiesUtil.getContexrtParam("WXPAY_APP_ID");
		Assert.hasLength(appId, "WXPAY_APP_ID未配置");
		// 微信支付公众号appid
		if(StringUtils.isBlank(openId)){
			String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
			Assert.hasLength(wgjurl, "WGJ_URL未配置");
			StringBuffer setOpendIdUrl = new StringBuffer();
			setOpendIdUrl = setOpendIdUrl.append(wgjurl).append("/pay/getOpenId").append(".htm");
			System.out.println("---------++-------------");
			model.addAttribute("orderNos", orderNos);
			return super.getRedirectUrl(WXPayUtil.getoauth2Url(appId, setOpendIdUrl.toString(), "/pay/wechatPay.htm?orderNos=" + orderNos));
		}
		// 主机地址
		String spbillCreateIp = request.getRemoteAddr();
		WechatPayVo wechatPayVo = paymentBusiness.paymentWechatOrder(userId, openId, spbillCreateIp, orderNoList);
		Assert.notNull(wechatPayVo, "微信支付异常");
		Assert.hasLength(wechatPayVo.getPrepayId());
		Assert.hasLength(wechatPayVo.getTransactionSerialNumber());
		String payKey = PropertiesUtil.getContexrtParam("WXPAY_SN");
		Assert.hasLength(payKey, "WXPAY_SN未配置");
		// 生成微信支付参数，此处拼接为完整的JSON格式，符合支付调起传入格式
		String jsParam = WXPayUtil.createPackageValue(appId, payKey, wechatPayVo.getPrepayId());
		// 此处可以添加订单的处理逻辑
		model.addAttribute("jsParam", jsParam);
		System.out.println("=======jsParam"+ jsParam);
		model.addAttribute("tsn", wechatPayVo.getTransactionSerialNumber());
		model.addAttribute("orderNos", orderNos);
		return "/wap/trade/payment/pay-wechat-confirm";
	}
	
	@RequestMapping(value = "/pay/wechatRecharge")
	public String wechatRecharge(HttpServletRequest request, String rechargeNo, Model model){
		try {
			// 获取当前微信用户id
			Long userId = getUserId();
			
			String openId = (String) request.getSession().getAttribute("openId");
			String appId = PropertiesUtil.getContexrtParam("WXPAY_APP_ID");
			Assert.hasLength(appId, "WXPAY_APP_ID未配置");
			// 微信支付公众号appid
			if(StringUtils.isBlank(openId)){
				String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
				Assert.hasLength(wgjurl, "WGJ_URL未配置");
				StringBuffer setOpendIdUrl = new StringBuffer();
				setOpendIdUrl = setOpendIdUrl.append(wgjurl).append("/pay/getOpenId").append(".htm");
				System.out.println("---------++-------------");
				model.addAttribute("rechargeNo", rechargeNo);
				return "redirect:"+WXPayUtil.getoauth2Url(appId, setOpendIdUrl.toString(), "/pay/wechatRecharge.htm?rechargeNo=" + rechargeNo);
			}
			// 主机地址
			String spbillCreateIp = request.getRemoteAddr();
			WechatPayVo wechatPayVo = rechargePayBusiness.paymentWechatOrder(userId, openId, spbillCreateIp, rechargeNo);
			Assert.notNull(wechatPayVo, "微信支付异常");
			Assert.hasLength(wechatPayVo.getPrepayId());
			Assert.hasLength(wechatPayVo.getTransactionSerialNumber());
			String payKey = PropertiesUtil.getContexrtParam("WXPAY_SN");
			Assert.hasLength(payKey, "WXPAY_SN未配置");
			// 生成微信支付参数，此处拼接为完整的JSON格式，符合支付调起传入格式
			String jsParam = WXPayUtil.createPackageValue(appId, payKey, wechatPayVo.getPrepayId());
			// 此处可以添加订单的处理逻辑
			model.addAttribute("jsParam", jsParam);
			model.addAttribute("tsn", wechatPayVo.getTransactionSerialNumber());
			model.addAttribute("rechargeNo", rechargeNo);
			return "/wap/trade/recharge/pay-wechat-confirm";
		}catch(BaseException e){
			log.error(e.getMessage(), e);
			model.addAttribute("message", e.getExternalMsg());
			return "/wap/common/error404";
		}
	}
	
	/**
	 * 获取OPEN_ID
	 * @Title: Oauth2MeUrl
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param request
	 * @param code
	 * @param state
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午7:46:20
	 */
	@RequestMapping(value = "/pay/getOpenId")
	public String Oauth2MeUrl(HttpServletRequest request, String code, String state, Model model){
		try{
			System.out.println("===============" + state);
			String appId = PropertiesUtil.getContexrtParam("WXPAY_APP_ID");
			Assert.hasLength(appId, "WXPAY_APP_ID未配置");
			String appSecret = PropertiesUtil.getContexrtParam("WXPAY_APP_SECRET");
			Assert.hasLength(appSecret, "WXPAY_APP_SECRET未配置");
			WechatDevInfo devInfo = new WechatDevInfo();
			devInfo.setAppId(appId);
			devInfo.setAppSecret(appSecret);
			System.out.println("++++++++++++++++++++++++++==");
			// 通过code换取网页授权access_token
			GrandInfo grandInfo = shopExternalService.getDynamicAccessoken(code, devInfo);
			System.out.println("------------------------------==");
			request.getSession().setAttribute("openId", grandInfo.getOpenId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + state;
	}
	
	/***
	 * 微信支付-通知消息接口
	 * @Title: wechatConsumer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return void
	 * @author phb
	 * @date 2015年4月16日 下午7:46:33
	 */
	@RequestMapping(value = "/pay/wechatConsumer/{thirdParty}",method = RequestMethod.POST)
	public void wechatConsumer(@PathVariable String thirdParty,HttpServletRequest request, HttpServletResponse response) throws Exception{
		InputStream inStream = null;
		ByteArrayOutputStream outSteam = null;
		try {
			// 获取请求数据
			inStream = request.getInputStream();
			outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			String result = new String(outSteam.toByteArray(), "utf-8");
			log.info(result);
			WXNotice notice = XmlHelper.parseXmlCdata(result, WXNotice.class);
			if(!"SUCCESS".equals(notice.getReturn_code())){
				response.getOutputStream().write("error".getBytes());
				return ;
			}
			// 此处调用订单查询接口验证是否交易成功
			WechatTransactionRecord wtr = null;
			if("ORDER".equals(thirdParty)){
				wtr = paymentBusiness.wechatConsumer(notice);
			}else if("RECHARGE".equals(thirdParty)){
				wtr = rechargePayBusiness.wechatConsumer(notice);
			}else{
				log.error("当前支付类型不存在");
			}
			// 商户处理后同步返回给微信参数
			ReturnInfo info = new ReturnInfo();
			if(null != wtr && StringUtils.isNotBlank(wtr.getTransactionSerialNumber()) && StringUtils.isNotBlank(wtr.getWechatSerialNumber())) {
				if("ORDER".equals(thirdParty)){
					List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(notice.getOut_trade_no());
					List<String> ordersList = new ArrayList<String>();
					for (SalesOrder salesOrder : salesOrderList) {
						ordersList.add(salesOrder.getOrderNo());
					}
					salesOrderBusiness.dealOrderAfterPay(ordersList);
				}
				info.setReturn_code("SUCCESS");
				info.setReturn_msg("OK");
				log.info("支付成功");
			} else {
				info.setReturn_code("FAIL");
				info.setReturn_msg("签名失败");
				log.info("支付失败");
			}
			String returnInfo = XmlHelper.objectToXMLCDATA(info);
			returnInfo = returnInfo.replaceAll("__", "_");
			response.getOutputStream().write(returnInfo.getBytes(Charset.forName("UTF-8")));
			
		} catch (Exception e) {
			throw e;
		} finally {
			if(null != outSteam){
				outSteam.close();
			}
			if(null != inStream){
				inStream.close();
			}
		}
	}
	
	/**
	 * 钱包支付准备
	 * @Title: pursePay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午7:46:53
	 */
	@RequestMapping(value = "/pay/pursePay")
	public String pursePay(@RequestParam String orderNos,HttpServletRequest request, Model model){
		List<String> orderNoList = JsonUtil.toArray(orderNos);
		Long userId = getUserId();
		List<SalesOrder> list = salesOrderBusiness.findSalesOrderListByOrderNoList(orderNoList);
		Long totalPrice = 0l;
		for(SalesOrder salesOrder : list){
			totalPrice += salesOrder.getTotalPrice();
		}
		Account account = accountBusiness.getAccountByUserId(userId);
		Long amount = account.getWithdrawAmount() != null ? account.getWithdrawAmount() : 0l;
		if(totalPrice > amount){
			model.addAttribute("insufficient", true);
		}
		//是否需要输入支付密码
		model.addAttribute("isPassword", request.getParameter("isPassword"));
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("amount", amount);
		model.addAttribute("orderNos", orderNos);
		return "/wap/trade/payment/pay-purse";
	}
	
	/**
	 * 准备钱包充值会员卡
	 * @Title: purseRecharge
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午8:19:10
	 */
//	@RequestMapping(value = "/pay/purseRecharge")
	public String purseRecharge(@RequestParam String rechargeNo, Model model){
		Long userId = getUserId();
		Recharge recharge = rechargeBusiness.getRechargeByRechargeNo(rechargeNo);
		Account account = accountBusiness.getAccountByUserId(userId);
		Long amount = account.getWithdrawAmount() != null ? account.getWithdrawAmount() : 0l;
		Long totalPrice = recharge.getDiscountPrice();
		if(totalPrice > amount){
			model.addAttribute("insufficient", true);
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("amount", amount);
		model.addAttribute("rechargeNo", rechargeNo);
		return "/wap/trade/recharge/pay-purse";
	}
	
	/**
	 * 钱包支付
	 * @Title: pursePayConfirm
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param paymentCode
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午8:01:45
	 */
	@RequestMapping(value = "/pay/pursePayConfirm", method = RequestMethod.POST)
	public String pursePayConfirm(String orderNos, String paymentCode, Model model){
		try{
			List<String> orderNoList = JsonUtil.toArray(orderNos);
			Long userId = getUserId();
			WalletTransactionRecord wtr = paymentBusiness.paymentConsumerByWallet(userId, paymentCode, orderNoList);
			if(null != wtr){
				salesOrderBusiness.dealOrderAfterPay(orderNoList);
			}
			User user = userBusiness.getUserById(userId);
			model.addAttribute("isWechat", getIsWechat(user));
			return "/wap/trade/payment/pay-success";
		}catch(BaseException e){
			log.error(e.getMessage(), e);
			model.addAttribute("message", e.getExternalMsg());
			return "/wap/common/error404";
		}
	}
	
	/**
	 * 钱包充值会员卡
	 * @Title: purseRechargeConfirm
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param paymentCode
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午8:23:01
	 */
	@RequestMapping(value = "/pay/purseRechargeConfirm", method = RequestMethod.POST)
	public String purseRechargeConfirm(String rechargeNo, String paymentCode, Model model){
		try{
			rechargePayBusiness.paymentConsumerByWallet(paymentCode, rechargeNo);
			return "/wap/trade/recharge/pay-success";
		}catch(BaseException e){
			log.error(e.getMessage(), e);
			model.addAttribute("message", e.getExternalMsg());
			return "/wap/common/error404";
		}
	}
	
	/**
	 * 支付成功
	 * @Title: paySuccess
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param tsn
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月16日 下午8:01:57
	 */
	@RequestMapping(value = "/pay/wechat-pay-success",method = RequestMethod.GET)
	public String paySuccess(String tsn,Model model) {
		User user = userBusiness.getUserById(getUserId());
		model.addAttribute("isWechat", getIsWechat(user));
		return "/wap/trade/payment/pay-success";
	}
	
	@RequestMapping(value = "/pay/wechat-recharge-success",method = RequestMethod.GET)
	public String rechargeSuccess(String tsn,Model model) {
		return "/wap/trade/recharge/pay-success";
	}
	
	/**
	 * 会员卡支付准备
	 * @Title: purseMemberCardPay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月17日 下午2:44:28
	 */
//	@RequestMapping(value = "/pay/memberCardPay")
	public String purseMemberCardPay(@RequestParam String orderNos, Model model){
		List<String> orderNoList = JsonUtil.toArray(orderNos);
		Long userId = getUserId();
		List<SalesOrder> list = salesOrderBusiness.findSalesOrderListByOrderNoList(orderNoList);
		Long totalPrice = 0l;
		for(SalesOrder salesOrder : list){
			totalPrice += salesOrder.getTotalPrice();
		}
		MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, super.getShopNo());
		Long amount = memberCard.getAmount() != null ? memberCard.getAmount() : 0l;
		if(totalPrice > amount){
			model.addAttribute("insufficient", true);
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("amount", amount);
		model.addAttribute("orderNos", orderNos);
		return "/wap/trade/payment/pay-membercard";
	}
	
	/**
	 * 会员卡支付
	 * @Title: purseMemberCardPayConfirm
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param paymentCode
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年4月17日 下午2:46:08
	 */
//	@RequestMapping(value = "/pay/memberCardPayConfirm", method = RequestMethod.POST)
	public String purseMemberCardPayConfirm(String orderNos, String paymentCode, Model model){
		try{
			List<String> orderNoList = JsonUtil.toArray(orderNos);
			Long userId = getUserId();
			MemberTransactionRecord wtr = paymentBusiness.paymentConsumerByMemberCard(userId, paymentCode, orderNoList);
			if(null != wtr){
				salesOrderBusiness.dealOrderAfterPay(orderNoList);
			}
			User user = userBusiness.getUserById(userId);
			model.addAttribute("isWechat", getIsWechat(user));
			return "/wap/trade/payment/pay-success";
		}catch(BaseException e){
			log.error(e.getMessage(), e);
			model.addAttribute("message", e.getExternalMsg());
			return "/wap/common/error404";
		}
	}
	
	/**
	 * 捷蓝接口
	 * 银行卡支付准备 跳转到 选择银行卡页面
	 * @Title: bankCardPursePay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年5月29日 下午3:41:24
	 */
	@RequestMapping(value = "/pay/bankCardPay")
	public String bankCardPursePay(String orderNos, String errorMsg, Model model){
		Long userId = getUserId();
		List<BankCardVo> bankCardList = bankCardProducer.findPayCardList(userId);
		model.addAttribute("bankCardList", bankCardList);
		model.addAttribute("orderNos", orderNos);
		model.addAttribute("errorMsg", errorMsg);
		return "/wap/trade/payment/pay-switch-card";
	}
	
	/**
	 * 捷蓝接口
	 * 选择了银行卡 去付款
	 * @Title: bankCardToPay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param bankCardId
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年5月29日 下午4:06:31
	 */
	@Token(save=true)
	@RequestMapping(value = "/pay/switchBankCard", method = RequestMethod.GET)
	public String bankCardToPay(String orderNos,Long bankCardId,String message,Model model){
		Assert.hasLength(orderNos,"参数orderNos为空");
		Assert.notNull(bankCardId,"参数bankCardId为空");
		List<String> orderNoList = JsonUtil.toArray(orderNos);
		List<SalesOrder> list = salesOrderBusiness.findSalesOrderListByOrderNoList(orderNoList);
		Long totalPrice = 0l;
		for(SalesOrder salesOrder : list){
			totalPrice += salesOrder.getTotalPrice();
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("bankCardId", bankCardId);
		model.addAttribute("orderNos", orderNos);
		model.addAttribute("message", message);
		return "/wap/trade/payment/pay-card";
	}
	/**
	 * 捷蓝接口
	 * 支付
	 * @Title: bindPay
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNos
	 * @param bankCardId
	 * @param paymentCode
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年5月29日 下午4:23:13
	 */
	@Token(remove=true)
	@RequestMapping(value = "/pay/bindPay", method = RequestMethod.POST)
	public String bindPay(String orderNos,Long bankCardId,String paymentCode,Model model){
		try {
			Assert.hasLength(orderNos,"参数orderNos为空");
			Assert.notNull(bankCardId,"参数bankCardId为空");
			Assert.notNull(paymentCode,"参数paymentCode为空");
			List<String> orderNoList = JsonUtil.toArray(orderNos);
			Long userId = getUserId();
			Integer retParam = paymentBusiness.bindPayProducer(userId, paymentCode, orderNoList, bankCardId);
			if(retParam == 1){
				return "/wap/trade/payment/pay-accepted";
			} else if(retParam == 2){
				return "/wap/trade/payment/pay-success";
			} else {
				model.addAttribute("message", "支付失败");
				return "/wap/common/error404";
			}
		} catch (BaseException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("message", e.getExternalMsg());
			model.addAttribute("orderNos", orderNos);
			model.addAttribute("bankCardId", bankCardId);
			return super.getRedirectUrl("/pay/switchBankCard");
		} 
	}
	
	/**
	 * 捷蓝接口 银行卡 充值 会员卡
	 * @Title: bankCardPurseRecharge
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param rechargeNo
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年6月1日 下午4:00:59
	 */
//	@RequestMapping(value = "/pay/bankCardRecharge")
	public String bankCardPurseRecharge(String rechargeNo, String errorMsg ,Model model){
		Long userId = getUserId();
		List<BankCardVo> bankCardList = bankCardProducer.findPayCardList(userId);
		model.addAttribute("bankCardList", bankCardList);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("rechargeNo", rechargeNo);
		return "/wap/trade/recharge/pay-switch-card";
	}
	
//	@RequestMapping(value = "/pay/switchBankCardForRecharge", method = RequestMethod.GET)
	public String bankCardToPayForRecharge(String rechargeNo,Long bankCardId,String message,Model model){
		Assert.hasLength(rechargeNo,"参数rechargeNo为空");
		Assert.notNull(bankCardId,"参数bankCardId为空");
		Recharge recharge = rechargeBusiness.getRechargeByRechargeNo(rechargeNo);
		
		model.addAttribute("totalPrice", recharge.getDiscountPrice());
		model.addAttribute("bankCardId", bankCardId);
		model.addAttribute("rechargeNo", rechargeNo);
		model.addAttribute("message", message);
		return "/wap/trade/recharge/pay-card";
	}
	
//	@RequestMapping(value = "/pay/bindPayForRecharge", method = RequestMethod.POST)
	public String bindPayForRecharge(String rechargeNo,Long bankCardId,String paymentCode,Model model){
		try {
			Assert.hasLength(rechargeNo,"参数rechargeNo为空");
			Assert.notNull(bankCardId,"参数bankCardId为空");
			Assert.notNull(paymentCode,"参数paymentCode为空");
			Integer retParam = rechargePayBusiness.bindPayProducer(paymentCode, rechargeNo, bankCardId);
			if(retParam == 1){
				return "/wap/trade/recharge/pay-accepted";
			} else if(retParam == 2){
				return "/wap/trade/recharge/pay-success";
			} else {
				model.addAttribute("message", "支付失败");
				return "/wap/common/error404";
			}
		} catch (BaseException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("message", e.getExternalMsg());
			model.addAttribute("rechargeNo", rechargeNo);
			model.addAttribute("bankCardId", bankCardId);
			return super.getRedirectUrl("/pay/switchBankCardForRecharge");
		} 
	}
	
	/**
	 * 订单支付生成微信支付二维码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/wechatNative",method = RequestMethod.POST)
	@ResponseBody
	public String wechatNative(HttpServletRequest request, HttpServletResponse response){
		String orderNos = request.getParameter("orderNos");
		if(null == orderNos){
			return getReqJson(false, "订单数据错误");
		}
		List<String> orderNoList = null;
		try {
			orderNoList = JsonUtil.toArray(orderNos);
		} catch (RuntimeException e) {
			log.error("订单数据错误", e);
			return getReqJson(false, "订单数据错误");
		}
		// 获取当前微信用户id
		Long userId = getUserId();
		// 主机地址
		String spbillCreateIp = request.getRemoteAddr();
		
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		
		WechatPayVo wechatPayVo = paymentBusiness.paymentWechatOrder(userId, null, spbillCreateIp, orderNoList,"NATIVE");
		
		clock.stop();
		log.info("微信统一下单耗时:" + clock.getTime() + " ms ");
		
		//微信支付二维码地址
		String codeUrl = wechatPayVo.getPrepayId();
		
		//生成二维码
		String json = generatedCode(codeUrl);
		
		return json;
	}
	
	/**
	 * 会员卡充值生成微信支付二维码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay/wechatNativeRecharge",method = RequestMethod.POST)
	@ResponseBody
	public String wechatNativeRecharge(HttpServletRequest request, HttpServletResponse response){
		String rechargeNo = request.getParameter("rechargeNo");
		if(null == rechargeNo){
			return getReqJson(false, "充值数据错误");
		}
		// 获取当前微信用户id
		Long userId = getUserId();
		// 主机地址
		String spbillCreateIp = request.getRemoteAddr();
		
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		
		WechatPayVo wechatPayVo = rechargePayBusiness.paymentWechatOrder(userId, null, spbillCreateIp, rechargeNo,"NATIVE");
		
		clock.stop();
		log.info("微信统一下单耗时:" + clock.getTime() + " ms ");
		
		//微信支付二维码地址
		String codeUrl = wechatPayVo.getPrepayId();
		
		//生成二维码
		String json = generatedCode(codeUrl);
		
		return json;
	}
	
	/**
	 * 生成二维码,并上传图片服务器
	 * @param codeUrl		微信的二维码支付
	 * @return				json:{'success':true,'info':path}
	 */
	private String generatedCode(String codeUrl){
		try {
			byte[] b = null;
			
			StopWatch QRcodeClock = new StopWatch();
			QRcodeClock.start(); // 计时开始
			
			byte[] qrbyte = QRCodeUtil.encode(codeUrl, b, true);
			
			QRcodeClock.stop();
			log.info("生成二维码耗费:" + QRcodeClock.getTime() + " ms ");
			
			if(null == qrbyte){
				return getReqJson(false, "生成二维码失败");
			}
			
			StopWatch uploadClock = new StopWatch();
			uploadClock.start(); // 计时开始
			
			//上传二维码
			String flag = imageService.upload(qrbyte);
			
			uploadClock.stop();
			log.info("上传图片服务器耗时:" + uploadClock.getTime() + " ms ");
			
			JSONObject jsonObject = JSONObject.fromObject(flag);
			if(!"SUCCESS".equals(jsonObject.get("flag").toString())){
				return getReqJson(false, "上传二维码失败");
			}
			if(StringUtils.isBlank(jsonObject.getString("path"))){
				return getReqJson(false, "上传二维码失败");
			}
			
			return getReqJson(true, jsonObject.getString("path"));
		} catch (Exception e) {
			log.error("上传二维码失败", e);
			return getReqJson(false, "上传二维码失败");
		}
	}
	
	@RequestMapping(value="/pay/alipay/alipayProducer")
	public String alipayProducer(String orderNos, Model model,HttpServletResponse response){
		Long userId = getUserId();
		List<String> orderNoList = JsonUtil.toArray(orderNos);
		String flag = paymentBusiness.alipayProducer(userId, orderNoList);
		System.out.println(flag);
		model.addAttribute("flag", flag);
		return "/wap/trade/payment/pay-alipay";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/pay/alipay/alipayConsumer")
	public String alipayConsumer(Model model,HttpServletRequest request,HttpServletResponse response){
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		if("T".equals(params.get("is_success"))){
			return "/wap/trade/payment/pay-accepted";
		}else{
			model.addAttribute("message", "支付受理失败");
			return "/wap/common/error404";
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/pay/alipay/alipayConsumerAsyn")
	public void alipayConsumerAsyn(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			AlipayTransactionRecord tr = paymentBusiness.alipayConsumer(params);
			if(null != tr){
				List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(params.get("out_trade_no"));
				List<String> ordersList = new ArrayList<String>();
				for (SalesOrder salesOrder : salesOrderList) {
					ordersList.add(salesOrder.getOrderNo());
				}
				salesOrderBusiness.dealOrderAfterPay(ordersList);
				response.getWriter().println("success");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	@RequestMapping(value="/pay/lakalaPay")
	public String lakalaSelect(String retCode,String orderNos, Model model,HttpServletResponse response){
		model.addAttribute("retCode", retCode);
		Long userId = getUserId();
		//List<String> orderNoList = JsonUtil.toArray(orderNos);
		List<BankCard> cardList= bankCardProducer.findBankCardListByUserId(userId);
		List<BankCardVo> list=new ArrayList<BankCardVo>();
		for(BankCard bc:cardList){
			BankCardVo vo=new BankCardVo(bc);
			list.add(vo);
		}
		model.addAttribute("orderNos", orderNos);
		model.addAttribute("bankCardList", list);
		return "/wap/trade/payment/lakala/topay";
	}
	@RequestMapping(value="/pay/lakala/producer")
	public String lakalaProducer(String orderNos,Long bankCardId, Model model,HttpServletResponse response){
		model.addAttribute("orderNos", orderNos);
		model.addAttribute("bankCardId", bankCardId);
		Long userId = getUserId();
		List<String> orderNoList = JsonUtil.toArray(orderNos);
		
		List<SalesOrder> list = salesOrderBusiness.findSalesOrderListByOrderNoList(orderNoList);
		Map<String,String> resultMap=paymentBusiness.lakalaProducer(list,userId, bankCardId);
		model.addAttribute("retCode", resultMap.get("retCode"));
		if(!"0000".equals(resultMap.get("retCode"))){
			if("0210".equals(resultMap.get("retCode"))){
				BankCard edit=new BankCard();
				edit.setId(bankCardId);
				edit.setLklSign(0);
				bankCardProducer.editBankCard(edit);
			}
			return getRedirectUrl("/pay/lakalaPay");
		}
		BankCard bc=bankCardProducer.getBankCardByPk(bankCardId);
		String cell=null;
		if(bc != null && (cell=bc.getCell()) != null){
			model.addAttribute("cell", cell.substring(0, 3)+"****"+cell.substring(cell.length()-4));
		}
		model.addAttribute("resultMap", resultMap);
		return "/wap/trade/payment/lakala/confirm";
		
	}
	@RequestMapping(value="/pay/lakala/consumer")
	@ResponseBody
	public String lakalaConsumer(HttpServletRequest request,String orderNos,Long bankCardId, Model model,HttpServletResponse response){
		
		Long userId=getUserId();
		Map<String, String> resultMap=paymentBusiness.lakalaConsumer(request, orderNos, userId, bankCardId);
		return JsonUtil.fromMap(resultMap, false, new String[]{});
		//{"retCode":"0312","retMsg":"交易未成功，短信验证码错误"}
		//{"retCode":"0308","retMsg":"订单：2015090900044099重复支付"}
		//{"retCode":"0008","retMsg":"此订单已支付成功，请勿重复操作"}
		//{"retCode":"0307","retMsg":"支付失败"}
	}
	@RequestMapping("/pay/pay-success")
	public String paySuccess(Model model){
		User user = userBusiness.getUserById(getUserId());
		model.addAttribute("isWechat", getIsWechat(user));
		return "/wap/trade/payment/pay-success";
	}
	
	/**
	 * @Description: 描述
	 * @param orderNos
	 * @param type:1,支付页面;2,银行卡列表页面 ;
	 * @param model
	 * @return
	 * @date: 2015年10月4日 下午3:27:51
	 * @author: zyl
	 */
	@RequestMapping("/pay/lakala/addBankCard")
	public String toAddBankCard(String orderNos,String type,Model model){
		model.addAttribute("orderNos", orderNos);
		model.addAttribute("type", type);
		Long userId=getUserId();
		User user=userProducer.getUserById(userId);
		if(user!=null){
			String identity=user.getIdentity();
			String name=user.getContacts();
			model.addAttribute("name", name);
			if(identity!=null && identity.length()>=15){
				model.addAttribute("identity", identity.substring(0, 4)+"********"+identity.substring(identity.length()-4));
			}
		}
		return "/wap/trade/payment/lakala/addBankCard";
	}
	@RequestMapping(value="/pay/lakala/checkBankCard",method=RequestMethod.POST)
	@ResponseBody
	public String checkBankCard(String bankCard){
		BankCardVo bankCardVo=paymentBusiness.getBankCardMsgByCardNo(bankCard);
		log.info(bankCardVo);
		return getReqJson(bankCardVo!=null, "");
	}
	@RequestMapping(value="/pay/lakala/addBankCard",method=RequestMethod.POST)
	public String addBankCard(String orderNos,String bankCard,String type,Model model){
		model.addAttribute("orderNos", orderNos);
		model.addAttribute("bankCard", bankCard);
		model.addAttribute("type", type);
		Long userId=getUserId();
		User user=userProducer.getUserById(userId);
		LakalaReq check=new LakalaReq();
		check.setCardNo(bankCard);
		check.setClientId(user.getIdentity());
		check.setClientName(user.getContacts());
		check.setMobile(user.getCell());
		//String result=paymentBusiness.signCheck(check);
		BankCard bc=bankCardProducer.getBankCardByCardNoAndUserId(bankCard,userId);
		if(bc!= null && bc.getLklSign()==1){
			model.addAttribute("sign", 1);
			if(user!=null){
				String identity=user.getIdentity();
				String name=user.getContacts();
				model.addAttribute("name", name);
				if(identity!=null && identity.length()>=15){
					model.addAttribute("identity", identity.substring(0, 4)+"********"+identity.substring(identity.length()-4));
				}
			}
			return "/wap/trade/payment/lakala/addBankCard";
		}
		BankCardVo bankCardVo=paymentBusiness.getBankCardMsgByCardNo(bankCard);
		model.addAttribute("bankCardVo", bankCardVo);
		return "/wap/trade/payment/lakala/sign";
	}
	@RequestMapping(value="/pay/lakala/getSignCode",method=RequestMethod.POST)
	@ResponseBody
	public String getSignCode(String bankCard,String cell,String cvv,String endTime){
		Long userId=getUserId();
		User user=userProducer.getUserById(userId);
		LakalaReq lr=new LakalaReq();
		lr.setCardNo(bankCard);
		lr.setCvv(cvv);
		if(endTime!=null && endTime.length()==5 && endTime.indexOf("/")==2){
			endTime=endTime.substring(3, 5)+endTime.substring(0, 2);
		}else if(endTime!=null && endTime.length()==4){
			endTime=endTime.substring(2, 4)+endTime.substring(0, 2);
		}
		lr.setDateOfExpire(endTime);
		lr.setMobile(cell);
		lr.setClientId(user.getIdentity());
		lr.setClientName(user.getContacts());
		Map<String,String> resultMap=paymentBusiness.getLklSignCode(lr);
		log.info(resultMap);
		//{"retCode":"1002","retMsg":"此卡已签约","agstat":null}
		if("1002".equals(resultMap.get("retCode"))){
			/** 已签约,将此银行卡插入数据库*/
			BankCard bc=new BankCard();
			bc.setBankCard(bankCard);
			bc.setCvv(cvv);
			bc.setEndTime(endTime);
			bc.setCell(cell);
			paymentBusiness.addLklSignBankCard(bc,userId, 2);
		}
		String result=JsonUtil.fromMap(resultMap, false, new String[]{});
		return result;
	}
	@RequestMapping(value="/pay/lakala/getPayCode",method=RequestMethod.POST)
	@ResponseBody
	public String getPayCode(HttpServletRequest request,String bankCard,String cell,String cvv,String endTime){
		LakalaReq lr=new LakalaReq();
		
		String merOrderId = request.getParameter("merOrderId");
		String orderAmount = request.getParameter("orderAmount");
		String payeeAmount = request.getParameter("payeeAmount");
		String msgCode = request.getParameter("msgCode");
		String transactionId = request.getParameter("transactionId");
		String ext1 = request.getParameter("ext1");//扩展字段1
		String ext2 = request.getParameter("ext2");//扩展字段2
		lr.setMerOrderId(merOrderId);
		lr.setOrderAmount(orderAmount);
		lr.setPayeeAmount(payeeAmount);
		lr.setMsgCode(msgCode);
		lr.setTransactionId(transactionId);
		lr.setExt1(ext1);
		lr.setExt2(ext2);
		Map<String,String> resultMap=paymentBusiness.getLklPayCode(lr);
		//{"retCode":"1002","retMsg":"此卡已签约","agstat":null}
		log.info(resultMap);
		return JsonUtil.fromMap(resultMap, false, new String[]{});
	}
	@RequestMapping(value="/pay/lakala/sign",method=RequestMethod.POST)
	@ResponseBody
	public String lakalaSign(String orderNos,String bankCard,String cell,String cvv,String endTime,String msgCode,Model model){
		Long userId=getUserId();
		User user=userProducer.getUserById(userId);
		LakalaReq lr=new LakalaReq();
		lr.setCardNo(bankCard);
		lr.setCvv(cvv);
		if(endTime!=null && endTime.length()==5 && endTime.indexOf("/")==2){
			endTime=endTime.substring(3, 5)+endTime.substring(0, 2);
		}else if(endTime!=null && endTime.length()==4){
			endTime=endTime.substring(2, 4)+endTime.substring(0, 2);
		}
		lr.setDateOfExpire(endTime);
		lr.setMobile(cell);
		lr.setClientId(user.getIdentity());
		lr.setClientName(user.getContacts());
		lr.setMsgCode(msgCode);
		Map<String,String> resultMap=paymentBusiness.lklSign(lr);
		log.info(resultMap);
		//{"retCode":"0002","retMsg":"请求参数错误,支付卡卡号为空"}
		//{"retCode":"1105","retMsg":"签约短信验证失败,短信验证码错误","mobile":"18758206507"}
		//{"retCode":"1101","retMsg":"交易失败,签约信息有误","mobile":"18758206507"}
		//{"retCode":"1101","retMsg":"银行认证中，请稍后再试","mobile":"18758206507"}
		//{"retCode":"1101","retMsg":"短信验证失败","mobile":"18758206507"}
		//{"retCode":"1101","retMsg":"交易失败,账号有误","mobile":"18758206507"}
		if("0000".equals(resultMap.get("retCode"))){
			/** 已签约,将此银行卡插入数据库*/
			BankCard bc=new BankCard();
			bc.setBankCard(bankCard);
			bc.setCvv(cvv);
			bc.setEndTime(endTime);
			bc.setCell(cell);
			paymentBusiness.addLklSignBankCard(bc,userId, 1);
		}
		String result=JsonUtil.fromMap(resultMap, false, new String[]{});
		return result;
	}
}
