package com.zjlp.face.web.util;

import org.apache.log4j.Logger;

/**
 * 日志打印
 * @ClassName: PrintLog 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月28日 上午10:26:47
 */
public final class PrintLog {

	private StringBuilder logEntity = new StringBuilder();
	private Logger log = null;
	public PrintLog(){
		this.log = Logger.getLogger(getClass());
	}
	public PrintLog(Class<?> clazz){
		this.log = Logger.getLogger(clazz);
	}
	public PrintLog(String logName){
		this.log = Logger.getLogger(logName);
	}
	public PrintLog(Logger log){
		this.log = log;
	}
	
	public void writeLog(Object...msgs) {
		if (null == msgs || 0 == msgs.length) {
			return;
		}
		logEntity.delete(0, logEntity.length());
		for (Object msg : msgs) {
			logEntity.append(String.valueOf(msg));
		}
		log.info(logEntity.toString());
	}
	
	public void writeDebugLog(Object...msgs){
//		if (!log.isDebugEnabled()){
//			return;
//		}
		if (null == msgs || 0 == msgs.length) {
			return;
		}
		logEntity.delete(0, logEntity.length());
		for (Object msg : msgs) {
			logEntity.append(String.valueOf(msg));
		}
		System.out.println(logEntity);
		log.debug(logEntity.toString());
	}
}
