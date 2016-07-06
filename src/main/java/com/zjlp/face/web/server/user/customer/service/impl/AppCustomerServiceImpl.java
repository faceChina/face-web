package com.zjlp.face.web.server.user.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.server.user.customer.dao.AppCustomerDao;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;
import com.zjlp.face.web.server.user.customer.service.AppCustomerService;

/**
* 
* @author Baojiang Yang
* @date 2015年8月11日 下午5:01:13
*
*/ 
@Service
public class AppCustomerServiceImpl implements AppCustomerService {

	@Autowired
	private AppCustomerDao appCustomerDao;

	@Override
	public void addAppCustomer(AppCustomer appCustomer) {
		this.appCustomerDao.add(appCustomer);

	}

	@Override
	public void editAppCustomer(AppCustomer appCustomer) {
		this.appCustomerDao.edit(appCustomer);
	}

	@Override
	public void removeAppCustomer(Long appCustomerId) throws AppCustomerException {
		this.appCustomerDao.removeById(appCustomerId);
	}

	@Override
	public List<AppCustomer> findAppCustomerList(Long userId) throws AppCustomerException {
		return this.appCustomerDao.getByUserId(userId);
	}

	@Override
	public AppCustomer findAppCustomer(Long userId, Long customerId) throws AppCustomerException {
		return this.appCustomerDao.getByUserIdAndCustomerId(userId, customerId);
	}


	@Override
	public Pagination<MyCustomerVo> getMyCustomerById(Integer shoptype,Long userId, String orderBy, String customerName, Pagination<MyCustomerVo> pagination) throws Exception {
		Integer totalRows = this.appCustomerDao.getPageCount(shoptype, userId, customerName);
		List<MyCustomerVo> datas = this.appCustomerDao.getMyCustomerById(shoptype, userId, orderBy, pagination.getStart(),
				pagination.getPageSize(), customerName);
		pagination.setTotalRow(totalRows);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public List<AppCustomer> findCurrentGroupCustomer(AppCustomer appcustomer) {
		return this.appCustomerDao.fingCurrentGroupCustomer(appcustomer);
	}

	@Override
	public AppCustomer findMyAppCustomer(Long id, Long userId, Integer type) throws AppCustomerException {
		return this.appCustomerDao.getByIdAndUserId(id, userId, type);
	}

	@Override
	public Pagination<MyCustomerVo> findStaffCount(String shopNo, String orderBy, Pagination<MyCustomerVo> pagination)
			throws Exception {
		Integer totalRows = this.appCustomerDao.getStaffTotalRows(shopNo);
		List<MyCustomerVo> datas = this.appCustomerDao.findStaffCount(shopNo, orderBy, pagination.getStart(),
				pagination.getPageSize());
		pagination.setTotalRow(totalRows);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public List<AppCustomer> findUngroupCustomer(Long userId, Integer type) {
		return this.appCustomerDao.selectUngroupCustomer(userId, type);
	}

	@Override
	public AppCustomer findAppCustomerById(Long id) {
		return this.appCustomerDao.findAppCustomerById(id);
	}

	@Override
	public Pagination<MyCustomerVo> findMySubbranchOrder(Long userId, Long customerId, Pagination<MyCustomerVo> pagination) throws Exception {
		Integer totalRows = this.appCustomerDao.countMySubbranchOrder(userId, customerId);
		List<MyCustomerVo> datas = this.appCustomerDao.selectMySubbranchOrder(userId, customerId, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRows);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public MyCustomerVo findMyShopOrder(Long userId, Long customerId) {
		return this.appCustomerDao.selectMyShopOrder(userId, customerId);
	}

	@Override
	public MyCustomerVo countAllMyShopOrder(Long userId, Long customerId) {
		return this.appCustomerDao.countAllMyShopOrder(userId, customerId);
	}

}
