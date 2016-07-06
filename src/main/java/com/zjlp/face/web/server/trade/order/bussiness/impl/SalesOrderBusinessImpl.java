package com.zjlp.face.web.server.trade.order.bussiness.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.data.ProccessUtil;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.metaq.producer.AccountMetaOperateProducer;
import com.zjlp.face.web.component.metaq.producer.OrderJobMetaOperateProducer;
import com.zjlp.face.web.component.metaq.producer.OrderMetaOperateProducer;
import com.zjlp.face.web.component.metaq.producer.ReserveOrderMetaOperateProducer;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.component.sms.Sms.SwitchType;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.ctl.AppointmentCtl;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.operation.appoint.service.AppointmentService;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketOrderDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.IntegralProducer;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.operation.member.service.MemberCardService;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchGoodRelationProducer;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.cart.domain.BuyItem;
import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuy;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuyVo;
import com.zjlp.face.web.server.trade.cart.service.CartService;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.service.CouponService;
import com.zjlp.face.web.server.trade.order.bussiness.PurchaseOrderBussiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.PromotionDsetailFactory;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler.CouponHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler.IntegralDJHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler.IntegralSendHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.handler.MemberHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.AgencySetNew;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.PurcharseItemDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset.PluAgencyRateSet;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.purcharse.rateset.RateSetSuit;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.AgencyOrderDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrdinaryOrderDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice.rule.RatioAllocation;
import com.zjlp.face.web.server.trade.order.calculate.CalculateOrder;
import com.zjlp.face.web.server.trade.order.domain.OrderCouponRelation;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.OrderOperateRecord;
import com.zjlp.face.web.server.trade.order.domain.PromotionDsetail;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OrdersCountDto;
import com.zjlp.face.web.server.trade.order.domain.vo.SingleStatuCountDto;
import com.zjlp.face.web.server.trade.order.service.OrderAppointmentDetailService;
import com.zjlp.face.web.server.trade.order.service.OrderCouponRelationService;
import com.zjlp.face.web.server.trade.order.service.OrderItemService;
import com.zjlp.face.web.server.trade.order.service.OrderRecordService;
import com.zjlp.face.web.server.trade.order.service.PromotionDsetailService;
import com.zjlp.face.web.server.trade.order.service.PurchaseOrderService;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.trade.payment.producer.PaymentProducer;
import com.zjlp.face.web.server.trade.payment.producer.PopularizeCommissionRecordProducer;
import com.zjlp.face.web.server.user.customer.producer.CustomerProducer;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.server.user.shop.producer.LogisticsProducer;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.util.Logs;
import com.zjlp.face.web.util.NumberConversion;
import com.zjlp.face.web.util.PrintLog;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;
import com.zjlp.face.web.util.files.ImageUp;
import com.zjlp.face.web.util.files.ImageUpProducer;
import com.zjlp.face.web.util.pattern.ElementSuit;

@Component("salesOrderBusiness")
public class SalesOrderBusinessImpl implements SalesOrderBusiness {
	
	private PrintLog log = new PrintLog("OrderInfoLog");
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	/** 商品直接购买 */
	public final static Integer GOOD_BUY = 1;
	
	/** 购物车购买 */
	public final static Integer CART_BUY = 2;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private PromotionDsetailService promotionDsetailService;
	
	@Autowired
	private AddressProducer addressProducer;
	
	@Autowired
	private GoodProducer goodProducer;
	
	@Autowired
	private ShopProducer shopProducer;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MemberProducer memberProducer;
	
	@Autowired
	private UserProducer userProducer;
	
	/*@Autowired
	private AuthorizationCodeProducer authorizationCodeProducer;*/
	
	@Autowired
	private PaymentProducer paymentProducer;
	
	@Autowired
	private PopularizeCommissionRecordProducer popularizeCommissionRecordProducer;
	
	@Autowired
	private LogisticsProducer logisticsProducer;
	
	@Autowired
	private OrderRecordService orderRecordService;
	
	@Autowired
	private ImageService imageService;
	
//	@Autowired
//	private OrderJobManager orderJobManager;
	
	@Autowired
	private OrderJobMetaOperateProducer orderJobMetaOperateProducer;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private MarketingProducer marketingProducer;
	
	@Autowired
	private IntegralProducer integralProducer;
	
	@Autowired
	private OrderAppointmentDetailService orderAppointmentDetailService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private CustomerProducer customerProducer;

    @Autowired
	private OrderMetaOperateProducer orderMetaOperateProducer;
	
	@Autowired
	private ReserveOrderMetaOperateProducer reserveOrderMetaOperateProducer;
	
	@Autowired
	private SubbranchProducer subbranchProducer;
	
	@Autowired
	private PurchaseOrderBussiness purchaseOrderBussiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private AccountMetaOperateProducer accountMetaOperateProducer;
	
	@Autowired
	private SubbranchGoodRelationProducer subbranchGoodRelationProducer;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private OrderCouponRelationService orderCouponRelationService;
	
	@Autowired
	private MemberCardService memberCardService;

	
	@Override
	@Deprecated
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public List<String> addSalesOrder(OrderBuyVo orderBuyVo) throws OrderException{
		try{
			AssertUtil.notNull(orderBuyVo.getAddressId(), EC.prtNull("AddressId"));
			AssertUtil.notNull(orderBuyVo.getUserId(), EC.prtNull("UserId"));
			AssertUtil.notEmpty(orderBuyVo.getOrderBuyList(), EC.prtNull("OrderBuyList"));

			List<String> orderNoList = new ArrayList<String>();
			List<SalesOrder> orderList = new ArrayList<SalesOrder>();
			orderBuyVo.setOrderList(orderList);
			//买家收货地址信息
			Address address = addressProducer.getAddressById(orderBuyVo.getAddressId());
			Long userId = orderBuyVo.getUserId();
			User user = userProducer.getUserById(userId);
			List<OrderBuy> orderBuyList = orderBuyVo.getOrderBuyList(true);
			Collections.sort(orderBuyList);
			User sellerCopy = new User();
			String orderName = StringUtils.EMPTY;
			for(OrderBuy orderBuy :orderBuyList){
				SalesOrder salesOrder = new SalesOrder();
				orderList.add(salesOrder);
				List<OrderItem> orderItemCommonList = new ArrayList<OrderItem>();
				/** 基本信息 */
				salesOrder.setStatus(Constants.STATUS_WAIT);//待付款
				salesOrder.setPayWay(Constants.PAY_WAY_ONLINE);//在线支付
				salesOrder.setFromInner(Constants.FROM_INNER_WAP);//来源：Wap
				
				/** 物流配送方式 */
				if(null != orderBuy.getDeliveryWay() && 3 == orderBuy.getDeliveryWay().intValue()){
					PickUpStore store = logisticsProducer.getPickUpStoreById(orderBuy.getPickUpId(), orderBuy.getShopNo());
					if(null != store){
						salesOrder.setPickUpAddress(store.getAddress());
						salesOrder.setPickUpPhone(store.getPhone());
					}
				}
				if(null != orderBuy.getDeliveryWay() && 2 == orderBuy.getDeliveryWay().intValue()){
					List<ShopDistribution> shopDistributionList=logisticsProducer.findShopDistributionList(orderBuy.getShopNo());
					if(null != shopDistributionList&&shopDistributionList.size()>0){
						salesOrder.setDeliveryRange(shopDistributionList.get(0).getDistributionRange());
					}
				}
				salesOrder.setDeliveryWay(orderBuy.getDeliveryWay());
				if(1 == orderBuy.getDeliveryWay()){
					salesOrder.setHasPostFee(1);
				}else{
					salesOrder.setHasPostFee(0);
				}
				
				/** 收货地址 */
				salesOrder.setReceiverName(address.getName());
				salesOrder.setReceiverPhone(address.getCell());
				salesOrder.setReceiverAddress(address.getAddressDetail());
				salesOrder.setvAreaCode(address.getvAreaCode());
				VaearDto vareaDto = addressProducer.getAreaByAreaCode(address.getvAreaCode());
				salesOrder.setReceiverState(vareaDto.getProvinceName());
				salesOrder.setReceiverCity(vareaDto.getCityName());
				salesOrder.setReceiverDistrict(vareaDto.getAreaName());
				
				/** 买家信息 */
				salesOrder.setUserId(orderBuyVo.getUserId());//用户信息
				salesOrder.setBuyerAccount(user.getLoginAccount());
				salesOrder.setBuyerNick(user.getNickname() != null ? user.getNickname() : user.getLoginAccount());
				salesOrder.setBuyerMessage(orderBuy.getBuyerMessage());
				
				/** 卖家信息 */
				String joinCartShopNo = orderBuy.getShopNo();//商品加入购物车时店铺号
				salesOrder.setShopNo(joinCartShopNo);
				Shop shop = shopProducer.getShopByNo(joinCartShopNo);
				User seller = userProducer.getUserById(shop.getUserId());
				sellerCopy = seller;
				salesOrder.setShopName(shop.getName());
				salesOrder.setSellerName(seller.getContacts() != null ? seller.getContacts() : seller.getLoginAccount());
				salesOrder.setSellerPhone(seller.getCell());
				
				/** 时间信息 */
				Date date = new Date();
//				salesOrder.setTimeoutTime(ProccessUtil.getTime("TIMER_JOB_ORDER_CLOSE"));
				
				salesOrder.setTimeoutTime(com.zjlp.face.util.date.DateUtil.addDay(new Date(), 2));
				salesOrder.setOrderTime(date);
				salesOrder.setCreateTime(date);
				salesOrder.setUpdateTime(date);
				
				/** 交易明细信息 */
				for(BuyItem buyItem : orderBuy.getBuyItemList()){
					Long skuId = null;
					Long pduItemId = null;
					String shareId = null;//推广业务时推广者ID信息
					if(GOOD_BUY.intValue() == orderBuyVo.getBuyType()){
						skuId = buyItem.getId();
						shareId = buyItem.getShareId();
					}else if(CART_BUY.intValue() == orderBuyVo.getBuyType()){
						Cart cart = cartService.getCartById(buyItem.getId());
						cartService.deleteCart(buyItem.getId());
						skuId = cart.getGoodSkuId();
						shareId = cart.getShareId();
					}
					//(TODO)
					boolean isNotPdu = null == pduItemId?true:false;
					if (isNotPdu) {
						GoodSku	goodSku = goodProducer.getGoodSkuById(skuId);
						AssertUtil.notNull(goodSku, "找不到商品SKU" ,"您购买的商品不存在！");
						/** 商品信息 */
						Good good = goodProducer.getGoodById(goodSku.getGoodId());
						AssertUtil.notNull(good, "找不到商品","您购买的商品不存在！");
						orderName = good.getName();
						String imgPath = goodSku.getPicturePath();
						Long quantity = buyItem.getQuantity() < goodSku.getStock() ? buyItem.getQuantity() : goodSku.getStock();
						if(0 == quantity || Constants.GOOD_STATUS_DEFAULT.intValue() != goodSku.getStatus()){
							throw new OrderException("商品已售罄");
						}
						/** 订单类型相关操作 此处为交易重要参数 */
						Classification classification = goodProducer.getClassificationById(good.getClassificationId());
						/** 订单类型：1 普通订单 2 协议订单 3 预约订单(此方法不处理预约订单) */
						salesOrder.setOrderCategory(classification.getCategory());
						salesOrder.setPicturePath(good.getPicUrl());
						//普通商品部分
						if(Constants.GOOD_CLASSIFICATION_COMMON.intValue() == classification.getCategory()){
							OrderItem orderItem = new OrderItem();
							orderItem.setGoodId(goodSku.getGoodId());
							orderItem.setGoodSkuId(goodSku.getId());
							orderItem.setGoodName(goodSku.getName());
							orderItem.setClassificationId(good.getClassificationId());
							orderItem.setSkuPicturePath(imgPath);
							orderItem.setAdjustPrice(0l);
							orderItem.setPrice(goodSku.getSalesPrice());
							Long discountPrice = memberProducer.getDiscountPrice(userId, shop.getNo(), goodSku.getSalesPrice(), goodSku.getPreferentialPolicy());
							orderItem.setDiscountPrice(discountPrice);
							orderItem.setQuantity(quantity);
							orderItem.setTotalPrice(CalculateUtils.get(discountPrice, orderItem.getQuantity()));
							orderItem.setSkuPropertiesName(goodSku.getSkuPropertiesName());
							orderItem.setCreateTime(date);
							orderItem.setUpdateTime(date);
							orderItem.setShareId(shareId);//设置推广号
							orderItemCommonList.add(orderItem);
						}else{
//							new OrderException("超出业务能力范围 OrderCategory ： " + classification.getCategory());
							log.writeLog("超出业务能力范围 OrderCategory ： " + classification.getCategory());
						}
					}else{}
				}
				//销售商品订单
				_addSalesOrder(orderNoList, salesOrder, orderItemCommonList, orderBuyVo.getAddressId(),orderBuy.getDeductionIndex());
				/** 订单记录 */
				orderRecordService.addOrderRecord(salesOrder.getOrderNo(), salesOrder.getUserId());
				log.writeLog("订单[" + salesOrder.getOrderNo() + "]操作记录已处理完成！");
				/** 自动任务 */
				try{
//					orderJobManager.closeOrder(salesOrder.getOrderNo());
					orderJobMetaOperateProducer.closeOrder(salesOrder.getOrderNo());
					log.writeLog("订单[" + salesOrder.getOrderNo() + "]自动业务已处理完成！");
				}catch(Exception e){
//					new OrderException("订单自动关闭任务埋点失败! 订单号:" + salesOrder.getOrderNo());
					log.writeLog("订单自动关闭任务埋点失败! 订单号:" + salesOrder.getOrderNo());
				}
				// 提交订单后 成为我的客户
				this.customerProducer.addCustomer(user, sellerCopy);
				// 推送PUSH
				try {
					orderMetaOperateProducer.senderAnsyToAll(salesOrder.getOrderNo(), orderName, -1L);
				} catch (Exception e) {
					log.writeLog("订单生成MetaQ推送异常！", e);
				}
			}
			return orderNoList;
		}catch(Exception e){
			log.writeLog(e.getMessage(), e);
			throw new OrderException(e.getMessage(),e);
		}
	}
	
