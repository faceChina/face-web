package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodProperty;

/**
 * 商品属性基础服务层
 * @ClassName: GoodPropertyService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月17日 下午4:15:44
 */
public interface GoodPropertyService {
	
	/**
	 * 查询商品属性
	 * @Title: findGoodPropertiesSkuByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月17日 下午4:17:47  
	 * @author Administrator
	 */
	List<GoodProperty> findGoodProperties(GoodProperty goodProperty);
	/**
	 * 查询商品销售属性
	 * @Title: findGoodPropertiesSkuByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月17日 下午4:17:47  
	 * @author Administrator
	 */
	List<GoodProperty> findGoodSalesProperties(GoodProperty salesGoodProperty);
	/**
	 * 查询商品输入的并且非关键的属性
	 * @Title: findInputNotKeyGoodProperties 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param gnotKeyoodProperty
	 * @return
	 * @date 2015年3月26日 下午5:04:20  
	 * @author Administrator
	 */
	List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty);
	
	/**
	 * 查询商品输入的销售属性
	 * @Title: findSalesInputKeyGoodProperties 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesInputProperty
	 * @return
	 * @date 2015年5月20日 下午3:46:39  
	 * @author dzq
	 */
	List<GoodProperty> findSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty);
	/**
	 * 根据标准属性值ID及商品ID查询商品属性
	 * @Title: getGoodPropertiesByGoodIdAndPropValueId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param propValueId
	 * @return
	 * @date 2015年4月3日 上午1:35:47  
	 * @author Administrator
	 */
	GoodProperty getGoodPropertiesByGoodIdAndPropValueId(Long goodId,
			Long propValueId);
	/**
	 * 根据主键查询商品属性对象
	 * @Title: getGoodPropertyById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年4月7日 下午2:41:10  
	 * @author Administrator
	 */
	GoodProperty getGoodPropertyById(Long id);
	
	List<GoodProperty> findGoodPropertyListByGoodId(Long goodId);


}
