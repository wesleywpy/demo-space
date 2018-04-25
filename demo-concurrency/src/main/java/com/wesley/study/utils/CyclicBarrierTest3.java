package com.wesley.study.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier和CountDownLatch的区别
 * CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置
 * @author Created by Wesley on 2017/3/26.
 */
public class CyclicBarrierTest3 {
    /**
     * CyclicBarrier其他方法
     * getNumberWaiting方法可以获得Cyclic-Barrier阻塞的线程数量
     * isBroken()方法用来了解阻塞的线程是否被中断
     */
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Thread thread = new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {
            }
        });
        thread.start();
        thread.interrupt();
        try {
            c.await();
        } catch (Exception e) {
            System.out.println(c.isBroken());
        }
    }
}
