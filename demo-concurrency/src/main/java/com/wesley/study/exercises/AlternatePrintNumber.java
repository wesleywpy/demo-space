package com.wesley.study.exercises;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用两个线程交替打印奇偶数
 * @author Created by Wesley on 2018/7/30.
 */
public class AlternatePrintNumber {

    private static int number = 1;

    private static boolean flag = false;

    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        Thread odd = new Thread(new OddThread());
        odd.setName("奇数线程");
        Thread even = new Thread(new EvenThred());
        even.setName("偶数线程");

        odd.start();
        even.start();
    }

    /**
     * 打印奇数线程
     */
    private static class OddThread implements Runnable {
        @Override
        public void run() {
            while (number <= 100){
                if(!flag){
                    try{
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + " -> " + number);
                        number ++;
                        flag = true;
                    }finally {
                        LOCK.unlock();
                    }
                }else{
                    try {
                        // 防止线程空转
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class EvenThred implements Runnable {

        @Override
        public void run() {
            while (number <= 100){
                if(flag){
                    try{
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + " -> " + number);
                        number ++;
                        flag = false;
                    }finally {
                        LOCK.unlock();
                    }
                }else{
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
