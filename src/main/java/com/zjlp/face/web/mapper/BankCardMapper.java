package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

public interface BankCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BankCard record);

    int insertSelective(BankCard record);

    BankCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCard record);

    int updateByPrimaryKey(BankCard record);

	BankCard selectValidById(Long id);

	void removeBankCardById(Long id);

	List<BankCard> selectBankCardList(BankCard bankCard);

	List<BankCard> selectCardForDfType(BankCard bankCard);

	void updateCardDfTypeById(BankCard bankCard);

	List<BankCard> selectIdenticalValidCard(BankCard bankCard);

	void updateCardStatusById(BankCard bankCard);

	void updateCardNoAgreeById(BankCard bankCard);

	int selectCardNumber(BankCard bankCard);

	BankCard getBankCard(BankCard bankCard);

	List<BankCard> findBankCardListByUserId(Long userId);

	BankCard getDefaultBankCardByUserId(Long userId);
}