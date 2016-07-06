package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatQRCodeMapper {

	int inser(MulChatQRCode mulChatQRCode);

	int deleteByPrimarykey(String qRCode);

	int updateByPrimaryKey(MulChatQRCode mulChatQRCode);

	MulChatQRCode selectByQRcode(String qRCode);
	
	MulChatQRCode selectByPrimarykey(Long id);
}
