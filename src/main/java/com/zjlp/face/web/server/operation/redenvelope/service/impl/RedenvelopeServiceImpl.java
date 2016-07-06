package com.zjlp.face.web.server.operation.redenvelope.service.impl;

import java.util.Date;
import java.util.List;

import javax.print.DocFlavor.READER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.redenvelope.dao.ReceiveRedenvelopeRecordDao;
import com.zjlp.face.web.server.operation.redenvelope.dao.SendRedenvelopeRecordDao;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;
import com.zjlp.face.web.server.operation.redenvelope.service.RedenvelopeService;

@Service("redenvelopeService")
public class RedenvelopeServiceImpl implements RedenvelopeService {
	
	@Autowired
	private SendRedenvelopeRecordDao sendRedenvelopeRecordDao;
	@Autowired
	private ReceiveRedenvelopeRecordDao receiveRedenvelopeRecordDao;
	
	@Override
	public Long countUserSendAmountToday(Long userId) {
		return sendRedenvelopeRecordDao.countUserSendAmountToday(userId);
	}

	@Override
	public Long insertSend(SendRedenvelopeRecord sendRedenvelopeRecord) {
		Date date = new Date();
		sendRedenvelopeRecord.setCreateTime(date);
		sendRedenvelopeRecord.setUpdateTime(date);
		sendRedenvelopeRecord.setStatus(Constants.NOTDEFAULT);
		return sendRedenvelopeRecordDao.insertSend(sendRedenvelopeRecord);
	}

	@Override
	public void insertReceive(List<ReceiveRedenvelopeRecord> recordList) {
		receiveRedenvelopeRecordDao.insertReceive(recordList);
	}

	@Override
	public SendRedenvelopeRecord getSendRocordById(Long id) {
		return sendRedenvelopeRecordDao.getSendRocordById(id);
	}

	@Override
	public void updateSendRocordStatusToPayed(SendRedenvelopeRecord newRecord) {
		Date date = new Date();
		newRecord.setUpdateTime(date);
		newRecord.setPayTime(date);
		sendRedenvelopeRecordDao.updateSendRocordStatus(newRecord);
	}

	@Override
	public Pagination<ReceivePerson> findReceivePerson(
			Pagination<ReceivePerson> pagination, Long sendRecordId, Date clickTime) {
		Integer totalRow = receiveRedenvelopeRecordDao.countReceivePerson(sendRecordId, clickTime);
		List<ReceivePerson> datas = receiveRedenvelopeRecordDao.findReceivePerson(pagination, sendRecordId, clickTime);
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			Long redenvelopeId, Long receiveUserId) {
		return receiveRedenvelopeRecordDao.getReceiveRecordByReceiveUserIdAndSendId(redenvelopeId, receiveUserId);
	}

	@Override
	public Long sumHasReceiveMoney(Long redenvelopeId) {
		return receiveRedenvelopeRecordDao.sumHasReceiveMoney(redenvelopeId);
	}

	@Override
	public boolean isAllReceive(Long redenvelopeId) {
		Integer count = receiveRedenvelopeRecordDao.countNotReceive(redenvelopeId);
		return count.intValue() == 0;
	}

	@Override
	public ReceiveRedenvelopeRecord getLastReceive(Long redenvelopeId) {
		return receiveRedenvelopeRecordDao.getLastReceive(redenvelopeId);
	}

	@Override
	public ReceiveRedenvelopeRecord getBestLuckReceive(Long redenvelopeId) {
		return receiveRedenvelopeRecordDao.getBestLuckReceive(redenvelopeId);
	}

}
