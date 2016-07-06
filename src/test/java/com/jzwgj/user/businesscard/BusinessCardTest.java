package com.jzwgj.user.businesscard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.producer.ImFriendsProducer;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.server.user.user.producer.UserGisProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
public class BusinessCardTest {
	
	@Autowired
	private BusinessCardBusiness bussinessCardBusiness;
	
	@Autowired
	private ImFriendsProducer imFriendsProducer;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserGisProducer userGisProducer;
	
	//@Test
	public void testFindLeiDa(){
		UserGisVo userGisVo = new UserGisVo();
		userGisVo.setId(30L);
		userGisVo.setRadarEnable(1);
		List<UserGisDto> list  = userGisProducer.findUserByLeida(userGisVo);
		System.out.println(list.size());
	}
	
	@Test
	public void testCloseLeiDa() {
		UserGisVo userGisVo = new UserGisVo();
		userGisVo.setId(30L);
		boolean close = userGisProducer.closeLeida(userGisVo);
		System.out.println(close);
	}
	
//	@Test
	public void findCardCase(){
		Pagination<BusinessCardVo> pagination = new Pagination<BusinessCardVo>();
		pagination.setTotalRow(23);
		BusinessCardVo businessCardVo = new BusinessCardVo();
		businessCardVo.setUserId(1202L);
		pagination = bussinessCardBusiness.findCardPageByCase(pagination, businessCardVo);
		Assert.assertNotNull(pagination);
		for (BusinessCardVo dto : pagination.getDatas()) {
			System.out.println(dto);
		}
	}
	@Test
	public void testquery(){
		List<BusinessCardVo> list = new ArrayList<BusinessCardVo>();
		list = bussinessCardBusiness.findCardByCase(1213L);
		System.out.println(list.size());
	}

//	@Test
	public void testAdd() {
		CardCase card = new CardCase();
		card.setUserId(1213L);
		card.setCardId(29L);
		boolean long1 = bussinessCardBusiness.addCardToCase(card);
		System.out.println(long1);
	}
	
//	@Test 
	public void testRemove() {
		
		boolean l = bussinessCardBusiness.removeCardFromCase(1140L, 28L);
		System.out.println(l);
	}
	
	//@Test
	public void testCardCase(){
		CardCase cas = new CardCase();
		cas.setUserId(1203L);
		cas.setCardId(2222L);
		cas.setCreateTime(new Date());
		cas.setUserPingyin("ddddd");
		boolean l = bussinessCardBusiness.addCardToCase(cas);
		System.out.println(l);
	}
	
//	@Test
	public void testUpdate() {
		BusinessCard card = new BusinessCard();
		card.setId(27L);
		card.setUserId(1202L);
		card.setShopType(3);
		bussinessCardBusiness.updateBusinessCard(card);
	}
	
//	@Test
	public void get() {
		BusinessCard card = bussinessCardBusiness.getBusinessCardByUserId(274L);
		System.out.println(card);
	}
	
//	@Test
	public void testFriend() {
		Boolean friend = imFriendsProducer.isFriend("18868823930", "18868823930");
		System.out.println("========================================="+friend+"===========================================");
	}
	
//	@Test
	public void upload() {
		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;
		try {
			File file = new File("E:"+File.separator+"QQ图片20150901131559.jpg");
			baos = new ByteArrayOutputStream();
			fis = new FileInputStream(file);
			int n;
			while((n = fis.read()) != -1) {
				baos.write(n);
			}
			byte[] byteArray = baos.toByteArray();
			String json = imageService.upload(byteArray); 
			
			JSONObject jsonObj = JSONObject.fromObject(json);
			String path = jsonObj.getString("path");
			BusinessCard card = new BusinessCard();
			card.setId(27L);
			card.setUserId(1202L);
			card.setBackgroundPic(path); 
			card.setPicType(BusinessCard.PIC_TYPE_CUSTOM);
			bussinessCardBusiness.updateBusinessCard(card);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//@Test
	public void getByDoubleId() {
		CardCase cardCase = bussinessCardBusiness.getCardCaseByUserId(1140L, 28L);
		System.out.println(cardCase);
	}

}
