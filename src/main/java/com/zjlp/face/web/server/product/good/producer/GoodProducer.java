package com.zjlp.face.web.server.product.good.producer;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;

public interface GoodProducer {

	/**
	 * 根据主键查询商品对象
	 * 
	 * @Title: getGoodById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            商品ID
	 * @return Good 商品对象
	 * @date 2015年3月16日 下午12:49:12
	 * @author dzq
	 */
	Good getGoodById(Long id);

	/**
	 * 根据主键查询商品sku对象
	 * 
	 * @Title: getGoodSkuById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param skuId
	 * @return
	 * @date 2015年3月21日 下午4:36:42
	 * @author Administrator
	 */
	GoodSku getGoodSkuById(Long skuId);
	/**
	 * 根据
	 * @Title: getGoodSkuAllById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param skuId
	 * @return
	 * @date 2015年5月26日 上午9:18:33  
	 * @author dzq
	 */
	GoodSku getGoodSkuStatelessById(Long skuId);

	/**
	 * 修改商品库存
	 * 
	 * @Title: editGoodSkuStock
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param skuId
	 * @param stock
	 *            库存增量 正数为增加库存 负数为减少库存
	 * @date 2015年3月21日 下午4:40:28
	 * @author Administrator
	 */
	void editGoodSkuStock(Long skuId, Long stock);

	/**
	 * 查询店铺商品
	 * 
	 * @Title: findGoodsByShopNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @return
	 * @date 2015年3月25日 下午5:14:49
	 * @author Administrator
	 */
	List<Good> findGoodsByShopNo(String shopNo);

	/**
	 * 查询商品销售属性
	 * 
	 * @Title: findGoodPropertiesSkuByGoodId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param goodId
	 * @return
	 * @date 2015年3月17日 下午4:17:47
	 * @author Administrator
	 */
	List<GoodProperty> findGoodProperties(GoodProperty goodProperty);

	/**
	 * 根据商品ID查询商品SKU信息
	 * 
	 * @Title: findGoodSkusByGoodId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param goodId
	 * @return
	 * @date 2015年3月16日 下午12:52:42
	 * @author Administrator
	 */
	List<GoodSku> findGoodSkusByGoodId(Long goodId);

	Classification getClassificationById(Long classificationId);
	
	
	

	List<GoodProperty> findGoodSalesProperties(GoodProperty salesGoodProperty);

	List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty);
	/**
	 * 查询普通商品图片（包含供货商产品库图片）
	 * @Title: findZoomByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年5月14日 上午11:56:55  
	 * @author dzq
	 */
	List<GoodImgVo> findZoomByGoodId(Long goodId);


	void editGood(Good browerGood);
	
	/**
	 * 根据商品ID及SKU属性查询SKU信息
	 * @Title: selectGoodSkuByGoodIdAndPrprerty 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId 商品ID
	 * @param skuProperties 商品销售属性
	 * @return
	 * @date 2015年5月11日 下午3:19:48  
	 * @author dzq
	 */
	GoodSku selectGoodSkuByGoodIdAndPrprerty(Long goodId, String skuProperties);
	
	void addGoodShopTypeRelation(GoodShopTypeRelation relation);
	
	/**
	 * @Description: 查询goodSku列表,图片为空时不进行处理
	 * @param goodId
	 * @return
	 * @date: 2015年5月15日 下午4:36:44
	 * @author: zyl
	 */
	List<GoodSku> findGoodSkuImgListByGoodId(Long goodId);
}
