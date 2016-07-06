package com.zjlp.face.web.server.product.im.init;


/**
 * 初始化匿名用户加载器
 * @ClassName: ImAnonymousUserInitilize 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2014年10月14日 上午11:32:55
 */
public class ImAnonymousUserInitilize {
	
	static ImAnonymousUserInitilize initilize = new ImAnonymousUserInitilize();
	
	public static ImAnonymousUserInitilize getInstance(){
		return initilize;
	}
	
	/**
	 * 初始化
	 * @Title: InitializeUserPool 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2014年10月10日 上午10:53:02  
	 * @author dzq
	 */
	public void InitializeUserPool(){
		ImAnonymousUserPool.getInstance().initialize();
	}
}
