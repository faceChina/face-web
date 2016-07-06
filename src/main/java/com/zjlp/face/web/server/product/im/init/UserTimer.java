package com.zjlp.face.web.server.product.im.init;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class UserTimer extends Timer {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	// 执行完成后3秒关闭此任务
	private static long COLSE_TIMER_DELAY = 3000;

	@Override
	public void schedule(TimerTask task, long delay) {
		long closedelay =delay+COLSE_TIMER_DELAY;
		try {
			super.schedule(task, delay);
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
		}finally{
			//重要 启动后必须关闭此定时器
			super.schedule(new Shutdown(this), closedelay);
		}
	}
	
	class Shutdown extends TimerTask {
		Timer timer = null;
		public Shutdown() {
		}
		public Shutdown(Timer mytimer) {
			timer = mytimer;
		}
		public void run() {
			// 使用这个方法退出任务
			timer.cancel();
			if (_logger.isDebugEnabled()) {
				_logger.debug("[IM定时器关闭]开始关闭"+timer.hashCode()+"定时器");
			}
		}

	}
	
}
