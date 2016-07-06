package com.zjlp.face.web.server.product.im.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;
import com.zjlp.face.web.server.product.im.producer.ImMessageProducer;
import com.zjlp.face.web.server.product.im.service.ImMessageService;

@Repository("imMessageProducer")
public class ImMessageProducerImpl implements ImMessageProducer {
	
	@Autowired
	private ImMessageService imMessageService;

	@Override
	public void addImMessage(ImMessage imMessage) {
		imMessageService.addImMessage(imMessage);
	}

	@Override
	public Pagination<ImMessageDto> findImUserMessage(
			ImMessageDto imMessageDto, Pagination<ImMessageDto> pagination) {
		return imMessageService.findImUserMessage(imMessageDto, pagination);
	}

}
