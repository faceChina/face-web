package com.jzwgj.component.daosupport.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.component.daosupport.dao.UserDao;
import com.jzwgj.component.daosupport.domain.User;
import com.zjlp.face.util.exception.AssertUtil;

//@Service
public class UserServiceImpl implements UserService {

	@Autowired(required=false)
	private UserDao userDao;
	
	@Override
	@Transactional
	public void addAndEdit() {
		User user = new User();
		user.setName("lys");
		user.setAge(21);
		Date date = new Date();
		user.setCreateTime(date);
		user.setUpdateTime(date);
		userDao.insert(user);
		
		User user1 = new User();
		user1.setName("lys1");
		user.setAge(21);
		user.setCreateTime(date);
		user.setUpdateTime(date);
		userDao.insert(user);
		AssertUtil.isNull(user, "发生错误啦");
	}

	@Override
	public User getUserById(Long id) {
//		StringRedisHelper helper = RedisClusterClient.getStringClient();
//		if (null != helper) {
//			String key = CacheKey.LEFT_MENU_ANNOUNCEMENT_COUNT.getCacheKey("HHasdfasdf");
//			User user = helper.get(key);
//			if (null == user) {
//				user = userDao.selectByPrimaryKey(id);
//				helper.set(key, user, 20);
//			}
//			return user;
//		} else {
//			return userDao.selectByPrimaryKey(id);
//		}
		return null;
	}

}
