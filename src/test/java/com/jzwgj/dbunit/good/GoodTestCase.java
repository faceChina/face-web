package com.jzwgj.dbunit.good;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.dbunit.MyDataBaseTestCase;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.vo.GoodDetailVo;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:/spring-beans.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true,transactionManager="jzTransactionManager")
public class GoodTestCase extends MyDataBaseTestCase {

	@Autowired
	private GoodBusiness goodBusiness;
	
	@Test
	public void test(){
		GoodDetailVo vo = goodBusiness.getGoodDetailById(1L);
		super.assertNotNull("查询商品VO为空",vo);
		super.assertNotNull("商品为空",vo.getGood());
		super.assertNotNull("商品图片为空",vo.getGoodImgs());
		super.assertNotNull("商品SKU为空",vo.getSkuList());
	}
}
