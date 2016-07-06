package com.zjlp.face.web.server.social.businesscircle.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssCircleMsgVo;
import com.zjlp.face.web.component.metaq.producer.CircleMetaOperateProducer;
import com.zjlp.face.web.exception.ext.AppCircleException;
import com.zjlp.face.web.server.social.businesscircle.business.AppCircleBusiness;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;
import com.zjlp.face.web.server.social.businesscircle.helper.CircleDispatcher;
import com.zjlp.face.web.server.social.businesscircle.producer.AppCircleProducer;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;

@Repository("appCircleBusiness")
public class AppCircleBusinessImpl implements AppCircleBusiness {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private AppCircleProducer appCircleProducer;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private CircleMetaOperateProducer circleMetaOperateProducer;
	@Autowired
	private CircleDispatcher circleDispatcher;
	
	@Override
	public Long addAppCircleMsg(AppCircleMsg appCircle,String username)
			throws AppCircleException {
		try {
			Long id = appCircleProducer.addAppCircleMsg(appCircle, username);
			appCircle.setId(id);
			circleDispatcher.dispatcherCircle(appCircle, username);
			return id;
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public void deleteAppCircleMsg(Long id, Long userId)
			throws AppCircleException {
		appCircleProducer.deleteAppCircleMsg(id, userId);
	}

	@Override
	public AssCircleMsgVo getAppCircleById(Long id) throws AppCircleException {
		
		AppCircleMsgVo circle = appCircleProducer.getAppCircleById(id);
		AssCircleMsgVo vo = null;
		if (circle != null) {
			vo = new AssCircleMsgVo(circle);
		}
		return vo;
	}

	@Override
	public Pagination<AssCircleMsgVo> findMyAppCircleMsgVo(Long userId,com.zjlp.face.util.page.Pagination<AppCircleMsgVo> pagination)
			throws AppCircleException {
		
		pagination = appCircleProducer.findMyAppCircleMsgVo(userId,pagination);
		List<AssCircleMsgVo> voList = new ArrayList<AssCircleMsgVo>();
		for (AppCircleMsgVo circle : pagination.getDatas()) {
			AssCircleMsgVo vo = new AssCircleMsgVo(circle);
			if (vo.getUserId() != null) {
				User user = this.userBusiness.getUserById(vo.getUserId());
				vo.setCirclePictureUrl(user != null ? user.getCirclePictureUrl() : StringUtils.EMPTY);
			}
			voList.add(vo);
		}
		Pagination<AssCircleMsgVo> pag = new Pagination<AssCircleMsgVo>();
		pag.setDatas(voList);
		pag.setCurPage(pagination.getCurPage());
		pag.setStart(pagination.getStart());
		pag.setTotalRow(pagination.getTotalRow());
		pag.setPageSize(pagination.getPageSize());
		pag.setToPage(pagination.getToPage());
		return pag;
	}

	@Override
	public List<AssCircleMsgVo> queryAppCircleMsgVo(Long userId,Long msgId)
			throws AppCircleException {
		try {
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			/*String key = RedisKey.AppCircle_addAppCircleMsg_key_+userId;
			*//**获取我能查看的消息ID**//*
			List<Long> list = RedisListUtil.get(key);
			if(null == list || list.size() < 1){
				return null;
			}
			int	i = list.indexOf(msgId);
			int index = list.size();
			List<Long> idList = new ArrayList<Long>();
			if ( i >= index -1 ) {
				return null ;
			}else if ( i < 0  ){
				if ( index > 20) {
					idList = list.subList(index-20, index);
				}else {
					idList = list;
				}
			}else if ( index - i - 1 > 20 ) {
				idList = list.subList(index-20, index);
			}else{
				idList = list.subList(index-i, index);
			}
			Collections.reverse(idList);*/
			
			List<Long> idList = new ArrayList<Long>();
			List<AppCircleMsgVo> circleList = appCircleProducer.queryAppCircleMsgVo( userId, msgId,idList);
			List<AssCircleMsgVo> voList = new ArrayList<AssCircleMsgVo>();
			for (AppCircleMsgVo circle : circleList) {
				AssCircleMsgVo vo = new AssCircleMsgVo(circle);
				voList.add(vo);
			}
			return voList;
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public Long addAppPraise(AppPraise appPraise) throws AppCircleException {
			Long id = appCircleProducer.addAppPraise(appPraise);
			circleDispatcher.dispatcherCirclePraiseMQ(id);
			return id;
			
	}

	@Override
	public Long addAppComment(AppComment appComment) throws AppCircleException {
			Long id =  appCircleProducer.addAppComment(appComment);
			circleDispatcher.dispatcherCircleCommentMQ(id);
			return id;
	}

	@Override
	public int deleteAppComment(Long id, Long userId)
			throws AppCircleException {
			return appCircleProducer.deleteAppComment(id, userId);
	}

	@Override
	public Pagination<AssCircleMsgVo> queryAppCircleMsgVo(Long userId,Long upMsgId,Long downMsgId
,
			com.zjlp.face.util.page.Pagination<AppCircleMsgVo> pagination)
			throws AppCircleException {
		pagination = appCircleProducer.queryAppCircleMsgVo(userId, upMsgId,downMsgId, pagination);
		List<AssCircleMsgVo> voList = new ArrayList<AssCircleMsgVo>();
		for (AppCircleMsgVo circle : pagination.getDatas()) {
			AssCircleMsgVo vo = new AssCircleMsgVo(circle);
			if (vo.getUserId() != null) {
				User user = this.userBusiness.getUserById(vo.getUserId());
				vo.setCirclePictureUrl(user != null ? user.getCirclePictureUrl() : StringUtils.EMPTY);
			}
			voList.add(vo);
		}
		Pagination<AssCircleMsgVo> pag = new Pagination<AssCircleMsgVo>();
		pag.setDatas(voList);
		pag.setCurPage(pagination.getCurPage());
		pag.setStart(pagination.getStart());
		pag.setTotalRow(pagination.getTotalRow());
		pag.setPageSize(pagination.getPageSize());
		pag.setToPage(pagination.getToPage());
		return pag;
	}

	@Override
	public int cancelPraise(Long id, Long userId) throws AppCircleException {
		return appCircleProducer.cancelPraise(id,userId);
	}
}
