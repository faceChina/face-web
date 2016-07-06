package com.zjlp.face.web.server.trade.order.service;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;

/**
 * 交易采购单
 * @ClassName: PurchaseOrderService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月8日 下午2:06:46
 */
public interface PurchaseOrderService {
	
	/**
	 * 新增采购单
	 * @Title: add 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param purchaseOrder
	 * @date 2015年5月8日 下午2:08:39  
	 * @author dzq
	 */
	void add(PurchaseOrder purchaseOrder,List<PurchaseOrderItem> purchaseOrderItemList) throws Exception;
	
	/**
	 * 订单号查询交易采购单
	 * @Title: findPurchaseOrderList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo
	 * @return
	 * @date 2015年5月8日 下午2:09:24
	 * @author dzq
	 */
	List<PurchaseOrder> findPurchaseOrderList(String orderNo) ;
	
	/**
	 * 订单号查询交易采购单
	 * @Title: findPurchaseOrderList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo 订单号
	 * @param cooperationWay 合作方式 :1分销 2推广
	 * @return
	 * @date 2015年5月8日 下午2:09:24
	 * @author dzq
	 */
	List<PurchaseOrder> findPurchaseOrderList(String orderNo,Integer cooperationWay); 
	
	/**
	 * 采购单ID查询采购明细
	 * @Title: findPurchaseOrderItemList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param purchaseOrderId 采购单ID
	 * @return
	 * @date 2015年5月8日 下午2:24:08  
	 * @author dzq
	 */
	List<PurchaseOrderItemDto> findPurchaseOrderItemList(Long purchaseOrderId) ;

	/**
	 * 查询对应店铺对某订单的采购信息
	 * @Title: getPurchaseOrder 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo 订单号
	 * @param purchaserNo 采购者编号
	 * @return
	 * @throws Exception
	 * @date 2015年5月13日 上午11:05:56  
	 * @author lys
	 */
	PurchaseOrderDto getPurchaseOrder(String orderNo, String purchaserNo) throws Exception ;

	/**
	 * 修改采购单的价格
	 * @Title: editPurchaseOrder 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param purchaseOrder 采购单
	 * @throws Exception
	 * @date 2015年5月13日 上午11:27:58  
	 * @author lys
	 */
	void editPurchaseOrder(PurchaseOrder purchaseOrder) throws Exception ;

	/**
	 * 查询推广skuId列表
	 * @Title: findPopularizeSkuIdList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo 订单号
	 * @param skuIdList sku主键列表
	 * @return
	 * @throws Exception
	 * @date 2015年5月13日 下午5:43:01  
	 * @author lys
	 */
	List<Long> findPopularizeSkuIdList(String orderNo, List<Long> skuIdList) throws Exception ;
	/**
	 * 根据订单号和合作方式查询采购单数量
	 * @Title: getPurchaseOrderCountByOrderNOAndCooperationWay 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo
	 * @param cooperationWay
	 * @return
	 * @date 2015年5月15日 下午5:50:13  
	 * @author dzq
	 */
	Integer getPurchaseOrderCountByOrderNOAndCooperationWay(String orderNo,
			Integer cooperationWay);
	
	void sendPopularizeSms(String orderNo);
	
	/**
	 * 查询对应订单的推广信息
	 * @Title: getPromoteOrdersByOrderNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo 订单编号
	 * @return
	 * @throws Exception
	 * @date 2015年5月22日 下午2:35:48  
	 * @author lys
	 */
	List<PurchaseOrderDto> findPromoteOrdersByOrderNo(String orderNo) throws Exception;
	PurchaseOrder getSupplierOrder(String orderNo, String supplierNo);

	/**
	 * 分店店铺订单个数
	 * @Title: getSubbranchOrderCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cell
	 * @return
	 * @date 2015年7月9日 上午10:52:50  
	 * @author lys
	 */
	Integer getSubbranchOrderTDPCount(String cell);
	/**
	 * 查找订单来源
	 * @Title: getOrderSourceByPrimaryKey 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @author huangxilei
	 */
	String getOrderSourceByPrimaryKey(Long id);

	/**
	 * 
	 * @Title: getCommissionByTime 
	 * @Description: 获取分店的佣金，条件为时间
	 * @param subbranchId
	 * @param time 1为昨日,2为7日， 3为30日
	 * @return
	 * @date 2015年8月13日 上午11:05:21  
	 * @author cbc
	 */
	Long getCommissionByTime(Long subbranchId, Integer time);

	/**
	 * 
	 * @Title: getOrderPayCommission 
	 * @Description: 获取单笔订单的支付佣金 
	 * @param orderNo
	 * @param supplierNo 供货商(如果以总店角色查询佣金支出时，传总店店铺号，以分店角色查询佣金支出时，传分店手机号)
	 * @param supplierType 供货商类型（1.供货商，2.分销商）
	 * @return
	 * @date 2015年8月18日 上午10:41:35  
	 * @author cbc
	 */
	Long getOrderPayCommission(String orderNo, String supplierNo,
			Integer supplierType);

}
