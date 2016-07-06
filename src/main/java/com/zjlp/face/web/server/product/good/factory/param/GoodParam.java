package com.zjlp.face.web.server.product.good.factory.param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
/**
 * 商品新增|编辑参数传输对象
 * @ClassName: GoodParam 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月26日 下午9:40:38
 */
public class GoodParam extends Good {
	
	private static final long serialVersionUID = -47199564128544879L;
	
	/** 属性回显页面格式 */
    public static final String[] BASE_JSON_COLOR_SIZE = {"id","propValueAlias","propValueId"};
    
	/** skuJson 格式 */
    public static final String[] BASE_JSON_ITEM = {"barCode","colorName","colorId","picturePath","priceYuan","sizeName","sizeId","stock","skuId"};

	/** 操作时间 */
	private Date date = new Date();

	/** 市场价格 */
	private String marketPriceYuan;

	/** 销售价格 */
	private String salesPriceYuan;
	
	/** 运费 */
	private String postFeeYuan;

	/** 页面参数：skuJson 参数 */
	private String skuJson;
	
	/** 页面参数： freeGoodSku参数 */
	private String freeGoodSku;
	
	/** 页面参数：只存在非关键属性商品SKU */
	private GoodSku goodSku;
	
	/** 页面参数：商品属性 */
	private Map<String, GoodProperty> goodPropertyMap;

	/** 页面参数：商品非关键属性属性 */
	private Map<String, GoodProperty> notkeyProperties;

	/** 页面参数：商品关联关系 */
	private GoodShopTypeRelation[] shoptype;

	/** 页面参数：商品主图片 */
	private GoodImg[] goodImgs;
	
	/** 页面参数：商品颜色图片 */
	private Map<String, GoodProperty> propertyImgs;
	
	/** sku列表 */
	private List<GoodSku> skuList;
	
	/** 类目是否具备销售属性组合*/
	private Boolean hasSalesProp;
	
	private Boolean hasProtocol;
	
	/** 用户ID */
	private Long userId;
	
	/** 商品是否享受会员折扣 */
	private Boolean isJoinMember;

	
	/**
	 * 初始化商品是否离家会员折扣
	 * @Title: setIsJoinMember 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param isJoinMember
	 * @date 2015年4月22日 下午8:47:16  
	 * @author dzq
	 */
	public void setIsJoinMember(Boolean isJoinMember) {
		this.isJoinMember = isJoinMember;
		if (isJoinMember) {
			this.setPreferentialPolicy(1);
		}else{
			this.setPreferentialPolicy(0);
		}
	}

	/**
	 * 初始化只有非关键属性类型的商品Sku信息
	 * @Title: setNokeySku 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @date 2015年3月26日 下午9:28:59  
	 * @author Administrator
	 */
	public void setNokeySku(GoodParam goodParam){
		goodSku = new GoodSku();
		goodSku.setName(goodParam.getName());
		goodSku.setStock(goodParam.getInventory());
		goodSku.setSalesPrice(goodParam.getSalesPrice());
		goodSku.setMarketPrice(goodParam.getMarketPrice());
		skuList = new ArrayList<GoodSku>(1);
		skuList.add(goodSku);
	}
	/**
	 * 初始化协议商品SKU
	 * @Title: setNokeySku 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @date 2015年3月26日 下午9:28:59  
	 * @author Administrator
	 */
	public void setProtocolSku(GoodParam goodParam){
		goodSku = new GoodSku();
		goodSku.setName(goodParam.getName());
		goodSku.setStock(goodParam.getInventory());
		goodSku.setSalesPrice(goodParam.getSalesPrice());
		goodSku.setMarketPrice(goodParam.getSalesPrice());
		skuList = new ArrayList<GoodSku>(1);
		skuList.add(goodSku);
	}

	
	public void setFreeSku(GoodParam goodParam) {
		skuList = new ArrayList<GoodSku>();
		List<GoodSku> goodSkus = JsonUtil.toArrayBean(goodParam.getFreeGoodSku(), GoodSku.class);
		for (GoodSku goodSku : goodSkus) {
			GoodSku newGoodSku = new GoodSku();
			newGoodSku.setId(goodSku.getId());
			newGoodSku.setName(goodSku.getName());
			newGoodSku.setAttributeName(goodSku.getAttributeName());
			newGoodSku.setStock(goodSku.getStock());
			newGoodSku.setSalesPrice(goodSku.getSalesPrice());
			newGoodSku.setMarketPrice(goodParam.getMarketPrice());
			skuList.add(newGoodSku);
		}
		
	}
	/**
	 * 转换Sku列表
	 * @Title: setSkuJson 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param skuJson
	 * @throws Exception
	 * @date 2015年3月26日 下午9:28:34  
	 * @author Administrator
	 */
	public void setSkuJson(String skuJson) throws Exception {
		this.skuJson = skuJson;
		if (null != skuJson && !"".equals(skuJson)) {
			List<GoodSku> goodSkus = JsonUtil.toArrayBean(skuJson, GoodSku.class);
			this.setSkuList(goodSkus);
		}
	}
	
