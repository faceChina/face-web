package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;

public interface GoodSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodSku record);

    int insertSelective(GoodSku record);

    GoodSku selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(GoodSku record);

    int updateByPrimaryKey(GoodSku record);

	List<GoodSku> selectGoodSkusByGoodId(Long goodId);

	GoodSku selectByGoodIdAndPrprerty(Map<String, Object> map);

	void removeByGoodId(Long goodId);

	void removeById(Long id);

	List<GoodSku> selectGoodskuByGoodIdAndPrprerty(GoodSku goodSku);

	List<GoodSku> selectAllGoodSkusByGoodId(Long goodId);

	List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Map<String, Object> map);

}