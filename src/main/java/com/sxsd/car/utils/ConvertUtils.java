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
    public static String onOffLine(String msgType){
        String res = "未知";
        if("OFFLINE".equals(msgType)){
            res = "下线";
        }
        if("ONLINE".equals(msgType)){
            res = "上线线";
        }
        return res;
    }

    public static Map<String,String> getVehicle(ConsumerCallBackVo vo){
        Map<String,String> map = new HashMap<>();
        if(vo == null){
            return map;
        }
        map.put("deviceId",vo.getHeader().getDeviceId());
        if(Constants.IS_API.equals(vo.getHeader().getType())){
            //处理特殊字符
            String payload = vo.getBody().getPayload();
            payload = stripNonValidXMLCharacters(payload).replaceAll("`","");
            EventNotificationAlert eventNotificationAlert = (EventNotificationAlert)XMLObjectUtils.xmlToObj(EventNotificationAlert.class,payload);
            if("vehicle".equals(eventNotificationAlert.getANPR().getVehicleType())){
                map.put("plateNumber",eventNotificationAlert.getANPR().getLicensePlate());
                String direction = eventNotificationAlert.getANPR().getEntranceInfo().getDirection();
                if("leave".equals(eventNotificationAlert.getANPR().getEntranceInfo().getDirection())){
                    map.put("direction",direction+"--离开");
                }else{
                    map.put("direction",direction+"--进入");
                }

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

    // 保留合法字符
    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                    (current == 0xA) ||
                    (current == 0xD) ||
                    ((current >= 0x20) && (current <= 0xD7FF)) ||
                    ((current >= 0xE000) && (current <= 0xFFFD)) ||
                    ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }
}