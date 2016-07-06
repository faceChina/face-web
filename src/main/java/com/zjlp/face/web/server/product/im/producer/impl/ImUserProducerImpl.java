package com.zjlp.face.web.server.product.im.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.producer.ImUserProducer;
import com.zjlp.face.web.server.product.im.service.ImUserService;

@Repository("imUserProducer")
public class ImUserProducerImpl implements ImUserProducer {
	
	@Autowired
	private ImUserService imUserService;

	@Override
	public void addImUser(ImUser imUser) {
		imUserService.addImUser(imUser);

	}

	@Override
	public void editImUser(ImUser imUser) {
		imUserService.editImUser(imUser);

	}

	@Override
	public ImUser getImUserById(Long id) {
		return imUserService.getImUserById(id);
	}

	@Override
	public ImUser getImUserByRemoteId(String remoteId, Integer type) {
		return imUserService.getImUserByRemoteId(remoteId, type);
	}

	@Override
	public ImUser getImUserByUserName(String userName) {
		return imUserService.getImUserByUserName(userName);
	}

	@Override
	public ImUser getImUserByUserName(String userName, Integer type) {
		return imUserService.getImUserByUserName(userName, type);
	}

}
