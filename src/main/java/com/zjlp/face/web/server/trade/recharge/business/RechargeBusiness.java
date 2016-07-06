package com.zjlp.face.web.server.trade.recharge.business;

import java.util.List;

import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.trade.recharge.domain.dto.RechargeDto;

/**
 * 充值订单业务接口
 * @ClassName: RechargeBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月16日 下午2:24:21
 */
public interface RechargeBusiness {

	/**
	 * 新增充值订单
	 * @Title: addRecharge 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param recharge
	 * @date 2015年4月16日 下午2:40:55  
	 * @author ah
	 */
	void addRecharge(Recharge recharge);

	/**
	 * 查询会员活动充值列表
	 * @Title: getRechargeList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param rechargeDto
	 * @return
	 * @date 2015年4月16日 下午4:57:00  
	 * @author ah
	 */
	List<MarketingActivityDetailDto> getRechargeList(RechargeDto rechargeDto);
	
	/**
	 * 根据充值编号查询充值订单
	 * @Title: getRechargeByRechargeNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param rechargeNo
	 * @return
	 * @date 2015年4月16日 上午11:22:19  
	 * @author ah
	 */
	Recharge getRechargeByRechargeNo(String rechargeNo);
}
