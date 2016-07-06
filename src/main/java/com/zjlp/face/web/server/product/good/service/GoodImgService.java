package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;

public interface GoodImgService {
	/**
	 * 新增或修改商品图片
	 * @Title: addGoodImg 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodImg
	 * @date 2015年3月26日 上午11:38:55  
	 * @author Administrator
	 */
	void addOrEdit(GoodImg goodImg);
	/**
	 * 根据主键删除商品图片
	 * @Title: deleteById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年3月26日 上午11:40:07  
	 * @author Administrator
	 */
	void deleteById(Long id);
	
	/**
	 * 删除商品图片
	 * @Title: deleteByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @date 2015年3月26日 上午11:42:05  
	 * @author Administrator
	 */
	void deleteByGoodId(Long goodId);
	
	/**
	 * 主键查询对象
	 * @Title: getById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月26日 上午11:41:37  
	 * @author Administrator
	 */
	GoodImg getById(Long id);
	
	/**
	 * 
	 * @Title: getFirst 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月26日 上午11:42:01  
	 * @author Administrator
	 */
	GoodImg getFirst(Long goodId);
	
	/**
	 * 查询商品所有图片
	 * @Title: findByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param type TODO
	 * @return
	 * @date 2015年3月26日 上午11:41:21  
	 * @author Administrator
	 */
	List<GoodImg> findByGoodIdAndType(Long goodId, Integer type);
	
	/**
	 * 查询商品所有缩略图片
	 * @Title: findZoomByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param type TODO
	 * @return
	 * @date 2015年3月26日 上午11:41:21  
	 * @author Administrator
	 */
	List<GoodImgVo> findZoomByGoodIdAndType(Long goodId, Integer type);

}
