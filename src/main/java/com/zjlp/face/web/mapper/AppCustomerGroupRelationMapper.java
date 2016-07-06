package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerGroupRelationVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月11日 下午2:13:50
 *
 */
public interface AppCustomerGroupRelationMapper {

	/**
	 * @Title: insertGroup
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param consumerGroupRelation
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午6:14:17
	 */
	void insertGroup(AppCustomerGroupRelation consumerGroupRelation);

	/**
	 * @Title: selectCustomerGroups
	 * @Description: (我的分组列表)
	 * @param customerId
	 * @return
	 * @return List<AppCustomerGroupRelation> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午6:14:07
	 */
	List<AppCustomerGroupRelation> selectCustomerGroups(Long customerId);

	/**
	 * @Title: deletedByPrimarykey
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param primarykey
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午6:13:59
	 */
	void deletedByPrimarykey(Long primarykey);

	/**
	 * @Title: deleteByGroupId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param map
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午6:13:55
	 */
	void deleteByGroupId(Map<String, Object> map);

	/**
	 * @Title: selectRelationByUserIdAndGrpId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param consumerGroupRelation
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午6:12:54
	 */
	AppCustomerGroupRelation selectRelationByUserIdAndGrpId(AppCustomerGroupRelation consumerGroupRelation);

	/**
	 * @Title: selectUserIsInGroup
	 * @Description: (统计user是否在组内)
	 * @param map
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月12日 下午6:16:16
	 */
	AppCustomerGroupRelation selectUserIsInGroup(AppCustomerGroupRelationVo relationVo);

	/**
	 * @Title: updateAppGroupRelationShip
	 * @Description: (更新分组关系)
	 * @param groupRelation
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 上午9:51:20
	 */
	void updateAppGroupRelationShip(AppCustomerGroupRelation groupRelation);

	/**
	 * @Title: selectAppGroupRelationShipById
	 * @Description: (通过主键查找分组关系)
	 * @param id
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月14日 上午11:45:35
	 */
	AppCustomerGroupRelation selectAppGroupRelationShipById(Long id);

}
