package com.wesley.study.exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 写一个程序，包含十个线程，子线程必须等待主线程sleep 10秒钟之后才执行，并打印当前时间
 * @author Created by Wesley on 2018/7/12.
 */
public class Exercise01 {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程启动... ");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("主线程等待时间结束");

        for (int i = 0; i < 10; i++) {
            executorService.execute(() ->
                System.out.println(Thread.currentThread().getName() + "启动, 当前时间: "+ System.currentTimeMillis())
            );
        }
        executorService.shutdown();
    }
}
