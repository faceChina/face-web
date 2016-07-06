package com.zjlp.face.web.server.operation.member.service;

import java.util.Date;

import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;

public interface IntegralService {
	
	/**
	 * 新增积分操作记录
	 * @Title: addIntegralRecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param integralRecode
	 * @date 2015年4月15日 下午5:36:28  
	 * @author dzq
	 */
	void addIntegralRecode(IntegralRecode integralRecode);
	
	void addIntegralRecode(IntegralRecode integralRecode,Date date); 
	
	/**
	 * 新增赠送积分记录
	 * @Title: addSendIntegralRecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sendIntegralRecode 积分记录
	 * @return
	 * @date 2015年9月8日 下午12:47:53  
	 * @author lys
	 */
	Long addSendIntegralRecode(SendIntegralRecode sendIntegralRecode);
	
	/**
	 * 统计赠送未领取积分个数
	 * @Title: sumSendIntegral 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param userId 买家
	 * @return
	 * @date 2015年9月8日 下午4:17:51  
	 * @author lys
	 */
    Long sumSendIntegral(Long sellerId, Long userId);
    
    /**
     * 统一编辑积分记录的状态
     * @Title: editStatus 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param sellerId
     * @param userId
     * @param oldStatus
     * @param status
     * @return
     * @date 2015年9月8日 下午4:44:08  
     * @author lys
     */
    boolean editStatus(Long sellerId, Long userId, Integer oldStatus, Integer status);
    
    /**
     * 统一修改已领取积分记录的状态
     * @Title: editClaimIntegralStatus 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param sellerId 卖家
     * @param userId 买家
     * @return
     * @date 2015年9月8日 下午4:19:24  
     * @author lys
     */
    boolean editClaimIntegralStatus(Long sellerId, Long userId);

    /**
     * 最近一条赠送记录
     * @Title: getLastRecord 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param sendIntegralRecode 查询条件
     * @return
     * @date 2015年9月10日 下午3:04:40  
     * @author lys
     */
	SendIntegralRecode getLastRecord(SendIntegralRecode sendIntegralRecode);
	
}
