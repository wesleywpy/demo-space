package com.wesley.study.utils;

import java.util.concurrent.CountDownLatch;

/**
 * @author Created by Wesley on 2017/3/24.
 */
public class CountDownLatchTest {
    /**
     * 等待N个点完成，计数器必须大于等于0
     */
    private static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
                System.out.println(1);
                //countDown方法时，N就会减1
                c.countDown();
                System.out.println(2);
                c.countDown();
        }).start();

        //await方法会阻塞当前线程，直到N变成零
        c.await();
        System.out.println("3");
    }
}
