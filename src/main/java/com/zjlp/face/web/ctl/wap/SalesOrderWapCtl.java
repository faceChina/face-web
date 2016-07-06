package com.zjlp.face.web.ctl.wap;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.http.filter.PopularizeFilter;
import com.zjlp.face.web.http.filter.SubbranchFilter;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuy;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuyVo;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.util.WeixinUtil;


@Controller
@RequestMapping("/wap/{shopNo}/buy")
public class SalesOrderWapCtl extends WapCtl {
	private Log log=LogFactory.getLog(getClass());
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	
	@Autowired
	private AccountBusiness accountBusiness;
	
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	
	/**
	 * @Description: 生成订单
	 * @param orderBuyVo
	 * @return
	 * @date: 2015年3月18日 上午9:54:50
	 * @author: zyl
	 */
	@Token(remove=true)
	@RequestMapping(value = "/order/orderBuy",method=RequestMethod.POST)
	public String orderBuy(OrderBuyVo orderBuyVo,HttpSession httpSession, HttpServletRequest httpRequest, Model model){
		try{
			orderBuyVo.setUserId(getUserId());
			if (1==orderBuyVo.getBuyType()) {
				List<OrderBuy> list = orderBuyVo.getOrderBuyList();
				Shop buyShop = super.getShop();
				list.get(0).setBuyShopNo(buyShop.getNo());
				//推广id
				String shareId = PopularizeFilter.getShareId(httpSession, getUserId());
				list.get(0).getBuyItemList().get(0).setShareId(shareId);
				//分店id
				String subbranchId = SubbranchFilter.getSubbranchId(httpSession);
				if (StringUtils.isNotBlank(subbranchId)) {
					orderBuyVo.setSubbranchId(Long.valueOf(subbranchId));
				}
			}
			/** 生成订单 */
			List<String> orderNoList = salesOrderBusiness.add(orderBuyVo);
			String orderNos = JsonUtil.fromCollection(orderNoList);
			
			Long totalPrice = 0l;
			for(SalesOrder order : orderBuyVo.getOrderList()){
				totalPrice = totalPrice + order.getTotalPrice();
			}
			
			/**
			 * 判断订单总价是否为零,若为零直接跳转到支付成功页面
			 */
//			if(totalPrice.equals(0l)){
//				return "/wap/trade/payment/pay-success";
//			}
			/**
			 * 
			 */
			
			boolean existPaymentCode=accountBusiness.existPaymentCode(getUserId());
			
			if(totalPrice.equals(0l)){
				log.info("支付金额0元,无需选择支付方式,默认直接支付.");
				model.addAttribute("orderNos", orderNos);
				model.addAttribute("isPassword", true);
				return getRedirectUrl("/pay/pursePay.htm");
			}
			
			model.addAttribute("orderNos", orderNos);
			model.addAttribute("title", orderBuyVo.getOrderList().get(0).getTitle());
			
			/**判断是否有会员卡**/
			MemberCard memberCard = _getMemberCardIsUse(orderBuyVo);
			model.addAttribute("memberCard", memberCard);
			model.addAttribute("existPaymentCode", existPaymentCode);
			model.addAttribute("totalPrice", totalPrice);
			boolean isWechatPay = WeixinUtil.isWechatBrowser(httpRequest);
			model.addAttribute("isWechatPay", isWechatPay);
			return "/wap/trade/account/pay";
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return getRedirectUrl("/wap/"+getShopNo()+"/any/list");
		}
	}

