package com.zjlp.face.web.server.user.security.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.security.domain.dto.UserRoleRelationDto;
import com.zjlp.face.web.server.user.security.producer.RoleProducer;
import com.zjlp.face.web.server.user.security.service.PermissionService;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.producer.UserProducer;


@Service
public class RoleProducerImpl implements RoleProducer{

	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserProducer userProducer;
	
	@Autowired
	private ShopProducer shopProducer;
	
	
	
	
	
	
	@Override
	public void addUserRoleRelation(Long userId) throws SecurityException {
		try {
			permissionService.addUserRoleRelation(userId,Constants.STRING_ROLE_U);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	@Override
	public void addShopRoleByUserId(Long userId, Integer type)
			throws SecurityException {
		try {
			AssertUtil.notNull(userId, "用户ID为空");
			AssertUtil.notNull(type, "参数为空");
			UserRoleRelationDto urrd = new UserRoleRelationDto();
			urrd.setUserId(userId);
			urrd.setCode(this._getShopCode(type));
			Integer countRole = permissionService.countRoleAndRelationByUserIdAndCode(urrd);
			if(0==countRole.intValue()){
				permissionService.addUserRoleRelation(userId,this._getShopCode(type));
			}
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}
	
	
	private String _getShopCode(Integer type){
		if(Constants.SHOP_GW_TYPE.equals(type)){
			return Constants.STRING_ROLE_GW;
		}
		if(Constants.SHOP_SC_TYPE.equals(type)){
			return Constants.STRING_ROLE_SC;
		}
		if(Constants.SHOP_FREE_TYPE.equals(type)){
			return Constants.STRING_ROLE_FREE;
		}
		return "";
	}

}
 