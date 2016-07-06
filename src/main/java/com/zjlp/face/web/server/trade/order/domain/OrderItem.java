package com.zjlp.face.web.server.trade.order.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.web.server.user.shop.dao.AuthorizationCodeDao;
import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;

public class OrderItem implements Serializable{
	
    /**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 7803403119329250139L;

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

    private Long quantity;
	
	/** 商品优惠价  */
    private Long discountPrice;
	
	/** 总金额 =(discountPrice+adjustPrice)*quantity */
    private Long totalPrice;

    private Long adjustPrice;

    private Long payPrice;

    private Date createTime;

    private Date updateTime;
    
    private boolean isPopularize = false;
    
    private String shareId;
    
    private Long profitPrice;
    
    private Long skuSalesPrice;
    
    /** 优惠券优惠的价格 */
    private Long couponPrice;
    
    /** 积分抵价的价格 */
    private Long integralPrice;
	
	public Long getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(Long couponPrice) {
		this.couponPrice = couponPrice;
	}
	public Long getIntegralPrice() {
		return integralPrice;
	}
	public void setIntegralPrice(Long integralPrice) {
		this.integralPrice = integralPrice;
	}
	public Long getSkuSalesPrice() {
		return skuSalesPrice;
	}
	public void setSkuSalesPrice(Long skuSalesPrice) {
		this.skuSalesPrice = skuSalesPrice;
	}
	public Long getProfitPrice() {
		return profitPrice;
	}
	public void setProfitPrice(Long profitPrice) {
		this.profitPrice = profitPrice;
	}
	public boolean isPopularize() {
		return isPopularize;
	}
	public void setPopularize(boolean isPopularize) {
		this.isPopularize = isPopularize;
	}

    public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
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
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Long adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
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

	public static Long countToalPrice(List<OrderItem> items) {
		Long result = 0L;
		if (null == items || items.isEmpty())
			return result;
		for (OrderItem orderItem : items) {
			if (null == orderItem)
				continue;
			Logger.getLogger(OrderItem.class).info("ORDER_NO =" + orderItem.getOrderNo());
			Logger.getLogger(OrderItem.class).info("orderItem.getDiscountPrice()=" + orderItem.getDiscountPrice());
			Logger.getLogger(OrderItem.class).info("orderItem.getQuantity()" + orderItem.getQuantity());
			result += CalculateUtils.get(orderItem.getDiscountPrice(), orderItem.getQuantity()); // 取优惠单价
		}
		return result;
	}
    public AuthorizationCode getAuthorizationCode(){
    	return ContextLoaderListener.getCurrentWebApplicationContext().getBean(AuthorizationCodeDao.class).getByOrderItemId(id);
    }
	@Override
	public String toString() {
		return new StringBuilder("OrderItem [id=").append(id)
				.append(", orderNo=").append(orderNo).append(", status=")
				.append(status).append(", classificationId=")
				.append(classificationId).append(", goodId=").append(goodId)
				.append(", goodSkuId=").append(goodSkuId).append(", goodName=")
				.append(goodName).append(", skuPropertiesName=")
				.append(skuPropertiesName).append(", skuPicturePath=")
				.append(skuPicturePath).append(", price=").append(price)
				.append(", quantity=").append(quantity)
				.append(", discountPrice=").append(discountPrice)
				.append(", totalPrice=").append(totalPrice)
				.append(", adjustPrice=").append(adjustPrice)
				.append(", payPrice=").append(payPrice).append(", createTime=")
				.append(createTime).append(", updateTime=").append(updateTime)
				.append(", isPopularize=").append(isPopularize)
				.append(", shareId=").append(shareId).append(", profitPrice=")
				.append(profitPrice).append(", skuSalesPrice=")
				.append(skuSalesPrice).append("]").toString();
	}
	
	public String getDiscountPriceStr() {
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(this.discountPrice/100.0);
	}
}