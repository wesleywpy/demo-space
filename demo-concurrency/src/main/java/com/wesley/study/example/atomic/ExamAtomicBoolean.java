package com.wesley.study.example.atomic;

import com.wesley.study.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 通常用于让某一段代码只执行一次
 * @author Wesley Created By 2018/8/2
 */
@Slf4j
@ThreadSafe
public class ExamAtomicBoolean {

    private static final AtomicBoolean atomicBoolean = new AtomicBoolean();

    public static void main(String[] args) throws InterruptedException {
        // 模拟请求总数
        int clientToal = 5000;
        // 并发线程
        int threadTotal = 200;

        ExecutorService executorService = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientToal);
        for(int i = 0; i < clientToal; i++){
            executorService.execute(() -> {
                try {
                    //获取一个许可证
                    semaphore.acquire();
                    test();
                    //释放
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    log.error("执行出错", e);
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();
    }

    private static void test(){
        if (atomicBoolean.compareAndSet(false, true)) {
            log.info("execute success");
        }
    }
}
