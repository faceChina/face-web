package com.zjlp.face.web.server.trade.payment.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 会员卡交易明细表
 * @ClassName: MemberTransactionRecord 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月10日 下午5:01:19
 */
public class MemberTransactionRecord implements Serializable {
	private static final long serialVersionUID = -912983651421294532L;
	//主键
	private Long id;
	//会员卡id
    private Long memberCardId;
    //卖家id
    private String sellerId;
    //流水号
    private String transactionSerialNumber;
    //渠道
    private String channel;
    //类型
    private Integer type;
    //商品信息
    private String goodInfo;
    //交易价格
    private Long transPrice;
    //价格
    private Long amount;
    //开始价格
    private Long beforeAmount;
    //交易时间
    private Date transTime;
    //交易年
    private String transYear;
    //交易月
    private String transMonth;
    //创建时间
    private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberCardId() {
		return memberCardId;
	}
	public void setMemberCardId(Long memberCardId) {
		this.memberCardId = memberCardId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}
	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getGoodInfo() {
		return goodInfo;
	}
	public void setGoodInfo(String goodInfo) {
		this.goodInfo = goodInfo;
	}
	public Long getTransPrice() {
		return transPrice;
	}
	public void setTransPrice(Long transPrice) {
		this.transPrice = transPrice;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(Long beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	public Date getTransTime() {
		return transTime;
	}
	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}
	public String getTransYear() {
		return transYear;
	}
	public void setTransYear(String transYear) {
		this.transYear = transYear;
	}
	public String getTransMonth() {
		return transMonth;
	}
	public void setTransMonth(String transMonth) {
		this.transMonth = transMonth;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return new StringBuilder("MemberTransactionRecord [id=").append(id)
				.append(", memberCardId=").append(memberCardId)
				.append(", sellerId=").append(sellerId)
				.append(", transactionSerialNumber=")
				.append(transactionSerialNumber).append(", channel=")
				.append(channel).append(", type=").append(type)
				.append(", goodInfo=").append(goodInfo).append(", transPrice=")
				.append(transPrice).append(", amount=").append(amount)
				.append(", beforeAmount=").append(beforeAmount)
				.append(", transTime=").append(transTime)
				.append(", transYear=").append(transYear)
				.append(", transMonth=").append(transMonth)
				.append(", createTime=").append(createTime).append("]")
				.toString();
	}
    
}