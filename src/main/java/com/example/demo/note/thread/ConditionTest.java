package com.example.demo.note.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 线程间通讯test
 * @author Administrator
 */
public class ConditionTest {
	
	final Lock lock = new ReentrantLock();  //对象锁
	final Condition notFull = lock.newCondition(); //写线程条件
	final Condition notEmpty = lock.newCondition(); //读线程条件
	final Object[] items = new Object[10];//缓存队列
	
	int putIndex; //写索引
	int takeIndex;//读索引
	int count;//队列中存在数据的个数
	
	
	public static void main(String[] args) throws InterruptedException {
		final ConditionTest test = new ConditionTest();
		for(int i =0;i<15;i++){
			final int n = i+1;
			//读线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						test.take();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}).start();;
			//写线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						test.put(n);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}).start();;
		}
		
		Thread.sleep(5000);
	}
	
	
	public void put (Object x) throws InterruptedException{
		lock.lock();
		try{
			while(count == items.length){ //写满了
				notFull.await(); //阻塞写线程
			}
			items[putIndex] = x; //赋值
			System.out.println("++++++ put["+putIndex+"]:" + x);
			if(++putIndex == items.length){
				putIndex = 0; //写索引到最后一个位置了，置为0从头开始
			}
			++count;
			notEmpty.signal();//唤醒读线程
		}finally{
			lock.unlock();
		}
	}
	
	public Object take() throws InterruptedException{
		lock.lock();
		try{
			while(count == 0){//队列为空
				notEmpty.await(); //阻塞写线程
			}
			Object x = items[takeIndex]; //赋值
			System.out.println("------ take["+takeIndex+"]:" + x);
			if(++takeIndex == items.length){
				takeIndex = 0; //读索引到最后一个位置了，置为0从头开始
			}
			--count;
			notFull.signal();//唤醒读线程
			return x;
		}finally{
			lock.unlock();
		}
		
	}
	
}
