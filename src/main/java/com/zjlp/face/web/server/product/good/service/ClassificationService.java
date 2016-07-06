package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.ClassificationException;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.vo.ClassificationVo;

/**
 * 商品类目基础服务
 * @ClassName: ClassificationService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月16日 下午1:55:32
 */
public interface ClassificationService {
	
	/**
	 * 根据主键查询商品分类信息
	 * @Title: getClassificationById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月16日 下午1:55:13  
	 * @author dzq
	 */
	Classification getClassificationById(Long id);
	
	/**
	 * 根据父级ID查询商品类目列表
	 * @Title: findClassificationByPid 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pid 父级ID
	 * @param level 等级
	 * @return
	 * @date 2015年3月16日 下午1:21:12  
	 * @author dzq
	 */
	List<Classification> findClassificationByPid(Long pid,Integer level);
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
	Boolean hasProtocolClassification(Long classificationId);
	/**
	 * 获得最近一次使用的类目
	 * @Title: getLatestClassification 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年3月31日 上午9:32:30  
	 * @author Administrator
	 */
	ClassificationVo getLatestClassification(String shopNo);
	/**
	 * 根据类目ID获取类目名称字符链条
	 * 如 普通商品>服装类>鞋子
	 * @Title: getClassificationStringById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param classificationId 第三级类目ID
	 * @return
	 * @date 2015年3月31日 上午9:38:38  
	 * @author Administrator
	 */
	ClassificationVo getClassificationVoById(Long classificationId);

}
