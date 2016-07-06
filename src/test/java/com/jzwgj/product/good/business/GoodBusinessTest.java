/*package com.jzwgj.product.good.business;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jzwgj.json.JsonUtil;
import com.jzwgj.server.product.good.business.GoodBusiness;
import com.jzwgj.server.product.good.domain.Good;
import com.jzwgj.server.product.good.domain.GoodSku;
import com.jzwgj.server.product.good.domain.vo.GoodDetailVo;
import com.jzwgj.server.trade.order.domain.vo.RspSelectedSkuVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class GoodBusinessTest {
	
	@Autowired
	private GoodBusiness goodBusiness;
	
	@Test
	public void getDetailByid(){
		GoodDetailVo goodDetailVos = goodBusiness.getGoodDetailById(24L);
		System.out.println("商品：");
		Good good =  goodDetailVos.getGood();
		System.out.println("名称 ："+good.getName());
		System.out.println("价格 ："+good.getSalesPrice());
		System.out.println("销量："+good.getSalesVolume());
		System.out.println("浏览量："+good.getSalesVolume());
		System.out.println("内容："+good.getGoodContent());
		
		System.out.println("属性");
//		for (GoodProperty goodProperty : goodDetailVos.getPropertieList()) {
//			System.out.println(goodProperty.getPropName()+" "+ goodProperty.getPropValueName());
//		}
		System.out.println("属性sku");
		System.out.println("属性sku:"+goodDetailVos.getSkuJson());
		System.out.println(JSONObject.fromObject(goodDetailVos).toString());
		System.out.println("SKU");
		for (GoodSku goodSku : goodDetailVos.getSkuList()) {
			System.out.println(JSONObject.fromObject(goodSku).toString());
			System.out.println(goodSku.getId()+goodSku.getName());
		}
//		System.out.println(JSONObject.fromObject(goodDetailVos).toString());
	}
	
	@Test
	public void selectGoodskuByGoodIdAndPrprerty(){
		Long goodId = 24L;
		String skuProperties ="15;14";
		RspSelectedSkuVo goosSku = goodBusiness.selectGoodskuByGoodIdAndPrprerty(goodId, skuProperties);
		String[] propertys = {"id","picturePath","salesPrice","stock"};
		String resultJson = JsonUtil.fromObject(goosSku, false, propertys);
		System.out.println(resultJson);
	}
}
*/