package com.wesley.study.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Wesley Created By 2018/8/6
 */
public class SemaphoreExam1 {

    /**
     * 线程总数
     */
    private static final int threadTotal = 30;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 只有三个信号量
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadTotal; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    // 200毫秒内, 尝试一次性获取三个信号量, 成功才执行
                    if(semaphore.tryAcquire(3,200, TimeUnit.MILLISECONDS)){
                        test(threadNum);
                        // 一次性释放
                        semaphore.release(3);
                    }
                } catch (InterruptedException e) {
                    System.out.println("exception: " +  e.getMessage());
                }

            });
        }

        executorService.shutdown();
    }

    private static void test(int num) throws InterruptedException {
        System.out.println("num: " +  num);
        TimeUnit.MILLISECONDS.sleep(400);
    }
}
