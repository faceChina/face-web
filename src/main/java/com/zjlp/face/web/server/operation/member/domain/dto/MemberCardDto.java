package com.zjlp.face.web.server.operation.member.domain.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.user.shop.domain.Shop;

public class MemberCardDto extends MemberCard {

	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	private static final long serialVersionUID = -1937537025350979384L;
	private static final Integer[] ORDERBYCODE_LIST = {0, 1, 2, 3, 4, 5, 6};
	private static final Map<Integer, String> ORDERBY_MAP = new HashMap<Integer, String>();
	
	static {
		ORDERBY_MAP.put(ORDERBYCODE_LIST[0], "CLAIM_TIME ASC");
		//消费额
		ORDERBY_MAP.put(ORDERBYCODE_LIST[1], "AMOUNT ASC");
		ORDERBY_MAP.put(ORDERBYCODE_LIST[2], "AMOUNT DESC");
		//余额
		ORDERBY_MAP.put(ORDERBYCODE_LIST[3], "CONSUMPTION_AMOUT ASC");
		ORDERBY_MAP.put(ORDERBYCODE_LIST[4], "CONSUMPTION_AMOUT DESC");
		//积分排序
		ORDERBY_MAP.put(ORDERBYCODE_LIST[5], "INTEGRAL ASC");
		ORDERBY_MAP.put(ORDERBYCODE_LIST[6], "INTEGRAL DESC");
	}
	//排序code
	private Integer orderbyCode = 0;
	//查询：最大消费金额
	private Long maxAmount;
	//查询：最小金额
	private Long minAmount;
	//查询：辅助工具
	private Aide aide = new Aide();
	//会员卡等级名称
	private String levalName;
	//会员卡适用的店铺列表
	private List<Shop> shopList;
	//文字模糊检索条件
	private String condition;
	//店铺编号
	private String shopNo;
	//会员卡图片路径
	private String cardImgPath;
	//会员卡名称颜色
	private String cardNameColor;
	//卡号文字颜色
	private String cardNoColor;
	
	//赠送积分
	private Long sendIntegral;
	
	//会员折扣
	private Integer discount;
	
	public MemberCardDto(){}
	public MemberCardDto(Long settleId, Long userId) {
	    this.setSellerId(settleId);
	    this.setUserId(userId);
	}
	public MemberCardDto(String shopNo, Long userId) {
	    this.setUserId(userId);
	    this.setShopNo(shopNo);
	}

	public Integer getOrderbyCode() {
		return orderbyCode;
	}
	public void setOrderbyCode(Integer orderbyCode) {
		this.orderbyCode = orderbyCode;
	}
	public Long getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Long maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Long getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Long minAmount) {
		this.minAmount = minAmount;
	}
	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}
	public String getOrderbySQL() {
		return ORDERBY_MAP.get(this.orderbyCode);
	}
	public Integer[] getOrderbyMap() {
		return ORDERBYCODE_LIST;
	}
	public String getLevalName() {
		return levalName;
	}
	public void setLevalName(String levalName) {
		this.levalName = levalName;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getCardImgPath() {
		return cardImgPath;
	}
	public void setCardImgPath(String cardImgPath) {
		this.cardImgPath = cardImgPath;
	}
	public String getCardNameColor() {
		return cardNameColor;
	}
	public void setCardNameColor(String cardNameColor) {
		this.cardNameColor = cardNameColor;
	}
	public String getCardNoColor() {
		return cardNoColor;
	}
	public void setCardNoColor(String cardNoColor) {
		this.cardNoColor = cardNoColor;
	}
	public Long getSendIntegral() {
		return sendIntegral;
	}
	public void setSendIntegral(Long sendIntegral) {
		this.sendIntegral = sendIntegral;
	}
}
