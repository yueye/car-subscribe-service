package com.sxsd.car.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("redisPay")
public class RedisPay {
	/** 支付相关key路径 */
	private String PAY_KEY = "pay:";
	/** 支付订单id */
	private String PAY_ORDER_ID = "payOrderId:%s";
	/** 锁定支付订单 */
	private String LOCK = "lock:";

    /** 异步通知 */
    private String NOTIFY = "notify:";

	public  static final String SIGN_UNDERSCORE = "_";

	@Autowired
	RedisBaseService redisBaseService;

	public void setPayOrderId(Long parnter,String sign,String outTradeNo,Long payOrderId){
		String key = getPayOrderKey(parnter, sign, outTradeNo);
		redisBaseService.set(PAY_KEY+ String.format(PAY_ORDER_ID,key),payOrderId+"");
	}

	private String getPayOrderKey(Long parnter, String sign, String outTradeNo) {
		return parnter + SIGN_UNDERSCORE + sign + SIGN_UNDERSCORE + outTradeNo;
	}

	public Long getPayOrderId(Long parnter,String sign,String outTradeNo){
		String key = getPayOrderKey(parnter, sign, outTradeNo);
		String id = redisBaseService.get(PAY_KEY+ String.format(PAY_ORDER_ID,key));
		if(StringUtils.isNotBlank(id)){
			return Long.valueOf(id);
		}
		return null;
	}

	public boolean lockPayOrder(Long parnter,String sign,String outTradeNo){
		String key = getPayOrderKey(parnter, sign, outTradeNo);
		return redisBaseService.secKill(PAY_KEY+LOCK+key,1,30);
	}

    public void rpushNotifyPayOrder(String minute, String value){
        redisBaseService.rpush(PAY_KEY+NOTIFY+minute,value);
    }

    public String getNotifyPayOrder(String minute){
        return redisBaseService.lpop(PAY_KEY+NOTIFY+minute);
    }

    public void delNotifyPayOrder(String minute){
        redisBaseService.del(PAY_KEY+NOTIFY+minute);
    }

}
