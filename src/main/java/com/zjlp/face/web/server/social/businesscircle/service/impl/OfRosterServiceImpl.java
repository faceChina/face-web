package com.zjlp.face.web.server.social.businesscircle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.social.businesscircle.dao.OfRosterDao;
import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;
import com.zjlp.face.web.server.social.businesscircle.service.OfRosterService;

@Service("ofRosterService")
public class OfRosterServiceImpl implements OfRosterService {
	
	@Autowired
	private OfRosterDao ofRosterDao;

	@Override
	public List<OfRoster> queryRosterByUserName(String userName,String excludeName) {
		return ofRosterDao.selectRosterByUserName(userName,excludeName);
	}

}
