package com.zjlp.face.web.server.user.bankcard.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡
 * 
 * <p>
 * 
 * 注：确认是否是相同的银行卡字段【外部ID， 外部ID类型， 银行编码， 银行卡号，类型， 用途】
 * 
 * @ClassName: BankCard
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月13日 上午10:25:12
 */
public class BankCard implements Serializable {

	private static final long serialVersionUID = -3901027199858792822L;
	/** 外部ID类型:用户 */
	public static final Integer REMOTE_TYPE_USER = 1;
	/** 外部ID类型:店铺 */
	public static final Integer REMOTE_TYPE_SHOP = 2;
	/** 储值卡 */
	public static final Integer TYPE_DEBIT = 2;
	/** 信用卡 */
	public static final Integer TYPE_CREDIT = 3;
	/** 支付 */
	public static final Integer USERFOR_PAY = 1;
	/** 结算（提现） */
	public static final Integer USERFOR_SETTLE = 2;
	// 主键
	private Long id;
	// 外部ID 标识所属的唯一ID
	private String remoteId;
	// 外部ID类型：1.用户 2店铺
	private Integer remoteType;
	// 银行编码
	private String bankCode;
	// 银行卡号
	private String bankCard;
	// 银行名称
	private String bankName;
	// 手机
	private String cell;
	// 用户名
	private String name;
	// 类型： 3、贷记卡 2、储值卡
	private Integer bankType;
	// 用途：1、支付 2、结算
	private Integer type;
	// 0：失效，1：正常
	private Integer status;
	// 贷记卡有效期
	private String endTime;
	// 贷记卡安全码
	private String cvv;
	// 身份证
	private String identity;
	// 2.快捷支付（借记卡）3.快捷支付（信用卡）
	private Integer payType;
	// 签约协议号
	private String noAgree;
	// 是否为默认银行卡 （0 否 1是）
	private Integer isDefault;
	//绑定id
	private String bindId; 
	private Integer lklSign;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;

	public BankCard() {
	}

	public BankCard(String remoteId, Integer remoteType) {
		this.remoteId = remoteId;
		this.remoteType = remoteType;
	}

	public BankCard(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLklSign() {
		return lklSign;
	}

	public void setLklSign(Integer lklSign) {
		this.lklSign = lklSign;
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId == null ? null : remoteId.trim();
	}

	public Integer getRemoteType() {
		return remoteType;
	}

	public void setRemoteType(Integer remoteType) {
		this.remoteType = remoteType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard == null ? null : bankCard.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell == null ? null : cell.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv == null ? null : cvv.trim();
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity == null ? null : identity.trim();
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getNoAgree() {
		return noAgree;
	}

	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree == null ? null : noAgree.trim();
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
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

	@Override
	public String toString() {
		return new StringBuilder("BankCard [id=").append(id)
				.append(", remoteId=").append(remoteId).append(", remoteType=")
				.append(remoteType).append(", bankCode=").append(bankCode)
				.append(", bankCard=").append(bankCard).append(", bankName=")
				.append(bankName).append(", cell=").append(cell)
				.append(", name=").append(name).append(", bankType=")
				.append(bankType).append(", type=").append(type)
				.append(", status=").append(status).append(", endTime=")
				.append(endTime).append(", cvv=").append(cvv)
				.append(", identity=").append(identity).append(", payType=")
				.append(payType).append(", noAgree=").append(noAgree)
				.append(", isDefault=").append(isDefault)
				.append(", bindId=").append(bindId)
				.append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime).append("]")
				.toString();
	}
}