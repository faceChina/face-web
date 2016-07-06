package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.trade.order.domain.PromotionDsetail;

public interface PromotionDsetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PromotionDsetail record);

    int insertSelective(PromotionDsetail record);

    PromotionDsetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionDsetail record);

    int updateByPrimaryKey(PromotionDsetail record);

	List<PromotionDsetail> selectByOrderNo(String orderNo);
}