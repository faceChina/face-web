package com.zjlp.face.web.server.social.businesscircle.producer;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AppCircleException;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;
import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

/**
 * APP 生意圈功能
* @ClassName: AppCircleProducer
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年5月8日 下午1:42:07
*
 */
public interface AppCircleProducer {
	
	/**
	 * 发布一条生意圈消息
	* @Title: addAppCircleMsg
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param appCircle
	* @return
	* @return Long    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:42:46
	 */
	Long addAppCircleMsg(AppCircleMsg appCircle,String username) throws AppCircleException;
	
	/**
	 * 删除生意圈消息
	* @Title: deleteAppCircleMsg
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:43:12
	 */
	void deleteAppCircleMsg(Long id,Long userId) throws AppCircleException;
	
	/**
	 * 获取一条生意圈消息
	* @Title: getAppCircleById
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return
	* @return AppCircleMsgVo    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:43:58
	 */
	AppCircleMsgVo getAppCircleById(Long id) throws AppCircleException;
	
	/**
	*  获取我的生意圈
	* @Title: findMyAppCircleMsgVo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return
	* @return Pagination<AppCircleMsgVo>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:46:56
	 */
	Pagination<AppCircleMsgVo> findMyAppCircleMsgVo(Long userId,Pagination<AppCircleMsgVo> pagination) throws AppCircleException;
	
	/**
	 * 
	* @Title: queryAppCircleMsgVo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return
	* @throws AppCircleException
	* @return List<AppCircleMsgVo>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:50:36
	 */
	List<AppCircleMsgVo> queryAppCircleMsgVo(Long userId,Long msgId,List<Long> idList) throws AppCircleException;
	
	/**
	 * 新增点赞信息
	* @Title: addAppPraise
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param appPraise
	* @return
	* @throws AppCircleException
	* @return Long    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:51:03
	 */
	Long addAppPraise(AppPraise appPraise) throws AppCircleException;
	/**
	 * 新增评论
	* @Title: addAppComment
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param appComment
	* @return
	* @throws AppCircleException
	* @return Long    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:51:55
	 */
	Long addAppComment(AppComment appComment) throws AppCircleException;
	
	/**
	 * 删除评论
	* @Title: deleteAppComment
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @throws AppCircleException
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月8日 下午1:52:02
	 */
	int deleteAppComment(Long id,Long userId) throws AppCircleException;
	
	Pagination<AppCircleMsgVo> queryAppCircleMsgVo(Long userId,Long upMsgId,Long downMsgId,Pagination<AppCircleMsgVo> pagination) throws AppCircleException;
	
	/**
	 * 查询我的好友
	* @Title: queryRosterByUserName
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userName
	* @return
	* @return List<OfRoster>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月11日 上午11:50:58
	 */
	List<OfRoster> queryRosterByUserName(String userName,String excludeName);
	/**
	 * 新增用户能查看的生意圈消息
	* @Title: addAppUserMsgRelation
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @param circleMsgId
	* @throws AppCircleException
	* @return Long    返回类型
	* @author wxn  
	* @date 2015年5月15日 上午10:21:14
	 */
	Long addAppUserMsgRelation(Long userId,Long circleMsgId) throws AppCircleException;
	/**
	 * 删除该用户能查看的所有生意圈消息
	* @Title: deleteAppUserMsgRelationByUserId
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月15日 上午10:21:46
	 */
	void deleteAppUserMsgRelationByUserId(Long userId);
	/**
	 * 删除查看这条消息的所有用户
	* @Title: deleteAppUserMsgRelationByCircleMsgId
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param circleMsgId
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月15日 上午10:22:14
	 */
	void deleteAppUserMsgRelationByCircleMsgId(Long circleMsgId);
	/**
	 * 查找该用户能查看的最新消息
	* @Title: queryAppUserMsgRelation
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @param resultNumber
	* @return
	* @throws AppCircleException
	* @return List<AppUserMsgRelation>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月15日 上午10:23:08
	 */
	List<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Long startId,int resultNumber) throws AppCircleException;
	
	Pagination<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Pagination<AppUserMsgRelation> pagination) throws AppCircleException;
	/**
	 * 获取点赞
	* @Title: getAppPraise
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return AppPraise    返回类型
	* @author wxn  
	* @date 2015年5月19日 上午11:12:34
	 */
	AppPraise getAppPraise(Long id);
	/**
	 * 获取评论
	* @Title: getAppComment
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @return
	* @return AppComment    返回类型
	* @throws
	* @author wxn  
	* @date 2015年5月19日 上午11:12:22
	 */
	AppComment getAppComment(Long id);
	/**
	 * 取消点赞
	* @Title: cancelPraise
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param id
	* @param userId
	* @throws AppCircleException
	* @return int    返回类型
	* @author wxn  
	* @date 2015年5月19日 上午11:12:06
	 */
	int cancelPraise(Long id,Long userId)throws AppCircleException;
	
}
