package com.zjlp.face.web.server.operation.redenvelope.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.RedenvelopeException;
import com.zjlp.face.web.exception.ext.RedenvlopeException;
import com.zjlp.face.web.server.operation.redenvelope.business.RedenvelopeBusiness;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.domain.ReceiveRedenvelopeRecordDto;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.processor.RedenvelopeProcessor;
import com.zjlp.face.web.server.operation.redenvelope.domain.vo.ReceivePerson;
import com.zjlp.face.web.server.operation.redenvelope.service.RedenvelopeService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.util.Logs;
import com.zjlp.face.web.util.redenvelope.RandomArgRedPackage;

@Service("redenvelopeBusiness")
public class RedenvelopeBusinessImpl implements RedenvelopeBusiness {
	
	@Autowired
	private RedenvelopeService redenvelopeService;
	@Autowired
	private RedenvelopeProcessor grabRedenvelopeProcessor;
	@Autowired
	private RedenvelopeProcessor openRedenvelopeProcessor;
	@Autowired
	private UserProducer userProducer;
	@Override
	public Long grabRedenvelope(Long userId, Long sendRedPkgId)
			throws RedenvelopeException {
		try {
			//参数验证
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(sendRedPkgId, "sendRedPkgId不能为空");
			//该用户是否抢该红包的权限验证
			//TODO
			//抢红包结果返回
			return grabRedenvelopeProcessor.grabRedenvelope(userId, sendRedPkgId);
		} catch (Exception e) {
			throw new RedenvelopeException(e);
		}
	}

	@Override
	public Boolean openRedenvelope(Long userId, Long grabRedPkgId)
			throws RedenvelopeException {
		try {
			//参数验证
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(grabRedPkgId, "grabRedPkgId不能为空");
			//抢红包的用户
			User user = userProducer.getUserById(userId);
			ReceiveRedenvelopeRecordDto data = openRedenvelopeProcessor.openRedenvelope(user, grabRedPkgId);
			if (null == data) {
				return false;
			}
			//已抢红包的状态更新
			
			//账务更新
			
			//账务更新结果入库
			
			return true;
		} catch (Exception e) {
			throw new RedenvelopeException(e);
		}
	}
	
	@Override
	public Long countUserSendAmountToday(Long userId) {
		try {
			AssertUtil.notNull(userId, "userId不能为空");
			return redenvelopeService.countUserSendAmountToday(userId);
		} catch (Exception e) {
			throw new RedenvlopeException(e);
		}
	}

	@Override
	public Long send(SendRedenvelopeRecord sendRedenvelopeRecord) {
		try {
			this.checkParam(sendRedenvelopeRecord);
			
			//今日发包金额不大于5000校验
			Long amount = this.countUserSendAmountToday(sendRedenvelopeRecord.getSendUserId());
			AssertUtil.isTrue(amount.compareTo(500000L) <= 0, "今日发包金额不得大于5000元");
			
			//红包入库到红包记录表
			return redenvelopeService.insertSend(sendRedenvelopeRecord);
		} catch (Exception e) {
			throw new RedenvlopeException(e);
		}
	}

	private void checkParam(SendRedenvelopeRecord sendRedenvelopeRecord) {
		AssertUtil.notNull(sendRedenvelopeRecord, "sendRedenvelopeRecord不能为空");
		AssertUtil.notNull(sendRedenvelopeRecord.getSendUserId(), "sendRedenvelopeRecord.sendUserId不能为空");
		AssertUtil.notNull(sendRedenvelopeRecord.getAmount(), "sendRedenvelopeRecord.amount不能为空");
		AssertUtil.notNull(sendRedenvelopeRecord.getType(), "sendRedenvelopeRecord.type不能为空");
		AssertUtil.notNull(sendRedenvelopeRecord.getTargetId(), "sendRedenvelopeRecord.targetId不能为空");
		AssertUtil.notNull(sendRedenvelopeRecord.getNumber(), "sendRedenvelopeRecord.number不能为空");
		
		User user = userProducer.getUserById(sendRedenvelopeRecord.getSendUserId());
		AssertUtil.notNull(user, "查无此用户");
		
		AssertUtil.isTrue(sendRedenvelopeRecord.checkType(), "type校验未通过");
		AssertUtil.isTrue(sendRedenvelopeRecord.getNumber().compareTo(1) >= 0, "红包数量必须大于等于1");
		
		//单个红包必须大于0.01元
		AssertUtil.isTrue(sendRedenvelopeRecord.getAmount().longValue() >= sendRedenvelopeRecord.getNumber().longValue(), "单个红包最小金额为0.01元");
	}

	@Override
	@Transactional
	public void afterPay(Long id, Integer payChannel) {
		try {
			AssertUtil.notNull(id, "id不能为空");
			AssertUtil.notNull(payChannel, "payChannel不能为空");
			//将红包状态该为已支付
			SendRedenvelopeRecord sendRedenvelopeRecord = this.getSendRocordById(id);
			AssertUtil.notNull(sendRedenvelopeRecord, "sendRedenvelopeRecord不能为空");
			this.updateSendRocordStatusToPayed(sendRedenvelopeRecord, payChannel);
			
			//将红包拆成小红包
			List<ReceiveRedenvelopeRecord> recordList = this.splitRedEnvelope(sendRedenvelopeRecord);
			
			//将小红包入库到领红包记录表
			redenvelopeService.insertReceive(recordList);
			
			//在redis中生成红包数量和小红包集合,已成功领取包3个键值对
		} catch (Exception e) {
			Logs.error(e);
			throw new RedenvlopeException(e);
		}
	}

