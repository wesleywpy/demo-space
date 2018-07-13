package com.wesley.study.bitmaps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 统计活跃用户
 * @author Created by Wesley on 2018/7/13.
 */
@Service
public class CountActiveUser {
    private static final String KEY_ACTIVE_USER = "users:active:%s:%s";

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    /**
     * 标记用户为活跃状态
     */
    public boolean alive(long userId){
        JedisConnection connection = (JedisConnection) jedisConnectionFactory.getConnection();

        try (Jedis jedis = connection.getNativeConnection()) {
            return jedis.setbit(getKey(userId), userId, true);
        }
    }

    /**
     * 统计一段时间内的活跃用户
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public long count(LocalDate startDate, LocalDate endDate){
        return 0;
    }

    private String getKey(long userId){
        return String.format(KEY_ACTIVE_USER, LocalDate.now().format(DateTimeFormatter.ISO_DATE), userId);
    }

}
