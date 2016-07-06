package com.zjlp.face.web.server.product.im.init;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.product.im.domain.ImUser;

/**
 * 匿名用户池
 * @ClassName: ImAnonymousUserPool
 * @Description: (这里用一句话描述这个类的作用)
 * @author dzq
 * @date 2014年10月14日 上午11:33:19
 */
public class ImAnonymousUserPool extends HashMap<ImUser, String> {
	
	private static final long serialVersionUID = -3006505080349289439L;
	
	private Logger _logger = Logger.getLogger(this.getClass());

	/** 池状态 */
	public static final String ANONYMOUS_KEY = "ANONYMOUS";

	public static final String STATES_AVAILABLE = "AVAILABLE";
	/** 池参数 */
	private int initialUserNum = 100;// 连接池初始连接数

	private int maxUserNum = 2000;// 连接池最大连接数

	private int incrementalUsers = 50;// 每次动态添加的连接数
	
	//用户最多使用30分钟
	private static int USER_CLOSE_TIME=20*3600*1000;

	private ImAnonymousUserPool(){
		String initialUserNum = PropertiesUtil.getContexrtParam("IM_USER_POOL_INITIAL_NUM");
		String maxUserNum = PropertiesUtil.getContexrtParam("IM_USER_POOL_MAX_NUM");
		String incrementalUsers = PropertiesUtil.getContexrtParam("IM_USER_POOL_INCREMENTAL_NUM");
		this.initialUserNum = Integer.valueOf(null!=initialUserNum?initialUserNum:String.valueOf(this.initialUserNum));
		this.maxUserNum = Integer.valueOf(null!=maxUserNum?maxUserNum:String.valueOf(this.maxUserNum));
		this.incrementalUsers = Integer.valueOf(null!=incrementalUsers?incrementalUsers:String.valueOf(this.incrementalUsers));
	}

	static ImAnonymousUserPool pool = new ImAnonymousUserPool();

	public static ImAnonymousUserPool getInstance() {
		return pool;
	}

	public void putUser(ImUser imUser, String value) {
		pool.put(imUser, value);
	}

	public ImUser getUser() throws Exception {
		ImUser imUser = null;;
		try {
			if (null == pool) {
				this.initialize();
			}
			imUser = this.getFreeUser();
			int count = 0;
			while (null == imUser && count<10) {
				try {
					_logger.info("开始第"+count+"次重试!");
					_logger.info("当前无可用的用户,开始等待!");
					this.wait(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				imUser = this.getFreeUser();
				count++;
			}
			//定时关闭User
			UserTimer timer = new UserTimer();  
			timer.schedule(new UserTimerColseTask(imUser), USER_CLOSE_TIME);
			return imUser;
		} catch (Exception e) {
			throw e;
		}
	}

	/* 函数，得到一个可用连接 */
	private ImUser getFreeUser() {
		ImUser imUser = null;
		// 查找一个可用连接
		imUser = this.findFreeUser();
		// 如果未找到可用连接，就建立一些新的连接，再次查找
		if (null == imUser) {
			_logger.info("当前用户不足,继续创建"+this.incrementalUsers+"个用户!");
			this.create(this.incrementalUsers);
			// 再次查找
			imUser = this.findFreeUser();
		}
		return imUser;
	}

	/**
	 * 查找一个可用的用户
	 * 
	 * @Title: findFreeUser
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return
	 * @date 2014年10月20日 下午2:38:27
	 * @author dzq
	 */
	private ImUser findFreeUser() {
		for (Entry<ImUser, String> entry : pool.entrySet()) {
			synchronized (entry) {
				if ("AVAILABLE".equals(entry.getValue())) {
					entry.setValue("NOTAVAILABLE");
					return (ImUser) entry.getKey();
				}
			}
		}
		return null;
	}
	/**
	 * 初始化
	 * @Title: init 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2014年10月20日 下午3:29:06  
	 * @author dzq
	 */
	public void initialize(){
		this.create(this.initialUserNum);
	}

	/**
	 * 创建用户
	 * 
	 * @Title: create
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param num
	 * @date 2014年10月20日 下午2:30:50
	 * @author dzq
	 */
	private void create(int num) {
		/* 创建用户需要首先检查当前用户数是否已经超出连接池最大用户数 */
		// 检查用户数量
		if (pool.size() >= this.maxUserNum) {
			return;
		}
		// 创建新的用户
		ImAnonymousUserBuilder.getInstance().build(num);
	}

	/**
	 * 等待(暂时无可用用户，进入等待队列等待m秒，再试)
	 * @Title: wait
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param mSecond
	 * @throws InterruptedException
	 * @date 2014年10月20日 下午2:38:04
	 * @author dzq
	 */
	private void wait(int mSecond) throws InterruptedException {
		Thread.sleep(mSecond);
	}

	/**
	 * 刷新连接池
	 * 
	 * @Title: refresh
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @date 2014年10月20日 下午2:37:21
	 * @author dzq
	 */
	public void refresh() {
		if (null != pool) {
			pool = null;
		}
		ImAnonymousUserBuilder.getInstance().build(this.initialUserNum);
	}

	/**
	 * 关闭连接池
	 * @Title: colseAnonymousUser
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param imUser
	 * @date 2014年10月20日 下午2:37:33
	 * @author dzq
	 */
	public void colseAnonymousUser(ImUser imUser) {
		if(null == imUser)return ;
		for (Entry<ImUser, String> entry : pool.entrySet()) {
			synchronized (entry) {
				if (entry.getKey().equals(imUser)) {
					entry.setValue("AVAILABLE");
				}
			}
		}
	}
	
	
	public void colseAnonymousUserByName(String nickname) {
		if(null == nickname || "".equals(nickname))return ;
		for (Entry<ImUser, String> entry : pool.entrySet()) {
			synchronized (entry) {
				if (entry.getKey().getNickname().equals(nickname)) {
					entry.setValue("AVAILABLE");
				}
			}
		}
	}

	/**
	 * 打印连接池状态
	 * 
	 * @Title: printPoolStates
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @date 2014年10月20日 下午2:37:46
	 * @author dzq
	 */
	public void printPoolStates() {
		for (Entry<ImUser, String> entry : pool.entrySet()) {
			synchronized (entry) {
				System.out.println("key : " + entry.getKey() + " Value : "
						+ entry.getValue()  );
			}
		}
	}
}
