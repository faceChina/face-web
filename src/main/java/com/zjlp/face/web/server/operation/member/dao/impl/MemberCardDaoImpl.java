package com.zjlp.face.web.server.operation.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MemberCardMapper;
import com.zjlp.face.web.server.operation.member.dao.MemberCardDao;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
@Repository("memberCardDao")
public class MemberCardDaoImpl implements MemberCardDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(MemberCard membershipCard) {
		sqlSession.getMapper(MemberCardMapper.class).insertSelective(membershipCard);
	}

	@Override
	public List<MemberCardDto> findMemberCardList(MemberCardDto memberCardDto) {
		return sqlSession.getMapper(MemberCardMapper.class).selectMemberCardList(memberCardDto);
	}

	@Override
	public void edit(MemberCard membershipCard) {
		sqlSession.getMapper(MemberCardMapper.class).updateByPrimaryKeySelective(membershipCard);

	}

	@Override
	public MemberCard getValidById(Long id) {
		return sqlSession.getMapper(MemberCardMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public Long findIntegralCountForWap(Long userId, Long sellerId) {
		return sqlSession.getMapper(MemberCardMapper.class).findIntegralCountForWap(userId, sellerId);
	}

	@Override
	public Long findMemberCardIdByUserIdAndShopId(Long userId, Long sellerId) {
		return sqlSession.getMapper(MemberCardMapper.class).findMemberCardIdByUserIdAndShopId(userId, sellerId);
	}

	@Override
	public MemberCard getMemberCard(MemberCard memberCard) {
		return sqlSession.getMapper(MemberCardMapper.class).getMemberCard(memberCard);
	}

	@Override
	public Integer getLastMemberCardBySellerId(Long userId) {
		return sqlSession.getMapper(MemberCardMapper.class).getLastMemberCardBySellerId(userId);
	}

	@Override
	public List<MemberCardDto> findMemberCardPageList(
			MemberCardDto memberCardDto, int start, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCardDto", memberCardDto);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize);
		return sqlSession.getMapper(MemberCardMapper.class).findPageList(paramMap);
	}

	@Override
	public Integer getMemberCardCount(MemberCardDto memberCardDto) {
		return sqlSession.getMapper(MemberCardMapper.class).getPageCount(memberCardDto);
	}

	@Override
	public MemberCard getMemberCardByIdLock(Long cardId) {
		return sqlSession.getMapper(MemberCardMapper.class).selectByPrimaryKeyLock(cardId);
	}

	@Override
	public MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto) {
		return sqlSession.getMapper(MemberCardMapper.class).getMemberCardForWap(memberCardDto);
	}

	@Override
	public void editAmount(MemberCard memberCard) {
		sqlSession.getMapper(MemberCardMapper.class).updateAmount(memberCard);
	}

	@Override
	public void editConsumptionAccount(MemberCard memberCard) {
		sqlSession.getMapper(MemberCardMapper.class).updateConsumptionAccount(memberCard);
	}

	@Override
	public void saveInfomation(MemberCardDto memberCardDto) {
		this.sqlSession.getMapper(MemberCardMapper.class).updateByPrimaryKeySelective(memberCardDto);
	}

	@Override
	public List<MemberCardDto> findCardPageForSeller(MemberCardDto cardDto) {
		return sqlSession.getMapper(MemberCardMapper.class).selectCardPageForSeller(cardDto);
	}

	@Override
	public Integer getCountForSeller(MemberCardDto cardDto) {
		return sqlSession.getMapper(MemberCardMapper.class).selectCountForSeller(cardDto);
	}

	@Override
	public MemberCardDto getByShopNoAndUserId(MemberCardDto dto) {
		return sqlSession.getMapper(MemberCardMapper.class).selectByShopNoAndUserId(dto);
	}

	@Override
	public MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto) {
		return sqlSession.getMapper(MemberCardMapper.class).getMemberCardForShow(memberCardDto);
	}

}
