package com.zjlp.face.web.server.user.mulchat.business.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.MulChatQRCodeException;
import com.zjlp.face.web.server.user.mulchat.business.MulChatQRCodeBusiness;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;
import com.zjlp.face.web.server.user.mulchat.service.MulChatQRCodeService;
import com.zjlp.face.web.util.DataUtils;
import com.zjlp.face.web.util.Md5Util;

/**
 * @author Baojiang Yang
 *
 */
@Repository("mulChatQRCodeBusiness")
public class MulChatQRCodeBusinessImpl implements MulChatQRCodeBusiness {

	private static final String ENCODE = "utf-8";

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private MulChatQRCodeService mulChatQRCodeService;

	@Override
	public MulChatQRCode addMulChatQRCode(MulChatQRCode mulChatQRCode) throws MulChatQRCodeException {
		try {
			AssertUtil.notNull(mulChatQRCode, "Param[mulChatQRCode] can not be null.");
			AssertUtil.notNull(mulChatQRCode.getGroupId(), "Param[groupId] can not be null.");
			AssertUtil.notNull(mulChatQRCode.getCreateUser(), "Param[createUser] can not be null.");
			Long id = this.mulChatQRCodeService.addMulChatQRCode(mulChatQRCode);
			if (id != null) {
				MulChatQRCode qRcodeAdded = this.mulChatQRCodeService.findByPrimarykey(id);
				if (qRcodeAdded != null && qRcodeAdded.getId() != null) {
					String qrcode = Md5Util.MD5Encode(qRcodeAdded.getId().toString(), ENCODE);
					mulChatQRCode.setCreateTime(new Date());// 初始化时间
					mulChatQRCode.setStatus(1);// 状态有效
					mulChatQRCode.setqRCode(qrcode);// ID加密字段
					this.mulChatQRCodeService.updateByPrimaryKey(mulChatQRCode);
				} else {
					_log.info("更新二维码失败！");
					return new MulChatQRCode(1800);
				}
			} else {
				_log.info("插入二维码失败！");
				return new MulChatQRCode(1801);
			}
			MulChatQRCode result = this.mulChatQRCodeService.findByPrimarykey(id);
			
			if (DataUtils.isNotBlankQRcode(result)) {
				result.setOverdueDate(DateUtil.addDay(result.getCreateTime(), 7));// 一周后过期
				//设置 群组二维码地址
				String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
				result.setqRCodeUrl(wgjurl+Constants.MUL_CHAT_QRC_URL.replace(Constants.MUL_CHAT_QRC, result.getqRCode()));//群组二维码 url
				return result;
			}
			return new MulChatQRCode(1801);
		} catch (MulChatQRCodeException e) {
			_log.error("添加二维码失败", e);
			throw new MulChatQRCodeException(e);
		}
	}

	@Override
	public MulChatQRCode findMulChatQRCode(String qRCode) throws MulChatQRCodeException {
		try {
			AssertUtil.notNull(qRCode, "Param[mulChatQRCode] can not be null.");
			return this.mulChatQRCodeService.findByQRCode(qRCode);
		} catch (Exception e) {
			_log.error("查找二维码失败", e);
			throw new MulChatQRCodeException(e);
		}
	}

	@Override
	public void removeByPrimarykey(String qRCode) {
		try {
			AssertUtil.notNull(qRCode, "Param[mulChatQRCode] can not be null.");
			MulChatQRCode existQRCOde = this.mulChatQRCodeService.findByQRCode(qRCode);
			if (DataUtils.isNotBlankQRcode(existQRCOde)) {
				this.mulChatQRCodeService.removeByPrimarykey(qRCode);
			}
		} catch (Exception e) {
			_log.error("删除二维码失败", e);
			throw new MulChatQRCodeException(e);
		}
	}

}
