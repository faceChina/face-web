package com.zjlp.face.web.server.user.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppCustomerMapper;
import com.zjlp.face.web.server.user.customer.dao.AppCustomerDao;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;

/**
* 
* @author Baojiang Yang
* @date 2015年8月11日 下午5:00:34
*
*/ 
@Repository("appCustomerDao")
public class AppCustomerDaoImpl implements AppCustomerDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public Long add(AppCustomer appCustomer) {
		this.sqlSession.getMapper(AppCustomerMapper.class).insert(appCustomer);
		return appCustomer.getId();
	}

	@Override
	public void removeById(Long id) {
		this.sqlSession.getMapper(AppCustomerMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void edit(AppCustomer appCustomer) {
		this.sqlSession.getMapper(AppCustomerMapper.class).updateByPrimaryKey(appCustomer);
	}

	@Override
	public List<AppCustomer> getByUserId(Long id) {
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectByUserId(id);
	}

	@Override
	public AppCustomer getByUserIdAndCustomerId(Long userId, Long customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("customerId", customerId);
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectByUserIdAndCutsomerId(map);
	}

	@Override
	public Integer getPageCount(Integer shoptype, Long userId, String customerName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("shopType", shoptype);
		map.put("customerName", customerName);
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectPageCount(map);
	}

	@Override
	public List<MyCustomerVo> getMyCustomerById(Integer shoptype, Long userId, String orderBy, int start, int pageSize, String customerName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("shopType", shoptype);
		map.put("orderBy", orderBy);
		map.put("start", start);
		map.put("pageSize", pageSize);
		map.put("customerName", customerName);
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectMyCustomerById(map);
	}

	@Override
	public List<AppCustomer> fingCurrentGroupCustomer(AppCustomer appcustomer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", appcustomer.getId());
		map.put("userId", appcustomer.getUserId());
		map.put("type", appcustomer.getType());
		return sqlSession.getMapper(AppCustomerMapper.class).selectCurrentGroupCustomers(map);
	}

	@Override
	public AppCustomer getByIdAndUserId(Long id, Long userId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userId", userId);
		map.put("type", type);
		return sqlSession.getMapper(AppCustomerMapper.class).selectByPrimarykeyAndUserId(map);
	}

	@Override
	public List<MyCustomerVo> findStaffCount(String shopNo, String orderBy, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("start", start);
		map.put("pageSize", pageSize);
		map.put("orderBy", orderBy);
		return sqlSession.getMapper(AppCustomerMapper.class).selectStaffCount(map);
	}

	@Override
	public Integer getStaffTotalRows(String shopNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		return this.sqlSession.getMapper(AppCustomerMapper.class).getStaffTotalRows(map);
	}

	@Override
	public List<AppCustomer> selectUngroupCustomer(Long userId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectUngroupCustomer(map);
	}

	@Override
	public AppCustomer findAppCustomerById(Long id) {
		return this.sqlSession.getMapper(AppCustomerMapper.class).findAppCustomerById(id);
	}

	@Override
	public Integer countMySubbranchOrder(Long userId, Long customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("customerId", customerId);
		return this.sqlSession.getMapper(AppCustomerMapper.class).countMySubbranchOrder(map);
	}

	@Override
	public List<MyCustomerVo> selectMySubbranchOrder(Long userId, Long customerId, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("customerId", customerId);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectMySubbranchOrder(map);
	}

	@Override
	public MyCustomerVo selectMyShopOrder(Long userId, Long customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("customerId", customerId);
		return this.sqlSession.getMapper(AppCustomerMapper.class).selectMyShopOrder(map);
	}

	@Override
	public MyCustomerVo countAllMyShopOrder(Long userId, Long customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("customerId", customerId);
		return this.sqlSession.getMapper(AppCustomerMapper.class).countAllMyShopOrder(map);
	}

}
