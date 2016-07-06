package com.zjlp.face.web.server.product.im.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.dao.ImMessageDao;
import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;
import com.zjlp.face.web.server.product.im.service.ImMessageService;
@Service
public class ImMessageServiceImpl implements ImMessageService {

	@Autowired
	private ImMessageDao imMessageDao;
	
	@Override
	public void addImMessage(ImMessage imMessage) {
		if(null == imMessage.getSendTime()){
			imMessage.setStates(0);
			imMessage.setSendTime(new Date());
		}
		imMessageDao.add(imMessage);
	}

	@Override
	public Pagination<ImMessageDto> findImUserMessage(ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination) {
		Integer totalRow = imMessageDao.getCount(imMessageDto);
		pagination.setTotalRow(totalRow);
		List<ImMessageDto> datas = imMessageDao.findImUserMessage(imMessageDto, pagination.getStart(), pagination.getPageSize());
		pagination.setDatas(datas);
		return pagination;
	}

}
