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
import com.sxsd.car.redis.RedisMsg;
import com.sxsd.car.utils.ResourceBundleUtil;
import com.sxsd.springboot.thread.ConsumerCallBackVoThread;
import com.sxsd.springboot.vo.ConsumerCallBackVo;
import com.ys.product.ysmq.front.msg.StandardConsumerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Order(1)
public class ReceiveMessageRunner implements CommandLineRunner {

    private static final String APP_KEY = ResourceBundleUtil.get("ezvizopen.appkey");
    private static final String APP_SECRET = ResourceBundleUtil.get("ezvizopen.appSecret");
    private static final Logger log =  LoggerFactory.getLogger(ReceiveMessageRunner.class);
    private static final String GROUP = "group3";
    private static final String PATH = "https://open.ys7.com"; //开放平台的url,这是test环境的url https://test.ys7.com:65
    private static final long consumeIntervalTime = 200;
    private static final String VEHICLE = "ys.open.vehicle";

    private static BlockingQueue<List<Object>> msgQueue = new LinkedBlockingQueue<>();

//    @Resource(name = "httpClientExecutor")
//    protected ThreadPoolTaskExecutor httpClientExecutor;
    @Resource(name = "taskExecutor")
    protected ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    protected RedisMsg redisMsg;

    @Override
    public void run(String... args) {
        log.info("receiveMessageRunner init");
        AsyncHandleThread asyncHandleThread = new AsyncHandleThread();
        asyncHandleThread.start();
        //启动SDK消费消息
        init();
    }

    public static void init() {
        // 设置你的appKey,appSecret,group
        StandardConsumerMessage consumerMessage = new StandardConsumerMessage(PATH, APP_KEY, APP_SECRET, GROUP);
        // 设置调用接口的间隔时间,ms
        consumerMessage.setConsumeIntervalTime(consumeIntervalTime);
        // 设置消费消息的回调接口,建议把消息放入本地队列,不要在回调函数里做业务处理.
        consumerMessage.setConsumerCallBack(msg -> {
            try {
//                log.info("查询消息通道："+msg.size());
                if(msg.size()>0){
                    msgQueue.put(msg);
                }
            } catch (InterruptedException e) {
                // 用户这里需要处理异常
                log.error(String.format("放入队列失败,msgs:", msg), e);
            }
        });
        consumerMessage.initClient();
    }

    /**
     * <p>
     * 异步处理消息的线程,消息量非常大的时候,请使用线程池.
     * </p>
     */
    class AsyncHandleThread extends Thread {
        AtomicBoolean isRunning = new AtomicBoolean(true);
        public AsyncHandleThread() {
            super("Async-HandleMsg-Thread");
        }

        @Override
        public void run() {
            while (isRunning.get()) {
                try {
                    List<Object> msgs = msgQueue.take();
                    userProcessMsgFunction(msgs);
                } catch (InterruptedException e) {
                    // 用户这里需要处理异常
                    log.error("线程中断异常", e);
                }
            }
        }
        // 关闭线程
        public void shutdown() {
            isRunning.set(false);
        }
    }

    /**
     * <p>
     * 用户自己的处理消息的函数
     * </p>
     *
     * @param msgs
     */
    void userProcessMsgFunction(List<Object> msgs) {
        log.info("异步处理消息,消息数量:", msgs.size());
//        if (msgs.size() > 100) {
//            //消息太多,只打印前100个
//            log.info("msgSize:{},msg:{}", msgs.size(), msgs.subList(0, 100));
//        }
        for (Object object : msgs) {
            log.info("原始消息："+object.toString());
            ConsumerCallBackVo vo = JSON.parseObject(object.toString(),ConsumerCallBackVo.class);
            taskExecutor.execute(new ConsumerCallBackVoThread(log,redisMsg,vo));
        }
    }
}