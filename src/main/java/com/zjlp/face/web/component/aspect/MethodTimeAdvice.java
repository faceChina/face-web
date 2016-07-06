package com.zjlp.face.web.component.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

public class MethodTimeAdvice implements MethodInterceptor {
	
	Logger _comLogger = Logger.getLogger("CommonServiceLog");
	/**
	 * 拦截要执行的目标方法
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		/**
		 *  打印参数信息
		 */
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		Object result = invocation.proceed();
		clock.stop(); // 计时结束

		Class<?>[] params = invocation.getMethod().getParameterTypes();
		String[] simpleParams = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			simpleParams[i] = params[i].getSimpleName();
		}
		if (0L <= clock.getTime() && clock.getTime() < 500L) {
		} else if (500L <= clock.getTime() && clock.getTime() < 1000) {
			_comLogger.info("警告！！！该方法执行耗费:" + clock.getTime() + " ms ["
					+ invocation.getThis().getClass().getName() + "."
					+ invocation.getMethod().getName() + "("
					+ StringUtils.join(simpleParams, ",") + ")] ");
		} else {
			_comLogger.info("严重警告！！！该方法执行耗费:" + clock.getTime() + " ms ["
					+ invocation.getThis().getClass().getName() + "."
					+ invocation.getMethod().getName() + "("
					+ StringUtils.join(simpleParams, ",") + ")] ");
		}
		return result;
	}

}
