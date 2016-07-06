package com.zjlp.face.web.server.product.material.service;

import com.zjlp.face.web.exception.ext.FancyMessageException;
import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;


public interface FancyMessageService {
	
	/**
	 * 根据ID查询刷脸精选细项
	 * @param id	
	 * @return
	 * @throws FancyMessageException
	 */
	FancyMessageItem selectByPrimaryKey(Long id) throws FancyMessageException;
	
	/**
	 * 修改刷脸精选细项
	 * @param fancyMessageItem
	 */
	void update(FancyMessageItem fancyMessageItem);
	
	/**
	 * 修改浏览量
	 * @param fancyMessageItem
	 */
	void editBrowerTimeById(FancyMessageItem fancyMessageItem);

	/**
	 * 点赞
	 * @param fancyMessageItem
	 */
	void editLikeById(FancyMessageItem fancyMessageItem);
}
