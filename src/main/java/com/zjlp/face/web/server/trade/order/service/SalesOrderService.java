package com.zjlp.face.web.server.trade.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SingleStatuCountDto;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerOrder;
public interface SalesOrderService {
	
	void addSalesOrder(SalesOrder salesOrder);
	
	void editSalesOrder(SalesOrder salesOrder);
	
	SalesOrder getSalesOrderByOrderNo(String orderNo);
	
	SalesOrderDto getSalesOrderDtoByOrderNo(String orderNo);
	
	
	
	/**
	 * @Description: 卖家查询订单
	 * @param salesOrderVo
	 * @param pagination
	 * @return
	 * @date: 2015年3月18日 上午10:12:02
	 * @author: zyl
	 */
	Pagination<SalesOrderDto> findSalesOrderDetailPage(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination);
	
	/**
	 * @Description: 统计订单数量
	 * @param salesOrderVo
	 * @return
	 * @date: 2015年3月18日 上午10:12:09
	 * @author: zyl
	 */
	Integer countSalesOrder(SalesOrderVo salesOrderVo);
	
	/**
	 * @Description: 描述
	 * @param orderNo
	 * @return
	 * @date: 2015年3月18日 上午10:12:15
	 * @author: zyl
	 */
	List<OrderItem> getOrderItemListByOrderNo(String orderNo);
	
	/**
	 * @Description: 修改订单状态
	 * @param orderNo
	 * @param status
	 * @date: 2015年3月18日 上午10:11:55
	 * @author: zyl
	 */
	void editSalesOrderStatus(String orderNo, Integer status);

	/**
	 * @Description: 买家查询订单
	 * @param salesOrderVo
	 * @param pagination
	 * @return
	 * @date: 2015年3月18日 上午10:11:49
	 * @author: zyl
	 */
	Pagination<SalesOrderDto> findSalesOrderPageForWap(SalesOrderVo salesOrderVo, Pagination<SalesOrderDto> pagination);
	
	/**
	 * @Description: 交易流水号查询订单
	 * @param tsn
	 * @return
	 * @date: 2015年3月20日 上午11:46:06
	 * @author: zyl
	 */
	List<SalesOrder> findSalesOrderListByTSN(String tsn);
	
	/**
	 * @Description: 订单号查询订单
	 * @param orderNoList
	 * @return
	 * @date: 2015年3月25日 下午7:14:48
	 * @author: zyl
	 */
	List<SalesOrder> findSalesOrderListByOrderNoList(List<String> orderNoList);
	
	/**
	 * @Description: 查询订单交易成功的金额
	 * @param shopNo
	 * @param object
	 * @param endTime
	 * @return
	 * @date: 2015年3月26日 下午1:48:29
	 * @author: zyl
	 */
	Long getSalesOrderIncome(String shopNo, Date startTime, Date endTime);
	
	/**
	 * @Description: 查询订单冻结金额
	 * @param shopNo
	 * @return
	 * @date: 2015年3月26日 下午1:54:41
	 * @author: zyl
	 */
	Long getFreezeSalesOrderIncome(String shopNo);
	
	/**
	 * @Description: 验证订单
	 * @param salesOrderVo
	 * @return
	 * @date: 2015年3月27日 下午7:52:21
	 * @author: zyl
	 */
	Integer validateOrder(SalesOrderVo salesOrderVo);

	Long getUserFreezeAmount(Long userId);

	void editSellerMemo(SalesOrder edit);

	/**
	 * 通过userID和sellerId查询订单
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	List<SalesOrder> findOrderListByUserIdAndSellerIdForWap(Long userId,
			Long sellerId, String beginTime, String endTime);

	void saveBookOrderRefuseReason(String orderNo,String memo);
	
	/**
	 * 查询预约次数
	 * @param appointmentId
	 * @param receiverPhone
	 * @return
	 */
	Integer getAppointNum(Long appointmentId, String receiverPhone);

	/**
	 * 根据用户ID和客户ID查询客户的在用户所拥有的店铺下产生的订单
	 * 
	 * @param userId
	 * @param customerId
	 * @return
	 */
	List<CustomerOrder> findCustomerOrderByCurrentUserId(Long userId, Long customerId);

	/**
	 * 
	 * @Title: findOrderCountByShopNo 
	 * @Description: 获取普通订单的总数
	 * @param shopNo
	 * @return
	 * @date 2015年5月6日 下午5:36:16  
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
	 * @date 2015年5月14日 上午11:58:11  
	 * @author cbc
	 */
	Long getPopularizeOrderFreezeAmount(Long userId);


