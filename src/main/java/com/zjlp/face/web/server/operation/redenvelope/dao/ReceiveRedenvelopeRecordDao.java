package com.zjlp.face.web.server.operation.redenvelope.dao;

import java.util.Date;
import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;

public interface ReceiveRedenvelopeRecordDao {

	void insertReceive(List<ReceiveRedenvelopeRecord> recordList);

	Integer countReceivePerson(Long sendRecordId, Date clickTime);

	List<ReceivePerson> findReceivePerson(Pagination<ReceivePerson> pagination,
			Long sendRecordId, Date clickTime);

	ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			Long redenvelopeId, Long receiveUserId);

	Long sumHasReceiveMoney(Long redenvelopeId);

	Integer countNotReceive(Long redenvelopeId);

	ReceiveRedenvelopeRecord getLastReceive(Long redenvelopeId);

	ReceiveRedenvelopeRecord getBestLuckReceive(Long redenvelopeId);


}
