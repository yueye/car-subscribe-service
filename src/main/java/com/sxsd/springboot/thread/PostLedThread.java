/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: f
 * Author:   liujx
 * Date:     2019/7/3 13:12
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot.thread;

import com.alibaba.fastjson.JSON;
import com.sxsd.car.utils.ConvertUtils;
import com.sxsd.car.utils.http.HttpClientUtil;
import com.sxsd.springboot.vo.ConsumerCallBackVo;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/7/3
 */
public class PostLedThread implements Runnable {

    private Logger logger;
    private ConsumerCallBackVo consumerCallBackVo;

    private static final String url ="http://api.waterbears-era.com/led/sendContentToLed";
    private static final String ledLink ="http://47.104.190.7:30001";

    public PostLedThread(Logger logger, ConsumerCallBackVo consumerCallBackVo) {
        super();
        this.logger = logger;
        this.consumerCallBackVo = consumerCallBackVo;
    }

    @Override
    public void run() {
        Map<String,String> map = ConvertUtils.getVehicle(consumerCallBackVo);
        if(!map.containsKey("deviceId") || !map.containsKey("plateNumber")){
            return ;
        }
        Map<String,String> params = new HashMap<>();
        params.put("ledLink",ledLink);
        params.put("line1",map.get("plateNumber"));
        params.put("line2","进来了");
        params.put("type","2");
        logger.info("发送led网关 请求参数："+url+" "+ JSON.toJSONString(params));
        Map<String,String> s = HttpClientUtil.doGet(url,params);
        logger.info("发送led网关 返回结果："+ JSON.toJSONString(params));
    }

}