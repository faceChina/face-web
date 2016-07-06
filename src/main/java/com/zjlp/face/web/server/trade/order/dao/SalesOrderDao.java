package com.zjlp.face.web.server.trade.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.operation.appoint.domain.OrderAppointmentDetail;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SingleStatuCountDto;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerOrder;

public interface SalesOrderDao {
	
	void addSalesOrder(SalesOrder salesOrder);
	
	void editSalesOrder(SalesOrder salesOrder);
	
	SalesOrder getSalesOrderByOrderNo(String orderNo);
	
	/**
	 * @Description: 卖家查询订单
	 * @param salesOrderVo
	 * @param pagination
	 * @return
	 * @date: 2015年3月18日 上午10:10:58
	 * @author: zyl
	 */
	Pagination<SalesOrderDto> findSalesOrderPage(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination);

	/**
	 * 根据订单序列号查找相关的预约详情信息
	 * @param orderNo 订单序列号
	 * @return
	 * @author lhh
	 */
	List<OrderAppointmentDetail> queryAppointmentDetail(String orderNo);
	
	/**
	 * @Description: 统计订单数量
	 * @param salesOrderVo
	 * @return
	 * @date: 2015年3月18日 上午10:11:24
	 * @author: zyl
	 */
	Integer countSalesOrder(SalesOrderVo salesOrderVo);
	
	SalesOrderDto getSalesOrderDtoByOrderNo(String orderNo);
	
	/**
	 * @Description: 买家查询订单
	 * @param salesOrderVo
	 * @param pagination
	 * @return
	 * @date: 2015年3月18日 上午10:10:39
	 * @author: zyl
	 */
	Pagination<SalesOrderDto> findSalesOrderPageForWap(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination);
	
	List<SalesOrder> findSalesOrderListByTSN(String tsn);
	
	/**
	 * @Description: 订单号查询订单
	 * @param orderNoList
	 * @return
	 * @date: 2015年3月25日 下午7:14:48
	 * @author: zyl
	 */
	List<SalesOrder> findSalesOrderListByOrderNoList(List<String> orderNoList);
	
	Long getSalesOrderIncome(String shopNo, Date startTime, Date endTime);
	
	Long getFreezeSalesOrderIncome(String shopNo);
	
	Integer validateOrder(SalesOrderVo salesOrderVo);

	Long getUserFreezeAmount(Long userId);

	void editSellerMemo(SalesOrder edit);

	
	void saveBookOrderRefuseReason(String orderNo,String memo);

	Integer getAppointNum(Long appointmentId, String receiverPhone);

	List<SalesOrder> findOrderListByUserIdAndSellerIdForWap(Long userId,
			Long sellerId, String beginTime, String endTime);

	/**
	 * 根据用户ID和客户ID查询客户的在用户所拥有的店铺下产生的订单
	 * 
	 * @param userId
	 * @param customerId
	 * @return
	 */
	List<CustomerOrder> getCustomerOrderByCurrentUserId(Long userId, Long customerId);

	/**
	 * 
	 * @Title: findOrderCountByShopNo 
	 * @Description: 查询普通订单的数量
	 * @param shopNo
	 * @return
	 * @date 2015年5月6日 下午5:37:53  
	 * @author cbc
	 */
	Integer findOrderCountByShopNo(String shopNo);







	List<SingleStatuCountDto> findOrderStatuCountDtoList(
			SalesOrderVo salesOrderVo);



	/**
	 * 
	 * @Title: getPopularizeOrderFreezeAmount 
	 * @Description: 获取用户的推广订单的冻结金额
	 * @param userId
	 * @return
	 * @date 2015年5月14日 下午12:00:38  
	 * @author cbc
	 */
	Long getPopularizeOrderFreezeAmount(Long userId);


	/**
	 * 
	* @Title: getShopSalesOrderCountInfo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param shopNo
	* @return Map<String,Integer>    返回类型
	* @author wxn  
	* @date 2015年6月4日 下午4:51:00
	 */
	Map<String,Object> getShopSalesOrderCountInfo(SalesOrderVo salesOrderVo);
	
	
	
