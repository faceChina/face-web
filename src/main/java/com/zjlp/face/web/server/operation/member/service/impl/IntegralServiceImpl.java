package com.zjlp.face.web.server.operation.member.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.operation.member.dao.IntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.dao.SendIntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.operation.member.service.IntegralService;

@Service
public class IntegralServiceImpl implements IntegralService {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private IntegralRecodeDao integralRecodeDao;
	@Autowired
	private SendIntegralRecodeDao sendIntegralRecodeDao;
	
	@Override
	public void addIntegralRecode(IntegralRecode integralRecode) {
		if (null == integralRecode.getCreateTime()) {
			Date date = new Date();
			integralRecode.setCreateTime(date);
		}
		if (StringUtils.isBlank(integralRecode.getOperYear())) {
			integralRecode.setOperYear(String.valueOf(DateUtil.getYear(integralRecode.getCreateTime())));
		}
		if (StringUtils.isBlank(integralRecode.getOperMonth())) {
			integralRecode.setOperMonth(String.valueOf(DateUtil.getMonth(integralRecode.getCreateTime())));
		}
		integralRecodeDao.add(integralRecode);
	}
	
	
	@Override
	public void addIntegralRecode(IntegralRecode integralRecode,Date date) {
		if (null == integralRecode.getCreateTime()) {
			integralRecode.setCreateTime(date);
		}
		if (StringUtils.isBlank(integralRecode.getOperYear())) {
			integralRecode.setOperYear(String.valueOf(DateUtil.getYear(integralRecode.getCreateTime())));
		}
		if (StringUtils.isBlank(integralRecode.getOperMonth())) {
			integralRecode.setOperMonth(String.valueOf(DateUtil.getMonth(integralRecode.getCreateTime())));
		}
		integralRecodeDao.add(integralRecode);
	}


	@Override
	public Long addSendIntegralRecode(SendIntegralRecode sendIntegralRecode) {
		AssertUtil.notNull(SendIntegralRecode.isMustInput(sendIntegralRecode), "Param can't be null.");
		if (null == sendIntegralRecode.getStatus()) {
			sendIntegralRecode.setStatus(SendIntegralRecode.UN_GET);
		}
		if (null == sendIntegralRecode.getCreateTime() ||
				null == sendIntegralRecode.getUpdateTime()) {
			Date date = new Date();
			sendIntegralRecode.setCreateTime(date);
			sendIntegralRecode.setUpdateTime(date);
		}
		return sendIntegralRecodeDao.add(sendIntegralRecode);
	}


	@Override
	public Long sumSendIntegral(Long sellerId, Long userId) {
		AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
		AssertUtil.notNull(userId, "param[userId] can't be null.");
		SendIntegralRecode record = new SendIntegralRecode(sellerId, userId, null);
		record.setStatus(SendIntegralRecode.UN_GET);
		return sendIntegralRecodeDao.sumSendIntegral(record);
	}

	@Override
	public boolean editStatus(Long sellerId, Long userId, Integer oldStatus,
			Integer status) {

		AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
		AssertUtil.notNull(userId, "param[userId] can't be null.");
		AssertUtil.isTrue(SendIntegralRecodeDto.validCgStatus(oldStatus, status), "chang's status is wrong.");
		SendIntegralRecodeDto record = new SendIntegralRecodeDto(sellerId, userId, null);
		Date changeDate = SendIntegralRecodeDto.changeTime(record, status);
		record.setStatus(status);
		record.setOldStatus(oldStatus);
		record.setUpdateTime(changeDate);
		sendIntegralRecodeDao.editClaimIntegralStatus(record);
		return true;
	}

	@Override
	public boolean editClaimIntegralStatus(Long sellerId, Long userId) {
		return editStatus(sellerId, userId, SendIntegralRecode.UN_GET, SendIntegralRecode.GETED);
	}


	@Override
	public SendIntegralRecode getLastRecord(
			SendIntegralRecode sendIntegralRecode) {
		AssertUtil.notNull(sendIntegralRecode.getSellerId(), "param[sellerId] can't be null.");
		AssertUtil.notNull(sendIntegralRecode.getUserId(), "param[userId] can't be null.");
		return sendIntegralRecodeDao.getLastRecord(sendIntegralRecode);
	}

}
