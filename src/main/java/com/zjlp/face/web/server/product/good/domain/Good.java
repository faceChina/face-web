package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品对象
 * @ClassName: Good 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午1:42:03
 */
public class Good implements Serializable {
	
	private static final long serialVersionUID = -8716445783479480252L;
	
	/**
	 * 删除
	 */
	public static final Integer STATUS_DELETE = 0;
	/**
	 * 正常
	 */
	public static final Integer STATUS_NORMAL = 1;
	/**
	 * 冻结
	 */
	public static final Integer STATUS_FREEZE = 2;
	/**
	 * 下架
	 */
	public static final Integer STATUS_UNSALEED = 3;

	//主键
	private Long id;
	//业务能力ID
	private Long serviceId;
	//商品发布店铺
    private String shopNo;
    //类目ID
    private Long classificationId;
    //店铺名称
    private String shopName;
    //卖家昵称
    private String nick;
    //商品名称
    private String name;
    //商品编号
    private String productNumber;
    //商品货号
    private String no;
    //市场价（单位：分）
    private Long marketPrice;
    //销售价（单位：分）
    private Long salesPrice;
    //状态( -1：删除，1：正常 2.冻结 3 下架 )
    private Integer status;
    //商品库存
    private Long inventory;
    //优惠策略（0 无优惠  1 会员折扣）
    private Integer preferentialPolicy;
    //商品销量
    private Long salesVolume;
    //浏览次数
    private Long browerTime;
    //排序
    private Integer sort;
    //商品WAP访问地址
    private String detailWapUrl;
    //商品主图路径
    private String picUrl;
    //是否线下商品（1默认线上商品  2 线下商品）
    private Integer isOffline;
    //是否推荐至首页 (0否 1是)
    private Integer isSpreadIndex;
    //物流方式 1 店铺运费模版 2 统一运费
    private Integer logisticsMode;
    //邮费（单位：分 0表示不需要运费）当物流方式为统一运费时填写，其它情况为0
    private Long postFee;
    //上架时间
    private Date listTime;
    //下架时间
    private Date delistTime;
    //仙居外链链接
    private String outerLink;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //solr更新时间
    private Date solrTime;
    //商品简介
    private String goodContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
		return serviceId;
	}

	public String getOuterLink() {
		return outerLink;
	}

	public void setOuterLink(String outerLink) {
		this.outerLink = outerLink;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber == null ? null : productNumber.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Integer getPreferentialPolicy() {
        return preferentialPolicy;
    }

    public void setPreferentialPolicy(Integer preferentialPolicy) {
        this.preferentialPolicy = preferentialPolicy;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Integer getLogisticsMode() {
		return logisticsMode;
	}

	public void setLogisticsMode(Integer logisticsMode) {
		this.logisticsMode = logisticsMode;
	}

	public Long getPostFee() {
		return postFee;
	}

	public void setPostFee(Long postFee) {
		this.postFee = postFee;
	}

	public Long getBrowerTime() {
        return browerTime;
    }

    public void setBrowerTime(Long browerTime) {
        this.browerTime = browerTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDetailWapUrl() {
        return detailWapUrl;
    }

    public void setDetailWapUrl(String detailWapUrl) {
        this.detailWapUrl = detailWapUrl == null ? null : detailWapUrl.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
    	this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(Integer isOffline) {
        this.isOffline = isOffline;
    }

    public Integer getIsSpreadIndex() {
        return isSpreadIndex;
    }

    public void setIsSpreadIndex(Integer isSpreadIndex) {
        this.isSpreadIndex = isSpreadIndex;
    }

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public Date getDelistTime() {
        return delistTime;
    }

    public void setDelistTime(Date delistTime) {
        this.delistTime = delistTime;
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

    public String getGoodContent() {
        return goodContent;
    }

    public void setGoodContent(String goodContent) {
        this.goodContent = goodContent == null ? null : goodContent.trim();
    }

	public Date getSolrTime() {
		return solrTime;
	}

	public void setSolrTime(Date solrTime) {
		this.solrTime = solrTime;
	}
}