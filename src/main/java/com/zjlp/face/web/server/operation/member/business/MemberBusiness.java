package com.zjlp.face.web.server.operation.member.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.operation.member.domain.vo.BalanceVo;
import com.zjlp.face.web.server.operation.member.domain.vo.ConsumeVo;
import com.zjlp.face.web.server.operation.member.domain.vo.IntegralVo;
import com.zjlp.face.web.server.operation.member.domain.vo.MemberAttendanceRecordVo;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.domain.User;

/**
 * 会员卡业务层接口
 * @ClassName: MemberBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月11日 下午2:55:35
 */
public interface MemberBusiness {

	/**
	 * 会员卡充值
	 * 
	 * @Title: recharge
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param sellerId
	 *            卖家id
	 * @param userId
	 *            买家id
	 * @param cardId
	 *            会员卡id
	 * @param activeId
	 *            充值活动id
	 * @param amount
	 *            充值金额
	 * @return
	 * @throws MemberException
	 * @date 2015年4月11日 下午2:56:51
	 * @author lys
	 */
	@Deprecated
	boolean recharge(Long sellerId, Long userId, Long cardId, Long activeId, Long amount) throws MemberException;
	
	/**
	 * 签到送积分
	 * 
	 * @Title: registrat
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺
	 * @param userId
	 *            买家id
	 * @param cardId
	 *            会员卡id
	 * @return (结果【签到成功】：当次送积分数，下次送积分数【连续签到】，总积分)
	 * @throws MemberException
	 * @date 2015年4月11日 下午5:05:38
	 * @author lys
	 */
	MemberAttendanceRecordVo registrat(String shopNo, Long userId,
			Long cardId) throws MemberException;
	
	/**
	 * 是否有签到规则
	 * 
	 * @Title: hasRegistratRule 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺
	 * @return
	 * @throws MemberException
	 * @date 2015年4月21日 下午3:28:39  
	 * @author lys
	 */
	boolean hasRegistratRule(String shopNo) throws MemberException;

	/**
	 * 获取会员设置
	 * @Title: getMemberEnactment 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @return
	 * @throws MemberException
	 * @date 2015年4月13日 下午2:06:38  
	 * @author lys
	 */
	MemberEnactment getMemberEnactment(Long sellerId) throws MemberException;

	/**
	 * 编辑会员设置
	 * @Title: editMemberEnactment 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param enactment 会员设置
	 * @param sellerId 卖家id
	 * @return
	 * @date 2015年4月13日 下午2:09:44  
	 * @author lys
	 */
	boolean editMemberEnactment(MemberEnactment enactment, Long sellerId) throws MemberException;

	/**
	 * 会员规则列表查询
	 * @Title: findMemberList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId
	 * @return
	 * @throws MemberException
	 * @date 2015年4月13日 下午2:15:02  
	 * @author lys
	 */
	List<MemberRule> findMemberList(Long sellerId) throws MemberException;

	/**
	 * 会员规则保存
	 * @Title: editRule 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param list 规则列表
	 * @param sellerId 卖家id
	 * @return
	 * @throws MemberException
	 * @date 2015年4月13日 下午2:19:20  
	 * @author lys
	 */
	boolean editRule(List<MemberRule> list, Long sellerId) throws MemberException;

	/**
	 * 查询营销工具
	 * @Title: getMarketingTool 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId
	 * @param mkTypeXf
	 * @param pdTypeJf
	 * @return
	 * @throws MemberException
	 * @date 2015年4月14日 下午8:41:35  
	 * @author lys
	 */
	MarketingTool getMarketingTool(Long sellerId, Integer mkTypeXf,
			Integer pdTypeJf) throws MemberException;

	/**
	 * 添加或修改标准活动
	 * @Title: addOrEditStandardActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param cj 场景
	 * @param pd 范围
	 * @param detail 活动详情
	 * @return
	 * @throws MemberException
	 * @date 2015年4月15日 上午9:44:54  
	 * @author lys
	 */
	boolean addOrEditStandardActivity(Long sellerId, Integer cj, Integer pd,
			MarketingActivityDetail detail) throws MemberException;
	
	/**
	 * 添加或修改非标准活动
	 * @Title: addOrEditNotStandardActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param cj 场景
	 * @param pd 范围
	 * @param activity 活动
	 * @return
	 * @throws MemberException
	 * @date 2015年4月16日 上午10:18:45  
	 * @author lys
	 */
	boolean addOrEditNotStandardActivity(Long sellerId, Integer cj, Integer pd, 
			MarketingActivityDto activity) throws MemberException;

	/**
	 * 查找活动详情
	 * @Title: findDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param cjType 场景
	 * @param pdType 范围
	 * @return
	 * @date 2015年4月15日 下午2:57:04  
	 * @author lys
	 */
	List<MarketingActivityDetail> findDetail(Long sellerId, Integer cjType,
			Integer pdType);

