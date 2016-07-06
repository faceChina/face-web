package com.zjlp.face.web.server.trade.order.domain.vo;
/**
 * 商品详情选择sku返回对象
 * @ClassName: SkuSelectedVo 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月12日 下午3:48:59
 */
public class RspSelectedSkuVo {
	//商品skuId
	private Long id;
	//商品sku图片
	private String picturePath;
	//sku销售价格
	private Long salesPrice;
	//sku库存
	private Long stock;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Long getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}

}
