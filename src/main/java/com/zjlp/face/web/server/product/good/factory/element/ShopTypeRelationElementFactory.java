package com.zjlp.face.web.server.product.good.factory.element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodShopTypeRelationDao;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
/**
 * 店铺分类元素工厂
 * @ClassName: ShopTypeRelationFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月11日 下午3:33:47
 */
@Service("shopTypeRelationElementFactory")
public class ShopTypeRelationElementFactory implements ElementFactory<GoodShopTypeRelation[]> {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GoodShopTypeRelationDao goodShopTypeRelationDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodShopTypeRelation[] create(GoodParam goodParam) throws GoodException{
		try {
			AssertUtil.notNull(goodParam.getId(), "发布商品分类关联失败，商品主键为空！");
			GoodShopTypeRelation[] relations = goodParam.getShoptype();
			if (null == relations || relations.length < 1) {
				return null;
			}
			/** 4 新增商品分类 */
		    for (GoodShopTypeRelation goodShopTypeRelation : relations) {
		    	if(null == goodShopTypeRelation.getShopTypeId())continue;
		    	goodShopTypeRelation.setGoodId(goodParam.getId());
		    	goodShopTypeRelationDao.add(goodShopTypeRelation);
		    	AssertUtil.notNull(goodShopTypeRelation.getId(),"新增商品分类关联失败，商品ID:"+goodParam.getId());
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("create  ShopTypeRelation success! goodId is "+goodParam.getId());
			}
		    return relations;
		} catch (Exception e) {
			throw new GoodException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodShopTypeRelation[] edit(GoodParam goodParam) throws GoodException{
		try {
			AssertUtil.notNull(goodParam.getId(), "编辑商品失败，商品主键为空！");
			GoodShopTypeRelation[] relations =  goodParam.getShoptype();
			//删除旧的分类
			goodShopTypeRelationDao.deleteAllGoodShopTypeByGoodId(goodParam.getId());
			if (null == relations || relations.length < 1) {
				return null;
			}
			/** 4 新增商品分类 */
		    for (GoodShopTypeRelation goodShopTypeRelation : relations) {
		    	goodShopTypeRelation.setGoodId(goodParam.getId());
		    	goodShopTypeRelationDao.add(goodShopTypeRelation);
		        AssertUtil.notNull(goodShopTypeRelation.getId(), "编辑商品分类关联失败，商品ID:"+goodParam.getId());
			}
		    
			if (_logger.isDebugEnabled()) {
				_logger.debug("edit ShopTypeRelation success! goodId is "+goodParam.getId());
			}
			return null;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}

}
