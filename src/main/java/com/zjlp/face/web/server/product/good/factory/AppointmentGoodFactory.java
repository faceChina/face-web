package com.zjlp.face.web.server.product.good.factory;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.element.ElementFactory;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
/**
 * 预约商品工厂
 * @ClassName: ProtocolGoodFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年4月15日 下午2:54:29
 */
@Component("appointmentGoodFactory")
public class AppointmentGoodFactory implements GoodFactory{
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ElementFactory<GoodParam> goodElementFactory;
	
	@Autowired
	private ElementFactory<List<GoodSku>> goodSkuElementFactory;
	
	@Autowired
	private ElementFactory<GoodShopTypeRelation[]> shopTypeRelationElementFactory;
	
	@Autowired
	private ElementFactory<GoodParam> goodFileElementFactory;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "GoodException" })
	public void addGood(GoodParam goodParam) throws GoodException {
		/** step1  创建商品对象*/
		goodParam.setLogisticsMode(0);
		goodParam = goodElementFactory.create(goodParam);
		
		/** step2  创建商品SKU*/
		goodSkuElementFactory.create(goodParam);
		
		/** step3  商品分类管理*/
		shopTypeRelationElementFactory.create(goodParam);
		
		/** step4  创建商品图片*/
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		goodFileElementFactory.create(goodParam);
		clock.stop(); // 计时结束
		_logger.info("严重警告！！！该方法执行耗费:" + clock.getTime() + " ms ");
		_logger.info("call add protocol good factory success,goodId is "+goodParam.getId());
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "GoodException" })
	public void editGood(GoodParam goodParam) throws GoodException {
		/** step1  修改商品对象*/
		goodParam = goodElementFactory.edit(goodParam);
		
		/** step2 修改商品规格属性*/
		goodSkuElementFactory.edit(goodParam);
		
		/** step3  商品分类管理*/
		shopTypeRelationElementFactory.edit(goodParam);
		
		/** step4  修改商品图片*/
		goodFileElementFactory.edit(goodParam);
		_logger.info("call edit protocol good factory success,goodId is "+goodParam.getId());
	}

}
