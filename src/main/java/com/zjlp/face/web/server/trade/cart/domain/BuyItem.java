package com.zjlp.face.web.server.trade.cart.domain;

import java.io.Serializable;

/**
 * 购买商品人信息
 * @ClassName: BuyItem 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月8日 下午2:40:50
 */
public class BuyItem implements Serializable{
	
	private static final long serialVersionUID = 1424680848267925032L;
	
	/** 购买商品skuID*/
	private Long id;
	
	/** 推广时Id*/
	private String shareId;
	
	/** 购买商品数量*/
	private Long quantity;
	
	/** 商品渠道  0.自营 1.分销  2.推广*/
	private Integer goodChannel;
	
	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public Long getQuantity(){
		return quantity;
	}
	
	public void setQuantity(Long quantity){
		this.quantity = quantity;
	}
	
	public Integer getGoodChannel() {
		return goodChannel;
	}

	public void setGoodChannel(Integer goodChannel) {
		this.goodChannel = goodChannel;
	}
	
}
