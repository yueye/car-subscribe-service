package com.sxsd.car.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("redisBaseService")
public class RedisBaseService {
	@Autowired
    @Qualifier("redisTemplateMain")
    protected RedisTemplate<String, String> redisTemplate;
	
	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public void set(String key, String json) {
		getRedisTemplate().opsForValue().set(key, json); 
	}
	
	/**
	 * set的同时，设置 过期时间
	 * @param key
	 * @param json
	 * @param time
	 * @param unit
	 */
	public void setAndExpire(String key, String json,long time,TimeUnit unit) {
		getRedisTemplate().opsForValue().set(key, json,time,unit); 
	}
	
	public String get(String key) { 
		return getRedisTemplate().opsForValue().get(key);
	}
	
	public void del(String key) {
		getRedisTemplate().delete(key); 
	}
	
	public void delByKeyBeginStr(String beingStr){ 
		Set<String> set = getRedisTemplate().keys(beingStr+"*");
		Iterator<String> it = set.iterator();  
        while(it.hasNext()){  
            String keyStr = it.next();
            getRedisTemplate().delete(keyStr); 
        }
	}
 
	public <T> void setPushList(String key, List<T> datas) {
		del(key);
		if(datas!=null&&datas.size()>0) {
	        List<String> values=new ArrayList<String>();
	        for (T t : datas) {
		        	String str = JSONObject.toJSONString(t);
		        	values.add(str);
		    }
			getRedisTemplate().opsForList().rightPushAll(key, values); 
		}
	}
	
	public <T> List<T> getList(String key, Class<T> class1, long start, long end) {
		List<String> dd=null;
		dd=getRedisTemplate().opsForList().range(key, start, end);
		List<T> result = new ArrayList<T>();
		for (String str : dd) {
			try {
				T ele = JSON.parseObject(str, class1);
				result.add(ele);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage()+",key:"+key);
			}
		}
		return result;
	} 

	
	public List<String> getList(String key) {
		return getRedisTemplate().opsForList().range(key, 0, -1);
	}

	
	public <T> List<T> getList(String key, Class<T> class1) {
		return getList(key,class1,0,-1);
	}

	
	public Long getListSize(String key) {
		return getRedisTemplate().opsForList().size(key);
	}
	
	
	public boolean setIsExist(String key, String value) {
		return getRedisTemplate().opsForSet().isMember(key, value);
	}
	
	public void setPush(String key, String value) {
		getRedisTemplate().opsForSet().add(key, value); 
	} 
	public Set<String> getPushMembers(String key){
		return getRedisTemplate().opsForSet().members(key);
	}
	
	public Long removePushMembers(String key,Object...obj) {
		return getRedisTemplate().opsForSet().remove(key, obj);
	}

	 public Set<String> like(String key) {
		 return getRedisTemplate().keys(key);
	 }
	 
	 /**
	  * 原子操作 递增
	  * @param key
	  * @param delta 要增加几；如果小于0，默认是1
	  * @return
	  */
	 public long incr(String key,long delta) {
		 if(delta <0) {
			 delta = 1;
		 }
		 return getRedisTemplate().opsForValue().increment(key, delta);
	 }
	 
	/**
	 * 递减
	 * 
	 * @param key   键
	 * @param delta 要减少几，如果小于0，默认是1
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			delta = 1;
		}
		return getRedisTemplate().opsForValue().increment(key, -delta);
	}
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param time  时间
	 * @param unit TimeUnit.  单位，秒/分/时...
	 */
	public void setExpire(String key,long time,TimeUnit unit) {
		if(time <0) {
			time = 1;
		}
		getRedisTemplate().expire(key, time, unit);
	}

	/**
	 * 獲取有效期
	 * 
	 * @param key
	 * @param unit
	 * @return
	 */
	public long getExpire(String key, TimeUnit unit) {
		return getRedisTemplate().getExpire(key, unit);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key) {
		return getRedisTemplate().hasKey(key);
	}

	/**
	 * 原子自增带失效时间
	 * @param key
	 * @param liveTime
	 * @return
	 */
	public Long incrementAndGet(String key,long liveTime) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, getRedisTemplate().getConnectionFactory());
		Long increment = entityIdCounter.incrementAndGet();
		if ((null == increment || increment.longValue() == 1) && liveTime > 0) {//初始设置过期时间
			entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
		}
		return increment;
	}

	/**
	 * 原子自减带失效时间
	 * @param key
	 * @return
	 */
	public Long decrementAndGet(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, getRedisTemplate().getConnectionFactory());
		Long increment = entityIdCounter.decrementAndGet();
		return increment;
	}

	/**
	 * 秒杀
	 * @param key
	 * @param count
	 * @return
	 */
	public boolean secKill(String key, long count) {
		Long inc = incrementAndGet(key,0L);
		if(inc.longValue()>count){
			decrementAndGet(key);
			return false;
		}
		return true;
	}

	/**
	 * 秒杀带时间
	 * @param key
	 * @param count
	 * @param liveTime
	 * @return
	 */
	public boolean secKill(String key, long count, long liveTime) {
		Long inc = incrementAndGet(key,liveTime);
		if(inc.longValue()>count){
			decrementAndGet(key);
			return false;
		}
		return true;
	}

	/**
	 * 从list取出
	 * @param key
	 * @return
	 */
	public String lpop(String key){
		return getRedisTemplate().opsForList().leftPop(key);
	}

	/**
	 * 追加到list尾部
	 * @param key
	 * @param value
	 */
	public void rpush(String key, String value) {
		getRedisTemplate().opsForList().rightPush(key,value);
	}
}
