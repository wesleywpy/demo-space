package com.wesley.growth.time.client;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Created by 2018/08/27
 */
public class UnixTime {

    private final long value;


    public UnixTime(long value) {
        this.value = value;
    }

    public UnixTime() {
        // (System.currentTimeMillis()/1000L 获取到的是格林威治时间，+2208988800 变成东八区的时间，否则会有时差
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public long value() {
        return this.value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}
