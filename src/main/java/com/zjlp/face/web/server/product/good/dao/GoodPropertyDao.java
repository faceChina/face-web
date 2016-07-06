package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodProperty;
/**
 * 商品规格属性表
 * @ClassName: GoodPropertyDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午2:29:21
 */
public interface GoodPropertyDao {

	void add(GoodProperty goodProperty);
	
	void edit(GoodProperty goodProperty);
	
	GoodProperty getById(Long id);
	
	void delete(Long id);
	
	List<GoodProperty> findGoodPropertyListByGoodId(Long goodId);

	List<GoodProperty> findGoodProperties(GoodProperty goodProperty);

	GoodProperty getByGoodIdAndPropValueId(Long goodId,Long propValueId);

	List<GoodProperty> findGoodSalesProperties(GoodProperty salesGoodProperty);

	List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty);

	void removeById(Long id);

	List<GoodProperty> findSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty);

}
