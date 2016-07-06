package com.zjlp.face.web.server.product.material.bussiness;

import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;

public interface FancyMessageBusiness {

	FancyMessageItem selectByPrimaryKeyEditBrowerTime(Long id);
	
	/**
	 * 点赞
	 * @param id	刷脸精选细项ID
	 */
	Long like(Long id);
}
