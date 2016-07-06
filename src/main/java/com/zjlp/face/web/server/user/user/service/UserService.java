package com.zjlp.face.web.server.user.user.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

public interface UserService {
	
	/**
	 * 根据微信用户openId静默注册
	 * @Title: registerAnonymousUser 
	 * @Description: (查询该openid是否有对应的用户，如果没有则生成，有则不做处理) 
	 * @param openId
	 * @date 2015年9月22日 下午2:50:20  
	 * @author talo
	 */
	void wechatRegisterUser(User user);
	
	/**
	 * 根据用户openid获取微信注册用户
	 * @param openId
	 * @return
	 * @author talo
	 */
	User getWechatUserByOpenId(String openId);
	
	
	/**
	 * 登陆验证
	 * @Title: getUserByNameAndPasswd
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param username
	 * @param password
	 * @return
	 * @date 2014年7月22日 上午11:25:03
	 * @author fjx
	 */
	User getUserByNameAndPasswd(String username, String password);
	
	User getUserByName(String username);

	/**
	 * @Title: getByLoginAccountRedis
	 * @Description: (带缓存取用户,性能依赖于Rdis服务器的是都和server在同一局域网内)
	 * @param loginAccount
	 * @return
	 * @return User 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月10日 下午8:43:28
	 */
	User getByLoginAccountRedis(String loginAccount);

	Long add(UserVo userVo);
	
	void edit(User user);
	
	User getById(Long id);
	
	/**
	 * 修改登录密码
	 * 
	 * @Title: editPassWd
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param passwd
	 *            新密码
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午1:41:13
	 * @author lys
	 */
	boolean editPassWd(Long userId, String passwd) throws UserException;

	/**
	 * 修改用户手机号码
	 * 
	 * @Title: editCell
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param cell
	 *            手机号码
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午1:47:40
	 * @author lys
	 */
	boolean editCell(Long userId, String cell) throws UserException;
	
	
	User getAllUserByLoginAccount(String mobile);

	/**
	 * 根据用户openid查询用户
	 * @Title: getUserByOpenId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param openId
	 * @return
	 * @date 2015年4月17日 下午1:57:01  
	 * @author ah
	 */
	User getUserByOpenId(String openId);

	/**
	 * 新增匿名用户
	 * @Title: addAnonymousUser 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param u
	 * @date 2015年4月17日 下午2:06:32  
	 * @author ah
	 */
	void addAnonymousUser(User user);

	/**
	 * 新增用户
	 * @Title: add 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param user
	 * @date 2015年5月11日 下午4:35:01  
	 * @author ah
	 */
	void add(User user);
	
	/**
	 * 注册
	 * @param userVo
	 */
	void register(UserVo userVo);

	/**
	 * 
	 * @Title: updateLpNoByUserId 
	 * @Description: 更新用户脸谱号
	 * @param userId
	 * @param lpNo
	 * @date 2015年8月25日 下午3:23:47  
	 * @author cbc
	 */
	void updateLpNoByUserId(Long userId, String lpNo);

	/**
	 * 
	 * @Title: countLpNo 
	 * @Description: 查询该脸谱号的个数
	 * @param lpNo
	 * @return
	 * @date 2015年8月25日 下午3:28:07  
	 * @author cbc
	 * @param userId 
	 */
	Integer countLpNo(String lpNo, Long userId);
	
	/**
	 * 
	 * @Title: getUserByLpNo 
	 * @Description: 根据脸谱号查找用户
	 * @param lpNo
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2015年8月31日 下午19:20:07  
	 * @author talo
	 */
	Pagination<User> getUserByLpNo (String phoneOrlpNo,Pagination<User> pagination);
	
	/**
	 * @Description: 查询邀请码是否存在
	 * @date: 2015年9月23日 上午9:52:06
	 * @author: zyl
	 */
	Integer getCountByMyInvitationCode(String myInvitationCode);

	User getUserByMyInvitationCode(String myInvitationCode);

	/**
	 * @Title: findInvitedUsers
	 * @Description: (通过我的邀请码统计被邀请的用户)
	 * @param myInvitationCode
	 * @return
	 * @return List<User> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月28日 上午10:51:21
	 */
	List<User> findInvitedUsers(String myInvitationCode);
	
	/**
	 * @Title: getCountInvitationAmount
	 * @Description: (统计我邀请的人数和二度邀请的人数)
	 * @param myInvitationCode
	 * @return UserVo 返回类型
	 * @throws
	 * @author talo
	 * @date 2015年10月6日 下午13:15:50
	 */
	UserVo getCountInvitationAmount (Long userId);

	void updateShuaLianUser(User user);
}
