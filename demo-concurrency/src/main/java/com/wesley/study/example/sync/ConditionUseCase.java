package com.wesley.study.example.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Yani on 2020/07/30
 */
public class ConditionUseCase {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void conditionWait() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() throws InterruptedException {
        lock.lock();
        try {
            // 唤醒一个等待在Condition上的线程，该线程从等待方法返回前必须获得与Condition相关联的锁。
            condition.signal();
        } finally {
            lock.unlock();
        }

    }

}
