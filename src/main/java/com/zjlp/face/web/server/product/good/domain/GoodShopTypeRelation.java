package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
/**
 * 商品与商品分类关联关系
 * @ClassName: GoodShopTypeRelation 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午2:25:37
 */
public class GoodShopTypeRelation implements Serializable {
	
	private static final long serialVersionUID = 1396681975130918408L;
	//主键
	private Long id;
	//商品分类ID
    private Long shopTypeId;
    //商品ID
    private Long goodId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(Long shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

}