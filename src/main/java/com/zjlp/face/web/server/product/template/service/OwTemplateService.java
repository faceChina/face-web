package com.zjlp.face.web.server.product.template.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.TemplateException;
import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.OwTemplate;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;

public interface OwTemplateService {

	/**
	 * 初始模板相关数据 模板 模块 轮播图
	 * @Title: initTemplate 
	 * @Description: (初始模板相关数据 模板 模块 轮播图) 
	 * @param shopNo
	 * @param shopType
	 * @date 2015年3月20日 下午5:26:35  
	 * @author ah
	 */
	void initTemplate(String shopNo, Integer shopType);
	/**
	 * 添加
	 * @Title: addOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param owTemplate
	 * @return void
	 * @author phb
	 * @date 2015年3月12日 下午5:26:15
	 */
	void addOwTemplate(OwTemplate owTemplate);
	/**
	 * 修改模版状态
	 * @Title: editOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param status
	 * @return void
	 * @author phb
	 * @date 2015年3月12日 下午5:26:20
	 */
	void editOwTemplateStatus(Long id,Integer status);
	
	/**
	 * 获取官网首页热门模版数据
	 * @Title: findWgwHpHotOwTemplate
	 * @Description: (用于首页展示)
	 * @return
	 * @return List<OwTemplateDto>
	 * @author phb
	 * @date 2015年3月13日 下午5:11:37
	 */
	List<OwTemplateDto> findWgwHpHotOwTemplate();
	/**
	 * 获取官网首页其他模版数据
	 * @Title: findWgwHpOtherOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @return List<OwTemplateDto>
	 * @author phb
	 * @date 2015年3月13日 下午5:12:29
	 */
	List<OwTemplateDto> findWgwHpOtherOwTemplate();
	/**
	 * 获取官网商城首页热门模版数据
	 * @Title: findWgwScHpHotOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @return List<OwTemplateDto>
	 * @author phb
	 * @date 2015年3月13日 下午5:12:53
	 */
	List<OwTemplateDto> findWgwScHpHotOwTemplate();
	/**
	 * 获取官网商城首页其他模版数据
	 * @Title: findWgwScHpOtherOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @return List<OwTemplateDto>
	 * @author phb
	 * @date 2015年3月13日 下午5:13:14
	 */
	List<OwTemplateDto> findWgwScHpOtherOwTemplate();
	/**
	 * 获取商城首页热门模版数据
	 * @Title: findWscHpHotOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @return List<OwTemplateDto>
	 * @author phb
	 * @date 2015年3月13日 下午5:17:42
	 */
	List<OwTemplateDto> findWscHpHotOwTemplate();
	/**
	 * 获取商城首页其他模版数据
	 * @Title: findWscHpOtherOwTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @return List<OwTemplateDto>
	 * @author phb
	 * @date 2015年3月13日 下午5:17:57
	 */
	List<OwTemplateDto> findWscHpOtherOwTemplate();
	/**
	 * 切换模板
	 * @Title: switchTemplate 
	 * @Description: (切换模板) 
	 * @param shopNo
	 * @param code
	 * @date 2015年3月19日 下午3:30:18  
	 * @author ah
	 */
	void switchTemplate(String shopNo, String code);
	/**
	 * 获取当前模板
	 * @Title: getCurrentTemplate 
	 * @Description: (获取当前模板) 
	 * @param shopNo
	 * @param shopType
	 * @return
	 * @date 2015年3月20日 下午1:50:56  
	 * @author ah
	 */
	OwTemplate getCurrentTemplate(String shopNo, Integer shopType);
	/**
	 * 查询模板细项
	 * @Title: getTemplateDetailByTemplateId 
	 * @Description: (查询模板细项) 
	 * @param tempalteId
	 * @return
	 * @date 2015年3月20日 下午1:57:11  
	 * @author ah
	 */
	TemplateDetail getTemplateDetailByTemplateId(Long tempalteId);
	/**=================================模块========================================*/
	/**
	 * 获取模板基础数据
	 * @Title: findBaseModularBySubCode 
	 * @Description: (用于选择模块数据集合) 
	 * @param subCode (WGW SC_WGW WSC WLP)
	 * @return
	 * @throws TemplateException
	 * @date 2014年7月25日 下午7:36:21  
	 * @author Administrator
	 */
	List<Modular> findBaseModular(String subCode) throws TemplateException;
	
	/**
	 * 根据模板编号，查询所有模块
	 * @Title: findModularByOwTemplateId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param owTemplateId
	 * @return
	 * @throws TemplateException
	 * @date 2014年7月28日 下午5:50:30  
	 * @author Administrator
	 */
	List<Modular> findModularByOwTemplateId(Long owTemplateId) throws TemplateException;
	
