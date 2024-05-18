package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author LHF
 * @version 1.0
 * @date 2024/5/17 19:00
 */
@Component
@Slf4j
public class MyTask {

//    @Scheduled(cron = "0/5 * * * * *")
    public void execute(){
        System.out.println("测试案例，每隔五秒执行一次");
    }
}