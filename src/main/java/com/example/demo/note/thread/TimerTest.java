package com.example.demo.note.thread;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * java定时器
 * @author Administrator
 */
public class TimerTest {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("当前时间："+ new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 5);
		Timer timer = new Timer(true); //守护线程
		timer.schedule(new MyTask(), cal.getTime());
		Thread.sleep(6000);
	}
	
	static class MyTask extends TimerTask{
		@Override
		public void run() {
			System.out.println("任务执行时间："+ new Date());
		}
		
	} 

}
