package com.wesley.study.example.aqs;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Created by Wesley on 2017/3/24.
 */
@Slf4j
public class CountDownLatchTest {
    /**
     * 线程数
     */
    private static final int threadTotal = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 等待N个点完成，计数器必须大于等于0
        final CountDownLatch countDownLatch = new CountDownLatch(threadTotal);

        for (int i = 0; i < threadTotal; i ++){
            final int threadNum = i;
            executorService.execute(() -> {
                try{
                    test(threadNum);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        //await方法会阻塞当前线程，直到N变成零, 可设置等待时间
        countDownLatch.await();
        log.info("finished");
        executorService.shutdown();
    }

    private static void test(int num){
        log.info("num: {}", num);
    }

}
