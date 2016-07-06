package com.jzwgj.product.good.tdd;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.good.factory.param.DefaultGoodParam;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class GoodTdd {
	@Autowired
	private ClassificationBusiness classificationBusiness;
	
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	
	@Test
	public void pcStart(){

		Scanner sc = new Scanner(System.in);
		System.out.println("请选择操作种类：\n 1 商品发布流程 2 商品编辑流程");
		int type = sc.nextInt();
		if (1==type) {
			publishGood();
		}else {
			System.out.println("未开发完成");
		}
//
//		System.out.println(“请输入你的姓名：”);
//
//		　　String name = sc.nextLine();
	}
	
	public void publishGood(){
		/** 商品类目操作 */
		int classific1ationLevel=0;
		Long pid = 0L;
		Long lastClassificationId = null;
		do {
			List<Classification> list = classificationBusiness.findClassificationByPid(pid, classific1ationLevel);
			classific1ationLevel++;
			Scanner sc = new Scanner(System.in);
			StringBuilder sBuilder = new StringBuilder("请选择"+classific1ationLevel+"级商品类目：\n");
			for (int i = 0; i < list.size(); i++) {
				sBuilder.append("["+i+"]:"+list.get(i).getName()+"\n");
			}
			System.out.println(sBuilder.toString());
			int index = sc.nextInt();
			Classification classification = list.get(index);
			pid=classification.getId();
			lastClassificationId = classification.getId();
		} while (classific1ationLevel<3);
		if (null == lastClassificationId) {
			System.out.println("出错了，未找到相关类目！");
		}
		/** 初始化商品 */
		GoodParam goodParam = new DefaultGoodParam();
		Scanner name = new Scanner(System.in);
		System.out.println("请输入商品标题\n");
		goodParam.setName(name.nextLine());
		System.out.println(goodParam.getName());
		
		List<ShopTypeVo> shopList  = shopTypeBusiness.findShopType("HZJZ0001140724232mHd");
		Scanner shoptype = new Scanner(System.in);
		System.out.println("请选择商品分类：\n");
		goodParam.setName(shoptype.nextLine());
		System.out.println(goodParam.getName());
	}

}
