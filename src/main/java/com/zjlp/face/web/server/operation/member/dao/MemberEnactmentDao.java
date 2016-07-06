package com.zjlp.face.web.server.operation.member.dao;

import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;

/**
 * 会员设置Dao
 * @ClassName: MemberEnactmentDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月10日 上午11:26:00
 */
public interface MemberEnactmentDao {

	Long add(MemberEnactment enactment);

	MemberEnactment getBySellerId(Long adminId);

	MemberEnactment getValidById(Long id);

	void edit(MemberEnactment enactment);

}
