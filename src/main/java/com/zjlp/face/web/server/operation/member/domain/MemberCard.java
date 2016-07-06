package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 会员卡
 * @ClassName: MemberCard 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月10日 下午4:37:32
 */
public class MemberCard implements Serializable {
	
	private static final long serialVersionUID = -7729092678056035218L;
	
	//主键
	private Long id;
	//卖家ID
    private Long sellerId;
    //用户ID
    private Long userId;
    //用户唯一标识符
    private String openId;
    //微信用户唯一标识
    private String fakeId;
    //会员名称
    private String userName;
    //会员手机号
    private String cell;
    //会员卡号
    private String memberCard;
    //会员卡英文前缀
    private String memberCardPrefix;
    //会员卡编号
    private Integer memberCardNo;
    //可用总余额(单位：分)  默认0元 使用总余额消费 = 实际充值余额+赠送余额,消费时优先使用赠送余额
    private Long amount;
    //实际充值余额(单位：分)  默认0元
    private Long realAmount;
    //赠送余额(单位：分)  默认0元
    private Long giftAmount;
    //消费额（会员等级计算时使用的消费额度）
    private Long consumptionAmout;
    //实际消费额（会员实际付款总额度）
    private Long realConsumptionAccount;
    //可用积分
    private Long availableIntegral;
    //冻结积分
    private Long frozenIntegral;
    //状态 -1：删除，0：冻结，1：正常
    private Integer status;
    //图片路径
    private String imgPath;
    //领卡时间
    private Date claimTime;
    //性别
    private Integer sex;
    //具体地址
    private String addressDetail;
    //地址码
    private Integer vAreaCode;
    //省
    private String province;
    //城市
    private String city;
    //区
    private String district;
    //生日
    private String birthday;
    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    
    public MemberCard(){}
    
    public MemberCard(Long sellerId, Long userId){
    	this.sellerId = sellerId;
    	this.userId = userId;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getMemberCard() {
		return memberCard;
	}
	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}
	public String getMemberCardPrefix() {
		return memberCardPrefix;
	}
	public void setMemberCardPrefix(String memberCardPrefix) {
		this.memberCardPrefix = memberCardPrefix;
	}
	public Integer getMemberCardNo() {
		return memberCardNo;
	}
	public void setMemberCardNo(Integer memberCardNo) {
		this.memberCardNo = memberCardNo;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getConsumptionAmout() {
		return consumptionAmout;
	}
	public void setConsumptionAmout(Long consumptionAmout) {
		this.consumptionAmout = consumptionAmout;
	}
	public Long getRealConsumptionAccount() {
		return realConsumptionAccount;
	}
	public void setRealConsumptionAccount(Long realConsumptionAccount) {
		this.realConsumptionAccount = realConsumptionAccount;
	}
	public Long getAvailableIntegral() {
		return availableIntegral;
	}
	public void setAvailableIntegral(Long availableIntegral) {
		this.availableIntegral = availableIntegral;
	}
	public Long getFrozenIntegral() {
		return frozenIntegral;
	}
	public void setFrozenIntegral(Long frozenIntegral) {
		this.frozenIntegral = frozenIntegral;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Date getClaimTime() {
		return claimTime;
	}
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	public Long getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(Long realAmount) {
		this.realAmount = realAmount;
	}
	public Long getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(Long giftAmount) {
		this.giftAmount = giftAmount;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public Integer getvAreaCode() {
		return vAreaCode;
	}
	public void setvAreaCode(Integer vAreaCode) {
		this.vAreaCode = vAreaCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return new StringBuilder("MemberCard [id=").append(id)
				.append(", sellerId=").append(sellerId)
				.append(", openId=").append(openId).append(", userId=")
				.append(userId).append(", userName=").append(userName)
				.append(", cell=").append(cell).append(", memberCard=")
				.append(memberCard).append(", memberCardPrefix=")
				.append(memberCardPrefix).append(", memberCardNo=")
				.append(memberCardNo).append(", amount=").append(amount)
				.append(", consumptionAmout=").append(consumptionAmout)
				.append(", realConsumptionAccount=")
				.append(realConsumptionAccount).append(", availableIntegral=")
				.append(", frozenIntegral=").append(frozenIntegral)
				.append(availableIntegral).append(", status=").append(status)
				.append(", imgPath=").append(imgPath).append(", claimTime=")
				.append(claimTime).append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime).append("]")
				.toString();
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getFakeId() {
		return fakeId;
	}
	public void setFakeId(String fakeId) {
		this.fakeId = fakeId;
	}
}