	/**
	 * 
	 * @Title: findIntegralCountForWap 
	 * @Description: 通过用户ID和shopNo查询积分总数
	 * @param userId
	 * @param shopNo
	 * @return
	 * @date 2015年4月14日 下午6:14:20  
	 * @author cbc
	 */
	Long findIntegralCountForWap(Long userId, String shopNo);

	/**
	 * 
	 * @Title: findMemberCardIdByUserIdAndShopId 
	 * @Description: 通过userID和sellerID查询会员卡ID 
	 * @param userId
	 * @param shopNo
	 * @return
	 * @date 2015年4月14日 下午8:09:48  
	 * @author cbc
	 */
	Long findMemberCardIdByUserIdAndShopNo(Long userId, String shopNo);

	/**
	 * 
	 * @Title: findIntegralListForWap 
	 * @Description: 通过会员卡ID查询积分记录
	 * @param memberCardId
	 * @return
	 * @date 2015年4月14日 下午8:30:57  
	 * @author cbc
	 */
	List<IntegralVo> findIntegralListForWap(Long memberCardId, String openYear, String openMonth);

	/**
	 * 
	 * @Title: findMemberCardById 
	 * @Description: 通过IC查询会员卡
	 * @param memberCardId
	 * @return
	 * @date 2015年4月15日 上午10:56:32  
	 * @author cbc
	 */
	MemberCard findMemberCardById(Long memberCardId);

	/**
	 * 
	 * @Title: findBalanceListByMemberCardId 
	 * @Description: 通过ID和时间查询消费账单
	 * @param memberCardId
	 * @param year
	 * @param month
	 * @return
	 * @date 2015年4月15日 上午10:57:39  
	 * @author cbc
	 */
	List<BalanceVo> findBalanceListByMemberCardId(Long memberCardId,
			String year, String month);


	/**
	 * 初始化会员卡
	 * @Title: generateMember 
	 * @Description: (查询该openid是否有会员卡，没有则添加会员卡，有则不做处理) 
	 * @param openId
	 * @param adminId 店铺用户id
	 * @date 2015年4月13日 下午5:10:18  
	 * @author ah
	 * @param shop 
	 */
	@Deprecated
	void generateMemberCard(String openId, Long adminId);
	
	/**
	 * 领取会员卡
	 * @Title: generateMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param shopNo 店铺号
	 * @param userId 买家
	 * @return
	 * @date 2015年9月4日 上午11:34:51  
	 * @author lys
	 */
	MemberCard generateMemberCard(Long sellerId, String shopNo, Long userId);
	
	/**
	 * 绑定会员卡
	 * @Title: bindMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param openId
	 * @param user
	 * @param shop
	 * @date 2015年4月14日 下午8:26:49  
	 * @author ah
	 */
	@Deprecated  //不进行关注领卡
	void bindMemberCard(String openId, User user, Shop shop);
	
