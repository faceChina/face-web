package com.zjlp.face.web.server.trade.recharge.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.exception.ext.RechargeException;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.service.MemberService;
import com.zjlp.face.web.server.trade.recharge.business.RechargeBusiness;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.trade.recharge.domain.dto.RechargeDto;
import com.zjlp.face.web.server.trade.recharge.service.RechargeService;

@Service
public class RechargeBusinessImpl implements RechargeBusiness {

	@Autowired
	private RechargeService rechargeService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MarketingProducer marketingProducer;
	/** 充值账号类型  1.会员卡充值 */
	private static final Integer ACCOUNT_TYPE = 1;
	/** 记录类型（1 正常充值 2 人工冲正(预留)）*/
	private static final Integer COMMON_RECHARGE = 1;
	
	@Override
	public void addRecharge(Recharge recharge) throws RechargeException{
		try {
			AssertUtil.notNull(recharge.getPrice(), "参数充值金额为空");
			AssertUtil.notNull(recharge.getShopNo(), "参数充值店铺编号为空");
			AssertUtil.notNull(recharge.getSellerId(), "参数卖家用户id为空");
			AssertUtil.notNull(recharge.getUserId(), "参数买家用户id为空");
			// 1.根据买家用户id跟买家用户id查询会员卡
			MemberCard memberCard = memberService.getMemberCard(recharge.getShopNo(), recharge.getUserId());
			AssertUtil.notNull(memberCard, "该用户没有会员卡");
			recharge.setUserAccount(memberCard.getId().toString());
			// 2.根据充值金额查询活动优惠
			Long giftPrice = marketingProducer.getRechargeGiftAmount(recharge.getSellerId(), recharge.getPrice());
			// 设置赠送金额
			recharge.setGiftPrice(giftPrice);
			// 设置优惠金额
			recharge.setDiscountPrice(recharge.getPrice());
			recharge.setAccountType(ACCOUNT_TYPE);
			recharge.setRechargeChannel(Constants.RECHARGE_CHANNEL_WAP);
//			// 3.验证充值方式是否有误
//			AssertUtil.isTrue(!RechargeDto.validatePayWay(recharge.getRechargeWay()), "不合法的支付方式");
			recharge.setRecordType(COMMON_RECHARGE);
			recharge.setStatus(Constants.RECHARGE_STATUS_WAIT);
			// 获取充值编号
			String rechargeNo = GenerateCode.getRechargeNo(recharge.getShopNo(), recharge.getUserId().toString());
			recharge.setRechargeNo(rechargeNo);
			rechargeService.addRecharge(recharge);
		} catch (Exception e) {
			throw new RechargeException(e);
		}
	}

	@Override
	public List<MarketingActivityDetailDto> getRechargeList(
			RechargeDto rechargeDto) throws RechargeException{
		try {
			AssertUtil.notNull(rechargeDto.getSellerId(), "参数卖家用户id为空");
			// 查询正在使用的非标准活动详情
			return marketingProducer.findNotStandardDetail(rechargeDto.getSellerId(), MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY);
		} catch (Exception e) {
			throw new RechargeException(e);
		}
	}
	
	@Override
	public Recharge getRechargeByRechargeNo(String rechargeNo) {
		try {
			AssertUtil.notNull(rechargeNo, "参数充值订单编号为空");
			return rechargeService.getRechargeByRechargeNo(rechargeNo);
		} catch (Exception e) {
			throw new RechargeException(e);
		}
	}
}
