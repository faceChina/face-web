package com.zjlp.face.web.server.trade.payment.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.trade.payment.dao.MemberTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;
import com.zjlp.face.web.server.trade.payment.service.MemberTransactionRecordService;
@Service
public class MemberTransactionRecordServiceImpl implements
		MemberTransactionRecordService {
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberTransactionRecordDao memberTransactionRecordDao;
	
	@Override
	public void addMemberTransactionRecord(
			MemberTransactionRecord transactionRecord) throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(transactionRecord, "Param[transactionRecord] can't be null.");
			this.checkTransactionRecord(transactionRecord);
//			//过滤重复
//			MemberTransactionRecord old = this
//					.getTransactionRecordBySerialNo(transactionRecord.getTransactionSerialNumber());
//			AssertUtil.isNull(old, "MemberTransactionRecord[type={}, serialNo={}] is already exists.",
//					old.getType(), old.getTransactionSerialNumber());
//			//添加
//			old.setCreateTime(new Date());
			memberTransactionRecordDao.add(transactionRecord);
			log.info(new StringBuilder("Add ").append(transactionRecord.toString()).append(" end."));
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	private void checkTransactionRecord(
			MemberTransactionRecord transactionRecord) {
		AssertUtil.notNull(transactionRecord.getMemberCardId(), 
				"[TransactionRecord Check] TransactionRecord.memberCardId can't be null.");
		AssertUtil.notNull(transactionRecord.getSellerId(), 
				"[TransactionRecord Check] TransactionRecord.sellerId can't be null.");
		AssertUtil.notNull(transactionRecord.getTransactionSerialNumber(), 
				"[TransactionRecord Check] TransactionRecord.transactionSerialNumber can't be null.");
		AssertUtil.notNull(transactionRecord.getChannel(), 
				"[TransactionRecord Check] TransactionRecord.channel can't be null.");
		AssertUtil.notNull(transactionRecord.getType(), 
				"[TransactionRecord Check] TransactionRecord.type can't be null.");
		AssertUtil.notNull(transactionRecord.getAmount(), 
				"[TransactionRecord Check] TransactionRecord.pice can't be null.");
		AssertUtil.notNull(transactionRecord.getAmount(), 
				"[TransactionRecord Check] TransactionRecord.amount can't be null.");
		AssertUtil.notNull(transactionRecord.getBeforeAmount(), 
				"[TransactionRecord Check] TransactionRecord.beforeAmount can't be null.");
		AssertUtil.notNull(transactionRecord.getTransTime(), 
				"[TransactionRecord Check] TransactionRecord.rechargeTime can't be null.");
	}
	
	@Override
	public MemberTransactionRecord getTransactionRecordBySerialNo(String serialNo) {
		try {
			AssertUtil.hasLength(serialNo, "Param[serialNo] can't be null.");
			return memberTransactionRecordDao.getByTransactionSerialNumber(serialNo);
		} catch (RuntimeException e) {
			throw new MemberException(e);
		}
	}

	@Override
	public List<MemberTransactionRecordDto> findTransactionRecordList(
			MemberTransactionRecordDto dto)
			throws MemberException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can't be null.");
			AssertUtil.notNull(dto.getSellerId(), "Param[dto.sellerId] can't be null.");
			AssertUtil.notNull(dto.getType(), "Param[dto.type] can't be null.");
			return memberTransactionRecordDao.findTransactionRecordList(dto);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Integer getTransactionRecordCount(MemberTransactionRecordDto dto)
			throws MemberException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can't be null.");
			AssertUtil.notNull(dto.getSellerId(), "Param[dto.sellerId] can't be null.");
			AssertUtil.notNull(dto.getType(), "Param[dto.type] can't be null.");
			return memberTransactionRecordDao.getTransactionCount(dto);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

}
