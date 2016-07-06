package com.zjlp.face.web.server.user.bankcard.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.BankCardDto;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.bankcard.producer.BankCardProducer;
import com.zjlp.face.web.server.user.bankcard.service.BankCardService;

@Repository("bankCardProducer")
public class BankCardProducerImpl implements BankCardProducer {

	@Autowired
	private BankCardService bankCardService;
	
	@Override
	public BankCard getBankCardById(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			return bankCardService.getValidBankCardById(id);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public boolean activationBankCard(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			BankCard card = bankCardService.getBankCardByPk(id);
			AssertUtil.notNull(card, "BankCard[id={}] can not be null.", id);
			if (Constants.FREEZE.equals(card.getStatus())) {
				bankCardService.editCardStatus(id, Constants.VALID);
			}
			return true;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getBankCardByPk(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			BankCard card = bankCardService.getBankCardByPk(id);
			return card;
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	@Override
	public List<BankCardVo> findPayCardList(Long userId) throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<BankCard> list = bankCardService.findPayCardList(userId);
			return BankCardDto.coverCardsForView(list);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}
	
	@Override
	public List<BankCard> findBankCardListByUserId(Long userId) {
		return bankCardService.findBankCardListByUserId(userId);
	}

	@Override
	public List<BankCardVo> findSettleCardList(Long userId)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<BankCard> list = bankCardService.findSettleCardList(userId);
			return BankCardDto.coverCardsForView(list);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getBankCardByCardNoAndUserId(String bankCard, Long userId) {
		return bankCardService.getBankCardByCardNoAndUserId(bankCard,userId);
	}

	@Override
	public void addBankCard(BankCard bankCard) {
		bankCardService.addBankCard(bankCard);
	}

	@Override
	public void editBankCard(BankCard bankCard) {
		bankCardService.editBankCard(bankCard);
	}
	
}