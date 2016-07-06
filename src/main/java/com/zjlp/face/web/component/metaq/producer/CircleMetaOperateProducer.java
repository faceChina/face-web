package com.zjlp.face.web.component.metaq.producer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.CircleMessage;
import com.jzwgj.metaq.vo.Topic;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
import com.zjlp.face.web.component.metaq.log.MetaLog;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;
import com.zjlp.face.web.server.social.businesscircle.producer.AppCircleProducer;

@Component
public class CircleMetaOperateProducer {

	private Logger _logger  = Logger.getLogger("metaqProducerError");

	@Autowired
	private AppCircleProducer appCircleProducer;
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;

	
	/**
	 * @param orderNo
	 * @param productName
	 */
	public void senderCircleMsgAnsy(Long circleMsgId,Set<Long> receiverIdSet) {

		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + circleMsgId + "=========="
					+ Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_CIRCLEMSG");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.CircleMsg"));
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			
			AppCircleMsgVo vo = appCircleProducer.getAppCircleById(circleMsgId);
			if (null == vo) {
				_logger.error(MetaLog.getString("Error.CircleMsg.NullCircleMsg"));
				return;
			}
			
			CircleMessage circleMessage = new CircleMessage();
			circleMessage.setType(6);// 生意圈发布消息
			circleMessage.setUserType(2);
			circleMessage.setCircleMsgId(vo.getId());
			circleMessage.setHeadImgUrl(vo.getHeadimgurl());
			circleMessage.setCreateTime(vo.getCreateTime().getTime());
			for (Long id : receiverIdSet) {
				if (!id.equals(vo.getUserId())) {
					circleMessage.setUserId(id);//消息接收者
					// 开启线程处理
					executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.CIRCLETOPIC, circleMessage.toJson()));
					_logger.info(MetaLog.getString("CIRCLEMSG.Admin.End", id.toString(), circleMsgId.toString()));
				}
			}
			
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
	/**
	 * @param orderNo
	 * @param productName
	 */
	public void senderPraiseAnsy(Long praiseId) {

		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + praiseId + "=========="
					+ Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_CIRCLEMSG");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.CircleMsg"));
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			AppPraise appPraise = appCircleProducer.getAppPraise(praiseId);
			if (null == appPraise) {
				_logger.error(MetaLog.getString("Error.AppPraise.NullAppPraise"));
				return;
			}
			AppCircleMsgVo vo = appCircleProducer.getAppCircleById(appPraise.getCircleMsgId());
			if (null == vo) {
				_logger.error(MetaLog.getString("Error.CircleMsg.NullCircleMsg"));
				return;
			}
			//消息接收者
			Set<Long> set = new HashSet<Long>();
			set.add(vo.getUserId());
			for (AppPraise pr : vo.getPraiseList()) {
					set.add(pr.getUserId());
			}
			for (AppComment com : vo.getCommentList()) {
					set.add(com.getSenderId());
			}
			set.remove(appPraise.getUserId());
			
			CircleMessage circleMessage = new CircleMessage();
			circleMessage.setType(7);// 生意圈发布消息
			circleMessage.setUserType(2);
			circleMessage.setCircleMsgId(appPraise.getCircleMsgId());
			circleMessage.setHeadImgUrl(appPraise.getHeadimgurl());
			circleMessage.setSenderName(appPraise.getUserName());
			circleMessage.setImgUrl(getImg(vo.getImg1()));
			circleMessage.setUrl(vo.getUrl());
			circleMessage.setContent(vo.getContent());
			Date date = new Date();
			circleMessage.setCreateTime(date.getTime());
			//消息接收者
			for (Long id : set) {
				if (!id.equals(appPraise.getUserId())) {
					circleMessage.setUserId(id);//消息接收者
					// 开启线程处理
					executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.CIRCLETOPIC, circleMessage.toJson()));
					_logger.info(MetaLog.getString("Praise.Admin.End",id.toString(), praiseId.toString()));
				}
			}
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
	
	/**
	 * @param orderNo
	 * @param productName
	 */
	public void senderCommentAnsy(Long commentId) {

		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + commentId + "=========="
					+ Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_CIRCLEMSG");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.CircleMsg"));
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			
			
			AppComment appComment = appCircleProducer.getAppComment(commentId);
			if (null == appComment) {
				_logger.error(MetaLog.getString("Error.AppComment.NullAppComment"));
				return;
			}
			
			AppCircleMsgVo vo = appCircleProducer.getAppCircleById(appComment.getCircleMsgId());
			if (null == vo) {
				_logger.error(MetaLog.getString("Error.AppComment.NullAppComment"));
				return;
			}
			//消息接收者
			Set<Long> set = new HashSet<Long>();
			set.add(vo.getUserId());
			for (AppPraise pr : vo.getPraiseList()) {
					set.add(pr.getUserId());
			}
			for (AppComment com : vo.getCommentList()) {
					set.add(com.getSenderId());
			}
			set.remove(appComment.getSenderId());
			
			CircleMessage circleMessage = new CircleMessage();
			circleMessage.setType(8);// 生意圈发布消息
			circleMessage.setUserType(2);
			circleMessage.setCircleMsgId(appComment.getCircleMsgId());
			circleMessage.setHeadImgUrl(appComment.getHeadimgurl());
			circleMessage.setSenderName(appComment.getSenderName());
			circleMessage.setReceiveName(appComment.getReceiveName());
			circleMessage.setImgUrl(getImg(vo.getImg1()));
			circleMessage.setUrl(vo.getUrl());
			circleMessage.setContent(vo.getContent());
			circleMessage.setComment(appComment.getContent());
			circleMessage.setCreateTime(appComment.getCreateTime().getTime());
			//消息接收者
			for (Long id : set) {
				if (!id.equals(appComment.getSenderId())) {
					circleMessage.setUserId(id);// 消息接收者
					// 开启线程处理
					executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.CIRCLETOPIC, circleMessage.toJson()));
					_logger.info(MetaLog.getString("AppComment.Admin.End", id.toString(), commentId.toString()));
				}
			}
			
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}
	
	private String getImg(String imgs){
		String img = "";
		if (imgs != null && !"".equals(imgs)){
			String[] imgArr = imgs.split(";");
			img = imgArr[0];
		}
		return img;
	}
}