	@RequestMapping(value = "/order/calculatePostFee",method=RequestMethod.POST)
	@ResponseBody
	public String calculatePostFee(OrderBuyVo orderBuyVo, Model model){
		orderBuyVo.setUserId(getUserId());
		Long postFee = salesOrderBusiness.calculatePostFee(orderBuyVo);
		return getReqJson(true, postFee.toString());
	}
	/**
	 * @Description: 买家查询订单
	 * @param pagination
	 * @param states
	 * @param model
	 * @return
	 * @date: 2015年3月18日 上午9:57:07
	 * @author: zyl
	 */
	@RequestMapping(value = "/order/list")
	public String findSalesOrderForWap(Pagination<SalesOrderDto> pagination, Integer status, Model model){
		if(null != status && 1 != status && 2 != status && 3 != status && 4 != status && 5 != status){
			status = null;
		}
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setUserId(getUserId());
		salesOrderVo.setStatus(status);
		pagination = salesOrderBusiness.findOrderPageForWap(salesOrderVo, pagination);
		// 
		Integer newOrder = 0;
		Integer payOrder = 0;
		Integer deliverOrder = 0;
		newOrder = operateDataBusiness.countBuyerOrderNumber(getUserId(), 1);
		payOrder = operateDataBusiness.countBuyerOrderNumber(getUserId(), 2);
		deliverOrder = operateDataBusiness.countBuyerOrderNumber(getUserId(), 3);
		model.addAttribute("pagination", pagination);
		model.addAttribute("status", status);
		model.addAttribute("newOrderNumber", newOrder);
		model.addAttribute("payOrderNumber", payOrder);
		model.addAttribute("deliverOrderNumber", deliverOrder);
		return "/wap/trade/order/order";
	}

	/**
	 * @Description: 买家订单分页加载
	 * @param pagination
	 * @param states
	 * @return
	 * @date: 2015年3月18日 上午9:57:25
	 * @author: zyl
	 */
	@RequestMapping(value = "/order/appendList", method = RequestMethod.POST)
	public String appendSalesOrderForWap(Pagination<SalesOrderDto> pagination, Integer status,Model model){
		try{
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setUserId(getUserId());
			salesOrderVo.setStatus(status);
			pagination = salesOrderBusiness.findOrderPageForWap(salesOrderVo, pagination);
			model.addAttribute("pagination", pagination);
			return "/wap/trade/order/append";
		}catch(Exception e){
			return super.getReqJson(false, "动态加载失败");
		}
	}

