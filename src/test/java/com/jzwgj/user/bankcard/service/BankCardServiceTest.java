package com.jzwgj.user.bankcard.service;

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

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCardDto;
import com.zjlp.face.web.server.user.bankcard.service.BankCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
@Transactional
public class BankCardServiceTest {

	@Autowired
	private BankCardService bankCardService;

	private Long addCard(Long userId, String cardNo) {
		BankCard bankCard = BankCardDto.InitCreditCardForPay(
				String.valueOf(userId), BankCard.REMOTE_TYPE_USER, "01030000",
				"农业银行", cardNo, "18655015835", "林亦生", "362323209910237511",
				"2222", "123");
		return bankCardService.addBankCard(bankCard);
	}

	private String[][] addCardList(Long userId) {
		String[] payCardNos = { "123456", "645321", "456789", "987654" };
		String[] settleCardNos = { "741852", "852963", "963852" };
		for (String cardNo : payCardNos) {
			BankCard bankCard = BankCardDto.InitDebitCardForPay(String.valueOf(userId),
					BankCard.REMOTE_TYPE_USER, "01030000", "农业银行", cardNo,
					"18655015835", "林亦生", "362323209910237511");
			bankCardService.addBankCard(bankCard);
		}
		for (String cardNo : settleCardNos) {
			BankCard bankCard = BankCardDto.InitDebitCardForSettle(String.valueOf(userId),
					BankCard.REMOTE_TYPE_USER, "01030000", "农业银行", cardNo,
					"18655015835", "林亦生", "362323209910237511");
			bankCardService.addBankCard(bankCard);
		}
		String[][] a = { payCardNos, settleCardNos };
		return a;
	}

	@Test
	public void addBankCardTest() {
		Long userId = 108L;
		Long id = this.addCard(userId, "1234567890123456");
		BankCard bankCard = bankCardService.getValidBankCardById(id);
		Assert.assertNotNull(bankCard);
		System.out.println(bankCard);
		Assert.assertEquals(String.valueOf(userId), bankCard.getRemoteId());
		Assert.assertEquals(BankCard.REMOTE_TYPE_USER, bankCard.getRemoteType());
		Assert.assertEquals("01030000", bankCard.getBankCode());
		Assert.assertEquals("1234567890123456", bankCard.getBankCard());
		Assert.assertEquals("农业银行", bankCard.getBankName());
		Assert.assertEquals("18655015835", bankCard.getCell());
		Assert.assertEquals("林亦生", bankCard.getName());
		Assert.assertEquals(BankCard.TYPE_CREDIT, bankCard.getBankType());
		Assert.assertEquals(BankCard.REMOTE_TYPE_USER, bankCard.getType());
		Assert.assertEquals(Constants.VALID, bankCard.getStatus());
		Assert.assertEquals("2222", bankCard.getEndTime());
		Assert.assertEquals("123", bankCard.getCvv());
		Assert.assertEquals("362323209910237511", bankCard.getIdentity());
		Assert.assertEquals(BankCard.TYPE_CREDIT, bankCard.getPayType());
		Assert.assertEquals(Constants.NOTDEFAULT, bankCard.getIsDefault());
		Assert.assertNull(bankCard.getNoAgree());
		Assert.assertNotNull(bankCard.getCreateTime());
		Assert.assertNotNull(bankCard.getUpdateTime());
	}

	@Test
	public void addBankCardTestException() {
		Long userId = 108L;
		this.addCard(userId, "1234567890123456");
		try {
			this.addCard(userId, "1234567890123456");
			Assert.assertFalse(true);
		} catch (BankCardException e) {
			Assert.assertEquals(
					"BankCard[remoteType=1,remoteId=108,cardNo=1234567890123456,bankCode=01030000,bankType=3,type=1] is already exists.",
					e.getMessage());
		}
	}

	@Test
	public void invalidateBankCardTest() {
		Long userId = 108L;
		Long id = this.addCard(userId, "1234567890123456");
		bankCardService.invalidateBankCard(id);
		BankCard card;
		try {
			card = bankCardService.getValidBankCardById(id);
			Assert.assertFalse(true);
		} catch (BankCardException e) {
			Assert.assertEquals("BankCard[id=" + id + "] is not exists.",
					e.getMessage());
		}
		card = bankCardService.getBankCardByPk(id);
		Assert.assertNotNull(card);
	}

	@Test
	public void defaultBankCardTest() {
		Long userId = 108L;
		Long id = this.addCard(userId, "1234567890123456");
		bankCardService.setDefaultBankCard(id, userId);
		BankCard card1 = bankCardService.getValidBankCardById(id);
		Assert.assertNotNull(card1);
		Assert.assertEquals(Constants.ISDEFAULT, card1.getIsDefault());

		Long userId2 = 109L;
		Long id2 = this.addCard(userId2, "123456789012345126");
		BankCard card2 = bankCardService.getValidBankCardById(id2);
		Assert.assertNotNull(card2);
		bankCardService.setDefaultBankCard(id2, userId2);
		card1 = bankCardService.getValidBankCardById(id);
		Assert.assertEquals(Constants.ISDEFAULT, card1.getIsDefault());

		Long id3 = this.addCard(userId, "6543210987654321");
		bankCardService.setDefaultBankCard(id3, userId);
		card1 = bankCardService.getValidBankCardById(id);
		BankCard card3 = bankCardService.getValidBankCardById(id3);
		Assert.assertEquals(Constants.NOTDEFAULT, card1.getIsDefault());
		Assert.assertEquals(Constants.ISDEFAULT, card3.getIsDefault());
	}

	@Test
	public void noAgreeTest() {
		Long userId = 108L;
		Long id = this.addCard(userId, "6543210987654321");
		bankCardService.setCardNoAgree(id, "201503131543");
		BankCard bankCard = bankCardService.getValidBankCardById(id);
		Assert.assertEquals("201503131543", bankCard.getNoAgree());
	}

	@Test
	public void findCardListTest() {
		Long userId = 108L;
		String[][] a = addCardList(userId);
		List<BankCard> payList = bankCardService.findPayCardList(userId);
		List<BankCard> settleList = bankCardService.findSettleCardList(userId);
		Assert.assertNotNull(payList);
		Assert.assertEquals(4, payList.size());
		
		for (int i = 0; i < payList.size(); i++) {
			Assert.assertEquals(a[0][i], payList.get(i).getBankCard());
		}
		
		Assert.assertNotNull(settleList);
		Assert.assertEquals(0, settleList.size());
		
		for (String cardNo : a[1]) {
			BankCard card = bankCardService.getIdenticalValidCard(String.valueOf(userId), 
					BankCard.REMOTE_TYPE_USER, cardNo, BankCard.USERFOR_SETTLE);
			bankCardService.editCardStatus(card.getId(), Constants.VALID);
		}
		
		for (int i = 0; i < settleList.size(); i++) {
			Assert.assertEquals(a[1][i], settleList.get(i).getBankCard());
		}
	}

}
