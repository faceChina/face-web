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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AgencyOrderVo;
import com.zjlp.face.web.appvo.AssOrderItem;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.appvo.AssSalesOrderVo;
import com.zjlp.face.web.appvo.ShopOrderVo;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.user.shop.bussiness.AssShopBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.DataUtils;
import com.zjlp.face.web.util.VerificationRegexUtil;
/**
 * 订单管理
* @ClassName: SalesOrderCtl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年4月14日 上午9:41:57
*
 */
@Controller
@RequestMapping({"/assistant/ass/order/"})
public class SalesOrderAppCtl extends BaseCtl {
	
	public static final String[] LIST_JSON_FIELDS = {"shopType","orderStatus","shopNo","curPage","start","pageSize","totalRow",
		"newOrderNumber","payOrderNumber","deliverOrderNumber","receiveOrderNumber",
		"orders.orderNo","orders.status","orders.quantity","orders.totalPrice","orders.totalPriceStr",
		"orders.payPrice","orders.payPriceStr","orders.orderCategory","orders.createTimeStamp","orders.goodPicturePath","orders.orderItemList.goodName","orders.deliveryWay","orders.deliveryRemoteId","orders.deliveryRemoteType","orders.isDeliver",
		"orders.payCommssion","orders.payCommssionStr","orders.orderSource",
		"orders.purchaserNick","orders.isModifyPrice","orders.orderItemList.price","orders.orderItemList.priceStr",
		"orders.orderItemList.quantity","orders.orderItemList.skuPicturePath","orders.orderItemList.skuPropertiesName"};
	
	 public static final String[] DETAIL_JSON_FIELDS = {"orderNo","status","createTimeStamp","buyerMessage",
		 "totalPrice","totalPriceStr","payPrice","payPriceStr","price","priceStr","purchaserNick","refuseReason","payCommssion","payCommssionStr",
				"sellerMemo","deliveryCompany","deliverySn","senderName","senderCell","deliveryWay","orderCategory","isDeliver","isModifyPrice",
				"adjustPrice","adjustPriceStr","integral","integralStr","discountPrice","couponPriceStr","promoteSpend","promoteSpendStr","postFee","postFeeStr","totalIncome","totalIncomeStr","totalProfitPrice","totalProfitPriceStr",
				"receiverName","receiverPhone","receiverAddressInfo","orderItemList.skuPicturePath","orderItemList.goodName",
				"orderItemList.price","orderItemList.priceStr","orderItemList.quantity","orderItemList.goodStatus", "orderItemList.detailWapUrl","orderItemList.skuPropertiesName"};
	 
	 public static final String[] APPOINMENT_ORDER_FIELDS = {"curPage","start","pageSize","totalRow", "datas.orderNo", "datas.status", "datas.quantity", "datas.totalPriceStr",
		  "datas.title", "datas.appointmentTime", "datas.itemList.skuPicturePath", "datas.goodPicturePath", "waitForOperateCount"};
	 
	 public static final String[] APPOINTMENT_ORDER_DETAIL_FIELDS = {"appointmentTime", "totalPrice","title", "totalPriceStr", "status", "picturePath", "orderItemList.goodName", "orderItemList.discountPrice","orderItemList.discountPriceStr", "orderItemList.quantity"
		 ,"orderItemList.skuPicturePath" ,"receiverName", "receiverPhone", "orderAppointmentDetails.attrName", "orderAppointmentDetails.attrValue", "buyerMessage", "refuseTime", "confirmTime", "cancelTime"};
	 
	public static final String[] CUSTOMER_ORDER_FIELDS = { "curPage", "start", "pageSize", "totalRow", "datas.orderNo",
			"datas.status", "datas.title", "datas.goodPicturePath", "datas.payPriceStr", "datas.quantity", "datas.orderTimeStamp" };

	public static final String[] CUSTOMER_ORDER_IN_SHOP_FIELDS = { "curPage", "start", "pageSize", "totalRow", "orders.orderNo",
			"orders.status", "orders.goodName", "orders.goodPicturePath", "orders.totalPriceStr", "orders.quantity",
			"orders.createTimeStamp" };

