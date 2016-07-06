package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AgencyOrderVo;
import com.zjlp.face.web.appvo.AssOrderItem;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller
@RequestMapping({"/assistant/ass/subOrder/"})
public class SubBranchOrderAppCtl extends BaseCtl {
	
	public static final String[] LIST_JSON_FIELDS = {"orderStatus","shopNo","curPage","start","pageSize","totalRow",
		"newOrderNumber","payOrderNumber","deliverOrderNumber","receiveOrderNumber",
		"orders.orderNo","orders.status","orders.quantity",
		"orders.totalPriceStr","orders.totalPrice","orders.payPrice","orders.payPriceStr","orders.totalProfitPriceStr","orders.totalProfitPrice","orders.adjustPrice","orders.adjustPriceStr",
		"orders.purchaserNick","orders.isDeliver","orders.orderCategory","orders.isModifyPrice",
		"orders.createTimeStamp","orders.goodPicturePath","orders.deliveryWay","orders.orderItemList.goodName","orders.orderItemList.priceStr","orders.orderItemList.price",
		"orders.orderItemList.quantity","orders.orderItemList.skuPicturePath","orders.orderItemList.popularize"};
	
	 public static final String[] DETAIL_JSON_FIELDS = {"orderNo","status","createTimeStamp","buyerMessage","totalPrice","totalPriceStr","payPrice","payPriceStr","price","priceStr","purchaserNick",
			"sellerMemo","deliveryCompany","deliverySn","senderName","senderCell","deliveryWay","totalProfitPrice","totalProfitPriceStr","orderCategory","isDeliver","isModifyPrice",
			"refuseReason","adjustPrice","adjustPriceStr","integral","integralStr","discountPrice","couponPriceStr","promoteSpend","promoteSpendStr","postFee","postFeeStr","totalIncome","totalIncomeStr",
			"receiverName","receiverPhone","receiverAddressInfo","orderItemList.skuPicturePath","orderItemList.goodName",
			"orderItemList.price", "orderItemList.priceStr", "orderItemList.quantity", "orderItemList.goodStatus","orderItemList.detailWapUrl","orderItemList.skuPropertiesName"};
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private SubBranchSaleOrderBusiness subBranchSaleOrderBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	@Autowired
	private GoodBusiness goodBusiness;
	
