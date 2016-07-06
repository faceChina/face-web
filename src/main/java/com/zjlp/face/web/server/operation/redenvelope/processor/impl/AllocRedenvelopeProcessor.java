package com.zjlp.face.web.server.operation.redenvelope.processor.impl;

import org.springframework.stereotype.Repository;

import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecordDto;
import com.zjlp.face.web.server.operation.redenvelope.processor.RedenvelopeProcessor;

@Repository("allocRedenvelopeProcessor")
public class AllocRedenvelopeProcessor extends RedenvelopeProcessor {

	@Override
	public void allocRedenvelope(Long id) {
		
		//红包个数初始化
		wgjStringHelper.set(super.getNumkey(id), 10);
		//红包明细初始化
		for (int i = 0; i < 10; i++) {
			wgjListHelper.rpush(super.getGrapkey(id), new ReceiveRedenvelopeRecordDto(id, i+10L));
		}
	}

}
