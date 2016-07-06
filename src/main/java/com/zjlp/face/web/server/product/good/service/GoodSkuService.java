package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;


public interface GoodSkuService {
	
	void add(GoodSku goodSku);
	
	void edit(GoodSku goodSku);
	
	/**
	 * 查询所有商品，包含状态为删除的
	 * @Title: getAllById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年5月26日 上午9:11:10  
	 * @author dzq
	 */
	GoodSku getById(Long id);
	
	/**
	 * 根据商品ID查询商品SKU信息,SKU无图时取主图(有状态的查询)
	 * @Title: findGoodSkusByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月16日 下午12:52:42  
	 * @author dzq
	 */
	List<GoodSku> findGoodSkusByGoodId(Long goodId);
	/**
	 * 根据商品ID查询商品SKU信息（只查询上线的）
	 * @Title: findAllGoodSkuByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年4月7日 上午11:16:47  
	 * @author Administrator
	 */
	List<GoodSku> findAllGoodSkuByGoodId(Long goodId);
	/**
	 * 根据商品ID及SKU查询SKU
	 * @Title: selectGoodskuByGoodIdAndPrprerty 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param skuProperties
	 * @return
	 * @author Administrator
	 */
	GoodSku selectGoodskuByGoodIdAndPrprerty(Long goodId, String skuProperties);
	
	/**
	 * 根据商品ID及属性ID查询Sku
	 * @Title: findGoodSkuListByGoodIdAndProerptyIdStr 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param proerptyIdStr  ;+propertyId+;
	 * @return
	 * @date 2015年4月3日 上午1:11:17  
	 * @author Administrator
	 */
	List<GoodSku> findGoodSkuListByGoodIdAndProerptyIdStr(Long goodId, String proerptyIdStr);

	List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Long appointmentId, Long shopTypeId);

	List<GoodSku> findGoodSkuImgListByGoodId(Long goodId);
	
}
