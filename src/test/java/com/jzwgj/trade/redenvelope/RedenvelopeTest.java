package com.jzwgj.trade.redenvelope;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zjlp.face.jredis.jredis.RedisClientInitilize;
import com.zjlp.face.web.server.operation.redenvelope.processor.RedenvelopeProcessor;
import com.zjlp.face.web.server.operation.redenvelope.processor.impl.AllocRedenvelopeProcessor;
import com.zjlp.face.web.server.operation.redenvelope.processor.impl.GrabRedenvelopeProcessor;
import com.zjlp.face.web.server.operation.redenvelope.processor.impl.OpenRedenvelopeProcessor;
import com.zjlp.face.web.server.user.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
public class RedenvelopeTest {

//	@Autowired
//	private RedenvelopeProcessor allocRedenvelopeProcessor;
//	
//	@Autowired
//	private RedenvelopeProcessor grabRedenvelopeProcessor;
//	
//	@Autowired
//	private RedenvelopeProcessor openRedenvelopeProcessor;
	
	static final Long ID = 13L;
	static {
		try {
			RedisClientInitilize.getInstance().cacheClientPoolInitialized();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void alloc(final Long id){
		RedenvelopeProcessor allocRedenvelopeProcessor = new AllocRedenvelopeProcessor();
		allocRedenvelopeProcessor.allocRedenvelope(id);
	}
	
	public static void grab(final Long id) throws InterruptedException {
		final RedenvelopeProcessor grabRedenvelopeProcessor = new GrabRedenvelopeProcessor();
		final RedenvelopeProcessor openRedenvelopeProcessor = new OpenRedenvelopeProcessor();
		for (int i = 0; i < 100; i++) {
			final Long userId = Long.valueOf(i);
			new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("开启一个线程");
				User user = new User(userId);
				user.setNickname("哈哈");
				grabRedenvelopeProcessor.grabRedenvelope(user.getId(), id);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				openRedenvelopeProcessor.openRedenvelope(user, id); 
				System.out.println("结束一个线程");
			}
			}).start();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		for (long i = 1; i < 6; i++) {
			alloc(i);
			
			grab(i);
		}
	}
	
}
