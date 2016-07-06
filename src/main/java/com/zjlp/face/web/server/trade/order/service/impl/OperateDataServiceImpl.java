package com.zjlp.face.web.server.trade.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.server.trade.order.dao.SalesOrderDao;
import com.zjlp.face.web.server.trade.order.service.OperateDataService;

@Service
public class OperateDataServiceImpl implements OperateDataService {

	@Autowired
	private SalesOrderDao salesOrderDao;
	
	@Override
	public Long getAencyFreezeAmount(String shopNo) throws OperateDataException {
		Long amount = salesOrderDao.getAencyFreezeAmount(shopNo);
		return null == amount ? 0L : amount;
	}

	
}
