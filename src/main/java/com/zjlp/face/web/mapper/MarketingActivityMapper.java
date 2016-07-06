package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;

public interface MarketingActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketingActivity record);

    int insertSelective(MarketingActivity record);

    MarketingActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketingActivity record);

    int updateByPrimaryKey(MarketingActivity record);

	MarketingActivity selectValidActivityByToolId(Long toolId);

	List<MarketingActivity> selectListByToolId(Long toolId);

	List<MarketingActivityDto> selectPage(MarketingActivityDto dto);

	Integer selectCount(MarketingActivityDto dto);

	void delById(Long id);

	void updateStatusByToolId(MarketingActivity activity);
}