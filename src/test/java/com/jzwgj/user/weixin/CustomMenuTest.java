package com.jzwgj.user.weixin;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;
import com.zjlp.face.web.server.user.weixin.service.CustomMenuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
@Transactional
public class CustomMenuTest {
	
	@Autowired
	private CustomMenuService customMenuService;
	@Autowired(required=false)
	private ShopExternalService shopExternalService;
	@Autowired(required=false)
	private ImageService imageService;

//	@Test
	public void listTest() {
		CustomMenuDto customMenuDto = new CustomMenuDto();
		customMenuDto.setShopNo("HZJZ1503261737oJAjR1");
		List<CustomMenuVo> customMenuVos = customMenuService.findCustomMenuVoList(customMenuDto);
		Assert.assertNotNull(customMenuVos);
	}
	
	@Test
	public void  TDCtest() {
		// 获取accessToken
		String accessToken = shopExternalService.getAccessToken("wx4d59a1b4fb4d9335", "e556a9e673e704d74e7767fb1d24ffc8");
		// 获取二维码
		byte[] twoDimensionalCode = shopExternalService.getWechatTwoDimensionalCode("安辉推荐", accessToken);
		String path = imageService.upload(twoDimensionalCode);
		System.out.println(path);
	}
}
