package com.example.demo.note.thread;


/**
 * Thread.join测试类
 * @author Administrator
 */
public class ThreadJoinTest {
	
	public static void main(String[] args) throws Exception{
		Thread th1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("Thread sleep stard");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				System.out.println("Thread sleep end");
			}
		});
		
		th1.start();
		th1.join(); //阻塞主线程，等待调用线程th1执行完毕恢复主线程
		System.out.println("mainThread end");
	
	}
	

}
