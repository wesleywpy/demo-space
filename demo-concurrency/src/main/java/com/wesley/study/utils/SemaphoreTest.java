package com.wesley.study.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源
 * @author Created by Wesley on 2017/3/26.
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    /**
     * 允许10个线程获取许可证，也就是最大并发数是10
     * 其他方法
     * intavailablePermits()：返回此信号量中当前可用的许可证数。
     * intgetQueueLength()：返回正在等待获取许可证的线程数。
     * booleanhasQueuedThreads()：是否有线程正在等待获取许可证。
     * void reducePermits（int reduction）：减少reduction个许可证，是个protected方法。
     * Collection getQueuedThreads()：返回所有等待获取许可证的线程集合，是个protected方法。
     */
    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            int y = i;
            threadPool.execute(() -> {
                try {
                    //获取一个许可证
                    s.acquire();
                    System.out.println("save data:"+ y);
                    //释放
                    s.release();
                } catch (InterruptedException e) {
                }
            });
        }
        threadPool.shutdown();
    }
}
