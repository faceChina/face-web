package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssCircleMsgVo;
import com.zjlp.face.web.server.social.businesscircle.business.AppCircleBusiness;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller
@RequestMapping({"/assistant/ass/circle/"})
public class CircleAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private AppCircleBusiness appCircleBusiness;
	@Autowired
	private UserBusiness userBusiness;
	
	/**
	 * 删除生意圈消息
	* @Title: deleteCircleMsg
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午4:55:35
	 */
	@RequestMapping(value="my/msg/delete")
	@ResponseBody
	public String deleteCircleMsg(@RequestBody JSONObject jsonObj){
		
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = jsonObj.getLong("id");
			if (null == id) {
				return null;
			}
			Long userId = super.getUserId();
			appCircleBusiness.deleteAppCircleMsg(id, userId);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("删除生意圈消息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 发布生意圈消息
	* @Title: addCircleMsg
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:56:10
	 */
	@RequestMapping(value="my/msg/add")
	@ResponseBody
	public String addCircleMsg(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long userId = super.getUserId();
			AppCircleMsg appCircle = JsonUtil.toBean(jsonObj.toString(), AppCircleMsg.class);
			appCircle.setUserId(userId);
			String userName = getLoginUser().getUsername();
			Long id = appCircleBusiness.addAppCircleMsg(appCircle,userName);
			Map<String,Long> map = new HashMap<String, Long>();
			map.put("id", id);
			return outSucceed(map, false, "");
		} catch (Exception e) {
			_logger.error("发布生意圈消息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 查询我的生意圈消息
	* @Title: getMyCircleMsg
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @param pagination
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:56:30
	 */
	@RequestMapping(value="my/msg/query")
	@ResponseBody
	public String getMyCircleMsg(@RequestBody JSONObject jsonObj,Pagination<AppCircleMsgVo> pagination){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);

			Long userId = super.getUserId();
			Pagination<AssCircleMsgVo> pag = new Pagination<AssCircleMsgVo>();
			pag = appCircleBusiness.findMyAppCircleMsgVo(userId, pagination);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("curPage", pag.getCurPage());
			dataMap.put("start", pag.getEnd());
			dataMap.put("pageSize", pag.getPageSize());
			dataMap.put("totalRow", pag.getTotalRow());
			dataMap.put("msgList", pag.getDatas());
			 return outSucceed(dataMap,false,"");
		} catch (Exception e) {
			_logger.error("发布生意圈消息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 查询别人生意圈消息
	* @Title: getMyCircleMsg
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @param pagination
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:56:30
	 */
	@RequestMapping(value="friend/msg/query")
	@ResponseBody
	public String getFirendCircleMsg(@RequestBody JSONObject jsonObj,Pagination<AppCircleMsgVo> pagination){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object idObje = jsonObj.get("userId");
			if (null == idObje && "".equals(idObje)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long userId =Long.parseLong(idObje.toString());
			// 分页信息
			pagination = super.initPagination(jsonObj);

			Pagination<AssCircleMsgVo> pag = new Pagination<AssCircleMsgVo>();
			pag = appCircleBusiness.findMyAppCircleMsgVo(userId, pagination);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("curPage", pag.getCurPage());
			dataMap.put("start", pag.getEnd());
			dataMap.put("pageSize", pag.getPageSize());
			dataMap.put("totalRow", pag.getTotalRow());
			dataMap.put("msgList", pag.getDatas());
			if (jsonObj.containsKey(START)) {
				int start = jsonObj.getInt(START);
				if (start == 0) {
					User user = this.userBusiness.getUserById(userId);
					if (user != null) {
						dataMap.put("circlePictureUrl", user.getCirclePictureUrl());
						dataMap.put("headimgurl", user.getHeadimgurl());
					}
				}
			}
			 return outSucceed(dataMap,false,"");
		} catch (Exception e) {
			_logger.error("查询我的生意圈消息", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 查找最新的生意圈消息 
	* @Title: findCircle
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月13日 下午4:06:55
	 */
	@RequestMapping(value="find/new/msg")
	@ResponseBody
	public String findCircle(@RequestBody JSONObject jsonObj){
		
		if (null == jsonObj) {
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		}
		Object idObje = jsonObj.get("id");
		if (null == idObje && "".equals(idObje)){
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		}
		Long msgId = Long.parseLong(idObje.toString());
		Long userId = super.getUserId();
		List<AssCircleMsgVo> list = new ArrayList<AssCircleMsgVo>();
		list = appCircleBusiness.queryAppCircleMsgVo(userId, msgId);
		return outSucceed(list,false,"");
	}
	/**
	 * 查找生意圈消息带翻页
	* @Title: findCircle
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月13日 下午4:06:55
	 */
	@RequestMapping(value="find/pag/msg")
	@ResponseBody
	public String findCirclePagMsg(@RequestBody JSONObject jsonObj,Pagination<AppCircleMsgVo> pagination){
		
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Object upMsgIdObj = jsonObj.get("upMsgId");
			Long upMsgId = null;
			if (null != upMsgIdObj && !"".equals(upMsgIdObj)){
				upMsgId = Long.parseLong(upMsgIdObj.toString());
			}
			Object downMsgIdObj = jsonObj.get("downMsgId");
			Long downMsgId = null;
			if (null != downMsgIdObj && !"".equals(downMsgIdObj)){
				downMsgId = Long.parseLong(downMsgIdObj.toString());
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);

			Long userId = super.getUserId();
			Pagination<AssCircleMsgVo> pag = new Pagination<AssCircleMsgVo>();
			pag = appCircleBusiness.queryAppCircleMsgVo(userId,upMsgId, downMsgId, pagination);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("curPage", pag.getCurPage());
			dataMap.put("start", pag.getEnd());
			dataMap.put("pageSize", pag.getPageSize());
			dataMap.put("totalRow", pag.getTotalRow());
			dataMap.put("msgList", pag.getDatas());
			 return outSucceed(dataMap,false,"");
		} catch (Exception e) {
			_logger.error("查找生意圈消息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 生意圈消息详情
	* @Title: getOneCircleInfo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:56:49
	 */
	@RequestMapping(value="details")
	@ResponseBody
	public String getOneCircleInfo(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = jsonObj.getLong("id");
			AssCircleMsgVo vo = appCircleBusiness.getAppCircleById(id);
			if (vo == null) {
				vo = new AssCircleMsgVo();
			}
			return outSucceed(vo,false,"");
		} catch (Exception e) {
			_logger.error("查看生意圈消息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 点赞
	* @Title: givePraise
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:57:19
	 */
	@RequestMapping(value="praise/give")
	@ResponseBody
	public String givePraise(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = jsonObj.getLong("id");
			if (null == id) {
				return null;
			}
			Long userId = super.getUserId();
			AppPraise appPraise = new AppPraise();
			appPraise.setUserId(userId);
			appPraise.setCircleMsgId(id);
			Long praiseId = appCircleBusiness.addAppPraise(appPraise);
			Map<String,Long> map = new HashMap<String, Long>();
			map.put("id", praiseId);
			return outSucceed(map, false, "");
		} catch (Exception e) {
			_logger.error("点赞失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 发布评论
	* @Title: addComment
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:57:25
	 */
	@RequestMapping(value="comment/add")
	@ResponseBody
	public String addComment(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj) {
				return outFailure(1, "");
			}
			Long userId = super.getUserId();
			AppComment appComment = JsonUtil.toBean(jsonObj.toString(), AppComment.class);
			appComment.setSenderId(userId);
			Long id = appCircleBusiness.addAppComment(appComment);
			Map<String,Long> map = new HashMap<String, Long>();
			map.put("id", id);
			return outSucceed(map, false, "");
		} catch (Exception e) {
			_logger.error("发布生意圈评论失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 删除评论
	* @Title: deleteComment
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午5:57:41
	 */
	@RequestMapping(value="comment/delete")
	@ResponseBody
	public String deleteComment(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj) {
				return outFailure(1, "");
			}
			Long id = jsonObj.getLong("id");
			if (null == id) {
				return null;
			}
			Long userId = super.getUserId();
			appCircleBusiness.deleteAppComment(id, userId);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("删除评论失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 取消点赞
	* @Title: cancelPraise
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @return String    返回类型
	* @author wxn  
	* @date 2015年5月19日 上午11:22:30
	 */
	@RequestMapping(value="praise/cancel")
	@ResponseBody
	public String cancelPraise(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = jsonObj.getLong("id");
			if (null == id) {
				return null;
			}
			Long userId = super.getUserId();
			int i = appCircleBusiness.cancelPraise(id, userId);
			if (i < 1){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}else{
				return outSucceedByNoData();
			}
			
		} catch (Exception e) {
			_logger.error("取消点赞失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

}
