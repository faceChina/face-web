package com.zjlp.face.web.server.trade.order.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.OrderOperateRecordMapper;
import com.zjlp.face.web.server.trade.order.dao.OrderOperateRecordDao;
import com.zjlp.face.web.server.trade.order.domain.OrderOperateRecord;

@Repository
public class OrderOperateRecordDaoImpl implements OrderOperateRecordDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addOrderOperateRecord(OrderOperateRecord orderOperateRecord){
		sqlSession.getMapper(OrderOperateRecordMapper.class).insertSelective(orderOperateRecord);
	}
}
