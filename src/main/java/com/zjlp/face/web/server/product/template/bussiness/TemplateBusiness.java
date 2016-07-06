package com.zjlp.face.web.server.product.template.bussiness;

import java.util.List;

import com.zjlp.face.web.exception.ext.TemplateException;
import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.OwTemplate;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.domain.vo.ModularColorVo;
import com.zjlp.face.web.server.product.template.domain.vo.TemplateVo;

/**
 * 模板业务层
 * @ClassName: TemplateBusiness 
 * @Description: (模板业务层) 
 * @author ah
 * @date 2015年3月16日 上午9:47:29
 */
public interface TemplateBusiness {
	
	/**
	 * 初始模板相关数据 模板 模块 轮播图
	 * @Title: initTemplate 
	 * @Description: (用于商务后台新增产品时初始模板诗句) 
	 * @param shopNo   店铺编号
	 * @param shopType 产品类型 1：官网 2：商城 3：脸谱
	 * @throws TemplateException
	 * @date 2014年7月26日 上午10:27:42  
	 * @author Administrator
	 */
	void initTemplate(String shopNo,Integer shopType);
	
	/**
	 * 根据模板场景 获取模板集合
	 * @Title: findOwTemplateBySubCode 
	 * @Description: (根据模板场景 获取模板集合) 
	 * @param subCode
	 * @param classification
	 * @return
	 * @throws TemplateException
	 * @date 2015年3月16日 上午10:30:48  
	 * @author ah
	 */
	List<OwTemplateDto> findOwTemplateBySubCode(String subCode, Integer classification) throws TemplateException;
	
	/**
	 * 选择模板
	 * @Title: switchTemplate 
	 * @Description: (选择模板) 
	 * @param shopNo
	 * @param code
	 * @throws TemplateException
	 * @date 2015年3月16日 上午10:36:08  
	 * @author ah
	 */
	void switchTemplate(String shopNo, String code) throws TemplateException;
	
	/**
	 * 查询当前首页模板
	 * @Title: getCurrentHomePageOwTemplate 
	 * @Description: (查询当前首页模板) 
	 * @param shopNo
	 * @param shopType
	 * @return
	 * @throws TemplateException
	 * @date 2015年3月19日 下午5:09:11  
	 * @author ah
	 */
	OwTemplateDto getCurrentHomePageOwTemplate(String shopNo, Integer shopType) throws TemplateException;
	
	/**
	 * 查询商品分类页模板
	 * @Title: getCurrentGoodTypePageOwTemplate 
	 * @Description: (查询商品分类页模板) 
	 * @param shopNo
	 * @param shopType
	 * @return
	 * @throws TemplateException
	 * @date 2015年3月20日 下午2:41:16  
	 * @author ah
	 */
	OwTemplateDto getCurrentGoodTypePageOwTemplate(String shopNo, Integer shopType) throws TemplateException;
	
	/**
	 * 查询免费店铺当前首页模板
	 * @Title: getCurrentHomePageOwTemplateForFree 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param shopType
	 * @return
	 * @throws TemplateException
	 * @date 2015年5月12日 下午4:44:33  
	 * @author ah
	 */
	OwTemplate getCurrentHomePageOwTemplateForFree(String shopNo, Integer shopType) throws TemplateException;
	
	/**=================================模块=======================================*/
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
	List<Modular> findModularByOwTemplateId(Long owTemplateId,Integer modularType) throws TemplateException;
	
	/***
	 * 保存单个模块
	 * @Title: saveModuarl 
	 * @Description: (根据ID判断是新增还是修改) 
	 * @param modular
	 * @throws TemplateException
	 * @date 2014年7月28日 下午5:50:52  
	 * @author Administrator
	 */
	Long saveModuarl(Modular modular,OwTemplateDto owTemplateDto, String shopNo,Long userId) throws TemplateException;
	
	/**
	 * 删除模块
	 * @Title: deleteModular
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @throws TemplateException
	 * @return void
	 * @author phb
	 * @date 2015年3月26日 下午1:23:19
	 */
	void deleteModular(Long id,OwTemplateDto owTemplateDto) throws TemplateException;
	
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
	 * 模板排序
	 * @Title: sortModular 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subId
	 * @param tarId
	 * @throws TemplateException
	 * @date 2014年7月29日 下午8:44:42  
	 * @author Administrator
	 */
	void sortModular(Long subId,Long tarId) throws TemplateException;
	
