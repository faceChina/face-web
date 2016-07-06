package com.zjlp.face.web.server.trade.order.bussiness.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.metaq.producer.OrderJobMetaOperateProducer;
import com.zjlp.face.web.component.metaq.producer.OrderMetaOperateProducer;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.component.sms.Sms.SwitchType;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
import com.zjlp.face.web.server.trade.order.calculate.CalculateOrder;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.trade.order.service.OrderItemService;
import com.zjlp.face.web.server.trade.order.service.OrderRecordService;
import com.zjlp.face.web.server.trade.order.service.PurchaseOrderService;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.util.NumberConversion;

@Service("subBranchSaleOrderBusiness")
public class SubBranchSaleOrderBusinessImpl implements
		SubBranchSaleOrderBusiness {
	private Logger _log = Logger.getLogger(getClass());
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private OrderRecordService orderRecordService;
//	@Autowired
//	private OrderJobManager orderJobManager;
	@Autowired
	private OrderJobMetaOperateProducer orderJobMetaOperateProducer;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderMetaOperateProducer orderMetaOperateProducer;
	@Autowired
	private UserProducer userProducer;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private SubbranchService subbranchService;
	
	/**
	 * 卖家类型为店铺
	 */
	private static Integer  REMOTETYPE_SHOPNO = 1;
	/**
	 * 卖家类型为手机
	 */
	private static Integer  REMOTETYPE_PHONE = 2;
	
	private void sendTradePush(String orderNo, Long deliveryUserId) {
		// 获取订单中一个商品名称用于显示
		List<OrderItem> orderItem = this.salesOrderService.getOrderItemListByOrderNo(orderNo);
		String productName = null;
		if (!orderItem.isEmpty()) {
			productName = orderItem.get(0).getGoodName();
		}
		// PUSH
		try {
			orderMetaOperateProducer.senderAnsyToAll(orderNo, productName, deliveryUserId);
		} catch (Exception e) {
			_log.error("订单生成MetaQ推送异常！", e);
		}
	}
	/*******************************
	 * @Title setOrderSource
	 * @Description (添加订单来源)
	 * @param subBranchSalesOrderVo
	 * @return
	 * @Return void
	 * @author Xilei Huang
	 *******************************/
	private void setOrderSource(SubBranchSalesOrderVo subBranchSalesOrderVo){
		if (subBranchSalesOrderVo == null) {
			return;
		}
		String orderSource=subBranchSalesOrderVo.getOrderSource();
		if (!StringUtils.isEmpty(orderSource)) {
			subBranchSalesOrderVo.setPurchaserNick(orderSource);
			return;
		}
		try {
			orderSource=purchaseOrderService.getOrderSourceByPrimaryKey(subBranchSalesOrderVo.getPurchaseID());
			subBranchSalesOrderVo.setOrderSource(orderSource);
			subBranchSalesOrderVo.setPurchaserNick(orderSource);//冗余来源数据
		} catch (Exception e) {
			_log.error("订单来源查找失败",e);
		}
		
	}
	
	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForOwn(Long subbranchId, Integer status,
			String orderBy, Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest)
			throws OrderException {
		try {
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination = salesOrderService.findOwnSalesOrderPageBySubbranchId(subbranchId, status, orderBy, pagination, userId,
					isShopRequest);
			List<SubBranchSalesOrderVo> lists = pagination.getDatas();
			if (lists == null || lists.size() == 0) {
				return pagination;
			}
			for (SubBranchSalesOrderVo salesOrder : lists) {
				List<OrderItem> orderItems = orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
				setOrderSource(salesOrder);
			}
			return pagination;
		} catch (Exception e) {
			_log.error("通过手机查找本店铺订单失败");
			throw new OrderException(e);
		}
		
	}
	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForOwn(Long subbranchId, Integer status,
			String orderBy, String searchKey, Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest)
			throws OrderException {
		try{
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination=salesOrderService.findOwnSalesOrderPageBySubbranchId(subbranchId, status, orderBy,searchKey, pagination,userId,isShopRequest);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
			}
			return pagination;
		}catch(Exception e){
			_log.error("通过手机查找本店铺订单失败");
			throw new OrderException(e);
		}
		
	}
	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageByShopNoAndStatusForOwn (
			String shopNo, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination)throws OrderException {
		try{
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination= salesOrderService.findOwnSalesOrderPageByShopNo(shopNo, status, orderBy, pagination);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
				salesOrder.setOrderSource(salesOrder.getPurchaserNick());
			}
			return pagination;
			
		}catch(Exception e){
			_log.error("通过店铺编号查找本店铺订单失败");
			throw new OrderException(e);
		}
		
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageByShopNoAndStatusForSub(
			String shopNo, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) throws OrderException{
		try{
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination = salesOrderService.findSubBranchSalesOrderPageByShopNo(shopNo, status, orderBy, pagination);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
				salesOrder.setOrderSource(salesOrder.getPurchaserNick());
			}
			return pagination;
		}catch(Exception e){
			_log.error("通过店铺编号查找分店订单失败");
			throw new OrderException(e);
		}
		
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForSub(Long subbranchId, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) throws OrderException {
		try{
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination = salesOrderService.findSubBranchSalesOrderPageBySubbranchId(subbranchId, status, orderBy, pagination);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
				setOrderSource(salesOrder);
			}
			return pagination;
		}catch(Exception e){
			_log.error("通过手机查找分店订单失败");
			throw new OrderException(e);
		}
	}
	@Override
	public Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForSub(Long subbranchId, Integer status, String orderBy,String searchKey,
			Pagination<SubBranchSalesOrderVo> pagination) throws OrderException {
		try{
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination = salesOrderService.findSubBranchSalesOrderPageBySubbranchId(subbranchId, status, orderBy,searchKey ,pagination);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
				setOrderSource(salesOrder);
			}
			return pagination;
		}catch(Exception e){
			_log.error("通过手机查找分店订单失败");
			throw new OrderException(e);
		}
	}
	/*******************************
	 * @Title validation
	 * @Description (验证用户是否有权限)
	 * @param order
	 * @param userId
	 * @param type 0.发货验证，1改价验证
	 * @return
	 * @Return Boolean
	 * @date 2015年6月26日
	 * @author Xilei Huang
	 *******************************/
	private Boolean validation(SalesOrder order,Long userId,int type){
		Integer remoteType=null;
		String shopNo= null;
		if(type == 0){
			//发货对应的id
			remoteType=order.getDeliveryRemoteType();
			shopNo=order.getDeliveryRemoteId();
		}else if(type == 1){
			//直接出售对应的id
			remoteType=order.getSellerRemoteType();
			shopNo=order.getSellerRemoteId();
		}else{
			return false;
		}
		if(REMOTETYPE_SHOPNO.equals(remoteType)){
			Shop shop = shopProducer.getShopByNo(shopNo);
			if (null != shop && shop.getUserId().equals(userId)) {
				return true;
			}
		}else if(REMOTETYPE_PHONE.equals(remoteType)){
			User user = userProducer.getUserById(userId);
			if(null != user && shopNo.equals(user.getCell())){
				return true;
			}
		}
		return false;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void deliveryOrder(SalesOrder salesOrder, Long userId)
			throws OrderException {
		try {
			AssertUtil.notNull(salesOrder, "Param[salesOrder] can not be null.");
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(salesOrder.getOrderNo(), "Param[orderNo] can not be null.");
			SalesOrder order=salesOrderService.getSalesOrderByOrderNo(salesOrder.getOrderNo());
			AssertUtil.notNull(order,"发货订单不存在");
			AssertUtil.notNull(order.getOrderNo(),"发货订单不存在");
			AssertUtil.isTrue(validation(order,userId,0),"警告：用户id："+userId+"试图非法发货，订单号："+salesOrder.getOrderNo());
			SalesOrder edit = new SalesOrder();
			edit.setOrderNo(salesOrder.getOrderNo());
			edit.setSenderName(salesOrder.getSenderName());
			edit.setSenderCell(salesOrder.getSenderCell());
			edit.setDeliveryCompany(salesOrder.getDeliveryCompany());
			edit.setDeliverySn(salesOrder.getDeliverySn());
			Date date = new Date();
			edit.setUpdateTime(date);
			salesOrderService.editSalesOrder(edit);
			salesOrderService.editSalesOrderStatus(salesOrder.getOrderNo(), Constants.STATUS_SEND);
			orderRecordService.deliveryOrderRecord(salesOrder.getOrderNo(), userId);
			String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
//			Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, SmsContent.UMS_211, 
//					order.getReceiverPhone(),order.getOrderNo(),wgjurl+"/wap/"+order.getShopNo()+"/buy/personal/index.htm");
			String no = NumberConversion.setShopNo(order.getShopNo());
			Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, SmsContent.UMS_211, order.getReceiverPhone(),order.getOrderNo(),wgjurl+"/uc/"+no+".htm");
			try{
//				orderJobManager.compileOrder(salesOrder.getOrderNo());
				orderJobMetaOperateProducer.compileOrder(salesOrder.getOrderNo());
			}catch(Exception e){
				_log.error("订单自动收货任务埋点失败! 订单号:" + salesOrder.getOrderNo());
				throw new OrderException("订单自动收货任务埋点失败! 订单号:" + salesOrder.getOrderNo());
			}
			
		} catch (Exception e) {
			_log.error("发货失败，订单号："+salesOrder.getOrderNo());
			throw new OrderException(e);
		}
		sendTradePush(salesOrder.getOrderNo(), userId);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void cancleOrder(String orderNo, String refuseReason,Long userId) throws OrderException {
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			AssertUtil.notNull(refuseReason, "param[refuseReason] can't be null.");
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			SalesOrder order=salesOrderService.getSalesOrderByOrderNo(orderNo);
			AssertUtil.isTrue(validation(order,userId,0),"警告：用户id："+userId+"试图非法取消，订单号："+order.getOrderNo());
			salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_CANCEL);
			salesOrderService.saveBookOrderRefuseReason(orderNo,refuseReason);
		} catch (Exception e) {
			_log.error("订单取消失败，订单号："+orderNo);
			throw new OrderException(e);
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void modifyOrderPrice(String orderNo, Long benefitPrice,
			Long userId) throws OrderException {
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			AssertUtil.notNull(benefitPrice, "param[newPrice] can't be null.");
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			AssertUtil.isTrue(benefitPrice<=0, "不支持加价格");
			SubBranchSalesOrderVo order=this.getSubBranchSalesOrderWithPurchaseOrderItemsByPrimaryKey(orderNo,userId);
			//断言让利价格不高于商户所得佣金
			AssertUtil.notTrue(order.getTotalProfitPrice().longValue() < Math.abs(benefitPrice.longValue()), "让利价格不可多于佣金");
			AssertUtil.notNull(order,"订单不存在");
			AssertUtil.notNull(order.getOrderNo(),"订单不存在");
			AssertUtil.isTrue(validation(order,userId,1),"警告：用户id："+userId+"试图非法修改价格，订单号："+order.getOrderNo());
			//计算新的总价格
			CalculateOrder calculateOrder = new CalculateOrder(order.getPrice(),order.getPostFee(),order.getDiscountPrice(),benefitPrice);
			SalesOrder edit = new SalesOrder();
			edit.setOrderNo(orderNo);
			edit.setAdjustPrice(benefitPrice);
			edit.setTotalPrice(calculateOrder.calculate());
			edit.setUpdateTime(new Date());
			//获取最低价格
			String orderMinPrice=PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE");
			//断言修改后的价格不低于最低价格
			AssertUtil.notTrue(edit.getTotalPrice() < Long.valueOf(orderMinPrice),"调整后订单总价最小值"+orderMinPrice);
			salesOrderService.editSalesOrder(edit);
			orderRecordService.adjustPriceRecord(orderNo,1,edit.getTotalPrice(), benefitPrice, userId);
		} catch (Exception e) {
			_log.error("价格修改失败");
			throw new OrderException(e);
		}
	}
	@Override
	public SubBranchSalesOrderVo getSubBranchSalesOrderWithPurchaseOrderItemsByPrimaryKey(String orderNo, Long userId) throws OrderException {
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			AssertUtil.notNull(userId, "param[userId] can't be null.");
//			List<Subbranch> subbranchs=subbranchBusiness.findSubbranchByUserId(userId);
//			AssertUtil.notNull(subbranchs, "分销商不存在");
//			AssertUtil.isTrue(subbranchs.size()>0, "分销商不存在");
//			String phone = subbranchs.get(0).getUserCell();
			User user = userProducer.getUserById(userId);
			AssertUtil.notNull(user, "分销商不存在");
			SubBranchSalesOrderVo salesOrder=this.getOrderDetail(orderNo, user.getLoginAccount());
			return salesOrder;
		}catch(Exception e){
			_log.error("分销订单获取失败");
			throw new OrderException(e);
		}
	}
	
	@Override
	public SubBranchSalesOrderVo getSubOrderDetail(String orderNo, Long userId)
			throws OrderException {
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			List<Subbranch> subbranchs=subbranchBusiness.findSubbranchByUserId(userId);
			AssertUtil.notNull(subbranchs, "分销商不存在");
			AssertUtil.isTrue(subbranchs.size()>0, "分销商不存在");
			String phone = subbranchs.get(0).getUserCell();
			SubBranchSalesOrderVo salesOrder = salesOrderService.getSubOrder(orderNo, phone);
			List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
			salesOrder.setOrderItems(orderItems);
			salesOrder.setOrderSource(salesOrder.getPurchaserNick());
			return salesOrder;
		} catch (Exception e) {
			_log.error("查询下级分店订单详情失败");
			throw new OrderException(e);
		}
	}

	private SubBranchSalesOrderVo getOrderDetail(String orderNo, String phone) {
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			SubBranchSalesOrderVo salesOrder = salesOrderService.getSubBranchSalesOrderByPrimaryKey(orderNo, phone);
			AssertUtil.notNull(salesOrder, "订单不存在！");
			List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
			salesOrder.setOrderItems(orderItems);
			setOrderSource(salesOrder);
			return salesOrder;
		}catch(Exception e){
			_log.error("分销订单获取失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Integer countSubBranchSalesOrderCountForProducer(String shopNo) {
		try {
			AssertUtil.notNull(shopNo, "param[shopNo] can't be null.");
			Integer count=salesOrderService.countSubBranchSalesOrderCountForProducer(shopNo);
			return count==null?0:count;
		}catch(Exception e){
			_log.error("统计总店下今日付款订单数失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Integer countSubBranchSalesOrderCountForDistributor(String shopNo) {
		try {
			AssertUtil.notNull(shopNo, "param[shopNo] can't be null.");
			Integer count= salesOrderService.countSubBranchSalesOrderCountForDistributor(shopNo);
			return count==null?0:count;
		}catch(Exception e){
			_log.error("统计分店下今日付款订单数失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Long getSubBranchSalesOrderTotalPrice(String shopNo) {
		try {
			AssertUtil.notNull(shopNo, "param[shopNo] can't be null.");
			Long price=salesOrderService.getSubBranchSalesOrderTotalPrice(shopNo);
			return price==null?0:price;
		}catch(Exception e){
			_log.error("统计总店下今日付款订单金额失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Long getSubBranchSalesOrderUnFreezeTotalProfitPrice(Long userId) {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			Long price= salesOrderService.getSubBranchSalesOrderUnFreezeTotalProfitPrice(userId);
			return price==null?0:price;
		}catch(Exception e){
			_log.error("统计分店下今日解冻佣金失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findAllMySalesOrderByShopNo(SalesOrder salesOrder, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination, Long userId, String isShopRequest) throws OrderException {
		    AssertUtil.notNull(salesOrder.getShopNo(), "param[shopNo] can't be null.");
		    AssertUtil.notNull(pagination, "param[pagination] can't be null.");
		pagination = salesOrderService.findAllMySalesOrderByShopNo(salesOrder, orderBy, pagination, userId, isShopRequest);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrderVo:lists){
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrderVo.getOrderNo());
				if (4 == salesOrderVo.getOrderCategory().intValue()) {
//					payCommssion = OrderItem.countToalPrice(orderItems) - salesOrderVo.getTotalPurchasePrice();
					/**查出所有分店各自得到佣金,相加得到总店支出佣金**/
				    Long payCommssion = this._computeTotalProfit(salesOrderVo.getOrderNo());
					salesOrderVo.setPayCommssion(payCommssion);
				}
				salesOrderVo.setOrderItems(orderItems);
				if(orderItems==null){
					salesOrderVo.setItems(0);
				}else{
					salesOrderVo.setItems(orderItems.size());
				}
				salesOrderVo.setOrderSource(salesOrderVo.getPurchaserNick());//冗余来源
			}
			return pagination;
	}

	/**
	 * @Title: _computeTotalProfit
	 * @Description: (计算P的支出佣金)
	 * @param orderNo
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月16日 下午2:43:46
	 */
	private Long _computeTotalProfit(String orderNo) {
		List<PurchaseOrder> purOrderList = this.purchaseOrderService.findPurchaseOrderList(orderNo, 1);
		Long payCommssion = 0L;
		if (CollectionUtils.isNotEmpty(purOrderList)) {
			for (PurchaseOrder current : purOrderList) {
				payCommssion += current.getTotalProfitPrice();
			}
		}
		return payCommssion;
	}

	@Override
	public SubBranchSalesOrderVo getSupplierSubBranchSalesOrderByOrderNo(String orderNo) throws OrderException {
		
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			SalesOrder salesOrder=salesOrderService.getSalesOrderByOrderNo(orderNo);
			List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
			PurchaseOrder purchaseOrder=null;
			if(4==salesOrder.getOrderCategory()){
				purchaseOrder = purchaseOrderService.getSupplierOrder(orderNo, salesOrder.getShopNo());
			}
			SubBranchSalesOrderVo subBranchSalesOrderVo=new SubBranchSalesOrderVo();
			subBranchSalesOrderVo.SetSalesOrder(salesOrder);
			subBranchSalesOrderVo.setPurchaseOrder(purchaseOrder);
			subBranchSalesOrderVo.setOrderItems(orderItems);
			if(orderItems==null){
				subBranchSalesOrderVo.setItems(0);
			}else{
				subBranchSalesOrderVo.setItems(orderItems.size());
			}
			//计算支出佣金
			if(4==salesOrder.getOrderCategory()){
//				Long payCommssion = OrderItem.countToalPrice(orderItems) - purchaseOrder.getTotalPurchasePrice();
				Long payCommssion = this._computeTotalProfit(salesOrder.getOrderNo());
				subBranchSalesOrderVo.setPayCommssion(payCommssion);
			}
			subBranchSalesOrderVo.setOrderSource(subBranchSalesOrderVo.getPurchaserNick());//冗余来源
			return subBranchSalesOrderVo;
		}catch(Exception e){
			_log.error("获取总店供应单详情失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Integer getSubbranchOrderTDPCount(Long subbrachId)
			throws OrderException {
		try {
			AssertUtil.notNull(subbrachId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM, "subbrachId");
			Subbranch subbranch = subbranchService.findByPrimarykey(subbrachId);
			return this.getSubbranchOrderTDPCount(subbranch.getUserCell());
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}

	@Override
	public Integer getSubbranchOrderTDPCount(String cell) throws OrderException {
		try {
			AssertUtil.hasLength(cell, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.toString(), "cell");
			Integer count = purchaseOrderService.getSubbranchOrderTDPCount(cell);
			return null == count ? 0 : count;
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findHistoryOrderForOwn(Long subBranchId, Integer status, Long customerId,
			String orderBy, Pagination<SubBranchSalesOrderVo> pagination, String isShopRequest) throws OrderException {
		
		try{
			AssertUtil.notNull(subBranchId, "Param[subBranchId] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination = salesOrderService.findHistoryOrderForOwn(subBranchId, status, customerId, orderBy, pagination,
					isShopRequest);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				setOrderSource(salesOrder);
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
			}
			return pagination;
		}catch(Exception e){
			_log.error("通过手机查找本店铺历史订单失败");
			throw new OrderException(e);
		}
	}

	@Override
	public Pagination<SubBranchSalesOrderVo> findHistoryOrderForSub(
			Long subBranchId, Integer status, String orderBy,
			Pagination<SubBranchSalesOrderVo> pagination) throws OrderException {
		try{
			AssertUtil.notNull(subBranchId, "Param[shopNo] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			pagination = salesOrderService.findHistoryOrderForSub(subBranchId, status, orderBy, pagination);
			List<SubBranchSalesOrderVo> lists=pagination.getDatas();
			if(lists==null|| lists.size()==0){
				return pagination;
			}
			for(SubBranchSalesOrderVo salesOrder:lists){
				setOrderSource(salesOrder);
				List<OrderItem> orderItems=orderItemService.findOrderItemListByOrderNo(salesOrder.getOrderNo());
				salesOrder.setOrderItems(orderItems);
			}
			return pagination;
		}catch(Exception e){
			_log.error("通过店铺编号查找分店历史订单失败");
			throw new OrderException(e);
		}
	}


	
}
