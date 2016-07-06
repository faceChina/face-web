package com.jzwgj.user.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = false, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class ShopBusinessTest {
	
	@Autowired
	private ShopBusiness shopBusiness;
	
	
	private String shopNo = "HZJZ1506291710P6qFv1";
	private String lat = "321";
	private String lng = "123";
	private String address = "市民中心D座1";
	private String cell = "151571467261";
	private String area = "江干区";
	
	@Test
	public void text(){
//		isShopLocation(shopNo);
//		getShopLocation(shopNo);
		editShopLocation(shopNo, lat, lng,address,cell,area);
	}
	
	private void isShopLocation(String shopNo){
		try {
			shopBusiness.isShopLocation(null);
		} catch (Exception e) {
			System.out.println("shopNo is null:" + e.getMessage());
		}
		try {
			shopBusiness.isShopLocation("");
		} catch (Exception e) {
			System.out.println("shopNo is '':" + e.getMessage());
		}
		try {
			shopBusiness.isShopLocation("  ");
		} catch (Exception e) {
			System.out.println("shopNo is '  ':" + e.getMessage());
		}
		
		try {
			shopBusiness.isShopLocation("sadasdsad");
		} catch (Exception e) {
			System.out.println("shopNo is error:" + e.getMessage());
		}
		
		boolean flag = shopBusiness.isShopLocation(shopNo);
		System.out.println("shopNo is :" + flag);
	}
	
	private void getShopLocation(String shopNo){
		try {
			shopBusiness.getShopLocation(null);
		} catch (Exception e) {
			System.out.println("shopNo is null:" + e.getMessage());
		}
		try {
			shopBusiness.getShopLocation("");
		} catch (Exception e) {
			System.out.println("shopNo is '':" + e.getMessage());
		}
		try {
			shopBusiness.getShopLocation("  ");
		} catch (Exception e) {
			System.out.println("shopNo is '  ':" + e.getMessage());
		}
		try {
			shopBusiness.getShopLocation("sadasdsad");
		} catch (Exception e) {
			System.out.println("shopNo is error:" + e.getMessage());
		}
		
		ShopLocationDto shopLocationDto = shopBusiness.getShopLocation(shopNo);
		System.out.println(shopLocationDto.toString());
	}
	
	private void editShopLocation(String shopNo, String lat, String lng,String address, String cell,String area){
		shopBusiness.editShopLocation(shopNo, lat, lng, address, cell, area);
	}
}
