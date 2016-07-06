package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;

public class GoodSku implements Serializable{
	private static final long serialVersionUID = 735170156587532604L;
    /** 计算对象对比方法*/
//	public static final String SKUPROP_DELIMITER = ";";

	private Long id;
	//商品ID
    private Long goodId;
    
    /**1,普通商品;2,预约商品;3,代理商品*/
    private Long serviceId;
    //商品名称
    private String name;
    //条形码
    private String barCode;
    //库存
    private Long stock;
    //市场价格
    private Long marketPrice;
    //销售价格
    private Long salesPrice;
    //是否会员优惠
    private Integer preferentialPolicy;
    //状态( -1：删除，1：正常 2.冻结 3 下架 )
    private Integer status;
    //SKU属性	
    private String skuProperties;
    //SKU属性名称	
    private String skuPropertiesName;
    //版本
    private String version;
    //图片
    private String picturePath;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //属性名称
    private String attributeName;
    
    /** 零售价格替换时，使用此属性保留原价格*/
    private Long skuSalesPrice;
    
    /** 以下为参数转换时使用，未与数据库对应 */
	private String colorId;
	
	private String colorName;
	
	private String sizeName;
	
	private String sizeId;
	
	private String priceYuan;
	
	private String skuId;
	
	public Long getSkuSalesPrice() {
		return skuSalesPrice;
	}

	public void setSkuSalesPrice(Long skuSalesPrice) {
		this.skuSalesPrice = skuSalesPrice;
	}

	/** 计算sku属性排序方法*/
	public void setSkuId(String skuId) {
		this.skuId = skuId;
		if (StringUtils.isNotBlank(skuId)) {
			this.setId(Long.valueOf(skuId));
		}
	}

	/** 计算sku属性排序方法
	 *        参数 A;B或B;A       
	 *        返回 ;A;B;
	 */
	public static String sortDbProperties(String skuProperties){
		if (StringUtils.isBlank(skuProperties)) {
			return skuProperties;
		}
		String[] strs = skuProperties.split(";");
		Arrays.sort(strs);  
		StringBuffer sorts = new StringBuffer();
		for(String str : strs) {  
			sorts.append(";").append(str);
		} 
		sorts.append(";");
		return sorts.toString();
	}
	
		/** 计算sku属性排序方法*/
	public static String sortProperties(String skuProperties){
		if (StringUtils.isBlank(skuProperties)) {
			return skuProperties;
		}
		String[] strs = skuProperties.split(";");
		Arrays.sort(strs);  
		StringBuffer sorts = new StringBuffer();
		for(String str : strs) {
			sorts.append(str).append(";");
		} 
		sorts.delete(sorts.length()-1, sorts.length());
		return sorts.toString();
	}

    /** 计算对象对比方法*/
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        GoodSku sku = (GoodSku) obj;
        if (goodId.equals(sku.getGoodId()) && skuPropertiesName.equals(sku.getSkuPropertiesName()) &&
        	salesPrice.equals(sku.getSalesPrice()) && skuProperties.equals(sku.getSkuProperties())) {
			return true;
		}
        return false;
	}
	
    /** 计算商品库存 */
    public static Long calculateSkuInventory(List<? extends GoodSku> goodSkuList){
    	Long inventory = 0L;
    	if (null == goodSkuList || goodSkuList.isEmpty()) {
			return inventory;
		}
    	for (GoodSku goodItem : goodSkuList) {
    		if (0 > goodItem.getStock()) {
				throw new GoodException("商品库存参数不正确，计算失败");
			}
    		inventory = CalculateUtils.getSum(inventory,goodItem.getStock());
		}
    	return inventory;
    }
    /** 计算最小价格 */
    public static Long getMinSkuPrice(List<? extends GoodSku> goodSkuList){
    	Long minPrice = 0L;
    	if (null == goodSkuList || goodSkuList.isEmpty()) {
			return minPrice;
		}
    	minPrice = goodSkuList.get(0).getSalesPrice();
    	for (GoodSku goodItem : goodSkuList) {
    		minPrice = minPrice>=goodItem.getSalesPrice()?goodItem.getSalesPrice():minPrice;
		}
		AssertUtil.notTrue(Constants.GOOD_MIN_PIRCE_PANNY > minPrice,"操作失败，商品销售价格小于商品最低价格(0.1元)");
    	return minPrice;
    }
    
	public String getSkuId() {
		return skuId;
	}


    public Long getId() {
        return id;
    }

    public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Long salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Integer getPreferentialPolicy() {
        return preferentialPolicy;
    }

    public void setPreferentialPolicy(Integer preferentialPolicy) {
        this.preferentialPolicy = preferentialPolicy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSkuProperties() {
        return skuProperties;
    }
    
    public void setSkuProperties(String skuProperties) {
        this.skuProperties = skuProperties == null ? null : skuProperties.trim();
    }

    public String getSkuPropertiesName() {
        return skuPropertiesName;
    }

    public void setSkuPropertiesName(String skuPropertiesName) {
        this.skuPropertiesName = skuPropertiesName == null ? null : skuPropertiesName.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
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

	public String getColorId() {
		return colorId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getSizeId() {
		return sizeId;
	}

	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
	}

	public String getPriceYuan() {
		return priceYuan;
	}

	public void setPriceYuan(String priceYuan) {
		this.priceYuan = priceYuan;
		if (null != priceYuan && !"".equals(priceYuan)) {
			setSalesPrice(CalculateUtils.converYuantoPenny(priceYuan));
		}
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	
    
}