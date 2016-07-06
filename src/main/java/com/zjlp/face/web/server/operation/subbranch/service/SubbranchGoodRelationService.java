package com.zjlp.face.web.server.operation.subbranch.service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.GoodSkuDifferenceVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;

/**
 * 
 * @author 熊斌
 * 2015年8月19日上午11:38:20
 * @version 1.0
 */
public interface SubbranchGoodRelationService {

	Long add(SubbranchGoodRelation subbranchGoodRelation);

	SubbranchGoodRelation findByPrimarykey(Long id);

	void updateByPrimaryKey(SubbranchGoodRelation subbranchGoodRelation);

	void deleteByPrimaryKey(Long id);
	
	Integer getTotalCount(SubbranchGoodRelationVo subbranchGoodRelationVo);
	
	Pagination<SubbranchGoodRelationVo> findPageSubbracheGood(SubbranchGoodRelationVo subbranchGoodRelationVo,Pagination<SubbranchGoodRelationVo> pagination);
	
	/**
	 * 检测商品是否已设置佣金比例
	 * @param subbranchGoodRelation
	 * @return
	 */
	Boolean isExist(SubbranchGoodRelation subbranchGoodRelation);
	
	Integer getSubbranchGoodRate (SubbranchGoodRelation subbranchGoodRelation);

	/**
	 * 查询商品sku销售价最大,最小值
	 * @param goodId	商品ID
	 * @return
	 */
	GoodSkuDifferenceVo querySkuDifference(Long goodId);
}
