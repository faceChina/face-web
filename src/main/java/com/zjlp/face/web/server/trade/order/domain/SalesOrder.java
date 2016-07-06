package com.zjlp.face.web.server.trade.order.domain;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.constants.Constants;

public class SalesOrder implements Serializable, Cloneable {

	private static final long serialVersionUID = -7121834012981049703L;

	/** 父订单的交易编号 */
	private String orderNo;

	/**
	 * 交易状态(0、 生成 01、待付款 02、已付款 03、已发货 04 已收货 05、交易成功 10、已取消 11、已删除 20、退订 30
	 * 超时关闭 ,预约状态(40 待确认 41 卖家已确认 42 卖家已拒绝 43 用户已取消))
	 */
	private Integer status;

	/** 1,普通订单;2,协议订单 ;3,预约订单 */
	private Integer orderCategory;

	/** 交易流水号 */
	private String transactionSerialNumber;

	/** 交易标题 */
	private String title;

	/** 交易内部来源（wap 或.Android 或ios） */
	private Integer fromInner;

	/** 商品购买数量 （取值范围：大于零的整数） */
	private Long quantity;

	/** 商品总金额(单位：分) */
	private Long price;

	/** 总金额（单位：分 取子订单应付金额之合+邮费） */
	private Long totalPrice;

	/** 实付金额(单位：分) */
	private Long payPrice;

	/** 商品优惠总金额(单位：分) */
	private Long discountPrice;
	/** 积分抵扣时使用的积分(单位：个) 记录使用，生成后将不会改变 */
	private Long integral;

	/** 积分抵扣时实际使用的积分 支付完成后写入，待付款状态下该值为0 */
	private Long realIntegral;

	/** 消费送积分将获得的积分(单位：个)，交易成功后才能获得 */
	private Long obtainIntegral;

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

	/** 直接卖家id（有店铺为店铺号，没有店铺时，为手机号） */
	private String sellerRemoteId;
	/** 直接卖家类型（1.店铺，2.手机账号） */
	private Integer sellerRemoteType;

	/** 商品所属店铺 */
	private String shopNo;

	/** 商品所属店铺 */
	private String shopName;

	/** 卖家姓名 */
	private String sellerName;

	/** 卖家联系方式 ，如果是预约订单，这里为商家联系方式 */
	private String sellerPhone;

	/** 预约订单的预约地址 */
	private Long appointmentId;
	/**
	 * 预约订单的预约地址
	 */
	private String appointmentAddress;

	/** 卖家备注：只有卖家才能看到 */
	private String sellerMemo;

	/** 支付方式(1 在线支付 2.线下支付 3 货到付款（预留） ) */
	private Integer payWay;

	/** 支付渠道 ：1.银行卡付款 2.钱包付款 3.微信支付 4.会员卡支付 5.支付宝支付*/
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

	/**
	 * 人工调整金额
	 */
	private Long adjustPrice;

	/**
	 * 收货人姓名 注：如果是预约订单，此字段记录预约人姓名
	 */
	private String receiverName;

	/**
	 * 收货人电话 注：如果是预约订单，此字段记录预约人电话
	 */
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
	/** 发货者id（如果为p时，为店铺号，如果为bf1时，手机号） */
	private String deliveryRemoteId;
	/** 发货者类型（1.店铺号，2.手机号） */
	private Integer deliveryRemoteType;

	/** 快递公司 */
	private String deliveryCompany;

	/** 快递单号 */
	private String deliverySn;

	/** 订购时间 */
	private Date orderTime;

	/** 支付时间 */
	private Date paymentTime;

	/** 发货时间 */
	private Date deliveryTime;

	/** 收货时间 */
	private Date receiveTime;

	/** 关闭时间 */
	private Date closingTime;

	/** 取消时间 (预约订单表示买家取消时间) */
	private Date cancelTime;

	/** 删除时间 */
	private Date deleteTime;

	/** 超时到期时间(订单一定时间内付款，超时时间) */
	private Date timeoutTime;

	/** 创建时间 */
	private Date createTime;

	/** 创建时间 */
	private Date updateTime;

	/** solr更新时间 */
	private Date solrTime;

	/**
	 * 预约的商品或服务的名称
	 */
	private String appointmentName;

	/** 图片路径 */
	private String picturePath;

	/**
	 * 预约时间
	 */
	private Date appointmentTime;

	/**
	 * 拒绝时间
	 */
	private Date refuseTime;

	/**
	 * 预约订单拒绝原因
	 */
	private String refuseReason;

	/**
	 * 预约订单确认订单时间,订单确认时间
	 */
	private Date confirmTime;

