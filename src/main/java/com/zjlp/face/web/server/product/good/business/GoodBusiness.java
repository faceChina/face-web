package com.zjlp.face.web.server.product.good.business;


import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodDetailVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.trade.order.domain.vo.RspSelectedSkuVo;
/**
 * 商品业务服务层
 * @ClassName: GoodBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月16日 下午2:32:14
 */
public interface GoodBusiness {
	
	/**
	 * 搜索引擎查询商品分类列表
	 * @Title: searchPageGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodVo
	 * @param pagination
	 * @return
	 * @date 2015年3月23日 下午9:09:56  
	 * @author Administrator
	 */
	Pagination<GoodVo> searchPageGood(GoodVo goodVo,Pagination<GoodVo> pagination) throws GoodException;
	
	/**
	 * 查询商品图片列表
	 * @Title: findGoodImgByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param type TODO
	 * @return
	 * @throws GoodException
	 * @date 2015年3月26日 下午2:40:46  
	 * @author Administrator
	 */
	List<GoodImgVo> findGoodImgByGoodIdAndType(Long goodId, Integer type) throws GoodException;
	
	/**
	 * 添加一个商品
	 * @Title: addGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @date 2015年3月16日 下午2:02:41  
	 * @author dzq
	 */
	void addGood(GoodParam goodParam) throws GoodException;
	
	/**
	 * 四阶段新增商品
	 * @Title: addGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @date 2015年7月8日 下午2:02:41  
	 * @author zyl
	 */
	void addGoodNew(GoodParam goodParam) throws GoodException;
	/**
	 * 四阶段 编辑一个商品(新方法)
	 * 
	 * @Title: editGoodNew
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param goodParam
	 * @throws GoodException
	 * @date 2015年7月7日 下午2:03:32
	 * @author talo
	 */
	void editGoodNew(GoodParam goodParam) throws GoodException;
	
	/**
	 * 编辑一个商品
	 * 
	 * @Title: editGood
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param goodParam
	 * @throws GoodException
	 * @date 2015年3月16日 下午2:03:32
	 * @author dzq
	 */
	void editGood(GoodParam goodParam) throws GoodException;
	
	/**
	 * 查询商品详情
	 * 
	 * @Title: getGoodDetailById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @date 2015年3月16日 下午1:02:28
	 * @author dzq
	 */
	GoodDetailVo getGoodDetailById(Long goodId) throws GoodException;
	
	
	/**
	 * 查询商品销售属性信息
	 * @Title: getGoodSalesDetailById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @throws GoodException
	 * @date 2015年3月26日 下午4:37:37  
	 * @author Administrator
	 */
	GoodDetailVo getGoodSalesDetailById(Long goodId) throws GoodException;
	/**
	 * 用户选择SKU
	 * @Title: selectGoodsku 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param skuProperties
	 * @return
	 * @date 2015年3月18日 上午11:23:09  
	 * @author Administrator
	 */
	RspSelectedSkuVo selectGoodskuByGoodIdAndPrprerty(Long goodId, String skuProperties) throws GoodException;
	
	
	
	/**
	 * 查询商品编辑页面
	 * @Title: getGoodEditById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月24日 上午10:37:56  
	 * @author Administrator
	 */
	GoodVo getGoodEditById(Long goodId) throws GoodException;
	/**
	 * 查询商品属性
	 * @Title: findGoodProperties 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodProperty
	 * @return
	 * @throws GoodException
	 * @date 2015年3月24日 下午3:32:12  
	 * @author Administrator
	 */
	List<GoodProperty> findGoodProperties(GoodProperty goodProperty) throws GoodException;
	
