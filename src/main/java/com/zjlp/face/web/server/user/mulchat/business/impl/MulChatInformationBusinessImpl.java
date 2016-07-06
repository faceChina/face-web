package com.zjlp.face.web.server.user.mulchat.business.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.MulChatInfomationException;
import com.zjlp.face.web.server.user.mulchat.business.MulChatInformationBusiness;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;
import com.zjlp.face.web.server.user.mulchat.service.MulChatInformationService;

/**
 * @author Baojiang Yang
 *
 */
@Repository("mulChatInformationBusiness")
public class MulChatInformationBusinessImpl implements MulChatInformationBusiness {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private MulChatInformationService mulChatInfService;
	
	@Override
	public String insert(MulChatInformation mulChatInformation) throws MulChatInfomationException {
		try {
			AssertUtil.notNull(mulChatInformation, "Param[MulChatInformation] can not be null.");
			AssertUtil.notNull(mulChatInformation.getGroupId(), "Param[grouId] can not be null.");
			MulChatInformation existMulChatInfor = this.mulChatInfService.selectByPrimarykey(mulChatInformation.getGroupId());
			if (existMulChatInfor == null) {
				return this.mulChatInfService.insert(mulChatInformation);
			} else {
				_log.info(mulChatInformation.getGroupId() + "已经存在，无法写入！");
				return StringUtils.EMPTY;
			}
		} catch (Exception e) {
			throw new MulChatInfomationException(e);
		}
	}

	@Override
	public void deleteByPrimarykey(String id) throws MulChatInfomationException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			MulChatInformation existMulChatInfor = this.mulChatInfService.selectByPrimarykey(id);
			if (existMulChatInfor != null && StringUtils.isNotBlank(existMulChatInfor.getGroupId())) {
				this.mulChatInfService.deleteByPrimarykey(id);
			} else {
				_log.info(id + "不存在，无法删除！");
			}
		} catch (Exception e) {
			throw new MulChatInfomationException(e);
		}
	}

	@Override
	public MulChatInformation selectByPrimarykey(String id) throws MulChatInfomationException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			return this.mulChatInfService.selectByPrimarykey(id);
		} catch (Exception e) {
			throw new MulChatInfomationException(e);
		}
	}

	@Override
	public String updateByPrimaryKey(MulChatInformation mulChatInformation) throws MulChatInfomationException {
		try {
			AssertUtil.notNull(mulChatInformation, "Param[mulChatInformation] can not be null.");
			MulChatInformation existMulChatInfor = this.mulChatInfService.selectByPrimarykey(mulChatInformation.getGroupId());
			if (existMulChatInfor != null && StringUtils.isNotBlank(existMulChatInfor.getGroupId())) {
				return this.mulChatInfService.updateByPrimaryKey(mulChatInformation);
			} else {
				this.mulChatInfService.insert(mulChatInformation);
				_log.info("群组：" + mulChatInformation.getGroupId() + "本不存在，由于提交数据完整，已经进行insert操作!");
				return mulChatInformation.getGroupId();
			}
		} catch (Exception e) {
			throw new MulChatInfomationException(e);
		}
	}

}
