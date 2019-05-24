/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: dsf
 * Author:   liujx
 * Date:     2019/4/30 12:44
 * Description:
 * History:
 * 描述
 */
package com.sxsd.car.controllar;

import com.ezviz.open.callback.AbstractMessageCallBack;
import com.ezviz.open.client.EzvizOpenClientBuilder;
import com.ezviz.open.model.*;
import com.sxsd.base.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/4/30
 */
@RestController
public class TestController extends BaseApiController {

    @Resource(name="taskExecutor")
    protected ThreadPoolTaskExecutor executor;
    /**
     * 请输入申请的AppKey
     */
    private static final String APPKEY = "e2b4dfb1313f4d65b0a73cf5e493c1ca";
    /**
     * 请输入申请的Secret
     */
    private static final String SECRET = "67b0b1d6ce57bedfad6127164108ee8f";

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/receiveMessage")
    public void receiveMessage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        //初始化客户端并设置回调
        EzvizOpenClientBuilder.init(APPKEY, SECRET, executor.getThreadPoolExecutor(), new AbstractMessageCallBack() {

            /**
             * 获取车辆检测消息回调方法（可选）
             * @param message 车辆检测消息结构体
             */
            @Override
            public void onVehicleMessageReceived(EzvizVehicleMessage message) {
                System.out.println(message);
            }

            /**
             * 获取异常消息回调方法（可选）
             * @param message 异常消息结构体
             */
            @Override
            public <T> void errorMessageHandler(MessageContent<T> message) {
                System.out.println(message);
            }
        });
        //开始监听
        EzvizOpenClientBuilder.start();
        latch.await();
    }


}