package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;

public class AssSalesOrderVo  implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 3776841104341308744L;
	
	/** 父订单的交易编号 */
	private String orderNo;
	/**
	 * 交易状态(0、 生成 01、待付款 02、已付款 03、已发货 04 已收货 05、交易成功 10、已取消 11、已删除 20、退订 30
	 * 超时关闭)
	 */
	private Integer status;
	/** 1,普通订单;2,协议订单 */
	private Integer orderCategory;
	/** 交易流水号 */
	private String transactionSerialNumber;
	/** 交易标题 */
	private String title;
	/** 交易内部来源（wap 或.Android 或ios） */
	private Integer fromInner;
	
	/** 商品购买数量 （取值范围：大于零的整数） */
	private Long quantity;
	
	/** 商品主图地址 */
	private String goodPicturePath;
	
	/** 商品总金额(单位：分) */
	private Long price;
	
	private String priceStr;
	
	/** 总金额（单位：分 取子订单应付金额之合+邮费） */
	private Long totalPrice;
	private String totalPriceStr;
	
	/** 实付金额(单位：分) */
	private Long payPrice;
	private String payPriceStr;
	
	/** 商品优惠总金额(单位：分) */
	private Long discountPrice;
	private String discountPriceStr;
	
	/** 买家/用户ID(FK) */
	private Long userId;
	
	/** 买家账户 */
	private String buyerAccount;
	
	/** 买家昵称 */
	private String buyerNick;
	
	/** 买家下单时留言 */
	private String buyerMessage;
	
	/** 买家备注：只有买家才能看到 */
	private String buyerMemo;
	
	/** 购买店铺号(FK) */
	private String shopNo;
	
	/** 购买店铺名称 */
	private String shopName;
	
	/** 卖家姓名 */
	private String sellerName;
	
	/** 卖家联系方式 */
	private String sellerPhone;
	
	/** 卖家备注：只有卖家才能看到 */
	private String sellerMemo;
	
	/** 支付方式(1 在线支付 2.线下支付 3 货到付款（预留） ) */
	private Integer payWay;
	
	/** 支付渠道 ：1.银行卡付款 2.钱包付款 */
	private Integer payChannel;
	
	/** 付款类型(1 单笔支付 2 合并支付 ) */
	private Integer payType;
	
	/** 付款银行卡号 */
	private Long bankCard;
	
	private String openId;
	
	/** 配送方式（1 快递配送；2 店铺配送；3 门店自取；） */
	private Integer deliveryWay;
	
	/** 配送范围 (店铺配送时使用) */
	private String deliveryRange;
	
	/** 自提联系方式 */
	private String pickUpPhone;
	
	/** 自提地址（门店自取时使用） */
	private String pickUpAddress;
	
	/** 是否包含邮费 */
	private Integer hasPostFee;
	
	/** 邮费（单位：分） */
	private Long postFee;
	
	/** 收货人姓名 */
	private String receiverName;
	
	/** 收货人电话 */
	private String receiverPhone;
	
	/** 收货区域ID */
	private Integer vAreaCode;
	
	/** 收货人所在省份 */
	private String receiverState;
	
	/** 收货人的所在地区 */
	private String receiverDistrict;
	
	/** 收货人的所在城市 */
	private String receiverCity;
	
	/** 收货人详细地址 */
	private String receiverAddress;
	
	/** 送货人姓名 */
	private String senderName;
	
	/** 送货人电话 */
	private String senderCell;
	
	/** 快递公司 */
	private String deliveryCompany;
	
	/** 快递单号 */
	private String deliverySn;
	
	/** 订购时间 */
	private Date orderTime;
	private Long orderTimeStamp;
	/** 支付时间 */
	private Date paymentTime;
	private Long paymentTimeStamp;
	/** 发货时间 */
	private Date deliveryTime;
	private Long deliveryTimeStamp;
	/** 确认时间 */
	private Date confirmTime;
	private Long confirmTimeStamp;
	/** 关闭时间 */
	private Date closingTime;
	private Long closingTimeStamp;
	/** 取消时间 */
	private Date cancelTime;
	private Long cancelTimeStamp;
	
	/** 删除时间 */
	private Date deleteTime;
	private Long deleteTimeStamp;
	
	/** 超时到期时间(订单一定时间内付款，超时时间) */
	private Date timeoutTime;
	private Long timeoutTimeStamp;
	
	/** 创建时间 */
	private Date createTime;
	private Long createTimeStamp;
	/** 更新时间 */
	private Date updateTime;
	private Long updateTimeStamp;
	
	/** solr更新时间 */
	private Date solrTime;
	/**收货人地址信息：省 市地区街道**/
	private String receiverAddressInfo;
	
	private Long adjustPrice;
	
	/** 积分抵扣时使用的积分(单位：个) 记录使用，生成后将不会改变  */
	private Long integral; 
	
	/**推广支出**/
	private Long promoteSpend;
	
	/** 发货者id（如果为p时，为店铺号，如果为bf1时，手机号） */
	private String deliveryRemoteId;
	/** 发货者类型（1.店铺号，2.手机号） */
	private Integer deliveryRemoteType;
	/**有误发货权限 0 没有 1 有**/
	private Integer isDeliver;
	/**有无修改价格权限**/
	private Integer isModifyPrice;	
	/**
	 * 预约订单拒绝原因
	 */
	private String refuseReason;
	/**预约时间*/
	private String appointmentTime;
	/***/
	private List<OrderItem> itemList;
	
	public Long getAdjustPrice() {
		return adjustPrice;
	}
	public void setAdjustPrice(Long adjustPrice) {
		this.adjustPrice = adjustPrice;
	}

	/** 商品名 **/
	private String goodName;
	public AssSalesOrderVo() {
		super();
	}
	public AssSalesOrderVo(SalesOrder order) {
		super();
		this.orderNo = order.getOrderNo();
		this.status = order.getStatus();
		this.orderCategory = order.getOrderCategory();
		this.transactionSerialNumber = order.getTransactionSerialNumber();
		this.title = order.getTitle();
		this.fromInner = order.getFromInner();
		this.quantity = order.getQuantity();
		this.goodPicturePath = order.getPicturePath();
		this.setPrice(order.getPrice());
		this.setTotalPrice(order.getTotalPrice());
		this.setPayPrice(order.getPayPrice());
		this.setDiscountPrice(order.getDiscountPrice());
		this.userId = order.getUserId();
		this.buyerAccount = order.getBuyerAccount();
		this.buyerNick = order.getBuyerNick();
		this.buyerMessage = order.getBuyerMessage();
		this.buyerMemo = order.getBuyerMemo();
		this.shopNo = order.getShopNo();
		this.shopName = order.getShopName();
		this.sellerName = order.getSellerName();
		this.sellerPhone = order.getSellerPhone();
		this.sellerMemo = order.getSellerMemo();
		this.payWay = order.getPayWay();
		this.payChannel = order.getPayChannel();
		this.payType = order.getPayType();
		this.bankCard = order.getBankCard();
		this.openId = order.getOpenId();
		this.deliveryWay = order.getDeliveryWay();
		this.deliveryRange = order.getDeliveryRange();
		this.pickUpPhone = order.getPickUpPhone();
		this.pickUpAddress = order.getPickUpAddress();
		this.hasPostFee = order.getHasPostFee();
		this.postFee = order.getPostFee();
		this.receiverName = order.getReceiverName();
		this.receiverPhone = order.getReceiverPhone();
		this.vAreaCode = order.getvAreaCode();
		this.receiverState = order.getReceiverState();
		this.receiverDistrict = order.getReceiverDistrict();
		this.receiverCity = order.getReceiverCity();
		this.receiverAddress = order.getReceiverAddress();
		this.senderName = order.getSenderName();
		this.senderCell = order.getSenderCell();
		this.deliveryCompany = order.getDeliveryCompany();
		this.deliverySn = order.getDeliverySn();
		this.setOrderTime(order.getOrderTime());
		this.setPaymentTime(order.getPaymentTime());
		this.setDeliveryTime(order.getDeliveryTime());
		this.setConfirmTime(order.getConfirmTime());
		this.setClosingTime(order.getClosingTime());
		this.setCancelTime(order.getCancelTime());
		this.setDeleteTime(order.getDeleteTime());
		this.setTimeoutTime(order.getTimeoutTime());
		this.setCreateTime(order.getCreateTime());
		this.setUpdateTime(order.getUpdateTime());
		this.solrTime = order.getSolrTime();
		this.setAdjustPrice(order.getAdjustPrice());
		this.setIntegral(order.getIntegral());
		this.setPromoteSpend(order.getPromoteSpend());
		this.setDeliveryRemoteId(order.getDeliveryRemoteId());
		this.setDeliveryRemoteType(order.getDeliveryRemoteType());
		this.setRefuseReason(order.getRefuseReason());
		this.setGoodName(order.getTitle());
	}
	
	public AssSalesOrderVo(SalesOrderDto order) {
		super();
		this.orderNo = order.getOrderNo();
		this.status = order.getStatus();
		this.orderCategory = order.getOrderCategory();
		this.transactionSerialNumber = order.getTransactionSerialNumber();
		this.title = order.getTitle();
		this.fromInner = order.getFromInner();
		this.quantity = order.getQuantity();
		this.goodPicturePath = order.getPicturePath();
		this.setPrice(order.getPrice());
		this.setTotalPrice(order.getTotalPrice());
		this.setPayPrice(order.getPayPrice());
		this.setDiscountPrice(order.getDiscountPrice());
		this.userId = order.getUserId();
		this.buyerAccount = order.getBuyerAccount();
		this.buyerNick = order.getBuyerNick();
		this.buyerMessage = order.getBuyerMessage();
		this.buyerMemo = order.getBuyerMemo();
		this.shopNo = order.getShopNo();
		this.shopName = order.getShopName();
		this.sellerName = order.getSellerName();
		this.sellerPhone = order.getSellerPhone();
		this.sellerMemo = order.getSellerMemo();
		this.payWay = order.getPayWay();
		this.payChannel = order.getPayChannel();
		this.payType = order.getPayType();
		this.bankCard = order.getBankCard();
		this.openId = order.getOpenId();
		this.deliveryWay = order.getDeliveryWay();
		this.deliveryRange = order.getDeliveryRange();
		this.pickUpPhone = order.getPickUpPhone();
		this.pickUpAddress = order.getPickUpAddress();
		this.hasPostFee = order.getHasPostFee();
		this.postFee = order.getPostFee();
		this.receiverName = order.getReceiverName();
		this.receiverPhone = order.getReceiverPhone();
		this.vAreaCode = order.getvAreaCode();
		this.receiverState = order.getReceiverState();
		this.receiverDistrict = order.getReceiverDistrict();
		this.receiverCity = order.getReceiverCity();
		this.receiverAddress = order.getReceiverAddress();
		this.senderName = order.getSenderName();
		this.senderCell = order.getSenderCell();
		this.deliveryCompany = order.getDeliveryCompany();
		this.deliverySn = order.getDeliverySn();
		this.setOrderTime(order.getOrderTime());
		this.setPaymentTime(order.getPaymentTime());
		this.setDeliveryTime(order.getDeliveryTime());
		this.setConfirmTime(order.getConfirmTime());
		this.setClosingTime(order.getClosingTime());
		this.setCancelTime(order.getCancelTime());
		this.setDeleteTime(order.getDeleteTime());
		this.setTimeoutTime(order.getTimeoutTime());
		this.setCreateTime(order.getCreateTime());
		this.setUpdateTime(order.getUpdateTime());
		this.solrTime = order.getSolrTime();
		this.setAdjustPrice(order.getAdjustPrice());
		this.setIntegral(order.getIntegral());
		this.setPromoteSpend(order.getPromoteSpend());
		this.setDeliveryRemoteId(order.getDeliveryRemoteId());
		this.setDeliveryRemoteType(order.getDeliveryRemoteType());
		this.setRefuseReason(order.getRefuseReason());
		this.appointmentTime = order.getOrderAppointmentDetails().get(0).getAttrValue().replace(",", " ");
		this.setItemList(order.getOrderItemList());
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(Integer orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getFromInner() {
		return fromInner;
	}

	public void setFromInner(Integer fromInner) {
		this.fromInner = fromInner;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getGoodPicturePath() {
		return goodPicturePath;
	}

	public void setGoodPicturePath(String goodPicturePath) {
		this.goodPicturePath = goodPicturePath;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
		if (null != price){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.priceStr = df.format(price/100.0);
		}else{
			this.priceStr = "0.00";
		}
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
		if (null != totalPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.totalPriceStr = df.format(totalPrice/100.0);
		}else{
			this.totalPriceStr = "0.00";
		}
	}

	public Long getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Long payPrice) {
		this.payPrice = payPrice;
		if (null != payPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.payPriceStr = df.format(payPrice/100.0);
		}else{
			this.payPriceStr = "0.00";
		}
	}

	public Long getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
		if (null != discountPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.discountPriceStr = df.format(discountPrice/100.0);
		}else{
			this.discountPriceStr = "0.00";
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getBuyerMessage() {
		if(null == buyerMessage){
			return  "";
		}else{
			return buyerMessage;
		}
		
	}

	public void setBuyerMessage(String buyerMessage) {
			this.buyerMessage = buyerMessage;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		
		if(null == buyerMemo){
			this.buyerMemo = "";
		}else{
			this.buyerMemo = buyerMemo;
		}
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Long getBankCard() {
		return bankCard;
	}

	public void setBankCard(Long bankCard) {
		this.bankCard = bankCard;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(Integer deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getDeliveryRange() {
		return deliveryRange;
	}

	public void setDeliveryRange(String deliveryRange) {
		this.deliveryRange = deliveryRange;
	}

	public String getPickUpPhone() {
		return pickUpPhone;
	}

	public void setPickUpPhone(String pickUpPhone) {
		this.pickUpPhone = pickUpPhone;
	}

	public String getPickUpAddress() {
		return pickUpAddress;
	}

	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
	}

	public Integer getHasPostFee() {
		return hasPostFee;
	}

	public void setHasPostFee(Integer hasPostFee) {
		this.hasPostFee = hasPostFee;
	}

	public Long getPostFee() {
		return postFee;
	}

	public void setPostFee(Long postFee) {
		this.postFee = postFee;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Integer getvAreaCode() {
		return vAreaCode;
	}

	public void setvAreaCode(Integer vAreaCode) {
		this.vAreaCode = vAreaCode;
	}

	public String getReceiverState() {
		return receiverState;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderCell() {
		return senderCell;
	}

	public void setSenderCell(String senderCell) {
		this.senderCell = senderCell;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getDeliverySn() {
		return deliverySn;
	}

	public void setDeliverySn(String deliverySn) {
		this.deliverySn = deliverySn;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
		if (null != orderTime){
			this.orderTimeStamp = orderTime.getTime();
		}
	}

	public Long getOrderTimeStamp() {
		return orderTimeStamp;
	}

	public void setOrderTimeStamp(Long orderTimeStamp) {
		this.orderTimeStamp = orderTimeStamp;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
		if (null != paymentTime){
			this.paymentTimeStamp = paymentTime.getTime();
		}
	}

	public Long getPaymentTimeStamp() {
		return paymentTimeStamp;
	}

	public void setPaymentTimeStamp(Long paymentTimeStamp) {
		this.paymentTimeStamp = paymentTimeStamp;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
		if (null != deliveryTime){
			this.deliveryTimeStamp = deliveryTime.getTime();
		}
	}

	public Long getDeliveryTimeStamp() {
		return deleteTimeStamp;
	}

	public void setDeliveryTimeStamp(Long deliveryTimeStamp) {
		this.deliveryTimeStamp = deliveryTimeStamp;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
		if (null != confirmTime){
			this.confirmTimeStamp = confirmTime.getTime();
		}
	}

	public Long getConfirmTimeStamp() {
		return confirmTimeStamp;
	}

	public void setConfirmTimeStamp(Long confirmTimeStamp) {
		this.confirmTimeStamp = confirmTimeStamp;
	}

	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
		if (null != closingTime){
			this.closingTimeStamp = closingTime.getTime();
		}
	}

	public Long getClosingTimeStamp() {
		return closingTimeStamp;
	}

	public void setClosingTimeStamp(Long closingTimeStamp) {
		this.closingTimeStamp = closingTimeStamp;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		if (null != cancelTime){
			this.cancelTimeStamp = cancelTime.getTime();
		}
	}

	public Long getCancelTimeStamp() {
		return cancelTimeStamp;
	}

	public void setCancelTimeStamp(Long cancelTimeStamp) {
		this.cancelTimeStamp = cancelTimeStamp;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
		if (null != deleteTime){
			this.deleteTimeStamp = deleteTime.getTime();
		}
	}

	public Long getDeleteTimeStamp() {
		return deleteTimeStamp;
	}

	public void setDeleteTimeStamp(Long deleteTimeStamp) {
		this.deleteTimeStamp = deleteTimeStamp;
	}

	public Date getTimeoutTime() {
		return timeoutTime;
	}

	public void setTimeoutTime(Date timeoutTime) {
		this.timeoutTime = timeoutTime;
		if (null != timeoutTime){
			this.timeoutTimeStamp = timeoutTime.getTime();
		}
	}

	public Long getTimeoutTimeStamp() {
		return timeoutTimeStamp;
	}

	public void setTimeoutTimeStamp(Long timeoutTimeStamp) {
		this.timeoutTimeStamp = timeoutTimeStamp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if (null != createTime){
			this.createTimeStamp = createTime.getTime();
		}
	}

	public Long getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		if (null != updateTime){
			this.updateTimeStamp = updateTime.getTime();
		}
	}

	public Long getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Long updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public Date getSolrTime() {
		return solrTime;
	}

	public void setSolrTime(Date solrTime) {
		this.solrTime = solrTime;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public String getTotalPriceStr() {
		return totalPriceStr;
	}
	public void setTotalPriceStr(String totalPriceStr) {
		this.totalPriceStr = totalPriceStr;
	}
	public String getPayPriceStr() {
		return payPriceStr;
	}
	public void setPayPriceStr(String payPriceStr) {
		this.payPriceStr = payPriceStr;
	}
	public String getDiscountPriceStr() {
		return discountPriceStr;
	}
	public void setDiscountPriceStr(String discountPriceStr) {
		this.discountPriceStr = discountPriceStr;
	}
	public String getReceiverAddressInfo() {
		String address = this.getReceiverState()+this.getReceiverCity()+this.getReceiverDistrict()+this.getReceiverAddress();
		return address;
	}
	public void setReceiverAddressInfo(String receiverAddressInfo) {
		this.receiverAddressInfo = receiverAddressInfo;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public Long getPromoteSpend() {
		return promoteSpend;
	}
	public void setPromoteSpend(Long promoteSpend) {
		this.promoteSpend = promoteSpend;
	}
	public String getDeliveryRemoteId() {
		return deliveryRemoteId;
	}
	public void setDeliveryRemoteId(String deliveryRemoteId) {
		this.deliveryRemoteId = deliveryRemoteId;
	}
	public Integer getDeliveryRemoteType() {
		return deliveryRemoteType;
	}
	public void setDeliveryRemoteType(Integer deliveryRemoteType) {
		this.deliveryRemoteType = deliveryRemoteType;
	}
	public Integer getIsDeliver() {
		return isDeliver;
	}
	public void setIsDeliver(Integer isDeliver) {
		this.isDeliver = isDeliver;
	}
	public Integer getIsModifyPrice() {
		return isModifyPrice;
	}
	public void setIsModifyPrice(Integer isModifyPrice) {
		this.isModifyPrice = isModifyPrice;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		if (null == refuseReason){
			this.refuseReason = "";
		}else{
			this.refuseReason = refuseReason;
		}
	}
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public List<OrderItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
}
