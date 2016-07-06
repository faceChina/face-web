package com.zjlp.face.web.server.trade.order.bussiness;


import java.util.Map;

import javax.management.openmbean.OpenDataException;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

/**
 * 经营数据业务接口
 * @ClassName: OperateDataBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月21日 上午11:37:14
 */

public interface OperateDataBusiness {
	
	/**
	 * 今日佣金总收入
	 * 
	 * 1.角色：分店<br>
	 * 
	 * 2.包括该分店的佣金收入和下级分店的订单产生的佣金提成收入<br>
	 * 
	 * 3.状态：已付款的订单<br>
	 * 
	 * @Title: countTodayCommission 
	 * @Description: 今日佣金总收入
	 * @param subbranchId 该分店的subbranchId
	 * @return
	 * @throws OrderException 参数为空的时候抛出异常
	 * @date 2015年7月21日 下午3:43:34  
	 * @author cbc
	 */
	Long countTodayCommission(Long subbranchId) throws OperateDataException;
	
	/**
	 * 今日付款金额<br>
	 * 
	 * 1.角色：供货商<br>
	 * 
	 * 2.包括自营订单，以及分店代理订单<br>
	 * 
	 * 3.状态：已付款的订单(包括发货，收货等)<br>
	 * 
	 * @Title: supplierRecivePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 
	 *             供货商用户id
	 * @return
	 * @throws OperateDataException <br>
	 *                            1.参数为空时，抛出异常<br>
	 *                            2.用户不存在<br>
	 *                            3.用户没有店铺<br>
	 * @date 2015年7月21日 上午11:42:38  
	 * @author lys
	 */
	Long supplierRecivePrice(Long userId) throws OperateDataException;
	
	/**
	 * 佣金支出<br>
	 * 
	 * 1.角色：供货商<br>
	 * 
	 * 2.供货商的分销订单所应支付的佣金<br>
	 * 
	 * 3.状态：已付款的订单(包括发货，收货等)<br>
	 * 
	 * @Title: supplierPayCommission 
	 * 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * 
	 * @param userId
	 *            供货商用户id
	 * @return
	 * @throws OperateDataException <br>
	 *                            1.参数为空时，抛出异常<br>
	 *                            2.用户不存在<br>
	 *                            3.用户没有店铺<br>
	 * @date 2015年7月21日 上午11:48:06  
	 * @author lys
	 */
	Long supplierPayCommission(Long userId) throws OperateDataException;
	
	/**
	 * 供货商今日付款订单数<br>
	 * 
	 * 1.角色：供货商<br>
	 * 
	 * 2.包括自营订单，以及分店代理订单<br>
	 * 
	 * 3.状态：已付款的订单(包括发货，收货等)<br>
	 * 
	 * @Title: supplierPayOrderCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 *            供货商用户id
	 * @return
	 * @throws OperateDataException <br>
	 *                            1.参数为空时，抛出异常<br>
	 *                            2.用户不存在<br>
	 *                            3.用户没有店铺<br>
	 * @date 2015年7月21日 上午11:53:33  
	 * @author lys
	 */
	Integer supplierPayOrderCount(Long userId) throws OperateDataException;
	
	/**
	 * 客户数<br>
	 * 
	 * 1.角色：供货商<br>
	 * 
	 * 2.包括自营订单，以及分店代理订单<br>
	 * 
	 * 3.状态：已付款的订单(包括发货，收货等)<br>
	 * 
	 * @Title: supplierConsumerCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 *            供货商用户id
	 * @return
	 * @throws OperateDataException <br>
	 *                            1.参数为空时，抛出异常<br>
	 *                            2.用户不存在<br>
	 *                            3.用户没有店铺<br>
	 * @date 2015年7月21日 上午11:56:22  
	 * @author lys
	 */
	Integer supplierConsumerCount(Long userId) throws OperateDataException;
	
