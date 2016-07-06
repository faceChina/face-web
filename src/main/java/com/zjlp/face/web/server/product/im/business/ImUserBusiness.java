package com.zjlp.face.web.server.product.im.business;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;
import com.zjlp.face.web.server.product.im.domain.vo.ImUserVo;

/**
 * 聊天账户业务服务层
 * @ClassName: ImUserBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2014年10月10日 上午10:41:57
 */
public interface ImUserBusiness {
	
	/**
	 * 登录
	 * @Title: login 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imUser
	 * @return
	 * @throws Exception
	 * @date 2014年10月13日 上午10:55:21  
	 * @author dzq
	 */
	ImUserVo login(ImUser imUser) throws Exception;
	
	/**
	 * IM匿名用户登录
	 * @Title: loginAnonymout 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param session
	 * @return
	 * @throws Exception
	 * @date 2014年10月20日 上午11:54:56  
	 * @author dzq
	 */
	void loginAnonymout(HttpSession session) throws Exception;
	/**
	 * 查询最近联系人
	 * @Title: findFriendsListByUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param imUserId
	 * @return
	 * @date 2014年10月13日 下午2:17:03  
	 * @author dzq
	 */
	List<ImFriendsDto> findFriendsListByUserId(Long imUserId)throws Exception;
	/**
	 * 编辑用户状态
	 * @Title: editImUserStates 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param statesUserNormal
	 * @throws Exception
	 * @date 2014年10月14日 下午1:47:03  
	 * @author dzq
	 */
	void editImUserStates(Long userId, Integer states)throws Exception;
	
	
}
