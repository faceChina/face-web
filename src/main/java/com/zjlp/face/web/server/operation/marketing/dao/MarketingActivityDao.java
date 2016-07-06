package com.zjlp.face.web.server.operation.marketing.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;

public interface MarketingActivityDao {

	Long add(MarketingActivity activity);

	MarketingActivity getValidActivityByToolId(Long toolId);

	MarketingActivity getById(Long id);

	List<MarketingActivity> findListByToolId(Long toolId);

	List<MarketingActivityDto> findPage(MarketingActivityDto dto);

	Integer getCount(MarketingActivityDto dto);

	void edit(MarketingActivity activity);

	void delById(Long activityId);

	void editStatus(MarketingActivity activity);

}