	/*******************************
	 * @Title selectOwnSalesOrderPageByShopNo
	 * @Description (本店铺订单查询)
	 * @param shopNo
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return List<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	List<SubBranchSalesOrderVo> selectOwnSalesOrderPageByShopNo(String shopNo,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination);
	/*******************************
	 * @Title selectOwnSalesOrderPageByPhone
	 * @Description (本店铺订单查询)
	 * @param phone
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return List<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	List<SubBranchSalesOrderVo> selectOwnSalesOrderPageBySubbranchId(Long subbranchId,Integer status,String orderBy,String searchKey,Pagination<SubBranchSalesOrderVo> pagination,Long userId,String isShopRequest);
	/*******************************
	 * @Title selectSubBranchSalesOrderPageByShopNo
	 * @Description (分店订单查询)
	 * @param shopNo
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return List<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	List<SubBranchSalesOrderVo> selectSubBranchSalesOrderPageByShopNo(String shopNo,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination);
	/*******************************
	 * @Title selectSubBranchSalesOrderPageByPhone
	 * @Description (分店订单查询)
	 * @param subbranchId
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return List<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	List<SubBranchSalesOrderVo> selectSubBranchSalesOrderPageBySubbranchId(Long subbranchId,Integer status,String orderBy,String searchKey,Pagination<SubBranchSalesOrderVo> pagination);
	
	/*******************************
	 * @Title selectOwnSalesOrderCountByShopNo
	 * @Description (本店铺总订单数查询)
	 * @param shopNo
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer selectOwnSalesOrderCountByShopNo(String shopNo,Integer status);
	/*******************************
	 * @Title selectOwnSalesOrderCountByPhone
	 * @Description (本店铺总订单数查询)
	 * @param phone
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer selectOwnSalesOrderCountBySubbranchId(Long subbranchId, Integer status, String searchKey, Long userId);
	
	/*******************************
	 * @Title selectSubBranchSalesOrderCountByShopNo
	 * @Description (分店订单总数)
	 * @param shopNo
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer selectSubBranchSalesOrderCountByShopNo(String shopNo,Integer status);
	/*******************************
	 * @Title selectSubBranchSalesOrderCountByPhone
	 * @Description (分店订单总数)
	 * @param subbranchId
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer selectSubBranchSalesOrderCountBySubbranchId(Long subbranchId,Integer status,String searchKey);
	
	SubBranchSalesOrderVo selectSubBranchSalesOrderByPrimaryKey(String orderNo,String purchaserNo);
	SubBranchSalesOrderVo selectSubOrder(String orderNo,String supplierNo);
	Integer selectSubBranchSalesOrderCountForProducer(String shopNo,Date time);
	Integer selectSubBranchSalesOrderCountForDistributor(String shopNo,Date time);
	Long selectSubBranchSalesOrderTotalPrice(String shopNo,Date time);
	Long selectSubBranchSalesOrderUnFreezeTotalProfitPrice(Long userId,Date time);
	Pagination<SubBranchSalesOrderVo> selectAllMySalesOrderByShopNo(SalesOrder salesOrder,String orderBy,Pagination<SubBranchSalesOrderVo> pagination,Long userId, String isShopRequest);

	/**
	 * 查询所有（测试支持用）
	 * @Title: selectAll 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年7月9日 上午10:59:13  
	 * @author lys
	 */
	List<SalesOrder> selectAll();
	
	/**
	 * 
	* @Title: findHistoryOrderForOwn
	* @Description: (查询历史分店的店铺订单)
	* @param subBranchId
	* @param status
	* @param orderBy
	* @param pagination
	* @return
	* @throws OrderException
	* @return Pagination<SubBranchSalesOrderVo>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月14日 上午11:07:39
	 */
	Pagination<SubBranchSalesOrderVo> findHistoryOrderForOwn(Long subBranchId,Integer status,Long customerId, String orderBy,Pagination<SubBranchSalesOrderVo> pagination,String isShopRequest) throws OrderException;
	
	/**
	 * 
	* @Title: findHistoryOrderForSub
	* @Description: (查询历史分店的分店订单)
	* @param subBranchId
	* @param status
	* @param orderBy
	* @param pagination
	* @return
	* @throws OrderException
	* @return Pagination<SubBranchSalesOrderVo>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月14日 上午11:08:20
	 */
	Pagination<SubBranchSalesOrderVo> findHistoryOrderForSub(Long subBranchId,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination) throws OrderException;

