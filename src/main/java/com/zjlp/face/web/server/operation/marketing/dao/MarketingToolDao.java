package com.zjlp.face.web.server.operation.marketing.dao;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;

public interface MarketingToolDao {

	Long add(MarketingTool tool);

	MarketingTool getById(Long id);

	MarketingTool getToolByType(MarketingTool tool);

	void edit(MarketingTool tool);

}