	@Override
	@Transactional
	public List<String> add(OrderBuyVo orderBuyVo) throws OrderException{
		try{
			AssertUtil.notNull(orderBuyVo.getAddressId(), EC.prtNull("AddressId"));
			AssertUtil.notNull(orderBuyVo.getUserId(), EC.prtNull("UserId"));
			AssertUtil.notEmpty(orderBuyVo.getOrderBuyList(), EC.prtNull("OrderBuyList"));
			List<String> orderNoList = new ArrayList<String>();
			//买家收货地址信息
			Address address = addressProducer.getAddressById(orderBuyVo.getAddressId());
			VaearDto vareaDto = addressProducer.getAreaByAreaCode(address.getvAreaCode());
			//买家
			Long userId = orderBuyVo.getUserId();
			User user = userProducer.getUserById(userId);
			List<OrderBuy> orderBuyList = orderBuyVo.getOrderBuyList(true);
			Collections.sort(orderBuyList);
			for(OrderBuy orderBuy : orderBuyList){
				String orderNo = GenerateCode.getOderNo(orderBuy.getShopNo(), userId.toString());
				orderNoList.add(orderNo);
				//卖家信息
				Shop shop = shopProducer.getShopByNo(orderBuy.getShopNo());
				User seller = userProducer.getUserById(shop.getUserId());
				OrdinaryOrderDetail ordinaryOrder = new OrdinaryOrderDetail(orderNo);
				ordinaryOrder.withAddress(address, vareaDto)
				             .withBuyer(user, orderBuy.getBuyerMessage())
				             .withSeller(seller, shop)
				             .withGoodOwner(seller, shop);
				//配送方式
				this.initDeliveWay(orderBuy, ordinaryOrder);
				//交易明细信息
				Long subbranchId = null;
				List<GoodSku> goodSkuList = new ArrayList<GoodSku>(); //购物车处理
				for(BuyItem buyItem : orderBuy.getBuyItemList()){
					subbranchId = this.initOrderItemDetail(orderBuyVo, userId,
							shop, buyItem, ordinaryOrder,goodSkuList);
				}
				//营销活动
				List<MarketingActivityDetail> detailList = this.initMarkingInfo(userId, shop, ordinaryOrder,orderBuy);
				
				//订单生成
				SalesOrder salesOrder = null;
				if (null != subbranchId) {
					salesOrder = this.generateAgencyOrderNew(ordinaryOrder, orderBuy.getShopNo(), 
							subbranchId, goodSkuList); //单品佣金率
				} else {
					ordinaryOrder.execute();
					
					//交易订单表,记录使用优惠券抵价的价格
					this.addSalesOrderCouponPrice(ordinaryOrder);
					//修改会员卡积分使用情况
					this.updateMemberCardIntegral(ordinaryOrder.getSeller().getId(), 
							ordinaryOrder.getBuyer().getId(), ordinaryOrder.getAllIntegral());
					
					salesOrder = ordinaryOrder.getSalesOrder();
					salesOrderService.addSalesOrder(salesOrder);
				}
				
				orderBuyVo.getOrderList().add(salesOrder);
				//订单细项
				List<OrderItem> orderItemList = this.addOrderItem(ordinaryOrder);
				//优惠信息记录入库
				this.addPromotionDsetails(ordinaryOrder, detailList);
				//推广部分
				purchaseOrderBussiness.addPopularizePurchaseOrder(salesOrder.getShopNo(), 1,null, salesOrder, orderItemList);
				//订单记录
				orderRecordService.addOrderRecord(salesOrder.getOrderNo(), salesOrder.getUserId());
				log.writeLog("订单[" + salesOrder.getOrderNo() + "]操作记录已处理完成！");
				/** 自动任务 */
				try{
					orderJobMetaOperateProducer.closeOrder(salesOrder.getOrderNo());
					log.writeLog("订单[" + salesOrder.getOrderNo() + "]自动业务已处理完成！");
				}catch(Exception e){
					log.writeLog("订单自动关闭任务埋点失败! 订单号:" + salesOrder.getOrderNo());
				}
				try {
					/** 提交订单后 成为总店客户 **/
					this.customerProducer.addCustomer(user, seller);
					/** 如果是分店订单，成为该分店的客户 **/
					if (subbranchId != null) {
						Subbranch subbranch = this.subbranchBusiness.findSubbranchById(subbranchId);
						AssertUtil.notNull(subbranch, "subbranch为空！");
						User subSeller = this.userProducer.getUserById(subbranch.getUserId());
						AssertUtil.notNull(subSeller, "subSeller为空！");
						this.customerProducer.addCustomer(user, subSeller);
					}
				} catch (Exception e) {
					log.writeLog("成为客户失败！", e);
				}
				// 推送PUSH
				try {
					orderMetaOperateProducer.senderAnsyToAll(salesOrder.getOrderNo(), salesOrder.getTitle(), -1L);
				} catch (Exception e) {
					log.writeLog("订单生成MetaQ推送异常！", e);
				}
			}
			return orderNoList;
		}catch(Exception e){
			log.writeLog(e.getMessage(), e);
			throw new OrderException(e.getMessage(),e);
		}
	}

	/**
	 * 记录优惠劵信息入库
	 * @param ordinaryOrder
	 */
	private void addSalesOrderCouponPrice(OrdinaryOrderDetail ordinaryOrder) {
		Long allCouponPrice = ordinaryOrder.getAllCouponPrice();
		
		if(null==allCouponPrice || allCouponPrice.longValue()<=0){
			return;
		}

		ordinaryOrder.getSalesOrder().setCouponPrice(allCouponPrice);
	}
	
	/**
	 * 修改会员卡积分使用情况
	 * @param ordinaryOrder
	 */
	private void updateMemberCardIntegral(Long sellerId, Long userId, Long integral) {

		if(null==integral || integral.longValue()<=0){
			return;
		}
		integralProducer.frozenIntegral(sellerId, userId, integral);
	}

	@Transactional
	private void addPromotionDsetails(OrdinaryOrderDetail ordinaryOrder,
			List<MarketingActivityDetail> detailList) {

		PromotionDsetail promotionDsetail = null;
		Date date = new Date();
		SalesOrder order = ordinaryOrder.getSalesOrder();
		if (null != detailList && !detailList.isEmpty()) {
			for (MarketingActivityDetail detail : detailList) {
				if (null == detail) continue;
				if (MarketingActivityDetailDto.TYPE_DK.equals(detail.getType())) {  //TODO
					promotionDsetail = PromotionDsetailFactory.createDk(order.getOrderNo(), detail, ordinaryOrder.getAllIntegralPrice(), date);
				} else {
					promotionDsetail = PromotionDsetailFactory.createXfSong(order.getOrderNo(), detail, date);
				}
				promotionDsetailService.addPromotionDsetail(promotionDsetail);
			}
		}

	}

	/** 分店单品佣金率 */
	@Transactional(propagation = Propagation.REQUIRED)
	private SalesOrder generateAgencyOrderNew(OrdinaryOrderDetail ordinaryOrder, String shopNo, Long subbranchId,List<GoodSku> goodSkuList) {
		try {
			Assert.notNull(shopNo);
			Assert.notNull(subbranchId);
			
			//总店用户id
			Long sellerId = ordinaryOrder.getSeller().getId();
			
			AgencyOrderDetail orderDetail = new AgencyOrderDetail(ordinaryOrder);
			List<Subbranch> subbranchs = subbranchProducer.findSubbranchList(subbranchId);
			Assert.notEmpty(subbranchs);
			Subbranch subbranch = subbranchs.get(0);
			//发货权限
			if (1 == subbranch.getDeliver()) {
				orderDetail.withDeliver(subbranch.getUserCell());
			}
			//卖家
			Subbranch sellerSubbranch = subbranchs.get(subbranchs.size() - 1);
			User seller = new User();
			seller.setId(sellerSubbranch.getUserId());
			seller.setCell(sellerSubbranch.getUserCell());
			seller.setContacts(sellerSubbranch.getUserName());
			ordinaryOrder.withSeller(seller, null);
			//代理配置
			ElementSuit<PluAgencyRateSet> agencySet = this.createPluAgencyRateSet(subbranchs,goodSkuList);
			orderDetail.withAgencySet(agencySet);
			orderDetail.execute();
			
			//交易订单表,记录使用优惠券抵价的价格
			this.addSalesOrderCouponPrice(ordinaryOrder);
			//修改会员卡积分使用情况
			this.updateMemberCardIntegral(sellerId, ordinaryOrder.getBuyer().getId(), ordinaryOrder.getAllIntegral());
			
			salesOrderService.addSalesOrder(orderDetail.getSalesOrder());
			//采购信息入库
			for (PurcharseItemDetail detail : orderDetail.getPurcharseDetail().getDetails()) {
				purchaseOrderService.add(detail.getPurchaseOrder(), detail.getPurchaseOrderItems());
			}
			log.writeLog("采购信息入库完成");
			return orderDetail.getSalesOrder();
		} catch (Exception e) {
			log.writeLog(e.getMessage(),e);
			throw new OrderException(e);
		}
	}
	
