package com.jzwgj.product.good.factory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.GoodFactory;
import com.zjlp.face.web.server.product.good.factory.param.DefaultGoodParam;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class CustomGoodFactoryTest {
	
	
	@Autowired
	private GoodFactory defaultGoodFactory;
	
	private GoodParam goodParam;
	@Before
	public void init(){
		initCustomfz();
	}
	
	private void  initCustomfz(){
		goodParam = new DefaultGoodParam();
		goodParam.setClassificationId(2L);
		goodParam.setShopNo("HZJZ0001140724232mHd");
		goodParam.setShopName("goodParam");
		goodParam.setNick("测试小丁");//*
		goodParam.setName("测试其它类商品");
		goodParam.setProductNumber("number123123124");
		goodParam.setMarketPrice(123L);
		goodParam.setSalesPrice(1L);
		goodParam.setInventory(123l);
		goodParam.setPicUrl("123123");
		goodParam.setSort(1);
		goodParam.setGoodContent("测试其它类商品");

		List<GoodProperty> pList = new ArrayList<GoodProperty>();
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsInputProp(true);//自定义类型属于输入属性
		goodProperty.setPropName("其它类属性名");
		goodProperty.setPropValueName("其它类属性值");
		goodProperty.setName("属性名:其它类属性值");
		pList.add(goodProperty);
		GoodProperty goodProperty2 = new GoodProperty();
		goodProperty2.setIsInputProp(true);//自定义类型属于输入属性
		goodProperty2.setPropName("其它类属性名1");
		goodProperty2.setPropValueName("其它类属性值1");
		goodProperty2.setName("其它类属性名1:其它类属性值1");
		pList.add(goodProperty2);
//		goodParam.setPropList(pList);
		//sku
		List<GoodSku> list = new ArrayList<GoodSku>();
		GoodSku goodSku2 = new GoodSku();
		goodSku2.setMarketPrice(123L);
		goodSku2.setSalesPrice(123L);
		goodSku2.setStock(10L);
		list.add(goodSku2);
		goodParam.setSkuList(list);
	}
	
	
	@Test
	public void add(){
		try {
			defaultGoodFactory.addGood(goodParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void edit(){
		defaultGoodFactory.editGood(goodParam);
	}

}