	/**
	 * 手机端会员卡展示
	 * @Title: getMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCard
	 * @return
	 * @date 2015年4月14日 下午8:41:03  
	 * @author ah
	 */
	MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto);
	
	/**
	 * 查询会员卡列表
	 * @Title: findMemberCardDtoBySellerId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @param pagination
	 * @return
	 * @date 2015年4月15日 上午11:11:33  
	 * @author ah
	 */
	Pagination<MemberCardDto> findMemberCardDtoByUserId(MemberCardDto memberCardDto, Pagination<MemberCardDto> pagination);

	/**
	 * 充值活动列表
	 * @Title: findCzPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination 分页信息
	 * @param id 充值营销工具id
	 * @return
	 * @throws MemberException
	 * @date 2015年4月15日 下午9:13:22  
	 * @author lys
	 */
	Pagination<MarketingActivityDto> findCzPage(
			Pagination<MarketingActivityDto> pagination, Long id) throws MemberException;

	/**
	 * 查找对应的活动以及详情
	 * @Title: getMarketingActivityById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 活动id
	 * @param sellerId 卖家
	 * @return
	 * @throws MemberException
	 * @date 2015年4月16日 下午2:16:09  
	 * @author lys
	 */
	MarketingActivityDto getMarketingActivityById(Long id, Long sellerId) throws MemberException;

	/**
	 * 删除非标准活动
	 * @Title: delActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param sellerId
	 * @return
	 * @throws MemberException
	 * @date 2015年4月16日 下午2:49:17  
	 * @author lys
	 */
	boolean delNotStantdardActivity(Long id, Long sellerId) throws MemberException;

	/**
	 * 查询卖家对应的所有会员卡
	 * @Title: findMemberCardPageForSeller 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardDto 查询条件
	 * @param pagination 分页信息
	 * @return
	 * @throws MemberException
	 * @date 2015年4月16日 下午3:48:34  
	 * @author lys
	 */
	Pagination<MemberCardDto> findMemberCardPageForSeller(
			MemberCardDto cardDto, Pagination<MemberCardDto> pagination) throws MemberException;
	
	/**
	 * 根据卡id查询会员卡
	 * @Title: getMemberCardById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @param sellerId 卖家
	 * @param userId 买家
	 * @return
	 * @throws MemberException 如果查询失败则抛出异常
	 * @date 2015年4月16日 下午5:52:44  
	 * @author lys
	 */
	MemberCard getMemberCardById(Long id, Long sellerId, Long userId) throws MemberException;

	/**
	 * 
	 * @Title: saveInfomation 
	 * @Description: 保存用户的具体资料
	 * @param memberCardDto
	 * @date 2015年4月16日 下午7:04:49  
	 * @author cbc
	 */
	void saveInfomation(MemberCardDto memberCardDto);

	/**
	 * 修改会员卡资料
	 * @Title: editMemberCard 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param card
	 * @throws MemberException
	 * @date 2015年4月16日 下午8:58:25  
	 * @author lys
	 */
	void editMemberCard(MemberCard card, Long sellerId) throws MemberException;

	/**
	 * 查找会员交易记录
	 * @Title: findTransactionPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto 查询条件
	 * @param pagination 分页信息
	 * @return
	 * @throws MemberException
	 * @date 2015年4月17日 下午1:48:28  
	 * @author lys
	 */
	Pagination<MemberTransactionRecordDto> findTransactionPage(
			MemberTransactionRecordDto dto,
			Pagination<MemberTransactionRecordDto> pagination) throws MemberException;

	/**
	 * 改变营销工具的状态
	 * @Title: editMarketingRuleStatus 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param toolId 营销工具id
	 * @param status 改变的状态
	 * @return
	 * @throws MemberException
	 * @date 2015年4月17日 下午5:07:23  
	 * @author lys
	 */
	boolean editMarketingRuleStatus(Long sellerId, Long toolId, Integer status) throws MemberException;

	/**
	 * 删除营销活动详情
	 * @Title: delDetailById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 详情id
	 * @param sellerId 卖家
	 * @return
	 * @throws MemberException
	 * @date 2015年4月18日 上午11:24:43  
	 * @author lys
	 */
	boolean delDetailById(Long id, Long sellerId) throws MemberException;
	
	/**
	 * 今日是否签到
	 * @Title: isTodayAttendance 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 卡主键
	 * @return
	 * @throws MemberException
	 * @date 2015年4月18日 下午3:31:05  
	 * @author lys
	 */
	boolean isTodayAttendance(Long cardId) throws MemberException;
	
	

	/**
	 * 通过
	 * @Title: findOrderListByUserIdAndSellerIdForWap 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param sellerId
	 * @param object
	 * @param object2
	 * @return
	 * @date 2015年4月21日 下午2:55:15  
	 * @author cbc
	 * @param memberCardId 
	 */
	List<ConsumeVo> findOrderListByUserIdAndSellerIdForWap(Long userId,
			Long sellerId, String year, String month, Long memberCardId);

	/**
	 * 
	 * @Title: getMemberCardForShow 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @return
	 * @date 2015年4月23日 下午7:55:05  
	 * @author ah
	 */
	MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto);
	
	/**
	 * 卖家向买家赠送定量积分
	 * @Title: sendIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户
	 * @param sellerId 卖家
	 * @param integral 积分个数
	 * @return
	 * @throws MemberException
	 * @date 2015年9月8日 上午11:30:26  
	 * @author lys
	 */
	boolean sendIntegral(Long userId, Long sellerId, Long integral) throws MemberException;
	
	/**
	 * 领取赠送积分
	 * 
	 * <p>
	 * 
	 * 1.买家如果已领取会员卡：直接赠送；如果没有领取会员卡：先领取会员卡，在进行赠送
	 * 
	 * @Title: claimIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @param sellerId 卖家id
	 * @return
	 * @throws MemberException
	 * @date 2015年9月8日 上午11:38:39  
	 * @author lys
	 */
	Long claimIntegral(Long userId, Long sellerId) throws MemberException;

	/**
	 * 卖家是否领取会员卡，如已领取，直接跳到http://192.168.1.198/wap/HZJZ1509041634vqbeL1/buy/member/index.htm页面
	 * 如果未领取就直接领取。
	 * @param userId 用户ID
	 * @param sellerId 卖家ID
	 * @return
	 * @throws MemberException
	 * @date 2015年9月8日 上午11:19
	 */
	MemberCard isReceiveMemberCard(Long userId, Long sellerId) throws MemberException;
	
	/**
	 * 刷脸App客户积分  增加积分赠送记录查询
	 * @param sendDto
	 * @param pagination
	 * @return
	 * @throws MemberException
	 * @date 2015年9月14日 
	 */
	Pagination<SendIntegralRecodeDto> queryIntegralRecode(SendIntegralRecodeDto sendDto, Pagination<SendIntegralRecodeDto> pagination) throws MemberException;
}
