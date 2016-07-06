package com.zjlp.face.web.server.user.mulchat.service;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;

/**
 * @author Baojiang Yang
 *
 */

public interface MulChatQRCodeService {

	Long addMulChatQRCode(MulChatQRCode mulChatQRCode);

	void removeByPrimarykey(String qRCode);

	Long updateByPrimaryKey(MulChatQRCode mulChatQRCode);

	MulChatQRCode findByQRCode(String qRCode);

	MulChatQRCode findByPrimarykey(Long id);
}
