package com.zjlp.face.web.server.product.good.factory;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.factory.element.ElementFactory;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

@Component("freeGoodFactory")
public class FreeGoodFactory implements GoodFactory {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ElementFactory<GoodParam> goodElementFactory;
	
	@Autowired
	private ElementFactory<GoodShopTypeRelation[]> shopTypeRelationElementFactory;
	
	@Autowired
	private ElementFactory<GoodParam> goodFileElementFactory;
	
	@Autowired
	private ElementFactory<GoodParam> freeGoodPropertyAndSkuElementFactory;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "GoodException" })
	public void addGood(GoodParam goodParam) throws GoodException {
		try {
			/** step1  创建商品对象*/
			goodParam.setClassificationId(Constants.CLASSIFICATION_ID_FREE_GOOD);
			goodParam.setServiceId(Constants.SERVICE_ID_COMMON);
			goodParam = goodElementFactory.create(goodParam);
			
			/** step2 创建商品规格属性跟商品SKU */
			freeGoodPropertyAndSkuElementFactory.create(goodParam);
			
			/** step3 创建商品分类关联 */
			shopTypeRelationElementFactory.create(goodParam);
			
			/** step4  创建商品图片*/
			StopWatch clock = new StopWatch();
			clock.start(); // 计时开始
			goodFileElementFactory.create(goodParam);
			clock.stop(); // 计时结束
			_logger.info("严重警告！！！该方法执行耗费:" + clock.getTime() + " ms ");

			_logger.info("call add default good factory success,goodId is "+goodParam.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new GoodException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "GoodException" })
	public void editGood(GoodParam goodParam) throws GoodException {
		try {
			/** step1  修改商品对象*/
			goodParam.setClassificationId(Constants.CLASSIFICATION_ID_FREE_GOOD);
			goodParam.setServiceId(Constants.SERVICE_ID_COMMON);
			goodParam = goodElementFactory.edit(goodParam);
			
			/** step2 修改商品属性跟商品规格 */
			freeGoodPropertyAndSkuElementFactory.edit(goodParam);
			
			/** step3  修改商品分类关联*/
			shopTypeRelationElementFactory.edit(goodParam);
			
			/** step4  修改商品图片*/
			goodFileElementFactory.edit(goodParam);
			_logger.info("call edit default good factory success,goodId is "+goodParam.getId());
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}

	}

}