	/**
	 * 转换市场价格
	 * @Title: setMarketPriceYuan 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param marketPriceYuan
	 * @date 2015年3月26日 下午9:28:04  
	 * @author Administrator
	 */
	public void setMarketPriceYuan(String marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
		if (null != marketPriceYuan && !"".equals(marketPriceYuan)) {
			super.setMarketPrice(CalculateUtils.converYuantoPenny(marketPriceYuan));
		}
	}
	
	/**
	 * 转换销售价格
	 * @Title: setSalesPriceYuan 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesPriceYuan
	 * @date 2015年3月26日 下午9:28:17  
	 * @author Administrator
	 */
	public void setSalesPriceYuan(String salesPriceYuan) {
		this.salesPriceYuan = salesPriceYuan;
		if (null != salesPriceYuan && !"".equals(salesPriceYuan)) {
			super.setSalesPrice(CalculateUtils.converYuantoPenny(salesPriceYuan));
		}
	}
	
	public void setPostFeeYuan(String postFeeYuan) {
		this.postFeeYuan = postFeeYuan;
		if (null != postFeeYuan && !"".equals(postFeeYuan)) {
			super.setPostFee(CalculateUtils.converYuantoPenny(postFeeYuan));
		}
	}
	
	public Map<String, GoodProperty> getPropertyImgs() {
		return propertyImgs;
	}
	public void setPropertyImgs(Map<String, GoodProperty> propertyImgs) {
		this.propertyImgs = propertyImgs;
	}
	public String getPostFeeYuan() {
		return postFeeYuan;
	}

	public Boolean getHasSalesProp() {
		return hasSalesProp;
	}

	public void setHasSalesProp(Boolean hasSalesProp) {
		this.hasSalesProp = hasSalesProp;
	}

	public GoodSku getGoodSku() {
		return goodSku;
	}

	public void setGoodSku(GoodSku goodSku) {
		this.goodSku = goodSku;
	}

	public Map<String, GoodProperty> getNotkeyProperties() {
		return notkeyProperties;
	}

	public void setNotkeyProperties(Map<String, GoodProperty> notkeyProperties) {
		this.notkeyProperties = notkeyProperties;
	}

	public GoodImg[] getGoodImgs() {
		return goodImgs;
	}
	
	public Boolean getIsJoinMember() {
		return isJoinMember;
	}

	public void setGoodImgs(GoodImg[] goodImgs) {
		this.goodImgs = goodImgs;
	}

	public String getSkuJson() {
		return skuJson;
	}

	public String getSalesPriceYuan() {
		return salesPriceYuan;
	}

	public String getMarketPriceYuan() {
		return marketPriceYuan;
	}

	public GoodShopTypeRelation[] getShoptype() {
		return shoptype;
	}

	public void setShoptype(GoodShopTypeRelation[] shoptype) {
		this.shoptype = shoptype;
	}

	public Good getGood() {
		return (Good) this;
	}

	public Map<String, GoodProperty> getGoodPropertyMap() {
		return goodPropertyMap;
	}

	public void setGoodPropertyMap(Map<String, GoodProperty> goodPropertyMap) {
		this.goodPropertyMap = goodPropertyMap;
	}

	public List<GoodSku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<GoodSku> skuList) {
		this.skuList = skuList;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Boolean getHasProtocol() {
		return hasProtocol;
	}
	public void setHasProtocol(Boolean hasProtocol) {
		this.hasProtocol = hasProtocol;
	}
	
	public String getFreeGoodSku() {
		return freeGoodSku;
	}

	public void setFreeGoodSku(String freeGoodSku) {
		this.freeGoodSku = freeGoodSku;
	}

	public void setQITASku(GoodParam goodParam) {
		goodSku = new GoodSku();
		goodSku.setName(goodParam.getName());
		goodSku.setStock(goodParam.getInventory());
		goodSku.setSalesPrice(goodParam.getSalesPrice());
		goodSku.setMarketPrice(goodParam.getMarketPrice());
		goodSku.setId(goodParam.getSkuList().get(0).getId());
		skuList = new ArrayList<GoodSku>(1);
		skuList.add(goodSku);
	}
}
