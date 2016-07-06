package com.jzwgj.product.good.produer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class GoodProducerTest {
	@Autowired
	private GoodProducer goodProduer;
	

	@Test
	public void testEditGoodSkuStock(){
		Long skuIdLong = 914L;
		Long stockLong = 50L;
		GoodSku oldSku  = goodProduer.getGoodSkuStatelessById(skuIdLong);
		Good oldGood = goodProduer.getGoodById(oldSku.getGoodId());
		Long oldSkuStockLong = oldSku.getStock();
		Long oldStockLong = oldGood.getInventory();
		Long oldSalesVolume = oldGood.getSalesVolume();
		System.out.println("原SKU库存 :" +oldSkuStockLong +" 原商品库存 ： "+oldStockLong+"商品销量　："+oldSalesVolume);
		goodProduer.editGoodSkuStock(skuIdLong, stockLong);
		GoodSku newSku  = goodProduer.getGoodSkuStatelessById(skuIdLong);
		Good newGood = goodProduer.getGoodById(oldSku.getGoodId());
		Long newSkuStockLong = newSku.getStock();
		Long newStockLong = newGood.getInventory();
		Long newSalesVolume = newGood.getSalesVolume();
		System.out.println("SKU库存 :" +newSkuStockLong +" 商品库存 ： "+newStockLong +"商品销量　："+newSalesVolume);
		Assert.assertEquals("商品SKU库存扣除失败", Long.valueOf(oldSkuStockLong-stockLong), newSkuStockLong);
		Assert.assertEquals("商品库存不同失败", Long.valueOf(oldStockLong-stockLong), newStockLong);
		Assert.assertEquals("商品销量不同失败", Long.valueOf(oldSalesVolume+stockLong), newSalesVolume);
	}
}
