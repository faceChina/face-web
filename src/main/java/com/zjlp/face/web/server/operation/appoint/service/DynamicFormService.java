package com.zjlp.face.web.server.operation.appoint.service;

import java.util.List;

import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;

public interface DynamicFormService {
	List<DynamicForm> findDynamicFormByRemoteIdAndCode(Long remoteId, Integer remoteCode);

	void insert(DynamicForm dynamicForm);

	void delete(Long id);

	void update(DynamicForm dynamicForm);
}
