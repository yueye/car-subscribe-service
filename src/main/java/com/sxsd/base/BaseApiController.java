package com.sxsd.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sxsd.car.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class BaseApiController {
    @Autowired
    protected SuperLog superLog;
    @Autowired
    protected HttpServletRequest httpServletRequest;

    protected void requestLog(String sequeceId){
        Map<String, String> params = httpReq2Params(httpServletRequest);
        superLog.getLogger().info(httpServletRequest.getRequestURI()+" request("+sequeceId+") "+ JSON.toJSONString(params,SerializerFeature.WriteMapNullValue));
    }

    protected void responseLog(String sequeceId,Object o){
        superLog.getLogger().info(httpServletRequest.getRequestURI()+" response("+sequeceId+") "+JSON.toJSONString(o));
    }

    protected Map<String, String> httpReq2Params(HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        Map<?,?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        superLog.getLogger().info("参数：" + params.toString());
        return params;
    }

    protected String uuidToken(boolean sequeceId){
        String token = UUID.randomUUID().toString().replaceAll("-","");
        if(sequeceId){
            token+= RandomUtil.generateLowerString(8);
        }
        return token;
    }

}
