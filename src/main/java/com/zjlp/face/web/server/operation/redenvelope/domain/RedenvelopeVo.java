package com.zjlp.face.web.server.operation.redenvelope.domain;

public class RedenvelopeVo {

	//红包ID
	private Long redenvelopeId;
	//红包类型
	private Integer type;
	//祝福语
	private String wish;
	//领取者的headUrl
	private String headImgUrl;
	//发该红包用户名
	private String sendUserName;
	//个人领取的红包的金额
	private Long reciveAmount;
	//该红包是否已领完
	private Integer openState;
	//红包总个数
	private Integer totalCount;
	//已领取的红包个数
	private Integer openCount;
	//最后一个领取时间(yyyyMMdd HH:mm:ss)
	private String lastTime;
	
	public RedenvelopeVo(){}
	
	public RedenvelopeVo(Long redenvelopeId, Long reciveAmount){
		this.redenvelopeId = redenvelopeId;
		this.reciveAmount = reciveAmount;
	}
	
	public Long getRedenvelopeId() {
		return redenvelopeId;
	}
	public void setRedenvelopeId(Long redenvelopeId) {
		this.redenvelopeId = redenvelopeId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getWish() {
		return wish;
	}
	public void setWish(String wish) {
		this.wish = wish;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public Long getReciveAmount() {
		return reciveAmount;
	}
	public void setReciveAmount(Long reciveAmount) {
		this.reciveAmount = reciveAmount;
	}
	public Integer getOpenState() {
		return openState;
	}
	public void setOpenState(Integer openState) {
		this.openState = openState;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getOpenCount() {
		return openCount;
	}
	public void setOpenCount(Integer openCount) {
		this.openCount = openCount;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
}
