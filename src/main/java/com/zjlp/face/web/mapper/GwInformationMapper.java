package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.GwInformation;

public interface GwInformationMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(GwInformation record);

    int insertSelective(GwInformation record);

    GwInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GwInformation record);

    int updateByPrimaryKey(GwInformation record);

	int getCount(GwInformation fwInformation);

	List<GwInformation> findGwInformationPageList(Map<String, Object> map);
	
	Long getMaxSort();
	
    int delete(Long id);

	Long getMinSort();
}