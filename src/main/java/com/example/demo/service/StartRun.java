package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动后执行
 * Created by zhouwei on 2018/1/10
 **/
@Component
@Order(value = 1)  //定义执行顺序
public class StartRun implements ApplicationRunner {
    private static  final Logger logger = LoggerFactory.getLogger(StartRun.class);

    @Autowired
    private StartService startService;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("-------------StartRun start---------------");
        startService.excutor();
        logger.info("-------------StartRun end---------------");
    }
}
