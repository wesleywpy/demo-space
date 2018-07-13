package com.wesley.study.bitmaps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.time.LocalDate;
import java.util.Date;

/**
 * 用户签到Redis Bitmap实现
 */
@Service
public class UserCheckIn {

    private static final String KEY_USER_CHECK_IN = "users:checkin:%s";

    /**
     * 签到功能上线的日期
     */
    private static final LocalDate START_DATE = LocalDate.of(2018, 7, 1);

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    /**
     * 签到
     * @param userId 用户Id
     */
    public boolean checkIn(long userId){
        return fillCheck(userId, LocalDate.now());
    }

    /**
     * 补签
     * @param userId 用户Id
     * @param localDate 补签日期
     */
    public boolean fillCheck(long userId, LocalDate localDate){
        JedisConnection connection = (JedisConnection) jedisConnectionFactory.getConnection();

        try (Jedis jedis = connection.getNativeConnection()) {
            return jedis.setbit(getKey(userId), getOffset(localDate), true);
        }
    }

    private String getKey(long userId){
        return String.format(KEY_USER_CHECK_IN, userId);
    }

    /**
     * 当前时间与签到功能上线时间的相隔天数
     */
    private long getOffset(LocalDate localDate){
        return localDate.toEpochDay() - START_DATE.toEpochDay();
    }
}
