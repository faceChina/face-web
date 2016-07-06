package com.zjlp.face.web.server.user.mulchat.business;

import com.zjlp.face.web.exception.ext.MulChatQRCodeException;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;


/**
 * @author Baojiang Yang
 *
 */
public interface MulChatQRCodeBusiness {

	/**
	 * 将生成二维码存入DB
	 *
	 * @param mulChatQRCode
	 * @return
	 */
	MulChatQRCode addMulChatQRCode(MulChatQRCode mulChatQRCode) throws MulChatQRCodeException;

	/**
	 * 扫描二维码 检查是否有效 返回扫描信息
	 *
	 * @param id
	 * @return
	 */
	MulChatQRCode findMulChatQRCode(String qRCode) throws MulChatQRCodeException;

	/**
	 * 删除过期的二维码
	 * 
	 * @param qRCode
	 */
	void removeByPrimarykey(String qRCode);

}
