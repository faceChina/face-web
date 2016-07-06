package com.jzwgj.operating.maketing;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.marketing.service.MarketingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = false, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class MarketingTest {

	@Autowired
	private MarketingService marketingService;
	@Autowired
	private MarketingProducer marketingProducer;
	
//	@Test
	public void addMemberRuleListTest() throws Exception {
		Long sellerId = 116L;
		MarketingTool tool = MarketingToolDto.initMemberRuleTool(sellerId);
		Long id = marketingService.addTool(tool);
		AssertUtil.notNull(id, "");
		List<MarketingActivityDetail> detail = MarketingActivityDetailDto.initMemberRule(id);
		marketingService.addActivityDetail(detail);
		List<MarketingActivityDetail> list = marketingService.findListByToolId(id);
		AssertUtil.isTrue(4 == list.size(), "");
		for (MarketingActivityDetail data : list) {
			System.out.println(data.toString());
		}
	}
	
//	@Test
	public void addMarketingToolTest1() {
		Long sellerId = 116L;
		MarketingTool tool = MarketingToolDto.initTool(sellerId, 
				MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF, MarketingToolDto.STANDARD_TYPE_YES);
		Long toolId = marketingService.addTool(tool);
		AssertUtil.notNull(toolId, "");
		MarketingActivity activity = MarketingActivityDto.initActivity(sellerId, toolId);
		Long activityId = marketingService.addActivity(activity);
		AssertUtil.notNull(activityId, "");
	}
	
//	@Test
	public void addMarketingToolTest2() {
		Long sellerId = 116L;
		MarketingTool tool = MarketingToolDto.initTool(sellerId, 
				MarketingToolDto.CJ_TYPE_QD, MarketingToolDto.PD_TYPE_JF, MarketingToolDto.STANDARD_TYPE_YES);
		Long toolId = marketingService.addTool(tool);
		AssertUtil.notNull(toolId, "");
		MarketingActivity activity = MarketingActivityDto.initActivity(sellerId, toolId);
		Long activityId = marketingService.addActivity(activity);
		AssertUtil.notNull(activityId, "");
	}
	
//	@Test
	public void addMarketingToolTest3() {
		Long sellerId = 116L;
		MarketingTool tool = MarketingToolDto.initTool(sellerId, 
				MarketingToolDto.CJ_TYPE_ZF, MarketingToolDto.PD_TYPE_JF, MarketingToolDto.STANDARD_TYPE_YES);
		Long toolId = marketingService.addTool(tool);
		AssertUtil.notNull(toolId, "");
		MarketingActivity activity = MarketingActivityDto.initActivity(sellerId, toolId);
		Long activityId = marketingService.addActivity(activity);
		AssertUtil.notNull(activityId, "");
	}
	
	
//	@Test
	public void addMarketingToolTest4() {
		Long sellerId = 116L;
		MarketingTool tool = MarketingToolDto.initTool(sellerId, 
				MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY, MarketingToolDto.STANDARD_TYPE_NO);
		Long toolId = marketingService.addTool(tool);
		AssertUtil.notNull(toolId, "");
//		MarketingActivity activity = MarketingActivityDto.initActivity(sellerId, toolId);
//		Long activityId = marketingService.addActivity(activity);
//		AssertUtil.notNull(activityId, "");
	}
	
//	@Test
	public void findNotStandardDetailTest() {
		List<MarketingActivityDetailDto> list = marketingProducer.findNotStandardDetail(116L, MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY);
		AssertUtil.notEmpty(list, "");
		for (MarketingActivityDetail detail : list) {
			System.out.println(detail);
		}
	}
	
	@Test
	public void initMarketingTool() {
		marketingProducer.initMarketingTool(3L);
	}
}
