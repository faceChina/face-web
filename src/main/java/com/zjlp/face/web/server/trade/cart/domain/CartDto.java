package com.zjlp.face.web.server.trade.cart.domain;

import com.zjlp.face.web.server.product.good.domain.GoodSku;



public class CartDto extends Cart {
	

	/** sku状态 */
	private Integer status;
	
	/** sku salesPrice */
	private Long salesPrice;
	
	/** 库存 */
	private Long stock;
	
	public CartDto() {
	}

	public CartDto(GoodSku goodSku,Long quantity) {
		this.setId(goodSku.getId());
		this.setGoodId(goodSku.getGoodId());
		this.setGoodSkuId(goodSku.getId());
		this.setSkuPicturePath(goodSku.getPicturePath());
		this.setSkuPropertiesName(goodSku.getSkuPropertiesName());
		this.setGoodName(goodSku.getName());
		this.setSalesPrice(goodSku.getMarketPrice());
		this.setUnitPrice(goodSku.getSalesPrice());
		this.setStock(goodSku.getStock());
		this.setQuantity(quantity);
	}

	public Integer getStatus(){
		return status;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Long getSalesPrice(){
		return salesPrice;
	}

	public void setSalesPrice(Long salesPrice){
		this.salesPrice = salesPrice;
	}
	
	public Long getStock(){
		return stock;
	}
	
	public void setStock(Long stock){
		this.stock = stock;
	}

	public String getBuyShopName() {
		if(null != getSubbranchShopName() && !"".equals(getSubbranchShopName())){
			return getSubbranchShopName();
		}
		return getShopName();
	}
}
