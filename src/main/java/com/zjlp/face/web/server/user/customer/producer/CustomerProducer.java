package com.zjlp.face.web.server.user.customer.producer;

import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.server.user.user.domain.User;

public interface CustomerProducer {

	/**
	 * 添加buyer为seller的客户
	 * 
	 * @param buyer
	 * @param seller
	 * @throws AppCustomerException
	 */
	void addCustomer(User buyer, User seller) throws AppCustomerException;

}
