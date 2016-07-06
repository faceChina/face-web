package com.zjlp.face.web.component.dubbo;

/**
 * 通知接口
 * 
 * @ClassName: Notify
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年10月16日 下午7:49:08
 */
public interface Notify {

	/**
	 * 正常返回通知接口
	 * 
	 * @Title: onReturn
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param objs
	 *            参数
	 * @date 2015年10月16日 下午7:49:45
	 * @author lys
	 */
	public void onReturn(Object... objs);

	/**
	 * 抛出异常的处理
	 * 
	 * @Title: onThrow
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param ex
	 *            异常信息
	 * @param objs
	 *            处理信息
	 * @date 2015年10月16日 下午8:04:34
	 * @author lys
	 */
	public void onThrow(Throwable ex, Object... objs);
}
