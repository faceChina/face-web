package com.zjlp.face.web.server.operation.marketing.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;

public interface MarketingActivityDetailDao {

	Long add(MarketingActivityDetail activityDetail);

	MarketingActivityDetail getValidById(Long id);

	List<MarketingActivityDetail> findList(MarketingActivityDetail detail);

	void edit(MarketingActivityDetail detail);

	List<MarketingActivityDetailDto> findDtoList(MarketingActivityDetail detail);

	void delByActivityId(Long activityId);

	void delById(Long id);

	void editStatus(MarketingActivityDetail detail);

}
