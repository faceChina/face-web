package com.zjlp.face.web.server.operation.subbranch.domain;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.constants.Constants;

/**
 * 总分店domain
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午10:19:18
 *
 */
public class Subbranch implements Serializable {

	private static final long serialVersionUID = 9093592222321681316L;
	// 主键
	private Long id;
	// 上级节点ID
	private Long pid;
	// 上级类型:1,供货商;2,bestface
	private Integer type;
	// 上级ID
	private Long superiorUserId;
	// 分店用户ID
	private Long userId;
	// 分店用户名称
	private String userName;
	// 分店用户账号/登录号
	private String userCell;
	// 分店用户联系方式/绑定手机号
	private String userBindingCell;
	// 分店店铺名称(总店-分店名称)
	private String shopName;
	// 发货权限:0,无;1,有
	private Integer deliver;
	// 佣金比例(上级设置)
	private Integer profit;
	// 供货商店铺号
	private String supplierShopNo;
	// 状态:1,有效;0,冻结;-1:删除
	private Integer status;
	// 是否冻结分店自有店铺:1,是;0,否
	private Integer freezeShop;
	// 二维码地址
	private String twoDimensionCode;
	// 店铺logo
	private String shopLogo;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 操作者id,此id必须为该店铺上级
	private Long actionUserId;
	//是否能招募下级代理, true可以招募，false不可以
	private Boolean isAbleToRecruit;
	public Subbranch(){}
	public Subbranch(Integer status, Date updateTime) {
		this.status = status;
		this.updateTime = updateTime;
	}

	public Boolean getIsAbleToRecruit() {
		return isAbleToRecruit;
	}

	public void setIsAbleToRecruit(Boolean isAbleToRecruit) {
		this.isAbleToRecruit = isAbleToRecruit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSuperiorUserId() {
		return superiorUserId;
	}

	public void setSuperiorUserId(Long superiorUserId) {
		this.superiorUserId = superiorUserId;
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

	public String getUserCell() {
		return userCell;
	}

	public void setUserCell(String userCell) {
		this.userCell = userCell;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getDeliver() {
		return deliver;
	}

	public void setDeliver(Integer deliver) {
		this.deliver = deliver;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public String getSupplierShopNo() {
		return supplierShopNo;
	}

	public void setSupplierShopNo(String supplierShopNo) {
		this.supplierShopNo = supplierShopNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFreezeShop() {
		return freezeShop;
	}
	public void setFreezeShop(Integer freezeShop) {
		this.freezeShop = freezeShop;
	}
	public String getTwoDimensionCode() {
		return twoDimensionCode;
	}

	public void setTwoDimensionCode(String twoDimensionCode) {
		this.twoDimensionCode = twoDimensionCode;
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

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getActionUserId() {
		return actionUserId;
	}

	public void setActionUserId(Long actionUserId) {
		this.actionUserId = actionUserId;
	}

	public String getUserBindingCell() {
		return userBindingCell;
	}

	public void setUserBindingCell(String userBindingCell) {
		this.userBindingCell = userBindingCell;
	}
	
	public static boolean isValidStatus(Integer status){
		if (Constants.VALID.equals(status) || Constants.UNVALID.equals(status)) {
			return true;
		}
		return false;
	}
	
}
