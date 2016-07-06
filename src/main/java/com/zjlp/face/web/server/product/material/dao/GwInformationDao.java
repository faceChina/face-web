package com.zjlp.face.web.server.product.material.dao;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.GwInformation;

public interface GwInformationDao {
	
	GwInformation getByPrimaryKey(Long id);
	
	int getCount(GwInformation gwInformation);

	List<GwInformation> findGwInformationPageList(Map<String, Object> map);
}
