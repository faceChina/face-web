package com.zjlp.face.web.server.user.mulchat.business.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.MulChatViewNickException;
import com.zjlp.face.web.server.user.mulchat.business.MulChatViewNickBusiness;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;
import com.zjlp.face.web.server.user.mulchat.service.MulChatViewNickService;

/**
 * @author Baojiang Yang
 *
 */
@Repository("mulChatViewNickBusiness")
public class MulChatViewNickBusinessImpl implements MulChatViewNickBusiness {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private MulChatViewNickService mulChatViewNickService;

	@Override
	public String insert(MulChatViewNick mulChatViewNick) throws MulChatViewNickException {
		try {
			AssertUtil.notNull(mulChatViewNick, "Param[mulChatViewNick] can not be null.");
			AssertUtil.notNull(mulChatViewNick.getLoginAccount(),"Param[loginAccount] can not be null.");
			MulChatViewNick existViewNick = this.mulChatViewNickService.select(mulChatViewNick);
			if (existViewNick == null) {
				return this.mulChatViewNickService.insert(mulChatViewNick);
			}else {
				_log.info("群组ID：" + mulChatViewNick.getGroupId() + "组员：" + mulChatViewNick.getLoginAccount() + "数据已经存在");
				return StringUtils.EMPTY;
			}
		} catch (Exception e) {
			throw new MulChatViewNickException(e);
		}
	}

	@Override
	public void delete(MulChatViewNick mulChatViewNick) throws MulChatViewNickException {
		try {
			AssertUtil.notNull(mulChatViewNick, "Param[mulChatViewNick] can not be null.");
			AssertUtil.notNull(mulChatViewNick.getGroupId(),"Param[groupId] can not be null.");
			AssertUtil.notNull(mulChatViewNick.getLoginAccount(),"Param[loginAccount] can not be null.");
			MulChatViewNick existViewNick = this.mulChatViewNickService.select(mulChatViewNick);
			if (existViewNick != null && StringUtils.isNotBlank(existViewNick.getLoginAccount())) {
				this.mulChatViewNickService.delete(mulChatViewNick);
			} else {
				_log.info("群组ID：" + mulChatViewNick.getGroupId() + "组员：" + mulChatViewNick.getLoginAccount() + "要删除数据不存在");
			}
		} catch (Exception e) {
			throw new MulChatViewNickException(e);
		}
	}

	@Override
	public MulChatViewNick select(MulChatViewNick mulChatViewNick) throws MulChatViewNickException {
		try {
			AssertUtil.notNull(mulChatViewNick, "Param[loginAccount] can not be null.");
			return this.mulChatViewNickService.select(mulChatViewNick);
		} catch (Exception e) {
			throw new MulChatViewNickException(e);
		}
	}

	@Override
	public String update(MulChatViewNick mulChatViewNick) throws MulChatViewNickException {
		try {
			AssertUtil.notNull(mulChatViewNick, "Param[mulChatViewNick] can not be null.");
			AssertUtil.notNull(mulChatViewNick.getGroupId(),"Param[groupId] can not be null.");
			AssertUtil.notNull(mulChatViewNick.getLoginAccount(),"Param[loginAccount] can not be null.");
			MulChatViewNick existViewNick = this.mulChatViewNickService.select(mulChatViewNick);
			if (existViewNick != null && StringUtils.isNotBlank(existViewNick.getLoginAccount())) {
				return this.mulChatViewNickService.update(mulChatViewNick);
			} else {
				_log.info("群组ID：" + mulChatViewNick.getGroupId() + "组员：" + mulChatViewNick.getLoginAccount() + "要更新的群显示昵称不存在,自动为您插入数据。");
				return this.insert(mulChatViewNick);
			}
		} catch (Exception e) {
			throw new MulChatViewNickException(e);
		}
	}

	@Override
	public List<MulChatViewNick> findGroupNickList(String groupId) throws MulChatViewNickException {
		try {
			AssertUtil.notNull(groupId, "Param[groupId] can not be null.");
			return this.mulChatViewNickService.findGroupNickList(groupId);
		} catch (Exception e) {
			throw new MulChatViewNickException(e);
		}
	}

}
