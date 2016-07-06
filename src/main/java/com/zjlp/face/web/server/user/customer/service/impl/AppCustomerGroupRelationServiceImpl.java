package com.zjlp.face.web.server.user.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.customer.dao.AppCustomerGroupRelationDao;
import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerGroupRelationVo;
import com.zjlp.face.web.server.user.customer.service.AppCustomerGroupRelationService;

/**
* 
* @author Baojiang Yang
* @date 2015年8月11日 下午5:01:09
*
*/ 
@Service
public class AppCustomerGroupRelationServiceImpl implements AppCustomerGroupRelationService {

	@Autowired
	private AppCustomerGroupRelationDao appCustomerGroupRelationDao;

	@Override
	public List<AppCustomerGroupRelation> findCustomerGroups(Long customerId) {
		return this.appCustomerGroupRelationDao.getCutsomerGroups(customerId);
	}

	@Override
	public Long addAppGroupRelationShip(AppCustomerGroupRelation groupRelation) {
		return this.appCustomerGroupRelationDao.addCustomerGroupRelation(groupRelation);
	}

	@Override
	public void removeAppGroupRelationShip(Long primarykey) {
		this.appCustomerGroupRelationDao.removeAppGroupRelationShip(primarykey);
	}

	@Override
	public void removeAppGroupRelationShipByGroupId(Long groupId, Integer type) {
		this.appCustomerGroupRelationDao.removeAppGroupRelationShipByGroupId(groupId, type);
	}

	@Override
	public AppCustomerGroupRelation selectRelationByUserIdAndGrpId(AppCustomerGroupRelation consumerGroupRelation) {
		return this.appCustomerGroupRelationDao.selectRelationByUserIdAndGrpId(consumerGroupRelation);
	}

	@Override
	public AppCustomerGroupRelation selectUserIsInGroup(AppCustomerGroupRelationVo relationVo) {
		return this.appCustomerGroupRelationDao.selectUserIsInGroup(relationVo);
	}

	@Override
	public void updateAppGroupRelationShip(AppCustomerGroupRelation groupRelation) {
		this.appCustomerGroupRelationDao.updateAppGroupRelationShip(groupRelation);
	}

	@Override
	public AppCustomerGroupRelation findAppGroupRelationShipById(Long id) {
		return this.appCustomerGroupRelationDao.selectAppGroupRelationShipById(id);
	}

}
