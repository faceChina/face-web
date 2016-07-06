package com.zjlp.face.web.server.product.im.init;

import java.util.Random;

import org.apache.log4j.Logger;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.util.ImConstantsUtil;
/**
 * 匿名用户建造者
 * @ClassName: ImAnonymousUserBuilder 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2014年10月14日 上午11:32:35
 */
public class ImAnonymousUserBuilder {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	public static final String ANONYMOUS_BASE_NAME="ANONYMOUS_";
	
	private ImAnonymousUserBuilder(){
	}
	
	private static ImAnonymousUserBuilder builder  = new ImAnonymousUserBuilder();

	public static ImAnonymousUserBuilder getInstance(){

		return builder;
	}
	
	public void build(Integer size){
		if (null == size || 0 > size) {
			throw new IllegalArgumentException("创建参数错误！");
		}
		ImAnonymousUserPool pool = ImAnonymousUserPool.getInstance();
		ImUser value = null;
		for (int i = 0; i < size.intValue(); i++) {
			value = new ImUser();
			value.setStates(1);
		    int t = new Random().nextInt(99999);
		    if(t < 10000) t+=10000;
			value.setNickname("匿名用户"+t);
			value.setRemoteId(null);
			value.setType(ImConstantsUtil.REMOTE_TYPE_ANONYMOUS);
			value.setUserName(ANONYMOUS_BASE_NAME+t);
			String userPwd = PropertiesUtil.getContexrtParam("IM_INIT_ANONYMOUS_USER_PWD");
			value.setUserPwd(userPwd);
			pool.putUser(value, ImAnonymousUserPool.STATES_AVAILABLE);
			if (_logger.isDebugEnabled()) {
				_logger.debug("[IM用户创建]开始创建第"+pool.size()+"个用户");
			}
		}
		_logger.info("[IM用户创建]操作成功，当前已创建用户总数："+size);

	}
}
