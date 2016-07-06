package com.jzwgj.user.invitation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.vo.UserInvitationVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年9月28日 下午5:12:00
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
@Transactional
public class UserInvitation {

	@Autowired
	private UserBusiness userBusiness;
	
	/** 
	 *   0级       1级         2级              3级
	 *            1141  572,573
	 *       1139
	 *            1213  574,575
	 *  1138       
	 *            1214  576,577
	 *       1140  
	 *            1215  578,581
	 * **/

	@Test
	public void findMyInvitedRelationshipTest() {
		UserInvitationVo vo = this.userBusiness.findMyInvitedRelationship(1139L);
		System.out.println(vo);
	}

}
