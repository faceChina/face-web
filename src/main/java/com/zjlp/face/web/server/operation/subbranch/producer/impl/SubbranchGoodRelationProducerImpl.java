package com.zjlp.face.web.server.operation.subbranch.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchGoodRelation;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchGoodRelationProducer;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchGoodRelationService;

@Component
public class SubbranchGoodRelationProducerImpl implements SubbranchGoodRelationProducer {
	@Autowired
	private SubbranchGoodRelationService subbranchGoodRelationService;

	@Override
	public Integer getSubbranchGoodRate(SubbranchGoodRelation subbranchGoodRelation) {
		return subbranchGoodRelationService.getSubbranchGoodRate(subbranchGoodRelation);
	}
	
}
