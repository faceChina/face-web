package com.zjlp.face.web.server.user.customer.producer.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.producer.CustomerProducer;
import com.zjlp.face.web.server.user.customer.service.AppCustomerService;
import com.zjlp.face.web.server.user.user.domain.User;

@Repository("customerProducer")
public class CustomerProducerImpl implements CustomerProducer {

	private Logger _logger = Logger.getLogger(getClass());

	@Autowired
	private AppCustomerService appCustomerService;

	/* (non-Javadoc)
	 * @see com.zjlp.face.web.server.user.customer.producer.CustomerProducer#addCustomer(com.zjlp.face.web.server.user.user.domain.User, com.zjlp.face.web.server.user.user.domain.User)
	 */
	@Override
	public void addCustomer(User buyer, User seller) throws AppCustomerException {
		try {
			AssertUtil.notNull(buyer, "buyer为空，无法添加");
			AssertUtil.notNull(seller, "seller为空，无法添加");
			AppCustomer existCustomer = this.appCustomerService.findAppCustomer(seller.getId(), buyer.getId());
			if (existCustomer == null || existCustomer.getId() == null) {
				AppCustomer criteria = new AppCustomer();
				criteria.setType(AppCustomer.TYPE_CUSTOMER);
				criteria.setUserId(seller.getId());
				criteria.setCustomerId(buyer.getId());
				criteria.setCustomerName(StringUtils.isNotBlank(buyer.getContacts()) ? buyer.getContacts() : buyer.getNickname());// 存入真实姓名，如果为空，存入昵称
				criteria.setCreateTime(new Date());
				criteria.setUpdateTime(new Date());
				this.appCustomerService.addAppCustomer(criteria);
				_logger.info("买家[" + buyer.getLoginAccount() + "]成功成为卖家[" + seller.getLoginAccount() + "]的客户.");
			} else {
				_logger.info("买家[" + buyer.getLoginAccount() + "]已经是卖家[" + seller.getLoginAccount() + "]的客户.");
			}
		} catch (Exception e) {
			_logger.info("添加客户失败！", e);
		}
	}

}
