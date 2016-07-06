package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.VArea;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

public interface VAreaMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(VArea record);

    int insertSelective(VArea record);

    VArea selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(VArea record);

    int updateByPrimaryKey(VArea record);

	VaearDto selectOneAreaByCode(Integer code);

	List<VArea> selectAllByParentId(VArea area);

	VArea getAreaByAreaName(String areaName);
	
	VaearDto selectOneCityByCode(Integer code);
}