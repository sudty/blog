package com.blog.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * @author 31676
 */
@Slf4j
@Component
public class RedisCache<k,v> implements Cache<k,v> {

    private final k prefix = (k) "shiro_cache";
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public v get(k k) throws CacheException {
        log.info("get key: "+k);
        log.info("拿出缓存"+String.valueOf(((v) redisTemplate.opsForHash().get(prefix,k))));
        return (v) redisTemplate.opsForHash().get(prefix,k);
    }

    @Override
    public v put(k k, v v) throws CacheException {
        log.info("put key: "+k);
        log.info("get value: "+v);
        redisTemplate.opsForHash().put(prefix,k,v);
        return this.get(k);
    }

    @Override
    public v remove(k k) throws CacheException {
        redisTemplate.opsForHash().delete(prefix,k);
        return this.get(k);
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(prefix);
    }

    @Override
    public int size() {
        return redisTemplate.opsForHash().size(prefix).intValue();
    }

    @Override
    public Set<k> keys() {
        return redisTemplate.opsForHash().keys(prefix);
    }

    @Override
    public Collection<v> values() {
        return redisTemplate.opsForHash().values(prefix);
    }
}
