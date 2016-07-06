package com.zjlp.face.web.server.trade.order.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.web.mapper.OrderItemMapper;
import com.zjlp.face.web.server.trade.order.dao.OrderItemDao;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addOrderItem(OrderItem orderItem){
		sqlSession.getMapper(OrderItemMapper.class).insertSelective(orderItem);
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"orderItem:id"},container={"orderItemList_orderNo:orderNo"})
	public void editOrderItem(OrderItem orderItem){
		sqlSession.getMapper(OrderItemMapper.class).updateByPrimaryKeySelective(orderItem);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"orderItem"})
	public OrderItem getOrderItemById(Long id){
		return sqlSession.getMapper(OrderItemMapper.class).selectByPrimaryKey(id);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"findOrderItemListByOrderNo"},container={"orderItemList_orderNo"})
	public List<OrderItem> findOrderItemListByOrderNo(String orderNo){
		return sqlSession.getMapper(OrderItemMapper.class).selectOrderItemListByOrderNo(orderNo);
	}

}
