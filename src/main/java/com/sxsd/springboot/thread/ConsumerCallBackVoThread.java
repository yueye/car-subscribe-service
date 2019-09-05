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
import com.sxsd.base.Constants;
import com.sxsd.car.redis.RedisMsg;
import com.sxsd.car.utils.ConvertUtils;
import com.sxsd.car.utils.http.HttpClientUtil;
import com.sxsd.springboot.vo.ConsumerCallBackVo;
import com.sxsd.springboot.vo.VehiclePropsAndTraffic;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/7/3
 */
public class ConsumerCallBackVoThread implements Runnable {

    private Logger logger;
    private ConsumerCallBackVo consumerCallBackVo;
    private RedisMsg redisMsg;

    private static final String url ="http://api.waterbears-era.com/led/sendContentToLed";
    private static final String ledLink ="http://47.104.190.7:30001";
    private static final String vehicle_props_url = "https://open.ys7.com/api/lapp/intelligence/vehicle/analysis/props";

    public ConsumerCallBackVoThread(Logger logger,RedisMsg redisMsg, ConsumerCallBackVo consumerCallBackVo) {
        super();
        this.logger = logger;
        this.consumerCallBackVo = consumerCallBackVo;
        this.redisMsg = redisMsg;
    }

    @Override
    public void run() {
        try{
            //设备上下线通知
            if(Constants.ON_OFF_LINE.equals(consumerCallBackVo.getHeader().getType())){
                logger.info("设备上下线通知：设备号："+consumerCallBackVo.getHeader().getDeviceId()+
                        "--状态：" +ConvertUtils.onOffLine(consumerCallBackVo.getBody().getMsgType())+
                        "--触发时间："+consumerCallBackVo.getBody().getOccurTime()+"");
                return ;
            }
            //本地消息通知
            if(Constants.IS_API.equals(consumerCallBackVo.getHeader().getType())){
                Map<String,String> map = ConvertUtils.getVehicle(consumerCallBackVo);
                logger.info("本地消息："+map.get("plateNumber")+"--"+map.get("direction"));
                return ;
            }
            //车牌识别&车牌识别+车型识别
            Map<String,String> map = ConvertUtils.getVehicle(consumerCallBackVo);
            if(!map.containsKey("deviceId") || !map.containsKey("plateNumber")){
                logger.info("数据错误，不包含设备或者车牌");
                return ;
            }
            String picUrl = map.get("picUrl");
            if(StringUtils.isBlank(picUrl)){
                logger.error("图片为空");
                return ;
            }

            VehiclePropsAndTraffic vehiclePropsAndTraffic = getVehiclePropsAndTraffic(picUrl);
            if(!"200".equals(vehiclePropsAndTraffic.getCode())){
                logger.error("返回数据错误");
                return ;
            }
            String plateNumber = filterVehicle(vehiclePropsAndTraffic,map.get("plateNumber"));
            logger.info("=====================过滤后的返回结果:"+plateNumber+" ======================");
            post2Led(map.get("deviceId"),plateNumber);
        }catch (Exception e){
            logger.info("线程异常"+e.getMessage());
        }

    }

    private void post2Led(String deviceId,String plateNumber) {
        Map<String,String> params = new HashMap<>();
        params.put("ledLink",ledLink);
        params.put("line1",deviceId);
        params.put("line2",plateNumber);
        params.put("type","2");
        logger.info("发送led网关 请求参数："+url+" "+ JSON.toJSONString(params));
        Map<String,String> s = HttpClientUtil.retryDoGet(url,params,3);
        logger.info("发送led网关 返回结果："+ JSON.toJSONString(params));
    }

    protected String filterVehicle(VehiclePropsAndTraffic vehiclePropsAndTraffic, String apiPlateNumber) {
        List<VehiclePropsAndTraffic.Props> list = vehiclePropsAndTraffic.getData();
        List<String> res = new ArrayList<>();
        for(VehiclePropsAndTraffic.Props props:list){
           if(StringUtils.isBlank(props.getPlateNumber()) ||
                   "车牌".equals(props.getPlateNumber()) ||
                   "null".equals(props.getPlateNumber())){
               continue;
           }
           res.add(props.getPlateNumber());
        }
        logger.info("=====================本地识别结果--->"+apiPlateNumber+" 云端结果--->"+JSON.toJSONString(res)+" ======================");
        if(res.contains(apiPlateNumber)){
           return apiPlateNumber;
        }
        if(res.size()>0){
           return StringUtils.join(res,",");
        }
        return apiPlateNumber;
    }

    private VehiclePropsAndTraffic getVehiclePropsAndTraffic(String image) {
        String token = redisMsg.getEzvizopenToken();
        Map<String,String> params = new HashMap<>();
        params.put("accessToken",token);
        params.put("dataType","0");
        params.put("image",image);
        logger.info("请求萤石云车辆属性识别接口："+vehicle_props_url+" "+JSON.toJSONString(params));
        Map<String,String> res = HttpClientUtil.doPost(vehicle_props_url,params);
        String result = res.get("result");
        VehiclePropsAndTraffic vehiclePropsAndTraffic = JSON.parseObject(result, VehiclePropsAndTraffic.class);
        logger.info("请求萤石云车辆属性识别接口结果："+JSON.toJSONString(vehiclePropsAndTraffic));
        StringBuffer buffer = new StringBuffer();
        for(VehiclePropsAndTraffic.Props props:vehiclePropsAndTraffic.getData()){
            buffer.append(props.getPlateNumber()+" "+props.getVehicleLogo()+" "+props.getVehicleModel()+" "+
                    props.getVehicleSublogo()+" "+props.getVehicleType().getDes()+" "+props.getYellowLabel().getDes()+"\r\n");
        }
        logger.info("==================请求萤石云车辆属性识别接口结果简化:"+buffer.toString()+" =============================");
        return vehiclePropsAndTraffic;
    }

}