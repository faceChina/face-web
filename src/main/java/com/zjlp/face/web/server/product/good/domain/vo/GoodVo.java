package com.zjlp.face.web.server.product.good.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;

/**
 * 商品页面对象
 * 
 * @ClassName: GoodVo
 * @Description: (这里用一句话描述这个类的作用)
 * @author dzq
 * @date 2015年3月24日 下午9:19:14
 */
public class GoodVo extends Good {

	@Override
	public void setPicUrl(String picUrl) {
		if (null != picUrl && !"".equals(picUrl)) {
			String size = PropertiesUtil.getContexrtParam(ImageConstants.GOOD_FILE_280_280);
			super.setPicUrl(ImageConstants.getCloudstZoomPath(picUrl, size));
		} else {
			super.setPicUrl(picUrl);
		}
	}

	private static final long serialVersionUID = 4745805060445351643L;

	// 集合id
	private Long groupId;

	private Long shopTypeId;

	private String shopTypeName;

	// 刷新截至时间
	private Date currentTimeDate;

	// 刷新截至时间
	private Long currentTime;

	// 全部商品数量
	private Integer totalGoodNum;

	// 最新商品数量
	private Integer newGoodNum;

	// 查询最新商品标识
	private Integer newGoodMark;

	/** 价格排序 */
	private String priceSort;
	
	/** pc端产品标识  1为电脑端  0 为手机端*/
    private String isPcGoods;
	
	/** 产品线ID */
	private Long productLineId;

	private String productLineName;

	private Integer retailLowPercent;

	private Integer retailHighPercent;

	private Integer discountRate;

	private List<GoodSku> goodSkuList;

	private String distributorShopNo;


	private Aide aide = new Aide();

	public Aide getAide() {
		return aide;
	}

	public void setAide(Aide aide) {
		this.aide = aide;
	}

	/** 销量排序 */
	private String volumeSort;

	private List<Long> productLineIdList;


	public Integer getTotalGoodNum() {
		return totalGoodNum;
	}

	public Long getProductLineId() {
		return productLineId;
	}

	public String getDistributorShopNo() {
		return distributorShopNo;
	}

	public void setDistributorShopNo(String distributorShopNo) {
		this.distributorShopNo = distributorShopNo;
	}

	public void setProductLineId(Long productLineId) {
		this.productLineId = productLineId;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	public String getProductLineName() {
		return productLineName;
	}

	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}

	public List<Long> getProductLineIdList() {
		return productLineIdList;
	}

	public void setProductLineIdList(List<Long> productLineIdList) {
		this.productLineIdList = productLineIdList;
	}

	public Integer getRetailLowPercent() {
		return retailLowPercent;
	}

	public List<GoodSku> getGoodSkuList() {
		return goodSkuList;
	}

	public void setGoodSkuList(List<GoodSku> goodSkuList) {
		this.goodSkuList = goodSkuList;
	}

	public void setRetailLowPercent(Integer retailLowPercent) {
		this.retailLowPercent = retailLowPercent;
	}

	public Integer getRetailHighPercent() {
		return retailHighPercent;
	}

	public void setRetailHighPercent(Integer retailHighPercent) {
		this.retailHighPercent = retailHighPercent;
	}

	public void setTotalGoodNum(Integer totalGoodNum) {
		this.totalGoodNum = totalGoodNum;
	}

	public Integer getNewGoodNum() {
		return newGoodNum;
	}

	public void setNewGoodNum(Integer newGoodNum) {
		this.newGoodNum = newGoodNum;
	}

	public String getShopTypeName() {
		return shopTypeName;
	}

	public void setShopTypeName(String shopTypeName) {
		this.shopTypeName = shopTypeName;
	}

