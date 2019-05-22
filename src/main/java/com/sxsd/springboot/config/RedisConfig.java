package com.sxsd.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    StringRedisSerializer jackson2JsonRedisSerializer = new StringRedisSerializer();


    @Bean
    @Qualifier("redisTemplate")
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
        //GenericObjectPool
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return rcm;
    }

    @Bean(name = "poolConfig")
    @ConfigurationProperties("spring.redis.pool")
    JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(name = "jedisConnectionFactoryMain")
    @ConfigurationProperties("spring.redis.main")
    JedisConnectionFactory jedisMainConnectionFactory(@Qualifier("poolConfig") JedisPoolConfig poolConfig) {
        return init(poolConfig);
    }

    @Bean(name = "redisTemplateMain")
    public RedisTemplate<String, String> redisTemplateMain(@Qualifier("jedisConnectionFactoryMain") JedisConnectionFactory jedisMainConnectionFactory) {
        return init(jedisMainConnectionFactory);
    }


    private JedisConnectionFactory init(JedisPoolConfig poolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(poolConfig);
        return jedisConnectionFactory;
    }

    private RedisTemplate<String, String> init(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        return template;
    }

}