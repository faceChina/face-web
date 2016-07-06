package com.zjlp.face.web.server.product.good.domain.vo;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.ShopType;

public class ShopTypeVo extends ShopType {
	
	private static final long serialVersionUID = -4617814408507588040L;
	//商品ID
	private Long goodId;
	// 集合id
	private Long groupId;
	
	private Long appointmentId;
	
	private Boolean flag;
	/** 该分类下的商品列表 */
    private List<GoodVo> goodList;
    
    //该商品分类下商品数量
    private Integer count;
    
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<GoodVo> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<GoodVo> goodList) {
		this.goodList = goodList;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
}
