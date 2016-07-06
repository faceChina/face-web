package com.zjlp.face.web.server.product.good.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ImgTemplateVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;

/**
 * 商品分类业务服务层
 * @ClassName: ShopTypeBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月16日 下午2:32:23
 */
public interface ShopTypeBusiness {
	
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
	 * @date 2015年3月16日 下午2:33:20  
	 * @author Administrator
	 */
	void addShopType(ShopType shopType) throws GoodException;
	
	/**
	 * 修改商品分类
	 * @Title: editShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopType
	 * @throws GoodException
	 * @date 2015年3月16日 下午2:34:29  
	 * @author Administrator
	 */
	void editShopType(ShopType shopType) throws GoodException;
	
	/**
	 * 删除店铺分类
	 * @Title: deleteShopTypeById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @throws GoodException
	 * @date 2015年3月16日 下午3:01:48  
	 * @author Administrator
	 */
	void deleteShopTypeById(Long id) throws GoodException;
	
	/**
	 * 商品分类排序
	 * @Title: sortShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param parseLong
	 * @param parseLong2
	 * @return
	 * @date 2014年7月17日 下午8:57:23  
	 * @author dzq
	 */
	void sortShopType(Long upId, Long downId) throws GoodException;
	
	/**
	 * 获取店铺商品分类
	 * @Title: findShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月16日 下午4:34:07  
	 * @author Administrator
	 */
	List<ShopTypeVo> findShopType(String shopNo) throws GoodException;
	
	/**
	 * 获取店铺商品分类
	 * @Title: findShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月16日 下午4:34:07  
	 * @author Administrator
	 */
	/**
	 * 获取店铺商品分类信息（若返回）
	 * @Title: findShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺号
	 * @param goodId 商品ID
	 * @return
	 * @throws GoodException
	 * @date 2015年3月16日 下午4:35:40  
	 * @author Administrator
	 */
	List<ShopTypeVo> findShopType(String shopNo,Long goodId) throws GoodException;
	
	/**
	 * 获取商品分类的模板图片
	 * @Title: findShopTypeTempImg 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param type
	 * @param color
	 * @param pagination
	 * @return
	 * @throws GoodException
	 * @date 2015年3月23日 下午8:01:02  
	 * @author Administrator
	 */
	Pagination<ImgTemplateVo> findShopTypeTempImg(String type, String color, Pagination<ImgTemplateVo> pagination)
			throws GoodException;
	

	/**
	 * 分页查询商品分类列表
	 * @Title: findShopTypeByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopType
	 * @param pagination
	 * @return
	 * @throws GoodException
	 * @date 2015年3月16日 下午2:36:55  
	 * @author Administrator
	 */
	Pagination<ShopTypeVo> findPageShopType(ShopTypeVo shopType,Pagination<ShopTypeVo> pagination) throws GoodException;
	
	/**
     * @Description: 查询店铺下的商品分类及该分类下的商品，轮播图模板专用
     * @param shopNo
     * @param shopTypeNum
     *            查询的商品分类的数量
     * @param goodNum
     *            每个分类下商品的数量
     * @return
     * @date: 2014年9月23日 下午2:02:46
     * @author: zyl
     */
    List<ShopTypeVo> findShopTypeListAndGoodByShopNo(String shopNo, int shopTypeNum, int goodNum);

	List<ShopTypeVo> findShopType(ShopTypeVo shopTypeVo);
	/**
	 * @Description: 预约商品分类(只查询有商品的分类)
	 * @param shopTypeVo
	 * @return
	 * @date: 2015年4月23日 下午6:00:37
	 * @author: zyl
	 */
	List<ShopTypeVo> findAppointShopType(ShopTypeVo shopTypeVo);
	
	/**
	 * @Description: 手机端通过商品分类查询商品列表:1,供货商查询自有商品;2,代理商查询自有商品和代理商品
	 * @param id
	 * @param goodNum
	 * @return
	 * @date: 2015年5月19日 下午2:02:31
	 * @author: zyl
	 */
	List<ShopTypeVo> findShopTypeListAndGoodForWap(String shopNo, int shopTypeNum, int goodNum);
	
	/**
	 * app端新增商品分类
	 * @Title: addShopType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年8月11日 上午11:18:20  
	 * @author xb
	 */
	Long addShopTypeToApp(String name,String shopNo) throws GoodException;
}
