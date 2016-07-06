package com.zjlp.face.web.component.metaq.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;

@Component
public class OrderJobMetaOperateProducer {

	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired(required=false)
	private MetaQProviderClinet metaQProviderClinet;
	
	public void closeOrder(String orderNo){
		_logger.info("Method(closeOrder).Param(OrderNo):"+orderNo+" currentThread："+Thread.currentThread().getName());
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", "closeOrder");
			param.put("data", orderNo);
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
	
	public void compileOrder(String orderNo){
		_logger.info("Method(compileOrder).Param(OrderNo):"+orderNo+" currentThread："+Thread.currentThread().getName());
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", "compileOrder");
			param.put("data", orderNo);
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
	
	public void confirmAppointOrder(String orderNo){
		_logger.info("Method(confirmAppointOrder).Param(OrderNo):"+orderNo+" currentThread："+Thread.currentThread().getName());
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", "confirmAppointOrder");
			param.put("data", orderNo);
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
	
	public void receiveOrder(String orderNo){
		_logger.info("Method(receiveOrder).Param(OrderNo):"+orderNo+" currentThread："+Thread.currentThread().getName());
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", "receiveOrder");
			param.put("data", orderNo);
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
