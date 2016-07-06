package com.zjlp.face.web.server.operation.subbranch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.subbranch.dao.UnregisteredSubUserDao;
import com.zjlp.face.web.server.operation.subbranch.domain.UnregisteredSubUser;
import com.zjlp.face.web.server.operation.subbranch.service.UnregisteredSubUserService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月15日 下午3:34:33
 *
 */
@Service
public class UnregisteredSubUserServiceImpl implements UnregisteredSubUserService {

	@Autowired
	private UnregisteredSubUserDao unregisteredSubUserDao;

	@Override
	public Long addUnregisteredSubUser(UnregisteredSubUser unregisteredSubUser) {
		return this.unregisteredSubUserDao.addUnregisteredSubUser(unregisteredSubUser);
	}

	@Override
	public UnregisteredSubUser findUnregisteredSubUserById(Long id) {
		return this.unregisteredSubUserDao.findUnregisteredSubUserById(id);
	}

	@Override
	public List<UnregisteredSubUser> findByLoginAccount(String loginAccount) {
		return this.unregisteredSubUserDao.findByLoginAccount(loginAccount);
	}

	@Override
	public void updateByPrimarykey(UnregisteredSubUser unregisteredSubUser) {
		this.unregisteredSubUserDao.updateByPrimarykey(unregisteredSubUser);

	}

	@Override
	public void removeByPrimarykey(Long id) {
		this.unregisteredSubUserDao.removeByPrimarykey(id);
	}

}
