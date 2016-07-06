package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;

public interface MarketingToolMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketingTool record);

    int insertSelective(MarketingTool record);

    MarketingTool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketingTool record);

    int updateByPrimaryKey(MarketingTool record);

	MarketingTool selectByUserAndType(MarketingTool tool);
}