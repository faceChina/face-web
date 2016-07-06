package com.zjlp.face.web.http.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.zjlp.face.jredis.RedisInitializeListener;
//@Component
public class RedisApplicationListener  implements ApplicationListener<ContextRefreshedEvent>{

	/**
	 * 注意:
	 * 	  Spring启动时会存在两个容器系统会存在两个容器，root application context
	 * 和 projectName-servlet context（作为root application context的子容器）。
	 * 导致onApplicationEvent()执行2次
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/**
		 * 为了避免上面提到的问题，我们可以只在root application context初始化完成后调用逻辑代码
		 */
		if(null == event.getApplicationContext().getParent()){	
			new RedisInitializeListener().contextInitialized(null);
		}
	}
	
}
