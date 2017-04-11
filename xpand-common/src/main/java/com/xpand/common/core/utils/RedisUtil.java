package com.xpand.common.core.utils;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by root on 17-4-10.
 */
public class RedisUtil {
    private static RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");
    public static void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public static Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
