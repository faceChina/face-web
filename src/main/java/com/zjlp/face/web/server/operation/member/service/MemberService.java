package com.zjlp.face.web.server.operation.member.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.member.domain.ConsumptionAdjustmentRecord;
import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

public interface MemberService {

	/**
	 * 初始化会员设置
	 * 
	 * <p>
	 * 
	 * 注：<br>
	 * 1.每个拥有店铺的管理者都应该生成一条会员基础设置记录<br>
	 * 2.其它的地方不应该进行调用<br>
	 * 
	 * @Title: addMemberEnactment
	 * 
	 * @param settleId
	 *            管理者
	 * @return
	 * @throws MemberException
	 * @date 2015年4月10日 上午9:52:43
	 * @author lys
	 */
	Long addMemberEnactment(Long settleId) throws MemberException;

	/**
	 * 根据管理者ID查询会员设置
	 * 
	 * @Title: getEnactmentBySettleId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param sellerId
	 *            管理者
	 * @return
	 * @throws MemberException
	 *             如果取得结果为空，抛出异常
	 * 
	 * @date 2015年4月10日 上午9:53:44
	 * @author lys
	 */
	MemberEnactment getEnactmentBySettleId(Long sellerId) throws MemberException;

	/**
	 * 修改会员设置
	 * 
	 * @Title: editEnactment
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param enactment
	 *            会员设置
	 * @param sellerId
	 *            管理者
	 * @return
	 * @throws MemberException
	 * @date 2015年4月10日 上午10:24:45
	 * @author lys
	 */
	boolean editEnactment(MemberEnactment enactment, Long sellerId)
			throws MemberException;

	/**
	 * 新增会员卡
	 * 
	 * @Title: addMemberCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param membershipCard
	 *            会员卡
	 * @return
	 * @throws MemberException
	 * @date 2015年4月10日 上午10:26:50
	 * @author lys
	 */
	Long addMemberCard(MemberCard addMemberCard)
			throws MemberException;

	/**
	 * 查询会员卡
	 * 
	 * <p>
	 * 
	 * 注：<br>
	 * 1.结果状态：正常  | 冻结
	 * 
	 * @Title: getMemberCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param settleId
	 *            管理者
	 * @param userId
	 *            用户id
	 * @return
	 * @throws MemberException
	 * @date 2015年4月10日 上午10:27:35
	 * @author lys
	 */
	MemberCardDto getMemberCard(Long settleId, Long userId)
			throws MemberException;
	
	/**
	 * 查询会员卡
	 * 
	 * <p>
	 * 
	 * 注：<br>
	 * 1.结果状态：正常  | 冻结
	 * 
	 * @Title: getMemberCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            管理者
	 * @param userId
	 *            用户id
	 * @return
	 * @throws MemberException
	 * @date 2015年4月21日 上午10:44:31  
	 * @author lys
	 */
	MemberCardDto getMemberCard(String shopNo, Long userId) throws MemberException;
	/**
	 * 查询会员卡列表
	 * @Title: findMembershipCardList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param membershipCardDto 查询条件
	 * @return
	 * @throws MemberException
	 * @date 2015年4月10日 下午3:57:02  
	 * @author lys
	 */
	List<MemberCardDto> findMemberCardList(
			MemberCardDto membershipCardDto) throws MemberException;
	
	/**
	 * 修改会员卡信息
	 * 
	 * @Title: editMembershipCard
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param membershipCard
	 *            会员卡信息
	 * @param settleId
	 *            管理者
	 * @return
	 * @throws MemberException
	 * @date 2015年4月10日 上午10:34:50
	 * @author lys
	 */
	boolean editMemberCard(MemberCard membershipCard, Long settleId)
			throws MemberException;
	
	/**
	 * 根据主键查询会员卡
	 * @Title: getMembershipCardById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws MemberException 如果查询失败，则抛出异常
	 * @date 2015年4月10日 下午4:03:39  
	 * @author lys
	 */
	MemberCard getMemberCardById(Long id) throws MemberException;
	