	/**
	 * 一周付款金额<br>
	 * 
	 * 1.角色：供货商<br>
	 * 
	 * 2.包括自营订单，以及分店代理订单<br>
	 * 
	 * 3.状态：已付款的订单(包括发货，收货等)<br>
	 * 
	 * @Title: supplierRecivePrices 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 *            供货商用户id
	 * @return
	 * @throws OperateDataException <br>
	 *                            1.参数为空时，抛出异常<br>
	 *                            2.用户不存在<br>
	 *                            3.用户没有店铺<br>
	 * @date 2015年7月21日 下午1:58:22  
	 * @author lys
	 */
	Map<String, Long> supplierRecivePrices(Long userId) throws OperateDataException;
	
	/**
	 * 一周佣金支出<br>
	 * 
	 * 1.角色：供货商<br>
	 * 
	 * 2.供货商的分销订单所应支付的佣金<br>
	 * 
	 * 3.状态：已付款的订单(包括发货，收货等)<br>
	 * 
	 * @Title: supplierPayCommissions 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 *            供货商用户id
	 * @return
	 * @throws OperateDataException <br>
	 *                            1.参数为空时，抛出异常<br>
	 *                            2.用户不存在<br>
	 *                            3.用户没有店铺<br>
	 * @date 2015年7月21日 下午1:58:25  
	 * @author lys
	 */
	Map<String, Long> supplierPayCommissions(Long userId) throws OperateDataException;
	
	/**
	 * 
	* @Title: countMyShopOrderNumber
	* @Description: (查询不同状态下店铺订单数)
	* @param shopNo
	* @param status
	* @return
	* @return Integer    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月21日 下午3:24:00
	 */
	Integer countMyShopOrderNumber(String shopNo,Integer status) throws OperateDataException;
	/**
	 * 
	* @Title: countOwnSaleSubOrderNumber
	* @Description: (查询不同状态下分店自售订单数)
	* @param subbranchId
	* @param status
	* @return
	* @return Integer    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月21日 下午3:24:35
	 */
	Integer countOwnSaleSubOrderNumber(Long subbranchId, Integer status, Long userId) throws OperateDataException;
	
	/**
	 * 
	* @Title: countSubSaleSubOrderNumber
	* @Description: TODO(查询不同状态下下级分店订单数)
	* @param subbranchId
	* @param status
	* @return
	* @return Integer    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月21日 下午3:25:31
	 */
	Integer countSubSaleSubOrderNumber(Long subbranchId,Integer status) throws OperateDataException;
	
	/**
	 * 
	* @Title: countBuyerOrderNumber
	* @Description: (买家查询不同状态下的订单数)
	* @param userId
	* @param status
	* @return
	* @throws OperateDataException
	* @return Integer    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月21日 下午3:54:59
	 */
	Integer countBuyerOrderNumber(Long userId,Integer status)throws OperateDataException;

	/**
	 * 今日店铺佣金收入 
	 * 1.角色：分店<br>
	 * 
	 * 2.状态：已付款的订单<br>
	 * 
	 * 3.该分店的订单所产生的佣金收入<br>
	 * 
	 * @Title: countTodayShopCommission 
	 * @Description: 今日店铺佣金收入 
	 * @param subbranchId 该分店的subbranchId
	 * @param userCell 用户的手机号(不是申请分店时候所绑定的手机号，而是用户的账户手机)
	 * @return
	 * @throws OrderException 参数为空的时候会报错
	 * @date 2015年7月21日 下午3:14:04  
	 * @author cbc
	 */
	Long countTodayShopCommission(Long subbranchId, String userCell) throws OperateDataException;
	
