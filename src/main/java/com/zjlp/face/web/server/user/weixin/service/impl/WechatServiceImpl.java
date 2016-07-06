package com.zjlp.face.web.server.user.weixin.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.weixin.dao.MessageBodyDao;
import com.zjlp.face.web.server.user.weixin.dao.MessageContentDao;
import com.zjlp.face.web.server.user.weixin.dao.MessageSettingDao;
import com.zjlp.face.web.server.user.weixin.dao.ToolSettingDao;
import com.zjlp.face.web.server.user.weixin.domain.MessageBody;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;
import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;
import com.zjlp.face.web.server.user.weixin.service.WechatService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WechatServiceImpl implements WechatService {

    @Autowired
    private MessageSettingDao messageSettingDao;

    @Autowired
    private MessageContentDao messageContentDao;

    @Autowired
    private MessageBodyDao messageBodyDao;
    
    @Autowired
    private ToolSettingDao toolSettingDao;

    @Override
    public void addMessageBody(MessageBody messageBody){
        messageBodyDao.add(messageBody);
    }

    @Override
    public void addMessageContent(MessageContent messageContent){
        messageContentDao.add(messageContent);
    }

    @Override
    public void addMessageSetting(MessageSetting messageSetting){
        messageSettingDao.add(messageSetting);
    }

    @Override
    public void editMessageBody(MessageBody messageBody){
        messageBodyDao.edit(messageBody);
    }

    @Override
    public void editMessageContent(MessageContent messageContent){
        messageContentDao.editMessageContent(messageContent);
    }

    @Override
    public void editMessageSetting(MessageSetting messageSetting){

        messageSettingDao.edit(messageSetting);
    }

    @Override
    public List<MessageContent> findMessageContentByMessageBodyId(Long messageBodyId){
        return messageContentDao.findByMessageBodyId(messageBodyId);
    }

    @Override
    public Pagination<MessageSettingDto> findMessageSettingPageList(MessageSettingDto dto, Pagination<MessageSettingDto> pagination){

        Integer totalRow = messageSettingDao.getCount(dto);
        pagination.setTotalRow(totalRow);
        List<MessageSettingDto> datas = messageSettingDao.findMessageSettingPageList(dto, pagination.getStart(), pagination.getPageSize());
        pagination.setDatas(datas);
        return pagination;
    }

    @Override
    public MessageContent getMessageContentById(Long id){
        return messageContentDao.getById(id);
    }

    @Override
    public MessageSetting getMessageSettingById(Long id){
        return messageSettingDao.getById(id);
    }

    @Override
    public void delMessageContentByMessageBodyId(Long messageBodyId){
        messageContentDao.delMessageContentByMessageBodyId(messageBodyId);

    }

    @Override
    public void delMessageContentById(Long id){
        messageContentDao.delMessageContentById(id);

    }

    @Override
    public void removeMessageSetting(MessageSetting messageSetting){
        messageSettingDao.removeMessageSetting(messageSetting);

    }

	@Override
	public ToolSetting getToolSettingByShopNo(String shopNo) {
		return toolSettingDao.getByShopNo(shopNo);
	}

	@Override
	public void editToolSetting(ToolSetting toolSetting) {
		toolSetting.setUpdateTime(new Date());
		toolSettingDao.edit(toolSetting);
	}

	@Override
	public MessageSetting getMessageSetting(MessageSetting messageSetting) {
		return messageSettingDao.getMessageSetting(messageSetting);
	}

	@Override
	public MessageBody getMessageBodyById(Long id) {
		return messageBodyDao.getMessageBodyById(id);
	}

	@Override
	public void editMessageSettingStatus(MessageSetting meg) {
		messageSettingDao.editMessageSettingStatus(meg);
	}

}
