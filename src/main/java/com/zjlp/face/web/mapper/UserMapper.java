package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.user.user.domain.User;


public interface UserMapper {
	
	User getWechatUserByOpenId(String registerSourceUserId);
	
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);
    
    User getUserByNameAndPasswd(Map<String,String> map);
    
    User getUserByName(String username);

	void updatePassWdById(User user);

	void updateCellById(User user);
    
    User getAllUserByLoginAccount(String loginAccount);

	User getUserByOpenId(String openId);

	void updateLpNoByUserId(@Param("userId")Long userId, @Param("lpNo")String lpNo);

	Integer countLpNo(@Param("lpNo")String lpNo, @Param("userId")Long userId);
    
	List<User> getUserByLpNo (Map<String,Object> map);
	
	Integer getTotalCountByLpNo (Map<String,Object> map);

	Integer getCountByMyInvitationCode(String myInvitationCode);

	User getUserByMyInvitationCode(String myInvitationCode);

	List<User> findInvitedUsers(String myInvitationCode);
	
	Integer getCountMyInvitationAmount(Long userId);
	
	Integer getCountBisInvitationAmount(Long userId);

	void updateMyInvitationCodeByUserId(Long userId, String myInvitationCode);
}