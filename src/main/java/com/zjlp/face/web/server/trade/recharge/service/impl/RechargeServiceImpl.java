package com.zjlp.face.web.server.trade.recharge.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.RechargeException;
import com.zjlp.face.web.server.trade.recharge.dao.RechargeDao;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.trade.recharge.domain.dto.RechargeDto;
import com.zjlp.face.web.server.trade.recharge.service.RechargeService;

@Service
public class RechargeServiceImpl implements RechargeService {
	
	@Autowired
	private RechargeDao rechargeDao;
	
	private Log log = LogFactory.getLog(getClass());

	@Override
	public void addRecharge(Recharge recharge) {
		Date date = new Date();
		recharge.setUpdateTime(date);
		recharge.setCreateTime(date);
		rechargeDao.add(recharge);
		
	}

	@Override
	public void editRecharge(Recharge recharge) {
		rechargeDao.edit(recharge);
	}

	@Override
	public Recharge getRechargeByRechargeNo(String rechargeNo) {
		return rechargeDao.getRechargeByRechargeNo(rechargeNo);
	}

	@Override
	public void editRechargeStatus(String rechargeNo, Integer status) {
		Recharge recharge = rechargeDao.getRechargeByRechargeNo(rechargeNo);
		if(!RechargeDto.validate(status, recharge.getStatus())){
			log.info("充值订单状态异常不能修改:" + rechargeNo + "," + status + "," + recharge.getStatus());
			throw new RechargeException("订单状态异常,不能修改");
		}
		Date date = new Date();
		Recharge newRecharge = new Recharge();
		if(Constants.RECHARGE_STATUS_PAYING.equals(status)) {
			newRecharge.setRechargeTime(date);
		}
		newRecharge.setRechargeNo(rechargeNo);
		newRecharge.setStatus(status);
		newRecharge.setUpdateTime(date);
		rechargeDao.edit(newRecharge);
	}

	@Override
	public List<Recharge> findRechargeList(Recharge recharge) {
		return rechargeDao.findRechargeList(recharge);
	}

	@Override
	public List<Recharge> findRechargeListByUserAccountAndAccountType(
			String memberCardId, String beginTime, String endTime) {
		return rechargeDao.findRechargeListByUserAccountAndAccountType(memberCardId, beginTime, endTime);
	}

	
}