	public static final String COLON = ":";

	public static final String COMMA = ",";

	public static final String SEMICOLON = ";";

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private ShopBusiness shopBusiness;
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private SubBranchSaleOrderBusiness subBranchSaleOrderBusiness;
	@Autowired
	private GoodBusiness goodBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private AssShopBusiness assShopBusiness;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	

	/**
	 * 官网 && 商城 :订单列表
	* @Title: order 
	* @Description: 官网 && 商城 ：店铺订单列表
	* @param type
	* @param salesOrderDto
	* @param pagination
	* @param request
	* @param response
	* @throws Exception
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月19日 上午11:18:31
	 */
	@Deprecated
	@RequestMapping(value = "list/{type}", method = RequestMethod.POST)
	@ResponseBody
	public String oldOrderList(@PathVariable Integer type,@RequestBody JSONObject jsonObj,
			SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo) ){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			salesOrderVo.setShopNo(shopNo);
			
			Integer orderStatus = jsonObj.optInt("orderStatus");
			if (!StringUtils.isEmpty(orderStatus)){
				salesOrderVo.setStatus(orderStatus);
			}
			Object userId = jsonObj.get("customerId");
			if (userId != null&& !"".equals(userId)) {
				salesOrderVo.setUserId(Long.valueOf(userId.toString()));
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);

			// 查询订单列表
			pagination = salesOrderBusiness.findSalesOrderDetailPage(pagination, salesOrderVo);

			List<ShopOrderVo> list =  new ArrayList<ShopOrderVo>();
			
			for (SalesOrderDto order : pagination.getDatas()) {
				ShopOrderVo assorder = new ShopOrderVo(order);
				//组装商品信息
				List<AssOrderItem> assOrderItemList = new ArrayList<AssOrderItem>();
				for (OrderItem orderItem : order.getOrderItemList()) {
					AssOrderItem item = new AssOrderItem(orderItem);
					assOrderItemList.add(item);
				}
				assorder.setOrderItemList(assOrderItemList);
				list.add(assorder);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("shopType", type);
			dataMap.put("orderStatus", salesOrderVo.getStatus());
			dataMap.put("shopNo", salesOrderVo.getShopNo());
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			 return outSucceed(dataMap, true, LIST_JSON_FIELDS);
		} catch (Exception e) {
			log.error("订单列表", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 官网 && 商城 :订单列表
	* @Title: order
	* @Description: 官网 && 商城 ：店铺订单列表
	* @param type
	* @param salesOrderDto
	* @param pagination
	* @param request
	* @param response
	* @throws Exception
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月19日 上午11:18:31
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public String orderList(@RequestBody JSONObject jsonObj,
			SalesOrder salesOrder, Pagination<SubBranchSalesOrderVo> pagination) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo) ){
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			salesOrder.setShopNo(shopNo);
			
			Object orderStatus = jsonObj.get("orderStatus");
			if (null != orderStatus && !"".equals(orderStatus)){
				Integer status = Integer.parseInt(orderStatus.toString());
				if (0 != status.intValue()) {
					salesOrder.setStatus(status);
				}
			}
			Object userId = jsonObj.get("customerId");
			if (userId != null&& !"".equals(userId)) {
				salesOrder.setUserId(Long.valueOf(userId.toString()));
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			Object curPageObj = jsonObj.get("curPage");
			if (null != curPageObj && !"".equals(curPageObj)) {
//				根据start重新计算curPage保证从1开始,因为客户端传的curPage可能等于0
				DataUtils.resetCurPage(pagination,jsonObj);
			}
			String searchKey = jsonObj.optString("searchKey");
			if (!StringUtils.isEmpty(searchKey)){
				salesOrder.setSearchKey(searchKey.toUpperCase());
			}
			// 增加客户ID来过滤客户订单
			Long customerId = null;
			Object customerIdObj = jsonObj.get("customerId");
			if (null != customerIdObj && org.apache.commons.lang3.StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.parseLong(customerIdObj.toString());
			}
			String isShopRequest = null;
			Object isShopRequestObj = jsonObj.get("isShopRequest");
			if (isShopRequestObj != null && org.apache.commons.lang.StringUtils.isNotBlank(isShopRequestObj.toString())) {
				isShopRequest = isShopRequestObj.toString();
			}
			// 查询订单列表
			pagination = subBranchSaleOrderBusiness.findAllMySalesOrderByShopNo(salesOrder, null, pagination, customerId, isShopRequest);
			
			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
			for (SubBranchSalesOrderVo order : pagination.getDatas()) {
				AgencyOrderVo assorder = new AgencyOrderVo(order);
				//组装商品信息
				List<AssOrderItem> assOrderItemList = new ArrayList<AssOrderItem>();
				for (OrderItem orderItem : order.getOrderItems()) {
					AssOrderItem item = new AssOrderItem(orderItem);
					this._getSkuProValue(item);
					assOrderItemList.add(item);
				}
				authOperation(order,assorder,shopNo);
				assorder.setOrderItemList(assOrderItemList);
				list.add(assorder);
			}
			//统计订单信息
			Integer newOrder = 0;
			Integer payOrder = 0;
			Integer deliverOrder = 0;
			Integer receiveOrder= 0;
			
			newOrder = operateDataBusiness.countMyShopOrderNumber(shopNo, AssConstantsUtil.ORDER_STATE_1);
			payOrder = operateDataBusiness.countMyShopOrderNumber(shopNo, AssConstantsUtil.ORDER_STATE_2);
			deliverOrder = operateDataBusiness.countMyShopOrderNumber(shopNo, AssConstantsUtil.ORDER_STATE_3);
			receiveOrder = operateDataBusiness.countMyShopOrderNumber(shopNo, AssConstantsUtil.ORDER_STATE_4);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("orderStatus", salesOrder.getStatus());
			dataMap.put("shopNo", salesOrder.getShopNo());
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			dataMap.put("newOrderNumber", newOrder);
			dataMap.put("payOrderNumber", payOrder);
			dataMap.put("deliverOrderNumber", deliverOrder);
			dataMap.put("receiveOrderNumber", receiveOrder);
			 return outSucceed(dataMap, true, LIST_JSON_FIELDS);
		} catch (Exception e) {
			log.error("订单列表", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	

	/**
	 * @Title: _getSkuProValue
	 * @Description: (取sku属性值)
	 * @param item
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月4日 上午9:37:49
	 */
	public void _getSkuProValue(AssOrderItem item) {
		if (item != null) {
			if (org.apache.commons.lang.StringUtils.isNotBlank(item.getSkuPropertiesName())) {
				StringBuilder builder = new StringBuilder();
				if (!item.getSkuPropertiesName().contains(SEMICOLON)) {// ;
					return;
				}
				String skuArray[] = item.getSkuPropertiesName().split(SEMICOLON);// ;
				for (String array : skuArray) {
					if (!array.contains(COLON)) {
						return;
					}
					String proArray[] = array.split(COLON);// :
					if (proArray != null && proArray.length == 2) {
						builder.append(proArray[1]).append(COMMA);// ,
					}
				}
				String result = builder.toString();
				if (org.apache.commons.lang3.StringUtils.isNotBlank(result)) {
					item.setSkuPropertiesName(result.substring(0, result.length() - 1));
				}
			} else {
				item.setSkuPropertiesName(org.apache.commons.lang.StringUtils.EMPTY);// 避免转json后出现"null"
			}
		}
	}

	/**
	 * 取消订单
	 * 
	 * @Title: cancelOrder
	 * @Description: 取消订单
	 * @param salesOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author wxn
	 * @date 2014年12月19日 上午11:22:36
	 */
	@RequestMapping(value = "cancel")
	@ResponseBody
	public String cancelOrder(SalesOrder salesOrder,@RequestBody JSONObject jsonObj) throws Exception{
	try {
			if (null == jsonObj || jsonObj.isEmpty()) {
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
		String orderNo = jsonObj.optString("orderId");
		if (null == orderNo || "".equals(orderNo) ){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		String refuseReason = jsonObj.optString("refuseReason");
		/*String shopNo = jsonObj.optString("shopNo");
		if (null == shopNo || "".equals(shopNo) ){
			return outFailure(AssConstantsUtil.ORDERID_ERROR_CODE, "");
		}*/
		//salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
		subBranchSaleOrderBusiness.cancleOrder(orderNo, refuseReason, super.getUserId());
		return outSucceedByNoData();
		} catch (Exception e) {
			log.error("取消订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 发货
	* @Title: delivery
	* @Description: 发货
	* @param orderItem
	* @param request
	* @param response
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月19日 上午11:23:07
	 */
	@RequestMapping(value = "deliver")
	@ResponseBody
	public String delivery(SalesOrder salesOrder,@RequestBody JSONObject jsonObj) throws Exception{
	try {
			if (null == jsonObj || jsonObj.isEmpty()) {
			return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
		}
		String orderId =jsonObj.optString("orderId");
		if (null == orderId || "".equals(orderId) ){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		//String shopNo = jsonObj.optString("shopNo");
		String deliveryCompany = jsonObj.optString("deliveryCompany");
		String deliverySn = jsonObj.optString("deliverySn");
		String senderName = jsonObj.optString("senderName");
		String senderCell = jsonObj.optString("senderCell");
		if (org.apache.commons.lang.StringUtils.isNotBlank(deliverySn)) {
			deliverySn = deliverySn.replaceAll("\\s*", "");
		}
		//如果配送方式是店铺配送或者送货上门，则不需要快递单号
		SubBranchSalesOrderVo salesOrderExist = subBranchSaleOrderBusiness.getSupplierSubBranchSalesOrderByOrderNo(orderId);
		if ((salesOrderExist != null && salesOrderExist.getDeliveryWay() != 2 && salesOrderExist.getDeliveryWay() != 3) && (org.apache.commons.lang.StringUtils.isBlank(deliverySn) || deliverySn.length() > 32 || !deliverySn.matches("^[a-zA-Z0-9]+$"))) {
				return outFailure(AssConstantsUtil.OrderCode.FORMAT_LOGISTICS_ERROR_CODE, "");
		}
		if(!"".equals(senderCell)){
				if (!VerificationRegexUtil.newPhoneVer(senderCell)) {
				// 输入的手机号码不正确
				return outFailure(AssConstantsUtil.OrderCode.FORMAT_PHOEN_ERROR_CODE, "");
			}
		}
			salesOrder.setOrderNo(orderId);
			salesOrder.setSenderName(senderName);
			salesOrder.setSenderCell(senderCell);
			salesOrder.setDeliveryCompany(deliveryCompany);
			salesOrder.setDeliverySn(deliverySn);
			//salesOrderBusiness.validateOrderByShopNo(salesOrder.getOrderNo(), shopNo);
			subBranchSaleOrderBusiness.deliveryOrder(salesOrder, super.getUserId());
			 return outSucceedByNoData();
		} catch (Exception e) {
			log.error("发货失败", e);
			 return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 订单详情
	* @Title: orderDetail
	* @Description: 订单详情 
	* @param request
	* @param response
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月19日 下午1:04:17
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	public String orderDetail(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String orderNo = jsonObj.optString("orderId");
			if (StringUtils.isEmpty(orderNo)) {
				return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
			}

			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
			}
			// salesOrderBusiness.validateOrderByShopNo(orderNo, shopNo);
			// 查询订单
			SubBranchSalesOrderVo salesOrder = subBranchSaleOrderBusiness.getSupplierSubBranchSalesOrderByOrderNo(orderNo);
			AgencyOrderVo assOrder = new AgencyOrderVo(salesOrder);
			List<AssOrderItem> orderItemList = new ArrayList<AssOrderItem>();
			for (OrderItem orderItem : salesOrder.getOrderItems()) {
				AssOrderItem item = new AssOrderItem(orderItem);
				
				this._getSkuProValue(item);
				if (item != null && item.getGoodId() != null) {
					Good good = this.goodBusiness.getGoodById(item.getGoodId());
					item.setGoodStatus(good != null ? good.getStatus() : null);// 商品状态
					item.setDetailWapUrl(good != null ? good.getDetailWapUrl() : "");// 访问链接
				}
				orderItemList.add(item);
			}
			authOperation(salesOrder, assOrder, shopNo);
			assOrder.setOrderItemList(orderItemList);
			return outSucceed(assOrder, true, DETAIL_JSON_FIELDS);
		} catch (OrderException oe) {
			log.error("订单不存在", oe);
			return outFailure(AssConstantsUtil.OrderCode.ORDER_NOT_EXIST_CODE, "");
		} catch (Exception e) {
			log.error("查询订单详情失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 修改价格
	* @Title: modifyPrice
	* @Description: TODO(这里用一句话描述这个方法的作用)
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
		String orderIdCopy = null;
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
		String orderId =jsonObj.optString("orderId");
			orderIdCopy = orderId;
		if (null == orderId || "".equals(orderId) ){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		Long price = jsonObj.getLong("price");
		salesOrderBusiness.adjustOrderPrice(orderId, super.getUserId(),price);
			 return outSucceedByNoData();
		} catch (Exception e) {
			SalesOrder order = this.salesOrderBusiness.getSalesOrderByOrderNo(orderIdCopy);
			if (!Constants.STATUS_WAIT.equals(order.getStatus()) || null != order.getPayStatus()) {
				orderIdCopy = null;
				return outFailure(AssConstantsUtil.OrderCode.MODIFY_PRICE_DENIE_CODE, "");
			}
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: modifyPriceNew 
	 * @Description: 新的改价接口
	 * @param salesOrder
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @date 2015年10月16日 下午7:41:30  
	 * @author cbc
	 */
	@RequestMapping(value = "modifyPriceNew")
	@ResponseBody
	public String modifyPriceNew(SalesOrder salesOrder,@RequestBody JSONObject jsonObj) throws Exception{
		String orderIdCopy = null;
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
		String orderId =jsonObj.optString("orderId");
			orderIdCopy = orderId;
		if (null == orderId || "".equals(orderId) ){
			return outFailure(AssConstantsUtil.OrderCode.ORDERID_ERROR_CODE, "");
		}
		Long price = jsonObj.getLong("price");
		salesOrderBusiness.adjustOrderPriceNew(orderId, super.getUserId(),price);
			 return outSucceedByNoData();
		} catch (Exception e) {
			SalesOrder order = this.salesOrderBusiness.getSalesOrderByOrderNo(orderIdCopy);
			if (!Constants.STATUS_WAIT.equals(order.getStatus()) || null != order.getPayStatus()) {
				orderIdCopy = null;
				return outFailure(AssConstantsUtil.OrderCode.MODIFY_PRICE_DENIE_CODE, "");
			}
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	private void authOperation(SubBranchSalesOrderVo order,AgencyOrderVo assorder,String shopNo){
		
		if (order.getDeliveryRemoteType() == 0 ||(order.getDeliveryRemoteType() == 1 && shopNo.equals(order.getDeliveryRemoteId()))){
			assorder.setIsDeliver(1);
		}else {
			assorder.setIsDeliver(0);
		}
		/**修改价格**/
		if (order.getSellerRemoteType() == 0 ||(order.getSellerRemoteType() == 1 && shopNo.equals(order.getSellerRemoteId()))) {
			assorder.setIsModifyPrice(1);
		}else {
			assorder.setIsModifyPrice(0);
		}
	}
	
	//===================================预约订单========================================
	/**
	 * 分页查询预约订单<br>
	 * 角色:总店<br>
	 * 
	 * @Title: appointmentOrder 
	 * @Description: 分页查询预约订单
	 * @param jsonObj
	 * @param pagination
	 * @param status
	 * @return
	 * @throws Exception
	 * @date 2015年7月27日 下午5:23:35  
	 * @author cbc
	 */
	@RequestMapping(value = "appointment/list")
	@ResponseBody
	public String appointmentOrder(@RequestBody JSONObject jsonObj, Pagination<SalesOrderDto> pagination) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.AppoinmentOrderCode.NULL_PARAM_SHOPNO, "");
			}
			Object object = jsonObj.get("status");
			Integer status = null;
			if (null != object && !"".equals(object)) {
				status = Integer.parseInt(object.toString());
			}
			//组装查询参数
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setShopNo(shopNo);
			salesOrderVo.setOrderCategory(3);
			salesOrderVo.setStatus(status);
			//组装分页信息
			pagination = super.initPagination(jsonObj);
			pagination = salesOrderBusiness.findSalesOrderDetailPage(pagination, salesOrderVo);
			Integer waitForOperate = salesOrderBusiness.countBookOrder(shopNo, Constants.BOOKORDER_STATUS_WAIT);
			
			List<AssSalesOrderVo> list = new ArrayList<AssSalesOrderVo>();
			for (SalesOrderDto order : pagination.getDatas()) {
				AssSalesOrderVo assorder = new AssSalesOrderVo(order);
				list.add(assorder);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", pagination.getEnd());
			map.put("pageSize", pagination.getPageSize());
			map.put("curPage", pagination.getCurPage());
			map.put("datas", list);
			map.put("totalRow", pagination.getTotalRow());
			map.put("waitForOperateCount", waitForOperate);
			return outSucceed(map, true, APPOINMENT_ORDER_FIELDS);
		} catch (Exception e) {
			log.error("查询预约订单列表失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: appointmentOrderDetail 
	 * @Description: 预约订单详细信息
	 * @param jsonObj
	 * @return
	 * @date 2015年7月27日 下午6:09:27  
	 * @author cbc
	 */
	@RequestMapping(value = "appointment/detail")
	@ResponseBody
	public String appointmentOrderDetail(@RequestBody JSONObject jsonObj) {
		try {
			String orderNo = jsonObj.optString("orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				return outFailure(AssConstantsUtil.AppoinmentOrderCode.NULL_PARAM_SHOPNO, "");
			}
			SalesOrderDto orderDto = salesOrderBusiness.getSalesOrderDetailByOrderNo(orderNo);
			if (null == orderDto) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			List<OrderAppointmentDetail> orderAppointmentDetails = orderDto.getOrderAppointmentDetails();
			if (null != orderAppointmentDetails && !orderAppointmentDetails.isEmpty()) {
				OrderAppointmentDetail detail = orderAppointmentDetails.get(0);
				detail.setAttrValue(detail.getAttrValue().replace(",", " "));
			}
			return outSucceed(orderDto, true, APPOINTMENT_ORDER_DETAIL_FIELDS);
		} catch (Exception e) {
			log.error("查询预约订单详情失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: comfirmBookOrder 
	 * @Description: 确认预约订单
	 * @param jsonObj
	 * @return
	 * @date 2015年8月18日 上午9:42:38  
	 * @author cbc
	 */
	@RequestMapping(value = "appointment/confirm")
	@ResponseBody
	public String comfirmBookOrder(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String orderNo = jsonObj.optString("orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				return outFailure(AssConstantsUtil.AppoinmentOrderCode.NULL_PARAM_ORDERNO, "");
			}
			Long userId = getUserId();
			salesOrderBusiness.updateOrderStatus(orderNo, userId, 41, null);
			return outSucceedByNoData();
		} catch (OrderException e) {
			log.error("确认预约订单详情失败，库存不足", e);
			return outFailure(AssConstantsUtil.OrderCode.NOT_ENOUGH_GOOD_CODE);
		} catch (Exception e) {
			String message = e.getMessage();
			if ("商品库存不足，扣取库存失败！".equals(message)) {
				return outFailure(AssConstantsUtil.OrderCode.NOT_ENOUGH_GOOD_CODE);
			}
			log.error("确认预约订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} 
	}
	
	/**
	 * 
	 * @Title: refuseBookOrder 
	 * @Description: 拒绝预约订单
	 * @param jsonObj
	 * @return
	 * @date 2015年8月18日 上午9:42:38  
	 * @author cbc
	 */
	@RequestMapping(value = "appointment/refuse")
	@ResponseBody
	public String refuseBookOrder(@RequestBody JSONObject jsonObj) {
		try {
			String orderNo = jsonObj.optString("orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				return outFailure(AssConstantsUtil.AppoinmentOrderCode.NULL_PARAM_SHOPNO, "");
			}
			String memo = jsonObj.optString("memo");
			Long userId = getUserId();
			salesOrderBusiness.updateOrderStatus(orderNo, userId, 42, memo);
			return outSucceedByNoData();
		} catch (Exception e) {
			log.error("拒绝预约订单失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * @Title: customerOrderList
	 * @Description: (查询客户订单列表)
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月29日 上午10:34:40
	 */
	@RequestMapping(value = "customerOrderList")
	@ResponseBody
	public String customerOrderList(@RequestBody JSONObject jsonObj, Pagination<SalesOrderDto> pagination) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 组装分页信息
			pagination = super.initPagination(jsonObj);
			Long customerId = null;
			Object customerIdObj = jsonObj.get("customerId");
			if (customerIdObj != null && org.apache.commons.lang.StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.valueOf(customerIdObj.toString());
			}
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setUserId(customerId);
			pagination = salesOrderBusiness.findOrderPageForWap(salesOrderVo, pagination);
			List<AssSalesOrderVo> list = new ArrayList<AssSalesOrderVo>();
			for (SalesOrderDto order : pagination.getDatas()) {
				AssSalesOrderVo assorder = new AssSalesOrderVo(order);
				list.add(assorder);
			}
			AssPagination<AssSalesOrderVo> newpag = new AssPagination<AssSalesOrderVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(list);
			return outSucceed(newpag, true, CUSTOMER_ORDER_FIELDS);
		} catch (Exception e) {
			log.error("查找客户订单改失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: shopOrderList
	 * @Description: (查询某个客户在我的总店的订单列表(不包括下级分店))
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午3:55:22
	 */
	@RequestMapping(value = "shopOrderList", method = RequestMethod.POST)
	@ResponseBody
	public String shopOrderList(@RequestBody JSONObject jsonObj, Pagination<SalesOrder> pagination) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.ShopCode.NULL_SHOP_NO_CODE, "");
			}
			Long customerId = null;
			Object customerIdObj = jsonObj.get("customerId");
			if (null != customerIdObj && org.apache.commons.lang3.StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.parseLong(customerIdObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_COSTOMER_ID_CODE);
			}
			// 查询订单列表
			pagination = this.salesOrderBusiness.findCustomerOrderInMyShop(shopNo, customerId, pagination);
			List<AssSalesOrderVo> list = new ArrayList<AssSalesOrderVo>();
			for (SalesOrder current : pagination.getDatas()) {
				current.setOrderTime(current.getCreateTime());
				current.setTotalPrice(current.getPayPrice());
				AssSalesOrderVo vo = new AssSalesOrderVo(current);
				vo.setPayPriceStr(CalculateUtils.converFenToYuan(vo.getPayPrice()));
				list.add(vo);
			}
			// 重新封装Pagination对象
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("curPage", pagination.getCurPage());
			dataMap.put("start", pagination.getEnd());
			dataMap.put("pageSize", pagination.getPageSize());
			dataMap.put("totalRow", pagination.getTotalRow());
			dataMap.put("orders", list);
			return outSucceed(dataMap, true, CUSTOMER_ORDER_IN_SHOP_FIELDS);
		} catch (Exception e) {
			log.error("查询客户在总店的订单列表失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

}
