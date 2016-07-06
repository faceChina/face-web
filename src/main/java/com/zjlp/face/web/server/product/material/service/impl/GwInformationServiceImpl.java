package com.zjlp.face.web.server.product.material.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.GwInformationDao;
import com.zjlp.face.web.server.product.material.domain.GwInformation;
import com.zjlp.face.web.server.product.material.service.GwInformationService;

@Service("gwInformationService")
public class GwInformationServiceImpl implements GwInformationService {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private GwInformationDao gwInformationDao;
	
	@Override
	public GwInformation getGwInformation(Long id){
		AssertUtil.notNull(id, "资讯ID不能为空");
		GwInformation gwInformation = gwInformationDao.getByPrimaryKey(id);
		return gwInformation;
	}
	
	@Override
	public Pagination<GwInformation> findGwInformationPageList(GwInformation gwInformation, Pagination<GwInformation> pagination){
		int count = gwInformationDao.getCount(gwInformation);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gwInformation", gwInformation);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<GwInformation> list = gwInformationDao.findGwInformationPageList(map);
		pagination.setDatas(list);
		pagination.setTotalRow(count);
		return pagination;
	}
}
