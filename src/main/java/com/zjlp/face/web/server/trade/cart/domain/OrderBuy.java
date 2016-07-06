package com.zjlp.face.web.server.trade.cart.domain;

import java.util.List;

public class OrderBuy implements Comparable<OrderBuy> {
	
	/** 不使用积分抵扣 */
	public static final Integer NOT_DEDUCTION = -1;
	
	/** 商品所属店铺号 */
	private String shopNo;

	/** 购买店铺号 */     
	String buyShopNo;
	
	/** 积分抵扣序号 默认-1 表示不使用积分抵扣 记录积分抵扣时用户开启顺序*/
	private Integer deductionIndex = -1;
	
	/** 优惠劵ID */
	private Long couponId;
	
	/** 配送方式（1 快递配送；2 店铺配送；3 门店自取；） */
	private Integer deliveryWay;
	
	/** 自提点 */
	private Long pickUpId;
	
	/** 买家下单时留言 */
	private String buyerMessage;
	
	/** 购买商品信息 */
	private List<BuyItem> buyItemList;
	
	public String getShopNo(){
		return shopNo;
	}
	
	public void setShopNo(String shopNo){
		this.shopNo = shopNo;
	}
	
	public String getBuyShopNo() {
		return buyShopNo;
	}

	public void setBuyShopNo(String buyShopNo) {
		this.buyShopNo = buyShopNo;
	}

	public Integer getDeductionIndex() {
		return deductionIndex;
	}

	public void setDeductionIndex(Integer deductionIndex) {
		this.deductionIndex = deductionIndex;
	}

	public Integer getDeliveryWay(){
		return deliveryWay;
	}
	
	public void setDeliveryWay(Integer deliveryWay){
		this.deliveryWay = deliveryWay;
	}
	
	public String getBuyerMessage(){
		return buyerMessage;
	}
	
	public void setBuyerMessage(String buyerMessage){
		this.buyerMessage = buyerMessage;
	}
	
	public List<BuyItem> getBuyItemList(){
		return buyItemList;
	}
	
	public void setBuyItemList(List<BuyItem> buyItemList){
		this.buyItemList = buyItemList;
	}
	
	public Long getPickUpId(){
		return pickUpId;
	}
	
	public void setPickUpId(Long pickUpId){
		this.pickUpId = pickUpId;
	}

	@Override
	public int compareTo(OrderBuy o) {
		if(null==o || null == o.getDeductionIndex() || null==this.deductionIndex)return 0;
		return this.deductionIndex-o.getDeductionIndex();
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
}
