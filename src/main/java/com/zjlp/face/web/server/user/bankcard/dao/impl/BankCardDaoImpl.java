package com.zjlp.face.web.server.user.bankcard.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.BankCardMapper;
import com.zjlp.face.web.server.user.bankcard.dao.BankCardDao;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;

@Repository("bankCardDao")
public class BankCardDaoImpl implements BankCardDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addBankCard(BankCard bankCard) {
		sqlSession.getMapper(BankCardMapper.class).insertSelective(bankCard);
		return bankCard.getId();
	}

	@Override
	public BankCard getBankCardById(Long id) {
		return sqlSession.getMapper(BankCardMapper.class).selectValidById(id);
	}

	@Override
	public void removeBankCardById(Long id) {
		sqlSession.getMapper(BankCardMapper.class).removeBankCardById(id);
	}

	@Override
	public List<BankCard> findBankCardList(BankCard bankCard) {
		return sqlSession.getMapper(BankCardMapper.class).selectBankCardList(bankCard);
	}

	@Override
	public BankCard getBankCardByPk(Long id) {
		return sqlSession.getMapper(BankCardMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<BankCard> getCardForDfType(BankCard bankCard) {
		return sqlSession.getMapper(BankCardMapper.class).selectCardForDfType(bankCard);
	}

	@Override
	public void setCardDfTypeById(BankCard bankCard) {
		sqlSession.getMapper(BankCardMapper.class).updateCardDfTypeById(bankCard);
	}

	@Override
	public List<BankCard> findIdenticalValidCard(BankCard bankCard) {
		return sqlSession.getMapper(BankCardMapper.class).selectIdenticalValidCard(bankCard);
	}

	@Override
	public void editCardStatusById(BankCard bankCard) {
		sqlSession.getMapper(BankCardMapper.class).updateCardStatusById(bankCard);
	}

	@Override
	public void editCardNoAgreeById(BankCard bankCard) {
		sqlSession.getMapper(BankCardMapper.class).updateCardNoAgreeById(bankCard);
	}

	@Override
	public int getCardNumber(BankCard bankCard) {
		return sqlSession.getMapper(BankCardMapper.class).selectCardNumber(bankCard);
	}

	@Override
	public void edit(BankCard card) {
		sqlSession.getMapper(BankCardMapper.class).updateByPrimaryKeySelective(card);
	}

	@Override
	public BankCard getBankCard(BankCard bankCard) {
		return sqlSession.getMapper(BankCardMapper.class).getBankCard(bankCard);
	}

	@Override
	public List<BankCard> findBankCardListByUserId(Long userId) {
		return sqlSession.getMapper(BankCardMapper.class).findBankCardListByUserId(userId);
	}

	@Override
	public BankCard getDefaultBankCardByUserId(Long userId) {
		return sqlSession.getMapper(BankCardMapper.class).getDefaultBankCardByUserId(userId);
	}
	
}
