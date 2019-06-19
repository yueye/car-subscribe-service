/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: dfdf
 * Author:   liujx
 * Date:     2019/6/18 17:07
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot.run;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/6/18
 */

import com.alibaba.fastjson.JSON;
import com.ezviz.open.callback.AbstractMessageCallBack;
import com.ezviz.open.client.EzvizOpenClientBuilder;
import com.ezviz.open.model.EzvizVehicleMessage;
import com.ezviz.open.model.MessageContent;
import com.sxsd.car.redis.RedisMsg;
import com.sxsd.car.utils.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Component
@Order(1)
public class ReceiveMessageRunner implements CommandLineRunner {

    private static final String APP_KEY = ResourceBundleUtil.get("ezvizopen.appkey");
    private static final String APP_SECRET = ResourceBundleUtil.get("ezvizopen.appSecret");
    private static final Logger log =  LoggerFactory.getLogger(ReceiveMessageRunner.class);

    @Resource(name = "taskExecutor")
    protected ThreadPoolTaskExecutor executor;
    @Autowired
    protected RedisMsg redisMsg;

    @Override
    public void run(String... args) {
        log.info("receiveMessageRunner init");
        CountDownLatch latch = new CountDownLatch(1);
        //初始化客户端并设置回调
        EzvizOpenClientBuilder.init(APP_KEY, APP_SECRET, executor.getThreadPoolExecutor(), new AbstractMessageCallBack() {

            /**
             * 获取车辆检测消息回调方法（可选）
             * @param message 车辆检测消息结构体
             */
            @Override
            public void onVehicleMessageReceived(EzvizVehicleMessage message) {
                log.error("获取车辆检测消息回调：" + JSON.toJSONString(message));
            }

            @Override
            public <T> void onOtherMessageReceived(MessageContent<T> message) {
                log.error("其他消息回调：" + JSON.toJSONString(message));
                super.onOtherMessageReceived(message);
            }

            /**
             * 获取异常消息回调方法（可选）
             * @param message 异常消息结构体
             */
            @Override
            public <T> void errorMessageHandler(MessageContent<T> message) {
                log.error("异常消息回调：" + JSON.toJSONString(message));
            }

        });
        //开始监听
        EzvizOpenClientBuilder.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            //log.error(e.getMessage());
            //e.printStackTrace();
        }
    }
}