	/***
	 * 保存单个模块
	 * @Title: saveModuarl 
	 * @Description: (根据ID判断是新增还是修改) 
	 * @param modular
	 * @throws TemplateException
	 * @date 2014年7月28日 下午5:50:52  
	 * @author Administrator
	 */
	Long saveModuarl(Modular modular, String shopNo) throws TemplateException;
	
	/**
	 * 修改模块图片
	 * @Title: editModularImgPath
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param modular
	 * @throws TemplateException
	 * @return void
	 * @author phb
	 * @date 2015年3月25日 下午2:05:09
	 */
	void editModularImgPath(Modular modular) throws TemplateException;
	
	/**
	 * 重置模块
	 * @Title: resetModular 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @throws TemplateException
	 * @date 2014年7月28日 下午5:51:44  
	 * @author Administrator
	 */
	void resetModular(Long owTemplateId) throws TemplateException;
	
	/**
	 * 删除模块
	 * @Title: deleteModular 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @throws TemplateException
	 * @date 2014年7月28日 下午5:56:18  
	 * @author Administrator
	 */
	void deleteModular(Long id) throws TemplateException;
	
	/**
	 * 互换SORT
	 * @Title: sortModular 
	 * @Description: (用于排序) 
	 * @param subId
	 * @param tarId
	 * @throws TemplateException
	 * @date 2014年7月29日 下午8:21:14  
	 * @author Administrator
	 */
	void sortModular(Long subId,Long tarId) throws TemplateException;
	
	/**
	 * 根据模块编号获取模块
	 * @Title: getModularById 
	 * @Description: (用于删除的时候验证该ID的模块是不是当前模板的模块) 
	 * @param id
	 * @return
	 * @throws TemplateException
	 * @date 2014年7月30日 下午3:54:02  
	 * @author Administrator
	 */
	Modular getModularById(Long id) throws TemplateException;
	
	
	/**===================================轮播图========================================*/
	
	Long addCarouselDiagram(CarouselDiagramDto carouselDiagramDto,String shopNo);
	
	void editCarouselDiagram(CarouselDiagramDto carouselDiagramDto,String shopNo);
	
	/**
	 * 删除轮播图
	 * @Title: deleteCarouselDiagram
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @throws TemplateException
	 * @return void
	 * @author phb
	 * @date 2015年3月23日 上午11:58:16
	 */
	void deleteCarouselDiagram(Long id) throws TemplateException;
	
	/**
	 * 获取轮播图
	 * @Title: getCarouselDiagramById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @return
	 * @throws TemplateException
	 * @return CarouselDiagram
	 * @author phb
	 * @date 2015年3月23日 上午11:58:57
	 */
	CarouselDiagram getCarouselDiagramById(Long id) throws TemplateException;
	
	/**
	 * 查询模版下的轮播图
	 * @Title: findCarouselDiagramByOwTemplateId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param owTemplateId
	 * @return
	 * @throws TemplateException
	 * @return List<CarouselDiagram>
	 * @author phb
	 * @date 2015年3月23日 上午11:59:27
	 */
	List<CarouselDiagramDto> findCarouselDiagramByOwTemplateId(Long owTemplateId) throws TemplateException;
	
	/**
	 * 编辑模板细项
	 * @Title: editTemplateDetail 
	 * @Description: (编辑模板细项) 
	 * @param templateDetail
	 * @date 2015年3月27日 上午9:25:52  
	 * @author ah
	 */
	void editTemplateDetail(TemplateDetail templateDetail);
	
	/**
	 * 查询商品分类模板列表
	 * @Title: findGtOwTemplate 
	 * @Description: (查询商品分类模板列表) 
	 * @return
	 * @date 2015年3月27日 下午2:40:05  
	 * @author ah
	 */
	List<OwTemplateDto> findGtOwTemplate();
	
	/**
	 * 根据模版CODE获取模块
	 * @Title: findModularByCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param code
	 * @return
	 * @return List<Modular>
	 * @author phb
	 * @date 2015年3月28日 上午11:43:20
	 */
	List<Modular> findModularByCode(String code);
	
	/**
	 * 根据模版CODE取轮播图
	 * @Title: findCarouselDiagramByCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param code
	 * @return
	 * @return List<CarouselDiagram>
	 * @author phb
	 * @date 2015年3月28日 上午11:48:24
	 */
	List<CarouselDiagram> findCarouselDiagramByCode(String code);
}
