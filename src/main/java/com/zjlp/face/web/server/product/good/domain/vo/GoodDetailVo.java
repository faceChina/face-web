package com.zjlp.face.web.server.product.good.domain.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;

public class GoodDetailVo {

	private Good good;

	private Map<String, List<GoodProperty>> salesPropMap;

	private Integer salesPropSize = 0;

	private List<GoodProperty> salesPropList;

	private List<GoodProperty> notKeyPropList;

	private List<GoodSku> skuList;
	/** 商品图片 */
	private List<GoodImgVo> goodImgs;

	private String skuJson;
	/** 购物车数量显示 */
	private String cartCount;

	public List<GoodImgVo> getGoodImgs() {
		return goodImgs;
	}

	public void setGoodImgs(List<GoodImgVo> goodImgs) {
		this.goodImgs = goodImgs;
	}

	public String getCartCount() {
		return cartCount;
	}

	public void setCartCount(String cartCount) {
		this.cartCount = cartCount;
	}

	public String getObjectJson() {
		JSONObject allObject = new JSONObject();
		Long classificationId=this.getGood().getClassificationId();
		// salesPropMap---->Json
		JSONObject goodSalesPro = new JSONObject();
		if (this.getSalesPropMap() != null) {
			Map<String, List<GoodProperty>> map = this.getSalesPropMap();
			for (Entry<String, List<GoodProperty>> entry : map.entrySet()) {
				Iterator<GoodProperty> item = entry.getValue().iterator();
				JSONObject goodItem = null;
				while (item.hasNext()) {
					goodItem = new JSONObject();
					GoodProperty goodProperty = item.next();
					goodItem.put("id", goodProperty.getId());
					goodItem.put("propValueName", goodProperty.getPropValueName());
					goodItem.put("propValueAlias", goodProperty.getPropValueAlias());
				}
				goodSalesPro.put(entry.getKey(), goodItem);
			}
		}
		// notKeyPropList---->Json
		List<String> notKeyPropList = new ArrayList<String>();
		if (this.getNotKeyPropList() != null) {
			Iterator<GoodProperty> item = this.getNotKeyPropList().iterator();
			JSONObject notKeyPropItem = null;
			while (item.hasNext()) {
				GoodProperty goodPropery = item.next();
				notKeyPropItem = new JSONObject();
				notKeyPropItem.put("id", goodPropery.getId());
				notKeyPropItem.put("propValueName", goodPropery.getPropName());
				notKeyPropItem.put("propValueAlias",
						goodPropery.getPropValueAlias());
				notKeyPropList.add(notKeyPropItem.toString());
			}
		}
		// skuList---->Json
		JSONObject goodSkuItems = new JSONObject();
		if (this.getSkuList() != null) {
			List<GoodSku> goodSkuList = this.getSkuList();
			Iterator<GoodSku> item = goodSkuList.iterator();
			while (item.hasNext()) {
				JSONObject goodSkuItem = new JSONObject();
				GoodSku goodSku = item.next();
				String key = goodSku.getSkuProperties();
				goodSkuItem = new JSONObject();
				goodSkuItem.put("id", goodSku.getId());
				goodSkuItem.put("stock", goodSku.getStock());
				goodSkuItem.put("salesPrice", goodSku.getSalesPrice());
				goodSkuItem.put("picturePath", goodSku.getPicturePath());
				if(Constants.CLASSIFICATION_ID_FREE_GOOD.equals(classificationId)) {
				goodSkuItems.put(";"+goodSku.getId().toString()+";",goodSkuItem);
				}else{
					goodSkuItems.put(key == null ? ";"+goodSku.getId().toString()+";" : key,goodSkuItem);
				}
			}
		}
		// data--->Json
		JSONObject data = new JSONObject();
		data.put("goodId", this.getGood().getId());
		data.put("classificationId", this.getGood().getClassificationId());
		data.put("skusize", this.getSkuList() == null ? 0 : this.getSkuList()
				.size());
		data.put("skulist", goodSkuItems);
		if(Constants.CLASSIFICATION_ID_FREE_GOOD.equals(classificationId)) {
			data.put("salesPropMapSize", 1);
		}else{
			data.put("salesPropMapSize", this.getSalesPropMap() == null ? 0 : this
				.getSalesPropMap().size());
		}
		data.put("salesPropMap", goodSalesPro);
		data.put("notKeyPropList", this.getNotKeyPropList() == null ? "[]"
				: notKeyPropList);
		// 完整json
		allObject.put("data", data);
		allObject.put("code", 0);
		allObject.put("msg", "SUCCESS");
		this.skuJson = allObject.toString();
		return skuJson;
	}
	public String getSkuJson() {
		return skuJson;
	}
	public void setSkuJson(String skuJson) {
		this.skuJson = skuJson;
	}

	public List<GoodProperty> getSalesPropList() {
		return salesPropList;
	}

	public Map<String, List<GoodProperty>> getSalesPropMap() {
		return salesPropMap;
	}

	public void setSalesPropMap(Map<String, List<GoodProperty>> salesPropMap) {
		this.salesPropMap = salesPropMap;
	}

	public void setSalesPropList(List<GoodProperty> salesPropList) {
		this.salesPropList = salesPropList;
		if (null != salesPropList && !salesPropList.isEmpty()) {
			salesPropMap = new HashMap<String, List<GoodProperty>>();
			for (GoodProperty gp : salesPropList) {
				// 类型存在
				if (salesPropMap.containsKey(gp.getPropName())) {
					salesPropMap.get(gp.getPropName()).add(gp);
				} else {
					List<GoodProperty> tempNews = new ArrayList<GoodProperty>();
					tempNews.add(gp);
					salesPropMap.put(gp.getPropName(), tempNews);
				}
			}
		}
	}

	public void setSalesPropListToSort(List<GoodProperty> salesPropList,Map<String, List<GoodProperty>> map) {
		this.salesPropList = salesPropList;
		if (null != salesPropList && !salesPropList.isEmpty() && map!=null) {
			salesPropMap = new LinkedHashMap<String, List<GoodProperty>>(map);
			for (GoodProperty gp : salesPropList) {
				// 类型存在
				if (salesPropMap.containsKey(gp.getPropName())) {
					salesPropMap.get(gp.getPropName()).add(gp);
				} else {
					List<GoodProperty> tempNews = new ArrayList<GoodProperty>();
					tempNews.add(gp);
					salesPropMap.put(gp.getPropName(), tempNews);
				}
			}
		}
	}

	public Integer getSalesPropSize() {
		if (null == salesPropMap || salesPropMap.isEmpty()) {
			return salesPropSize;
		}
		return salesPropMap.size();
	}

	public void setSalesPropSize(Integer salesPropSize) {
		this.salesPropSize = salesPropSize;
	}

	public List<GoodProperty> getNotKeyPropList() {
		return notKeyPropList;
	}

	public void setNotKeyPropList(List<GoodProperty> notKeyPropList) {
		this.notKeyPropList = notKeyPropList;
	}

	public List<GoodSku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<GoodSku> skuList) {
		this.skuList = skuList;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

}
