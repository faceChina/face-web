package com.zjlp.face.web.server.operation.member.producer.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.IntegralProducer;
import com.zjlp.face.web.server.operation.member.service.IntegralService;
import com.zjlp.face.web.server.operation.member.service.MemberService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
@Service
public class IntegralProducerImpl implements IntegralProducer {
	
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private IntegralService integralService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ShopProducer shopProducer;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void editIntegral(Long sellerId, Long userId, Long integral,Integer way,Integer type) {
		try {
			AssertUtil.notNull(sellerId,"参数[sellerId] 不能为空");
			AssertUtil.notNull(userId,"参数[userId] 不能为空");
			AssertUtil.notNull(integral,"参数[integral] 不能为空");
			AssertUtil.notNull(way,"参数[integral] 不能为空");
			MemberCard param = new MemberCard();
			param.setSellerId(sellerId);
			param.setUserId(userId);
			MemberCard memberCard = memberService.getMemberCard(param); 
//			AssertUtil.notNull(memberCard,"未找到相应的会员信息");
			if (null == memberCard) {
				log.info("该单没有参加会员");
				return;   //没有参加会员卡
			}
			this._editIntegral(memberCard, integral, way,type,new Date());
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void editIntegral(Long memberCardId, Long integral, Integer way,Integer type) {
		try {
			AssertUtil.notNull(memberCardId,"参数[memberCardId] 不能为空");
			AssertUtil.notNull(integral,"参数[integral] 不能为空");
			AssertUtil.notNull(way,"参数[integral] 不能为空");
			MemberCard memberCard = memberService.getMemberCardById(memberCardId);
			AssertUtil.notNull(memberCard,"未找到相应的会员信息");
			this._editIntegral(memberCard, integral, way,type,new Date());
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void _editIntegral(MemberCard memberCard,Long integral, Integer way,Integer type,Date date){
		Long availableIntegral = memberCard.getAvailableIntegral();//可用积分
		Long frozenIntegral = memberCard.getFrozenIntegral();
		IntegralRecode integralRecode = new IntegralRecode();
		integralRecode.setMemberCardId(memberCard.getId());
		integralRecode.setIntegral(integral);
		integralRecode.setWay(way);
		switch (way) {
			case 1:
				//获得积分
				availableIntegral = CalculateUtils.getSum(availableIntegral, integral);
				break;
			case 2:
				//抵扣积分
				frozenIntegral -= integral;
				break;
			case 3:
				//积分冻结
				availableIntegral-=integral;
				frozenIntegral =  CalculateUtils.getSum(frozenIntegral, integral);
				break;
			case 4:
				//积分解冻
				availableIntegral =  CalculateUtils.getSum(availableIntegral, integral);
				frozenIntegral -= integral;
				break;
			default:
				return;
		}
		memberCard.setAvailableIntegral(availableIntegral);
		memberCard.setFrozenIntegral(frozenIntegral);
		memberCard.setUpdateTime(date);
		memberService.editMemberCard(memberCard, memberCard.getSellerId());
		integralRecode.setType(type);
		integralRecode.setAvailableIntegral(availableIntegral);
		integralRecode.setFrozenIntegral(frozenIntegral);
		integralService.addIntegralRecode(integralRecode,date);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void frozenIntegral(Long sellerId, Long userId, Long frozenIntegral) {
		try {
			this.editIntegral(sellerId,userId, frozenIntegral, IntegralRecode.WAY_FROZEN,IntegralRecode.TYPE_DEDUCTION);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void frozenIntegral(Long memberCardId, Long frozenIntegral) {
		try {
			this.editIntegral(memberCardId, frozenIntegral, IntegralRecode.WAY_FROZEN,IntegralRecode.TYPE_DEDUCTION);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void unfrozenIntegral(Long sellerId, Long userId, Long integral) {
		try {
			this.editIntegral(sellerId, userId, integral, IntegralRecode.WAY_UNFROZEN,IntegralRecode.TYPE_DEDUCTION);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void unfrozenIntegral(Long memberCardId, Long integral) {
		try {
			this.editIntegral(memberCardId, integral, IntegralRecode.WAY_UNFROZEN,IntegralRecode.TYPE_DEDUCTION);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}
	
	@Override
	public void unfrozenIntegral(String shopNo, Long userId, Long integral) {
		try {
			Shop shop = shopProducer.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "店铺存在，操作失败");
			this.editIntegral(shop.getUserId(),userId, integral, IntegralRecode.WAY_UNFROZEN,IntegralRecode.TYPE_DEDUCTION);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void consumerGiftIntegral(Long memberCardId, Long giftIntegral) {
		try {
			this.editIntegral(memberCardId, giftIntegral, IntegralRecode.WAY_ADD,IntegralRecode.TYPE_CONSUMER);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void consumerGiftIntegral(Long sellerId, Long userId,
			Long giftIntegral) {
		try {
			this.editIntegral(sellerId,userId ,giftIntegral, IntegralRecode.WAY_ADD,IntegralRecode.TYPE_CONSUMER);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void signinIntegral(Long sellerId, Long userId, Long giftIntegral) {
		try {
			this.editIntegral(sellerId,userId ,giftIntegral, IntegralRecode.WAY_ADD,IntegralRecode.TYPE_SIGNIN);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void signinIntegral(Long memberCardId, Long giftIntegral) {
		try {
			this.editIntegral(memberCardId ,giftIntegral, IntegralRecode.WAY_ADD,IntegralRecode.TYPE_SIGNIN);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(),e);
		}
		
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deductIntegral(Long sellerId, Long userId, Long integral) {
		try {
			this.editIntegral(sellerId, userId, integral, IntegralRecode.WAY_DEDUCTION, IntegralRecode.TYPE_DEDUCTION);
		} catch (Exception e) {
			throw new MemberException(e.getMessage(), e);
		}
	}








}