	public String getPriceSort() {
		return priceSort;
	}

	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}

	public String getVolumeSort() {
		return volumeSort;
	}

	public void setVolumeSort(String volumeSort) {
		this.volumeSort = volumeSort;
	}

	public Date getCurrentTimeDate() {
		return currentTimeDate;
	}

	public void setCurrentTimeDate(Date currentTimeDate) {
		this.currentTimeDate = currentTimeDate;
	}

	public Long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
		this.currentTimeDate = DateUtil.LongToDate(currentTime);
	}

	public Long getShopTypeId() {
		if (null != shopTypeId && 0 == shopTypeId) {
			return null;
		}
		return shopTypeId;
	}

	public void setShopTypeId(Long shopTypeId) {
		this.shopTypeId = shopTypeId;
	}

	public String getPath() {
		String size = PropertiesUtil.getContexrtParam(ImageConstants.GOOD_FILE);
		return ImageConstants.getCloudstZoomPath(super.getPicUrl(), size);
	}

	public Integer getNewGoodMark() {
		return newGoodMark;
	}

	public void setNewGoodMark(Integer newGoodMark) {
		this.newGoodMark = newGoodMark;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}



	public String getIsPcGoods() {
		return isPcGoods;
	}

	public void setIsPcGoods(String isPcGoods) {
		this.isPcGoods = isPcGoods;
	}

	public Long getMaxSalesPrice() {
		if (null == goodSkuList || goodSkuList.size() == 1) {
			return getSalesPrice();
		}
		Long price = getSalesPrice();
		for (GoodSku sku : goodSkuList) {
			if (sku.getSalesPrice() > price) {
				price = sku.getSalesPrice();
			}
		}
		return price;
	}

	public static Long[] getMinMaxPrice(Long[] priceList) {
		if (null == priceList || 0 == priceList.length) {
			return null;
		}
		Long[] list = { priceList[0], priceList[0] };
		for (Long price : priceList) {
			if (price == null)
				continue;
			if (price < list[0])
				list[0] = price;
			if (price > list[1])
				list[1] = price;
		}
		return list;
	}

	public String getLsFw() {
		if (null == goodSkuList || goodSkuList.isEmpty())
			return null;
		Long[] prices = new Long[goodSkuList.size()];
		for (int i = 0; i < goodSkuList.size(); i++) {
			prices[i] = goodSkuList.get(i).getSalesPrice();
		}
		Long[] arr = getMinMaxPrice(prices);
		if (null == arr) {
			return null;
		}
		if (arr[0].equals(arr[1])) {
			return cover(arr[0], 1);
		} else {
			return cover(arr[0], arr[1], 1);
		}
	}

	public String[] getPriceStrArray() {
		if (null == goodSkuList || goodSkuList.isEmpty())
			return null;
		Long[] prices = new Long[goodSkuList.size()];
		for (int i = 0; i < goodSkuList.size(); i++) {
			prices[i] = goodSkuList.get(i).getSalesPrice();
		}
		Long[] arr = getMinMaxPrice(prices);
		String[] result = new String[2];
		if (null == arr) {
		} else if (arr[0].equals(arr[1])) {
			result[0] = cover(arr[0] * discountRate, 2);
			result[1] = cover(arr[0] * retailLowPercent, arr[0]
					* retailHighPercent, 2);
		} else {
			result[0] = cover(arr[0] * discountRate, arr[1] * discountRate, 2);
			result[1] = cover(arr[0] * retailLowPercent, arr[1]
					* retailHighPercent, 2);
		}
		return result;
	}

	public static String cover(Long l1, Long l2, Integer step) {
		return new StringBuilder(converFentoYuan(l1,step))
				.append("<br/>~<br/>")
				.append(converFentoYuan(l2, step)).toString();
	}

	public static String cover(Long l1, Integer step) {
		return new StringBuilder(converFentoYuan(l1, step))
				.toString();
	}
	
	//保留两位小数  step:100的几次幂
	private static String converFentoYuan(Long fen, Integer step) {
		return new BigDecimal(fen).divide(new BigDecimal(Math.pow(100, step))).setScale(2).toString();
	}
	
}