	/**
	 * 将红包拆成小红包
	 * @Title: splitRedEnvelope 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sendRedenvelopeRecord
	 * @return
	 * @throws Exception
	 * @date 2015年10月14日 下午3:13:28  
	 * @author cbc
	 */
	private List<ReceiveRedenvelopeRecord> splitRedEnvelope(
			SendRedenvelopeRecord sendRedenvelopeRecord) throws Exception {
		List<ReceiveRedenvelopeRecord> recordList = new ArrayList<ReceiveRedenvelopeRecord>();
		if (sendRedenvelopeRecord.getType().equals(Constants.TYPE_GROUP_LUCK)) {
			//拼手气红包
			if (sendRedenvelopeRecord.getNumber().equals(1)) {
				//假如只有一个包，则不拆包
				ReceiveRedenvelopeRecord record = new ReceiveRedenvelopeRecord(sendRedenvelopeRecord);
				record.setAmount(sendRedenvelopeRecord.getAmount());
				recordList.add(record);
			} else {
				long[] list = RandomArgRedPackage.acquireSmailRedPackageList(sendRedenvelopeRecord.getAmount(), sendRedenvelopeRecord.getNumber());
				for (long l : list) {
					ReceiveRedenvelopeRecord record = new ReceiveRedenvelopeRecord(sendRedenvelopeRecord);
					record.setAmount(l);
					recordList.add(record);
				}
			}
		} else {
			//普通红包
			Long smallAmount = sendRedenvelopeRecord.getAmount()/sendRedenvelopeRecord.getNumber();
			for (int i = 0; i < sendRedenvelopeRecord.getNumber(); i++) {
				ReceiveRedenvelopeRecord record = new ReceiveRedenvelopeRecord(sendRedenvelopeRecord);
				record.setAmount(smallAmount);
				recordList.add(record);
			}
		}
		return recordList;
	}

	/**
	 * 
	 * @Title: updateSendRocordStatusToPayed 
	 * @Description: 更新红包为已支付
	 * @param sendRedenvelopeRecord
	 * @date 2015年10月14日 上午10:19:57  
	 * @author cbc
	 */
	private void updateSendRocordStatusToPayed(
			SendRedenvelopeRecord sendRedenvelopeRecord, Integer payChannel) {
		AssertUtil.isTrue(sendRedenvelopeRecord.getStatus().equals(Constants.NOTDEFAULT), "状态校验未通过");
		SendRedenvelopeRecord newRecord = new SendRedenvelopeRecord();
		newRecord.setStatus(Constants.VALID);
		newRecord.setId(sendRedenvelopeRecord.getId());
		newRecord.setPayChannel(payChannel);
		redenvelopeService.updateSendRocordStatusToPayed(newRecord);
	}

	@Override
	public SendRedenvelopeRecord getSendRocordById(Long id) {
		AssertUtil.notNull(id, "id不能为空");
		return redenvelopeService.getSendRocordById(id);
	}
	@Override
	public Pagination<ReceivePerson> findReceivePerson(
			Pagination<ReceivePerson> pagination, Long sendRecordId, Date clickTime) {
		AssertUtil.notNull(pagination, "pagination不能为空");
		AssertUtil.notNull(sendRecordId, "sendRecordId不能为空");
		AssertUtil.notNull(clickTime, "clickTime不能为空");
		return redenvelopeService.findReceivePerson(pagination, sendRecordId, clickTime);
	}

	@Override
	public ReceiveRedenvelopeRecord getReceiveRecordByReceiveUserIdAndSendId(
			Long redenvelopeId, Long receiveUserId) {
		AssertUtil.notNull(redenvelopeId, "redenvelopeId不能为空");
		AssertUtil.notNull(receiveUserId, "receiveUserId不能为空");
		return redenvelopeService.getReceiveRecordByReceiveUserIdAndSendId(redenvelopeId, receiveUserId);
	}

	@Override
	public Long sumHasReceiveMoney(Long redenvelopeId) {
		AssertUtil.notNull(redenvelopeId, "redenvelopeId不能为空");
		return redenvelopeService.sumHasReceiveMoney(redenvelopeId);
	}

	@Override
	public Long caculateLastReceiveTime(Long redenvelopeId) {
		AssertUtil.notNull(redenvelopeId, "redenvelopeId不能为空");
		ReceiveRedenvelopeRecord lastReceive = redenvelopeService.getLastReceive(redenvelopeId);
		return lastReceive.getReceiveTime().getTime() - lastReceive.getCreateTime().getTime();
	}

	@Override
	public ReceiveRedenvelopeRecord getBestLuckReceive(Long redenvelopeId) {
		AssertUtil.notNull(redenvelopeId, "redenvelopeId不能为空redenvelopeId");
		return redenvelopeService.getBestLuckReceive(redenvelopeId);
	}

}
