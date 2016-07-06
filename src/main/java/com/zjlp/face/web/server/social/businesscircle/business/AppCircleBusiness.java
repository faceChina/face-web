package com.zjlp.face.web.server.social.businesscircle.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssCircleMsgVo;
import com.zjlp.face.web.exception.ext.AppCircleException;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

/**
 * 生意圈
* @ClassName: AppCircleProducer
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年5月8日 下午1:42:07
*
 */
public interface AppCircleBusiness {
	
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
	AssCircleMsgVo getAppCircleById(Long id) throws AppCircleException;
	
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
	com.zjlp.face.util.page.Pagination<AssCircleMsgVo> findMyAppCircleMsgVo(Long userId,com.zjlp.face.util.page.Pagination<AppCircleMsgVo> pagination) throws AppCircleException;
	
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
	List<AssCircleMsgVo> queryAppCircleMsgVo(Long userId,Long msgId) throws AppCircleException;
	
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
	
	Pagination<AssCircleMsgVo> queryAppCircleMsgVo(Long userId,Long upMsgId,Long downMsgId,com.zjlp.face.util.page.Pagination<AppCircleMsgVo> pagination) throws AppCircleException;
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
