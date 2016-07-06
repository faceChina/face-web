package com.zjlp.face.web.server.operation.member.producer.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.operation.member.service.MemberService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Service("memberProducer")
public class MemberProducerImpl implements MemberProducer {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberService memberService;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private MarketingProducer marketingProducer;
	
	
	
	@Override
	public Long getDiscountPrice(Long userId, String shopNo, Long price,
			Integer isPreferential) throws MemberException {
		try {
			AssertUtil.notNull(userId, EC.prtNull("userId"));
			AssertUtil.hasLength(shopNo, EC.prtNull("shopNo"));
			AssertUtil.notTrue(null ==price || 0L > price, "原价错误!");
			if(!Constants.PRICE_PREFERENTIAL.equals(isPreferential)){
				log.info("商品已设置不参与会员折扣！返回原价 : "+price );
				return price;
			}
			MemberCard memberCard = memberService.getMemberCard(shopNo, userId);
			if (null == memberCard) {
				log.info("该用户 "+userId+ "不是会员！返回原价 : "+price );
				return price;
			}
			Shop shop = shopProducer.getShopByNo(shopNo);
			AssertUtil.notNull(shop, EC.prtNull("shop"));
			//查询会员折扣率
			Long consumptionAmout = memberCard.getConsumptionAmout();//用户消费总额
			Long rate = marketingProducer.getMemberActivityDiscount(shop.getUserId(),consumptionAmout);
			Long discountPrice = CalculateUtils.getDiscountPrice(price, rate.intValue());
			log.info("原价："+ price+"会员价为:" + discountPrice);
			return discountPrice;
		} catch (RuntimeException e) {
			throw new MemberException(e);
		}
	}
	
	
	@Override
	public Long initMemberEnactment(Long sellerId) throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
			return memberService.addMemberEnactment(sellerId);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public boolean sumMemberAmount(Long cardId, Long amount, Long giftAmount)
			throws MemberException {
		try {
			AssertUtil.notNull(cardId, "param[cardId] can't be null.");
			AssertUtil.notNull(amount, "param[amount] can't be null.");
			if (null == giftAmount) {
				giftAmount = 0L;
			}
			return memberService.sumMemberCardAmount(cardId, amount,giftAmount);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public boolean miniMemberAmount(Long cardId, Long amount)
			throws MemberException {
		try {
			AssertUtil.notNull(cardId, "param[cardId] can't be null.");
			AssertUtil.notNull(amount, "param[amount] can't be null.");
			return memberService.miniMemberCardAmount(cardId, amount);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Deprecated
	public MemberCard getMemberCardByUserIdAndSellerId(Long userId,
			Long sellerId) {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
			MemberCard memberCard = memberService.getMemberCard(sellerId, userId);
			if (null == memberCard){
				log.info("Card[userId="+userId+", sellerId="+sellerId+"] is not exists.");
				return null;
			} else if (Constants.FREEZE.equals(memberCard.getStatus())) {
				log.info("Card[userId="+userId+", sellerId="+sellerId+"] is freeze.");
				return null;
			}
			return memberCard;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	
	@Override
	public MemberCard getMemberCardByUserIdAndShopNo(Long userId, String shopNo) {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			Shop shop  = shopProducer.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "param[shop] can't be null.");
			AssertUtil.notNull(shop.getUserId(), "param[sellerId] can't be null.");
			return memberService.getMemberCard(shopNo, userId);
		} catch (MemberException e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberCard getMemberCardById(Long id) {
		try {
			AssertUtil.notNull(id, "param[id] can't be null.");
			return memberService.getMemberCardById(id);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}


	@Override
	@Transactional
	public boolean sumConsumAmount(Long userId, String shopNo, Long amount)
			throws MemberException {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			AssertUtil.notNull(shopNo, "param[shopNo] can't be null.");
			AssertUtil.notNull(amount, "param[amount] can't be null.");
			AssertUtil.isTrue(amount >= 0, "param[amount >= 0]");
			MemberCardDto card = memberService.getMemberCard(shopNo, userId);
			if(null == card){
				return true;
			}
			memberService.sumConsumptionAmount(card.getId(), amount);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}


	@Override
	public MemberEnactment getEnactmentBySettleId(Long userId) {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
			return memberService.getEnactmentBySettleId(userId);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
}
