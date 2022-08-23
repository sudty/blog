package com.blog.shiro.cache;

import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 31676
 */
@Component
public class RedisCacheManger implements CacheManager {

    @Resource
    private RedisCache redisCache;

    @Override
    public RedisCache getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        return redisCache;
    }
}
