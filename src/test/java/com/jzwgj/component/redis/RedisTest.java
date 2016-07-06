package com.jzwgj.component.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class RedisTest {
	
//	private static final String KEY = "listKey001";

//	@Test
	public void test() {
//		new RedisClusterInitializeListener().contextInitialized(null);
//		Integer id = getUserById(108L);
//		System.out.println(id);
	}
	
	public Integer getUserById(Long id) {
//		StringRedisHelper helper = RedisClusterClient.getStringClient();
//		if (null != helper) {
//			String key = CacheKey.LEFT_MENU_ANNOUNCEMENT_COUNT.getCacheKey("HHasdfasdf");
//			Integer user = helper.get(key);
//			if (null == user) {
//				helper.set(key, 20, 20);
//			}
//			return user;
//		}
		return null;
	}
	
	@Test
	public void listTest1() {
//		List<String> list = null;
//		ListRedisHelper helper = RedisClusterClient.getListClient();
//		if (null != helper) {
//			list = helper.findPage(KEY, 0, 10);
//			if (null == list) {
//				System.out.println("----------------have no data for list key-------------------");
//				helper.pushAll(KEY, this.getStringList(), 100);
//			} else {
//				System.out.println("----------------have data for list key-------------------");
//				for (String string : list) {
//					System.out.println(string);
//				}
//			}
//		}
	}
	
	public void clean() {
//		new RedisClusterInitializeListener().contextInitialized(null);
//		ListRedisHelper helper = RedisClusterClient.getListClient();
	}
	
//	private List<String> getStringList() {
//		List<String> list = new ArrayList<String>();
//		list.add("member 1");
//		list.add("member 2");
//		list.add("member 3");
//		list.add("member 4");
//		list.add("member 5");
//		list.add("member 6");
//		list.add("member 7");
//		list.add("member 8");
//		list.add("member 9");
//		list.add("member 10");
//		list.add("member 11");
//		list.add("member 12");
//		list.add("member 13");
//		list.add("member 14");
//		list.add("member 15");
//		list.add("member 16");
//		list.add("member 17");
//		list.add("member 18");
//		list.add("member 19");
//		list.add("member 20");
//		list.add("member 21");
//		list.add("member 22");
//		list.add("member 23");
//		list.add("member 24");
//		list.add("member 25");
//		list.add("member 26");
//		list.add("member 27");
//		list.add("member 28");
//		list.add("member 29");
//		return list;
//	}
}
