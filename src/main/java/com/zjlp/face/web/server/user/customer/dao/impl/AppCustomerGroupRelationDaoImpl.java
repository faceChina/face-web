package com.zjlp.face.web.server.user.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppCustomerGroupRelationMapper;
import com.zjlp.face.web.server.user.customer.dao.AppCustomerGroupRelationDao;
import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerGroupRelationVo;


/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月11日 上午9:46:07
 *
 */
@Repository("appCustomerGroupRelationDao")
public class AppCustomerGroupRelationDaoImpl implements AppCustomerGroupRelationDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<AppCustomerGroupRelation> getCutsomerGroups(Long customerId) {
		return this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).selectCustomerGroups(customerId);
	}

	@Override
	public Long addCustomerGroupRelation(AppCustomerGroupRelation customerGroupRelation) {
		this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).insertGroup(customerGroupRelation);
		return customerGroupRelation.getId();
	}

	@Override
	public void removeAppGroupRelationShip(Long primarykey) {
		this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).deletedByPrimarykey(primarykey);

	}

	@Override
	public void removeAppGroupRelationShipByGroupId(Long groupId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("type", type);
		this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).deleteByGroupId(map);
	}

	@Override
	public AppCustomerGroupRelation selectRelationByUserIdAndGrpId(AppCustomerGroupRelation consumerGroupRelation) {
		return this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).selectRelationByUserIdAndGrpId(consumerGroupRelation);
	}

	@Override
	public AppCustomerGroupRelation selectUserIsInGroup(AppCustomerGroupRelationVo relationVo) {
		return this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).selectUserIsInGroup(relationVo);
	}

	@Override
	public void updateAppGroupRelationShip(AppCustomerGroupRelation groupRelation) {
		this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).updateAppGroupRelationShip(groupRelation);
	}

	@Override
	public AppCustomerGroupRelation selectAppGroupRelationShipById(Long id) {
		return this.sqlSession.getMapper(AppCustomerGroupRelationMapper.class).selectAppGroupRelationShipById(id);
	}

}
