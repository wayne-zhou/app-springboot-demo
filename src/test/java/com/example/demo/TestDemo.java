package com.example.demo;

import com.example.demo.common.utils.date.DateTimeUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by zhouwei on 2018/1/2
 **/
public class TestDemo {

    @Test
    public void test1 (){
        Set<Long> set1 = new HashSet();
        set1.add(1L);
        set1.add(2L);
        Set<Long> set2 = new HashSet();
        set2.add(1L);

        set1.removeAll(set2);
        set1.forEach(a -> {
            System.out.println(a);
        });
    }

    @Test
    public void test2 (){
        ExecutorService threadPoo = Executors.newFixedThreadPool(3) ;

        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ "沉睡结束");
                return Thread.currentThread().getName();
            }
        };

        List<Future<String>> list = new ArrayList<>();
        list.add(threadPoo.submit(c));
        list.add(threadPoo.submit(c));
        list.add(threadPoo.submit(c));
        System.out.println("任务提交完毕");
        //测试Future.get 是否会阻塞主线程
        list.forEach(item -> {
            try {
                System.out.println(item.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadPoo.shutdown();
        System.out.println("线程池销毁");
    }

    @Test
    public void test3(){
        LocalDateTime date = DateTimeUtil.minu(LocalDateTime.now(), 440, ChronoUnit.MINUTES);
        System.out.println(DateTimeUtil.formatTime(date));

    }
}
