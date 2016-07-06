package com.zjlp.face.web.server.product.im.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.business.ImMessageBusiness;
import com.zjlp.face.web.server.product.im.domain.ImFriends;
import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;
import com.zjlp.face.web.server.product.im.service.ImFriendsService;
import com.zjlp.face.web.server.product.im.service.ImMessageService;
import com.zjlp.face.web.server.product.im.service.ImUserService;
import com.zjlp.face.web.server.product.im.util.ImConstantsUtil;
@Service
public class ImMessageBusinessImpl implements ImMessageBusiness {

	@Autowired
	private ImMessageService imMessageService;
	
	@Autowired
	private ImUserService imUserService;
	
	@Autowired
	private ImFriendsService imFriendsService;
	
	@Override
	public void sendMessage(Long sender,String receiver,String message) throws Exception{
		if(null == sender)return;//匿名用户发送消息s
		Assert.notNull(receiver);
		Assert.notNull(message);
		try {
			receiver = receiver.substring(0,receiver.indexOf("@"));
			//获取接受者的账号
			ImUser imUser = imUserService.getImUserByUserName(receiver);
			if(null == imUser) return;
			if(imUser.getType().intValue() == ImConstantsUtil.REMOTE_TYPE_ANONYMOUS.intValue())return;//发送给匿名用户
			//获取发送者账号
			ImUser senderUser = imUserService.getImUserById(sender);
			if(null == senderUser)return;
			if(senderUser.getType().intValue() ==ImConstantsUtil.REMOTE_TYPE_ANONYMOUS.intValue())return;//发送给匿名用户
			
			//保存聊天记录
			ImMessage imMessage = new ImMessage();
			imMessage.setSender(sender);
			imMessage.setMessage(message);
			imMessage.setReceiver(imUser.getId());
			imMessageService.addImMessage(imMessage);
			
			//添加最近联系人
			ImFriends imFriends1 = new ImFriends();
			imFriends1.setImUserId(sender);
			imFriends1.setFriendId(imUser.getId());
//			imFriends1.setFriendName(imUser.getNickname());
			Integer count1 = imFriendsService.getCountByFriends(imFriends1);
			if(count1 == 0){
				imFriendsService.addImFriends(imFriends1);
			}
			ImFriends imFriends2 = new ImFriends();
			imFriends2.setImUserId(imUser.getId());
			imFriends2.setFriendId(sender);
//			imFriends2.setFriendName(senderUser.getNickname());
			Integer count2 = imFriendsService.getCountByFriends(imFriends2);
			if(count2 == 0){
				imFriendsService.addImFriends(imFriends2);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void sendMassMessage(List<ImMessage> imMessages) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Pagination<ImMessageDto> findImUserMessage(String receiver,ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination)
			throws Exception {
		Assert.notNull(imMessageDto);
		Assert.notNull(receiver);
		try {
			//获取接受者的账号
			ImUser imUser = imUserService.getImUserByUserName(receiver);
			if (null == imUser || null == imUser.getId()) {
				return null;
			}
			imMessageDto.setReceiver(imUser.getId());
			return imMessageService.findImUserMessage(imMessageDto,pagination);
		} catch (Exception e) {
			throw e;
		}
	}

}