	/**
	 * 添加消费调整记录
	 * @Title: addConsumptionAdjustmentRecord 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param adjustmentRecord 消费额调整记录
	 * @return
	 * @throws MemberException
	 * @date 2015年4月11日 上午9:16:24  
	 * @author lys
	 */
	Long addConsumptionAdjustmentRecord(ConsumptionAdjustmentRecord adjustmentRecord) throws MemberException;
	
	/**
	 * 添加签到记录
	 * @Title: addMemberAttendanceRecord 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param attendanceRecord 签到记录
	 * @return
	 * @throws MemberException
	 * @date 2015年4月11日 上午10:59:49  
	 * @author lys
	 */
	Long addMemberAttendanceRecord(MemberAttendanceRecord attendanceRecord) throws MemberException;
	
	/**
	 * 返回最近该会员卡最近的一条签到记录
	 * @Title: getLastRecordByMemberCardId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId 会员卡id
	 * @return 如果没有签到记录，则返回空，否则返回最近一条签到记录
	 * @throws MemberException
	 * @date 2015年4月11日 上午11:18:07  
	 * @author lys
	 */
	MemberAttendanceRecord getLastRecordByMemberCardId(Long memberCardId) throws MemberException;
	

	/**
	 * 会员卡充值
	 * @Title: sumMemberCardAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 会员卡
	 * @param amount 充值金额
	 * @param amount 赠送金额
	 * @return
	 * @throws MemberException
	 * @date 2015年4月11日 下午6:31:58  
	 * @author lys
	 */
	boolean sumMemberCardAmount(Long cardId, Long amount, Long giftAmount) throws MemberException;
	
	/**
	 * 会员卡消费
	 * @Title: miniMemberCardAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 会员卡
	 * @param amount 充值
	 * @return
	 * @throws MemberException
	 * @date 2015年4月15日 下午8:04:42  
	 * @author lys
	 */
	boolean miniMemberCardAmount(Long cardId, Long amount) throws MemberException;

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
	 * 查询是否可以领卡
	 * @Title: IsGetCard 
	 * @Description: (返回null时则为不可领卡，非空时则为新卡卡号) 
	 * @param userId
	 * @return
	 * @date 2015年4月14日 下午4:54:23  
	 * @author ah
	 */
	Integer GetCardNo(Long userId);

	/**
	 * 查询会员看分页列表
	 * @Title: findMemberCardPageList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @param pagination
	 * @return
	 * @date 2015年4月15日 上午11:32:53  
	 * @author ah
	 */
	Pagination<MemberCardDto> findMemberCardPageList(
			MemberCardDto memberCardDto, Pagination<MemberCardDto> pagination);

