package com.wesley.study.distributed;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis实现分布式锁
 * @author Created by Wesley on 2018/5/7.
 */
public class RedisLock {
    private final JedisPool jedisPool;
    private volatile int count = 0;

    public RedisLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @param key 锁的Key
     * @param identifier 标识
     * @param expireTime 过期时间
     */
    public boolean lock(String key, String identifier, int expireTime){
        Jedis jedis = jedisPool.getResource();
        int retry = 0;
        //获取锁失败最多尝试10次
        int failRetryTimes = 10;
        while(retry < failRetryTimes){
            //1. 先获取锁，如果是当前线程已经持有，则直接返回
            //2. 防止后面设置超时锁，其实是设置成功，而网络超时导致客户端返回失败，所以获取锁之前需要查询一下
            String value = jedis.get(key);
            //如果当前锁存在，并且属于当前线程持有，则锁计数+1，直接返回
            if(Objects.nonNull(value) && value.equals(identifier)){
                count ++;
                return true;
            }

            //如果锁已经被持有了，那需要等待锁的释放
            if(Objects.isNull(value) || count <= 0){
                //获取锁
                if(1 == jedis.setnx(key, identifier)){
                    jedis.expire(key, expireTime);
                    count = 1;
                    return true;
                }
            }

            //获取锁失败间隔时间, 100毫秒
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                retry++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

        }
        return false;
    }

    public boolean unlock(String key, String identifier){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        if(Objects.isNull(value) || "".equals(value)){
            count = 0;
            return true;
        }

        //判断当前锁的持有者是否是当前线程，如果是的话释放锁，不是的话返回false
        if(value.equals(identifier)){
            if(count > 1){
                count --;
                return true;
            }

            Long result = jedis.del(key);
            if(1 == result){
                count = 0;
            }

            return 1 == result;
        }

        return false;
    }

}
