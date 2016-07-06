package com.zjlp.face.web.server.operation.appoint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.appoint.dao.DynamicFormDao;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;
import com.zjlp.face.web.server.operation.appoint.service.DynamicFormService;
@Service
public class DynamicFormServiceImpl implements DynamicFormService {
	@Autowired
	private DynamicFormDao dynamicFormDao;

	@Override
	public List<DynamicForm> findDynamicFormByRemoteIdAndCode(Long remoteId, Integer remoteCode) {
		return dynamicFormDao.findDynamicFormByRemoteIdAndCode(remoteId, remoteCode);
	}

	@Override
	public void insert(DynamicForm dynamicForm) {
		dynamicFormDao.insert(dynamicForm);
	}

	@Override
	public void delete(Long id) {
		dynamicFormDao.delete(id);
	}

	@Override
	public void update(DynamicForm dynamicForm) {
		dynamicFormDao.update(dynamicForm);
	}
	
}
