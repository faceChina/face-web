package com.zjlp.face.web.component.metaq.producer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.OrderMessage;
import com.jzwgj.metaq.vo.Topic;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
import com.zjlp.face.web.component.metaq.log.MetaLog;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.trade.order.service.OrderItemService;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;

@Component
public class OrderMetaOperateProducer {

	private Logger _logger = Logger.getLogger("metaqInfoLog");

	protected static final String ALL_NUMBER = "^[0-9]*$";

	@Autowired
	private UserService userService;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	
	

	/**
	 * @param orderNo
	 */
	public void senderAnsy(String orderNo) {
		this.senderAnsy(orderNo, null, null, null, null, null, null, null);
	}
	
	/**
	 * @param orderNo
	 * @param productName
	 */
	public void senderAnsy(String orderNo, String productName, String role, Long pushuserId, String shopNo, Long subId, Object[] roleArray, List<Object[]> roleList) {
		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + orderNo + "==========" + Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_ORDER");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.Order"));
				return;
			}
			executor = Executors.newSingleThreadExecutor();

			Pagination<SalesOrderDto> pagination = new Pagination<SalesOrderDto>();
			
			SalesOrderVo salesOrderVo = new SalesOrderVo();
			salesOrderVo.setOrderNo(orderNo);
			pagination = salesOrderBusiness.findSalesOrderDetailPage(pagination, salesOrderVo);
			
			List<SalesOrder> list = salesOrderBusiness.findSalesOrderListByOrderNoList(Arrays.asList(orderNo));
			List<OrderItem> item = orderItemService.findOrderItemListByOrderNo(orderNo);
			if (null == list || list.size() < 1) {
				_logger.error(MetaLog.getString("Error.Order.NullOrder"));
				return;
			}
			Long adminId = null;
			Shop shop = new Shop();
			if (pushuserId != null) {
				adminId = pushuserId;
			} else {
				shop = shopBusiness.getShopByNo(list.get(0).getShopNo());
				if (null == shop) {
					_logger.error(MetaLog.getString("Error.Order.NullShop"));
					return;
				}
				adminId = shop.getUserId();
			}
			Integer shopType = shop.getType() ;
			Integer orderStatus = list.get(0).getStatus();
			_logger.info(MetaLog.getString("Order.Admin.Begin", adminId.toString(), orderNo));
			OrderMessage orderMessage = new OrderMessage();
			orderMessage.setType(0);// 订单类型：交易
			orderMessage.setUserId(adminId);// 超管用户ID
			orderMessage.setOrderNo(orderNo);// 订单号
			orderMessage.setOwnershipType(list.get(0).getOrderCategory());// 订单业务类型
			orderMessage.setShopType(shopType);// 店铺类型
			orderMessage.setStatus(orderStatus);// 订单状态
			orderMessage.setProductName(productName);// 订单中商品名称
			orderMessage.setUserType(ConstantsUtil.U_ADMIN);
			orderMessage.setPicturePath(list.get(0).getPicturePath());
			orderMessage.setShopNo(shopNo);// 店铺号
			if (StringUtils.isNotBlank(shopNo) && subId == null) {
				Shop superShop = this.shopBusiness.getShopByNo(shopNo);
				orderMessage.setShopName(superShop != null ? superShop.getName() : null);// 总点名
			}
			orderMessage.setSubId(subId);// 分店ID
			if (subId != null && StringUtils.isBlank(shopNo)) {
				Subbranch subbranch = this.subbranchBusiness.findSubbranchById(subId);
				if (subbranch != null) {
					orderMessage.setSubName(subbranch.getShopName());// 分店名
					Shop supperShop = this.shopBusiness.getShopByNo(subbranch.getSupplierShopNo());
					orderMessage.setShopName(supperShop != null ? supperShop.getName() : null);// 这个分店对应的总店名
				}
			}
			if (item != null && !item.isEmpty()) {// 角色
				for (OrderItem current : item) {
					if (current.isPopularize()) {
						orderMessage.setPopularize(true);// 推广
						break;
					}
				}
			}
			orderMessage.setRole(role);
			// 订单中提现是否有发货权限字段
			if (shopNo.equals(list.get(0).getDeliveryRemoteId())) {
				orderMessage.setIsDeliver(1);
			} else {
				orderMessage.setIsDeliver(0);
			}
			// 设置推动通知 0 生成 ,01 待付款,02 已付款, 03 已发货,04 已收货 ,05 交易成功,10 已取消 ,11 已删除,20 退订,30 超时关闭
			if (orderStatus == 0 || orderStatus == 1) {
				orderMessage.setDescription("您有新订单啦！");
			} else if (orderStatus == 2) {
				orderMessage.setDescription("买家已付款，赶紧去发货呦！");
			} else if (orderStatus == 3) {
				orderMessage.setDescription("您有订单发货啦！");
			} else if (orderStatus == 4) {
				orderMessage.setDescription("您有订单确认收货啦！");
			} else if (orderStatus == 5) {
				orderMessage.setDescription("您有订单交易成功啦！");
			} else if (orderStatus == 30 || orderStatus == 10) {
				orderMessage.setDescription("您有订单已被取消！");
			}
			// 判断是否是分店的店铺订单或者分店的下级订单
			this._configSubOrderType(roleList, roleArray, orderMessage);
			// 开启线程处理
			executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.DEALTOPIC, orderMessage .toJson()));
			_logger.info(MetaLog.getString("Order.Admin.End", adminId.toString(), orderNo));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
	

	/**
	 * @Title: _configSubOrderType
	 * @Description: (判断订单是否是分店 店铺订单或者分店的下级订单)
	 * @param roleInfoArray
	 * @param orderMessage
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月30日 下午1:48:58
	 */
	private void _configSubOrderType(List<Object[]> roleList, Object[] roleArray, OrderMessage orderMessage) {
		if (null != roleList && !roleList.isEmpty() && roleList.size() >= 2 && null != roleArray && null != orderMessage) {
			if (roleArray[0].toString().toUpperCase().startsWith("BF")) {
				if (roleList.get(roleList.size() - 1)[0].equals(roleArray[0])) {
					orderMessage.setSubOrderType(1); // 来源于分店本身
				} else {
					orderMessage.setSubOrderType(2); // 来源于分店下级分店
				}
			}
		}
	}

	/**
	 * @Title: senderAnsyToAll
	 * @Description: (向与订单上行相关的卖家发送PUSH)
	 * @param orderNo
	 * @param productName
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月30日 下午4:58:00
	 */
	public void senderAnsyToAll(String orderNo, String productName, Long deliveryUserId) {
		if (StringUtils.isNotBlank(orderNo)) {
			
			
			SalesOrder salesOrder = this.salesOrderProducer.getSalesOrderByOrderNo(orderNo);
			if (salesOrder == null){
				_logger.info("订单号："+orderNo+"订单信息为空!");
			}
			List<Object[]> results = new ArrayList<Object[]>();
			if (salesOrder != null) {
				Object[] p = this._getSupplierInfo(salesOrder);
				results.add(p);
				if (salesOrder.getOrderCategory().intValue() == 4) {
					results.addAll(this._getSubBranchInfo(salesOrder));
				}
			}
			for (Object[] array : results) {
				try {
					 String role = array[0].toString();
					 Long pushuserId = Long.parseLong(array[1].toString());
					 String shopNo = array[2].toString();
					 Long subId = null;
					 if (array[3] != null){
						 subId =  Long.parseLong(array[3].toString());
					}
					// 订单发货卖家不收PUSH
					if (deliveryUserId.longValue() != pushuserId.longValue()) {
						this.senderAnsy(orderNo, productName, role, pushuserId, shopNo, subId, array, results);
					}
				} catch (Exception e) {
					// 单条发送失败下一条
					continue;
				}
			}
		}
	}
	/**
	 * @param salesOrderList
	 */
	public void ansyAllSalesOrder(List<SalesOrder> salesOrderList) {
		for (SalesOrder salesOrder : salesOrderList) {
			if (null == salesOrder || StringUtils.isBlank(salesOrder.getOrderNo())) {
				continue;
			}
			try {
				this.senderAnsy(salesOrder.getOrderNo());
			} catch (Exception e) {
				// 单条发送失败下一条
				continue;
			}
		}
	}
	
	/**
	 * 获取供应商信息
	* @Title: getSupplierInfo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param salesOrder
	* @return
	* @return Object[]    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月7日 下午5:01:22
	 */
	private Object[] _getSupplierInfo(SalesOrder salesOrder){
		Long pUserId = this.shopProducer.getShopUserIdByNo(salesOrder.getShopNo());// P
		Object[] arrayP = { "P", pUserId, salesOrder.getShopNo(),null};
		return arrayP;
	}
	
	/**
	 * 获取代理商信息
	* @Title: getSubBranchInfo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param salesOrder
	* @return
	* @return List<Object[]>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月7日 下午5:00:55
	 */
	private List<Object[]> _getSubBranchInfo(SalesOrder salesOrder) {
		List<Object[]> results = new ArrayList<Object[]>();
		if (salesOrder == null) return results;
		try {
			// 获取代理订单
			List<PurchaseOrder> purchaseOrderList = this.purchaseOrderProducer.findPurchaseOrderList(salesOrder.getOrderNo(), 1);
			List<PurchaseOrder> sortList = new ArrayList<PurchaseOrder>();
			this._sortPurchaseOrder(purchaseOrderList, salesOrder.getShopNo(), sortList);
			for (int i = 0; i < sortList.size(); i++) {
				PurchaseOrder purchaseOrder = sortList.get(i);
				Subbranch subbranch = subbranchBusiness.findByUserCell(purchaseOrder.getPurchaserNo());
				Long subBranchId = subbranch.getId();
				Long userId = subbranch.getUserId();
				Object[] array = { "BF" + (i + 1), userId, subbranch.getUserCell(), subBranchId };
				results.add(array);
			}
		} catch (Exception e) {
			_logger.error("获取subBranchInfo和角色role失败！", e);
		}
		return results;
	}
	
	/**
	* 采购商排序
	* @Title: _sortPurchaseOrder
	* @Description: 采购商排序
	* @param list
	* @param shopNo
	* @param sortList
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月6日 下午3:22:47
	*/
	private void _sortPurchaseOrder(List<PurchaseOrder> list,String shopNo,List<PurchaseOrder> sortList){
		String supplierNo = null;
		for (int i = 0 ; i < list.size();i++ ) {
			PurchaseOrder pur = list.get(i);
			if (pur.getSupplierNo().equals(shopNo)) {
				sortList.add(pur);
				supplierNo = pur.getPurchaserNo();
				list.remove(pur);
				this._sortPurchaseOrder(list, supplierNo,sortList);
			}
		}
	}
}
