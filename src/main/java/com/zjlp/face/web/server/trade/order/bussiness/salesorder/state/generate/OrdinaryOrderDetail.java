package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.util.Assert;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.DiscountHandler;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.discount.HandlerManager;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.util.PrintLog;

public class OrdinaryOrderDetail extends OrderGenerate {
	
	private PrintLog log = new PrintLog("OrderInfoLog");
	//会员卡
	protected MemberCard memberCard;
	//优惠活动管理类
	protected HandlerManager handlerManager;
	//优惠券总抵价
	protected Long allCouponPrice;
	//积分总抵价
	protected Long allIntegralPrice;
	//抵扣的总积分
	protected Long allIntegral;
	//会员打折减免的总价格
	protected Long allMbDiscountPrice;
	//应付的订单价格(商品价格 - 各种抵价折扣)，不包含邮费
	protected Long price;
	
	public OrdinaryOrderDetail() {
		salesOrder = new SalesOrder();
		salesOrder.setStatus(Constants.STATUS_WAIT);//待付款
		salesOrder.setPayWay(Constants.PAY_WAY_ONLINE);//在线支付
		salesOrder.setFromInner(Constants.FROM_INNER_WAP);//来源：Wap
		//时间信息 
		createTime = new Date();
		salesOrder.setOrderTime(createTime);
		salesOrder.setCreateTime(createTime);
		salesOrder.setUpdateTime(createTime);
		salesOrder.setTimeoutTime(DateUtil.addDay(createTime, 2));
		//价格初始化
		salesOrder.setPrice(L_ZERO);
		salesOrder.setDiscountPrice(L_ZERO);
		salesOrder.setAdjustPrice(L_ZERO);
		//件数
		salesOrder.setQuantity(L_ZERO);
	}
	
	public OrdinaryOrderDetail(String orderNo) {
		this();
		salesOrder.setOrderNo(orderNo);
	}

	@Override
	public void execute() {
		
		//订单细项
		this.dealOrderItems();
		//物流
		this.initDeliver();
		//收货地址
		this.initAddress();
		//买家
		this.initBuyer();
		//卖家
		this.initSeller();
		//处理营销活动
		this.dealMarketingActivity();
		
		salesOrder.setOrderCategory(ORDER_TYPE_ORDINARY);
		salesOrder.setPicturePath(itemDetailList.get(0).good.getPicUrl());
		salesOrder.setPrice(super.allGoodPrice - 
				(null == allMbDiscountPrice ? 0L : allMbDiscountPrice));
		salesOrder.setTitle(itemList.get(0).getGoodName());
		salesOrder.setTotalPrice(this.calcuTotalPrice());
	}
	
	/**
	 * 营销活动数据统计
	 * @Title: dealMarketingActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年9月25日 上午10:11:32  
	 * @author lys
	 */
	private void dealMarketingActivity() {
		log.writeLog("开始处理营销活动统计信息！");
		//初始化
		allCouponPrice = L_ZERO;
		allIntegralPrice = L_ZERO;
		allMbDiscountPrice = L_ZERO;
		//统计
		for (OrderItemDetail itemDetail : itemDetailList) {
			//会员打折统计
			if (null != itemDetail.getMemberDiscountPrice()) {
				allMbDiscountPrice =  CalculateUtils.getSum(allMbDiscountPrice, itemDetail.getMemberDiscountPrice());
			}
			//优惠券统计
			if (null != itemDetail.getCouponPrice()) {
				allCouponPrice = CalculateUtils.getSum(allCouponPrice, itemDetail.getCouponPrice());
			}
			//积分抵扣统计
			if (null != itemDetail.getIntegralPrice()) {
				allIntegralPrice = CalculateUtils.getSum(allIntegralPrice, itemDetail.getIntegralPrice());
				allIntegral =  CalculateUtils.getSum(allIntegral, itemDetail.getIntegral());
			}
			//消费送积分统计 （不是分件统计，略）
		}
		salesOrder.setDiscountPrice(allCouponPrice + allIntegralPrice);
		salesOrder.setIntegral(allIntegral);
		log.writeLog("处理营销活动统计信息结束，总共优惠价格为：", salesOrder.getDiscountPrice(), "， 其中-》会员打折：", allMbDiscountPrice, "分，优惠券抵价：", allCouponPrice,
				"分，积分抵扣抵价：", allIntegralPrice, "分，使用积分个数：", allIntegral , "个.");
	}