	/**
	 * 1银商已下单2银商受理中3支付成功4支付失败
	 */
	private Integer payStatus;
	// 推广总佣金
	private Long promoteSpend;
	// 检索关键字
	private String searchKey;

	/**
	 * 积分抵扣率(0~100:0为没有抵扣)
	 * 用于还原运费抵扣了多少积分
	 */
	private Integer deductRate;
	
	/** 使用优惠券抵价的价格 */
	private Long couponPrice;
	
	public Long getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(Long couponPrice) {
		this.couponPrice = couponPrice;
	}
	public Integer getDeductRate() {
		return deductRate;
	}
	public void setDeductRate(Integer deductRate) {
		this.deductRate = deductRate;
	}

	public Long getPromoteSpend() {
		return promoteSpend;
	}

	public void setPromoteSpend(Long promoteSpend) {
		this.promoteSpend = promoteSpend;
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Long payPrice) {
		this.payPrice = payPrice;
	}

	public Long getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBuyerAccount() {
		if (null != this.buyerAccount && this.buyerAccount.length() > 11) {
			this.buyerAccount = this.getBuyerNick();
		}
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
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Date getTimeoutTime() {
		return timeoutTime;
	}

	public void setTimeoutTime(Date timeoutTime) {
		this.timeoutTime = timeoutTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getSolrTime() {
		return solrTime;
	}

	public void setSolrTime(Date solrTime) {
		this.solrTime = solrTime;
	}

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getAppointmentName() {
		return appointmentName;
	}

	public void setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Date getRefuseTime() {
		return refuseTime;
	}

	public void setRefuseTime(Date refuseTime) {
		this.refuseTime = refuseTime;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Long getRealIntegral() {
		return realIntegral;
	}

	public void setRealIntegral(Long realIntegral) {
		this.realIntegral = realIntegral;
	}

	public Long getObtainIntegral() {
		return obtainIntegral;
	}

	public void setObtainIntegral(Long obtainIntegral) {
		this.obtainIntegral = obtainIntegral;
	}

	public String getAppointmentAddress() {
		return appointmentAddress;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public void setAppointmentAddress(String appointmentAddress) {
		this.appointmentAddress = appointmentAddress;
	}

	public Long getAdjustPrice() {
		return adjustPrice;
	}

	public void setAdjustPrice(Long adjustPrice) {
		this.adjustPrice = adjustPrice;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getSellerRemoteId() {
		return sellerRemoteId;
	}

	public void setSellerRemoteId(String sellerRemoteId) {
		this.sellerRemoteId = sellerRemoteId;
	}

	public Integer getSellerRemoteType() {
		return sellerRemoteType;
	}

	public void setSellerRemoteType(Integer sellerRemoteType) {
		this.sellerRemoteType = sellerRemoteType;
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

	public String getStatusName() {
		if (Constants.STATUS_GENARETE.equals(status)) {
			return "生成";
		} else if (Constants.STATUS_WAIT.equals(status)) {
			return "待付款";
		} else if (Constants.STATUS_PAY.equals(status)) {
			return "已付款";
		} else if (Constants.STATUS_SEND.equals(status)) {
			return "已发货";
		} else if (Constants.STATUS_RECEIVE.equals(status)) {
			return "已收货";
		} else if (Constants.STATUS_COMPILE.equals(status)) {
			return "交易完成";
		} else if (Constants.STATUS_CANCEL.equals(status)) {
			return "已取消";
		} else if (Constants.STATUS_DELETE.equals(status)) {
			return "已删除";
		} else if (Constants.STATUS_CLOSE.equals(status)) {
			return "超时关闭";
		} else if (Constants.BOOKORDER_STATUS_WAIT.equals(status)) {
			return "待处理";
		} else if (Constants.BOOKORDER_STATUS_CONFIRM.equals(status)) {
			return "已确认";
		} else if (Constants.BOOKORDER_STATUS_REFUSE.equals(status)) {
			return "已拒绝";
		} else if (Constants.BOOKORDER_STATUS_CANCEL.equals(status)) {
			return "已取消";
		} else {
			return "";
		}
	}

	public String getPayWayName() {
		if (null != payWay) {
			if (1 == payWay) {
				return "在线支付";
			} else if (2 == payWay) {
				return "线下支付";
			} else if (3 == payWay) {
				return "货到付款";
			}
		}
		return null;
	}

	public String getDeliveryWayName() {
		if (null != deliveryWay) {
			switch (deliveryWay) {
			case 1:
				return "快递配送";
			case 2:
				return "店铺配送";
			case 3:
				return "门店自取";
			default:
				break;
			}
		}
		return null;
	}

	public String getDeliveryCompanyName() {
		if (null != deliveryCompany) {
			if ("1".equals(deliveryCompany)) {
				return "申通E物流";
			} else if ("2".equals(deliveryCompany)) {
				return "圆通速递";
			} else if ("3".equals(deliveryCompany)) {
				return "中通速递";
			} else if ("4".equals(deliveryCompany)) {
				return "汇通快递";
			} else if ("5".equals(deliveryCompany)) {
				return "韵达快递";
			} else if ("6".equals(deliveryCompany)) {
				return "天天快递";
			} else if ("7".equals(deliveryCompany)) {
				return "宅急送";
			} else if ("8".equals(deliveryCompany)) {
				return "顺丰速运";
			} else if ("9".equals(deliveryCompany)) {
				return "全峰快递";
			} else if ("10".equals(deliveryCompany)) {
				return "国通快递";
			} else if ("11".equals(deliveryCompany)) {
				return "其它";
			}
		}
		return "";
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return new StringBuilder("SalesOrder [orderNo=").append(orderNo)
				.append(", status=").append(status).append(", orderCategory=")
				.append(orderCategory).append(", transactionSerialNumber=")
				.append(transactionSerialNumber).append(", title=")
				.append(title).append(", fromInner=").append(fromInner)
				.append(", quantity=").append(quantity).append(", price=")
				.append(price).append(", totalPrice=").append(totalPrice)
				.append(", payPrice=").append(payPrice)
				.append(", discountPrice=").append(discountPrice)
				.append(", integral=").append(integral)
				.append(", realIntegral=").append(realIntegral)
				.append(", obtainIntegral=").append(obtainIntegral)
				.append(", userId=").append(userId).append(", buyerAccount=")
				.append(buyerAccount).append(", buyerNick=").append(buyerNick)
				.append(", buyerMessage=").append(buyerMessage)
				.append(", buyerMemo=").append(buyerMemo)
				.append(", sellerRemoteId=").append(sellerRemoteId)
				.append(", sellerRemoteType=").append(sellerRemoteType)
				.append(", shopNo=").append(shopNo).append(", shopName=")
				.append(shopName).append(", sellerName=").append(sellerName)
				.append(", sellerPhone=").append(sellerPhone)
				.append(", appointmentId=").append(appointmentId)
				.append(", appointmentAddress=").append(appointmentAddress)
				.append(", sellerMemo=").append(sellerMemo).append(", payWay=")
				.append(payWay).append(", payChannel=").append(payChannel)
				.append(", payType=").append(payType).append(", bankCard=")
				.append(bankCard).append(", openId=").append(openId)
				.append(", deliveryWay=").append(deliveryWay)
				.append(", deliveryRange=").append(deliveryRange)
				.append(", pickUpPhone=").append(pickUpPhone)
				.append(", pickUpAddress=").append(pickUpAddress)
				.append(", hasPostFee=").append(hasPostFee)
				.append(", postFee=").append(postFee).append(", adjustPrice=")
				.append(adjustPrice).append(", receiverName=")
				.append(receiverName).append(", receiverPhone=")
				.append(receiverPhone).append(", vAreaCode=").append(vAreaCode)
				.append(", receiverState=").append(receiverState)
				.append(", receiverDistrict=").append(receiverDistrict)
				.append(", receiverCity=").append(receiverCity)
				.append(", receiverAddress=").append(receiverAddress)
				.append(", senderName=").append(senderName)
				.append(", senderCell=").append(senderCell)
				.append(", deliveryRemoteId=").append(deliveryRemoteId)
				.append(", deliveryRemoteType=").append(deliveryRemoteType)
				.append(", deliveryCompany=").append(deliveryCompany)
				.append(", deliverySn=").append(deliverySn)
				.append(", orderTime=").append(orderTime)
				.append(", paymentTime=").append(paymentTime)
				.append(", deliveryTime=").append(deliveryTime)
				.append(", closingTime=").append(closingTime)
				.append(", cancelTime=").append(cancelTime)
				.append(", deleteTime=").append(deleteTime)
				.append(", timeoutTime=").append(timeoutTime)
				.append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime)
				.append(", solrTime=").append(solrTime)
				.append(", appointmentName=").append(appointmentName)
				.append(", picturePath=").append(picturePath)
				.append(", appointmentTime=").append(appointmentTime)
				.append(", refuseTime=").append(refuseTime)
				.append(", refuseReason=").append(refuseReason)
				.append(", confirmTime=").append(confirmTime)
				.append(", payStatus=").append(payStatus)
				.append(", promoteSpend=").append(promoteSpend).append("]")
				.toString();
	}

}