	private ElementSuit<PluAgencyRateSet> createPluAgencyRateSet(List<Subbranch> subbranchs,
			List<GoodSku> goodSkuList) {
		
		Subbranch subbranch = subbranchs.get(0);
		
		PluAgencyRateSet rateSet = new PluAgencyRateSet(subbranch);
		SubbranchGoodRelation subbranchGoodRelation = new SubbranchGoodRelation();
		rateSet.setCommonRate(subbranch.getProfit());
		for(GoodSku goodSku : goodSkuList){
			subbranchGoodRelation.setSubbranchId(subbranch.getId());
			subbranchGoodRelation.setGoodId(goodSku.getGoodId());
			Integer profit = subbranchGoodRelationProducer.getSubbranchGoodRate(subbranchGoodRelation); //获得当前商品分店单品佣金率
			if (null != profit) {
				rateSet.addPluSet(goodSku.getGoodId(), profit);
				log.writeLog("分店ID：", subbranch.getId(), " 商品ID：", goodSku.getGoodId(), " 单品率佣金为：", profit);
			}
		}
		ElementSuit<PluAgencyRateSet> suit = new RateSetSuit(rateSet);
		ElementSuit<PluAgencyRateSet> root = suit;
		
		PluAgencyRateSet warpSet = rateSet;
		BigDecimal rate = null;
		for (int i = 1; i < subbranchs.size(); i++) {
			subbranch = subbranchs.get(i);
			if (null == subbranch) continue;
			rateSet = new PluAgencyRateSet(subbranch);
			rateSet.setCommonRate(warpSet.getCommonRate().multiply(BigDecimal.valueOf(subbranch.getProfit()))
					.divide(BigDecimal.valueOf(100)));
			if (null != warpSet.getPluSet() && !warpSet.getPluSet().isEmpty()) {
				for (Long goodId : warpSet.getPluSet().keySet()) {
					rate = warpSet.getPluSet().get(goodId).multiply(BigDecimal.valueOf(subbranch.getProfit()))
							.divide(BigDecimal.valueOf(100));
					rateSet.addPluSet(goodId, rate);
					log.writeLog("分店ID：", subbranch.getId(), " 商品ID：", goodId, " 单品率佣金为：", rate);
				}
			}
			suit.addChildByData(rateSet);
			warpSet = rateSet;
			suit = suit.getChilds().get(0);
		}
		return root;
	}

	/** 分店单品佣金率 */
	@SuppressWarnings("unused")
	@Deprecated
	private AgencySetNew createAgencySetNew(List<Subbranch> subbranchs,List<GoodSku> goodSkuList) {
		Subbranch subbranch = subbranchs.get(0);
		Map<Object,Integer> mapRate = new HashMap<Object, Integer>();
		SubbranchGoodRelation subbranchGoodRelation = new SubbranchGoodRelation();
		mapRate.put("isRate",0); //标志商品是(1)否(0)设置分店单品佣金率
		for(GoodSku goodSku : goodSkuList){
			subbranchGoodRelation.setSubbranchId(subbranch.getId());
			subbranchGoodRelation.setGoodId(goodSku.getGoodId());
			Integer profit = subbranchGoodRelationProducer.getSubbranchGoodRate(subbranchGoodRelation); //获得当前商品分店单品佣金率
			if (null == profit) {
				profit = subbranch.getProfit();
			}else {
				mapRate.put("isRate",1); //当前订单有商品设置了分店单品佣金率
				log.writeLog("分店ID："+subbranch.getId()+" 商品ID："+goodSku.getGoodId()+" 单品率佣金为："+profit);
			}
			mapRate.put(goodSku.getGoodId(), profit);
		}
		
		AgencySetNew agencySet = new AgencySetNew(subbranch.getId(), subbranch.getSupplierShopNo(), subbranch.getShopName(),
				subbranch.getUserCell(), subbranch.getUserName(), subbranch.getProfit(),mapRate);
		for (int i = 1; i < subbranchs.size(); i++) {
			subbranch = subbranchs.get(i);
			if (null == subbranch) continue;
			agencySet = agencySet.addChild(subbranch.getId(), agencySet.getPurchaser(), agencySet.getPurchaserNick(),
					subbranch.getUserCell(), subbranch.getUserName(), subbranch.getProfit(),mapRate);
		}
		return agencySet.getRoot();
	}
	
//	@Deprecated
//	@Transactional(propagation = Propagation.REQUIRED)
//	private SalesOrder generateAgencyOrder(OrdinaryOrderDetail ordinaryOrder, String shopNo, Long subbranchId) {
//		try {
//			Assert.notNull(shopNo);
//			Assert.notNull(subbranchId);
//			AgencyOrderDetail agencyOrderDetail = new AgencyOrderDetail(ordinaryOrder);
//			List<Subbranch> subbranchs = subbranchProducer.findSubbranchList(subbranchId);
//			Assert.notEmpty(subbranchs);
//			Subbranch subbranch = subbranchs.get(0);
//			//发货权限
//			if (1 == subbranch.getDeliver()) {
//				agencyOrderDetail.withDeliver(subbranch.getUserCell());
//			}
//			//卖家
//			Subbranch sellerSubbranch = subbranchs.get(subbranchs.size() - 1);
//			User seller = new User();
//			seller.setCell(sellerSubbranch.getUserCell());
//			seller.setContacts(sellerSubbranch.getUserName());
//			ordinaryOrder.withSeller(seller, null);
//			//代理配置
//			AgencySet agencySet = this.createAgencySet(subbranchs);
//			agencyOrderDetail.withAgencySet(agencySet);
//			agencyOrderDetail.execute();
//			salesOrderService.addSalesOrder(agencyOrderDetail.getSalesOrder());
//			//采购信息入库
//			for (PurcharseItemDetail detail : agencyOrderDetail.getPurcharseDetail().getDetails()) {
//				purchaseOrderService.add(detail.getPurchaseOrder(), detail.getPurchaseOrderItems());
//			}
//			log.writeLog("采购信息入库完成");
//			return agencyOrderDetail.getSalesOrder();
//		} catch (Exception e) {
//			throw new OrderException(e);
//		}
//	}
	
//	@Deprecated
//	private AgencySet createAgencySet(List<Subbranch> subbranchs) {
//		Subbranch subbranch = subbranchs.get(0);
//		AgencySet agencySet = new AgencySet(subbranch.getId(), subbranch.getSupplierShopNo(), subbranch.getShopName(),
//				subbranch.getUserCell(), subbranch.getUserName(), subbranch.getProfit());
//		for (int i = 1; i < subbranchs.size(); i++) {
//			subbranch = subbranchs.get(i);
//			if (null == subbranch) continue;
//			agencySet = agencySet.addChild(subbranch.getId(), agencySet.getPurchaser(), agencySet.getPurchaserNick(),
//					subbranch.getUserCell(), subbranch.getUserName(), subbranch.getProfit());
//		}
//		return agencySet.getRoot();
//	}

	@Transactional(propagation = Propagation.REQUIRED)
	private List<MarketingActivityDetail> initMarkingInfo(Long userId,
			Shop shop, OrdinaryOrderDetail ordinaryOrder,OrderBuy orderBuy) throws Exception {
		
		List<MarketingActivityDetail> list = new ArrayList<MarketingActivityDetail>();
		
		//用户是否有会员卡
		Boolean isMemberCard = true;
		
		MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, shop.getNo());
		if (null == memberCard) {
			log.writeLog("该用户不是该店铺的会员，无法参加任何营销活动！");
			isMemberCard = false;
		}else if(Constants.FREEZE.equals(memberCard.getStatus())){
			log.writeLog("该用户会员已被冻结，无法参加任何营销活动！");
			isMemberCard = false;
		}else{
			//会员打折活动
			Long consumptionAmout = memberCard.getConsumptionAmout();//用户消费总额
			Long rate = marketingProducer.getMemberActivityDiscount(shop.getUserId(), consumptionAmout);
			ordinaryOrder.addDiscountHandler(new MemberHandler(rate.intValue()));
		}
		
		if(null!=orderBuy.getDeductionIndex() && !OrderBuy.NOT_DEDUCTION.equals(orderBuy.getDeductionIndex()) && null!=orderBuy.getCouponId()){
			log.writeLog("积分抵扣和优惠劵不可同时使用！");
		}else if(null!=orderBuy.getDeductionIndex() && !OrderBuy.NOT_DEDUCTION.equals(orderBuy.getDeductionIndex()) && isMemberCard){
			//积分抵扣活动
			Long availableIntegral = memberCard.getAvailableIntegral();
			//使用积分抵扣
			MarketingActivityDetail detail = marketingProducer.getMarketingActivityDetail(shop.getNo(),
					MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF,availableIntegral.intValue());
			ordinaryOrder.addDiscountHandler(new IntegralDJHandler(availableIntegral, detail, new RatioAllocation()));
			list.add(detail);
		}else if(null != orderBuy.getCouponId()){
			//使用优惠劵
			Coupon coupon = couponService.getCouponById(orderBuy.getCouponId());
			AssertUtil.notNull(coupon, "优惠劵不存在");
			
			ordinaryOrder.addDiscountHandler(new CouponHandler(coupon, new RatioAllocation()));
			
			//订单与优惠券的关联入库
			Date now = new Date();
			OrderCouponRelation orderCouponRelation = new OrderCouponRelation();
			orderCouponRelation.setCouponId(coupon.getId());
			orderCouponRelation.setCreateTime(now);
			orderCouponRelation.setFaceValue(coupon.getFaceValue());
			orderCouponRelation.setOrderNo(ordinaryOrder.getSalesOrder().getOrderNo());
			orderCouponRelation.setUpdateTime(now);
			orderCouponRelationService.add(orderCouponRelation);
			
			//修改优惠劵已使用
			coupon.setStatus(Coupon.COUPON_HAS_STATUS);
			coupon.setUpdateTime(now);
			couponService.updateCoupon(coupon);
		}
		
