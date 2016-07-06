package com.jzwgj.trade.order.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrderItemDetail;
import com.zjlp.face.web.server.trade.order.bussiness.salesorder.state.generate.OrdinaryOrderDetail;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class OrdinaryOrderTest {
	
	String orderNo = "S534646445345645635";
	Date date = new Date();
	
	List<OrderItemDetail> itemDetailList = new ArrayList<OrderItemDetail>();
	
//	@Test
	public void generateItemTest(){
		generateItem();
	}
	
	private void generateItem(){
		OrderItemDetail detail = new OrderItemDetail(3L);
		detail.withClassification(getclassification());
		detail.withDiscountPrice(100L);
		detail.withGood(getGood(), getGoodSku());
		detail.generate(date);
		itemDetailList.add(detail);
	}

	@Test
	public void generateTest(){
		generateItem();
		OrdinaryOrderDetail detail = new OrdinaryOrderDetail(orderNo);
		detail.withAddress(getAddress(), getVaearDto());
		detail.withDeliverWay(1);
		detail.withBuyer(getBuyer(), "呵呵");
		detail.withSeller(getSeller(), getSellerShop());
		detail.withDeliveryTemplate(getDeliverTemplate());
		for (OrderItemDetail itemDetail : itemDetailList) {
			detail.addItemDetail(itemDetail);
		}
		detail.execute();
		System.out.println(detail.getSalesOrder());
	}
	
	private Good getGood(){
		Good good = new Good();
		good.setId(1L);
		good.setClassificationId(1L);
		good.setSalesPrice(100L);
		good.setName("good_name");
		good.setPostFee(10L);
		good.setPicUrl("good-picUrl.img");
		good.setShopNo("ShopNO-asdflasdkfl");
		good.setShopName("good_shop");
		good.setStatus(1);
		good.setLogisticsMode(1);
		good.setInventory(1000L);
		return good;
	}
	
	private GoodSku getGoodSku(){
		GoodSku goodSku = new GoodSku();
		goodSku.setGoodId(1L);
		goodSku.setStock(100L);
		goodSku.setId(1L);
		goodSku.setPicturePath("goodSku-picturePath.img");
		goodSku.setSalesPrice(100L);
		goodSku.setSkuPropertiesName("goodSku-SkuPropertiesName");
		goodSku.setSkuProperties("goodSku-skuProperties");
		goodSku.setStatus(1);
		return goodSku;
	}
	
	private Classification getclassification(){
		Classification classification = new Classification();
		classification.setId(1L);
		classification.setCategory(1);
		return classification;
	}
	
	private Address getAddress(){
		Address address = new Address();
		address.setId(1L);
		address.setName("address-name");
		address.setCell("address-cell");
		address.setAddressDetail("address-addressDetail");
		address.setvAreaCode(1000000);
		return address;
	}
	
	private VaearDto getVaearDto(){
		VaearDto dto = new VaearDto();
		dto.setProvinceCode("provinceCode");
		dto.setCityName("cityName");
		dto.setAreaCode("areaCode");
		return dto;
	}
	
	private DeliveryTemplateDto getDeliverTemplate(){
		return null;
	}
	
	private User getSeller(){
		User user = new User();
		user.setId(2L);
		user.setLoginAccount("18655015835");
		user.setNickname("小五天");
		user.setContacts("一品诰命");
		user.setCell("18655015835");
		return user;
	}
	
	private User getBuyer(){
		User user = new User();
		user.setId(1L);
		user.setLoginAccount("18655015835");
		user.setNickname("木木一生");
		return user;
	}
	
    private Shop getSellerShop(){
    	Shop shop = new Shop();
    	shop.setNo("SellerShop-no-001");
    	shop.setName("三缺一");
    	return shop;
    }
}
