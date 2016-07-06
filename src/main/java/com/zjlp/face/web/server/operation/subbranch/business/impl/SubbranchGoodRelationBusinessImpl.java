package com.zjlp.face.web.server.operation.subbranch.business.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchGoodRelationBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.GoodSkuDifferenceVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchGoodRelationService;

/**
 * 
 * @author 熊斌
 * 2015年8月19日上午11:50:02
 * @version 1.0
 */
@Service("subbranchGoodRelationBusiness")
public class SubbranchGoodRelationBusinessImpl implements SubbranchGoodRelationBusiness {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private SubbranchGoodRelationService subbranchGoodRelationService;

	@Override
	@Transactional
	public void addSubbranchGoodBrokerage(Long[] goodIds, Long subbranchId,Long userId,Integer rate) throws SubbranchException{
		try {
			AssertUtil.notEmpty(goodIds, "商品ID不能为空");
			AssertUtil.notNull(subbranchId, "分店ID不能为空");
			AssertUtil.notNull(userId, "总店用户ID不能为空");
			AssertUtil.notNull(rate, "佣金比例不能为空");
			
			if(rate<Constants.SUBBRANCH_GOOD_RATE_MIN || rate>Constants.SUBBRANCH_GOOD_RATE_MAX){
				AssertUtil.isTrue(false, "佣金比例设置错误");
			}
			
			Date time = new Date();
			
			for(Long goodId : goodIds){
				SubbranchGoodRelation subbranchGoodRelation = new SubbranchGoodRelation();
				subbranchGoodRelation.setGoodId(goodId);
				subbranchGoodRelation.setSubbranchId(subbranchId);
				
				Boolean isExist = subbranchGoodRelationService.isExist(subbranchGoodRelation);
				if(isExist){
					StringBuffer message = new StringBuffer();
					message.append("分店ID:").append(subbranchId).append("已设置过商品ID:").append(goodId).append("的佣金比例");
					AssertUtil.isTrue(false, message.toString());
				}
				
				subbranchGoodRelation.setCreateTime(time);
				subbranchGoodRelation.setUserId(userId);
				subbranchGoodRelation.setRate(rate);
				subbranchGoodRelationService.add(subbranchGoodRelation);
				
				StringBuffer message = new StringBuffer();
				message.append("新增分店ID:").append(subbranchId).append(",商品ID:").append(goodId).append("佣金比例出错");
				AssertUtil.notNull(subbranchGoodRelation.getId(), message.toString());
			}
		} catch (Exception e) {
			_log.error("新增分店佣金比例出错:" + e.getMessage());
			throw new SubbranchException(e.getMessage(), e);
		}
	}

	@Override
	public Integer getTotalCount(Long subbranchId, String shopNo,Integer type) throws SubbranchException{
		try {
			AssertUtil.notNull(subbranchId, "分店ID不能为空");
			AssertUtil.notNull(shopNo, "总店店铺No不能为空");
			AssertUtil.notNull(type, "查询类型不能为空");
			
			if(!Constants.SUBBRANCH_TYPE_HAVE.equals(type) && !Constants.SUBBRANCH_TYPE_NOT_HAVE.equals(type)){
				AssertUtil.isTrue(false, "查询类型错误");
			}
			
			SubbranchGoodRelationVo subbranchGoodRelationVo = new SubbranchGoodRelationVo();
			subbranchGoodRelationVo.setType(type);
			subbranchGoodRelationVo.setSubbranchId(subbranchId);
			subbranchGoodRelationVo.setShopNo(shopNo);
			
			return subbranchGoodRelationService.getTotalCount(subbranchGoodRelationVo);
		} catch (Exception e) {
			_log.error("获取分店商品数量出错:" + e.getMessage());
			throw new SubbranchException(e.getMessage(),e);
		}
	}

	@Override
	public void deleteByPrimaryKey(Long id) throws SubbranchException{
		try {
			AssertUtil.notNull(id, "ID不能为空");
			
			subbranchGoodRelationService.deleteByPrimaryKey(id);
		} catch (Exception e) {
			_log.error("删除分店佣金比例出错:" + e.getMessage());
			throw new SubbranchException(e.getMessage(),e);
		}
	}

	@Override
	public void update(Long id,Integer rate) throws SubbranchException {
		try {
			AssertUtil.notNull(id, "分店ID不能为空");
			AssertUtil.notNull(rate, "佣金比例不能为空");
			
			if(rate<Constants.SUBBRANCH_GOOD_RATE_MIN || rate>Constants.SUBBRANCH_GOOD_RATE_MAX){
				AssertUtil.isTrue(false, "佣金比例设置错误");
			}
			
			SubbranchGoodRelation subbranchGoodRelation = new SubbranchGoodRelation();
			subbranchGoodRelation.setId(id);
			subbranchGoodRelation.setRate(rate);
			
			subbranchGoodRelationService.updateByPrimaryKey(subbranchGoodRelation);
		} catch (Exception e) {
			_log.error("修改分店佣金比例出错:" + e.getMessage());
			throw new SubbranchException(e.getMessage(),e);
		}
	}
	
	@Override
	public Pagination<SubbranchGoodRelationVo> findPageSubbranchGood(
									Pagination<SubbranchGoodRelationVo> pagination,Long subbranchId,String shopNo,Integer type,String name) throws SubbranchException {
		try {
			AssertUtil.notNull(pagination, "分页对象不能为空");
			AssertUtil.notNull(subbranchId, "分店ID不能为空");
			AssertUtil.notNull(shopNo, "总店店铺No不能为空");
			AssertUtil.notNull(type, "查询类型不能为空");
			
			if(!Constants.SUBBRANCH_TYPE_HAVE.equals(type) && !Constants.SUBBRANCH_TYPE_NOT_HAVE.equals(type)){
				AssertUtil.isTrue(false, "查询类型错误");
			}
			
			SubbranchGoodRelationVo vo = new SubbranchGoodRelationVo();
			vo.setSubbranchId(subbranchId);
			vo.setShopNo(shopNo);
			vo.setType(type);
			
			if(StringUtils.isNotBlank(name)){
				vo.setName(name);
			}
			
			Pagination<SubbranchGoodRelationVo> page = subbranchGoodRelationService.findPageSubbracheGood(vo, pagination);
			List<SubbranchGoodRelationVo> list = page.getDatas();
			for(SubbranchGoodRelationVo subbranchGoodRelationVo : list){
				GoodSkuDifferenceVo goodSkuDifferenceVo = subbranchGoodRelationService.querySkuDifference(subbranchGoodRelationVo.getGoodId());
				if(null != goodSkuDifferenceVo){
					subbranchGoodRelationVo.setPriceMax(goodSkuDifferenceVo.getMax());
					subbranchGoodRelationVo.setPriceMin(goodSkuDifferenceVo.getMin());
				}else{
					subbranchGoodRelationVo.setPriceMax(0L);
					subbranchGoodRelationVo.setPriceMin(0L);
				}
			}
			
			return page;
		} catch (Exception e) {
			_log.error("查询分店商品出错:" + e.getMessage());
			throw new SubbranchException(e.getMessage(),e);
		}
	}
}
