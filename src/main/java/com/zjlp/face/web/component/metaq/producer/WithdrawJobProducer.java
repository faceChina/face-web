package com.zjlp.face.web.component.metaq.producer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.zjlp.face.account.dto.WithdrawReq;
import com.zjlp.face.account.dto.WithdrawRsp;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
@Component
public class WithdrawJobProducer {

private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired(required=false)
	private MetaQProviderClinet metaQProviderClinet;
	
	public void queryWithdrawJob(WithdrawRsp withdrawRsp,
			WithdrawReq withdrawReq, Long cardId, Long accountId,
			String cellPhone, Date date){
		_logger.info("Method(queryWithdrawJob).Param(cardId):"+cardId+"Param(accountId):"+accountId+" currentThread："+Thread.currentThread().getName());
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("withdrawRsp", withdrawRsp);
			data.put("withdrawReq", withdrawReq);
			data.put("cardId", cardId);
			data.put("accountId", accountId);
			data.put("cellPhone", cellPhone);
			data.put("date", date.getTime());
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", "queryWithdrawJob");
			param.put("data", JSONObject.fromObject(data).toString());
			String message = JSONObject.fromObject(param).toString();
			executor.execute(new MetaAnsyHelper(metaQProviderClinet,"jobtopic",message));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if(null != executor){
				executor.shutdown();
			}
		}
	}
}
