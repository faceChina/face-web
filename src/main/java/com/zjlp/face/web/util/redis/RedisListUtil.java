package com.zjlp.face.web.util.redis;
//package com.jzwgj.util.redis;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//import com.jzwgj.redis.jredisClient.helper.ListRedisHelper;
//import com.jzwgj.redis.jredisClient.proxy.RedisClusterClient;
//import com.jzwgj.util.redis.abstr.AbstractRedisDaoSupport;
//import com.jzwgj.util.redis.log.RedisLog;
//
//public class RedisListUtil {
//	
//	private static Logger _logger = Logger.getLogger(RedisStringUtil.class);
//	
//	public static <T> List<T> get(String key){
//		try {
//			List<T> list = null;
//			ListRedisHelper helper = RedisClusterClient.getListClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				list =  helper.findAll(key);
//				if (null != list) {
//					_logger.info(RedisLog.getString("Redis.hit","String", key));
//					return list;
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
//	public static  <T> List<T> setAll(String key,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			List<T> list = null;
//			ListRedisHelper helper = RedisClusterClient.getListClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				list =  (List<T>) abstractReidsDaoSupport.support();
//				boolean isPush = helper.pushAll(key, list);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return list;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (List<T>) abstractReidsDaoSupport.support();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static  <T> List<T> setAll(String key, Integer timeout,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			List<T> list = null;
//			ListRedisHelper helper = RedisClusterClient.getListClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				list =  (List<T>) abstractReidsDaoSupport.support();
//				boolean isPush = helper.pushAll(key, list);
//				helper.setExpire(key, timeout);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return list;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (List<T>) abstractReidsDaoSupport.support();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T> List<T> getAndSet(String key,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			List<T> list = null;
//			ListRedisHelper helper = RedisClusterClient.getListClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				list =  helper.findAll(key);
//				if (null != list) {
//					_logger.info(RedisLog.getString("Redis.hit","String", key));
//					return list;
//				}
//				_logger.info(RedisLog.getString("Redis.miss","String", key));
//				list =  (List<T>) abstractReidsDaoSupport.support();
//				boolean isPush = helper.pushAll(key, list);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return list;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (List<T>) abstractReidsDaoSupport.support();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T> List<T> getAndSet(String key, Integer timeout  ,AbstractRedisDaoSupport abstractReidsDaoSupport){
//		try {
//			List<T> list = null;
//			ListRedisHelper helper = RedisClusterClient.getListClient();
//			if (null != helper) {
//				_logger.info(RedisLog.getString("Redis.run","String"));
//				list =  helper.findAll(key);
//				if (null != list) {
//					_logger.info(RedisLog.getString("Redis.hit","String", key));
//					return list;
//				}
//				_logger.info(RedisLog.getString("Redis.miss","String", key));
//				list =  (List<T>) abstractReidsDaoSupport.support();
//				boolean isPush = helper.pushAll(key, list);
//				helper.setExpire(key, timeout);
//				_logger.info(RedisLog.getString("Redis.set","String",key,String.valueOf(isPush)));
//				return list;
//			}
//		} catch (Exception e) {
////			_logger.error(e.getMessage(),e);
//		}
//		return (List<T>) abstractReidsDaoSupport.support();
//	}
//
//}
