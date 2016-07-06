package com.zjlp.face.web.server.user.mulchat.business;

import com.zjlp.face.web.exception.ext.MulChatInfomationException;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatInformationBusiness {

	/**
	 * 插入群组信息
	 * 
	 * @param mulChatInformation
	 * @return
	 * @throws MulChatInfomationException
	 */
	String insert(MulChatInformation mulChatInformation) throws MulChatInfomationException;

	/**
	 * 删除群组信息
	 * 
	 * @param id
	 * @throws MulChatInfomationException
	 */
	void deleteByPrimarykey(String id) throws MulChatInfomationException;

	/**
	 * 查询群组信息
	 * 
	 * @param id
	 * @return
	 * @throws MulChatInfomationException
	 */
	MulChatInformation selectByPrimarykey(String id) throws MulChatInfomationException;

	/**
	 * 更新群组信息
	 * 
	 * @param mulChatInformation
	 * @return
	 * @throws MulChatInfomationException
	 */
	String updateByPrimaryKey(MulChatInformation mulChatInformation) throws MulChatInfomationException;
}
