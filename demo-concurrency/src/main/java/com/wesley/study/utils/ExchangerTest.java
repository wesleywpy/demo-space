package com.wesley.study.utils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger用于进行线程间的数据交换
 * @author Created by Wesley on 2017/3/26.
 */
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        /**
         * 如果第一个线程先执行exchange()方法，它会一直等待第二个线程也
         * 执行exchange方法，当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产
         * 出来的数据传递给对方
         */

        threadPool.execute(() -> {
            try {
                String A = "银行流水A"; // A录入银行流水数据
                String B = exgr.exchange(A);
                System.out.println(B);
            } catch (InterruptedException e) {
            }
        });
        threadPool.execute(() -> {
            try {
                String B = "银行流水B"; // B录入银行流水数据
                String A = exgr.exchange(B);
                System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的是："+ A + "，B录入是：" + B);
            } catch (InterruptedException e) {
            }
        });
        threadPool.shutdown();

        /**
         * 如果两个线程有一个没有执行exchange()方法，则会一直等待，
         * 如果担心有特殊情况发生，避免一直等待可以使用exchange（V x，longtimeout，TimeUnit unit）设置最大等待时长。
         */
    }
}
