package com.zjlp.face.web.server.user.customer.dao;

import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerGroupRelationVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月11日 下午2:13:27
 *
 */
public interface AppCustomerGroupRelationDao {

	List<AppCustomerGroupRelation> getCutsomerGroups(Long customerId);

	Long addCustomerGroupRelation(AppCustomerGroupRelation customerGroupRelation);

	void removeAppGroupRelationShip(Long primarykey);

	void removeAppGroupRelationShipByGroupId(Long groupId, Integer type);

	AppCustomerGroupRelation selectRelationByUserIdAndGrpId(AppCustomerGroupRelation consumerGroupRelation);

	AppCustomerGroupRelation selectUserIsInGroup(AppCustomerGroupRelationVo relationVo);

	void updateAppGroupRelationShip(AppCustomerGroupRelation groupRelation);

	AppCustomerGroupRelation selectAppGroupRelationShipById(Long id);
}
