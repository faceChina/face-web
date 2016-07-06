package com.zjlp.face.web.server.operation.subbranch.business;

import com.zjlp.face.web.exception.ext.UnregisteredSubUserException;

public interface UnregisteredSubUserBusiness {

	/**
	 * @Title: addUnregisteredSubUser
	 * @Description: (增加一条成为分店但是未注册用户记录)
	 * @param loginAccount
	 * @param name
	 * @return
	 * @throws UnregisteredSubUserException
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午3:59:18
	 */
	Long addUnregisteredSubUser(String loginAccount, String name) throws UnregisteredSubUserException;

	/**
	 * @Title: completeSubRelationship
	 * @Description: (对新注册用户检查此表是否有记录，有则建立正确的分店关系)
	 * @param loginAccount
	 * @throws UnregisteredSubUserException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月15日 下午4:01:43
	 */
	void completeSubRelationship(String loginAccount) throws UnregisteredSubUserException;

}
