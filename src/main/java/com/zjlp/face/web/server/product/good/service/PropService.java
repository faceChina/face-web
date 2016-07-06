package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
/**
 * 标准属性基础服务类
 * @ClassName: PropService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月16日 下午3:08:13
 */
public interface PropService {
	
	/**
	 * 根据主键查询标准属性
	 * @Title: getPropById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 标准属性ID
	 * @return
	 * @date 2015年3月16日 下午12:57:38  
	 * @author dzq
	 */
	Prop getPropById(Long id);
	/**
	 * 根据主键查询标准属性值
	 * @Title: getPropValueById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月25日 下午10:38:06  
	 * @author Administrator
	 */
	PropValue getPropValueById(Long id);
	
	/**
	 * 根据标准属性主键查询属性值
	 * @Title: findPropValuesByPropId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param propId
	 * @return
	 * @date 2015年3月16日 下午12:59:35  
	 * @author dzq
	 */
	List<PropValue> findPropValuesByPropId(Long propId);
	
	/**
	 * 是否有销售属性
	 * @Title: hasSalesProp 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId
	 * @return
	 * @date 2015年3月30日 下午2:01:48  
	 * @author Administrator
	 */
	Boolean hasSalesProp(Long classificationId);
	
	/**
	 * 查询类目下的属性关系
	 * @Title: findPropsByClassificationId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId
	 * @return
	 * @date 2015年3月16日 下午1:33:17  
	 * @author dzq
	 */
	List<Prop> findPropsByClassificationId(Long classificationId);
}
