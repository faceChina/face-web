package com.jzwgj.product.good.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.service.ClassificationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class ClassificationServiceTest {
	
	@Autowired
	private ClassificationService classificationService;
	
	@Test
	public void test(){

			Executor executor = Executors.newCachedThreadPool();
			for (int i = 0; i < 1000000; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 100000; j++) {
						Long classificationId = 2L;
						Classification classification = classificationService.getClassificationById(classificationId);
						System.out.println(Thread.currentThread()+classification.getName());
					}
				}
			});
		}
	}
}
