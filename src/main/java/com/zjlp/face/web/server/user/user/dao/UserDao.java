package com.zjlp.face.web.server.user.user.dao;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.user.user.domain.User;

public interface UserDao {
	
	void wechatRegisterUser(User user);
	
	User getWechatUserByOpenId(String openId);
	
	User getUserByNameAndPasswd(String username,String password);
	
	User getUserByName(String username);
	
	User getByLoginAccountRedis(String loginAccount);

	Long add(User user);
	
	User getAllUserByLoginAccount(String loginAccount);
	
	void edit(User user);
	
	User getById(Long id);

	void editPassWdById(User user);

	void editCellById(User user);

	User getUserByOpenId(String openId);

	void updateLpNoByUserId(Long userId, String lpNo);

	Integer countLpNo(String lpNo, Long userId);
	
	List<User> getUserByLpNo (Map<String,Object> map);

	Integer getTotalCountByLpNo (Map<String,Object> map);

	Integer getCountByMyInvitationCode(String myInvitationCode);

	User getUserByMyInvitationCode(String myInvitationCode);

	List<User> findInvitedUsers(String myInvitationCode);
	
	Integer getCountMyInvitationAmount(Long userId);
	
	Integer getCountBisInvitationAmount(Long userId);

	void updateMyInvitationCodeByUserId(Long userId, String myInvitationCode);
}