	/**
	 * 订单细项处理
	 * @Title: dealOrderItems 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年9月25日 上午10:17:55  
	 * @author lys
	 */
	private void dealOrderItems() {
		log.writeLog("初始化订单细项");
		Assert.notEmpty(itemDetailList);
		OrderItem orderItem;
		//处理营销活动
		if (null != handlerManager) {
			handlerManager.handle(this);
		}
		//数量
		Long quantity = 0L;
		for (OrderItemDetail detail : itemDetailList) {
			orderItem = detail.generate(createTime);
			orderItem.setOrderNo(salesOrder.getOrderNo());//订单号
			
			/** 记录使用的优惠劵或积分情况 */
			if(null != detail.getCouponPrice()){
				orderItem.setCouponPrice(detail.getCouponPrice());
			}
			if(null != detail.getIntegralPrice()){
				orderItem.setIntegralPrice(detail.getIntegralPrice());
			}
			
			itemList.add(orderItem);
			quantity = CalculateUtils.getSum(quantity, orderItem.getQuantity());
		}
		salesOrder.setQuantity(quantity);
	}
	
	/**
	 * 应付的订单价格(商品价格 - 各种抵价折扣)，不包含邮费
	 * 
	 * @Title: getPrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月25日 上午11:34:19  
	 * @author lys
	 */
	public Long getPrice() {
		log.writeLog("计算订单价格（不包含邮费的价格）");
		price = super.allGoodPrice - salesOrder.getDiscountPrice() -
				(null == allMbDiscountPrice ? 0L : allMbDiscountPrice);
		log.writeLog("计算订单价格（不包含邮费的价格）完毕：", price);
		return price;
	}

	/**
	 * 买家基本信息初始化
	 * @Title: initSeller 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年9月25日 下午8:27:41  
	 * @author lys
	 */
	private void initSeller(){
		Assert.notNull(goodOwnerShop);
		Assert.notNull(goodOwner);
		Assert.notNull(seller);
		//卖家
		if (null != sellerShop) {
			salesOrder.setSellerRemoteId(sellerShop.getNo());
			salesOrder.setSellerRemoteType(TYPE_1);
		} else {
			salesOrder.setSellerRemoteId(seller.getCell());
			salesOrder.setSellerRemoteType(TYPE_2);
		}
		salesOrder.setSellerName(seller.getContacts() != null 
				? seller.getContacts() : seller.getLoginAccount());
		salesOrder.setSellerPhone(seller.getCell());
		//发货权限
		salesOrder.setDeliveryRemoteId(goodOwnerShop.getNo());
		salesOrder.setDeliveryRemoteType(TYPE_1);
		//商品所有者
		salesOrder.setShopNo(goodOwnerShop.getNo());
		salesOrder.setShopName(goodOwnerShop.getName());
		log.writeLog("卖家基本信息-》， 卖家姓名：", salesOrder.getSellerName(), "， 联系号码：", seller.getCell(),
				"卖家类型：", TYPE_1.equals(salesOrder.getSellerRemoteId()) ? "总店" : "分店",
			    "，卖家：", salesOrder.getSellerRemoteId(), "， 商品所属店铺：", salesOrder.getShopNo(),
			    "，发货权限：", TYPE_1.equals(salesOrder.getDeliveryRemoteId()) ? "总店" : "分店");
	}
	
	/**
	 * 买家基本信息初始化
	 * @Title: initBuyer 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年9月25日 下午8:27:27  
	 * @author lys
	 */
	private void initBuyer(){
		Assert.notNull(buyer);
		salesOrder.setUserId(buyer.getId());//用户信息
		salesOrder.setBuyerAccount(buyer.getLoginAccount());
		salesOrder.setBuyerNick(buyer.getNickname() != null 
				? buyer.getNickname() : buyer.getLoginAccount());
		salesOrder.setBuyerMessage(buyerMessage);
		log.writeLog("买家信息-》用户ID：", buyer.getId(), "， 账号：",
				buyer.getId(), "，用户名：", salesOrder.getBuyerNick(), "， 预留信息为：", buyerMessage);
	}

	/**
	 * 买家收货地址信息初始化
	 * @Title: initAddress 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年9月25日 下午8:27:09  
	 * @author lys
	 */
	private void initAddress() {
		Assert.notNull(address);
		Assert.notNull(vareaDto);
		salesOrder.setReceiverName(address.getName());
		salesOrder.setReceiverPhone(address.getCell());
		salesOrder.setReceiverAddress(address.getAddressDetail());
		salesOrder.setvAreaCode(address.getvAreaCode());
		salesOrder.setReceiverState(vareaDto.getProvinceName());
		salesOrder.setReceiverCity(vareaDto.getCityName());
		salesOrder.setReceiverDistrict(vareaDto.getAreaName());
		log.writeLog("收货人姓名：", address.getName(), "， 联系号码：", address.getCell(), 
				"， 详细地址为：", address.getAddressDetail());
	}
	
