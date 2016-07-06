package com.zjlp.face.web.job.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.zjlp.face.web.job.init.impl.LoadAddressJob;

@Component
public class DsrPostProcessor implements BeanPostProcessor {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		if (bean instanceof LoadAddressJob) {
			LoadAddressJob ldaddress = (LoadAddressJob) bean;
			try {
				ldaddress.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return bean;
	}  
}
