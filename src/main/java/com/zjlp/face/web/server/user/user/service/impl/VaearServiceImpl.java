package com.zjlp.face.web.server.user.user.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.user.dao.VaearDao;
import com.zjlp.face.web.server.user.user.domain.VArea;
import com.zjlp.face.web.server.user.user.service.VaearService;

@Service
public class VaearServiceImpl implements VaearService {

	private Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private VaearDao vaearDao;
	
	@Override
	public Integer getAreaByAreaName(String areaName) {
		VArea vArea = vaearDao.getAreaByAreaName(areaName);
		
		if(vArea==null){
			return null;
		}
		
		return vArea.getCode();
	}
}
