package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;

public interface MemberEnactmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberEnactment record);

    int insertSelective(MemberEnactment record);

    MemberEnactment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberEnactment record);

    int updateByPrimaryKey(MemberEnactment record);

	MemberEnactment selectBySellerId(Long sellerId);

	MemberEnactment selectValidById(Long id);
}