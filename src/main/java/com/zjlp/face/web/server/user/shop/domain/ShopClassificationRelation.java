package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;

public class ShopClassificationRelation implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2190080679476828828L;

	private Long id;

    private String shopNo;

    private Long classificationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }
}