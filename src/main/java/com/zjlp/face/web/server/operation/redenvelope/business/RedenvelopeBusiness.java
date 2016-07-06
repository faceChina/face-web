package com.zjlp.face.web.server.operation.redenvelope.business;

import com.zjlp.face.web.exception.ext.RedenvelopeException;
import java.util.Date;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;

/**
 * 红包业务处理类
 * @ClassName: RedenvelopeBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年10月13日 上午11:53:01
 */
public interface RedenvelopeBusiness {
	
	/**
	 * 统计用户今日发红包总金额 <br>
	 * 单位：分<br>
	 * @Title: countUserSendAmountToday 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年10月13日 下午3:09:44  
	 * @author cbc
	 */
	public Long countUserSendAmountToday(Long userId);
	
	/**
	 * 功能:发红包<br>
	 * 
	 * @Title: send 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param record
	 * @return
	 * @date 2015年10月13日 下午3:14:23  
	 * @author cbc
	 */
	public Long send(SendRedenvelopeRecord record);
	
	/**
	 * 发了红包支付完成之后的动作
	 * @Title: afterPay 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @date 2015年10月13日 下午5:49:33  
	 * @author cbc
	 */
	public void afterPay(Long id, Integer payChannel);
	
	/**
	 * 通过ID查询发送红包记录
	 * @Title: getSendRocordById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年10月13日 下午5:51:12  
	 * @author cbc
	 */
	public SendRedenvelopeRecord getSendRocordById(Long id); 
	
	/**
	 * 分页查询收红包的人
	 * @Title: findReceivePerson 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param sendRecordId
	 * @return
	 * @date 2015年10月14日 下午3:41:23  
	 * @author cbc
	 */
	public Pagination<ReceivePerson> findReceivePerson(Pagination<ReceivePerson> pagination, Long sendRecordId, Date clickTime);

	/**
	 * 抢红包，判断是否该红包是否抢完
	 * 
	 * @Title: grabRedenvelope 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户ID
	 * @param redenvelopeId 红包ID
	 * @return 抢到的红包ID
	 * @throws RedenvelopeException
	 * @date 2015年10月13日 下午4:12:08  
	 * @author lys
	 */
	Long grabRedenvelope(Long userId, Long sendRedPkgId) throws RedenvelopeException;
	
	/**
	 * 拆红包
	 * 
	 * <p>
	 * 
	 * 1.判断该红包是否存在
	 * 
	 * 2.判断该红包是否已被抢
	 * 
	 * 3.对应的红包进行拆包（标志该红包被抢，更新红包记录，更新账务信息）
	 * 
	 * 4.返回相应信息
	 * 
	 * @Title: openRedenvelope 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户ID
	 * @param grabRedPkgId 对应抢到的红包ID
	 * @return
	 * @throws RedenvelopeException
	 * @date 2015年10月13日 下午4:15:24  
	 * @author lys
	 */
	Boolean openRedenvelope(Long userId, Long grabRedPkgId) throws RedenvelopeException;

	/**
	 * 通过领取者usreId和红包Id查询领取红包记录
	 * @Title: getReceiveRecordByReceiveUserIdAndSendId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param userId
	 * @return
	 * @date 2015年10月15日 上午10:21:33  
	 * @author cbc
	 */
	public ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			Long redenvelopeId, Long receiveUserId);

	/**
	 * 统计红包已经领取了多少金额
	 * @Title: sumHasReceiveMoney 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年10月15日 上午10:28:20  
	 * @author cbc
	 */
	public Long sumHasReceiveMoney(Long redenvelopeId);

	/**
	 * 统计最后一个领红包的时间
	 * @Title: caculateAllReceiveTime 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年10月15日 上午10:31:51  
	 * @author cbc
	 */
	public Long caculateLastReceiveTime(Long redenvelopeId);

	/**
	 * 
	 * @Title: getBestLuckReceive 
	 * @Description: 在所有领取中找出手气最佳
	 * @param id
	 * @return
	 * @date 2015年10月16日 上午10:12:01  
	 * @author cbc
	 */
	public ReceiveRedenvelopeRecord getBestLuckReceive(Long id);
	
}