	/**
	 * App 店铺统计订单信息
	* @Title: getShopSalesOrderCountInfo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param shopNo
	* @return
	* @return Map<String,Integer>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年6月4日 下午6:44:48
	 */
	Map<String,Integer> getShopSalesOrderCountInfo(SalesOrderVo salesOrderVo);
	
	
	/*******************************
	 * @Title findOwnSalesOrderPageByShopNo
	 * @Description (获取本店分销订单)
	 * @param shopNo
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findOwnSalesOrderPageByShopNo(String shopNo,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination);
	/*******************************
	 * @Title findOwnSalesOrderPageByPhone
	 * @Description (获取本店分销订单)
	 * @param phone
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findOwnSalesOrderPageBySubbranchId(Long subbranchId,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination,Long userId,String isShopRequest);
	Pagination<SubBranchSalesOrderVo> findOwnSalesOrderPageBySubbranchId(Long subbranchId,Integer status,String orderBy,String searchKey,Pagination<SubBranchSalesOrderVo> pagination,Long userId,String isShopRequest);
	/*******************************
	 * @Title findSubBranchSalesOrderPageByShopNo
	 * @Description (获取分店分销订单)
	 * @param shopNo
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findSubBranchSalesOrderPageByShopNo(String shopNo,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination);
	/*******************************
	 * @Title findSubBranchSalesOrderPageByPhone
	 * @Description (获取分店分销订单)
	 * @param phone
	 * @param status
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findSubBranchSalesOrderPageBySubbranchId(Long subbranchId,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination);
	Pagination<SubBranchSalesOrderVo> findSubBranchSalesOrderPageBySubbranchId(Long subbranchId,Integer status,String orderBy,String searchKey,Pagination<SubBranchSalesOrderVo> pagination);
	/*******************************
	 * @Title countOwnSalesOrderCountByShopNo
	 * @Description (本店分销订单数量)
	 * @param shopNo
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer countOwnSalesOrderCountByShopNo(String shopNo,Integer status);
	/*******************************
	 * @Title countOwnSalesOrderCountByPhone
	 * @Description (本店分销订单数量)
	 * @param phone
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer countOwnSalesOrderCountBySubbranchId(Long subbranchId, Integer status, Long userId);
	/*******************************
	 * @Title countSubBranchSalesOrderCountByShopNo
	 * @Description (分店分销订单数量)
	 * @param shopNo
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer countSubBranchSalesOrderCountByShopNo(String shopNo,Integer status);
	/*******************************
	 * @Title countSubBranchSalesOrderCountByPhone
	 * @Description (本店分销订单数量)
	 * @param subbranchId
	 * @param status
	 * @return
	 * @Return Integer
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Integer countSubBranchSalesOrderCountBySubbranchId(Long subbranchId,Integer status);
	SubBranchSalesOrderVo getSubBranchSalesOrderByPrimaryKey(String orderNo,String purchaserNo);
	SubBranchSalesOrderVo getSubOrder(String orderNo,String supplierNo);
	Integer countSubBranchSalesOrderCountForProducer(String shopNo);
	Integer countSubBranchSalesOrderCountForDistributor(String shopNo);
	Long getSubBranchSalesOrderTotalPrice(String shopNo);
	Long getSubBranchSalesOrderUnFreezeTotalProfitPrice(Long userId);
	Pagination<SubBranchSalesOrderVo> findAllMySalesOrderByShopNo(SalesOrder salesOrder,String orderBy,Pagination<SubBranchSalesOrderVo> pagination,Long userId, String isShopRequest);
	
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
	Pagination<SubBranchSalesOrderVo> findHistoryOrderForOwn(Long subBranchId,Integer status,Long customerId,String orderBy,Pagination<SubBranchSalesOrderVo> pagination,String isShopRequest) throws OrderException;
	
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
	 * @param userCell 分店用户手机号
	 * @return
	 * @date 2015年7月21日 上午11:38:36  
	 * @author cbc
	 */
	Long countTodayShopCommission(Long subbranchId, String userCell);

	/**
	 * 
	 * @Title: countTodaySubbranchCommission 
	 * @Description: 今日下级分店所获得的佣金提成收入
	 * @param subbranchId
	 * @param userCell
	 * @return
	 * @date 2015年7月21日 下午1:58:03  
	 * @author cbc
	 */
	Long countTodaySubbranchCommission(Long subbranchId, String userCell);

