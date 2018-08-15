package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by zhouwei on 2018/1/10
 **/
@Service
public class StartService {
    private static  final Logger logger = LoggerFactory.getLogger(StartService.class);

    public void excutor(){
        logger.info("StartService.excutor 执行");
    }
}
