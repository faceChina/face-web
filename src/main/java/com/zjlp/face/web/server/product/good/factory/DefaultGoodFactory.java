package com.zjlp.face.web.server.product.good.factory;


import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.jredis.client.AbstractRedisDaoSupport;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.element.ElementFactory;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.util.redis.RedisFactory;
import com.zjlp.face.web.util.redis.RedisKey;
/**
 * 交易商品工厂
 * @ClassName: DefaultGoodFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午3:33:30
 */
@Component("defaultGoodFactory")
public class DefaultGoodFactory implements GoodFactory {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ElementFactory<GoodParam> goodElementFactory;
	
	@Autowired
	private ElementFactory<List<GoodSku>> goodSkuElementFactory;
	
	@Autowired
	private ElementFactory<List<GoodProperty>> goodPropertyElementFactory;
	
	@Autowired
	private ElementFactory<GoodShopTypeRelation[]> shopTypeRelationElementFactory;
	
	@Autowired
	private ElementFactory<GoodParam> goodFileElementFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "GoodException" })
	public void addGood(GoodParam goodParam) throws GoodException {
		try {
			/** step1  创建商品对象*/
			goodParam = goodElementFactory.create(goodParam);
			
			/** step2 创建商品规格属性*/
			goodPropertyElementFactory.create(goodParam);
			
			/** step3  创建商品SKU*/
			goodSkuElementFactory.create(goodParam);
			
			/** step4  创建商品分类关联*/
			shopTypeRelationElementFactory.create(goodParam);
			
			/** step5  创建商品图片*/
			StopWatch clock = new StopWatch();
			clock.start(); // 计时开始
			goodFileElementFactory.create(goodParam);
			clock.stop(); // 计时结束
			_logger.info("严重警告！！！该方法执行耗费:" + clock.getTime() + " ms ");

			
			/** step5 缓存任务*/
			final GoodParam redisGoodParam = goodParam;
			// 设置最近一次使用的商品类目
			if (null != RedisFactory.getWgjStringHelper()) {
				String key = RedisKey.Classification_getLatestClassification_key+redisGoodParam.getShopNo();
				RedisFactory.getWgjStringHelper().set(key, new AbstractRedisDaoSupport<Long>() {
					@Override
					public Long support() throws RuntimeException {
						return redisGoodParam.getClassificationId();
					}
				});
			}
			_logger.info("call add default good factory success,goodId is "+goodParam.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new GoodException(e.getMessage(),e);
		}
   	}


	@Override
	public void editGood(GoodParam goodParam) throws GoodException {
		try {
			/** step1  修改商品对象*/
			goodParam = goodElementFactory.edit(goodParam);
			
			/** step2 修改商品属性*/
			goodPropertyElementFactory.edit(goodParam);
			
			 /** step3 修改商品规格属性*/
			 goodSkuElementFactory.edit(goodParam);
			
			/** step4  修改商品分类关联*/
			shopTypeRelationElementFactory.edit(goodParam);
			
			/** step5  修改商品图片*/
			goodFileElementFactory.edit(goodParam);
			_logger.info("call edit default good factory success,goodId is "+goodParam.getId());
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
	

}
