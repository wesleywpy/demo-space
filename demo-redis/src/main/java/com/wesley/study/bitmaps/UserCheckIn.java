package com.wesley.study.bitmaps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * 用户签到Redis Bitmap实现
 */
@Service
public class UserCheckIn {

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    public boolean checkIn(long userId){
        System.out.println(userId);
        System.out.println(jedisConnectionFactory);
        JedisConnection connection = (JedisConnection) jedisConnectionFactory.getConnection();
        Jedis jedis = connection.getNativeConnection();
        return false;
    }
}
