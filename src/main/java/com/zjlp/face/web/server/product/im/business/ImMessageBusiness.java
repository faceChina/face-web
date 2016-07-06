package com.zjlp.face.web.server.product.im.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;

public interface ImMessageBusiness {
	
	/**
	 * 发送消息
	 * @Title: sendMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sender
	 * @param receiver
	 * @param message
	 * @date 2014年10月14日 下午2:22:40  
	 * @author Administrator
	 */
	void sendMessage(Long sender,String receiver,String message) throws Exception;
	
	/**
	 * 群发信息
	 * @Title: sendMassMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imMessages
	 * @date 2014年10月9日 下午3:55:15  
	 * @author dzq
	 */
	void sendMassMessage(List<ImMessage> imMessages) throws Exception;
	
	/**
	 * 查询两个用户的聊天记录
	 * @Title: findImUserMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imMessageDto
	 * @param receiver
	 * @return
	 * @date 2014年10月14日 下午3:15:06  
	 * @author Administrator
	 */
	Pagination<ImMessageDto> findImUserMessage(String receiver,ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination) throws Exception;
	
}
