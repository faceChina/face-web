package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员设定
 * 
 * @ClassName: MemberEnactment
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年4月10日 上午9:19:07
 */
public class MemberEnactment implements Serializable {
	
	private static final long serialVersionUID = -4986212795711857159L;
	// 主键
	private Long id;
	// 管理者
	private Long sellerId;
	// 会员卡名称
	private String cardName;
	// 图片路径
	private String imgPath;
	// 会员卡名称颜色
	private String cardNameColor;
	// 卡号文字颜色
	private String cardNoColor;
	// 卡号编码
	private String cardCode;
	// 起始卡号
	private Integer startNo;
	// 截止卡号
	private Integer endNo;
	// 会员简介
	private String memberContent;
	// 状态 -1：删除，0：冻结，1：正常
	private Integer status;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName == null ? null : cardName.trim();
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath == null ? null : imgPath.trim();
	}

	public String getCardNameColor() {
		return cardNameColor;
	}

	public void setCardNameColor(String cardNameColor) {
		this.cardNameColor = cardNameColor == null ? null : cardNameColor
				.trim();
	}

	public String getCardNoColor() {
		return cardNoColor;
	}

	public void setCardNoColor(String cardNoColor) {
		this.cardNoColor = cardNoColor == null ? null : cardNoColor.trim();
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode == null ? null : cardCode.trim();
	}

	public Integer getStartNo() {
		return startNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public Integer getEndNo() {
		return endNo;
	}

	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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

	public String getMemberContent() {
		return memberContent;
	}

	public void setMemberContent(String memberContent) {
		this.memberContent = memberContent == null ? null : memberContent
				.trim();
	}

	@Override
	public String toString() {
		return new StringBuilder("MemberEnactment [id=").append(id)
				.append(", sellerId=").append(sellerId).append(", cardName=")
				.append(cardName).append(", imgPath=").append(imgPath)
				.append(", cardNameColor=").append(cardNameColor)
				.append(", cardNoColor=").append(cardNoColor)
				.append(", cardCode=").append(cardCode).append(", startNo=")
				.append(startNo).append(", endNo=").append(endNo)
				.append(", memberContent=").append(memberContent)
				.append(", status=").append(status).append(", createTime=")
				.append(createTime).append(", updateTime=").append(updateTime)
				.append("]").toString();
	}
}