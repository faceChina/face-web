package com.zjlp.face.web.server.user.user.producer;

import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.user.domain.User;

/**
 * 用户支撑类
 * 
 * @ClassName: UserProducer
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月16日 下午2:47:59
 */
public interface UserProducer {

	/**
	 * 修改用户的姓名和身份证号
	 * 
	 * @Title: editNameAndIdentity
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午2:49:29
	 * @author lys
	 */
	boolean editNameAndIdentity(Long userId, String userName, String identity)
			throws UserException;

	/**
	 * 编辑用户的手机号码
	 * 
	 * @Title: editCell
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            用户id
	 * @param phone
	 *            用户手机号码
	 * @return
	 * @throws UserException
	 * @date 2015年3月16日 下午2:51:37
	 * @author lys
	 */
	boolean editCell(Long userId, String phone) throws UserException;

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


	User getUserByName(String username);
	
	
	/**
	 * 根据登录名查询用户
	 * @Title: getUserByUserName
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param username
	 * @return
	 * @throws UserException
	 * @return User
	 * @author phb
	 * @date 2015年5月13日 上午11:14:12
	 */
	User getUserByUserName(String username) throws UserException;
	
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
}
