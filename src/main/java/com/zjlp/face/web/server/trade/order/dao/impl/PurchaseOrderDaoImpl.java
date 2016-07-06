package com.zjlp.face.web.server.trade.order.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.component.base.BaseDaoImpl;
import com.zjlp.face.web.mapper.PurchaseOrderMapper;
import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderDao;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingListVo;

@Repository
public class PurchaseOrderDaoImpl extends BaseDaoImpl<Long, PurchaseOrder> implements PurchaseOrderDao {
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	@Override
	public Class<?> getMapperClass() {
		return PurchaseOrderMapper.class;
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<PurchaseOrder> findPurchaseOrderList(String orderNo,
			Integer cooperationWay) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("cooperationWay", cooperationWay);
		
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectList(map);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public PurchaseOrderDto getPurchaseOrder(String orderNo, String purchaserNo) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("purchaserNo", purchaserNo);
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectPurchaseOrder(map);
	}

	@Override
	public void edit(PurchaseOrder purchaseOrder) {
		sqlSession.getMapper(PurchaseOrderMapper.class).updateByPrimaryKeySelective(purchaseOrder);
	}

	@Override
	public List<Long> findPopularizeSkuIdList(String orderNo,
			List<Long> skuIdList) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("skuIdList", skuIdList);
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectPopularizeSkuIdList(map);
	}

	@Override
	public Integer getPurchaseOrderCountByOrderNOAndCooperationWay(
			String orderNo, Integer cooperationWay) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("cooperationWay", cooperationWay);
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectPurchaseOrderCountByOrderNOAndCooperationWay(map);
	}

	@Override
	public List<PurchaseOrderDto> findPromoteOrdersByOrderNo(String orderNo) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectPromoteOrdersByOrderNo(orderNo);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public PurchaseOrder getSupplierOrder(String orderNo, String supplierNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("supplierNo", supplierNo);
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSupplierOrder(map);
	}

	@Override
	public List<PurchaseOrder> selectAll() {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectAll();
	}
	
	@Override
	public Integer getSubbranchOrderTDPCount(String cell, Date today) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSubbranchOrderTDPCount(cell, today);
	}

	@Override
	public List<OperateData> selectSupplierpayCommissions(
			SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSupplierpayCommissions(salesOrderVo);
	}

	@Override
	public Long selectSupplierpayCommission(SalesOrderVo salesOrderVo) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSupplierpayCommission(salesOrderVo);
	}

	@Override
	public String getOrderSourceByPrimaryKey(Long id) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectOrderSourceByPrimaryKey(id);
	}
	
	@Override
	public List<SubbranchRankingListVo> getSubbranchRaningList(String shopNo, Long subbranchId, Integer days){
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSubbranchRaningList(shopNo, subbranchId, days);
	}
	@Override
	public Integer getSubbranchMyRaning (String shopNo, Long subbranchId, Integer days) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSubbranchMyRaning(shopNo, subbranchId, days);
	}
	@Override
	public Long getSubbranchMyCommission (String shopNo, Long subbranchId, Integer days){
		return sqlSession.getMapper(PurchaseOrderMapper.class).selectSubbranchMyCommission(shopNo, subbranchId, days);
	}

	@Override
	public Long getCommissionByTime(Long subbranchId, Date countDay, Date today) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).getCommissionByTime(subbranchId, countDay, today);
	}

	@Override
	public Long getOrderPayCommission(String orderNo, String supplierNo,
			Integer supplierType) {
		return sqlSession.getMapper(PurchaseOrderMapper.class).getOrderPayCommission(orderNo, supplierNo, supplierType);
	}
	
	
}
