package com.zjlp.face.web.util.redis;
//package com.jzwgj.util.redis;
//
//import org.apache.log4j.Logger;
//
//import com.jzwgj.redis.jredisClient.helper.StringRedisHelper;
//import com.jzwgj.redis.jredisClient.proxy.RedisClusterClient;
//import com.jzwgj.util.redis.abstr.AbstractRedisDaoSupport;
//import com.jzwgj.util.redis.log.RedisLog;
//
//public class RedisStringUtil {
//
//	private static Logger _logger = Logger.getLogger(RedisStringUtil.class);
//	
//	
//	public static <T> T get(String key){
//		try {
//			StringRedisHelper helper = RedisClusterClient.getStringClient();
//			T t = null;
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				t =  helper.get(key);
//				if (null != t) {
//					_logger.info(RedisLog.getString("Redis.hit","String", key));
//					return t;
//				}
//				_logger.info(RedisLog.getString("Redis.miss","String", key));
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return null;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static  <T> T  set(String key,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			T t = null;
//			StringRedisHelper helper = RedisClusterClient.getStringClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				t =  (T) abstractReidsDaoSupport.support();
//				boolean isPush = helper.set(key, t);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return t;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (T) abstractReidsDaoSupport.support();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T> T getAndSet(String key,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			T t = null;
//			StringRedisHelper helper = RedisClusterClient.getStringClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				t =  helper.get(key);
//				if (null != t) {
//					_logger.info(RedisLog.getString("Redis.hit","String", key));
//					return t;
//				}
//				_logger.info(RedisLog.getString("Redis.miss","String", key));
//				t =  (T) abstractReidsDaoSupport.support();
//				boolean isPush = helper.set(key, t);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return t;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (T) abstractReidsDaoSupport.support();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static  <T> T  set(String key, Integer timeout, AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			T t = null;
//			StringRedisHelper helper = RedisClusterClient.getStringClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				t =  (T) abstractReidsDaoSupport.support();
//				boolean isPush = helper.set(key, t);
//				helper.setExpire(key, timeout);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return t;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (T) abstractReidsDaoSupport.support();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T> T getAndSet(String key, Integer timeout ,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			T t = null;
//			StringRedisHelper helper = RedisClusterClient.getStringClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				t =  helper.get(key);
//				if (null != t) {
//					_logger.info(RedisLog.getString("Redis.hit","String", key));
//					return t;
//				}
//				_logger.info(RedisLog.getString("Redis.miss","String", key));
//				t =  (T) abstractReidsDaoSupport.support();
//				boolean isPush = helper.set(key, t);
//				helper.setExpire(key, timeout);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return t;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (T) abstractReidsDaoSupport.support();
//	}
//
//}