	/**
	 * 物流信息初始化
	 * @Title: initDeliver 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年9月25日 上午11:08:28  
	 * @author lys
	 */
	private void initDeliver() {
		log.writeLog("初始化物流信息：deliveryWay="+deliveryWay);
		Assert.notNull(deliveryWay);
		if (3 == deliveryWay.intValue()) {
			Assert.notNull(pickUpStore);
			salesOrder.setPickUpAddress(pickUpStore.getAddress());
			salesOrder.setPickUpPhone(pickUpStore.getPhone());
		} else if (2 == deliveryWay.intValue()) {
			Assert.notNull(shopDistribution);
			salesOrder.setDeliveryRange(shopDistribution.getDistributionRange());
		}
		salesOrder.setDeliveryWay(deliveryWay);
		if(1 == deliveryWay){
			salesOrder.setHasPostFee(1);
			// 快递配送计算运费
			salesOrder.setPostFee(this.calculatePostFee());
			log.writeLog("该订单的邮费为", salesOrder.getPostFee(), "，好便宜不是，上帝欢呼吧！");
		}else{
			salesOrder.setHasPostFee(0);
			salesOrder.setPostFee(L_ZERO);
			log.writeLog("该订单为免邮订单，上帝欢呼吧！");
		}
	}
	
	/**
	 * 计算应付价格  总价  + 邮费 - 优惠价
	 * @Title: calcuTotalPrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月25日 上午11:04:29  
	 * @author lys
	 * @param long1 
	 */
	private Long calcuTotalPrice() {
		log.writeLog("开始统计订单应付金额（公式：优惠价格 + 邮费， 单位：分 ）");
		Long postFee = null != salesOrder.getPostFee() ? salesOrder.getPostFee() : L_ZERO;
		BigDecimal totalPrice = new BigDecimal(this.getPrice()).add(new BigDecimal(postFee));
		log.writeLog("计算订单应付金额完毕，总额为：", totalPrice);
		return totalPrice.longValue();
	}
	
	//邮费计算
	public Long calculatePostFee(){
		log.writeLog("计算邮费");
		//无邮费状态
		if (1 != deliveryWay) {
			return L_ZERO;
		}
		String code = address.getvAreaCode() / 10000 + "0000";
		DeliveryTemplateItemDto item = null;
		if(null != deliveryTemplate){
			for(DeliveryTemplateItemDto dto : deliveryTemplate.getItemList()){
				if(DeliveryTemplateItemDto.ALL.equals(dto.getDestination())){
					item = dto;
				}else if(-1 != dto.getDestination().indexOf(code)){
					item = dto;
					break;
				}
			}
		}
		Long maxUnifyFee = L_ZERO;
		int quantity = 0;
		for(OrderItemDetail detail : itemDetailList){
			if(1 == detail.good.getLogisticsMode()){
				quantity += detail.quantity;
			}else if(2 == detail.good.getLogisticsMode()){
				if(maxUnifyFee< detail.good.getPostFee()){
					maxUnifyFee= detail.good.getPostFee();
				}
			}
		}
		Long temFee = L_ZERO;
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
	public OrderGenerate withMemberCard(MemberCard memberCard){
		this.memberCard = memberCard;
		return this;
	}
	public MemberCard getMemberCard(){
		return this.memberCard;
	}
	public OrderGenerate addDiscountHandler(DiscountHandler handler){
		if (null == this.handlerManager) {
			this.handlerManager = new HandlerManager();
		}
		this.handlerManager.append(handler);
		return this;
	}
	public Long getAllCouponPrice() {
		return allCouponPrice;
	}
	public void setAllCouponPrice(Long allCouponPrice) {
		this.allCouponPrice = allCouponPrice;
	}
	public Long getAllIntegralPrice() {
		return allIntegralPrice;
	}
	public void setAllIntegralPrice(Long allIntegralPrice) {
		this.allIntegralPrice = allIntegralPrice;
	}
	public Long getAllMbDiscountPrice() {
		return allMbDiscountPrice;
	}
	public void setAllMbDiscountPrice(Long allMbDiscountPrice) {
		this.allMbDiscountPrice = allMbDiscountPrice;
	}

	public Long getAllIntegral() {
		return allIntegral;
	}
}
