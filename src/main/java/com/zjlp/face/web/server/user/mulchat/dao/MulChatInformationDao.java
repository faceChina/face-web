package com.zjlp.face.web.server.user.mulchat.dao;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;

/**
 * @author Boajiang Yang
 *
 */
public interface MulChatInformationDao {

	String insert(MulChatInformation mulChatInformation);

	void deleteByPrimarykey(String id);

	MulChatInformation selectByPrimarykey(String id);

	String updateByPrimaryKey(MulChatInformation mulChatInformation);

}
