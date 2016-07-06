package com.zjlp.face.web.server.product.good.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
/**
 * 商品基础服务类
 * @ClassName: GoodService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月16日 下午3:07:42
 */
public interface GoodService {

	/**
	 * 编辑商品人信息
	 * @Title: editGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param good
	 * @date 2015年3月11日 下午8:12:52  
	 * @author dzq
	 */
	void edit(Good good);
	
	void editGood(Good good);
	
	/**
	 * 根据主键查询商品信息
	 * @Title: getgoodById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @date 2015年3月11日 下午8:12:35  
	 * @author dzq
	 */
	Good getGoodById(Long goodId);
	/**
	 * 搜索商品
	 * @Title: searchPageGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodVo
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @date 2015年3月24日 下午3:31:40  
	 * @author Administrator
	 */
	Pagination<GoodVo> searchPageGood(GoodVo goodVo,
			Pagination<GoodVo> pagination)throws Exception;
	

	/**
	 * 商品上架
	 * @Title: upShelvesGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @return
	 * @throws GoodException
	 * @date 2015年3月25日 上午11:19:45  
	 * @author Administrator
	 */
	Good upShelvesGood(Long goodId) throws GoodException;
	
	/**
	 * 商品下架
	 * @Title: downShelvesGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param goodId
	 * @return
	 * @date 2015年3月25日 上午11:20:31  
	 * @author Administrator
	 */
	Good downShelvesGood(Long goodId) throws GoodException;
	/**
	 * 逻辑删除商品
	 * @Title: removeGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @date 2015年3月25日 上午11:55:51  
	 * @author Administrator
	 */
	void removeGood(Long goodId) throws GoodException;
	/**
	 * 查询商品列表
	 * @Title: findGoodsList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param good
	 * @return
	 * @date 2015年3月25日 下午5:16:51  
	 * @author Administrator
	 */
	List<Good> findGoodsList(Good good)throws GoodException;

	/**
	 * 查询商品分类下的商品
	 * @Title: findGoodByShopTypeId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param good TODO
	 * @param id
	 * @param goodNum
	 * @return
	 * @date 2015年3月26日 下午4:21:39  
	 * @author ah
	 */
	List<GoodVo> findGoodByShopTypeId(Good good, Long id, int goodNum);

	/**
	 * 查询商品数量
	 * @Title: getGoodNum 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodVo
	 * @return
	 * @date 2015年3月31日 下午1:55:03  
	 * @author ah
	 */
	GoodVo getGoodNum(GoodVo goodVo);
	/**
	 * @Description: 查询商品数量
	 * @param goodVo
	 * @return
	 * @date: 2015年4月1日 上午11:20:47
	 * @author: zyl
	 */
	Integer countGoodNum(GoodVo goodVo);

	List<Good> findGoodByIds(List<String> goodIdList);



	
	/**
	 * @Description: 手机端查询商品列表:1,供货商查询自有商品;2,代理商查询自有商品和代理商品
	 * @param shopNo TODO
	 * @param goodVo
	 * @param pagination
	 * @return
	 * @date: 2015年5月19日 上午11:16:20
	 * @author: zyl
	 */
	Pagination<GoodVo> findGoodVoPageForWap(String shopNo, GoodVo goodVo, Pagination<GoodVo> pagination);
	
	/**
	 * @Description: 查询商品数量:1,供货商查询自有商品;2,代理商查询自有商品和代理商品
	 * @param goodVo
	 * @return
	 * @date: 2015年5月19日 上午11:51:28
	 * @author: zyl
	 */
	Integer countGoodVoNum(GoodVo goodVo);
	
	/**
	 * @Description: 手机端通过商品分类查询商品列表:1,供货商查询自有商品;2,代理商查询自有商品和代理商品
	 * @param good TODO
	 * @param id
	 * @param goodNum
	 * @return
	 * @date: 2015年5月19日 下午2:02:31
	 * @author: zyl
	 */
	List<GoodVo> findGoodVoForWapByShopTypeId(Good good, Long id, int goodNum);

	/**
	 * 通过商品状态获取该店铺商品数量
	 * @param shopNo
	 * @param goodStatusDefault
	 * @return
	 */
	Integer getCountByStatus(String shopNo, Integer goodStatusDefault);

	void editBrowerTimeById(Long id);
	
	/**
	 * @Title: getRecommendGood
	 * @Description: 推荐商品
	 * @param name
	 * @param pagination
	 * @return 
	 * @date: 2015年9月2日 下午19:30:20
	 * @author: talo
	 */
	Pagination<RecommendGoodVo> getRecommendGood(Long userId,String goodName,Pagination<RecommendGoodVo> pagination);

	/**
	 * @Title: findSubbranchGoods
	 * @Description: (查找分店商品列表(包含佣金比例))
	 * @param subbranchId
	 * @param userId
	 * @param sortBy
	 * @param pagination
	 * @return
	 * @return Pagination<GoodProfitVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月1日 上午11:33:04
	 */
	Pagination<GoodProfitVo> findSubbranchGoods(Long subbranchId, Long userId, Integer sortBy, String searchKey,
			Pagination<GoodProfitVo> pagination);

}
