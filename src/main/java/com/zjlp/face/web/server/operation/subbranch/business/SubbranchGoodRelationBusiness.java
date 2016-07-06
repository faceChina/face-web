package com.zjlp.face.web.server.operation.subbranch.business;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;


/**
 * 
 * @author 熊斌
 * 2015年8月19日上午11:48:22
 * @version 1.0
 */
public interface SubbranchGoodRelationBusiness {
	
	/**
	 * 设置分店商品佣金比例
	 * @param goods			商品ID集合
	 * @param subbranchId	分店ID
	 * @param userId		总店用户ID
	 * @param rate			佣金比例
	 * @return
	 */
	void addSubbranchGoodBrokerage(Long [] goodIds,Long subbranchId,Long userId,Integer rate) throws SubbranchException;
	
	/**
	 * 查询分店商品佣金比例列表
	 * @param pagination		分页对象
	 * @param subbranchId		分店Id
	 * @param type				是否设置分店佣金(0:未设置;1:已设置)
	 * @param name				商品名称
	 * @return
	 * @throws SubbranchException
	 */
	Pagination<SubbranchGoodRelationVo> findPageSubbranchGood(Pagination<SubbranchGoodRelationVo> pagination,Long subbranchId,String shopNo,Integer type,String name) throws SubbranchException;
	
	/**
	 * 获取已设置分店商品数量
	 * @param subbranchId		分店Id
	 * @param shopNo	总店店铺No
	 * @param type		是否已设佣金比例(0:未设置;1:已设置)
	 * @return
	 */
	Integer getTotalCount(Long subbranchId,String shopNo,Integer type) throws SubbranchException;
	
	void deleteByPrimaryKey(Long id) throws SubbranchException;
	
	void update(Long id,Integer rate) throws SubbranchException;
}
