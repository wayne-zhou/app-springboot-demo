package com.example.demo;

import com.example.demo.common.utils.JsonUtil;
import com.example.demo.common.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhouwei on 2018/1/2
 **/
@Slf4j
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
        String json = "{\"result\": 1,\"resultMessage\": \"成功\",\"resultContent\": {},\"success\": true}";
        String errMsg = JsonUtil.jsonToMap(json).get("resultContent").toString();
        System.out.println(errMsg);

    }


    @Test
    public void test4(){
        String url ="";
        File sourceFile = new File("F:\\123.txt");
        File targetFile = new File("F:\\123_errMsg.txt");

        ExecutorService threadPoo = Executors.newFixedThreadPool(5) ;

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile, true)));
            String strLine = null;
            int n = 0;
            AtomicInteger count = new AtomicInteger(0);

            List<Param> list = new ArrayList<Param>(100);
            while ((strLine = br.readLine()) != null) {
                n++;
                String[] strS = strLine.split(",");
                Long listingId = Long.parseLong(strS[0]);
                Integer num = Integer.parseInt(strS[1]);

                list.add(new Param(listingId, num));
                if(n % 100 == 0){
                    threadPoo.submit(new ThreadHandle(url, list, bw, count));
                    list = new ArrayList<Param>(100);
                    System.out.println("当前提交到：" + n);
                }
            }

            if(!CollectionUtils.isEmpty(list)){
                threadPoo.submit(new ThreadHandle(url, list, bw, count));
            }
            System.out.println("提交完毕：" + n);

            threadPoo.shutdown();
            threadPoo.awaitTermination(300, TimeUnit.MINUTES); //阻塞主线程，等待线程池结束

            bw.flush();
            System.out.println("处理完毕：" + n);
        } catch (Exception e) {
            log.info("解析异常：", e);
        } finally {
            try {
                if(null != br){
                    br.close();
                }
                if(null != bw){
                    bw.close();
                }
            } catch (IOException e) {
                log.info("关闭流异常：", e);
            }
        }
    }

    class ThreadHandle implements Runnable {
        String url;

        List<Param> list;

        BufferedWriter bw;

        AtomicInteger count;

        public ThreadHandle(String url, List<Param> list, BufferedWriter bw, AtomicInteger count) {
            this.url = url;
            this.list = list;
            this.bw = bw;
            this.count = count;
        }

        @Override
        public void run() {
            try {
                String resp = HttpUtil.postHttpRequest(url, list);
                String msg = JsonUtil.jsonToMap(resp).get("resultContent").toString();
                if (!"{}".equals(msg)) {
                    bw.write(msg + "\n");
                }
                System.out.println("当前处理到"+ count.addAndGet(list.size()));
            }catch (Exception e){
                log.error("执行异常： list:{}, 异常：", JsonUtil.objectToJson(list) , e);
            }
        }
    }

    static class Param{
        Long listingId;
        Integer number;

        public Param(Long listingId, Integer number) {
            this.listingId = listingId;
            this.number = number;
        }

        public Long getListingId() {
            return listingId;
        }

        public void setListingId(Long listingId) {
            this.listingId = listingId;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }


}
