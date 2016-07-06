package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 
* @ClassName: AssGoodItemVo
* @Description: 商品信息
* @author wxn
* @date 2014年12月16日 下午2:43:42
*
 */
public class AssGoodItemVo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -6867337511061018989L;
    //商品的主图
    private String goodPicturePath;
    // 商品明细名称
    private String name;
    // 购物实时单价(单位:分)
    private Long price;
    // 格式化单价
    private String priceStr;
    
    private Long goodItemId;
    
    // 颜色代码
    private String colorValueCode;

    // 颜色名称
    private String colorValue;

    // 尺寸代码
    private String sizeValueCode;

    // 尺寸名称
    private String sizeValue;
    
    // 图片路径
    private String picturePath;
    
    // 商品库存
    private Long stock;
    // 商品条码
    private String barCode;
    
    public String getPriceStr() {
		return priceStr;
	}
	// 购买数量
    private Long quantity;
    
	public String getGoodPicturePath() {
		return goodPicturePath;
	}
	public void setGoodPicturePath(String goodPicturePath) {
		if (null == goodPicturePath){
			this.goodPicturePath = "";
		}else{
			this.goodPicturePath = goodPicturePath;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPrice() {
		return price;
	}
	public  void setPrice(Long price) {
		this.price = price;
		if (null != price){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.priceStr = df.format(price/100.0);
		}else{
			this.priceStr = "0.00";
		}
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getGoodItemId() {
		return goodItemId;
	}
	public void setGoodItemId(Long goodItemId) {
		this.goodItemId = goodItemId;
	}
	public String getColorValueCode() {
		return colorValueCode;
	}
	public void setColorValueCode(String colorValueCode) {
		this.colorValueCode = colorValueCode;
	}
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	public String getSizeValueCode() {
		return sizeValueCode;
	}
	public void setSizeValueCode(String sizeValueCode) {
		this.sizeValueCode = sizeValueCode;
	}
	public String getSizeValue() {
		return sizeValue;
	}
	public void setSizeValue(String sizeValue) {
		this.sizeValue = sizeValue;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	
}
