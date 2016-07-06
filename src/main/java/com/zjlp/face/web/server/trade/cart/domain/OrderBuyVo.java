package com.zjlp.face.web.server.trade.cart.domain;

import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
/**
 * 订单提交参数对象
 * @ClassName: OrderBuyVo 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月11日 下午4:00:55
 */
public class OrderBuyVo {
	
	/** 收货地址ID */
	private Long addressId;
	
	/** 1.单商品购买;2.购物车购买 */
	private Integer buyType;
	/** 分店ID*/
	private Long subbranchId;
	/** 购买信息 */
	private List<OrderBuy> orderBuyList;
	/** 买家id */
	private Long userId;
	
	private List<SalesOrder> orderList;
	
	public Long getAddressId(){
		return addressId;
	}
	
	public void setAddressId(Long addressId){
		this.addressId = addressId;
	}
	
	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

	public Integer getBuyType(){
		return buyType;
	}
	
	public void setBuyType(Integer buyType){
		this.buyType = buyType;
	}

	public List<OrderBuy> getOrderBuyList(){
		return orderBuyList;
	}
	
	public List<OrderBuy> getOrderBuyList(Boolean temp){
		List<OrderBuy> list = new ArrayList<OrderBuy>();
		for (OrderBuy orderBuy : orderBuyList) {
			if(null != orderBuy.getBuyItemList()){
				list.add(orderBuy);
			}
		}
		return list;
	}

	public void setOrderBuyList(List<OrderBuy> orderBuyList){
		this.orderBuyList = orderBuyList;
	}

	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public List<SalesOrder> getOrderList(){
		if (null == orderList) {
			orderList = new ArrayList<SalesOrder>();
		}
		return orderList;
	}
	
	public void setOrderList(List<SalesOrder> orderList){
		this.orderList = orderList;
	}
}
