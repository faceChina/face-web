package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
/**
 * 商品持久层接口
 * @ClassName: GoodDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午1:41:40
 */
public interface GoodDao {

	void add(Good good);
	
	void edit(Good good);
	
	void editGood(Good good);
	
	Good getById(Long id);
	
	Integer getPageCount(GoodVo goodVo);

	List<GoodVo> findPageShopType(GoodVo goodVo, int start, int pageSize);

	List<Good> findGoodsList(Good good);

	List<GoodVo> findGoodByShopTypeId(Good good, Long id, int goodNum);

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
	 * @param pagination
	 * @return
	 * @date: 2015年5月19日 上午11:16:20
	 * @author: zyl
	 */
	Integer countGoodVoNum(GoodVo goodVo);
	
	/**
	 * @Description: 手机端根据商品分类查询商品列表:1,供货商查询自有商品;2,代理商查询自有商品和代理商品
	 * @param good TODO
	 * @param goodVo
	 * @param pagination
	 * @return
	 * @date: 2015年5月19日 上午11:16:20
	 * @author: zyl
	 */
	List<GoodVo> findGoodVoForWapByShopTypeId(Good good, Long shopTypeId, int goodNum);

	Integer getCountByStatus(String shopNo, Integer status);

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
	 * @Title: countProfitGoods
	 * @Description: (查询分店商品(单品佣金)-统计)
	 * @param map
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月1日 下午12:01:22
	 */
	Integer countProfitGoods(Long subbranchId, Long userId, String searchKey);

	/**
	 * @Title: selectProfitGoods
	 * @Description: (查询分店商品(单品佣金)-分页)
	 * @param map
	 * @return
	 * @return List<GoodProfitVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月1日 下午12:01:52
	 */
	List<GoodProfitVo> selectProfitGoods(Long subbranchId, Long userId, Integer sortBy, String searchKey,
			Pagination<GoodProfitVo> pagination);

}
