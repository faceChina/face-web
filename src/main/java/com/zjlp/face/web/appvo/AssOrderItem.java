package com.zjlp.face.web.appvo;

import java.text.DecimalFormat;
import java.util.Date;

import com.zjlp.face.web.server.trade.order.domain.OrderItem;

public class AssOrderItem {
    private Long id;

    private String orderNo;

    private Integer status;

    private Long classificationId;

    private Long goodId;

    private Long goodSkuId;

    private String goodName;

    private String skuPropertiesName;

    private String skuPicturePath;
	
	/** 商品原价 */
    private Long price;
    private String priceStr;

    private Long quantity;
	
	/** 商品优惠价 */
    private Long discountPrice;
    private String discountPriceStr;
	
	/** 总金额 =(discountPrice+adjustPrice)*quantity */
    private Long totalPrice;
    private String totalPriceStr;

    private Long adjustPrice;
    private String adjustPriceStr;

    private Long payPrice;
    private String payPriceStr;

    private Date createTime;
    private Long createTimeStamp;
    private Date updateTime;
    private Long updateTimeStamp;
	/**商品状态( -1：删除，1：正常 2.冻结 3 下架 )*/
	private Integer goodStatus;
	/** 商品WAP访问地址 */
	private String detailWapUrl;

    public AssOrderItem() {
		super();
	}
	public AssOrderItem(OrderItem orderItem) {
		super();
		this.id = orderItem.getId();
		this.orderNo = orderItem.getOrderNo();
		this.status = orderItem.getStatus();
		this.classificationId = orderItem.getClassificationId();
		this.goodId = orderItem.getGoodId();
		this.goodSkuId = orderItem.getGoodSkuId();
		this.goodName = orderItem.getGoodName();
		this.skuPropertiesName = orderItem.getSkuPropertiesName();
		this.skuPicturePath = orderItem.getSkuPicturePath();
		this.setPrice(orderItem.getPrice());
		this.quantity = orderItem.getQuantity();
		this.setDiscountPrice(orderItem.getDiscountPrice());
		this.setTotalPrice(orderItem.getTotalPrice());
		this.setAdjustPrice(orderItem.getAdjustPrice());
		this.setPayPrice(orderItem.getPayPrice());
		this.setCreateTime(orderItem.getCreateTime());
		this.setUpdateTime(orderItem.getUpdateTime());
	}



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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(Long goodSkuId) {
        this.goodSkuId = goodSkuId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public String getSkuPropertiesName() {
        return skuPropertiesName;
    }

    public void setSkuPropertiesName(String skuPropertiesName) {
        this.skuPropertiesName = skuPropertiesName == null ? null : skuPropertiesName.trim();
    }

    public String getSkuPicturePath() {
        return skuPicturePath;
    }

    public void setSkuPicturePath(String skuPicturePath) {
        this.skuPicturePath = skuPicturePath == null ? null : skuPicturePath.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    	if (null != discountPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.discountPriceStr = df.format(discountPrice/100.0);
		}else{
			this.discountPriceStr = "0.00";
		}
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
        if (null != totalPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.totalPriceStr = df.format(totalPrice/100.0);
		}else{
			this.totalPriceStr = "0.00";
		}
    }

    public Long getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Long adjustPrice) {
        this.adjustPrice = adjustPrice;
        if (null != adjustPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.adjustPriceStr = df.format(adjustPrice/100.0);
		}else{
			this.adjustPriceStr = "0.00";
		}
    }

    public Long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
        if (null != payPrice){
			DecimalFormat df = new DecimalFormat("##0.00");
			this.payPriceStr = df.format(payPrice/100.0);
		}else{
			this.payPriceStr = "0.00";
		}
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        if (null != createTime){
        	this.createTimeStamp = createTime.getTime();
        }
    }

    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        if (null != updateTime){
        	this.updateTimeStamp = updateTime.getTime();
        }
    }

	public Long getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public Long getUpdateTimeStamp() {
		return updateTimeStamp;
		
	}

	public void setUpdateTimeStamp(Long updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public String getDiscountPriceStr() {
		return discountPriceStr;
	}
	public void setDiscountPriceStr(String discountPriceStr) {
		this.discountPriceStr = discountPriceStr;
	}
	public String getTotalPriceStr() {
		return totalPriceStr;
	}
	public void setTotalPriceStr(String totalPriceStr) {
		this.totalPriceStr = totalPriceStr;
	}
	public String getAdjustPriceStr() {
		return adjustPriceStr;
	}
	public void setAdjustPriceStr(String adjustPriceStr) {
		this.adjustPriceStr = adjustPriceStr;
	}
	public String getPayPriceStr() {
		return payPriceStr;
	}
	public void setPayPriceStr(String payPriceStr) {
		this.payPriceStr = payPriceStr;
	}

	public Integer getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(Integer goodStatus) {
		this.goodStatus = goodStatus;
	}

	public String getDetailWapUrl() {
		return detailWapUrl;
	}

	public void setDetailWapUrl(String detailWapUrl) {
		this.detailWapUrl = detailWapUrl;
	}
    
}