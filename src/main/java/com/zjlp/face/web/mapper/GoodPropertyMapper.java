package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.good.domain.GoodProperty;

public interface GoodPropertyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodProperty record);

    int insertSelective(GoodProperty record);

    GoodProperty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodProperty record);

    int updateByPrimaryKey(GoodProperty record);

	List<GoodProperty> selectListByGoodId(Long goodId);

	List<GoodProperty> selectList(GoodProperty goodProperty);

	GoodProperty selectByGoodIdAndPropValueId(Map<String, Object> map);

	List<GoodProperty> selectSalesList(GoodProperty salesGoodProperty);

	List<GoodProperty> selectInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty);

	void removeById(Long id);

	List<GoodProperty> selectSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty);
}