		if(isMemberCard){
			//积分赠送活动
			MarketingActivityDetail deatil = marketingProducer.getMarketingActivityDetail(shop.getNo(),MarketingToolDto.CJ_TYPE_ZF,
					MarketingToolDto.PD_TYPE_JF, ordinaryOrder.getPrice().intValue());
			ordinaryOrder.addDiscountHandler(new IntegralSendHandler(deatil));
			
			list.add(deatil);
		}
		
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private Long initOrderItemDetail(OrderBuyVo orderBuyVo, Long userId,
			Shop shop, BuyItem buyItem, OrdinaryOrderDetail ordinaryOrder,List<GoodSku> goodSkuList) {
		Long skuId = null;
		String shareId = null;//推广业务时推广者ID信息
		Long subbranchId = null;
		if(GOOD_BUY.intValue() == orderBuyVo.getBuyType()){
			skuId = buyItem.getId();
			shareId = buyItem.getShareId();
			subbranchId = orderBuyVo.getSubbranchId();
		}else if(CART_BUY.intValue() == orderBuyVo.getBuyType()){
			Cart cart = cartService.getCartById(buyItem.getId());
			cartService.deleteCart(buyItem.getId());
			skuId = cart.getGoodSkuId();
			shareId = cart.getShareId();
			subbranchId = cart.getSubbranchId();
			//代理不能进行推广
			if (null != subbranchId) {
				shareId = null;
			}
		}
		OrderItemDetail itemDetail = new OrderItemDetail(buyItem.getQuantity());
		GoodSku	goodSku = goodProducer.getGoodSkuById(skuId);
		AssertUtil.notNull(goodSku, "找不到商品SKU" ,"您购买的商品不存在！");
		goodSkuList.add(goodSku);
		/** 商品信息 */
		Good good = goodProducer.getGoodById(goodSku.getGoodId());
		AssertUtil.notNull(good, "找不到商品","您购买的商品不存在！");
		/** 订单类型相关操作 此处为交易重要参数 */
		Classification classification = goodProducer.getClassificationById(good.getClassificationId());
//		Long discountPrice = memberProducer.getDiscountPrice(userId, shop.getNo(), goodSku.getSalesPrice(), goodSku.getPreferentialPolicy());
		//初始化
		itemDetail.withGood(good, goodSku)
		          .withClassification(classification)
//		          .withDiscountPrice(discountPrice)
		          .withShareId(shareId);
		ordinaryOrder.addItemDetail(itemDetail);
		return subbranchId;
	}

