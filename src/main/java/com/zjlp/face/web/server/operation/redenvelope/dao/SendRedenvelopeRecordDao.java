package com.zjlp.face.web.server.operation.redenvelope.dao;

import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;

public interface SendRedenvelopeRecordDao {

	Long countUserSendAmountToday(Long userId);

	Long insertSend(SendRedenvelopeRecord sendRedenvelopeRecord);

	SendRedenvelopeRecord getSendRocordById(Long id);

	void updateSendRocordStatus(SendRedenvelopeRecord newRecord);

}
