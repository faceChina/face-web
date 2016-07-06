package com.zjlp.face.web.server.user.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.user.shop.service.VareaService;
import com.zjlp.face.web.server.user.user.dao.VaearDao;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VareaServiceImpl implements VareaService {

	@Autowired
	private VaearDao vaearDao;
	
	public VaearDto getVareaById(Integer areaCode) {
		return vaearDao.getAreaByAreaCode(areaCode);
	}

	@Override
	public VaearDto getCityById(Integer areaCode) {
		return vaearDao.getCityByAreaCode(areaCode);
	}

}
