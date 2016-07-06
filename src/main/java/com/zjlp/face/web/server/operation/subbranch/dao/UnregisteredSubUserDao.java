package com.zjlp.face.web.server.operation.subbranch.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.subbranch.domain.UnregisteredSubUser;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月15日 下午3:19:22
 *
 */
public interface UnregisteredSubUserDao {

	/**
	 * @Title: addUnregisteredSubUser
	 * @Description: (新增一条成为分店但是未注册用户)
	 * @param unregisteredSubUser
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午3:19:25
	 */
	Long addUnregisteredSubUser(UnregisteredSubUser unregisteredSubUser);

	/**
	 * @Title: findUnregisteredSubUserById
	 * @Description: (通过主键查找)
	 * @param id
	 * @return
	 * @return UnregisteredSubUser 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午3:30:56
	 */
	UnregisteredSubUser findUnregisteredSubUserById(Long id);

	/**
	 * @Title: findByLoginAccount
	 * @Description: (根据电话号码查找用户)
	 * @param loginAccount
	 * @return
	 * @return List<UnregisteredSubUser> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午3:30:06
	 */
	List<UnregisteredSubUser> findByLoginAccount(String loginAccount);
	
	/**
	 * @Title: updateByPrimarykey
	 * @Description: (更新用户)
	 * @param unregisteredSubUser
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午3:24:59
	 */
	void updateByPrimarykey(UnregisteredSubUser unregisteredSubUser);

	/**
	 * @Title: removeByPrimarykey
	 * @Description: (根据主键硬删除)
	 * @param id
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午3:22:03
	 */
	void removeByPrimarykey(Long id);

}