	/**
	 * 分店店铺订单列表
	* @Title: list
	* @Description: 分店店铺订单列表
	* @param jsonObj
	* @param pagination
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月29日 下午5:16:46
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public String list(@RequestBody JSONObject jsonObj,Pagination<SubBranchSalesOrderVo> pagination){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.getString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			
			Object orderStatus = jsonObj.get("orderStatus");
			Integer status  = null ;
			if (null != orderStatus && !"".equals(orderStatus)) {
				Integer temp = Integer.parseInt(orderStatus.toString());
				if (0 != temp.intValue()) {
					status = temp;
				}
			}
			String searchKey = jsonObj.optString("searchKey");
			if (StringUtils.isEmpty(searchKey)){
				searchKey = null;
			}else{
				searchKey = searchKey.toUpperCase();
			}
			Long id = Long.valueOf(shopNo);
			Subbranch subbranch = subbranchBusiness.findSubbranchById(id);
			String phone = subbranch.getUserCell();
			//增加客户ID来过滤客户订单
			Long customerId = null;
			Object customerIdObj = jsonObj.get("customerId");
			if (null != customerIdObj && org.apache.commons.lang3.StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.parseLong(customerIdObj.toString());
			}
			//判断是都是来自客户详情下店铺列表的请求标志位
			String isShopRequest = null;
			Object isShopRequestObj = jsonObj.get("isShopRequest");
			if (isShopRequestObj != null && org.apache.commons.lang.StringUtils.isNotBlank(isShopRequestObj.toString())) {
				isShopRequest = isShopRequestObj.toString();
			}
			pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageBySubbranchIdAndStatusForOwn(id, status, null,searchKey, pagination,customerId,isShopRequest);
			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
			for (SubBranchSalesOrderVo order : pagination.getDatas()) {
				AgencyOrderVo assorder = new AgencyOrderVo(order);
				//组装商品信息
				List<AssOrderItem> assOrderItemList = new ArrayList<AssOrderItem>();
				for (OrderItem orderItem : order.getOrderItems()) {
					AssOrderItem item = new AssOrderItem(orderItem);
					assOrderItemList.add(item);
				}
				authOperation(order,assorder,phone);
				assorder.setOrderItemList(assOrderItemList);
				list.add(assorder);
			}
			
			//统计订单信息
			Integer newOrder = 0;
			Integer payOrder = 0;
			Integer deliverOrder = 0;
			Integer receiveOrder= 0;
			
			newOrder = operateDataBusiness.countOwnSaleSubOrderNumber(id, AssConstantsUtil.ORDER_STATE_1, null);
			payOrder = operateDataBusiness.countOwnSaleSubOrderNumber(id, AssConstantsUtil.ORDER_STATE_2, null);
			deliverOrder = operateDataBusiness.countOwnSaleSubOrderNumber(id, AssConstantsUtil.ORDER_STATE_3, null);
			receiveOrder = operateDataBusiness.countOwnSaleSubOrderNumber(id, AssConstantsUtil.ORDER_STATE_4,null);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("orderStatus", status);
			dataMap.put("shopNo", shopNo);
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			dataMap.put("newOrderNumber", newOrder);
			dataMap.put("payOrderNumber", payOrder);
			dataMap.put("deliverOrderNumber", deliverOrder);
			dataMap.put("receiveOrderNumber", receiveOrder);
			String s =  outSucceed(dataMap, true, LIST_JSON_FIELDS);
			return s;
		} catch (Exception e) {
			_logger.error("分销商查询代理订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 分店订单列表
	* @Title: subList
	* @Description:  分店订单列表
	* @param jsonObj
	* @param pagination
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月29日 下午5:16:33
	 */
	@RequestMapping(value="subList")
	@ResponseBody
	public String subList(@RequestBody JSONObject jsonObj,Pagination<SubBranchSalesOrderVo> pagination){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			Object orderStatus = jsonObj.get("orderStatus");
			Integer status  = null ;
			if (null != orderStatus && !"".equals(orderStatus.toString())) {
				if (null != orderStatus && !"".equals(orderStatus)) {
					Integer temp = Integer.parseInt(orderStatus.toString());
					if (0 != temp.intValue()) {
						status = temp;
					}
				}
			}
			String searchKey = jsonObj.optString("searchKey");
			if (StringUtils.isEmpty(searchKey)){
				searchKey = null;
			}else{
				searchKey = searchKey.toUpperCase();
			}
			Long id = Long.valueOf(shopNo);
			Subbranch subbranch = subbranchBusiness.findSubbranchById(id);
			String phone = subbranch.getUserCell();
			pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageBySubbranchIdAndStatusForSub(id, status, null,searchKey, pagination);
			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
			for (SubBranchSalesOrderVo order : pagination.getDatas()) {
				AgencyOrderVo assorder = new AgencyOrderVo(order);
				// 佣金字段转换
				assorder.setTotalProfitPrice(order.getPreLeveCommssion());
				
				//组装商品信息
				List<AssOrderItem> assOrderItemList = new ArrayList<AssOrderItem>();
				for (OrderItem orderItem : order.getOrderItems()) {
					AssOrderItem item = new AssOrderItem(orderItem);
					assOrderItemList.add(item);
				}
				authOperation(order,assorder,phone);
				assorder.setOrderItemList(assOrderItemList);
				list.add(assorder);
			}
			
			//统计订单信息
			Integer newOrder = 0;
			Integer payOrder = 0;
			Integer deliverOrder = 0;
			
			newOrder = operateDataBusiness.countSubSaleSubOrderNumber(id, AssConstantsUtil.ORDER_STATE_1);
			payOrder = operateDataBusiness.countSubSaleSubOrderNumber(id, AssConstantsUtil.ORDER_STATE_2);
			deliverOrder = operateDataBusiness.countSubSaleSubOrderNumber(id,AssConstantsUtil.ORDER_STATE_3);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("orderStatus", status);
			dataMap.put("shopNo", shopNo);
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			dataMap.put("newOrderNumber", newOrder);
			dataMap.put("payOrderNumber", payOrder);
			dataMap.put("deliverOrderNumber", deliverOrder);
			return outSucceed(dataMap, true, LIST_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("分销商查询代理订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 分店代理订单详情
	* @Title: orderDetail
	* @Description:分店订单详情
	* @param jsonObj
	* @return
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月29日 下午5:16:17
	 */
	@RequestMapping(value ="detail")
	@ResponseBody
	public String orderDetail(@RequestBody JSONObject jsonObj) throws Exception {
		try {//检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
		String orderNo = jsonObj.optString("orderId");
		if (StringUtils.isEmpty(orderNo)){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		String shopNo = jsonObj.getString("shopNo");
		if (StringUtils.isEmpty(shopNo)){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		// 查询订单
		SubBranchSalesOrderVo salesOrder = subBranchSaleOrderBusiness.getSubBranchSalesOrderWithPurchaseOrderItemsByPrimaryKey(orderNo,super.getUserId());
		AgencyOrderVo assOrder = new AgencyOrderVo(salesOrder);
		//订单来源
		assOrder.setPurchaserNick(salesOrder.getOrderSource());
		List<AssOrderItem> orderItemList = new ArrayList<AssOrderItem>();
		for (OrderItem orderItem : salesOrder.getOrderItems()) {
			AssOrderItem item = new AssOrderItem(orderItem);
				SalesOrderAppCtl ctl = new SalesOrderAppCtl();
				ctl._getSkuProValue(item);
				if (item != null && item.getGoodId() != null) {
					Good good = this.goodBusiness.getGoodById(item.getGoodId());
					item.setGoodStatus(good != null ? good.getStatus() : null);// 商品状态
					item.setDetailWapUrl(good != null ? good.getDetailWapUrl() : "");// 访问链接
				}
			orderItemList.add(item);
		}
		Long id = Long.valueOf(shopNo);
		Subbranch subbranch = subbranchBusiness.findSubbranchById(id);
		String phone = subbranch.getUserCell();
		authOperation(salesOrder,assOrder,phone);
		assOrder.setOrderItemList(orderItemList);
		return outSucceed(assOrder, true, DETAIL_JSON_FIELDS);
		} catch (OrderException oe) {
			_logger.error("订单不存在", oe);
			return outFailure(AssConstantsUtil.OrderCode.ORDER_NOT_EXIST_CODE, "");
		} catch (Exception e) {
			_logger.error("分销商查询代理订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 修改价格
	* @Title: modifyPrice
	* @Description: (修改价格)
	* @param salesOrder
	* @param jsonObj
	* @return
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月29日 上午11:28:28
	 */
	@RequestMapping(value = "modifyPrice")
	@ResponseBody
	public String modifyPrice(SalesOrder salesOrder,@RequestBody JSONObject jsonObj) throws Exception{
	try {
			if (null == jsonObj || jsonObj.isEmpty()) {
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
		String orderId =jsonObj.getString("orderId");
		if (null == orderId || "".equals(orderId) ){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		Long price = jsonObj.getLong("price");
		subBranchSaleOrderBusiness.modifyOrderPrice(orderId, price, super.getUserId());
			 return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("价格修改失败", e);
			 return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	
	/**
	 * 分店店铺订单列表
	* @Title: list
	* @Description: 分店店铺订单列表
	* @param jsonObj
	* @param pagination
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月29日 下午5:16:46
	 */
	@RequestMapping(value="history/list")
	@ResponseBody
	public String historyList(@RequestBody JSONObject jsonObj,Pagination<SubBranchSalesOrderVo> pagination){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.getString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			
			Object orderStatus = jsonObj.get("orderStatus");
			Integer status  = null ;
			if (null != orderStatus && !"".equals(orderStatus)) {
				Integer temp = Integer.parseInt(orderStatus.toString());
				if (0 != temp.intValue()) {
					status = temp;
				}
			}
			// 客户ID
			Long customerId = null;
			Object customerIdObj = jsonObj.get("customerId");
			if (customerIdObj != null && org.apache.commons.lang.StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.parseLong(customerIdObj.toString());
			}
			Long id = Long.valueOf(shopNo);
			Subbranch subbranch = subbranchBusiness.findSubbranchById(id);
			String phone = subbranch.getUserCell();
			// 判断是都是来自客户详情下店铺列表的请求标志位
			String isShopRequest = null;
			Object isShopRequestObj = jsonObj.get("isShopRequest");
			if (isShopRequestObj != null && org.apache.commons.lang.StringUtils.isNotBlank(isShopRequestObj.toString())) {
				isShopRequest = isShopRequestObj.toString();
			}
			pagination = subBranchSaleOrderBusiness.findHistoryOrderForOwn(id, status, customerId, null, pagination,
					isShopRequest);
			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
			for (SubBranchSalesOrderVo order : pagination.getDatas()) {
				AgencyOrderVo assorder = new AgencyOrderVo(order);
				//组装商品信息
				List<AssOrderItem> assOrderItemList = new ArrayList<AssOrderItem>();
				for (OrderItem orderItem : order.getOrderItems()) {
					AssOrderItem item = new AssOrderItem(orderItem);
					assOrderItemList.add(item);
				}
				authOperation(order,assorder,phone);
				assorder.setOrderItemList(assOrderItemList);
				list.add(assorder);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("orderStatus", status);
			dataMap.put("shopNo", shopNo);
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			String s =  outSucceed(dataMap, true, LIST_JSON_FIELDS);
			return s;
		} catch (Exception e) {
			_logger.error("分销商查询代理订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 分店订单列表
	* @Title: subList
	* @Description:  分店订单列表
	* @param jsonObj
	* @param pagination
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月29日 下午5:16:33
	 */
	@RequestMapping(value="history/subList")
	@ResponseBody
	public String historySubList(@RequestBody JSONObject jsonObj,Pagination<SubBranchSalesOrderVo> pagination){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			Object orderStatus = jsonObj.get("orderStatus");
			Integer status  = null ;
			if (null != orderStatus && !"".equals(orderStatus)) {
				if (null != orderStatus && !"".equals(orderStatus)) {
					Integer temp = Integer.parseInt(orderStatus.toString());
					if (0 != temp.intValue()) {
						status = temp;
					}
				}
			}
			Long id = Long.valueOf(shopNo);
			Subbranch subbranch = subbranchBusiness.findSubbranchById(id);
			String phone = subbranch.getUserCell();
			pagination = subBranchSaleOrderBusiness.findHistoryOrderForSub(id, status, null, pagination);
			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
			for (SubBranchSalesOrderVo order : pagination.getDatas()) {
				AgencyOrderVo assorder = new AgencyOrderVo(order);
				// 佣金字段转换
				assorder.setTotalProfitPrice(order.getPreLeveCommssion());
				
				//组装商品信息
				List<AssOrderItem> assOrderItemList = new ArrayList<AssOrderItem>();
				for (OrderItem orderItem : order.getOrderItems()) {
					AssOrderItem item = new AssOrderItem(orderItem);
					assOrderItemList.add(item);
				}
				authOperation(order,assorder,phone);
				assorder.setOrderItemList(assOrderItemList);
				list.add(assorder);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("orderStatus", status);
			dataMap.put("shopNo", shopNo);
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			return outSucceed(dataMap, true, LIST_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("分销商查询代理订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	
	
	private void authOperation(SubBranchSalesOrderVo order,AgencyOrderVo assOrder,String phone){
		
		if (order.getDeliveryRemoteType() == 2  && phone.equals(order.getDeliveryRemoteId())) {
			assOrder.setIsDeliver(1);
		}else {
			assOrder.setIsDeliver(0);
		}
		/**修改价格**/
		if (order.getSellerRemoteType() == 2  && phone.equals(order.getSellerRemoteId())) {
			assOrder.setIsModifyPrice(1);
		}else {
			assOrder.setIsModifyPrice(0);
		}
	}
 }
