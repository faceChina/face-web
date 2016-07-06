package com.zjlp.face.web.server.user.bankcard.dao;

import java.util.List;

import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

public interface BankCardDao {

	Long addBankCard(BankCard bankCard);

	BankCard getBankCardById(Long id);

	void removeBankCardById(Long id);

	List<BankCard> findBankCardList(BankCard bankCard);

	BankCard getBankCardByPk(Long id);

	List<BankCard> getCardForDfType(BankCard bankCard);

	void setCardDfTypeById(BankCard bankCard);

	List<BankCard> findIdenticalValidCard(BankCard bankCard);

	void editCardStatusById(BankCard bankCard);

	void editCardNoAgreeById(BankCard bankCard);

	int getCardNumber(BankCard card);

	void edit(BankCard card);

	BankCard getBankCard(BankCard bankCard);

	List<BankCard> findBankCardListByUserId(Long userId);

	BankCard getDefaultBankCardByUserId(Long userId);

}
