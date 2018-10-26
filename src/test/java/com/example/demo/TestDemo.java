package com.example.demo;

import com.example.demo.common.utils.date.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;

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
        LocalDateTime date = DateTimeUtil.minu(LocalDateTime.now(), 440, ChronoUnit.MINUTES);
        System.out.println(DateTimeUtil.formatTime(date));

    }

    @Test
    public void test4(){
        String backId = "908872183084953601,911146723613806593,923890115091329025,933551668929523713,935368230275677185,936060679826461697,936060679826461702,936194665512989699,939770290043640838,943354322073445382,945566670792188929,951151408760251422,952410826583861249,953814825732165685,954883964345275439,958252571490022429,958274648368507911,958282402214734855,961633936956739586,962130655796547613,963723520469389313,969098828303323166,971798101641949203,973396779053896706,973832142486526983,973832142486526984,973832142490721280,974104617246023682,974160072722894885,975009430477499429,977897867836683276,978975529107470336,979228658910908418,980735588250701825,981097970160397363,981131771905331209,981131782747607051,981134470205952007,981134508114071559,981135225293918215,981137320176799751,981137544165216263,981139539211079687,981139791888535559,981139937258917895,981141722065293319,981141757616214023,981143610030575623,981145655244509191,981147687980388359,981151007268687879,981153403759775755,981156827263946763,981157190855577607,981157225781547015,981162463997480967,981162711000043531,981163503769636875,981164969574027271,981165525050871815,981363475588861953,981363475588861954,984743951795763201";
        List<String> backIdList = Arrays.asList(backId.split(","));
        File sourceFile = new File("F:\\105条漏推.txt");
        File targetFile = new File("F:\\sjsh.txt");
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile, true)));
            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                String[] strS = strLine.split(",");
                if(backIdList.contains(strS[1]))
                bw.write(strLine+"\n");
            }
            bw.flush();
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


}
