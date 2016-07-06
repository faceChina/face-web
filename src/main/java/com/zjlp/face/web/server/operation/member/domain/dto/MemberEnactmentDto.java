package com.zjlp.face.web.server.operation.member.domain.dto;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;

public class MemberEnactmentDto extends MemberEnactment {

	private static final long serialVersionUID = -4710235306826544248L;

	public static void checkInput(MemberEnactment enactment) {
		// 会员卡名称
		AssertUtil.hasLength(enactment.getCardName(),
				"[MemberEnactment check] cardName can't be null.");
		// 图片路径
		AssertUtil.hasLength(enactment.getImgPath(),
				"[MemberEnactment check] imgPath can't be null.");
		// 会员卡名称颜色
		AssertUtil.hasLength(enactment.getCardNameColor(),
				"[MemberEnactment check] cardNameColor can't be null.");
		// 卡号文字颜色
		AssertUtil.hasLength(enactment.getCardNoColor(),
				"[MemberEnactment check] cardNoColor can't be null.");
		// 卡号编码
		AssertUtil.hasLength(enactment.getCardCode(),
				"[MemberEnactment check] cardCode can't be null.");
		// 起始卡号
		AssertUtil.notNull(enactment.getStartNo(),
				"[MemberEnactment check] startNo can't be null.");
		// 截止卡号
		AssertUtil.notNull(enactment.getEndNo(),
				"[MemberEnactment check] endNo can't be null.");
	}
	
	/**
	 * 会员初始化
	 * @Title: initMemberEnactment 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId
	 * @return
	 * @date 2015年4月10日 上午9:49:07  
	 * @author lys
	 */
	public static MemberEnactment initMemberEnactment(Long sellerId){
    	MemberEnactment memberEnactment = new MemberEnactment();
    	memberEnactment.setSellerId(sellerId);
    	memberEnactment.setCardName("会员卡");
    	memberEnactment.setCardNameColor("#ffffff");;
    	memberEnactment.setCardNoColor("#ffffff");;
    	memberEnactment.setCardCode("BSD");;
    	memberEnactment.setStartNo(1);
    	memberEnactment.setEndNo(65535);;
    	memberEnactment.setStatus(1);;
    	memberEnactment.setImgPath("/resource/base/img/member-default.jpg");
    	return memberEnactment;
    }
}
