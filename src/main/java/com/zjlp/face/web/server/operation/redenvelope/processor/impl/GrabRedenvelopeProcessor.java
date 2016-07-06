package com.zjlp.face.web.server.operation.redenvelope.processor.impl;

import org.springframework.stereotype.Repository;

import com.zjlp.face.web.server.operation.redenvelope.processor.RedenvelopeProcessor;

/**
 * 抢红包的处理流程
 * @ClassName: GrabRedenvelopeProcessor 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年10月15日 上午9:46:43
 */
@Repository("grabRedenvelopeProcessor")
public class GrabRedenvelopeProcessor extends RedenvelopeProcessor {

	//领取失败
	private static final Long GRAP_FAILED = -1L;
	
	@Override
	public Long grabRedenvelope(Long userId, Long id) {

		super.printLog("[红包  ", id, "] 用户ID=", userId, "开始抢该红包。");
		// 判断是否有红包可抢，如果没有返回-1
		if (!super.has(id)) {
			super.printLog("[红包  ", id, "] 该红包已抢完，用户ID=", userId, "没有抢到该红包。");
			return GRAP_FAILED;
		}
		//判断该用户是否已抢该红包  说明：该池的作用有两个  1.判断是否有权限进行拆包动作  2.判断该用户是否已经拆过红包
		String key = super.getUserkey(id);
		String field = String.valueOf(userId);
		if (null == wgjHashesHelper.hget(key, field)) {
			wgjHashesHelper.hset(key, field, I_0);
		}
		// 如果有红包,则返回红包ID
		super.printLog("[红包  ", id, "] 用户ID=", userId, "抢到该红包。");
		return id;
	}

}
