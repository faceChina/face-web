package com.zjlp.face.web.server.product.template.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.product.template.producer.TemplateProducer;
import com.zjlp.face.web.server.product.template.service.OwTemplateService;
@Service
public class TemplateProducerImpl implements TemplateProducer{

	@Autowired
	private OwTemplateService owTemplateService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "Exception" })
	public void initTemplate(String shopNo, Integer shopType) {
		owTemplateService.initTemplate(shopNo, shopType);
	}

}
