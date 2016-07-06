package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.operation.subbranch.domain.UnregisteredSubUser;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月15日 下午3:15:45
 *
 */
public interface UnregisteredSubUserMapper {

	int insert(UnregisteredSubUser unregisteredSubUser);

	UnregisteredSubUser selectByPrimaryKey(Long id);

	List<UnregisteredSubUser> selectByLoginAccount(String loginAccount);

	int updateByPrimaryKey(UnregisteredSubUser unregisteredSubUser);

	void deleteByPrimarykey(Long id);
}
