package com.zjlp.face.web.server.social.businesscircle.producer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.AppCircleException;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;
import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;
import com.zjlp.face.web.server.social.businesscircle.producer.AppCircleProducer;
import com.zjlp.face.web.server.social.businesscircle.service.AppCircleMsgService;
import com.zjlp.face.web.server.social.businesscircle.service.AppCommentService;
import com.zjlp.face.web.server.social.businesscircle.service.AppPraiseService;
import com.zjlp.face.web.server.social.businesscircle.service.AppUserMsgRelationService;
import com.zjlp.face.web.server.social.businesscircle.service.OfRosterService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;

@Repository("appCircleProducer")
public class AppCircleProducerImpl implements AppCircleProducer {
	
	@Autowired
	private AppCircleMsgService appCircleMsgService;
	@Autowired
	private AppCommentService appCommentService;
	@Autowired
	private AppPraiseService appPraiseService;
	@Autowired
	private OfRosterService ofRosterService;
	@Autowired
	private UserProducer  userProducer;
	@Autowired
	private AppUserMsgRelationService appUserMsgRelationService;
	@Autowired(required=false)
	private ImageService imageService;

	@Override
	public Long addAppCircleMsg(AppCircleMsg appCircle,String username)
			throws AppCircleException {
			AssertUtil.notNull(appCircle.getUserId(),"Param[userId] can not be null");
			Date date = new Date();
			appCircle.setCreateTime(date);
			Long id = appCircleMsgService.add(appCircle);
			if (StringUtils.isNotEmpty(appCircle.getImg1())) {
				String img = _savePicture(appCircle.getImg1(),appCircle.getUserId(),id);
				appCircle.setId(id);
				appCircle.setImg1(img);
				appCircleMsgService.edit(appCircle);
			}
			return  id;
	}

