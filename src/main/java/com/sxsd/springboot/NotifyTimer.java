package com.sxsd.springboot;

import com.sxsd.car.service.NotifyProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * 注解形式的定时任务调度
 */

@Service
@EnableAsync
@EnableScheduling
public class NotifyTimer {
    @Autowired
    NotifyProcessorService notifyProcessorService;
    Logger log = LoggerFactory.getLogger(NotifyTimer.class);

    /**
     * 异步订单通知队列
     */
    @Async("taskExecutor")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void notifyProcessor() {
        notifyProcessorService.exe();
    }
}
