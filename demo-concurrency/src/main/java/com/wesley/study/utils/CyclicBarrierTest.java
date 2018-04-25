package com.wesley.study.utils;

import java.util.concurrent.CyclicBarrier;

/**
 * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，
 * 屏障才会开门，所有被屏障拦截的线程才会继续运行
 * @author Created by Wesley on 2017/3/26.
 */
public class CyclicBarrierTest {
    /**
     * 参数表示屏障拦截的线程数量
     */
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(() -> {
                try {
                    //每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞
                    c.await();
                } catch (Exception e) {}
                System.out.println(1);
        }).start();

        try {
            c.await();
        } catch (Exception e) {}

        /**
         * 因为主线程和子线程的调度是由CPU决定的，两个线程都有可能先执行，所以会产生两种输出
         */
        System.out.println(2);
    }
}
