package com.jzwgj.operating.member;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.operation.member.dao.SendIntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.operation.member.service.MemberCardService;
import com.zjlp.face.web.server.operation.member.service.MemberService;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = false, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class MemberTest {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberBusiness memberBusiness;
	@Autowired
	private MemberCardService memberCardService;
	
//	@Test
	public void initEnactment() throws Exception {
		Long sellerId = 3L;
		memberService.addMemberEnactment(sellerId);
	}
	
//	@Test
	public void editEnactment1() throws Exception {
		Long sellerId = 116L;
		MemberEnactment enactment = memberService.getEnactmentBySettleId(sellerId);
		Assert.assertNotNull("enactment can't be null.", enactment);
		enactment.setImgPath("/imgData");
		enactment.setCardName("会员卡名称");
		enactment.setCardNameColor("#0000001");
		enactment.setCardNoColor("#0000001");
		enactment.setCardCode("ABCD");
		enactment.setStartNo(50);
		enactment.setEndNo(1000);
		enactment.setMemberContent("asdfasdfasdfasdfasdf");
		String cardInfo = enactment.toString();
		boolean isTrue = memberService.editEnactment(enactment, sellerId);
		Assert.assertEquals(isTrue, true);
		enactment = memberService.getEnactmentBySettleId(sellerId);
		Assert.assertNotNull("enactment can't be null.", enactment);
		Assert.assertEquals(cardInfo, enactment.toString());
		System.out.println(cardInfo);
	}
	
//	@Test
	public void editEnactment2() throws Exception {
		Long sellerId = 116L;
		MemberEnactment enactment = memberService.getEnactmentBySettleId(sellerId);
		Long id = enactment.getId();
		Assert.assertNotNull("enactment can't be null.", enactment);
		enactment = new MemberEnactment();
		enactment.setId(id);
		memberService.editEnactment(enactment, sellerId);
		enactment = memberService.getEnactmentBySettleId(sellerId);
		System.out.println(enactment);
	}
	
//	@Test
	public void listMemberRuleTest() {
		Long sellerId = 116L;
		List<MemberRule> rules = memberBusiness.findMemberList(sellerId);
		Assert.assertNotNull(rules);
		for (MemberRule memberRule : rules) {
			System.out.println(memberRule);
		}
	}
	
//	@Test
	public void generateMemberCardTest() {
		memberCardService.generateMemberCard("oXTIpuEHuQ2WHsvyFMWkrP7GeZg0", 113l, "612924861", "HZJZ1503261737oJAjR1");
	}
	
//	@Test
	public void sendIntegralTest(){
		for (int i = 0; i < 10; i++) {
			memberBusiness.sendIntegral(1208L, 1201L, 100L);
		}
	}
	
	@Test
	public void querySendIntegral(){
		Pagination<SendIntegralRecodeDto> pagination = new Pagination<SendIntegralRecodeDto>();
		pagination.setTotalRow(25);
		SendIntegralRecodeDto sdto = new SendIntegralRecodeDto(1201L, 1204L);
		pagination = memberBusiness.queryIntegralRecode(sdto, pagination);
		Assert.assertNotNull(pagination);
		for (SendIntegralRecodeDto dto : pagination.getDatas()) {
			System.out.println(dto);
		}
	}
	
}
