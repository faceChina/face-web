package com.zjlp.face.web.server.product.im.dao;

import com.zjlp.face.web.server.product.im.domain.ImUser;

public interface ImUserDao {
	
	void add(ImUser imUser);
	
	void edit(ImUser imUser);
	
	void deleteById(Long id);
	
	ImUser getById(Long id);

	ImUser getByRemoteId(String remoteId, Integer type);
	
	ImUser getByUserName(String userName);
	
	ImUser getByUserName(String userName, Integer type);
}
