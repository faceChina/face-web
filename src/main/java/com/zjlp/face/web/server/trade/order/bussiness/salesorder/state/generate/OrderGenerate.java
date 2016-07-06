package com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.AbstractOrderState;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;


public abstract class OrderGenerate extends AbstractOrderState {

	protected static final Integer ORDER_TYPE_ORDINARY = 1;  //普通订单
	protected static final Integer ORDER_TYPE_APPOINT = 3;  //预约订单
	protected static final Integer ORDER_TYPE_AGENCY = 4;   //代理订单
	
	protected static final Long L_ZERO = 0L;
	protected static final Integer TYPE_1 = 1;
	protected static final Integer TYPE_2 = 2;

	protected Address address;
	protected DeliveryTemplateDto deliveryTemplate;
	protected VaearDto vareaDto;
	protected Integer deliveryWay;
	protected PickUpStore pickUpStore;
	protected ShopDistribution shopDistribution;
	protected User buyer;
	protected String buyerMessage;
	protected User seller;
	protected Shop sellerShop;
	protected User goodOwner;
	protected Shop goodOwnerShop;
	protected List<OrderItemDetail> itemDetailList = new ArrayList<OrderItemDetail>();
	protected List<OrderItem> itemList = new ArrayList<OrderItem>();
	protected List<Good> allGoods = new ArrayList<Good>();
	//创建时间
	protected Date createTime;
	//总的商品价格
	protected Long allGoodPrice = L_ZERO;
	
	@Override
	protected void check() throws Exception {
		//DO NOTHING
	}
	
	public OrderGenerate withAddress(Address address, VaearDto vaearDto) {
		this.address = address;
		this.vareaDto = vaearDto;
		return this;
	}
	public OrderGenerate withDeliverWay(Integer deliveryWay, PickUpStore store){
		this.deliveryWay = deliveryWay;
		this.pickUpStore = store;
		return this;
	}
	public OrderGenerate withDeliverWay(Integer deliveryWay, ShopDistribution distribution){
		this.deliveryWay = deliveryWay;
		this.shopDistribution = distribution;
		return this;
	}
	public OrderGenerate withDeliverWay(Integer deliveryWay){
		this.deliveryWay = deliveryWay;
		return this;
	}
	public OrderGenerate withBuyer(User user, String buyerMessage){
		this.buyer = user;
		this.buyerMessage = buyerMessage;
		return this;
	}
	public OrderGenerate withSeller(User seller, Shop sellerShop){
		this.seller = seller;
		this.sellerShop = sellerShop;
		return this;
	}
	public OrderGenerate withGoodOwner(User goodOwner, Shop goodOwnerShop){
		this.goodOwner = goodOwner;
		this.goodOwnerShop = goodOwnerShop;
		return this;
	}
	public OrderGenerate addItemDetail(OrderItemDetail itemDetail){
		this.itemDetailList.add(itemDetail);
		this.allGoods.add(itemDetail.good);
		//总的商品价格计算
		this.allGoodPrice = CalculateUtils.getSum(allGoodPrice, itemDetail.getGoodPrice());
		return this;
	}
	public OrderGenerate withDeliveryTemplate(DeliveryTemplateDto deliveryTemplate){
		this.deliveryTemplate = deliveryTemplate;
		return this;
	}
	public List<OrderItem> getItemList() {
		return itemList;
	}
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}
	public List<OrderItemDetail> getItemDetailList() {
		return itemDetailList;
	}
	public Long getAllGoodPrice() {
		return allGoodPrice;
	}

	public User getBuyer() {
		return buyer;
	}

	public User getSeller() {
		return seller;
	}
}