	/**
	 * 根据主键查询模块
	 * @Title: getModularById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @throws TemplateException
	 * @date 2015年3月21日 下午4:57:13  
	 * @author ah
	 */
	Modular getModularById(Long id) throws TemplateException;
	
	/**
	 * 更换店招
	 * @Title: changeShopStrokes
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param owTemplateDto
	 * @throws TemplateException
	 * @return void
	 * @author phb
	 * @date 2015年3月26日 下午2:50:59
	 */
	void changeShopStrokes(OwTemplateDto owTemplateDto) throws TemplateException;
	
	/**==================================轮播图======================================*/
	/**
	 * 根据模板编号，查询轮播图列表
	 * @Title: findCarouselDiagramByOwTemplateId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param owTemplateId
	 * @return
	 * @throws TemplateException
	 * @date 2014年7月30日 下午4:02:53  
	 * @author Administrator
	 */
	List<CarouselDiagramDto> findCarouselDiagramByOwTemplateId(Long owTemplateId) throws TemplateException;
	
	/**
	 * 根据轮播图编号查询轮播图
	 * @Title: getCarouselDiagramById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @throws TemplateException
	 * @date 2014年7月30日 下午4:09:07  
	 * @author Administrator
	 */
	CarouselDiagram getCarouselDiagramById(Long id) throws TemplateException;
	
	/**
	 * 新增或修改轮播图
	 * @Title: addOrEditCarouselDiagram 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param carouselDiagram
	 * @param owTemplateHp
	 * @return
	 * @throws TemplateException
	 * @date 2014年7月30日 下午5:59:40  
	 * @author Administrator
	 */
	Long addOrEditCarouselDiagram(CarouselDiagram carouselDiagram,OwTemplateDto owTemplateHp, String shopNo) throws TemplateException;
	
	/**
	 * 批量新增或修改轮播图
	 * @Title: addOrEditCarouselDiagram 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param list
	 * @param shopNo
	 * @throws TemplateException
	 * @date 2014年7月30日 下午5:59:46  
	 * @author Administrator
	 */
	void addOrEditCarouselDiagram(List<CarouselDiagramDto> list, String shopNo,Long userId,OwTemplateDto owTemplateHp) throws TemplateException;
	
	/**
	 * 删除轮播图
	 * @Title: deleteCarouselDiagram 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param OwTemplateDto
	 * @throws TemplateException
	 * @date 2014年7月30日 下午6:02:50  
	 * @author Administrator
	 */
	void deleteCarouselDiagramHp(Long id,OwTemplateDto owTemplateHp,Long userId,String shopNo) throws TemplateException;
	
	/**
	 * 删除分类页轮播图
	 * @Title: deleteCarouselDiagramGt 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param owTemplateGt
	 * @throws TemplateException
	 * @date 2014年8月26日 下午2:27:17
	 * @author Administrator
	 */
	void deleteCarouselDiagramGt(Long id,OwTemplateDto owTemplateGt,Long userId,String shopNo) throws TemplateException;
	
	/**
	 * 根据商铺获取模板信息
	 * @param shopNo
	 * @return
	 * @throws IndexExceptionEnum
	 */
	TemplateVo getTemplateDtoByShop(String shopNo, Integer shopType) throws TemplateException;

	/**
	 * 根据模板id查询模板细项
	 * @Title: getTemplateDetailByTemplateId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月26日 下午8:11:15  
	 * @author ah
	 */
	TemplateDetail getTemplateDetailByTemplateId(Long id);

	/**
	 * 编辑模板细项
	 * @Title: editTemplateDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateDetail
	 * @param shopNo
	 * @param userId
	 * @date 2015年3月27日 上午9:22:17  
	 * @author ah
	 */
	void editTemplateDetail(TemplateDetail templateDetail, String shopNo, Long userId);
	
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
	List<Modular> findModularByCode(String code,Integer modularType);
	
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
	List<CarouselDiagramDto> findCarouselDiagramByCode(String code);
	
	/**
	 * 模块颜色集合
	 * @Title: findModularColorVo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param modulars
	 * @return
	 * @return List<ModularColorVo>
	 * @author phb
	 * @date 2015年3月28日 下午3:24:52
	 */
	List<ModularColorVo> findModularColorVo(List<Modular> modulars);
}
