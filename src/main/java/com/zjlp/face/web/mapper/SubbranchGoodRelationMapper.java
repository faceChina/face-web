package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.GoodSkuDifferenceVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;

public interface SubbranchGoodRelationMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(SubbranchGoodRelation record);

    int insertSelective(SubbranchGoodRelation record);

    SubbranchGoodRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubbranchGoodRelation record);

    int updateByPrimaryKey(SubbranchGoodRelation record);

	Integer getTotalCount(SubbranchGoodRelationVo subbranchGoodRelationVo);

	List<SubbranchGoodRelationVo> findPageSubbracheGood(Map<String, Object> map);

	Integer isExist(SubbranchGoodRelation subbranchGoodRelation);
    
    Integer findSubbranchGoodRate (SubbranchGoodRelation subbranchGoodRelation);

	GoodSkuDifferenceVo querySkuDifference(Long goodId);
}