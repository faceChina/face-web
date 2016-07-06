package com.zjlp.face.web.server.product.material.bussiness.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.product.material.bussiness.FancyMessageBusiness;
import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;
import com.zjlp.face.web.server.product.material.service.FancyMessageService;

@Service
public class FancyMessageBusinessImpl implements FancyMessageBusiness{
	
	@Autowired
	private FancyMessageService fancyMessageService;
	
	@Override
	public FancyMessageItem selectByPrimaryKeyEditBrowerTime(Long id) {
		FancyMessageItem fancyMessageItem = fancyMessageService.selectByPrimaryKey(id);
		AssertUtil.notNull(fancyMessageItem, "精选已不存在！","精选已不存在");
		fancyMessageService.editBrowerTimeById(fancyMessageItem);
		return fancyMessageItem;
	}

	@Override
	public Long like(Long id) {
		FancyMessageItem fancyMessageItem = fancyMessageService.selectByPrimaryKey(id);
		AssertUtil.notNull(fancyMessageItem, "精选已不存在！","精选已不存在");
		fancyMessageService.editLikeById(fancyMessageItem);
		
		return fancyMessageItem.getLikeNum();
	}
	
}
