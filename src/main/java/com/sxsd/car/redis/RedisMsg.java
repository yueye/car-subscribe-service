package com.sxsd.car.redis;

import com.alibaba.fastjson.JSON;
import com.sxsd.car.utils.ResourceBundleUtil;
import com.sxsd.car.utils.http.HttpClientUtil;
import com.sxsd.car.utils.http.HttpStatus;
import com.sxsd.springboot.vo.EzvizopenToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("redisMsg")
public class RedisMsg {
	/** 消息相关key路径 */
	private String MSG_KEY = "msg:";
	/** 萤石token相关key路径 */
	private String EZVIZOPEN_TOKEN = "ezvizopen:token";
	/** token有效期6天 */
	private long EZVIZOPEN_TOKEN_EXP = 604800L;

	@Autowired
	RedisBaseService redisBaseService;

	public String getEzvizopenToken(){
		String token = redisBaseService.get(EZVIZOPEN_TOKEN);
		if(StringUtils.isNotBlank(token)){
			return token;
		}
		return refreshToken();
	}

	private String refreshToken() {
		String appKey = ResourceBundleUtil.get("ezvizopen.appkey");
		String appSecret = ResourceBundleUtil.get("ezvizopen.appSecret");
		String url = ResourceBundleUtil.get("ezvizopen.gettoken.url");
		Map<String,String> params = new HashMap<String, String>();
		params.put("appKey",appKey);
		params.put("appSecret",appSecret);
		Map<String,String> res = HttpClientUtil.doPost(url,params);
		if(HttpStatus.OK.equals(res.get("statusCode"))){
			String result = res.get("result");
			EzvizopenToken token = JSON.parseObject(result, EzvizopenToken.class);
			if(HttpStatus.OK.equals(token.getCode())){
				redisBaseService.setAndExpire(EZVIZOPEN_TOKEN,token.getData().getAccessToken(),EZVIZOPEN_TOKEN_EXP, TimeUnit.SECONDS);
				return token.getData().getAccessToken();
			}
		}
		return null;
	}


}
