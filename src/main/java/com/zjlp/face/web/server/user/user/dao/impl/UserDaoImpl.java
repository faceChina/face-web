package com.zjlp.face.web.server.user.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.web.mapper.UserMapper;
import com.zjlp.face.web.server.user.user.dao.UserDao;
import com.zjlp.face.web.server.user.user.domain.User;


@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	SqlSession sqlSession;
	
	
	@Override
	public void wechatRegisterUser(User user) { 
		sqlSession.getMapper(UserMapper.class).insertSelective(user);
	}
	
	@Override
	public User getWechatUserByOpenId(String registerSourceUserId) { 
		return sqlSession.getMapper(UserMapper.class).getWechatUserByOpenId(registerSourceUserId);
	}
	
	@Override
	public User getUserByNameAndPasswd(String username,String password) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("loginAccount", username);
		map.put("passwd", password);
		return sqlSession.getMapper(UserMapper.class).getUserByNameAndPasswd(map);
	}
	
	@Override
	public User getUserByName(String username) {
		return sqlSession.getMapper(UserMapper.class).getUserByName(username);
	}

	@Override
	@RedisCached(mode = CachedMode.GET, method = CachedMethod.GET_SET, key = { "user" })
	public User getByLoginAccountRedis(String loginAccount) {
		return sqlSession.getMapper(UserMapper.class).getUserByName(loginAccount);
	}

	@Override
	public User getAllUserByLoginAccount(String loginAccount) {
		return sqlSession.getMapper(UserMapper.class).getAllUserByLoginAccount(loginAccount);
	}
	
	@Override
	public Long add(User user){
		sqlSession.getMapper(UserMapper.class).insertSelective(user);
		return user.getId();
	}
	
	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"user:id"})
	public void edit(User user){
		sqlSession.getMapper(UserMapper.class).updateByPrimaryKeySelective(user);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"user"})
	public User getById(Long id){
		return sqlSession.getMapper(UserMapper.class).selectByPrimaryKey(id);
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"user:id"})
	public void editPassWdById(User user) {
		sqlSession.getMapper(UserMapper.class).updatePassWdById(user);
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"user:id"})
	public void editCellById(User user) {
		sqlSession.getMapper(UserMapper.class).updateCellById(user);
	}

	@Override
	public User getUserByOpenId(String openId) {
		return sqlSession.getMapper(UserMapper.class).getUserByOpenId(openId);
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"user"})
	public void updateLpNoByUserId(Long userId, String lpNo) {
		sqlSession.getMapper(UserMapper.class).updateLpNoByUserId(userId, lpNo);
	}
	
	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"user"})
	public void updateMyInvitationCodeByUserId(Long userId, String myInvitationCode) {
		sqlSession.getMapper(UserMapper.class).updateMyInvitationCodeByUserId(userId, myInvitationCode);
	}

	@Override
	public Integer countLpNo(String lpNo, Long userId) {
		return sqlSession.getMapper(UserMapper.class).countLpNo(lpNo, userId);
	}

	@Override
	public List<User> getUserByLpNo(Map<String,Object> map) {
		return sqlSession.getMapper(UserMapper.class).getUserByLpNo(map);
	}

	@Override
	public Integer getTotalCountByLpNo(Map<String,Object> map) {
		return sqlSession.getMapper(UserMapper.class).getTotalCountByLpNo(map);
	}

	@Override
	public Integer getCountByMyInvitationCode(String myInvitationCode) {
		return sqlSession.getMapper(UserMapper.class).getCountByMyInvitationCode(myInvitationCode);
	}

	@Override
	public User getUserByMyInvitationCode(String myInvitationCode) {
		return sqlSession.getMapper(UserMapper.class).getUserByMyInvitationCode(myInvitationCode);
	}

	@Override
	public List<User> findInvitedUsers(String myInvitationCode) {
		return sqlSession.getMapper(UserMapper.class).findInvitedUsers(myInvitationCode);
	}

	@Override
	public Integer getCountMyInvitationAmount(Long userId) {
		return sqlSession.getMapper(UserMapper.class).getCountMyInvitationAmount(userId);
	}

	@Override
	public Integer getCountBisInvitationAmount(Long userId) {
		return sqlSession.getMapper(UserMapper.class).getCountBisInvitationAmount(userId);
	}
	
}
