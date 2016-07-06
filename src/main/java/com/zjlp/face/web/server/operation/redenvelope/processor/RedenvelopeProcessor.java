package com.zjlp.face.web.server.operation.redenvelope.processor;

import java.util.List;

import org.apache.log4j.Logger;

import com.zjlp.face.jredis.client.RedisHashesHelper;
import com.zjlp.face.jredis.client.RedisListHelper;
import com.zjlp.face.jredis.client.RedisStringHelper;
import com.zjlp.face.jredis.client.lock.RedisLock;
import com.zjlp.face.web.exception.ext.RedenvelopeException;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecordDto;
import com.zjlp.face.web.server.operation.redenvelope.domain.RedenvelopeVo;
import com.zjlp.face.web.server.user.user.domain.User;


/**
 * 红包处理器
 * 
 * @ClassName: RedenvelopeProcessor
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年10月13日 下午5:52:04
 */
public abstract class RedenvelopeProcessor {
	
	protected Logger log = Logger.getLogger(RedenvelopeProcessor.class);
//	@Autowired
	protected RedisLock redisLock = new RedisLock("common");
//	@Autowired
	protected RedisStringHelper wgjStringHelper = new RedisStringHelper("common");
//	@Autowired
	protected RedisListHelper wgjListHelper = new RedisListHelper("common");
//	@Autowired
	protected RedisHashesHelper wgjHashesHelper = new RedisHashesHelper("common");
	
	/** 红包锁 */
	protected static final String LOCK_KEY = "LOCK_KEY_";
	/** 红包个数  */
	protected static final String NUM ="NUM_";
	/** 可拆红包用户 */
	protected static final String GRAPUSER = "GRAPUSER_";
	/** 未领取红包池 */
	protected static final String GRAB_POOL = "GRAB_POOL_";
	/** 已领取红包池 */
	protected static final String OPENED_POOL = "OPENED_POOL_";
	
	/** 在池中尚未抢红包 */
	protected static final Long I_0 = 0L;
	/** 在池中已经抢红包 */
	protected static final Long I_1 = 1L;
	
	/**
	 * 是否还有红包没有拆
	 * @Title: has 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 红包id
	 * @return
	 * @date 2015年10月14日 下午12:04:57  
	 * @author lys
	 */
	protected boolean has(Long id){
		// 对该红包数进行加锁
		Integer count = wgjStringHelper.get(this.getNumkey(id));
		return null != count && Integer.valueOf(count.toString()) > 0;
	}
	
	protected String getLockKey(Long id){
		return LOCK_KEY+id;
	}
	
	protected String getNumkey(Long id){
		return NUM+id;
	}
	
	protected String getGrapkey(Long id){
		return GRAB_POOL+id;
	}
	
	protected String getOpenedkey(Long id){
		return OPENED_POOL+id;
	}
	
	protected String getUserkey(Long id){
		return GRAPUSER+id;
	}
	
	protected void printLog(Object...objects){
		if (null == objects || objects.length == 0) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (Object object : objects) {
			sb.append(String.valueOf(object));
		}
		log.info(sb);
	}
	
	/**
	 * 分包
	 * @Title: allocRedenvelope 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @date 2015年10月13日 下午5:59:49  
	 * @author lys
	 */
	public void allocRedenvelope(Long id) {
		throw new RedenvelopeException("This processor do not support the method.");
	}

    /**
     * 抢包
     * 
     * @Title: grabRedenvelope 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @return
     * @date 2015年10月13日 下午5:59:57  
     * @author lys
     */
	public Long grabRedenvelope(Long userId, Long id) {
		throw new RedenvelopeException("This processor do not support the method.");
	}

	/**
	 * 拆包
	 * 
	 * @Title: openRedenvelope 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年10月13日 下午6:00:07  
	 * @author lys
	 */
	public ReceiveRedenvelopeRecordDto openRedenvelope(User user, Long id) {
		throw new RedenvelopeException("This processor do not support the method.");
	}

	/**
	 * 查询明细
	 * 
	 * @Title: pushAll 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年10月13日 下午6:00:14  
	 * @author lys
	 */
	public List<RedenvelopeVo> pushAll() {
		throw new RedenvelopeException("This processor do not support the method.");
	}
}
