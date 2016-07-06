package com.zjlp.face.web.server.user.user.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserInvitationVo;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

public interface UserBusiness {

	/**
	 * 根据微信用户openId静默注册
	 * @Title: registerAnonymousUser 
	 * @Description: (查询该openid是否有对应的用户，如果没有则生成，有则不做处理) 
	 * @param openId
	 * @date 2015年9月22日 下午2:50:20  
	 * @author talo
	 */
	void wechatRegisterUser(String openId);
	
	/**
	 * 根据用户openid获取微信注册用户
	 * @param openId
	 * @return
	 * @author talo
	 */
	User getWechatUserByOpenId(String openId);
	
	/**
	 * 通过用户名获取登录信息
	 * @Title: getUserInfo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param username 用户名
	 * @return
	 * @throws UserException
	 * @date 2015年3月24日 上午9:21:17  
	 * @author fjx
	 */
	UserInfo getUserInfo(String username) throws UserException;

	
	/**
	 * 通过用户名密码获取登录信息
	 * @Title: getUserInfo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param username 用户名
	 * @param password	密码
	 * @return
	 * @throws UserException
	 * @date 2015年3月24日 上午9:21:53  
	 * @author fjx
	 */
	UserInfo getUserInfo(String username, String password) throws UserException;

	/**
	 * 修改用户的资料
	 * 
	 * @Title: editProfile
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param headimgurl
	 *            用户头像url
	 * @param nickName
	 *            用户昵称
	 * @param email
	 *            用户邮箱
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午3:15:51
	 * @author lys
	 */
	boolean editProfile(Long userId, String headimgurl, String nickName,
			String email) throws UserException;

	/**
	 * 重新设置用户密码
	 * 
	 * @Title: editUserPasswd
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param newPasswd
	 *            新密码
	 * @param confirmPasswd
	 *            确认密码
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午3:27:44
	 * @author lys
	 */
	boolean editUserPasswd(Long userId, String newPasswd, String confirmPasswd)
			throws UserException;

	/**
	 * 重新设置用户密码
	 * 
	 * @Title: editUserPasswd
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param oldPasswd
	 *            旧密码
	 * @param newPasswd
	 *            新密码
	 * @param confirPasswd
	 *            确认密码
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午3:30:27
	 * @author lys
	 */
	boolean editUserPasswd(Long userId, String oldPasswd, String newPasswd,
			String confirPasswd) throws UserException;
	
	
	/**
	 * 查询用户
	 * 
	 * @Title: getUserById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午2:53:31
	 * @author lys
	 */
	User getUserById(Long userId) throws UserException;
	
	/**
	 * 重新绑定手机号码
	 * @Title: editUserCell 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @param oldCell 旧手机号码
	 * @param newCell 新手机号码
	 * @return
	 * @throws UserException
	 * @date 2015年3月23日 下午7:35:36  
	 * @author lys
	 */
	boolean editUserCell(Long userId, String oldCell, String newCell) throws UserException;
	
	/**
	 * 重新绑定手机号码
	 * @Title: editUserCell 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @param Cell 手机号码
	 * @return
	 * @throws UserException
	 * @date 2015年3月23日 下午7:47:46  
	 * @author lys
	 */
	boolean editUserCell(Long userId, String Cell) throws UserException;
	
	/**
	 * 注册
	 * @Title: register 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userVo 
	 * @return
	 * @throws UserException
	 * @date 2015年3月12日 上午11:37:46  
	 * @author fjx
	 */
	String register(UserVo userVo) throws UserException;
	
	
	/**
	 * 验证手机号是否被注册
	 * @Title: checkLoginAccount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param phone 手机号
	 * @return
	 * @throws UserException
	 * @date 2015年3月24日 上午9:28:21  
	 * @author fjx
	 */
	String checkLoginAccount(String phone) throws UserException;
	
	

	String checkUserPhone(String loginAccount) throws UserException;
	
	/**
	 * 验证用户登录帐号和绑定手机号的有效性
	 * @Title: checkUserPhone
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param loginAccount
	 * @param phone
	 * @return
	 * @throws UserException
	 * @return String
	 * @author phb
	 * @date 2015年4月6日 下午8:14:35
	 */
	String checkUserPhone(String loginAccount,String phone)
			throws UserException;


	User getUserByLoginAccount(String loginAccount)throws UserException;

	/**
	 * @Title: getByLoginAccountRedis
	 * @Description: (通过登录号从缓存取用户)
	 * @param loginAccount
	 * @return
	 * @throws UserException
	 * @return User 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月10日 下午8:45:50
	 */
	User getByLoginAccountRedis(String loginAccount) throws UserException;



