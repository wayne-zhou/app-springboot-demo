package com.example.demo.note.thread;


/**
 * 线程租
 * @author Administrator
 */
public class ThreadGroupTest {
	
	public static void main(String[] args) throws InterruptedException {
		ThreadTest a = new ThreadTest();
		ThreadTest b = new ThreadTest();
		ThreadGroup group = new ThreadGroup("TestGroup");
		Thread ta = new Thread(group, a);
		Thread tb = new Thread(group, b);
		ta.start();
		tb.start();
		Thread.sleep(1000);
		System.out.println("线程组的名字:" + group.getName());
		System.out.println("活动的线程数:" + group.activeCount());
		group.interrupt();
	}
	
	static class ThreadTest implements Runnable{
		@Override
		public void run() {
			int i =0;
			while(!Thread.currentThread().isInterrupted()){
				System.out.println(Thread.currentThread().getName() + (++i));
			}
		}
		
	}
}
