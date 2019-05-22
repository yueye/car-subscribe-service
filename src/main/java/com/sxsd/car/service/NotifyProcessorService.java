/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: NotifyProcessorService
 * Author:   liujx
 * Date:     2019/5/14 11:19
 * Description:
 * History:
 * 描述
 */
package com.sxsd.car.service;

import com.alibaba.fastjson.JSON;
import com.sxsd.car.base.CommonService;
import com.sxsd.car.utils.HttpTookit;
import com.sxsd.car.utils.StepCalculator;
import jodd.util.ThreadUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/5/14
 */
@Service
public class NotifyProcessorService extends CommonService {
    private static Logger logs =  LoggerFactory.getLogger(NotifyProcessorService.class);
    private static Integer THREAD_SLEEPTIME = 1000; // s
    private static Integer additionTime = 5 * 60; // s

    public void exe(){
        // 启动时候确定key值
        Long time = System.currentTimeMillis();
        String key = StepCalculator.getKey(time, 0);
        logs.info("线程 key : " + key + " 启动.");

        while (true) {
            // 超过生命周期，自动退出
            if (time + additionTime * 1000l < System.currentTimeMillis()) {
                logs.info("线程 key : " + key + " 结束.");
                // 删除redis key
                redisPay.delNotifyPayOrder(key);
                break;
            }
            try{
                String itemStr = redisPay.getNotifyPayOrder(key);
                if (StringUtils.isBlank(itemStr)) {
                    ThreadUtil.sleep(THREAD_SLEEPTIME);
                    continue;
                }
                logs.info("获取通知信息 : " + itemStr + "," + key);
                Map<String, String> item = JSON.parseObject(itemStr,Map.class);
                if (MapUtils.isEmpty(item)) {
                    superLog.getLogger().error("通知信息为空");
                    continue;
                }
                processData(item, key);
            }catch (Exception e) {
                ThreadUtil.sleep(THREAD_SLEEPTIME);
            }
        }
    }

    private void processData(Map<String, String> item, String key) {
        String urlString = item.get("url");
        String params = item.get("params");
        String time = item.get("time");
        String times = item.get("times");

        if (StringUtils.isBlank(urlString))
            return;

        long current = System.currentTimeMillis();
        Long schedual = StepCalculator.getTime(Integer.valueOf(times), Long.valueOf(time));
        if (null == schedual) {
            logs.error("通知已超过定时上限. " + schedual);
            logs.error(item.toString());
            return;
        }

        // 时间没到
        if (current < schedual.longValue()) {
            reputHandle(item, key);
            return;
        }

        try {
            String resp = HttpTookit.doGet(logs,urlString + "?" + params);
            superLog.getLogger().info("resp : " + resp);

            if (null != resp && resp.trim().equalsIgnoreCase("success")) {
                sucHandle(urlString);
                return;
            }

        } catch (Exception e) {
            superLog.getLogger().error(e.getMessage(), e);
            superLog.getLogger().error("request url error. " + urlString + "?" + params);
        }
        // 失败处理
        failHandle(item);
    }

    private void failHandle(Map<String, String> item) {
        if (MapUtils.isEmpty(item))
            return;

        String times = item.get("times");
        String time = item.get("time");

        StringBuffer sb = new StringBuffer();
        sb.append(times).append(",");
        sb.append(", from : ").append(time);
        sb.append(", url : ").append(item.get("url"));
        sb.append("?").append(item.get("params"));

        Integer ts = Integer.valueOf(times);
        String key = StepCalculator.getNextKey(Long.valueOf(time), ts);
        if (StringUtils.isBlank(key)) {
            logs.error("exceed notify times, " + sb.toString());
            return;
        }
        // update times
        item.put("times", String.valueOf(ts + 1));

        sb.append(", next time : ").append(key);

        String newItem = JSON.toJSONString(item);
        logs.info("通知失败,下次通知列表 : " + newItem);
        redisPay.rpushNotifyPayOrder(key,newItem);
        logs.info("通知失败, 下次通知时间, " + key + ", " + sb.toString());
    }

    private void sucHandle(String urlString) {
        logs.info("异步通知成功：" + urlString);
    }

    private void reputHandle(Map<String, String> item, String key) {
        if (MapUtils.isEmpty(item))
            return;

        redisPay.rpushNotifyPayOrder(key,JSON.toJSONString(item));
        logs.info("重新加入队列, " + key + ", " + item.toString());

        ThreadUtil.sleep(THREAD_SLEEPTIME);
    }
}