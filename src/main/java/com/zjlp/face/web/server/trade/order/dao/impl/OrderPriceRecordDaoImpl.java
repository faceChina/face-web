package com.zjlp.face.web.server.trade.order.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.OrderPriceRecordMapper;
import com.zjlp.face.web.server.trade.order.dao.OrderPriceRecordDao;
import com.zjlp.face.web.server.trade.order.domain.OrderPriceRecord;

@Repository
public class OrderPriceRecordDaoImpl implements OrderPriceRecordDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addOrderPriceRecord(OrderPriceRecord orderPriceRecord){
		sqlSession.getMapper(OrderPriceRecordMapper.class).insertSelective(orderPriceRecord);
	}
}
