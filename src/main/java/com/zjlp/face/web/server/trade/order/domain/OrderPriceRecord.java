package com.zjlp.face.web.server.trade.order.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderPriceRecord implements Serializable{
	
    /**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -1155422735186593666L;

	private Long id;

    private String orderNo;

    private Long orderItemId;

    private Integer type;

    private Integer quantity;

    private Long unitPrice;

    private Long subTotal;

    private Long sumPriceBf;

    private Long sumPriceAf;

    private Integer alterType;

    private Long alterAmout;

    private Long createAuthor;

    private String createAuthorName;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Long subTotal) {
        this.subTotal = subTotal;
    }

    public Long getSumPriceBf() {
        return sumPriceBf;
    }

    public void setSumPriceBf(Long sumPriceBf) {
        this.sumPriceBf = sumPriceBf;
    }

    public Long getSumPriceAf() {
        return sumPriceAf;
    }

    public void setSumPriceAf(Long sumPriceAf) {
        this.sumPriceAf = sumPriceAf;
    }

    public Integer getAlterType() {
        return alterType;
    }

    public void setAlterType(Integer alterType) {
        this.alterType = alterType;
    }

    public Long getAlterAmout() {
        return alterAmout;
    }

    public void setAlterAmout(Long alterAmout) {
        this.alterAmout = alterAmout;
    }

    public Long getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(Long createAuthor) {
        this.createAuthor = createAuthor;
    }

    public String getCreateAuthorName() {
        return createAuthorName;
    }

    public void setCreateAuthorName(String createAuthorName) {
        this.createAuthorName = createAuthorName == null ? null : createAuthorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}