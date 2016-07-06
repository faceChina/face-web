package com.zjlp.face.web.server.operation.subbranch.dao;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.GoodSkuDifferenceVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;

/**
 * 
 * @author 熊斌
 * 2015年8月19日上午11:32:45
 * @version 1.0
 */
public interface SubbranchGoodRelationDao {

	Long add(SubbranchGoodRelation subbranchGoodRelation);

	SubbranchGoodRelation findByPrimarykey(Long id);

	void updateByPrimaryKey(SubbranchGoodRelation subbranchGoodRelation);

	void deleteByPrimaryKey(Long id);
	
	/**
	 * 获取分店商品数量
	 * @param subbranchGoodRelationVo
	 * @return
	 */
	Integer getTotalCount(SubbranchGoodRelationVo subbranchGoodRelationVo);
	
	Pagination<SubbranchGoodRelationVo> findPageSubbracheGood(SubbranchGoodRelationVo subbranchGoodRelationVo, Pagination<SubbranchGoodRelationVo> pagination);
	
	/**
	 * 检测分店是否已设置该商品佣金比例
	 * @param subbranchGoodRelation
	 * @return
	 */
	Integer isExist(SubbranchGoodRelation subbranchGoodRelation);
	
	Integer getSubbranchGoodRate (SubbranchGoodRelation subbranchGoodRelation);

	/**
	 * 查询商品sku销售价最大,最小值
	 * @param goodId
	 * @return
	 */
	GoodSkuDifferenceVo querySkuDifference(Long goodId);
}
