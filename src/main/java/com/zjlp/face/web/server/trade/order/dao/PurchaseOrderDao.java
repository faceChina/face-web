package com.zjlp.face.web.server.trade.order.dao;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.component.base.BaseDao;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingListVo;

public interface PurchaseOrderDao extends BaseDao<Long,PurchaseOrder> {

	List<PurchaseOrder> findPurchaseOrderList(String orderNo, Integer cooperationWay);

	PurchaseOrderDto getPurchaseOrder(String orderNo, String purchaserNo);

	void edit(PurchaseOrder purchaseOrder);

	List<Long> findPopularizeSkuIdList(String orderNo, List<Long> skuIdList);
	
	Integer getPurchaseOrderCountByOrderNOAndCooperationWay(String orderNo,
			Integer cooperationWay);

	List<PurchaseOrderDto> findPromoteOrdersByOrderNo(String orderNo);
	
	PurchaseOrder getSupplierOrder(String orderNo, String supplierNo);

	List<PurchaseOrder> selectAll();

	/**
	 * 查询分店订单个数（自己卖的 + 下级分店卖的）
	 * @Title: getSubbranchOrderTDPCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cell 手机号码
	 * @param today 
	 * @return
	 * @date 2015年7月9日 上午10:59:08  
	 * @author lys
	 */
	Integer getSubbranchOrderTDPCount(String cell, Date today);

	List<OperateData> selectSupplierpayCommissions(SalesOrderVo salesOrderVo);

	Long selectSupplierpayCommission(SalesOrderVo salesOrderVo);
	
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
	 * 分店佣金排行榜
	 * @param supplierNo
	 * @param subbranchId
	 * @param days
	 * @return
	 * @author talo
	 */
	List<SubbranchRankingListVo> getSubbranchRaningList(String supplierNo, Long subbranchId, Integer days);
	
	/**
	 * 分店佣金排行榜-我的当前排名
	 * @param supplierNo
	 * @param subbranchId
	 * @param days
	 * @return
	 * @author talo
	 */
	Integer getSubbranchMyRaning (String supplierNo, Long subbranchId, Integer days);
	
	/**
	 * 分店佣金排行榜-我的当前佣金
	 * @param supplierNo
	 * @param subbranchId
	 * @param days
	 * @return
	 * @author talo
	 */
	Long getSubbranchMyCommission (String supplierNo, Long subbranchId, Integer days);

	/**
	 * 
	 * @Title: getCommissionByTime 
	 * @Description: 根据分店查询佣金 
	 * @param subbranchId
	 * @param countDay
	 * @return
	 * @date 2015年8月13日 上午11:07:21  
	 * @author cbc
	 */
	Long getCommissionByTime(Long subbranchId, Date countDay, Date today);

	/**
	 * 
	 * @Title: getOrderPayCommission 
	 * @Description: 获取单笔订单的支付佣金
	 * @param orderNo
	 * @param supplierNo
	 * @param supplierType
	 * @return
	 * @date 2015年8月18日 上午10:42:54  
	 * @author cbc
	 */
	Long getOrderPayCommission(String orderNo, String supplierNo,
			Integer supplierType);

}