	/**
	 * 根据登录名跟密码查询用户信息
	 * @Title: getUserByLoginAccountAndPasswd 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cell
	 * @param passwd
	 * @return
	 * @date 2015年4月15日 下午8:46:38  
	 * @author ah
	 */
	User getUserByLoginAccountAndPasswd(String cell, String passwd);
	
	/**
	 * 根据用户openid注册匿名用户
	 * @Title: registerAnonymousUser 
	 * @Description: (查询该openid是否有对应的匿名用户，如果没有则生成匿名用，有则不做处理) 
	 * @param openId
	 * @date 2015年4月17日 下午1:45:04  
	 * @author ah
	 */
	void registerAnonymousUser(String openId);
	
	/**
	 * 根据用户openid获取用户
	 * @param openId
	 * @return
	 */
	User getUserByOpenId(String openId);
	/**
	 * APP 增加修改用户信息方法
	* @Title: editProfile
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @param headimgurl
	* @param nickName
	* @param sex
	* @param areaCode
	* @param address
	* @param wechat
	* @param signature
	* @throws UserException
	* @return boolean    返回类型
	* @author wxn  
	* @date 2015年4月11日 下午5:51:37
	 */
	boolean editProfile(Long userId, String headimgurl, String circlePictureUrl, String nickName, Integer sex, Integer areaCode,String address,String wechat,String signature, String lpNo) throws UserException;

	/**
	 * 注册用户（免费店铺用）
	 * @Title: registerForFreeShop 
	 * @Description: (注册用户（免费店铺用）) 
	 * @param user
	 * @param pic
	 * @return
	 * @date 2015年2月6日 下午5:15:37  
	 * @author ah
	 */
	void registerForFreeShop(User user, byte[] pic);


	/**
	 * 
	 * @Title: editFreeShopUserInfo 
	 * @Description: 修改免费店铺的用户信息
	 * @param headimgurl 头像路径
	 * @param nickname 昵称
	 * @param shopName 店铺名称
	 * @param userId 
	 * @param shopNo 
	 * @date 2015年5月12日 下午1:45:16  
	 * @author cbc
	 * @param logoPath 
	 */
	void editFreeShopUserInfo(String headimgurl, String nickname,
			String shopName, String logoPath, Long userId, String shopNo);


	/**
	 * 修改用户信息
	 * @Title: editUserForFreeShop 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param headPicture
	 * @param nickName
	 * @param userId
	 * @date 2015年5月16日 上午11:50:44  
	 * @author ah
	 */
	void editUserForFreeShop(String headPicture, String nickName, Long userId);

	/**
	 * 
	 * @Title: updateLpNoByUserId 
	 * @Description: 更新用户的刷脸号
	 * @param userId
	 * @param lpNo
	 * @date 2015年8月25日 下午3:07:27  
	 * @author cbc
	 */
	void updateLpNoByUserId(Long userId, String lpNo) throws UserException;
	
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
	Pagination<User> getUserByLpNo (String lpNo,Pagination<User> pagination);


	User getUserByMyInvitationCode(String myInvitationCode);
	
	/**
	 * @Title: findUserInviteList
	 * @Description: (统计用户通过邀请码邀请关系（从节点往树根统计）)
	 * @param userId
	 * @return
	 * @throws UserException
	 * @return List<User> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月28日 上午10:33:43
	 */
	List<User> findUserInviteList(Long userId) throws UserException;

	/**
	 * @Title: findInvitedUsers
	 * @Description: (通过邀请码统计被邀请用户)
	 * @param InvitationCode
	 * @return
	 * @throws UserException
	 * @return List<User> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月28日 上午10:56:42
	 */
	List<User> findInvitedUsers(String InvitationCode) throws UserException;

	/**
	 * @Title: findInvitedUsers
	 * @Description: (通过邀请码统计被邀请用户)
	 * @param userId
	 * @return
	 * @throws UserException
	 * @return List<User> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月28日 上午11:15:23
	 */
	List<User> findInvitedUsers(Long userId) throws UserException;

	/**
	 * @Title: findMyInvitedRelationship
	 * @Description: (统计每个用户邀请加入的情况)
	 * @param userId
	 * @return
	 * @throws UserException
	 * @return UserInvitationVo 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月28日 下午3:33:38
	 */
	UserInvitationVo findMyInvitedRelationship(Long userId) throws UserException;
	
	
	/**
	 * @Title: getCountInvitationAmount
	 * @Description: (统计我邀请的人数和二度邀请的人数)
	 * @param myInvitationCode
	 * @return UserVo 返回类型
	 * @throws
	 * @author talo
	 * @date 2015年10月6日 下午13:15:50
	 */
	UserVo getCountInvitationAmount (Long userId) throws UserException;
	
	/**
	 * 修改用户信息,运营后台接口
	 * @param user
	 * @throws UserException
	 */
	void updateShuaLianUser(User user) throws UserException;
}
