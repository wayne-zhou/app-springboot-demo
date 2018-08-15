package com.example.demo.note.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 线程池测试类
 * @author Administrator
 */
public class ExecutorsTest {
	
	final static CountDownLatch begin = new CountDownLatch(1); 
	final static CountDownLatch end = new CountDownLatch(100);
	
	private static AtomicLong n = new AtomicLong(0);

	public static void main(String[] args) throws Exception {
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 40, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(60));
		for(int i=0;i<100;i++){
			final String name = "任务【~"+(i+1)+"~】";
			Future<String> result = executorService.submit(new MyCallable(name));
			resultList.add(result);
		}
		
		//任务已创建完成 
		 TimeUnit.MILLISECONDS.sleep(1000);
		 System.out.println("所有任务提交完毕--------------------");
		 System.out.println("提交总任务数："+ executorService.getTaskCount());
		 System.out.println("当前活动线程数："+ executorService.getActiveCount());
		 System.out.println("当前队列中任务数："+ executorService.getQueue().size());
		 System.out.println("放开栅栏，线程开始处理业务--------------");
		 begin.countDown();  
	     end.await(); 
	     System.out.println("所有任务都已执行---------------");
	     System.out.println("当前线程数："+ executorService.getPoolSize());
	     System.out.println("共执行任务数："+ n.toString());
	     TimeUnit.MILLISECONDS.sleep(2000);
	     System.out.println("当前线程数："+ executorService.getPoolSize());
	     executorService.shutdown();
	     executorService.awaitTermination(2000, TimeUnit.MILLISECONDS); //阻塞主线程，等待线程池结束
	     /*System.out.println("活动执行结果：-------------------------------");
	     for(Future<String> future : resultList){
	    	 if(future.isDone()){
	    		 System.out.println(future.get());
	    	 }
	     }*/
	     System.out.println("ExecoutTest end-------------------------------");
	}
	
	static class MyCallable implements Callable<String>{
		
		String name;

		MyCallable(String name) {
			super();
			this.name = name;
		}

		@Override
		public String call() throws Exception {
			System.out.println(this.name+"准备好了！当前线程名："+Thread.currentThread().getName());
			begin.await();
			n.addAndGet(1);
			end.countDown();
			return name;
		}
	}
	
	
}
