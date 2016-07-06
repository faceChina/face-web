package com.zjlp.face.web.server.product.good.business;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.exception.ext.ClassificationException;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
import com.zjlp.face.web.server.product.good.domain.vo.ClassificationVo;
/**
 * 商品类目业务服务层
 * @ClassName: ClassificationBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月16日 下午2:31:48
 */
public interface ClassificationBusiness {
	
	
	/**
	 * 根据主键查询商品分类信息
	 * @Title: getClassificationById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月16日 下午1:55:13  
	 * @author dzq
	 */
	Classification getClassificationById(Long id) throws ClassificationException;
	
	/**
	 * 根据父级ID查询商品类目列表
	 * @Title: findClassificationByPid 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pid 父级ID
	 * @param level 类目等级
	 * @return
	 * @date 2015年3月16日 下午1:21:12  
	 * @author dzq
	 */
	List<Classification> findClassificationByPid(Long pid,Integer level) throws ClassificationException;
	
	/**
	 * 查询类目下的属性关系
	 * @Title: findPropsByClassificationId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId 类目ID
	 * @return
	 * @date 2015年3月16日 下午1:33:17  
	 * @author dzq
	 */
	List<Prop> findPropsByClassificationId(Long classificationId) throws ClassificationException;
	/**
	 * 查询属性下的属性值
	 * @Title: findPropValuesByPropId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param propId 属性ID
	 * @return
	 * @throws ClassificationException
	 * @date 2015年3月20日 上午10:59:57  
	 * @author Administrator
	 */
	List<PropValue> findPropValuesByPropId(Long propId) throws ClassificationException;
	
	/**
	 * 查询类目下所有属性的集合
	 * @Title: findAllPropByClassificationId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId
	 * @return
	 * @throws ClassificationException
	 * @date 2015年3月20日 上午11:02:58  
	 * @author Administrator
	 */
	Map<Prop, List<PropValue>> findAllPropByClassificationId(Long classificationId) throws ClassificationException;
	
	/**
	 * 类目是否有销售属性
	 * @Title: hasSalesProp 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId
	 * @return
	 * @date 2015年3月30日 下午2:01:48  
	 * @author Administrator
	 */
	Boolean hasSalesProp(Long classificationId) throws ClassificationException;
	
	/**
	 * 类目是否为协议商品
	 * @Title: hasProtocolClassification 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId
	 * @return
	 * @throws ClassificationException
	 * @date 2015年3月30日 下午3:06:23  
	 * @author Administrator
	 */
	Boolean hasProtocolClassification(Long classificationId)throws ClassificationException;
	/**
	 * 获得最近一次使用的类目
	 * @Title: getLatestClassification 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年3月31日 上午9:30:22  
	 * @author Administrator
	 */
	ClassificationVo getLatestClassification(String shopNo)throws ClassificationException;
	
	/**
	 * 根据子类查询类目全部名称(父类名称>子类名称>XXXX)
	 * @param child
	 * @param name
	 * @return
	 * @throws ClassificationException
	 */
	String getClassificationName(Classification child, String name)throws ClassificationException;

	/**
	 * 根据类目ID,生成商品属性集合
	 * @param classificationId
	 * @return
	 * @throws ClassificationException
	 */
	Map<String, List<GoodProperty>> findAllPropNameByClassificationId(Long classificationId) throws ClassificationException;
}
