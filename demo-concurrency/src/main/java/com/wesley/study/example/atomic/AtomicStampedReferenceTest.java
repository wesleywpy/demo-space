package com.wesley.study.example.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用AtomicStampedReference解决CAS操作中ABA问题
 * <p>
 * 思路：每次更新就要设置一个不一样的版本号，修改的时候，不但要比较值有没有变，还要比较版本号对不对，这个思想在zookeeper中也有体现；
 * </p>
 * @author Created by Wesley on 2018/4/27.
 */
public class AtomicStampedReferenceTest {

    /**
     * 余额
     */
    private static final AtomicStampedReference<Integer> BALANCE_REFERENCE = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args){

        //模拟多个线程同时更新后台数据库，为用户充值
        for (int i = 0; i < 5; i++) {
            final int stamp = BALANCE_REFERENCE.getStamp();

            new Thread(() -> {
                Integer balance = BALANCE_REFERENCE.getReference();
                while(true){
                    if(balance < 20){
                        if(BALANCE_REFERENCE.compareAndSet(balance, balance + 10, stamp, stamp + 1)){
                            System.out.println("余额小于20元，充值成功，余额:"+ BALANCE_REFERENCE.getReference() +"元");
                            break;
                        }else{
                            break;
                        }
                    }
                }
            }).start();

        }

        //用户消费线程，模拟消费行为
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                while(true){
                    int stamp = BALANCE_REFERENCE.getStamp();
                    Integer balance = BALANCE_REFERENCE.getReference();
                    if(balance > 10){
                        if(BALANCE_REFERENCE.compareAndSet(balance, balance - 10, stamp, stamp + 1)){
                            System.out.println("成功消费10元，余额:"+ BALANCE_REFERENCE.getReference() +"元");
                            break;
                        }
                    }else{
                        System.out.println("没有足够的余额");
                        break;
                    }
                }
                try {Thread.sleep(100);} catch (InterruptedException e) {}
            }
        }).start();
    }

}
