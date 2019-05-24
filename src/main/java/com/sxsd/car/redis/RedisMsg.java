package com.sxsd.car.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("redisMsg")
public class RedisMsg {
	/** 消息相关key路径 */
	private String MSG_KEY = "msg:";

	@Autowired
	RedisBaseService redisBaseService;



}
