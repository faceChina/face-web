package com.zjlp.face.web.server.social.businesscircle.helper.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.jredis.client.RedisListHelper;
import com.zjlp.face.web.component.metaq.producer.CircleMetaOperateProducer;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.OfRoster;
import com.zjlp.face.web.server.social.businesscircle.helper.CircleProcessor;
import com.zjlp.face.web.server.social.businesscircle.helper.log.CircleLog;
import com.zjlp.face.web.server.social.businesscircle.producer.AppCircleProducer;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.redis.RedisKey;

@Component("circleProcessor")
public class CircleProcessorImpl  implements CircleProcessor<AppCircleMsg>{
	
	private Logger _logger  = Logger.getLogger("jredisProducerError");
	@Autowired
	private AppCircleProducer appCircleProducer;
	@Autowired
	private CircleMetaOperateProducer circleMetaOperateProducer;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private RedisListHelper redisListHelper;
	
	private static final Integer TIMEOUT = 1000*60*60*24*3;
	
	@Override
	public  void processor(AppCircleMsg appCircle,String username){
	
	/**
	 * 1.查找当前用户的好友以及好友的好友
	 * 2.将本条消息发送给有权限查看的人
	 */
	/**查找自己的好友**/
	List<OfRoster>  list = appCircleProducer.queryRosterByUserName(username,null);
	/**接受消息的人集合**/
	LinkedList<OfRoster> linkedList = new LinkedList<OfRoster>(list);
	/**metaQ推送消息接收者ID**/
	Set<Long> set = new HashSet<Long>();
	
	set.add(appCircle.getUserId());
	
	for (OfRoster ofRoster : list) {
		/**好友的好友**/
		String firendName= ofRoster.getJid().substring(0, ofRoster.getJid().indexOf("@"));
		List<OfRoster>  firendlist = appCircleProducer.queryRosterByUserName(firendName,username);
		linkedList.addAll(firendlist);
	}
	/**将消息ID放入到接受者的列表中**/
	for (OfRoster ofRoster : linkedList) {
		String name = ofRoster.getJid().substring(0, ofRoster.getJid().indexOf("@"));
		User user = null;		
		try {
			user = userBusiness.getUserByLoginAccount(name);
		} catch (Exception e) {
			continue;
		}
		if (null == user){
			continue;
		}
		set.add(user.getId());
		/*try {
			appCircleProducer.addAppUserMsgRelation(user.getId(),appCircle.getId());
		} catch (Exception e) {
			//log.error("发布生意圈消息发送到接收者失败",e);
			continue;
		}*/
	}
	
	this.senderToJredis(appCircle.getId(), set);
	
	set.remove(appCircle.getUserId());
	
		try {
			circleMetaOperateProducer.senderCircleMsgAnsy(appCircle.getId(), set);
		} catch (Exception e) {
			_logger.error("发布生意圈消息MetaQ推送异常！", e);
		}
	}
	
	private void senderToJredis(Long circleMsgId,Set<Long> receiverIdSet){
		
		for ( Long id : receiverIdSet ) {
			
			String key = RedisKey.SOCIAL_ADDAPPCIRCLEMSG_KEY_+id;
			
			boolean  isSuccess = redisListHelper.lpush(key, TIMEOUT, circleMsgId);
			
			if ( !isSuccess ) {
				_logger.info(CircleLog.getString("CircleMsg.Sender.error", id.toString(),circleMsgId.toString()));
			}
		}
	}

}