	/**
	 * 
	 * @Title: countTodayPayOrder 
	 * @Description: 该分店和下级分店今日付款订单数
	 * @param subbranchId
	 * @return
	 * @date 2015年7月21日 下午2:22:47  
	 * @author cbc
	 */
	Integer countTodayPayOrder(Long subbranchId);

	/**
	 * 
	 * @Title: countTodayCustomer 
	 * @Description: 今日付款客户总数
	 * @param subbranchId
	 * @return
	 * @date 2015年7月21日 下午2:37:58  
	 * @author cbc
	 */
	Integer countTodayCustomer(Long subbranchId);

	/**
	 * 
	 * @Title: countTodayCommission 
	 * @Description: 今日总佣金 
	 * @param subbranchId
	 * @return
	 * @date 2015年7月21日 下午3:45:55  
	 * @author cbc
	 */
	Long countTodayCommission(Long subbranchId);

	/**
	 * 
	 * @Title: countSubbranchWeekCommission 
	 * @Description: 查询分店7日佣金总收入 
	 * @param subbranchId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @date 2015年7月21日 下午4:13:07  
	 * @author cbc
	 */
	List<OperateData> countSubbranchWeekCommission(Long subbranchId,
			Date startTime, Date endTime);


	//----------------------------------经营数据统计    start--------------------------------------------
	/**
	 * 查看该店铺在每一天的付款金额
	 * @Title: supplierRecivePrices 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesOrderVo 1.店铺号  2.付款时间
	 * @return
	 * @date 2015年7月21日 下午3:28:07  
	 * @author lys
	 */
	List<OperateData> supplierRecivePrices(SalesOrderVo salesOrderVo);

	/**
	 * 查看该店铺在每一天的支出佣金
	 * @Title: supplierpayCommission 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesOrderVo 1.店铺号  2.付款时间
	 * @return
	 * @date 2015年7月21日 下午4:15:16  
	 * @author lys
	 */
	List<OperateData> supplierpayCommissions(SalesOrderVo salesOrderVo);

	/**
	 * 供货商当天的付款金额
	 * @Title: supplierRecivePrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesOrderVo
	 * @return
	 * @date 2015年7月21日 下午4:41:16  
	 * @author lys
	 */
	Long supplierRecivePrice(SalesOrderVo salesOrderVo);

	/**
	 * 供货商当天的支出佣金
	 * @Title: supplierpayCommission 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesOrderVo
	 * @return
	 * @date 2015年7月21日 下午4:53:25  
	 * @author lys
	 */
	Long supplierpayCommission(SalesOrderVo salesOrderVo);

	/**
	 * 供货商当日付款订单数
	 * @Title: supplierPayOrderCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesOrderVo
	 * @return
	 * @date 2015年7月21日 下午4:58:02  
	 * @author lys
	 */
	Integer supplierPayOrderCount(SalesOrderVo salesOrderVo);

	/**
	 * 供货商当日客户数
	 * @Title: supplierConsumerCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param salesOrderVo
	 * @return
	 * @date 2015年7月21日 下午5:02:07  
	 * @author lys
	 */
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
	
	//----------------------------------经营数据统计    end--------------------------------------------
	/**
	 * 
	* @Title: countBuyerOrderNumber
	* @Description: (统计买家不同状态下的订单数)
	* @param userId
	* @param status
	* @return Integer    返回类型
	* @author wxn  
	* @date 2015年7月21日 下午4:00:42
	 */
	Integer countBuyerOrderNumber(Long userId,Integer status);

	/**
	 * 
	 * @Title: getSubbranchFreezeAmount 
	 * @Description: 通过手机查询分销订单的冻结佣金
	 * @param cell
	 * @return
	 * @date 2015年8月4日 上午10:19:19  
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
	 * @date 2015年8月17日 下午2:48:08  
	 * @author cbc
	 */
	Integer countBookOrder(String shopNo, Integer status);

	/**
	 * @Title: findCustomerOrderInMyShop
	 * @Description: (查询客户在我的总店下的订单)
	 * @param shopNo
	 * @param customerId
	 * @param pagination
	 * @return
	 * @return Pagination<SalesOrder> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午4:11:17
	 */
	Pagination<SalesOrder> findCustomerOrderInMyShop(String shopNo, Long customerId, Pagination<SalesOrder> pagination);
	
}
