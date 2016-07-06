package com.zjlp.face.web.server.operation.subbranch.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.UnregisteredSubUserMapper;
import com.zjlp.face.web.server.operation.subbranch.dao.UnregisteredSubUserDao;
import com.zjlp.face.web.server.operation.subbranch.domain.UnregisteredSubUser;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月15日 下午3:23:08
 *
 */
@Repository("UnregisteredSubUserDao")
public class UnregisteredSubUserDaoImpl implements UnregisteredSubUserDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addUnregisteredSubUser(UnregisteredSubUser unregisteredSubUser) {
		this.sqlSession.getMapper(UnregisteredSubUserMapper.class).insert(unregisteredSubUser);
		return unregisteredSubUser.getId();
	}

	@Override
	public UnregisteredSubUser findUnregisteredSubUserById(Long id) {
		return this.sqlSession.getMapper(UnregisteredSubUserMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<UnregisteredSubUser> findByLoginAccount(String loginAccount) {
		return this.sqlSession.getMapper(UnregisteredSubUserMapper.class).selectByLoginAccount(loginAccount);
	}

	@Override
	public void updateByPrimarykey(UnregisteredSubUser unregisteredSubUser) {
		this.sqlSession.getMapper(UnregisteredSubUserMapper.class).updateByPrimaryKey(unregisteredSubUser);
	}

	@Override
	public void removeByPrimarykey(Long id) {
		this.sqlSession.getMapper(UnregisteredSubUserMapper.class).deleteByPrimarykey(id);
	}

}
