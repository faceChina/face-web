package com.zjlp.face.web.server.product.im.business.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zjlp.face.web.server.product.im.business.ImUserBusiness;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;
import com.zjlp.face.web.server.product.im.domain.vo.ImUserVo;
import com.zjlp.face.web.server.product.im.init.ImAnonymousUserPool;
import com.zjlp.face.web.server.product.im.service.ImFriendsService;
import com.zjlp.face.web.server.product.im.service.ImUserService;
import com.zjlp.face.web.server.product.im.util.ImConstantsUtil;

@Service
public class ImUserBusinessImpl implements ImUserBusiness {
	
	@Autowired
	private ImUserService imUserService;
	
	@Autowired
	private ImFriendsService imFriendsService;

	@Override
	public ImUserVo login(ImUser imUser) throws Exception {
		Assert.notNull(imUser);
		Assert.notNull(imUser.getType());
		Assert.notNull(imUser.getRemoteId());
		try {
			ImUserVo imUserVo = new ImUserVo();
			String remoteId = imUser.getRemoteId();
			Integer type = imUser.getType();
			ImUser checkImUser = imUserService.getImUserByRemoteId(remoteId, type);
			if (null == checkImUser) {//注册用户
				imUser.setStates(1);
				imUserService.addImUser(imUser);
				imUserVo.setImUser(imUser);
				imUserVo.setRegister(ImUserVo.REGISTER_TRUE);
				imUserVo.setId(imUser.getId());
			}else{
				imUserVo.setImUser(checkImUser);
				if (ImConstantsUtil.STATES_USER_INIT.equals(checkImUser.getStates())) {
					imUserVo.setRegister(ImUserVo.REGISTER_TRUE);
				}else{
					imUserVo.setRegister(ImUserVo.REGISTER_FALSE);
				}
				imUserVo.setId(checkImUser.getId());
			}
			return imUserVo;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void loginAnonymout(HttpSession session) throws Exception {
		Assert.notNull(session,"用户会话失效,匿名登录失败!");
		try {
			//Session中未存在用户
			ImUserVo imUserVo = null;
			if (null == session.getAttribute(ImConstantsUtil.IM_SESSION_USER_KEY)) {
				imUserVo = new ImUserVo();
				//从用户池中随即获取用户
				ImUser imUser = ImAnonymousUserPool.getInstance().getUser();
				if(null == imUser || StringUtils.isBlank(imUser.getUserName())){
					throw new RuntimeException("无法获取用户，登录失败！");
				}
				ImUser checkUser = imUserService.getImUserByUserName(imUser.getUserName());
				if (null == checkUser) {//用户未注册
					imUserService.addImUser(imUser);
					imUserVo.setImUser(imUser);
					imUserVo.setRegister(ImUserVo.REGISTER_TRUE);
				}else{//用户已注册
					imUserVo.setImUser(checkUser);
					imUserVo.setRegister(ImUserVo.REGISTER_FALSE);
				}
			}else{//Session中已存在用户
				imUserVo = (ImUserVo) session.getAttribute(ImConstantsUtil.IM_SESSION_USER_KEY);
				imUserVo.setRegister(ImUserVo.REGISTER_FALSE);
			}
			session.setAttribute(ImConstantsUtil.IM_SESSION_USER_KEY, imUserVo);
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	public List<ImFriendsDto> findFriendsListByUserId(Long imUserId) throws Exception {
		Assert.notNull(imUserId);
		try {
			return imFriendsService.findFriendsDtoListByUserId(imUserId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void editImUserStates(Long userId, Integer states) throws Exception {
		Assert.notNull(userId);
		Assert.notNull(states);
		try {
			ImUser checkUser = imUserService.getImUserById(userId);
			//状态未改变不做更改
			if (states.equals(checkUser.getStates())) {
				return ;
			}
			ImUser imUser = new ImUser();
			imUser.setId(userId);
			imUser.setStates(states);
			imUserService.editImUser(imUser);
		} catch (Exception e) {
			throw e;
		}
		
	}


	
}
