package com.example.demo.note.thread;


/**
 * 线程终止测试类
 * @author Administrator
 */
public class ThreadInterruptTest {
	
	public static void main(String[] args) throws InterruptedException{
		Thread th1 = new Thread(new TestRunable());
		System.out.println("thread stard ……");
		th1.start();
		Thread.sleep(3000);
		System.out.println("thread interrupt ……");
		th1.interrupt();
	}
	
	static class TestRunable implements Runnable{
		@Override
		public void run() {
			int i = 0;
			while(!Thread.interrupted()){
				System.out.println("Thread is running ……" + (++i));
				long time = System.currentTimeMillis();
//				while(System.currentTimeMillis() - time < 1000){
//					//程序循环1秒钟，不同于sleep(1000)会阻塞进程。  
//				}
				try {
					Thread.sleep(30*1000);
					System.out.println("Thread sleep :" + (System.currentTimeMillis() - time));
				} catch (InterruptedException e) { //抛出异常是为了线程从阻塞状态醒过来
					//抛出异常后中断表示置成false
					System.out.println("当前中断位：" + Thread.interrupted());
					//需再次打断
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
