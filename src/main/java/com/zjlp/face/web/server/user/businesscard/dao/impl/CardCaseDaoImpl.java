package com.zjlp.face.web.server.user.businesscard.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.BusinessCardMapper;
import com.zjlp.face.web.mapper.CardCaseMapper;
import com.zjlp.face.web.server.user.businesscard.dao.CardCaseDao;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;

@Repository("cardCaseDao")
public class CardCaseDaoImpl implements CardCaseDao{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long addCardCase(CardCase cardCase) {
		sqlSession.getMapper(CardCaseMapper.class).insertSelective(cardCase);
		return cardCase.getId();
	}

	@Override
	public CardCase getCardCaseByUserId(Long userId, Long cardId) {
		return sqlSession.getMapper(CardCaseMapper.class).getCardCaseByUserId(userId,cardId);
	}

	@Override
	public BusinessCard selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(BusinessCardMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void deleteById(Long id) {
		sqlSession.getMapper(CardCaseMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public Integer selectCardCaseCount(BusinessCardVo vo) {
		return sqlSession.getMapper(CardCaseMapper.class).selectCardCaseCount(vo);
	}

	@Override
	public List<BusinessCardVo> queryCardCase(BusinessCardVo vo) {
		return sqlSession.getMapper(CardCaseMapper.class).queryCardCase(vo);
	}

	@Override
	public CardCase selectCardCase(Long userId, Long cardid) {
		return sqlSession.getMapper(CardCaseMapper.class).selectCardCase(userId, cardid);
	}

	@Override
	public List<BusinessCardVo> queryCardCase(Long userId) {
		return sqlSession.getMapper(CardCaseMapper.class).queryCardCase(userId);
	}

}
