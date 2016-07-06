package com.zjlp.face.web.server.product.im.service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;

public interface ImMessageService {
	/**
	 * 新增
	 * @Title: addImMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imMessage
	 * @date 2014年10月9日 下午3:52:18  
	 * @author dzq
	 */
	void addImMessage(ImMessage imMessage);
	
	/**
	 * 聊天记录
	 * @Title: findImUserMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imMessageDto
	 * @return
	 * @date 2014年9月29日 上午10:37:45  
	 * @author Administrator
	 */
	Pagination<ImMessageDto> findImUserMessage(ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination);
}