	/**
	 * 
	 * @Title: countTodayShopCommission 
	 * @Description: 今日店铺(分店)佣金收入 
	 * @param subbranchId
	 * @param userCell
	 * @return
	 * @date 2015年7月21日 上午11:40:29  
	 * @author cbc
	 */
	Long countTodayShopCommission(Long subbranchId, String usreCell);

	/**
	 * 
	 * @Title: countTodaySubbranchCommission 
	 * @Description: 今日下级分店佣金提成收入 
	 * @param subbranchId
	 * @param userCell
	 * @return
	 * @date 2015年7月21日 下午1:59:44  
	 * @author cbc
	 */
	Long countTodaySubbranchCommission(Long subbranchId, String userCell);

	/**
	 * 
	 * @Title: countTodayPayOrder 
	 * @Description: 分店今日付款订单数
	 * @param subbranchId
	 * @return
	 * @date 2015年7月21日 下午2:25:05  
	 * @author cbc
	 */
	Integer countTodayPayOrder(Long subbranchId);

	/**
	 * 
	 * @Title: countTodayCustomer 
	 * @Description: 今日顾客数
	 * @param subbranchId
	 * @return
	 * @date 2015年7月21日 下午2:57:14  
	 * @author cbc
	 */
	Integer countTodayCustomer(Long subbranchId);

	/**
	 * 
	 * @Title: countTodayCommission 
	 * @Description: 查询今日总佣金 
	 * @param subbranchId
	 * @return
	 * @date 2015年7月21日 下午3:51:21  
	 * @author cbc
	 */
	Long countTodayCommission(Long subbranchId);

	/**
	 * 
	 * @Title: countSubbranchWeekCommission 
	 * @Description: 获取分店7日佣金收入总和 
	 * @param subbranchId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @date 2015年7月21日 下午4:14:05  
	 * @author cbc
	 */
	List<OperateData> countSubbranchWeekCommission(Long subbranchId,
			Date startTime, Date endTime);
	
	List<OperateData> selectSupplierRecivePrices(SalesOrderVo salesOrderVo);

	Long selectSupplierRecivePrice(SalesOrderVo salesOrderVo);

	Integer supplierPayOrderCount(SalesOrderVo salesOrderVo);

	Integer supplierConsumerCount(SalesOrderVo salesOrderVo);
	/**
	 * 
	* @Title: countOwnSalesOrderByShopNo
	* @Description: (统计自己店铺订单数)
	* @param shopNo
	* @param status
	* @return
	* @return Integer    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月21日 下午3:37:54
	 */
	Integer countOwnSalesOrderByShopNo(String shopNo,Integer status);
	
	Integer countBuyerOrderNumber(Long userId,Integer status);

	/**
	 * 
	 * @Title: getSubbranchFreezeAmount 
	 * @Description: 通过手机查询分销订单的冻结佣金
	 * @param cell
	 * @return
	 * @date 2015年8月4日 上午10:20:22  
	 * @author cbc
	 */
	Long getSubbranchFreezeAmount(String cell);

	/**
	 * 
	 * @Title: countBookOrder 
	 * @Description: 预约订单数量 
	 * @param shopNo
	 * @param status
	 * @return
	 * @date 2015年8月17日 下午2:49:18  
	 * @author cbc
	 */
	Integer countBookOrder(String shopNo, Integer status);

	/**
	 * @Title: countCustomerOrderInMyShop
	 * @Description: (查询客户在我的总店下的订单-统计)
	 * @param shopNo
	 * @param customerId
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午4:15:37
	 */
	Integer countCustomerOrderInMyShop(String shopNo, Long customerId);

	/**
	 * @Title: findCustomerOrderInMyShop
	 * @Description: (查询客户在我的总店下的订单-分页)
	 * @param shopNo
	 * @param customerId
	 * @param pagination
	 * @return
	 * @return List<SalesOrder> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午4:16:16
	 */
	List<SalesOrder> findCustomerOrderInMyShop(String shopNo, Long customerId, Pagination<SalesOrder> pagination);

	Long getAencyFreezeAmount(String shopNo);
	
}
