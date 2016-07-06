package com.zjlp.face.web.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;

public interface MemberCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    MemberCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);

	Long findIntegralCountForWap(@Param("userId")Long userId, @Param("sellerId")Long sellerId);

	Long findMemberCardIdByUserIdAndShopId(@Param("userId")Long userId, @Param("sellerId")Long sellerId);

	MemberCard getMemberCard(MemberCard memberCard);

	Integer getLastMemberCardBySellerId(Long userId);

	List<MemberCardDto> findPageList(Map<String, Object> paramMap);

	Integer getPageCount(MemberCardDto memberCardDto);

	MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto);

	void updateAmount(MemberCard memberCard);

	MemberCard selectByPrimaryKeyLock(Long id);

	List<MemberCardDto> selectMemberCardList(MemberCardDto memberCardDto);

	void updateConsumptionAccount(MemberCard memberCard);

	List<MemberCardDto> selectCardPageForSeller(MemberCardDto cardDto);

	Integer selectCountForSeller(MemberCardDto cardDto);

	MemberCardDto selectByShopNoAndUserId(MemberCardDto dto);

	MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto);
	
	MemberCardDto isReceiveMemberCard(Long userId, Long sellerId);
}