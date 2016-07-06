package com.zjlp.face.web.server.product.material.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.exception.ext.FancyMessageException;
import com.zjlp.face.web.server.product.material.dao.FancyMessageItemDao;
import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;
import com.zjlp.face.web.server.product.material.service.FancyMessageService;

@Service("fancyMessageService")
public class FancyMessageServiceImpl implements FancyMessageService {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private FancyMessageItemDao fancyMessageItemDao;
	
	@Override
	public FancyMessageItem selectByPrimaryKey(Long id)throws FancyMessageException {
		return fancyMessageItemDao.selectByPrimaryKey(id);
	}

	@Override
	public void update(FancyMessageItem fancyMessageItem) {
		fancyMessageItemDao.update(fancyMessageItem);
	}

	@Override
	public void editBrowerTimeById(FancyMessageItem fancyMessageItem){
		fancyMessageItem.setPageViews(fancyMessageItem.getPageViews() + 1);
		fancyMessageItemDao.update(fancyMessageItem);
	}

	@Override
	public void editLikeById(FancyMessageItem fancyMessageItem) {
		fancyMessageItem.setLikeNum(fancyMessageItem.getLikeNum() + 1);
		fancyMessageItemDao.update(fancyMessageItem);
	}
}