	/**
	 * 按状态查询预约订单列表
	 * @param pagination
	 * @param status 预约订单状态
	 * @param model
	 * @return
	 * @author lhh
	 */
	@RequestMapping(value = "/order/bookOrder")
	public String queryBookOrder(Pagination<SalesOrderDto> pagination, Integer status, Model model) {
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setUserId(getUserId());
		salesOrderVo.setStatus(status);
		salesOrderVo.setOrderCategory(3);
		pagination = salesOrderBusiness.findOrderPageForWap(salesOrderVo, pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("status", status);
		return "/wap/trade/order/book-list-buyer";
	}

	/**
	 * 按状态查询预约订单列表
	 * @param pagination
	 * @param status 预约订单状态
	 * @param model
	 * @return
	 * @author lhh
	 */
	@RequestMapping(value = "/order/appendBookOrder")
	public String appendBookOrder(Pagination<SalesOrderDto> pagination, Integer status, Model model) {
		try{
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setUserId(getUserId());
			salesOrderVo.setStatus(status);
			salesOrderVo.setOrderCategory(3);
			pagination = salesOrderBusiness.findOrderPageForWap(salesOrderVo, pagination);
			model.addAttribute("pagination", pagination);
			return "/wap/trade/order/book-append";
		}catch(Exception e){
			return super.getReqJson(false, "动态加载失败");
		}
	}
	
	@RequestMapping(value = "/order/detail")
	public String detail(String orderNo, Model model){
		salesOrderBusiness.validateOrderByUserId(orderNo, super.getUserId());
		SalesOrderDto salesOrderDto = salesOrderBusiness.getSalesOrderDetailByOrderNo(orderNo);
		model.addAttribute("salesOrder", salesOrderDto);
		if (salesOrderDto.getOrderCategory().equals(3)) {
			return "/wap/trade/order/book-detail";
		} else {
			String lowPrice = "";
			if((null==salesOrderDto.getCouponPrice() || salesOrderDto.getCouponPrice().longValue()==0L) && 
															(null!=salesOrderDto.getIntegral() && salesOrderDto.getIntegral().longValue()>0L)){
				lowPrice = "integral";
			}else if((null==salesOrderDto.getIntegral() || salesOrderDto.getIntegral().longValue()==0L) &&
															(null!=salesOrderDto.getCouponPrice() && salesOrderDto.getCouponPrice().longValue()>0L)){
				lowPrice = "coupon";
			}
			
			model.addAttribute("lowPrice", lowPrice);
			return "/wap/trade/order/detail";
		}
	}
	
	@RequestMapping(value = "/order/orderToPay")
	public String orderToPay(HttpServletRequest httpRequest, String orderNo, Model model){
		SalesOrder salesOrder = salesOrderBusiness.getSalesOrderByOrderNo(orderNo);
		if(salesOrder == null || Constants.STATUS_DELETE.equals(salesOrder.getStatus()) || !salesOrder.getUserId().equals(getUserId())){
			throw new OrderException("订单不存在");
		}
		AssertUtil.isTrue(Constants.STATUS_WAIT.equals(salesOrder.getStatus()), "订单状态异常","当前订单不能支付");
		
		model.addAttribute("salesOrder", salesOrder);
		List<String> orderNoList = new ArrayList<String>();
		orderNoList.add(orderNo);
		model.addAttribute("orderNos", JsonUtil.fromCollection(orderNoList));
		model.addAttribute("title", salesOrder.getTitle());
		boolean exist = accountBusiness.existPaymentCode(getUserId());
		model.addAttribute("existPaymentCode", exist);
		Long totalPrice = salesOrder.getTotalPrice();
		model.addAttribute("totalPrice", totalPrice);
		/**判断是否有会员卡**/
		MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(getUserId(), salesOrder.getShopNo());
		model.addAttribute("memberCard", memberCard);
		boolean isWechatPay = WeixinUtil.isWechatBrowser(httpRequest);
		model.addAttribute("isWechatPay", isWechatPay);
		return "/wap/trade/account/pay";
	}
	
	@RequestMapping(value = "/order/cancelOrder",method=RequestMethod.POST)
	@ResponseBody
	public String cancelOrder(String orderNo){
		salesOrderBusiness.validateOrderByUserId(orderNo, super.getUserId());
		salesOrderBusiness.cancleOrder(orderNo, super.getUserId());
		return getReqJson(true, "取消成功");
	}
	
	@RequestMapping(value = "/order/confirmOrder",method=RequestMethod.POST)
	@ResponseBody
	public String confirmOrder(String orderNo, String paymentCode){
		salesOrderBusiness.validateOrderByUserId(orderNo, super.getUserId());
		if(!accountBusiness.existPaymentCode(getUserId())){
			return getReqJson(false, "支付密码不存在");
		}
		if(!accountBusiness.checkPaymentCode(getUserId(), paymentCode)){
			return getReqJson(false, "支付密码错误");
		}
		salesOrderBusiness.receiveOrder(orderNo, super.getUserId());
		return getReqJson(true, "收货成功");
	}
	
	@RequestMapping(value = "/order/deleteOrder",method=RequestMethod.POST)
	@ResponseBody
	public String deleteOrder(String orderNo){
		salesOrderBusiness.validateOrderByUserId(orderNo, super.getUserId());
		salesOrderBusiness.deleteOrder(orderNo, super.getUserId());
		return getReqJson(true, "删除成功");
	}

	/**
	 * 修改订单状态接口，只支持预约订单的状态修改
	 * @param status 要修改为的订单状态
	 * @return`
	 */
	@RequestMapping(value = "/{orderNo}/{status}", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(@PathVariable String orderNo, @PathVariable Integer status) {
		//验证对该订单是否有权限
		salesOrderBusiness.validateOrderByUserId(orderNo, getUserId());
		salesOrderBusiness.updateOrderStatus(orderNo,getUserId(),status,null);
		return getReqJson(true, "取消成功");
	}
	
	/**
	 * 验证是否可以使用会员卡
	 * @Title: _getMemberCardIsUse
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderBuyVo
	 * @return
	 * @return boolean
	 * @author phb
	 * @date 2015年4月21日 下午2:10:44
	 */
	private MemberCard _getMemberCardIsUse(OrderBuyVo orderBuyVo){
		MemberCard tempCard = null;
		for (OrderBuy orderBuy : orderBuyVo.getOrderBuyList(true)) {
			MemberCard card = memberProducer.getMemberCardByUserIdAndShopNo(getUserId(), orderBuy.getShopNo());
			//没有可用会员卡
			if(null == card){
				return null;
			}
			if(null == tempCard){
				tempCard = card;
				continue;
			}
			//不是一个卖家(不是一张卡)
			if(tempCard.getId().longValue() != card.getId().longValue()){
				return null;
			}
		}
		return tempCard;
	}
}
