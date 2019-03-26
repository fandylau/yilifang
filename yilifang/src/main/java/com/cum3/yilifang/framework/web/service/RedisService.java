package com.cum3.yilifang.framework.web.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service("redisService")
public class RedisService<HK, T> {
    /**
     * 在构造器中获取redisTemplate实例, key(not hashKey) 默认使用String类型
     */
    private RedisTemplate<String, T> redisTemplate;
    /**
     * 在构造器中通过redisTemplate的工厂方法实例化操作对象
     */
    private HashOperations<String, HK, T> hashOperations;
    private ListOperations<String, T> listOperations;
    private ZSetOperations<String, T> zSetOperations;
    private SetOperations<String, T> setOperations;
    private ValueOperations<String, T> valueOperations;

    @Autowired
    public RedisService(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * Hash结构 添加元素
     * 
     * @param key
     *            key
     * @param hashKey
     *            hashKey
     * @param domain
     *            元素
     */
    public void hashPut(String key, HK hashKey, T domain) {
        hashOperations.put(key, hashKey, domain);
    }

    /**
     * Hash结构 获取指定key所有键值对
     * 
     * @param key
     * @return
     */
    public Map<HK, T> hashFindAll(String key) {
        return hashOperations.entries(key);
    }

    /**
     * Hash结构 获取单个元素
     * 
     * @param key
     * @param hashKey
     * @return
     */
    public T hashGet(String key, HK hashKey) {
        return hashOperations.get(key, hashKey);
    }

    public void hashRemove(String key, HK hashKey) {
        hashOperations.delete(key, hashKey);
    }

    /**
     * List结构 向尾部(Right)添加元素
     * 
     * @param key
     * @param domain
     * @return
     */
    public Long listPush(String key, T domain) {
        return listOperations.rightPush(key, domain);
    }

    /**
     * List结构 向头部(Left)添加元素
     * 
     * @param key
     * @param domain
     * @return
     */
    public Long listUnshift(String key, T domain) {
        return listOperations.leftPush(key, domain);
    }

    /**
     * List结构 获取所有元素
     * 
     * @param key
     * @return
     */
    public List<T> listFindAll(String key) {
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return listOperations.range(key, 0, listOperations.size(key));
    }

    /**
     * List结构 移除并获取数组第一个元素 *
     * 
     * @param key
     * @return
     */
    public T listLPop(String key) {
        return listOperations.leftPop(key);
    }

    /**
     * 对象的实体类
     * 
     * @param key
     * @param domain
     * @return
     */
    public void valuePut(String key, T domain) {
        valueOperations.set(key, domain);
    }

    /**
     * 获取对象实体类
     * 
     * @param key
     * @return
     */
    public T getValue(String key) {
        return valueOperations.get(key);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     * 
     * @param key
     *            键
     * @param timeout
     *            时间
     * @param timeUnit
     *            时间单位
     */
    public boolean expirse(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }
}