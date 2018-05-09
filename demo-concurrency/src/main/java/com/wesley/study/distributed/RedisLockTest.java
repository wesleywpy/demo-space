package com.wesley.study.distributed;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.util.UUID;

/**
 * @author Created by Wesley on 2018/5/9.
 */
public class RedisLockTest {

    static class SckillService {
        private static JedisPool pool = null;

        static {
            JedisPoolConfig config = new JedisPoolConfig();
            // 设置最大连接数
            config.setMaxTotal(200);
            // 设置最大空闲数
            config.setMaxIdle(8);
            // 设置最大等待时间
            config.setMaxWaitMillis(1000 * 100);
            // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, "192.168.2.151", 6379, 3000, "n3stv1s1on", 2, "Client-Wesley");
        }

        private RedisLock redisLock = new RedisLock(pool);

        int num = 500;
        public void sckill(String resource){
            String identifier = UUID.randomUUID().toString();
            try {
                if(redisLock.lock(resource, identifier, 1)){
                    System.out.println(Thread.currentThread().getName() + "获得了锁");
                    System.out.println(--num);
                }
            }finally {
                redisLock.unlock(resource, identifier);
            }
        }
    }

    public static void main(String[] args) {
        SckillService sckillService = new SckillService();

        for (int i = 0; i < 50; i ++){
            new Thread(() -> sckillService.sckill("product:123456")).start();
        }
    }
}
