package com.zjlp.face.web.server.operation.redenvelope.service;

import java.util.Date;
import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;

/**
 * 红包基础服务
 * @ClassName: RedenvelopeService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年10月13日 上午11:53:51
 */
public interface RedenvelopeService {

	Long countUserSendAmountToday(Long userId);

	Long insertSend(SendRedenvelopeRecord sendRedenvelopeRecord);

	void insertReceive(List<ReceiveRedenvelopeRecord> recordList);

	SendRedenvelopeRecord getSendRocordById(Long id);

	void updateSendRocordStatusToPayed(SendRedenvelopeRecord newRecord);

	Pagination<ReceivePerson> findReceivePerson(
			Pagination<ReceivePerson> pagination, Long sendRecordId, Date clickTime);

	ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			Long redenvelopeId, Long receiveUserId);

	Long sumHasReceiveMoney(Long redenvelopeId);

	boolean isAllReceive(Long redenvelopeId);

	ReceiveRedenvelopeRecord getLastReceive(Long redenvelopeId);

	ReceiveRedenvelopeRecord getBestLuckReceive(Long redenvelopeId);

}
