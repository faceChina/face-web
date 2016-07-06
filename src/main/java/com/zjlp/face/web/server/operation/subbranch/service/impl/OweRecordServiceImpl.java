package com.zjlp.face.web.server.operation.subbranch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.subbranch.dao.OweRecordDao;
import com.zjlp.face.web.server.operation.subbranch.domain.OweRecord;
import com.zjlp.face.web.server.operation.subbranch.service.OweRecordService;
@Service
public class OweRecordServiceImpl implements OweRecordService{
	@Autowired
	private OweRecordDao oweRecordDao;
	
	@Override
	public Long insertSelective(OweRecord record) {
		return oweRecordDao.insertSelective(record);
	}

	@Override
	public OweRecord selectByPrimaryKey(Long id) {
		return oweRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(OweRecord record) {
		oweRecordDao.updateByPrimaryKeySelective(record);
	}

}
