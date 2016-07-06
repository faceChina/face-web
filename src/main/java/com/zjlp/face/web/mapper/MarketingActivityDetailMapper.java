package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;

public interface MarketingActivityDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketingActivityDetail record);

    int insertSelective(MarketingActivityDetail record);

    MarketingActivityDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketingActivityDetail record);

    int updateByPrimaryKey(MarketingActivityDetail record);

	List<MarketingActivityDetail> selectByDetail(MarketingActivityDetail detail);

	List<MarketingActivityDetailDto> selectDtoList(
			MarketingActivityDetail detail);

	void delByActivityId(Long activityId);

	void delById(Long id);

	void updateStatusByToolId(MarketingActivityDetail detail);
}