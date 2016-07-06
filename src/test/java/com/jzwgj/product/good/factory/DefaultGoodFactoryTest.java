package com.jzwgj.product.good.factory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.GoodFactory;
import com.zjlp.face.web.server.product.good.factory.param.DefaultGoodParam;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class DefaultGoodFactoryTest {

	@Autowired
	private GoodFactory defaultGoodFactory;
	
//	@Autowired
//	private GoodBusiness goodBusiness;
	
	private GoodParam goodParam;
	@Before
	public void init(){
		initSkufz();
	}
	
	private void  initSkufz(){
		goodParam = new DefaultGoodParam();
		goodParam.setClassificationId(2L);
		goodParam.setShopNo("HZJZ0001140724232mHd");
		goodParam.setShopName("goodParam");
		goodParam.setNick("测试小丁");//*
		goodParam.setName("测试新增一个服装类商品");
		goodParam.setProductNumber("number123123123");
		goodParam.setMarketPrice(123L);
		goodParam.setSalesPrice(1L);
		goodParam.setInventory(123l);
		goodParam.setPicUrl("123123");
		goodParam.setSort(1);
		goodParam.setGoodContent("123123");

		List<GoodProperty> pList = new ArrayList<GoodProperty>();
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsSalesProp(true);//销售属性
		goodProperty.setIsColorProp(true);//颜色属性
		goodProperty.setIsStandard(true);//标准属性
		goodProperty.setPropId(1L);
		goodProperty.setPropName("颜色");
		goodProperty.setPropValueId(1L);
		goodProperty.setPropValueName("大红");
		goodProperty.setPropValueCode("RED");
		goodProperty.setPropValueAlias("大红色");
		goodProperty.setName("颜色:大红色");
		pList.add(goodProperty);
		GoodProperty goodProperty2 = new GoodProperty();
		goodProperty2.setIsSalesProp(true);
		goodProperty2.setIsEnumProp(true);
		goodProperty2.setIsStandard(true);
		goodProperty2.setPropId(1L);
		goodProperty2.setPropName("尺码");
		goodProperty2.setPropValueId(1L);
		goodProperty2.setPropValueName("M");
		goodProperty2.setPropValueCode("M1");
		goodProperty2.setPropValueAlias("M1");
		goodProperty2.setName("尺码:M1");
		pList.add(goodProperty2);
//		goodParam.setPropList(pList);
		//sku
		List<GoodSku> list = new ArrayList<GoodSku>();
		GoodSku goodSku2 = new GoodSku();
		goodSku2.setMarketPrice(123L);
		goodSku2.setSalesPrice(123L);//销售价格
		goodSku2.setStock(10L);//商品库存
		goodSku2.setPreferentialPolicy(0);
		goodSku2.setStatus(1);
		goodSku2.setPicturePath("123123");
		goodSku2.setVersion("123");
		goodSku2.setSkuProperties(goodProperty.getId()+";"+goodProperty2.getId());
		goodSku2.setSkuPropertiesName(goodProperty.getName()+":"+goodProperty.getPropName()+";"+
				goodProperty2.getName()+":"+goodProperty2.getPropName());
		list.add(goodSku2);
		goodParam.setSkuList(list);
	}
	//修改参数
	private GoodParam  initEditSkufz(GoodParam goodParam){
		
		goodParam.setId(24L);
		goodParam.setClassificationId(3L);
		goodParam.setShopNo("HZJZ0001140724232mHd");
		goodParam.setShopName("goodParam111");
		goodParam.setNick("测试小丁修改");//*
		goodParam.setName("测试修改一个服装类商品");
		goodParam.setProductNumber("修改");
		goodParam.setMarketPrice(999L);
		goodParam.setSalesPrice(999L);
		goodParam.setInventory(999L);
		goodParam.setPicUrl("999L");
		goodParam.setSort(999);
		goodParam.setGoodContent("999L");
		List<GoodProperty> pList = new ArrayList<GoodProperty>();
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsSalesProp(true);//销售属性
		goodProperty.setIsColorProp(true);//颜色属性
		goodProperty.setIsStandard(true);//标准属性
		goodProperty.setPropId(1L);
		goodProperty.setPropName("颜色");
		goodProperty.setPropValueId(1L);
		goodProperty.setPropValueName("黑色");
		goodProperty.setPropValueCode("black");
		goodProperty.setPropValueAlias("大黑色");
		goodProperty.setName("颜色:大黑色");
		pList.add(goodProperty);
		GoodProperty goodProperty2 = new GoodProperty();
		goodProperty2.setIsSalesProp(true);
		goodProperty2.setIsEnumProp(true);
		goodProperty2.setIsStandard(true);
		goodProperty2.setPropId(1L);
		goodProperty2.setPropName("尺码");
		goodProperty2.setPropValueId(1L);
		goodProperty2.setPropValueName("M");
		goodProperty2.setPropValueCode("M1");
		goodProperty2.setPropValueAlias("EM1");
		goodProperty2.setName("尺码:EM1");
		pList.add(goodProperty2);
//		goodParam.setPropList(pList);
		//sku
		List<GoodSku> list = new ArrayList<GoodSku>();
		GoodSku goodSku2 = new GoodSku();
		goodSku2.setMarketPrice(9998L);
		goodSku2.setSalesPrice(9998L);//销售价格
		goodSku2.setStock(999L);//商品库存
		goodSku2.setPreferentialPolicy(0);
		goodSku2.setStatus(1);
		goodSku2.setPicturePath("9999");
		goodSku2.setVersion("999");
		goodSku2.setSkuProperties(goodProperty.getId()+";"+goodProperty2.getId());
		goodSku2.setSkuPropertiesName(goodProperty.getName()+":"+goodProperty.getPropName()+";"+
				goodProperty2.getName()+":"+goodProperty2.getPropName());
		list.add(goodSku2);
		goodParam.setSkuList(list);
		return goodParam;
	}
	
	@Test
	public void run(){
		try {
//			defaultGoodFactory.addGood(goodParam);
			goodParam =initEditSkufz(goodParam);
			defaultGoodFactory.editGood(goodParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