	/**
	 * 今日分店提成佣金 
	 * 1.角色：分店<br>
	 * 
	 * 2.状态：已付款的订单<br>
	 * 
	 * 3.该分店的下级分店所产生的订单而带来的佣金提成收入<br>
	 * 
	 * 
	 * @Title: countTodaySubbranchCommission 
	 * @Description: 今日分店提成佣金 
	 * @param subbranchId 该分店的subbranchId
	 * @param userCell 用户的手机号(不是申请分店时候所绑定的手机号，而是用户的账户手机)
	 * @return
	 * @throws OrderException 参数为空的时候报错
	 * @date 2015年7月21日 下午3:16:11  
	 * @author cbc
	 */
	Long countTodaySubbranchCommission(Long subbranchId, String userCell) throws OperateDataException;
	
	/**
	 * 该分店今日付款订单数 (包括该分店和他的下级分店所产生的订单)
	 * 
	 * 1.角色：分店<br>
	 * 
	 * 2.状态：已付款的订单<br>
	 * 
	 * 
	 * @Title: countTodayPayOrder 
	 * @Description: 今日付款订单数
	 * @param subbranchId 该分店的subbranchId
	 * @return
	 * @throws OrderException
	 * @date 2015年7月21日 下午3:17:01  
	 * @author cbc
	 */
	Integer countTodayPayOrder(Long subbranchId) throws OperateDataException;
	
	/**
	 * 该分店的今日付款客户数（包括下级分店）
	 * 
	 * 1.角色：分店<br>
	 * 
	 * 2.状态：分店和下级分店已付款的订单
	 * 
	 * 
	 * @Title: countTodayCustomer 
	 * @Description: 今日付款客户数
	 * @param subbranchId 该分店的subbranchId
	 * @return
	 * @throws OrderException
	 * @date 2015年7月21日 下午3:24:36  
	 * @author cbc
	 */
	Integer countTodayCustomer(Long subbranchId) throws OperateDataException;
	
	/**
	 * 统计该分店的7日佣金收入（不包含今日佣金收入， 为佣金总收入，既该分店产生的订单所带来的佣金收入和下级分店产生的订单带来的佣金提成收入）
	 * 1.角色：分店<br>
	 * 
	 * 2.状态：已付款订单所产生的佣金收入<br>
	 * 
	 * 
	 * @Title: countSubbranchWeekCommission 
	 * @Description: 分店7日佣金收入统计
	 * @param subbranchId
	 * @return Map<String, Long>  key为时间(yyyy-MM-dd), value为佣金收入，单位为分
	 * @throws OpenDataException
	 * @date 2015年7月21日 下午3:57:31  
	 * @author cbc
	 */
	Map<String, Long> countSubbranchWeekCommission(Long subbranchId) throws OperateDataException;
	

	/**
	 * 查询总店销售排行榜<br>
	 * @Title: getSupplierSalesRaning 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param orderBy 1为成交金额排序， 2为订单数排序
	 * @param time 1为昨日，2为7日，3为30日
	 * @return
	 * @throws OperateDataException
	 * @date 2015年8月11日 下午2:49:42  
	 * @author cbc
	 */
	Pagination<SupplierSalesRankingVo> getSupplierSalesRaning(Long userId, Integer orderBy, Integer time, Pagination<SupplierSalesRankingVo> pagination) throws OperateDataException;
	
	/**
	 * 
	 * @Title: getSupplierSalesRankingDetail 
	 * @Description: 总店销售排行榜详情
	 * @param subbranchId
	 * @param time 1为昨日，2为7日，3为30日
	 * @return
	 * @throws OperateDataException
	 * @date 2015年8月12日 上午9:31:07  
	 * @author cbc
	 */
	SupplierSalesRankingVo getSupplierSalesRankingDetail(Long subbranchId, Integer time) throws OperateDataException;
	
	/**
	 * 分店佣金排行榜<br>
	 * @Title: getSubbranchRaning 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param supplierNo
	 * @param subbranchId
	 * @param days
	 * @return 
	 * @throws OperateDataException
	 * @date  2015年8月11日 下午4:49:42  
	 * @author talo
	 */
	SubbranchRankingVo getSubbranchRaning(Long subbranchId, Integer days) throws OperateDataException;
}
