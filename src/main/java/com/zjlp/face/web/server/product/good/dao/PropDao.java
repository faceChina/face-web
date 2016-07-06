package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Prop;
/**
 * 商品规格属性持久层
 * @ClassName: PropertyDefinitionDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午2:02:24
 */
public interface PropDao {
	
	void add(Prop prop);
	
	void edit(Prop prop);
	
	Prop getById(Long id);
	
	void delete(Long id);

	List<Prop> findPropsByClassificationId(Long classificationId);

	Integer hasSalesProp(Long classificationId);

}