	private void initDeliveWay(OrderBuy orderBuy, OrdinaryOrderDetail ordinaryOrder) {
		if (null == orderBuy.getDeliveryWay()) {
			return;
		}
		if(3 == orderBuy.getDeliveryWay().intValue()){
			if(null==orderBuy.getPickUpId()){
				List<PickUpStore> list=logisticsProducer.findPickUpStoreList(orderBuy.getShopNo());
				if(list.size()>0){
					orderBuy.setPickUpId(list.get(0).getId());
				}
			}
			PickUpStore store = logisticsProducer.getPickUpStoreById(orderBuy.getPickUpId(), orderBuy.getShopNo());
			ordinaryOrder.withDeliverWay(orderBuy.getDeliveryWay(), store);
		} else if(2 == orderBuy.getDeliveryWay().intValue()){
			List<ShopDistribution> shopDistributionList=logisticsProducer.findShopDistributionList(orderBuy.getShopNo());
			if(null != shopDistributionList&&shopDistributionList.size()>0){
				ordinaryOrder.withDeliverWay(orderBuy.getDeliveryWay(), shopDistributionList.get(0));
			}
		} else {
			ordinaryOrder.withDeliverWay(orderBuy.getDeliveryWay());
		}
		//运费模板
		DeliveryTemplateDto deliveryTemplate = logisticsProducer.getDeliveryTemplateByShopNo(orderBuy.getShopNo());
		ordinaryOrder.withDeliveryTemplate(deliveryTemplate);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private List<OrderItem> addOrderItem(OrdinaryOrderDetail ordinaryOrder) throws OrderException {
		try {
			final OrderItem newInstance = new OrderItem();
			for(OrderItem orderItem : ordinaryOrder.getItemList()){
				orderItemService.addOrderItem(orderItem);
				Good good = goodProducer.getGoodById(orderItem.getGoodId());
				Shop owner = shopProducer.getShopByNo(good.getShopNo());
				ImageUp imageUp = new ImageUp(ImageConstants.GOOD_SKU_FILE, owner.getUserId(), owner.getNo(),
						"ORDER_ITEM", String.valueOf(orderItem.getId()), null, ImageUpProducer.FILELABLE_BACKUP) {
					@Override
					protected void afterTypeImage(String code, String imgData) {
						if (ImageConstants.GOOD_SKU_FILE.equals(code)) {
							newInstance.setSkuPicturePath(imgData);
						}
					}
				};
				imageUp.addFileBizParam(orderItem.getSkuPicturePath());
//				imageUp.excute();
				newInstance.setId(orderItem.getId());
				newInstance.setUpdateTime(new Date());
				orderItemService.editOrderItem(newInstance);
			}
			return ordinaryOrder.getItemList();
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}

//	@Transactional(propagation = Propagation.REQUIRED)
//	private PromotionDsetail jfdk(Long userId, OrderBuy orderBuy,
//			String orderNo, String shopNo, OrdinaryOrderDetail ordinaryOrder)
//			throws Exception {
//		PromotionDsetail promotionDsetail = null;
//		MarketOrderDetailDto jfdk = null;
//		CalculateOrder notDiscountPrice = new CalculateOrder(ordinaryOrder.getPrice(), ordinaryOrder.calculatePostFee()); //积分可以抵扣运费
//		CalculateOrder notDiscountPrice = new CalculateOrder(ordinaryOrder.getPrice()); //积分不能抵扣运费
//		if (null != orderBuy.getDeductionIndex() && -1 != orderBuy.getDeductionIndex().intValue()) {
//			jfdk =  marketingProducer.getDeductionPoints(shopNo, userId,notDiscountPrice.calculate());
//			if (null != jfdk) {
////				ordinaryOrder.withJfdk(jfdk);
//				promotionDsetail = PromotionDsetailFactory
//						.createDk(orderNo, jfdk, new Date());
//			}
//		}
//		ordinaryOrder.addDiscountHandler(new IntegralDJHandler(integral, premiseVal, resultVal, max, transform, supportRule))
//		return promotionDsetail;
//	}

//	@Transactional(propagation = Propagation.REQUIRED)
//	private PromotionDsetail xfsjf(Long userId, String orderNo,
//			String shopNo, OrdinaryOrderDetail ordinaryOrder) {
//		PromotionDsetail promotionDsetail = null;
//		Long totalPrice = ordinaryOrder.calcuTotalPrice();
//		if (totalPrice <= 0) {
//			return promotionDsetail;
//		}
//		MarketOrderDetailDto xfsjf = marketingProducer.getConsumptionOfBonusPoints(shopNo, 
//				userId, ordinaryOrder.calcuTotalPrice());
//		if (null != xfsjf) {
////			ordinaryOrder.withXfsjf(xfsjf);
//			promotionDsetail = PromotionDsetailFactory
//					.createXfSong(orderNo, xfsjf, new Date());
//		}
//		return promotionDsetail;
//	}


	@Transactional(propagation = Propagation.REQUIRED)
	private FileBizParamDto _getFileBizParam(String code, String shopNo, Long userId, Long tableId, String tableName, String ZoomSizes){
		FileBizParamDto goodFile = new FileBizParamDto();
		goodFile.setCode(code);
		goodFile.setUserId(userId);
		goodFile.setShopNo(shopNo);
		goodFile.setTableId(String.valueOf(tableId));
		goodFile.setTableName(tableName);
		goodFile.setZoomSizes(ZoomSizes);
		goodFile.setFileLabel(2);
		return goodFile;
	}
	@Deprecated
	@Transactional(propagation = Propagation.REQUIRED)
	private void _addSalesOrder(List<String> orderNoList, SalesOrder salesOrder, List<OrderItem> orderItemList, Long addressId,Integer deductionIndex)throws Exception{
		Long totalDiscountPrice = 0L;
		if(orderItemList.size() > 0){
			String orderNo = GenerateCode.getOderNo(salesOrder.getShopNo(), salesOrder.getUserId().toString());
			Long price = 0l;
			Long postFee = 0l;
			List<Good> goods = new ArrayList<Good>();
			for(OrderItem orderItem : orderItemList){
				//订单细项的优惠价格与订单表不同，订单细项记录的是优惠后的价格，订单表中记录的是可以优惠金额
				price = CalculateUtils.getSum(price, orderItem.getDiscountPrice(), orderItem.getQuantity());
				Good g = new Good();
				g.setId(orderItem.getGoodId());
				g.setInventory(orderItem.getQuantity());
				goods.add(g);
			}
			if(1 == salesOrder.getDeliveryWay()){
				/** 快递配送计算运费 */
				postFee = this.calculatePostFee(goods, salesOrder.getShopNo(), addressId);
			}
			List<PromotionDsetail> promotionDsetailList = new LinkedList<PromotionDsetail>();
			/** 查询积分抵扣信息 */
			CalculateOrder notDiscountPrice = new CalculateOrder(price, postFee);
			Long integral = 0L;
			Long discountPrice = 0L;
			if (null != deductionIndex && -1 != deductionIndex.intValue()) {
				MarketOrderDetailDto modd =  marketingProducer.getDeductionPoints(salesOrder.getShopNo(),salesOrder.getUserId(),notDiscountPrice.calculate());
				if (null != modd) {
					PromotionDsetail promotionDsetail = new PromotionDsetail();
					discountPrice = modd.getDiscountFee();//优惠金额
					integral = modd.getIntegral();
					promotionDsetail.setOrderNo(orderNo);
					promotionDsetail.setDiscountFee(discountPrice);
					promotionDsetail.setToolCode(modd.getToolId().toString());
					promotionDsetail.setActivityId(modd.getActivityId());
					promotionDsetail.setDetailId(modd.getDetailId());
					promotionDsetail.setCreateTime(salesOrder.getCreateTime());
					promotionDsetailList.add(promotionDsetail);
				}
			}
			salesOrder.setIntegral(integral);
			salesOrder.setOrderNo(orderNo);
			salesOrder.setTitle(orderItemList.get(0).getGoodName());
			salesOrder.setPrice(price);
			totalDiscountPrice = CalculateUtils.getSum(totalDiscountPrice, discountPrice);
			salesOrder.setDiscountPrice(totalDiscountPrice);
			salesOrder.setPostFee(postFee);
			CalculateOrder calculateOrder = new CalculateOrder(price, postFee, discountPrice);
			salesOrder.setTotalPrice(calculateOrder.calculate());
			log.writeLog("订单价格[" +orderNo + "]计算结果 ："+calculateOrder);
//			if(salesOrder.getTotalPrice() < Long.valueOf(PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"))){
//				throw new OrderException("订单总价最小值" + PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"));
//			}
			if(salesOrder.getTotalPrice() < Long.valueOf(1)){
			throw new OrderException("订单总价最小值1" );
				}
			salesOrder.setQuantity((long) orderItemList.size());
			/** 查询消费送积分信息 */
			Long obtainIntegral = 0L;
			MarketOrderDetailDto consumerGiftDto = 	marketingProducer.getConsumptionOfBonusPoints(salesOrder.getShopNo(), salesOrder.getUserId(), salesOrder.getTotalPrice());
			if (null != consumerGiftDto) {
				obtainIntegral = consumerGiftDto.getGiftIntegral();
				PromotionDsetail promotionDsetail = new PromotionDsetail();
				promotionDsetail.setOrderNo(orderNo);
				promotionDsetail.setDiscountFee(0L);
				promotionDsetail.setToolCode(consumerGiftDto.getToolId().toString());
				promotionDsetail.setActivityId(consumerGiftDto.getActivityId());
				promotionDsetail.setDetailId(consumerGiftDto.getDetailId());
				promotionDsetail.setCreateTime(salesOrder.getCreateTime());
				promotionDsetailList.add(promotionDsetail);
			}
			salesOrder.setObtainIntegral(null == obtainIntegral?0L:obtainIntegral);
			salesOrder.setRealIntegral(0L);
			salesOrderService.addSalesOrder(salesOrder);
			Date date=new Date();
			for(OrderItem orderItem : orderItemList){
				orderItem.setOrderNo(orderNo);
				orderItemService.addOrderItem(orderItem);
				Good good = goodProducer.getGoodById(orderItem.getGoodId());
				Shop owner = shopProducer.getShopByNo(good.getShopNo());
				FileBizParamDto goodFile = this._getFileBizParam(ImageConstants.GOOD_SKU_FILE, owner.getNo(), owner.getUserId(), orderItem.getId(), "ORDER_ITEM", null);
				goodFile.setImgData(orderItem.getSkuPicturePath());
				List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
				bizParamDtos.add(goodFile);
				String imgPath = null;
				try{
					String resultJson = imageService.addOrEdit(bizParamDtos);
					JSONObject jsonObject = JSONObject.fromObject(resultJson);
					AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败," + jsonObject.getString("info"));
					imgPath = jsonObject.getJSONArray("data").getJSONObject(0).getString("imgData");
				}catch(Exception e){
					log.writeLog(e.getMessage(),e);
				}
				OrderItem edit=new OrderItem();
				edit.setId(orderItem.getId());
				edit.setSkuPicturePath(imgPath);
				edit.setUpdateTime(date);
				orderItemService.editOrderItem(edit);
			}
			//订单优惠信息
			for (PromotionDsetail promotionDsetail : promotionDsetailList) {
				promotionDsetailService.addPromotionDsetail(promotionDsetail);
			}
			orderNoList.add(orderNo);
		}
	}
	
	@Override
	public SalesOrder getSalesOrderByOrderNo(String orderNo){
		return salesOrderService.getSalesOrderByOrderNo(orderNo);
	}
	
	@Override
	public void updateOrderStatus(String orderNo,Long userId,Integer status,String memo) {
		//扣库存
		if (Constants.BOOKORDER_STATUS_CONFIRM.equals(status)) {
			editStock(orderNo);
		}
		
		salesOrderService.editSalesOrderStatus(orderNo,status);
		//保存商家拒绝原因
		SalesOrder order=salesOrderService.getSalesOrderByOrderNo(orderNo);
		//用于判断是普通用户还是微信用户
		User user = userProducer.getUserById(order.getUserId());
		SmsContent smsContent = null;
		if (Constants.BOOKORDER_STATUS_REFUSE.equals(status)) {
			smsContent = this._getSmsContent(user, SmsContent.UMS_303, SmsContent.UMS_306);
			salesOrderService.saveBookOrderRefuseReason(orderNo,memo);
			Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, smsContent, 
					order.getReceiverPhone(),order.getAppointmentName(),PropertiesUtil.getContexrtParam("WGJ_URL"));
		}else if(Constants.BOOKORDER_STATUS_CONFIRM.equals(status)){
			smsContent = this._getSmsContent(user, SmsContent.UMS_304, SmsContent.UMS_307);
			Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, smsContent, 
					order.getReceiverPhone(),order.getAppointmentName(),PropertiesUtil.getContexrtParam("WGJ_URL"));
		}else if(Constants.BOOKORDER_STATUS_CANCEL.equals(status)){
			Appointment appointment=appointmentService.selectByPrimaryKey(order.getAppointmentId());
			if(1==appointment.getIsStartSms()){
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, SmsContent.UMS_302, 
						appointment.getSmsPhone(),order.getReceiverName(),appointment.getName(),PropertiesUtil.getContexrtParam("WGJ_URL"));
			}
		}
		//记录日志
		orderRecordService.saveOrderOperateRecord(new OrderOperateRecord(orderNo,userId,status,status,new Date()));
		//PUSH
		if (status == 40 || status == 41 || status == 42 || status == 43) {
			if (status == 41 || status == 42) {
//				this._sendAppointPush(orderNo, order.getUserId());// 向买家PUSH
			} else {
				this._sendAppointPush(orderNo, null);// 向卖家PUSH
			}
		} else {
			sendTradePush(orderNo);// 普通订单
		}
	}

	private SmsContent _getSmsContent(User user,SmsContent smsContent1,SmsContent smsContent2){
		if (StringUtils.isBlank(user.getOpenId()) && StringUtils.isBlank(user.getRegisterSourceUserId())) {
			return smsContent1;
		}else {
			return smsContent2;
		}
	}
	
	/**
	 * 修改商品库存
	 * @param orderNo 订单编号
	 */
	private void editStock(String orderNo) {
		SalesOrder order = salesOrderService.getSalesOrderByOrderNo(orderNo);
		if (!order.getOrderCategory().equals(3)) {
			return;
		}
		Appointment appointment = appointmentService.selectByPrimaryKey(order.getAppointmentId());
		//如果是商品类预约
		if (appointment.getType().equals(1)) {
			List<OrderItem> orderItems = salesOrderService.getOrderItemListByOrderNo(orderNo);
			for (OrderItem orderItem : orderItems) {
				goodProducer.editGoodSkuStock(orderItem.getGoodSkuId(),orderItem.getQuantity());
			}
		} else if (appointment.getType().equals(2)) {
			//如果是服务类预约,库存可以不填
			if (appointment.getInventory() == null) {
				return;
			}
			if (appointment.getInventory() <= 0) {
				throw new OrderException(appointment.getName() + ":库存不足");
			}
			Appointment toSave = new Appointment();
			toSave.setId(appointment.getId());
			toSave.setInventory(appointment.getInventory() - 1);
			appointmentService.updateByPrimaryKey(toSave);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void closeOrder(String orderNo){
		salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_CLOSE);

		//返回已使用的优惠劵
		this._unfrozenCoupon(orderNo);
		
		this._unfrozenIntegralPoints(orderNo);
		sendTradePush(orderNo);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void receiveAutoOrder(String orderNo,Long userId) {
		// 修改状态为已收货(收货后N天后，自动交易成功后，资金才能到账)
		salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_RECEIVE);
		
		// 订单状态操作记录(收货)
		orderRecordService.receiveOrderRecord(orderNo, userId);
		Logs.print("自动完成埋点："+orderNo);
		/** 任务埋点：自动交易完成 */
		try{
			orderJobMetaOperateProducer.compileOrder(orderNo);
			log.writeLog("订单[" + orderNo + "]自动业务已处理完成！");
		}catch(Exception e){
			log.writeLog("订单自动交易完成任务埋点失败! 订单号:" + orderNo);
		}
		
		// 订单已收货消息
		sendTradePush(orderNo);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void compileAutoOrder(String orderNo,Long userId){
		try{
			// 修改状态为已收货
			//salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_RECEIVE);
			Logs.print("自动完成："+orderNo);
			// 匿名用户不累计会员及消费
			paymentProducer.sumCardAmountAndInteger(orderNo);
			//积分抵扣业务中冻结的积分使用
			this._deductionPoints(orderNo);
			// 佣金利益到账
			paymentProducer.divideCommissionsToAccount(orderNo);
			//消费送积分
			paymentProducer.comsuerSendInteger(orderNo);
			// 全民推广利益
			popularizeCommissionRecordProducer.compileOrderManageRecord(orderNo);
			// 全民推广短信发送
			try {
				purchaseOrderService.sendPopularizeSms(orderNo);
			} catch (Exception e) {
				log.writeLog("推广短信发送失败;"+e.getMessage(),e);
				//短信发送失败无需异常处理
			}
			//修改状态为交易成功
			salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_COMPILE);
			//订单状态操作记录(交易成功)
			orderRecordService.successOrderRecord(orderNo, userId);
			// 先推送完成订单消息
			sendTradePush(orderNo);
			// 再推收益到钱包消息
			sendAccountPush(orderNo);
		}catch(Exception e){
			throw new OrderException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void _deductionPoints(String orderNo) {
		SalesOrder salesOrder = this.getSalesOrderByOrderNo(orderNo);
		AssertUtil.notNull(salesOrder, "订单不存在");
		marketingProducer.deductionPoints(salesOrder.getShopNo(), salesOrder.getUserId(), salesOrder.getIntegral());
		SalesOrder editOrder  = new SalesOrder();
		editOrder.setOrderNo(orderNo);
		editOrder.setRealIntegral(salesOrder.getIntegral());
		salesOrderService.editSalesOrder(salesOrder);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	private void _unfrozenIntegralPoints(String orderNo) {
		SalesOrder salesOrder = this.getSalesOrderByOrderNo(orderNo);
		AssertUtil.notNull(salesOrder, "订单不存在");
		integralProducer.unfrozenIntegral(salesOrder.getShopNo(),salesOrder.getUserId(),salesOrder.getIntegral());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void receiveOrder(String orderNo, Long userId) throws OrderException{
		Assert.hasLength(orderNo, "订单号为空，订单收货失败！");
		try{
			//收货完成后订单交易完成
			//this.compileAutoOrder(orderNo);
			Logs.print("自动收货："+orderNo);
			//收货完成后,订单N天后自动完成
			this.receiveAutoOrder(orderNo,userId);
			
			//订单状态操作记录(收货)
			//orderRecordService.receiveOrderRecord(orderNo, userId);
		}catch(Exception e){
			throw new OrderException(e);
		}
	}
	
	@Override
	public void deleteOrder(String orderNo, Long userId){
		salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_DELETE);
		orderRecordService.deleteOrderRecord(orderNo, userId);
		// sendTradePush(orderNo);//订单删除不发通知
	}
	
	@Override
	@Transactional
	public void cancleOrder(String orderNo, Long userId){
		salesOrderService.editSalesOrderStatus(orderNo, Constants.STATUS_CANCEL);
		orderRecordService.cancleOrderRecord(orderNo, userId);
		
		//返回已使用的优惠劵
		this._unfrozenCoupon(orderNo);
		
		//积分抵扣业务中冻结的积分使用
		this._unfrozenIntegralPoints(orderNo);
		sendTradePush(orderNo);
	}
	
	@Transactional
	private void _unfrozenCoupon(String orderNo){
		SalesOrder salesOrder = this.getSalesOrderByOrderNo(orderNo);
		AssertUtil.notNull(salesOrder, "订单不存在");
		
		//查询此订单号下是否使用过优惠劵
		Coupon coupon = orderCouponRelationService.getCouponByOrderNo(orderNo);
		if(null != coupon){
			//将优惠劵更改为未使用状态
			coupon.setStatus(Coupon.COUPON_NOT_HAS_STATUS);
			coupon.setUpdateTime(new Date());
			couponService.updateCoupon(coupon);
			//删除订单优惠券的关联
			orderCouponRelationService.deleteByOrderNo(orderNo);
		}
	}
	
	@Override
	public Pagination<SalesOrderDto> findOrderPageForWap(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination){
		return salesOrderService.findSalesOrderPageForWap(salesOrderVo, pagination);
	}
	
	@Override
	public SalesOrderDto getSalesOrderDetailByOrderNo(String orderNo){
		try {
			SalesOrderDto salesOrderDto = salesOrderService.getSalesOrderDtoByOrderNo(orderNo);
			salesOrderDto.setOrderItemList(orderItemService.findOrderItemListByOrderNo(orderNo));
			//推广拥挤计算
			if (salesOrderDto.getOrderCategory().intValue() == 3) {
				List<OrderAppointmentDetail> list = orderAppointmentDetailService.findOrderAppointmentDetailListByOrderNo(orderNo);
				salesOrderDto.setOrderAppointmentDetails(list);
			}
			List<PurchaseOrderDto> list = purchaseOrderService.findPromoteOrdersByOrderNo(orderNo);
			Long promoteSpend = PurchaseOrderDto.getTotalPromoteSpend(list);
			salesOrderDto.setPromoteSpend(promoteSpend);
			return salesOrderDto;
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}
	
	@Override
	public List<SalesOrder> findSalesOrderListByOrderNoList(List<String> orderNoList){
		return salesOrderService.findSalesOrderListByOrderNoList(orderNoList);
	}
	
	@Override
	public Integer countTodayCompile(String shopNo){
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo(shopNo);
		Date date = new Date();
		salesOrderVo.setStartTime(DateUtil.getZeroPoint(date));
		salesOrderVo.setStatus(Constants.STATUS_COMPILE);
		return salesOrderService.countSalesOrder(salesOrderVo);
	}
	
	@Override
	public Integer countTodayAll(String shopNo){
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo(shopNo);
		Date date = new Date();
		salesOrderVo.setStartTime(DateUtil.getZeroPoint(date));
		return salesOrderService.countSalesOrder(salesOrderVo);
	}
	
	@Override
	public Integer countWait(String shopNo){
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo(shopNo);
		salesOrderVo.setStatus(Constants.STATUS_WAIT);
		return salesOrderService.countSalesOrder(salesOrderVo);
	}
	
	@Override
	public Integer countPay(String shopNo){
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo(shopNo);
		salesOrderVo.setStatus(Constants.STATUS_PAY);
		return salesOrderService.countSalesOrder(salesOrderVo);
	}
	
	@Override
	public Pagination<SalesOrderDto> findSalesOrderDetailPage(Pagination<SalesOrderDto> pagination, SalesOrderVo salesOrderVo){
		if(null != salesOrderVo.getStatus() && (0 == salesOrderVo.getStatus() || Constants.STATUS_DELETE == salesOrderVo.getStatus())){
			salesOrderVo.setStatus(null);
		}
		if(null != salesOrderVo.getPayWay() && 0 == salesOrderVo.getPayWay()){
			salesOrderVo.setPayWay(null);
		}
		if(null != salesOrderVo.getDeliveryWay() && 0 == salesOrderVo.getDeliveryWay()){
			salesOrderVo.setDeliveryWay(null);
		}
		if(null != salesOrderVo.getPayChannel() && 0 == salesOrderVo.getPayChannel()){
			salesOrderVo.setPayChannel(null);
		}
		if(null != salesOrderVo.getTimeStates()){
			Date current = new Date();
			Date zeroPoint = DateUtil.getZeroPoint(current);
			if(1 == salesOrderVo.getTimeStates()){
				/** 今天 */
				salesOrderVo.setStartTime(zeroPoint);
			}else if(2 == salesOrderVo.getTimeStates()){
				/** 昨天 */
				salesOrderVo.setStartTime(DateUtil.addDay(zeroPoint, -1));
				salesOrderVo.setEndTime(zeroPoint);
			}else if(7 == salesOrderVo.getTimeStates()){
				/** 最近一周 */
				salesOrderVo.setStartTime(DateUtil.addDay(zeroPoint, -7));
			}else if(30 == salesOrderVo.getTimeStates()){
				/** 最近一月 */
				salesOrderVo.setStartTime(DateUtil.addMonth(zeroPoint, -1));
			}
		}
		salesOrderVo.setPayWay(salesOrderVo.getPayWay());
		return salesOrderService.findSalesOrderDetailPage(salesOrderVo, pagination);
	}
	
	public Pagination<SalesOrderDto> findOrderPage(
			Pagination<SalesOrderDto> pagination, SalesOrderVo salesOrderVo) {
		pagination = this.findSalesOrderDetailPage(pagination, salesOrderVo);
		if (null == pagination.getDatas() || pagination.getDatas().isEmpty()) {
			return pagination;
		}
		for (SalesOrderDto order : pagination.getDatas()) {
			List<Long> skuIdList = new ArrayList<Long>();
			Map<Long, OrderItem> map = new HashMap<Long, OrderItem>();
			for (OrderItem item : order.getOrderItemList()) {
				if (null == item) continue;
				skuIdList.add(item.getGoodSkuId());
				map.put(item.getGoodSkuId(), item);
			}
			skuIdList = this.findPopularizeList(order.getOrderNo(), skuIdList);
			if (null == skuIdList || skuIdList.isEmpty()) continue;
			OrderItem item = null;
			for (Long skuId : skuIdList) {
				if (null == (item = map.get(skuId))) continue;
				item.setPopularize(true);
			}
		}
		return pagination;
	}
	
	@Override
	public void validateOrderByShopNo(String orderNo, String shopNo){
		if (shopNo == null) {
			throw new OrderException("无权操作该订单");
		}
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setOrderNo(orderNo);
		salesOrderVo.setShopNo(shopNo);
		Integer count = salesOrderService.validateOrder(salesOrderVo);
		if(0 == count){
			throw new OrderException("订单异常");
		}
	}
	
	@Override
	public void validateOrderByUserId(String orderNo, Long userId){
		if (userId == null) {
			throw new OrderException("无权操作该订单");
		}
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setOrderNo(orderNo);
		salesOrderVo.setUserId(userId);;
		Integer count = salesOrderService.validateOrder(salesOrderVo);
		if(0 == count){
			throw new OrderException("订单异常");
		}
	}
	
	@Override
	@Transactional
	public void adjustOrderPrice(String orderNo, Long userId,
			Long adjustPrice) {
		AssertUtil.hasLength(orderNo,EC.prtNull("orderNo"));
		AssertUtil.notNull(userId, EC.prtNull("userId"));
		AssertUtil.notNull(adjustPrice, EC.prtNull("adjustPrice"));
		try {
			SalesOrder salesOrder = salesOrderService.getSalesOrderByOrderNo(orderNo);
			AssertUtil.notNull(salesOrder, "订单不存在,不能调价");
			AssertUtil.notNull(salesOrder.getStatus(), "订单不存在,不能调价");
			if(!Constants.STATUS_WAIT.equals(salesOrder.getStatus())){
				throw new OrderException("订单状态异常,不能调价");
			} else if (null != salesOrder.getPayStatus()) {
				throw new OrderException("支付处理中，不能调价");
			} 
			AssertUtil.notNull(salesOrder.getPrice(), "订单子项合计价格为空");
			
			//重新计算主订单价格
			CalculateOrder calculateOrder = new CalculateOrder(salesOrder.getPrice(),
					salesOrder.getPostFee(),salesOrder.getDiscountPrice(),adjustPrice);
			SalesOrder edit = new SalesOrder();
			edit.setOrderNo(orderNo);
			edit.setAdjustPrice(adjustPrice);
			edit.setTotalPrice(calculateOrder.calculate());
			edit.setUpdateTime(new Date());
			if(edit.getTotalPrice() < Long.valueOf(PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"))){
				throw new OrderException("调整后订单总价最小值"+PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"));
			}
			salesOrderService.editSalesOrder(edit);
			orderRecordService.adjustPriceRecord(orderNo,1,edit.getTotalPrice(), adjustPrice, userId);
		} catch (Exception e) {
			throw new OrderException(e.getMessage(),e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void dealOrderAfterPay(List<String> orderNoList) throws OrderException{
		try{
			List<SalesOrder> orderList = salesOrderService.findSalesOrderListByOrderNoList(orderNoList);
			for(SalesOrder order : orderList){
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, SmsContent.UMS_203, 
						order.getSellerPhone(),order.getReceiverPhone());
				
				//用于判断是普通用户还是微信用户
				User user = userProducer.getUserById(order.getUserId());
				SmsContent smsContent = this._getSmsContent(user, SmsContent.UMS_210, SmsContent.UMS_212);
				
				String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
				String no = NumberConversion.setShopNo(order.getShopNo());
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, smsContent, order.getReceiverPhone(),order.getOrderNo(),wgjurl+"/uc/"+no+".htm");
				try {
					String shopNo = order.getDeliveryRemoteId();
					Long subbranchId = null;
					String role = null;
					Long pushId = null;
					if (2 == order.getDeliveryRemoteType()){
						Subbranch subbranch = subbranchBusiness.findByUserCell(shopNo);
						subbranchId = subbranch.getId();
						pushId = subbranch.getUserId();
						role = "BF1";
					}else {
						role = "P";
						Shop shop = shopProducer.getShopByNo(shopNo);
						pushId = shop.getUserId();
					}
					orderMetaOperateProducer.senderAnsy(order.getOrderNo(), order.getTitle(), role, pushId, shopNo, subbranchId, null, null);
				} catch (Exception e) {
					log.writeLog("订单生成MetaQ推送异常！", e);
				}
				
				
			}
		}catch(Exception e){
			log.writeLog("付款后订单处理异常:" + orderNoList);
			log.writeLog(e.getMessage(), e);
			throw new OrderException(e);
		}
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "OrderException" })
	public void dealOrderAfterPayV2(List<SalesOrder> orderList) throws OrderException{
		try{
			if(orderList==null)return;
			for(SalesOrder order : orderList){
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, SmsContent.UMS_203, 
						order.getSellerPhone(),order.getReceiverPhone());
				
				//用于判断是普通用户还是微信用户
				User user = userProducer.getUserById(order.getUserId());
				SmsContent smsContent = this._getSmsContent(user, SmsContent.UMS_210, SmsContent.UMS_212);
				
				String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
				String no = NumberConversion.setShopNo(order.getShopNo());
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, smsContent, order.getReceiverPhone(),order.getOrderNo(),wgjurl+"/uc/"+no+".htm");
				try {
					String shopNo = order.getDeliveryRemoteId();
					Long subbranchId = null;
					String role = null;
					Long pushId = null;
					if (2 == order.getDeliveryRemoteType()){
						Subbranch subbranch = subbranchBusiness.findByUserCell(shopNo);
						subbranchId = subbranch.getId();
						pushId = subbranch.getUserId();
						role = "BF1";
					}else {
						role = "P";
						Shop shop = shopProducer.getShopByNo(shopNo);
						pushId = shop.getUserId();
					}
					orderMetaOperateProducer.senderAnsy(order.getOrderNo(), order.getTitle(), role, pushId, shopNo, subbranchId, null, null);
				} catch (Exception e) {
					logger.error("订单生成MetaQ推送异常！", e);
				}
				
				
			}
		}catch(Exception e){
			StringBuilder sb=new StringBuilder();
			for(SalesOrder so:orderList){
				sb.append(so.getOrderNo()+",");
			}
			logger.error("付款后订单处理异常:" + sb.toString());
			logger.error(e.getMessage(), e);
			throw new OrderException(e);
		}
		
	}
	@Override
	@Transactional
	public void deliveryOrder(SalesOrder salesOrder, Long userId){
		SalesOrder order=salesOrderService.getSalesOrderByOrderNo(salesOrder.getOrderNo());
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
		String no = NumberConversion.setShopNo(order.getShopNo());
		
		//用于判断是普通用户还是微信用户
		User user = userProducer.getUserById(order.getUserId());
		SmsContent smsContent = this._getSmsContent(user, SmsContent.UMS_211, SmsContent.UMS_213);
		log.writeLog("*************用户open_id:"+user.getOpenId()+"用户registerSourceUserId："+user.getRegisterSourceUserId());
		Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, smsContent, order.getReceiverPhone(),order.getOrderNo(),wgjurl+"/uc/"+no+".htm");
		/** 任务埋点：自动收货 */
		try{
//			orderJobManager.compileOrder(salesOrder.getOrderNo());
			orderJobMetaOperateProducer.receiveOrder(salesOrder.getOrderNo());
			log.writeLog("订单[" + salesOrder.getOrderNo() + "]自动业务已处理完成！");
		}catch(Exception e){
			//new OrderException("订单自动收货任务埋点失败! 订单号:" + salesOrder.getOrderNo());
			log.writeLog("订单自动收货任务埋点失败! 订单号:" + salesOrder.getOrderNo(), e);
		}
		sendTradePush(salesOrder.getOrderNo());
	}
	
	@Override
	public Long calculatePostFee(List<Good> goods, String shopNo, Long addressId){
		if(null==addressId){
			return 0l;
		}
		Address address = addressProducer.getAddressById(addressId);
		DeliveryTemplateDto deliveryTemplateDto = logisticsProducer.getDeliveryTemplateByShopNo(shopNo);
		String code = address.getvAreaCode() / 10000 + "0000";
		DeliveryTemplateItemDto item = null;
		if(null != deliveryTemplateDto){
			for(DeliveryTemplateItemDto dto : deliveryTemplateDto.getItemList()){
				if(DeliveryTemplateItemDto.ALL.equals(dto.getDestination())){
					item = dto;
				}else if(-1 != dto.getDestination().indexOf(code)){
					item = dto;
					break;
				}
			}
		}
		Long maxUnifyFee = 0l;
		Long temFee = 0l;
		int quantity = 0;
		for(Good g : goods){
			Good good = goodProducer.getGoodById(g.getId());
			if(1 == good.getLogisticsMode()){
				/** 运费模板 */
				quantity += g.getInventory();
			}else if(2 == good.getLogisticsMode()){
				/** 统一运费 */
				if(maxUnifyFee<good.getPostFee()){
					maxUnifyFee=good.getPostFee();
				}
			}
		}
		if(quantity > 0 && null != item){
			int num = quantity - item.getStartStandard();
			if(num > 0){
				int times = num / item.getAddStandard() + (num % item.getAddStandard() == 0 ? 0 : 1);
				temFee += times * item.getAddPostage();
			}
			return (item.getStartPostage()>maxUnifyFee?item.getStartPostage():maxUnifyFee) + temFee;
		}
		return maxUnifyFee;
	}
	
	@Override
	public Long getYesterdayIncomeByShopNo(String no){
		Date date = new Date();
		Date endTime = DateUtil.getZeroPoint(date);
		Date startTime = DateUtil.addDay(endTime, -1);
		return salesOrderService.getSalesOrderIncome(no, startTime, endTime);
	}
	
	@Override
	public void sellerRemark(String orderNo, String sellerMemo){
		SalesOrder edit = new SalesOrder();
		edit.setOrderNo(orderNo);
		edit.setSellerMemo(sellerMemo);
		salesOrderService.editSellerMemo(edit);
	}

	@Override
	public Long calculatePostFee(OrderBuyVo orderBuyVo){
		Long postFee = 0l;
		if(null==orderBuyVo.getAddressId()){
			return postFee;
		}
		Address address = addressProducer.getAddressById(orderBuyVo.getAddressId());
		if(null==address){
			return postFee;
		}
		for(OrderBuy orderBuy : orderBuyVo.getOrderBuyList()){
			if(null == orderBuy.getDeliveryWay() || 1 != orderBuy.getDeliveryWay()){
				continue;
			}
			List<Good> goods = new ArrayList<Good>();
			for(BuyItem buyItem : orderBuy.getBuyItemList()){
				GoodSku goodSku = new GoodSku();
				if(GOOD_BUY.intValue() == orderBuyVo.getBuyType()){
					goodSku = goodProducer.getGoodSkuById(buyItem.getId());
				}else if(CART_BUY.intValue() == orderBuyVo.getBuyType()){
					Cart cart = cartService.getCartById(buyItem.getId());
					goodSku = goodProducer.getGoodSkuById(cart.getGoodSkuId());
				}
				Good g = new Good();
				g.setId(goodSku.getGoodId());
				g.setInventory(buyItem.getQuantity());
				goods.add(g);
			}
			postFee += this.calculatePostFee(goods, orderBuy.getShopNo(), orderBuyVo.getAddressId());
		}
		return postFee;
	}
	
	@Override
	public String addAppointOrder(Long id, SalesOrderVo salesOrderVo, List<GoodSkuVo> selectedList) throws Exception {
		Appointment appointment = appointmentService.selectByPrimaryKey(id);
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setStatus(40);
		salesOrder.setIntegral(0l);
		salesOrder.setObtainIntegral(0l);
		salesOrder.setPayWay(2);
		salesOrder.setDeliveryWay(0);
		salesOrder.setHasPostFee(0);
		salesOrder.setOrderCategory(3);
		Long userId = salesOrderVo.getUserId();
		User user = userProducer.getUserById(userId);
		List<OrderItem> orderItemCommonList = new ArrayList<OrderItem>();
		salesOrder.setFromInner(1);
		salesOrder.setTitle(appointment.getName());
		salesOrder.setAppointmentName(appointment.getName());
		salesOrder.setSellerPhone(appointment.getCell());
		salesOrder.setAppointmentAddress(appointment.getAddress());
		salesOrder.setAppointmentId(appointment.getId());
		salesOrder.setUserId(userId);
		salesOrder.setReceiverName(salesOrderVo.getReceiverName());
		salesOrder.setReceiverPhone(salesOrderVo.getReceiverPhone());
		salesOrder.setBuyerAccount(user.getLoginAccount());
		salesOrder.setBuyerNick(user.getNickname() != null ? user.getNickname() : user.getLoginAccount());
		salesOrder.setShopNo(salesOrderVo.getShopNo());
		salesOrder.setBuyerMessage(salesOrderVo.getBuyerMessage());
		Shop shop = shopProducer.getShopByNo(salesOrderVo.getShopNo());
		User seller = userProducer.getUserById(shop.getUserId());
		salesOrder.setShopName(shop.getName());
		salesOrder.setSellerName(seller.getContacts() != null ? seller.getContacts() : seller.getLoginAccount());
		Date date = new Date();
		salesOrder.setTimeoutTime(ProccessUtil.getTime("TIMER_JOB_ORDER_CLOSE"));
		salesOrder.setAppointmentTime(date);
		salesOrder.setPicturePath(appointment.getPicturePath());
		salesOrder.setOrderTime(date);
		salesOrder.setCreateTime(date);
		salesOrder.setUpdateTime(date);
		salesOrder.setPrice(appointment.getPrice());
		salesOrder.setDiscountPrice(appointment.getPrice());
		salesOrder.setTotalPrice(appointment.getPrice());
		salesOrder.setOrderCategory(3);
		String orderNo = GenerateCode.getOderNo(salesOrder.getShopNo(), salesOrder.getUserId().toString());
		salesOrder.setOrderNo(orderNo);
		if(null!=selectedList){
			for (GoodSkuVo goodSkuVo : selectedList) {
				GoodSku goodSku = goodProducer.getGoodSkuById(goodSkuVo.getId());
				Good good = goodProducer.getGoodById(goodSku.getGoodId());
				String imgPath = goodSku.getPicturePath();
				Long quantity = goodSkuVo.getQuantity() < goodSku.getStock() ? goodSkuVo.getQuantity() : goodSku.getStock();
				if (0 == quantity || Constants.GOOD_STATUS_DEFAULT.intValue() != goodSku.getStatus()) {
					throw new OrderException("商品已售完");
				}
				OrderItem orderItem = new OrderItem();
				orderItem.setGoodId(goodSku.getGoodId());
				orderItem.setGoodSkuId(goodSku.getId());
				orderItem.setGoodName(goodSku.getName());
				orderItem.setClassificationId(good.getClassificationId());
				orderItem.setSkuPicturePath(imgPath);
				orderItem.setAdjustPrice(0l);
				orderItem.setPrice(goodSku.getSalesPrice());
				Long discountPrice = memberProducer.getDiscountPrice(userId, shop.getNo(), goodSku.getSalesPrice(), goodSku.getPreferentialPolicy());
				orderItem.setDiscountPrice(discountPrice);
				orderItem.setQuantity(quantity);
				orderItem.setTotalPrice(CalculateUtils.get(discountPrice, orderItem.getQuantity()));
				orderItem.setSkuPropertiesName(goodSku.getSkuPropertiesName());
				orderItem.setCreateTime(date);
				orderItem.setUpdateTime(date);
				orderItemCommonList.add(orderItem);
			}
		}
		if(0!=appointment.getMaxVal()){
			Integer num=salesOrderService.getAppointNum(salesOrder.getAppointmentId(),salesOrder.getReceiverPhone());
			AssertUtil.isTrue(num<appointment.getMaxVal(), "一个手机号码只能预约"+appointment.getMaxVal()+"次","一个手机号码只能预约"+appointment.getMaxVal()+"次");
		}
		_addAppointSalesOrder(salesOrder, orderItemCommonList,appointment.getType());
		if(1==appointment.getIsStartSms()){
			Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_ORDER_SWITCH, SmsContent.UMS_301, 
					appointment.getSmsPhone(),appointment.getName(),PropertiesUtil.getContexrtParam("WGJ_URL"));
		}
		List<OrderAppointmentDetail> appointmentDetailList=salesOrderVo.getAppointmentDetailList();
		if(null!=appointmentDetailList){
			for(OrderAppointmentDetail detail:appointmentDetailList){
				if(StringUtils.isEmpty(detail.getAttrName()))continue;
				if(null!=detail.getAttrValue()&&detail.getAttrValue().startsWith(",")&&-1!=detail.getAttrValue().indexOf(":00-")){
					detail.setAttrValue("无");
				}
				detail.setOrderNo(salesOrder.getOrderNo());
				detail.setCreateTime(new Date());
				orderAppointmentDetailService.insert(detail);
			}
		}
		// 向卖家PUSH
		this._sendAppointPush(shop, salesOrder, salesOrder, orderItemCommonList, null);
		return salesOrder.getOrderNo();
	}


	private void _addAppointSalesOrder(SalesOrder salesOrder, List<OrderItem> orderItemList,Integer type) {
		Long quantity = 0L;
		if(type==AppointmentCtl.APPOINTMENT_GOOD_TYPE){
			Long price = 0l;
			Long discountPrice = 0l;
			Long postFee = 0l;
			for (OrderItem orderItem : orderItemList) {
				price = CalculateUtils.getSum(price, orderItem.getPrice(), orderItem.getQuantity());
				discountPrice = CalculateUtils.getSum(discountPrice, orderItem.getTotalPrice());
				quantity = quantity + orderItem.getQuantity();
			}
			if (orderItemList.size() > 0) {
				salesOrder.setTitle(orderItemList.get(0).getGoodName());
			}
			salesOrder.setPrice(price);
			salesOrder.setDiscountPrice(discountPrice);
			salesOrder.setPostFee(postFee);
			salesOrder.setTotalPrice(salesOrder.getDiscountPrice() + salesOrder.getPostFee());
			if (salesOrder.getTotalPrice() < Long.valueOf(PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"))) {
				throw new OrderException("订单总价最小值" + PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"));
			}
		}
		salesOrder.setQuantity(quantity);
		//预约订单补丁    字段默认值  
		salesOrder.setSellerRemoteId(salesOrder.getShopNo());
		salesOrder.setSellerRemoteType(Constants.AGENCY_ROLE_SUPPLIER);   //店铺
		salesOrder.setDeliveryRemoteId(salesOrder.getShopNo());
		salesOrder.setDeliveryRemoteType(Constants.AGENCY_ROLE_SUPPLIER);  //店铺
		salesOrderService.addSalesOrder(salesOrder);
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrderNo(salesOrder.getOrderNo());
			orderItemService.addOrderItem(orderItem);
			Good good = goodProducer.getGoodById(orderItem.getGoodId());
			Shop owner = shopProducer.getShopByNo(good.getShopNo());
			FileBizParamDto goodFile = this._getFileBizParam(ImageConstants.GOOD_SKU_FILE, owner.getNo(), owner.getUserId(), orderItem.getId(), "ORDER_ITEM", null);
			goodFile.setImgData(orderItem.getSkuPicturePath());
			List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
			bizParamDtos.add(goodFile);
			String imgPath = null;
			try {
				String resultJson = imageService.addOrEdit(bizParamDtos);
				JSONObject jsonObject = JSONObject.fromObject(resultJson);
				AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败," + jsonObject.getString("info"));
				imgPath = jsonObject.getJSONArray("data").getJSONObject(0).getString("imgData");
			} catch (Exception e) {
				throw new OrderException(e.getMessage(), e);
			}
			OrderItem edit = new OrderItem();
			edit.setId(orderItem.getId());
			edit.setSkuPicturePath(imgPath);
			edit.setUpdateTime(new Date());
			orderItemService.editOrderItem(edit);
		}
		orderRecordService.addOrderRecord(salesOrder.getOrderNo(), salesOrder.getUserId());
		try {
//			orderJobManager.confirmAppointOrder(salesOrder.getOrderNo());
			orderJobMetaOperateProducer.confirmAppointOrder(salesOrder.getOrderNo());
			log.writeLog("订单[" + salesOrder.getOrderNo() + "]自动业务已处理完成！");
		} catch (Exception e) {
//			new OrderException("订单自动关闭任务埋点失败! 订单号:" + salesOrder.getOrderNo());
			log.writeLog("订单自动关闭任务埋点失败! 订单号:" + salesOrder.getOrderNo());
		}
	}

	

	@Override
	public void confirmAppointOrder(String orderNo) {
		SalesOrder salesOrder=salesOrderService.getSalesOrderByOrderNo(orderNo);
		Shop shop=shopProducer.getShopByNo(salesOrder.getShopNo());
		this.updateOrderStatus(orderNo, shop.getUserId(), Constants.BOOKORDER_STATUS_CONFIRM, "");
		this._sendAppointPush(orderNo, null);// TODO
	}



	@Override
	public Integer findOrderCountByShopNo(String shopNo) {
		return this.salesOrderService.findOrderCountByShopNo(shopNo);
	}

	@Override
	@Transactional
	public void cancleOrder(String orderNo, String refuseReason, Long userId) {
		try {
			AssertUtil.notNull(orderNo, "param[orderNo] can't be null.");
			AssertUtil.notNull(refuseReason, "param[refuseReason] can't be null.");
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			this.cancleOrder(orderNo, userId);
			salesOrderService.saveBookOrderRefuseReason(orderNo,refuseReason);
		} catch (Exception e) {
			throw new OrderException(e);
		}
		sendTradePush(orderNo);
	}



	
	//查询对应订单编号和skuid列表
	private List<Long> findPopularizeList(String orderNo, List<Long> skuIdList) {
		try {
			AssertUtil.hasLength(orderNo, "param[orderNo] can't be null.");
			AssertUtil.notEmpty(skuIdList, "skulist is empty.");
			return purchaseOrderService.findPopularizeSkuIdList(orderNo, skuIdList);
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}

	

	@Override
	public OrdersCountDto getOrderCounts(SalesOrderVo salesOrderVo)
			throws OrderException {
		try {
			AssertUtil.notNull(salesOrderVo, "param[salesOrderVo] can't be null.");
			List<SingleStatuCountDto> dataList = salesOrderService.
					findOrderStatuCountDtoList(salesOrderVo);
			return new OrdersCountDto(dataList);
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}


	@Override
	public Integer getCountsByType(SalesOrderDto dto)
			throws OrderException {
		try {
			AssertUtil.notNull(dto, "param[dto] can't be null.");
//			return salesOrderService.getCountsByType(dto);
			return null;
		} catch (RuntimeException e) {
			throw new OrderException(e);
		}
	}

	// 发送交易PUSH消息 type：0
	private void sendTradePush(String orderNo) {
		// 获取订单中一个商品名称用于显示
		List<OrderItem> orderItem = this.salesOrderService.getOrderItemListByOrderNo(orderNo);
		String productName = null;
		if (!orderItem.isEmpty()) {
			productName = orderItem.get(0).getGoodName();
		}
		// PUSH
		try {
			orderMetaOperateProducer.senderAnsyToAll(orderNo, productName, -1L);
		} catch (Exception e) {
			log.writeLog("订单生成MetaQ推送异常！", e);
		}
	}

	private void sendAccountPush(String orderNo) {
		try {
			this.accountMetaOperateProducer.senderAnsy(orderNo, ConstantsUtil.PUSH_ACCOUNT_MESSAGE);
		} catch (Exception e) {
			log.writeLog("收益到钱包推送失败;" + e.getMessage(), e);
		}
	}

	// 发送预约PUSH消息 类型：3
	private void _sendAppointPush(String orderNo, Long buyerId) {
		SalesOrder salesOrder = this.salesOrderService.getSalesOrderByOrderNo(orderNo);
		List<OrderItem> orderItemCommonList = this.salesOrderService.getOrderItemListByOrderNo(orderNo);
		Shop shop = salesOrder != null ? this.shopProducer.getShopByNo(salesOrder.getShopNo()) : new Shop();
		this._sendAppointPush(shop, salesOrder, salesOrder, orderItemCommonList, buyerId);
	}

	private void _sendAppointPush(Shop shop, SalesOrder orderAddress, SalesOrder salesOrder, List<OrderItem> orderItemCommonList,Long buyerId) {
		try {
			/** 发送MetaQ消息 */
			reserveOrderMetaOperateProducer.senderAnsy(shop, orderAddress, salesOrder, orderItemCommonList, buyerId);
		} catch (Exception e) {
			log.writeLog("*发送MetaQ预定订单消息任务埋点失败，订单号：" + salesOrder.getOrderNo(), e);
			/** 注意：此处异常不处理 */
		}
	}


	@Override
	public Map<String,Integer>  getShopSalesOrderCountInfo(String shopNo) {
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo(shopNo);
		Date date = new Date();
		salesOrderVo.setStartTime(DateUtil.getZeroPoint(date));
		return salesOrderService.getShopSalesOrderCountInfo(salesOrderVo);
	}

	@Override
	public Integer countBookOrder(String shopNo, Integer status) {
		try {
			AssertUtil.notNull(shopNo, "参数shopNo不能为空");
			AssertUtil.notNull(status, "参数status不能为空");
			return salesOrderService.countBookOrder(shopNo, status);
		} catch (Exception e) {
			throw new OrderException(e);
		}
	}

	@Override
	public Pagination<SalesOrder> findCustomerOrderInMyShop(String shopNo, Long customerId, Pagination<SalesOrder> pagination) {
		try {
			AssertUtil.notNull(shopNo, "参数shopNo不能为空");
			AssertUtil.notNull(customerId, "参数customerId不能为空");
			return this.salesOrderService.findCustomerOrderInMyShop(shopNo, customerId, pagination);
		} catch (Exception e) {
			log.writeLog("查询客户在我的总店订单失败!", e);
			throw new OrderException(e);
		}
	}

	@Override
	public void adjustOrderPriceNew(String orderNo, Long userId,
			Long adjustPrice) {
		AssertUtil.hasLength(orderNo,EC.prtNull("orderNo"));
		AssertUtil.notNull(userId, EC.prtNull("userId"));
		AssertUtil.notNull(adjustPrice, EC.prtNull("adjustPrice"));
		try {
			SalesOrder salesOrder = salesOrderService.getSalesOrderByOrderNo(orderNo);
			AssertUtil.notNull(salesOrder, "订单不存在,不能调价");
			AssertUtil.notNull(salesOrder.getStatus(), "订单不存在,不能调价");
			if(!Constants.STATUS_WAIT.equals(salesOrder.getStatus())){
				throw new OrderException("订单状态异常,不能调价");
			} else if (null != salesOrder.getPayStatus()) {
				throw new OrderException("支付处理中，不能调价");
			} 
			AssertUtil.notNull(salesOrder.getPrice(), "订单子项合计价格为空");
			
			//重新计算主订单价格
			Long oldAdjustPrice = salesOrder.getAdjustPrice();
			Long newTotalPrice = 0L;
			Long newAdjustPrice = 0L;
			if (oldAdjustPrice != null && !oldAdjustPrice.equals(0L)) {
				//说明之前改过价
				newTotalPrice = salesOrder.getTotalPrice().longValue() + adjustPrice.longValue();
				if (newTotalPrice.compareTo(0L) == -1) {
					throw new Exception("订单价格不能小于0!");
				}
				newAdjustPrice = oldAdjustPrice.longValue() + adjustPrice.longValue();
			} else {
				//之前未改过价
				CalculateOrder calculateOrder = new CalculateOrder(salesOrder.getPrice(),
						salesOrder.getPostFee(),salesOrder.getDiscountPrice(),adjustPrice);
				newTotalPrice = calculateOrder.calculate();
				newAdjustPrice = adjustPrice;
			}
			SalesOrder edit = new SalesOrder();
			edit.setOrderNo(orderNo);
			edit.setAdjustPrice(newAdjustPrice);
			edit.setTotalPrice(newTotalPrice);
			edit.setUpdateTime(new Date());
			if(edit.getTotalPrice() < Long.valueOf(PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"))){
				throw new OrderException("调整后订单总价最小值"+PropertiesUtil.getContexrtParam("ORDER_MIN_PRICE"));
			}
			salesOrderService.editSalesOrder(edit);
			orderRecordService.adjustPriceRecord(orderNo,1,edit.getTotalPrice(), adjustPrice, userId);
		} catch (Exception e) {
			throw new OrderException(e.getMessage(),e);
		}
	}
	
}
