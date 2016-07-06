package com.zjlp.face.web.server.operation.member.service;

import com.zjlp.face.web.server.operation.member.domain.MemberCard;

/**
 * 会员卡基础服务接口
 * @ClassName: MemberCardService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月14日 下午9:03:39
 */
public interface MemberCardService {

	/**
	 * 获取会员卡id
	 * @Title: getMemberCardId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param adminId
	 * @param fakeId
	 * @param shopNo
	 * @date 2015年4月14日 下午9:11:12  
	 * @author ah
	 */
	@Deprecated
	public MemberCard getMemberCardId(Long adminId, String fakeId, String shopNo);
	
	/**
	 * 会员卡生成
	 * @Title: generateMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param adminId 商家
	 * @param userId 用户
	 * @param shopNo 店铺编号
	 * @return
	 * @date 2015年9月4日 上午10:04:56  
	 * @author lys
	 */
	public MemberCard generateMemberCard(Long adminId, Long userId, String shopNo);
	
	/**
	 * 初始化会员卡
	 * @Title: generateMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param openId
	 * @param adminId
	 * @param fakeId
	 * @param shopNo
	 * @date 2015年4月21日 上午11:24:36  
	 * @author ah
	 */
	@Deprecated
	MemberCard generateMemberCard(String openId, Long adminId, String fakeId, String shopNo);
	
	/**
	 * 查询会员卡
	 * @Title: getMemberCard 
	 * @Description: (查询会员卡) 
	 * @param memberCard
	 * @return
	 * @date 2015年4月14日 下午3:27:31  
	 * @author ah
	 */
	MemberCard getMemberCard(MemberCard memberCard);
	
	/**
	 * 是否领取会员卡
	 * @param userId
	 * @param shopNo
	 * @return
	 */
	public MemberCard isReceiveMemberCard(Long userId, Long shopNo);
}
