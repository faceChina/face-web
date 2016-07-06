package com.zjlp.face.web.server.user.customer.service;

import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerGroupRelationVo;

/**
* 
* @author Baojiang Yang
* @date 2015年8月11日 下午5:01:00
*
*/ 
public interface AppCustomerGroupRelationService {

	/**
	 * 新增加一个分组关系
	 * 
	 * @param groupRelation
	 * @param type
	 */
	Long addAppGroupRelationShip(AppCustomerGroupRelation groupRelation);

	/**
	 * 获取客户分组列表
	 * 
	 * @param cutsomerId
	 * @return
	 */
	List<AppCustomerGroupRelation> findCustomerGroups(Long customerId);

	/**
	 * @param primarykey
	 */
	void removeAppGroupRelationShip(Long primarykey);

	/**
	 * 移除关系表中分组关系
	 * 
	 * @param groupId
	 */
	void removeAppGroupRelationShipByGroupId(Long groupId, Integer type);
	
	/**
	 * @Title: selectRelationByUserIdAndGrpId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param consumerGroupRelation
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午6:15:52
	 */
	AppCustomerGroupRelation selectRelationByUserIdAndGrpId(AppCustomerGroupRelation consumerGroupRelation);


	/**
	 * @Title: selectUserIsInGroup
	 * @Description: (检查好友是否已经存在于分组)
	 * @param relationVo
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 上午9:12:12
	 */
	AppCustomerGroupRelation selectUserIsInGroup(AppCustomerGroupRelationVo relationVo);

	/**
	 * @Title: updateAppGroupRelationShip
	 * @Description: (更新分组关系)
	 * @param groupRelation
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 上午9:48:25
	 */
	void updateAppGroupRelationShip(AppCustomerGroupRelation groupRelation);

	/**
	 * @Title: findAppGroupRelationShipById
	 * @Description: (通过主键查找分组关系)
	 * @param id
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月14日 上午11:43:54
	 */
	AppCustomerGroupRelation findAppGroupRelationShipById(Long id);

}
