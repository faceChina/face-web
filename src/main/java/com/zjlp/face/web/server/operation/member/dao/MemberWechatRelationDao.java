package com.zjlp.face.web.server.operation.member.dao;

import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;

/**
 * 会员微信关注关系
 * @ClassName: MemberWechatRelationDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月21日 上午10:49:52
 */
@Deprecated
public interface MemberWechatRelationDao {

	/**
	 * 新增会员微信关注关系
	 * @Title: add 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberWechatRelation
	 * @date 2015年4月21日 上午10:51:12  
	 * @author ah
	 */
	void add(MemberWechatRelation memberWechatRelation);
	
	/**
	 * 查询会员微信关注关系
	 * @Title: getMemberWechatRelation 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberWechatRelation
	 * @return
	 * @date 2015年4月21日 上午10:52:33  
	 * @author ah
	 */
	MemberWechatRelation getMemberWechatRelation(MemberWechatRelation memberWechatRelation);

	/**
	 * 编辑会员微信关注关系
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberWechatRelation
	 * @date 2015年4月21日 下午2:03:07  
	 * @author ah
	 */
	void edit(MemberWechatRelation memberWechatRelation);
}
