package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;

/**
 * 商品分类基础服务
 * @ClassName: ShopTypeService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月16日 下午1:49:17
 */
public interface ShopTypeService {
	
	/**
	 * 主键查询商品分类
	 * @Title: getShopTypeById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月16日 下午2:57:26  
	 * @author dzq
	 */
	ShopType getShopTypeById(Long id);
	
	/**
	 * 新增商品分类
	 * @Title: addShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopType
	 * @date 2015年3月16日 下午1:49:29  
	 * @author dzq
	 */
	void addShopType(ShopType shopType);
	
	/**
	 * 编辑商品分类
	 * @Title: editShoptype 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopType
	 * @date 2015年3月16日 下午1:49:53  
	 * @author dzq
	 */
	void editShopType(ShopType shopType);
	
	/**
	 * 删除店铺分类
	 * @Title: deleteShopTypeById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @throws GoodException
	 * @date 2015年3月16日 下午3:01:48  
	 * @author dzq
	 */
	void deleteShopTypeById(Long id);
	
	/**
	 * 根据商品ID删除关联
	 * @Title: deleteAllGoodShopTypeByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @date 2014年7月22日 下午5:54:47  
	 * @author dzq
	 */
	void deleteAllGoodShopTypeByGoodId(Long goodId);
	
	/**
	 * 商品主键查询商品分类关系信息 
	 * @Title: getGoodShopTypeRelationByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月11日 下午8:14:26  
	 * @author dzq
	 */
	List<GoodShopTypeRelation> findGoodShopTypeRelationByGoodId(Long goodId);
	
	/**
	 * 商品分类主键查询商品分类关系信息  
	 * @Title: getGoodShopTypeRelationByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月11日 下午8:14:26  
	 * @author dzq
	 */
	List<GoodShopTypeRelation> findGoodShopTypeRelationByShopTypeId(Long shopTypeId);
	
	/**
	 * 分页查询商品分类信息
	 * @Title: findPageShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopType
	 * @param pagination
	 * @return
	 * @date 2015年3月16日 下午2:41:13  
	 * @author dzq
	 */
	Pagination<ShopTypeVo> findPageShopType(ShopTypeVo shopTypeVo,
			Pagination<ShopTypeVo> pagination);

	/**
	 * 查询店铺已选择商品的分类信息
	 * @Title: findShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param goodId
	 * @return
	 * @date 2015年3月16日 下午4:39:06  
	 * @author Administrator
	 */
	List<ShopTypeVo> findShopType(String shopNo, Long goodId);

	List<ShopTypeVo> findShopType(ShopTypeVo shopTypeVo);
	
	/**
	 * @Description: 预约商品分类(只查询有商品的分类)
	 * @param shopTypeVo
	 * @return
	 * @date: 2015年4月23日 下午6:01:48
	 * @author: zyl
	 */
	List<ShopTypeVo> findAppointShopType(ShopTypeVo shopTypeVo);
	
}
