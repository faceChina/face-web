package com.zjlp.face.web.server.operation.subbranch.business.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.UnregisteredSubUserException;
import com.zjlp.face.web.server.operation.subbranch.business.UnregisteredSubUserBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.UnregisteredSubUser;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.operation.subbranch.service.UnregisteredSubUserService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;

/**
* 
* @author Baojiang Yang
* @date 2015年7月15日 下午4:03:15
*
*/ 
@Service("unregisteredSubUserBusiness")
public class UnregisteredSubUserBusinessImpl implements UnregisteredSubUserBusiness {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private UnregisteredSubUserService unregisteredSubUserService;

	@Override
	public Long addUnregisteredSubUser(String loginAccount, String name) throws UnregisteredSubUserException {
		try {
			AssertUtil.notNull(loginAccount, "Param[loginAccount] can not be null.");
			UnregisteredSubUser unregisteredSubUser = new UnregisteredSubUser();
			unregisteredSubUser.setLoginAccount(loginAccount);
			unregisteredSubUser.setName(name);
			unregisteredSubUser.setStatus(1);
			unregisteredSubUser.setCreateTime(new Date());
			unregisteredSubUser.setUpdateTime(new Date());
			return this.unregisteredSubUserService.addUnregisteredSubUser(unregisteredSubUser);
		} catch (Exception e) {
			_log.error("分享成为分店失败！");
			throw new UnregisteredSubUserException(e);
		}
	}

	@Override
	public void completeSubRelationship(String loginAccount) throws UnregisteredSubUserException {
		try {
			AssertUtil.notNull(loginAccount, "Param[loginAccount] can not be null.");
			// 检查注册用户是否成功注册
			User existUser = this.userService.getAllUserByLoginAccount(loginAccount);
			AssertUtil.notNull(existUser.getId(), "用户:" + loginAccount + "不存在，操作失败！");
			// 检查是否存在待完善的分店关系
			Subbranch existSub = this.subbranchService.findUncompleteSubByUserCell(loginAccount);
			AssertUtil.notNull(existSub.getId(), "用户:" + loginAccount + "分店关系不存在，操作失败！");
			Subbranch updateSubbranch = new Subbranch();
			updateSubbranch.setId(existSub.getId());// 主键
			updateSubbranch.setUserId(existUser.getId());// 完善userId
			// 更新分店表
			this.subbranchService.updateByPrimaryKey(updateSubbranch);
			_log.info("用户:"+loginAccount+"已经正确维护分店关系.");
			// 删除原有数据
			List<UnregisteredSubUser> unregisteredSubUserList = this.unregisteredSubUserService.findByLoginAccount(loginAccount);
			UnregisteredSubUser unregisteredSubUser = new UnregisteredSubUser();
			if (CollectionUtils.isNotEmpty(unregisteredSubUserList)) {
				for (UnregisteredSubUser current : unregisteredSubUserList) {
					unregisteredSubUser.setId(current.getId());// 要更新的数据
					unregisteredSubUser.setStatus(0);// 已经注册
					unregisteredSubUser.setUpdateTime(new Date());
					this.unregisteredSubUserService.updateByPrimarykey(unregisteredSubUser);
					//如影响注册性能,删除这些记录。
//					this.unregisteredSubUserService.removeByPrimarykey(current.getId());
					_log.info("用户:"+current.getLoginAccount()+"已经正确更新.");
				}

			}
		} catch (Exception e) {
			_log.error("注册分店用户失败！");
			throw new UnregisteredSubUserException(e);
		}
		
	}

}
