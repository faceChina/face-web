package com.zjlp.face.web.ctl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.PurchaseOrderBussiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;

@Controller
@RequestMapping("/u/order")
public class SalesOrderCtl extends BaseCtl {
//	private Log logger = LogFactory.getLog(SalesOrderCtl.class);
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private PurchaseOrderBussiness purchaseOrderBussiness;
	@Autowired
	private OperateDataBusiness operateDataBusiness;

	/**
	 * 查询订单列表
	 * @param model
	 * @param pagination 查询参数
	 * @param salesOrderVo
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination<SalesOrderDto> pagination, SalesOrderVo salesOrderVo){
		String shopNo = super.getShopNo();
		Integer todayAll=salesOrderBusiness.countTodayAll(shopNo);
		Integer wait = operateDataBusiness.countMyShopOrderNumber(shopNo, Constants.STATUS_WAIT);
		Integer pay = operateDataBusiness.countMyShopOrderNumber(shopNo, Constants.STATUS_PAY);
		model.addAttribute("todayCompile", todayAll);
		model.addAttribute("wait", wait);
		model.addAttribute("pay", pay);
		salesOrderVo.setShopNo(shopNo);
		String sellerName = salesOrderVo.getSellerName();
		if (StringUtils.isNotBlank(sellerName)) {
			try {
				String decode = URLDecoder.decode(sellerName, "UTF-8");
				salesOrderVo.setSellerName(decode);
			} catch (UnsupportedEncodingException e) {
				throw new OrderException(e);
			}
		}
		if (null != salesOrderVo && null != salesOrderVo.getOrderCategory() && 0 == salesOrderVo.getOrderCategory()) {
			salesOrderVo.setOrderCategory(null);
		}
		pagination = salesOrderBusiness.findOrderPage(pagination, salesOrderVo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("shopNo", shopNo);
		return "/m/trade/order/list";
	}

	/**
	 * 查询预订订单列表
	 * @param model
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/bookOrder/list")
	public String getBookList(Model model, Pagination<SalesOrderDto> pagination) {
		String shopNo = super.getShopNo();
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo(shopNo);
		salesOrderVo.setOrderCategory(3);
		pagination = salesOrderBusiness.findSalesOrderDetailPage(pagination, salesOrderVo);
		model.addAttribute("pagination", pagination);
		return "/m/operation/appoint/appointment-order";
	}

	/**
	 * 查询预订订单列表
	 * @param model
	 * @param pagination
	 * @param salesOrderVo 查询参数
	 * @return
	 */
	@RequestMapping(value = "/bookOrder/list" ,method = RequestMethod.POST)
	public String queryBookList(Model model, Pagination<SalesOrderDto> pagination, SalesOrderVo salesOrderVo) {
		String shopNo = super.getShopNo();
		salesOrderVo.setShopNo(shopNo);
		salesOrderVo.setOrderCategory(3);
		pagination = salesOrderBusiness.findSalesOrderDetailPage(pagination, salesOrderVo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("salesOrderVo",salesOrderVo);
		return "/m/operation/appoint/appointment-order";
	}
	
	@RequestMapping(value = "/detail")
	public String detail(Model model, String orderNo){
		String shopNo = super.getShopNo();
		salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
		SalesOrderDto salesOrderDto = salesOrderBusiness.getSalesOrderDetailByOrderNo(orderNo);
		model.addAttribute("salesOrder", salesOrderDto);
		if (salesOrderDto.getOrderCategory().equals(3)) {
			return "/m/operation/appoint/appointment-order-detail";
		}
		Long payCommission = purchaseOrderBussiness.getOrderPayCommission(orderNo, shopNo, 1);
		model.addAttribute("payCommission", payCommission);
		model.addAttribute("shopNo", shopNo);
		return "/m/trade/order/detail";
	}
	
	@RequestMapping(value = "/delivery")
	@ResponseBody
	public String dispatch(Model model, SalesOrder salesOrder){
		String shopNo = super.getShopNo();
		salesOrderBusiness.validateOrderByShopNo(salesOrder.getOrderNo(), shopNo);
		salesOrderBusiness.deliveryOrder(salesOrder, super.getUserId());
		return super.getReqJson(true, "发货成功");
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public String cancel(String orderNo){
		String shopNo = super.getShopNo();
		salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
		salesOrderBusiness.cancleOrder(orderNo, super.getUserId());
		return "success";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(String orderNo){
		String shopNo = super.getShopNo();
		salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
		salesOrderBusiness.deleteOrder(orderNo, super.getUserId());
		return getForwardUrl("/u/order/list");
	}
	
	@RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
	public String orderDetail(Model model, String orderNo){
		SalesOrderDto salesOrderDto = salesOrderBusiness.getSalesOrderDetailByOrderNo(orderNo);
		model.addAttribute("salesOrder", salesOrderDto);
		return "/m/trade/order/updatePrice";
	}
	
	@RequestMapping(value = "/updatePrice", method = RequestMethod.POST)
	public String updatePrice(String orderNo, String adjustPriceYuan){
		String shopNo = super.getShopNo();
		salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
		Long adjustPrice = CalculateUtils.converYuantoPenny(String.valueOf(adjustPriceYuan));
		salesOrderBusiness.adjustOrderPrice(orderNo,super.getUserId(),adjustPrice);
		return getForwardUrl("/u/order/list");
	}
	
//	@RequestMapping(value = "/updatePrice", method = RequestMethod.POST)
//	public String updatePrice(String orderNo, String json){
//		String shopNo = super.getShopNo();
//		salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
//		List<OrderItem> orderItemList = JsonUtil.toArrayBean(json, OrderItem.class);
//		salesOrderBusiness.adjustPrice(orderNo, orderItemList, super.getUserId());
//		return getForwardUrl("/u/order/list");
//	}

	@RequestMapping(value = "/remark", method = RequestMethod.POST)
	@ResponseBody
	public String remark(String orderNo, String sellerMemo){
		salesOrderBusiness.sellerRemark(orderNo, sellerMemo);
		return getReqJson(true, "");
	}

	/**
	 * 修改订单状态接口，只支持预约订单的状态修改
	 * @param status 要修改为的订单状态
	 * @return`
	 */
	@RequestMapping(value = "/{orderNo}/{status}", method = RequestMethod.POST)
	public String updateStatus(@PathVariable String orderNo, @PathVariable Integer status,String memo) {
		//验证对该订单是否有权限
		salesOrderBusiness.validateOrderByShopNo(orderNo, getShopNo());
		salesOrderBusiness.updateOrderStatus(orderNo,getUserId(),status,memo);
		return "success";
	}

}
