package com.sxsd.springboot.config;

import com.alibaba.fastjson.JSON;
import com.ezviz.open.callback.AbstractMessageCallBack;
import com.ezviz.open.client.EzvizOpenClientBuilder;
import com.ezviz.open.model.EzvizVehicleMessage;
import com.ezviz.open.model.MessageContent;
import com.sxsd.car.listener.StartupListener;
import com.sxsd.car.redis.RedisMsg;
import com.sxsd.car.utils.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Configuration
public class ReceiveMessageConfig {

	private static final Logger log =  LoggerFactory.getLogger(ReceiveMessageConfig.class);
	private static final String APPKEY = ResourceBundleUtil.get("receiveMessage.appkey");
	private static final String SECRET = ResourceBundleUtil.get("receiveMessage.secert");

	@Resource(name="taskExecutor")
	protected ThreadPoolTaskExecutor executor;
	@Autowired
	protected RedisMsg redisMsg;

	public void exe() {
		CountDownLatch latch = new CountDownLatch(1);
		//初始化客户端并设置回调
		EzvizOpenClientBuilder.init(APPKEY, SECRET, executor.getThreadPoolExecutor(), new AbstractMessageCallBack() {

			/**
			 * 获取车辆检测消息回调方法（可选）
			 * @param message 车辆检测消息结构体
			 */
			@Override
			public void onVehicleMessageReceived(EzvizVehicleMessage message) {
				log.error("获取车辆检测消息回调："+JSON.toJSONString(message));
			}

			/**
			 * 获取异常消息回调方法（可选）
			 * @param message 异常消息结构体
			 */
			@Override
			public <T> void errorMessageHandler(MessageContent<T> message) {
				log.error("异常消息回调："+JSON.toJSONString(message));
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