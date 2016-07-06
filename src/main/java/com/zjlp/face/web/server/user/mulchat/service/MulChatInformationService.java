package com.zjlp.face.web.server.user.mulchat.service;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatInformationService {

	String insert(MulChatInformation mulChatInformation);

	void deleteByPrimarykey(String id);

	MulChatInformation selectByPrimarykey(String id);

	String updateByPrimaryKey(MulChatInformation mulChatInformation);

}
