package com.zjlp.face.web.server.operation.member.service.impl;

import java.awt.color.CMMException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.server.operation.member.dao.MemberWechatRelationDao;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;
import com.zjlp.face.web.server.operation.member.service.MemberCardService;
import com.zjlp.face.web.server.operation.member.service.MemberService;

@Service
public class MemberCardServiceImpl implements MemberCardService {

	private static final String LINE = "-";
	
	@Autowired
	private MemberService memberService;
	@Autowired @Deprecated
	private MemberWechatRelationDao memberWechatRelationDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public MemberCard getMemberCardId(Long adminId, String fakeId, String shopNo) {
		// 根据fakeId跟 adminId查询会员卡
		MemberCard memberCard = new MemberCard();
		memberCard.setSellerId(adminId);
		memberCard.setFakeId(fakeId);
		memberCard = this.getMemberCard(memberCard);
		if(null!=memberCard){
			return memberCard;
		}
		// 没有则给该fakeid对应的微信号添加会员卡
		// 查询是否还可以领卡
		Integer cardNo = memberService.GetCardNo(adminId);
		if(null != cardNo) {
			// 根据userId查询用户会员卡
			MemberEnactment memberEnactment = memberService.getEnactmentBySettleId(adminId);
			AssertUtil.notNull(memberEnactment, "会员卡信息为空");
//			memberCard = _generateMemberCard(cardNo, fakeId, memberEnactment);
			memberService.addMemberCard(memberCard);
//			AssertUtil.notNull(memberCard.getId(), "会员卡新增失败");
			return memberCard;
		}else {
			return null;
		}
	}
	
	
	@Override
	public MemberCard generateMemberCard(Long adminId, Long userId,
			String shopNo) {
		//过滤
		MemberCard memberCard = this.getMemberCard(new MemberCard(adminId, userId));
		AssertUtil.isNull(memberCard, CErrMsg.ALREADY_EXISTS.getErrCd(), 
				CErrMsg.ALREADY_EXISTS.getErrorMesage(), "会员卡：sellerId="+adminId+",userId="+userId);
		//会员卡生成
		Integer cardNo = memberService.GetCardNo(adminId);
		AssertUtil.notNull(cardNo, "CARDNO_OUTRANGE", "会员卡已经没有库存了");
		MemberEnactment memberEnactment = memberService.getEnactmentBySettleId(adminId);
		AssertUtil.notNull(memberEnactment, "会员卡信息为空");
		memberCard = _generateMemberCard(cardNo, userId, memberEnactment);
		memberService.addMemberCard(memberCard);
		//返回
		return memberCard;
	}

	


	@Deprecated
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public MemberCard generateMemberCard(String openId, Long sellerId, String fakeId,
			String shopNo) {
		
		// 获取会员卡id
		MemberCard memberCard = this.getMemberCardId(sellerId, fakeId, shopNo);
		if(null == memberCard) {
			return null;
		}
		// 根据fakeid跟shopNo查询会员微信关注关系
		MemberWechatRelation memberWechatRelation = new MemberWechatRelation();
		memberWechatRelation.setOpenId(openId);
		memberWechatRelation.setShopNo(shopNo);
		memberWechatRelation = memberWechatRelationDao.getMemberWechatRelation(memberWechatRelation);
		if(null == memberWechatRelation) {
			memberWechatRelation = _generateMemberWechatRelation(openId, sellerId, fakeId, shopNo, memberCard.getId());
			memberWechatRelationDao.add(memberWechatRelation);
			AssertUtil.notNull(memberWechatRelation.getId(), "新增会员微信关注关系失败");
		}
		return memberCard;
	}
	
	@Deprecated
	private MemberWechatRelation _generateMemberWechatRelation(String openId,
			Long sellerId, String fakeId, String shopNo, long memberCardId) {
		MemberWechatRelation memberWechatRelation = new MemberWechatRelation();
		memberWechatRelation.setOpenId(openId);
		memberWechatRelation.setSellerId(sellerId);
		memberWechatRelation.setFakeId(fakeId);
		memberWechatRelation.setShopNo(shopNo);
		memberWechatRelation.setMemberCardId(memberCardId);
		memberWechatRelation.setCreateTime(new Date());
		return memberWechatRelation;
	}

	/**
	 * 组装会员卡
	 * @Title: _generateMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardNo
	 * @param fakeId
	 * @param memberEnactment
	 * @return
	 * @date 2015年4月14日 下午5:19:58  
	 * @author ah
	 */
	public static MemberCard _generateMemberCard(Integer cardNo, Long userId, MemberEnactment memberEnactment) {
		Date date =  new Date();
		MemberCard memberCard = new MemberCard();
		memberCard.setImgPath(memberEnactment.getImgPath());
		memberCard.setSellerId(memberEnactment.getSellerId());
		memberCard.setUserId(userId);
//		memberCard.setFakeId(fakeId);
		memberCard.setMemberCardPrefix(memberEnactment.getCardCode());
		memberCard.setMemberCardNo(cardNo);
		memberCard.setMemberCard(memberEnactment.getCardCode() + LINE + cardNo);
		memberCard.setConsumptionAmout(0L);
		memberCard.setRealConsumptionAccount(0L);
		memberCard.setAmount(0L);
		memberCard.setAvailableIntegral(0L);
		memberCard.setFrozenIntegral(0L);
		memberCard.setStatus(Constants.ISDEFAULT);
		memberCard.setClaimTime(date);
		memberCard.setCreateTime(date);
		memberCard.setUpdateTime(date);
		return memberCard;
	}

	@Override
	public MemberCard getMemberCard(MemberCard memberCard) {
		memberCard = memberService.getMemberCard(memberCard);
		return memberCard;
	}

	/**
	 * 查询会员卡是否已领取
	 */
	@Override
	public MemberCard isReceiveMemberCard(Long userId, Long shopNo) {
		MemberCard memberCard = this.getMemberCard(new MemberCard(userId, shopNo));
		 
		return memberCard;
	}

}
