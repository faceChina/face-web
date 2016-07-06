package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;

public interface GoodMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKeyWithBLOBs(Good record);

    int updateByPrimaryKey(Good record);

	Integer selectPageCount(Map<String, Object> map);

	List<GoodVo> selectPageList(Map<String, Object> map);

	void removeById(Long goodId);

	List<Good> selectList(Good good);

	List<GoodVo> findGoodByShopTypeId(Map<String, Object> map);

	Integer countGoodNum(GoodVo goodVo);

	List<Good> findGoodByIds(Map<String, Object> map);

	Integer countGoodVoNum(GoodVo goodVo);

	List<GoodVo> findGoodVoPageForWap(Map<String, Object> map);

	List<GoodVo> findGoodVoForWapByShopTypeId(Map<String, Object> map);

	Integer getCountByStatus(@Param("shopNo")String shopNo, @Param("status")Integer status);

	void editBrowerTimeById(Long id);
	 
	List<RecommendGoodVo> getRecommendGood(Map<String, Object> map);
	
	Integer getRecommendGoodCount(Map<String, Object> map);

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
	Integer countProfitGoods(Map<String, Object> map);

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
	List<GoodProfitVo> selectProfitGoods(Map<String, Object> map);

}