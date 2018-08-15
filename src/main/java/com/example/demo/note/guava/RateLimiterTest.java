package com.example.demo.note.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouwei on 2018/8/15
 * 限流器
 **/
public class RateLimiterTest {

    public static void main(String[] args) throws InterruptedException {

        //创建令牌桶(每秒钟生成令牌数)，参数通常=qps
        RateLimiter rateLimiter = RateLimiter.create(1);

        //尝试获取令牌，50ms超时
        boolean flag = rateLimiter.tryAcquire(3,50, TimeUnit.MICROSECONDS);
        System.out.println(flag);

        //获取令牌，没有时阻塞线程，返回阻塞时间s
        double waitTime = rateLimiter.acquire();
        System.out.println(waitTime);

        //可实时改变速率
        rateLimiter.setRate(10);
        waitTime = rateLimiter.acquire(5);
        System.out.println(waitTime);


        /**
         * 信号量(并发数控制)
         */
        Semaphore semaphore = new Semaphore(2);
        //获取许可
        semaphore.acquire(2);
        boolean result = semaphore.tryAcquire();
        System.out.println(result);
        //需要手动释放
        semaphore.release(2);
    }

}
