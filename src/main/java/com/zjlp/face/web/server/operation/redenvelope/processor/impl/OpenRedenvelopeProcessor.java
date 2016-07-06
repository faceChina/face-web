package com.zjlp.face.web.server.operation.redenvelope.processor.impl;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.rpc.RpcContext;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecordDto;
import com.zjlp.face.web.server.operation.redenvelope.processor.RedenvelopeProcessor;
import com.zjlp.face.web.server.user.user.domain.User;

/**
 * 拆红包的处理流程
 * @ClassName: OpenRedenvelopeProcessor 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年10月15日 上午9:39:55
 */
@Repository("openRedenvelopeProcessor")
public class OpenRedenvelopeProcessor extends RedenvelopeProcessor {

	private static final Long TIMEOUT = 5000L;
	
	@Override
	public ReceiveRedenvelopeRecordDto openRedenvelope(User user, Long id) {
		
		//判断是否有红包可抢
		if (!super.has(id)) {
			return null;
		}
		
		//对该红包数进行加锁
		String lockKey = super.getLockKey(id);
		boolean islocked = false;
		try {
			if (islocked = redisLock.lock(lockKey, TIMEOUT)) {
				//对该值进行减值操作
				if (!super.has(id)) {
					return null;
				}
				//拆包权限
				if (!this.openRight(user.getId(), id)) {
					return null;
				}
				Long count = wgjStringHelper.decrease(super.getNumkey(id));
				super.printLog("[红包  ", id, "] 开始拆第  ", count, " 个红包。");
				//取出该红包  符合数组的索引规则  约定俗成
				ReceiveRedenvelopeRecordDto data = wgjListHelper.getByIndex(super.getGrapkey(id), count.intValue() - 1);
				//红包的值设置 
				this.initOpenUserInfo(data, user);
				//压入已被抢红包的队列
				wgjListHelper.rpush(super.getOpenedkey(id), data);
				//返回已拆包成功的标志
				return data;
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (islocked) {
				redisLock.unlock(lockKey);
			}
		}
		return null;
	}

	/**
	 * 设置已拆红包的用户信息
	 * 
	 * @Title: initOpenUserInfo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param data
	 *            已拆红包
	 * @param user
	 *            用户信息
	 * @date 2015年10月15日 下午3:37:02
	 * @author lys
	 */
	private void initOpenUserInfo(ReceiveRedenvelopeRecordDto data, User user){
		//用户名
		data.setUserName(null != user.getNickname() 
				? user.getNickname() : user.getContacts());
		//头像
		data.setHeadImgUrl(user.getHeadimgurl());
		//领取时间
		Date reciveTime = new Date();
		data.setUpdateTime(reciveTime);
		data.setReciveTime(reciveTime.getTime());
	}
	
	/**
	 * 是否有拆该红包的权限
	 * @Title: filter 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户
	 * @param id 红包
	 * @return
	 * @date 2015年10月15日 下午4:32:53  
	 * @author lys
	 */
	private boolean openRight(Long userId, Long id){
		String key = super.getUserkey(id);
		String field = String.valueOf(userId);
		Object count = wgjHashesHelper.hget(key, field);
		//没有抢到该红包
		if (null == count) {
			super.printLog("[红包  ", id, "] 该用户ID=", userId, " 没有抢到该红包，所以没法进行拆包动作！");
			return false;
		} else if (I_0.equals(count)) {
			//未抢红包
			wgjHashesHelper.hset(key, field, I_1);
			return true;
		} else {
			//已抢
			super.printLog("[红包  ", id, "] 该用户ID=", userId, "已经拆过红包，不能再次抢红包。");
			return false;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("begin");
		Future<Integer> barFuture = RpcContext.getContext().getFuture();
		Integer a = barFuture.get();
		System.out.println(a);
		System.out.println("end");
	}

}