	@Override
	public void deleteAppCircleMsg(Long id, Long userId)
			throws AppCircleException {
		try {
			AssertUtil.notNull(id,"Param[id] can not be null");
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			
			AppCircleMsgVo appCircleMsgVo = appCircleMsgService.getAppCircleById(id);
			if(null == appCircleMsgVo || !userId.equals(appCircleMsgVo.getUserId())){
				throw new AppCircleException("Delete AppCircleMsg Fail ");
			}
		    /**删除消息评论内容**/
		    appCommentService.deleteByCircleMsgId(id);
		    /**删除消息点赞信息**/
		    appPraiseService.deleteByCircleMsgId(id);
		    /**删除消息主体**/
		    appCircleMsgService.delete(id);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public AppCircleMsgVo getAppCircleById(Long id) throws AppCircleException {
		try {
			AssertUtil.notNull(id,"Param[id] can not be null");
			AppCircleMsgVo vo = appCircleMsgService.getAppCircleById(id);
			if (null == vo){
				return null;
			}
			List<AppPraise> pl = appPraiseService.queryAppPraise(vo.getId());
			List<AppComment> cl = appCommentService.queryAppComment(vo.getId());
			vo.setPraiseList(pl);
			vo.setCommentList(cl);
			return vo;
			
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public Pagination<AppCircleMsgVo> findMyAppCircleMsgVo(Long userId,Pagination<AppCircleMsgVo> pagination)
			throws AppCircleException {
		try {
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			return appCircleMsgService.findMyAppCircleMsgVo(userId,pagination);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
		
	}

	@Override
	public List<AppCircleMsgVo> queryAppCircleMsgVo(Long userId,Long msgId,List<Long> idList)
			throws AppCircleException {
		try {
			if (null == idList || idList.size() <= 0) {
				idList = new ArrayList<Long>();
				List<AppUserMsgRelation> list = appUserMsgRelationService.queryAppUserMsgRelation(userId,msgId,20);
				for (AppUserMsgRelation appUserMsgRelation : list) {
					idList.add(appUserMsgRelation.getCircleMsgId());
				}
			}
			List<AppCircleMsgVo> circle = new ArrayList<AppCircleMsgVo>();
			for (Long id : idList) {
				if (null != id){
					AppCircleMsgVo vo = this.getAppCircleById(id);
					if(null != vo){
						circle.add(vo);
					}
				}
			}
			return circle;
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public Long addAppPraise(AppPraise appPraise) throws AppCircleException {
		try {
			AssertUtil.notNull(appPraise.getCircleMsgId(),"Param[circleMsgId] can not be null");
			AssertUtil.notNull(appPraise.getUserId(),"Param[userId] can not be null");
			return appPraiseService.add(appPraise);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public Long addAppComment(AppComment appComment) throws AppCircleException {
		try {
			AssertUtil.notNull(appComment.getCircleMsgId(),"Param[circleMsgId] can not be null");
			AssertUtil.notNull(appComment.getSenderId(),"Param[senderId] can not be null");
			Date date = new Date();
			appComment.setCreateTime(date);
			return appCommentService.add(appComment);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public int deleteAppComment(Long id, Long userId)
			throws AppCircleException {
		try {
			AssertUtil.notNull(id,"Param[id] can not be null");
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			return appCommentService.delete(id, userId);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public Pagination<AppCircleMsgVo> queryAppCircleMsgVo(Long userId,Long upMsgId,Long downMsgId,
			Pagination<AppCircleMsgVo> pagination)
			throws AppCircleException {
		try {
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			User user = userProducer.getUserById(userId);
			Pagination<AppCircleMsgVo> pagelist = appCircleMsgService.queryAppCircleMsgVo(user.getLoginAccount(), upMsgId,downMsgId, pagination);
			for (AppCircleMsgVo vo : pagelist.getDatas()) {
				List<AppPraise> pl = appPraiseService.queryAppPraise(vo.getId());
				List<AppComment> cl = appCommentService.queryAppComment(vo.getId());
				vo.setPraiseList(pl);
				vo.setCommentList(cl);
			}
			return pagelist;
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
	}

	@Override
	public List<OfRoster> queryRosterByUserName(String userName,
			String excludeName) {
		return ofRosterService.queryRosterByUserName(userName, excludeName);
	}

	@Override
	public Long addAppUserMsgRelation(Long userId,Long circleMsgId)throws AppCircleException {
		
		try {
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			AssertUtil.notNull(circleMsgId,"Param[circleMsgId] can not be null");
			AppUserMsgRelation appUserMsgRelation = new AppUserMsgRelation();
			appUserMsgRelation.setUserId(userId);
			appUserMsgRelation.setCircleMsgId(circleMsgId);
			appUserMsgRelation.setCreateTime(new Date());
			return appUserMsgRelationService.add(appUserMsgRelation);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
		

	}

	@Override
	public void deleteAppUserMsgRelationByUserId(Long userId) {
		
		appUserMsgRelationService.deleteByUserId(userId);
	}

	@Override
	public void deleteAppUserMsgRelationByCircleMsgId(Long circleMsgId) {
		
		appUserMsgRelationService.deleteByCircleMsgId(circleMsgId);
	}

	@Override
	public List<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Long startId,int resultNumber) {
		
		return appUserMsgRelationService.queryAppUserMsgRelation(userId, startId ,resultNumber);
	}

	@Override
	public Pagination<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,
			Pagination<AppUserMsgRelation> pagination) {
		
		return appUserMsgRelationService.queryAppUserMsgRelation(userId, pagination);
	}

	@Override
	public AppPraise getAppPraise(Long id) {
		return appPraiseService.getAppPraise(id);
	}

	@Override
	public AppComment getAppComment(Long id) {
		return appCommentService.getAppComment(id);
	}

	@Override
	public int cancelPraise(Long id, Long userId)throws AppCircleException {
		
		try {
			AssertUtil.notNull(id,"Param[id] can not be null");
			AssertUtil.notNull(userId,"Param[userId] can not be null");
			return appPraiseService.cancelPraise(id, userId);
		} catch (Exception e) {
			throw new AppCircleException(e);
		}
		
	}
	
	/***
	 * 生意圈图片上传到TFS
	* @Title: _savePicture
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param img
	* @param userId
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月1日 下午9:49:13
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	private String _savePicture(String img,Long userId,Long circleId) {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		if (null == img || "".equals(img)){
			return null;
		}
		String[] imgArr = img.split(";");
		if (imgArr.length < 1){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String string : imgArr) {
	    	 //上传图片到TFS
        	//String zoomSizes = PropertiesUtil.getContexrtParam("shopLogoFile");
        	//AssertUtil.hasLength(zoomSizes, "imgConfig.properties还未配置shopLogoFile字段");
			sb.append(string).append(",");
		}
		FileBizParamDto dto = new FileBizParamDto();
		dto.setImgData(sb.toString());
		dto.setZoomSizes(null);
		dto.setUserId(userId);
		dto.setTableName("circle");
		dto.setTableId(String.valueOf(circleId));
		dto.setCode(ImageConstants.CIRCLE_PHOTO_FILE);
		dto.setFileLabel(1);
		list.add(dto);
        String flag = imageService.addOrEdit(list);
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
        StringBuilder imgs = new StringBuilder();
        FileBizParamDto fileBizParamDto = fbpDto.get(0);
        String imgPath = fileBizParamDto.getImgData();
        String[] imgPathArry = imgPath.split(",");
    	for (String temp : imgPathArry) {
    		imgs.append(temp).append(";");
		}
            	
        return imgs.toString();
	}
}
