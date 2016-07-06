package com.zjlp.face.web.server.operation.member.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;

/**
 * 会员卡Dao
 * @ClassName: MembershipCardDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月10日 下午1:51:42
 */
public interface MemberCardDao {

	void add(MemberCard membershipCard);

	List<MemberCardDto> findMemberCardList(
			MemberCardDto memberCardDto);

	void edit(MemberCard memberCard);

	MemberCard getValidById(Long id);

	@Deprecated
	Long findIntegralCountForWap(Long userId, Long sellerId);

	@Deprecated
	Long findMemberCardIdByUserIdAndShopId(Long userId, Long sellerId);

	MemberCard getMemberCard(MemberCard memberCard);

	Integer getLastMemberCardBySellerId(Long userId);

	List<MemberCardDto> findMemberCardPageList(MemberCardDto memberCardDto,
			int start, int pageSize);

	Integer getMemberCardCount(MemberCardDto memberCardDto);

	MemberCard getMemberCardByIdLock(Long cardId);

	MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto);

	void editAmount(MemberCard memberCard);

	void editConsumptionAccount(MemberCard memberCard);

	void saveInfomation(MemberCardDto memberCardDto);

	List<MemberCardDto> findCardPageForSeller(MemberCardDto cardDto);

	Integer getCountForSeller(MemberCardDto cardDto);

	MemberCardDto getByShopNoAndUserId(MemberCardDto dto);

	MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto);

}
