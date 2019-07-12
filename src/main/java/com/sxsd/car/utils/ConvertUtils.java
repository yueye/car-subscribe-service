/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: ConvertUtils
 * Author:   liujx
 * Date:     2019/7/4 17:51
 * Description:
 * History:
 * 描述
 */
package com.sxsd.car.utils;

import com.sxsd.base.Constants;
import com.sxsd.springboot.vo.ConsumerCallBackVo;
import com.sxsd.springboot.vo.EventNotificationAlert;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/7/4
 */
public class ConvertUtils {
    public static Map<String,String> getVehicle(ConsumerCallBackVo vo){
        Map<String,String> map = new HashMap<>();
        if(vo == null){
            return map;
        }
        map.put("deviceId",vo.getHeader().getDeviceId());
        if(Constants.IS_API.equals(vo.getHeader().getType())){
            //处理特殊字符
            String payload = vo.getBody().getPayload().replaceAll("\\\\u00","").replaceAll("`","");
            EventNotificationAlert eventNotificationAlert = (EventNotificationAlert)XMLObjectUtils.xmlToObj(EventNotificationAlert.class,payload);
            if("vehicle".equals(eventNotificationAlert.getANPR().getVehicleType())){
                map.put("plateNumber",eventNotificationAlert.getANPR().getLicensePlate());
            }
        }
        if(Constants.VEHICLE_PLATE.equals(vo.getHeader().getType())){
            map.put("plateNumber",vo.getBody().getPlateNumber());
            map.put("picUrl",vo.getBody().getPicUrl());
        }
        if(Constants.CAR_API.equals(vo.getHeader().getType())){
            map.put("plateNumber",vo.getBody().getResults().get(0).getPlateNumber());
            map.put("picUrl",vo.getBody().getPicUrl());
        }
        return map;
    }
}