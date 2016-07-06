package com.zjlp.face.web.server.trade.order.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.component.base.BaseDaoImpl;
import com.zjlp.face.web.mapper.PurchaseOrderItemMapper;
import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderItemDao;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;
@Repository
public class PurchaseOrderItemDaoImpl extends BaseDaoImpl<Long, PurchaseOrderItem> implements PurchaseOrderItemDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	@Override
	public Class<?> getMapperClass() {
		return PurchaseOrderItemMapper.class;
	}

	@Override
	public List<PurchaseOrderItemDto> findPurchaseOrderItemList(
			Long purchaseOrderId) {
		
		return sqlSession.getMapper(PurchaseOrderItemMapper.class).selectListByPurchaseOrderId(purchaseOrderId);
	}

	@Override
	public List<PurchaseOrderItem> selectAll() {
		return sqlSession.getMapper(PurchaseOrderItemMapper.class).selectAll();
	}


}
