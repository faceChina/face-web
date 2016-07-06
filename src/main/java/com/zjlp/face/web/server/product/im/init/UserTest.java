package com.zjlp.face.web.server.product.im.init;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zjlp.face.web.server.product.im.domain.ImUser;

public class UserTest {

	public static void main(String[] args) {
		try {
//			ImAnonymousUserBuilder builder = ImAnonymousUserBuilder.getInstance();
//			builder.build(5);
			//并发测试
			   ExecutorService executor = Executors.newFixedThreadPool(2);
		        for (int i = 0; i < 400; i++) {
		            executor.execute(new Runnable() {
		    			@Override
		    			public void run() {
		    					ImUser imUser = null;
								try {
									imUser = ImAnonymousUserPool.getInstance().getUser();
								} catch (Exception e) {
									e.printStackTrace();
								}
		    					System.out.println(Thread.currentThread().getName()+"获得用户"+imUser.getNickname());
		    					long sleeptime= (long) (Math.random()*1000);
		    					System.out.println(imUser.getNickname()+"用户在线聊天"+sleeptime+"毫秒秒");
		    					try {
									Thread.sleep(sleeptime);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}//随机数
//		    					UserTimer timer = new UserTimer();  
//		    					timer.schedule(new UserTimerColseTask(imUser), 1000);
		    			}
		    		});
		          }
			   ExecutorService executor2 = Executors.newFixedThreadPool(2);
		       for (int i = 0; i < 200; i++) {
		    	   executor2.execute(new Runnable() {
		   			@Override
		   			public void run() {
	    					ImUser imUser = null;
							try {
								imUser = ImAnonymousUserPool.getInstance().getUser();
							} catch (Exception e) {
								e.printStackTrace();
							}
	    					System.out.println(Thread.currentThread().getName()+"获得用户"+imUser.getNickname());
	    					long sleeptime = (long) (Math.random()*1000);
	    					System.out.println(imUser.getNickname()+"用户在线聊天"+sleeptime+"毫秒秒");
	    					try {
								Thread.sleep(sleeptime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//随机数
//	    					UserTimer timer = new UserTimer();  
//	    					timer.schedule(new UserTimerColseTask(imUser), 1000);
		   			}
		   		});
		         }
		       executor.shutdown();
		       while (!executor.isTerminated()) {
		       }
		       executor2.shutdown();
		       while (!executor2.isTerminated()) {
		       }
		       System.out.println("Finished all threads");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//打印
//			ImAnonymousUserPool.getInstance().printPoolStates();
		}
	}
}
