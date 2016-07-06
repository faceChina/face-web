package com.zjlp.face.web.server.operation.redenvelope.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.producer.RedenvelopeProducer;
import com.zjlp.face.web.server.operation.redenvelope.service.RedenvelopeService;

@Service("redenvelopeProducer")
public class RedenvelopeProducerImpl implements RedenvelopeProducer {
	
	@Autowired
	private RedenvelopeService redenvelopeService;

	@Override
	public SendRedenvelopeRecord getSendRocordById(Long id) {
		return redenvelopeService.getSendRocordById(id);
	}
	
	

}