	/**
	 * 查询会员卡
	 * @Title: getMemberCardForWap 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @return
	 * @date 2015年4月15日 下午4:12:47  
	 * @author ah
	 */
	MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto);

	/**
	 * 查询卖家的会员卡列表
	 * @Title: findCardPageForSeller 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardDto
	 * @return
	 * @date 2015年4月17日 上午9:29:47  
	 * @author lys
	 */
	List<MemberCardDto> findCardPageForSeller(MemberCardDto cardDto);

	/**
	 * 查询卖家会员卡件数
	 * @Title: getCountForSeller 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardDto
	 * @return
	 * @date 2015年4月17日 上午9:30:06  
	 * @author lys
	 */
	Integer getCountForSeller(MemberCardDto cardDto);

	/**
	 * 累计签到积分
	 * @Title: sumMemberCardIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 会员卡id
	 * @param integral 累加积分
	 * @return
	 * @date 2015年4月17日 上午9:29:20  
	 * @author lys
	 */
	boolean sumMemberCardIntegral(Long cardId, Integer integral);
	

	/**
	 * 
	 * @Title: findMemberTransactionRecordListByTime 
	 * @Description: 通过会员卡ID和时间查询账单
	 * @param memberCardId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @date 2015年4月15日 上午11:23:26  
	 * @author cbc
	 */
	List<MemberTransactionRecord> findMemberTransactionRecordListByTime(
			Long memberCardId, String beginTime, String endTime);

	/**
	 * 
	 * @Title: findIntegralCountForWap 
	 * @Description: 通过卖家ID和用户ID查询积分总数
	 * @param userId
	 * @param sellerId
	 * @return
	 * @date 2015年4月15日 下午4:36:22  
	 * @author cbc
	 */
	@Deprecated
	Long findIntegralCountForWap(Long userId, Long sellerId);

	/**
	 * 
	 * @Title: findMemberCardIdByUserIdAndShopId 
	 * @Description: 通过卖家ID和用户ID查询会员卡ID
	 * @param userId
	 * @param sellerId
	 * @return
	 * @date 2015年4月15日 下午4:36:52  
	 * @author cbc
	 */
	@Deprecated
	Long findMemberCardIdByUserIdAndShopId(Long userId, Long sellerId);

	/**
	 * 
	 * @Title: findIntegralRecordListByMemberCardId 
	 * @Description: 通过会员卡ID和时间查询积分记录
	 * @param memberCardId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @date 2015年4月15日 下午4:37:22  
	 * @author cbc
	 */
	List<IntegralRecode> findIntegralRecordListByMemberCardId(
			Long memberCardId, String beginTime, String endTime);

	/**
	 * 
	 * @Title: saveInfomation 
	 * @Description: 保存会员的详细资料
	 * @param memberCardDto
	 * @date 2015年4月16日 上午11:58:31  
	 * @author cbc
	 */
	void saveInfomation(MemberCardDto memberCardDto);
	
	
	/**
	 * 扣除会员卡积分
	 * @Title: miniMemberCardIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cardId 会员卡id
	 * @param integral 扣除积分数
	 * @return
	 * @date 2015年4月17日 上午9:31:31  
	 * @author lys
	 */
	boolean miniMemberCardIntegral(Long cardId, Integer integral);

	/**
	 * 修改消费额
	 * 
	 * 1.返回0值，代表没有进行修改
	 * 
	 * 2.返回整数值为修改值（带正负号）
	 * 
	 * @Title: editConsumptionAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param consumptionAmout
	 * @date 2015年4月17日 上午11:48:43  
	 * @author lys
	 */
	Long editConsumptionAmount(Long id, Long consumptionAmout);

	/**
	 * 查询会员微信关注关系
	 * @Title: getMemberWechatRelation 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberWechatRelation
	 * @return
	 * @date 2015年4月21日 下午12:01:59  
	 * @author ah
	 */
	@Deprecated
	MemberWechatRelation getMemberWechatRelation(
			MemberWechatRelation memberWechatRelation);

	/**
	 * 编辑会员微信关注关系
	 * @Title: editMemberWechatRelation 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberWechatRelation
	 * @date 2015年4月21日 下午2:00:11  
	 * @author ah
	 */
	@Deprecated
	void editMemberWechatRelation(MemberWechatRelation memberWechatRelation);
	
	/**
	 * 累加消费金额
	 * @Title: sumConsumptionAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 
	 * @param amount
	 * @return
	 * @date 2015年4月21日 下午5:38:58  
	 * @author lys
	 */
	boolean sumConsumptionAmount(Long cardId, Long amount);

	/**
	 * 会员卡展示
	 * @Title: getMemberCardForShow 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardDto
	 * @return
	 * @date 2015年4月23日 下午8:46:24  
	 * @author ah
	 */
	MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto);
	
	/**
	 * 赠送积分记录列表
	 * @param sendIntegralRecodeDto
	 * @return
	 * @throws MemberException
	 * @author jushuang
	 * @date 2015.9.14
	 */
	List<SendIntegralRecodeDto> querySendIntegralRecode(SendIntegralRecodeDto sendIntegralRecodeDto) throws MemberException;

	/**
	 * 查询积分记录数据
	 * @param dto
	 * @return
	 * @throws MemberException
	 * @author jushuang
	 * @date 2015.9.14
	 */
	Integer getSendIntegralRecode(SendIntegralRecodeDto dto) throws MemberException;
}
