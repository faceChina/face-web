package com.jzwgj.subbranch;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchGoodRelationBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-beans.xml","/spring-security-cas.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class SubbranchGoodRelationTest {

	@Autowired
	private SubbranchGoodRelationBusiness subbranchGoodRelationBusiness;
	
	private Long[] goodIds = {607L,742L,878L};
	
	private Long subbranchId = 1203L;
	
	private Long userId = 330L;
	
	private Integer rate = 10;
	
	private String shopNo = "HZJZ1506291710P6qFv1";
	
	private Pagination<SubbranchGoodRelationVo> pagination = new Pagination<SubbranchGoodRelationVo>();
	
	private String name = "服装";
	
	@Test
	public void Test(){
//		addTest();
		findTest();
//		getTotalCountTest();
//		update();
//		delete();
	}
	
	public void addTest() {
		try {
			addSubbranchGoodBrokerage(null, subbranchId, userId, rate);
		} catch (Exception e) {
			System.out.println("goodIds is null :" + e.getMessage());
		}
		try {
			addSubbranchGoodBrokerage(goodIds, null, userId, rate);
		} catch (Exception e) {
			System.out.println("subbranchId is null :" + e.getMessage());
		}
		try {
			addSubbranchGoodBrokerage(goodIds, subbranchId, null, rate);
		} catch (Exception e) {
			System.out.println("userId is null :" + e.getMessage());
		}
		try {
			addSubbranchGoodBrokerage(goodIds, subbranchId, userId, null);
		} catch (Exception e) {
			System.out.println("rate is null :" + e.getMessage());
		}
		try {
			addSubbranchGoodBrokerage(goodIds, subbranchId, userId, -1);
		} catch (Exception e) {
			System.out.println("rate < 0 :" + e.getMessage());
		}
		try {
			addSubbranchGoodBrokerage(goodIds, subbranchId, userId, 101);
		} catch (Exception e) {
			System.out.println("rate > 100 :" + e.getMessage());
		}
		
		addSubbranchGoodBrokerage(goodIds, subbranchId, userId, rate);
	}
	
	public void findTest(){
		try {
			findPageSubbranchGood(null,subbranchId,shopNo,Constants.SUBBRANCH_TYPE_HAVE,name);
		} catch (Exception e) {
			System.out.println("pagination is null :" + e.getMessage());
		}
		try {
			findPageSubbranchGood(pagination,null,shopNo,Constants.SUBBRANCH_TYPE_HAVE,name);
		} catch (Exception e) {
			System.out.println("subbranchId is null :" + e.getMessage());
		}
		try {
			findPageSubbranchGood(pagination,subbranchId,null,Constants.SUBBRANCH_TYPE_HAVE,name);
		} catch (Exception e) {
			System.out.println("shopNo is null :" + e.getMessage());
		}
		try {
			findPageSubbranchGood(pagination,subbranchId,shopNo,null,name);
		} catch (Exception e) {
			System.out.println("type is null :" + e.getMessage());
		}
		try {
			findPageSubbranchGood(pagination,subbranchId,shopNo,3,name);
		} catch (Exception e) {
			System.out.println("type 设置错误 :" + e.getMessage());
		}
		System.out.println("有name,已设置佣金比例商品:");
		findPageSubbranchGood(pagination,subbranchId,shopNo,Constants.SUBBRANCH_TYPE_HAVE,name);
		System.out.println("有name,未设置佣金比例商品:");
		findPageSubbranchGood(pagination,subbranchId,shopNo,Constants.SUBBRANCH_TYPE_NOT_HAVE,name);
		System.out.println("无name,已设置佣金比例商品:");
		findPageSubbranchGood(pagination,subbranchId,shopNo,Constants.SUBBRANCH_TYPE_HAVE,null);
		System.out.println("无name,未设置佣金比例商品:");
		findPageSubbranchGood(pagination,subbranchId,shopNo,Constants.SUBBRANCH_TYPE_NOT_HAVE,"");
	}
	
	public void getTotalCountTest(){
		try {
			getTotalCount(null,shopNo,Constants.SUBBRANCH_TYPE_HAVE);
		} catch (Exception e) {
			System.out.println("subbranchId is null :" + e.getMessage());
		}
		try {
			getTotalCount(subbranchId,null,Constants.SUBBRANCH_TYPE_HAVE);
		} catch (Exception e) {
			System.out.println("shopNo is null :" + e.getMessage());
		}
		try {
			getTotalCount(subbranchId,shopNo,3);
		} catch (Exception e) {
			System.out.println("type 设置错误  :" + e.getMessage());
		}
		System.out.println("已设置佣金比例的商品数量:");
		getTotalCount(subbranchId,shopNo,Constants.SUBBRANCH_TYPE_HAVE);
	}
	
	public void update(){
		try {
			update(null,rate);
		} catch (Exception e) {
			System.out.println("id is null :" + e.getMessage());
		}
		try {
			update(10L,-1);
		} catch (Exception e) {
			System.out.println("rate < 0:" + e.getMessage());
		}
		try {
			update(10L,1001);
		} catch (Exception e) {
			System.out.println("rate > 100:" + e.getMessage());
		}
		
		update(10L,50);
	}
	
	public void delete(){
		try {
			deleteByPrimaryKey(null);
		} catch (Exception e) {
			System.out.println("id is null :" + e.getMessage());
		}
		deleteByPrimaryKey(10L);
	}
	
	private void addSubbranchGoodBrokerage(Long [] goodIds,Long subbranchId,Long userId,Integer rate){
		subbranchGoodRelationBusiness.addSubbranchGoodBrokerage(goodIds, subbranchId, userId, rate);
	}
	
	private void findPageSubbranchGood(Pagination<SubbranchGoodRelationVo> pagination,Long subbranchId,String shopNo,Integer type,String name){
		pagination = subbranchGoodRelationBusiness.findPageSubbranchGood(pagination, subbranchId, shopNo, type,name);
		
		List<SubbranchGoodRelationVo> list = pagination.getDatas();
		for(SubbranchGoodRelationVo vo : list){
			System.out.println(vo.toString());
		}
	}
	
	private void getTotalCount(Long subbranchId,String shopNo,Integer type){
		Integer count = subbranchGoodRelationBusiness.getTotalCount(subbranchId, shopNo, type);
		System.out.println(count);
	}
	
	private void update(Long id,Integer rate){
		subbranchGoodRelationBusiness.update(id, rate);
	}
	
	public void deleteByPrimaryKey(Long id){
		subbranchGoodRelationBusiness.deleteByPrimaryKey(id);
	}
}
