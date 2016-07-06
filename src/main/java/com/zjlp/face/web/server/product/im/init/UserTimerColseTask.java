package com.zjlp.face.web.server.product.im.init;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.zjlp.face.web.server.product.im.domain.ImUser;

public class UserTimerColseTask extends TimerTask {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	private ImUser imUser;
	
	UserTimerColseTask(ImUser imUser){
		this.imUser = imUser;
	}

	@Override
	public void run() {
		if (null != imUser) {
			ImAnonymousUserPool.getInstance().colseAnonymousUser(imUser);
				_logger.info("[IM用户关闭]开始关闭第"+imUser.getNickname()+"个用户");
		}
	}

}
