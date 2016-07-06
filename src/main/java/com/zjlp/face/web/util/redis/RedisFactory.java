package com.zjlp.face.web.util.redis;

import com.zjlp.face.jredis.client.RedisListHelper;
import com.zjlp.face.jredis.client.RedisStringHelper;
import com.zjlp.face.web.job.PersistenceJobServiceLocator;

public final class RedisFactory {

	public static RedisListHelper getWgjListHelper() {
		return PersistenceJobServiceLocator.getJobService("wgjListHelper", RedisListHelper.class);
	}
	
	public static RedisStringHelper getWgjStringHelper() {
		return PersistenceJobServiceLocator.getJobService("wgjStringHelper", RedisStringHelper.class);
	}
}