	/**
	 * 查询商品输入的并且非关键的属性
	 * @Title: findInputNotKeyGoodProperties 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param gnotKeyoodProperty
	 * @return
	 * @date 2015年3月26日 下午5:04:20  
	 * @author Administrator
	 */
	List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty)throws GoodException;
	
	/**
	 * 根据商品ID查询Sku信息
	 * @Title: findGoodSkuByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @throws GoodException
	 * @date 2015年3月25日 下午11:14:55  
	 * @author Administrator
	 */
	List<GoodSku> findGoodSkuByGoodId(Long goodId) throws GoodException;
	/**
	 * 商品批量上架
	 * @Title: upShelvesAllGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param ids
	 * @return
	 * @date 2015年3月25日 上午11:17:28  
	 * @author Administrator
	 */
	List<Good> upShelvesAllGood(List<String> ids) throws GoodException;
	
	/**
	 * 商品批量上架
	 * @Title: upShelvesAllGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param ids
	 * @return
	 * @date 2015年3月25日 上午11:17:28  
	 * @author Administrator
	 */
	List<Good> downShelvesAllGood(List<String> ids, Long userId)throws GoodException;
	
    /**
     * 逻辑删除商品
     * 
     * @Title: removeGood
     * @Description: (这里用一句话描述这个方法的作用)
     * @param userId TODO
     * @param goodId
     * @date 2014年7月15日 上午10:26:03
     * @author dzq
     */
    void removeGood(Long userId, Long goodId) throws GoodException;
    
	/**
	 * 批量删除商品
	 * @Title: removeAllGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId TODO
	 * @param ids
	 * @throws GoodException
	 * @date 2014年11月21日 上午9:24:47  
	 * @author Administrator
	 */
	void removeAllGood(Long userId, List<String> ids)throws GoodException;
	/**
	 * 商品排序
	 * @Title: sortGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subId
	 * @param tarId
	 * @date 2015年3月25日 下午12:51:33  
	 * @author Administrator
	 */
	void sortGood(Long subId, Long tarId)throws GoodException;
	/**
	 * 根据主键查询商品
	 * @Title: getGoodById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月25日 下午11:41:50  
	 * @author Administrator
	 */
	Good getGoodById(Long goodId)throws GoodException;
	/**
	 * 查询skuJson(临时使用，PC页面问题解决后删除 )
	 * @Title: findGoodSkuJsonByGoodId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月26日 下午2:45:15  
	 * @author Administrator
	 */
	String findGoodSkuJsonByGoodId(Long goodId)throws GoodException;
	
	
	/**
	 * 推荐商品至首页
	 * @Title: spreadIndex 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @throws GoodException
	 * @date 2015年3月26日 下午11:45:54  
	 * @author Administrator
	 */
	void spreadIndex(Long goodId)throws GoodException;

	/**
	 * 查询商品数量
	 * @Title: getGoodNum 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodVo
	 * @return
	 * @date 2015年3月31日 下午1:42:07  
	 * @author ah
	 */
	GoodVo getGoodNum(GoodVo goodVo);
	/**
	 * @Description: 查询商品数量
	 * @param shopNo
	 * @param status
	 * @return
	 * @date: 2015年4月1日 上午11:15:34
	 * @author: zyl
	 */
	Integer countGoodNum(String shopNo, Integer status);

	/**
	 * 查询店铺商品
	 * @Title: findGoodsByShopNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @return
	 * @date 2015年3月25日 下午5:14:49
	 * @author Administrator
	 */
	List<Good> findGoodsByShopNo(String shopNo);

	List<Good> findGoodByIds(List<String> goodIdList);

	GoodSku getGoodSkuById(Long id);

	List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Long appointmentId, Long shopTypeId);

	/**
	 * 单个商品上架
	 * @Title: upShelvesGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @date 2015年5月13日 上午10:30:46  
	 * @author ah
	 */
	void upShelvesGood(Long id);

	/**
	 * 单个商品下架
	 * @Title: downShelvesGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @date 2015年5月13日 上午10:31:13  
	 * @author ah
	 */
	void downShelvesGood(Long id);

	/**
	 * 免费店铺新增商品 
	 * @Title: addGoodForFree 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @date 2015年5月13日 下午5:55:19  
	 * @author ah
	 */
	void addGoodForFree(GoodParam goodParam);
	
	/**
	 * 免费店铺编辑商品 
	 * @Title: editGoodForFree 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @date 2015年5月14日 上午10:43:20  
	 * @author ah
	 */
	void editGoodForFree(GoodParam goodParam);
	
	/**
	 * @Description: 手机端查询代理商的商品列表,包括自由商品和代理商品
	 * @param shopNo TODO
	 * @param goodVo
	 * @param pagination
	 * @return
	 * @date: 2015年5月19日 上午11:16:20
	 * @author: zyl
	 */
	Pagination<GoodVo> findGoodVoPageForWap(String shopNo, GoodVo goodVo, Pagination<GoodVo> pagination);
	
	/**
	 * @Description: 手机端查询代理商的商品数量,包括自由商品和代理商品
	 * @param goodVo
	 * @return
	 * @date: 2015年5月19日 上午11:51:28
	 * @author: zyl
	 */
	Integer countGoodVoNum(GoodVo goodVo);

	List<GoodProperty> findSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty);

	/**
	 * 获取普通商品的在售数量
	 * @param no
	 * @return
	 */
	Integer getOnSellCount(String shopNo);

	/**
	 * 
	 * @param 获取普通商品的下架数量
	 * @return
	 */
	Integer getUnShelveCount(String shopNo);
	
	GoodProperty getGoodPropertyById(Long id);

	List<GoodSku> findGoodSkuListByGoodId(Long id);

	List<GoodProperty> findGoodPropertyListByGoodId(Long goodId);
	
	/**
	 * @Description: 查询卖家商品详情
	 * @date: 2015年8月4日 下午5:08:43
	 * @author: zyl
	 */
	GoodDetailVo getSellerGoodDetailById(Long valueOf);
	
	/**
	 * @Title: getRecommendGood
	 * @Description: 推荐商品
	 * @param name
	 * @param pagination
	 * @return 
	 * @date: 2015年9月2日 下午19:30:20
	 * @author: talo
	 */
	Pagination<RecommendGoodVo> getRecommendGood(Long userId,String goodName,Pagination<RecommendGoodVo> pagination) throws GoodException;
	/**
	 * @Title: findSubbranchGoods
	 * @Description: (查找分店商品列表(包含佣金比例))
	 * @param subbranchId
	 * @param userId
	 * @param pagination
	 * @param sortBy
	 * @return
	 * @throws GoodException
	 * @return Pagination<GoodProfitVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月31日 下午8:59:49
	 */
	Pagination<GoodProfitVo> findSubbranchGoods(Long subbranchId, Long userId, Integer sortBy, String searchKey,
			Pagination<GoodProfitVo> pagination) throws GoodException;

}
