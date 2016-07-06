package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;

public interface MemberWechatRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberWechatRelation record);

    int insertSelective(MemberWechatRelation record);

    MemberWechatRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberWechatRelation record);

    int updateByPrimaryKey(MemberWechatRelation record);

	MemberWechatRelation getMemberWechatRelation(
			MemberWechatRelation memberWechatRelation);
}