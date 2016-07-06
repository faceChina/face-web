package com.zjlp.face.web.server.product.im.dao;

import java.util.List;

import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;

public interface ImMessageDao {

	void add(ImMessage imMessage);
	
	void edit(ImMessage imMessage);
	
	void deleteById(Long id);
	
	void deleteByUserId(Long userId);
	
	ImMessage getById(Long id);
	
	List<ImMessageDto> findImUserMessage(ImMessageDto imMessageDto,Integer start,Integer pageSize);
	
	Integer getCount(ImMessage imMessage);
}
