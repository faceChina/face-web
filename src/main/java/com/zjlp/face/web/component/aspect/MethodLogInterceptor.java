package com.zjlp.face.web.component.aspect;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;


/**
 * 方法日志打印
 * 
 * @ClassName: MethodLogInterceptor
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月17日 上午10:29:58
 */
public class MethodLogInterceptor implements MethodInterceptor {
	private Logger log = Logger.getLogger(getClass());

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] params = invocation.getArguments();
		StringBuilder info = new StringBuilder();
		this.printBeginLog(info, method, params);
		Object result = null;
		try {
			result = invocation.proceed();
		} catch (Exception e) {
			this.printErrLog(info, method, e);
			throw e;
		}
		this.printEndLog(info, method, result);
		return result;
	}

	/**
	 * 异常日志
	 * 
	 * @Title: printErrLog
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param info
	 *            容器
	 * @param method
	 *            方法体
	 * @param e
	 *            异常体
	 * @date 2015年3月17日 上午10:30:26
	 * @author lys
	 */
	private void printErrLog(StringBuilder info, Method method, Exception e) {
		info.delete(0, info.length());
		info.append("Method ").append(method.getName())
				.append(" has an Exception!");
		log.error(info, e);
	}

	/**
	 * 方法终止日志
	 * 
	 * @Title: printEndLog
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param info
	 *            容器
	 * @param method
	 *            方法体
	 * @param result
	 *            执行结果
	 * @date 2015年3月17日 上午10:30:49
	 * @author lys
	 */
	private void printEndLog(StringBuilder info, Method method, Object result) {
		info.delete(0, info.length());
		info.append("Method ").append(method.getName()).append(" is end");
		if (null == result) {
			info.append(", The result is ").append(result);
		}
		info.append(".");
		log.info(info);
	}

	/**
	 * 方法开始日志
	 * 
	 * @Title: printBeginLog
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param info
	 *            容器
	 * @param method
	 *            方法体
	 * @param params
	 *            方法参数
	 * @date 2015年3月17日 上午10:31:24
	 * @author lys
	 */
	private void printBeginLog(StringBuilder info, Method method,
			Object[] params) {
		info.append("Method ").append(method.getName());
		if (null != params && params.length > 0) {
			info.append("[");
			for (Object object : params) {
				if (null != object) {
					info.append(object.getClass()).append(",");
				} else {
					info.append("null").append(",");
				}
				
			}
			info.delete(info.length() - 1, info.length()).append("]");
		}
		log.info(info.append("is begin."));
	}

}
