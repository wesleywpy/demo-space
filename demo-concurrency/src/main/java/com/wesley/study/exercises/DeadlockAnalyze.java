package com.wesley.study.exercises;

/**
 * 写一段代码，让4个线程，相互死锁(A等待B，B等待C，C等待D，D等待A)。
 * 导出线程dump，并分析。给出死锁代码，线程dump和分析过程
 * @author Created by Wesley on 2018/7/27.
 */
public class DeadlockAnalyze {

    private static class DeadlocakThread implements Runnable{

        private Thread subThread;
        private String name;


        DeadlocakThread(String name){
            this.name = name;
        }

        public DeadlocakThread(Thread thread, String name) {
            this.subThread = thread;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " 开始执行...");
            System.out.println(name + " 等待 子线程");
            System.out.println("----------------");
            try {
                if(subThread != null){
                    subThread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void setSubThread(Thread subThread) {
            this.subThread = subThread;
        }
    }

    /**
     * JVM 参数
     -XX:+HeapDumpOnOutOfMemoryError
     -XX:HeapDumpPath=d:\tmp\jvm.dump
     -XX:+PrintGCDetails
     -Xms10M
     -Xmx10M
     */

    public static void main(String[] args) throws InterruptedException {
        DeadlocakThread deadA = new DeadlocakThread("线程A");
        DeadlocakThread deadB = new DeadlocakThread("线程B");
        DeadlocakThread deadC = new DeadlocakThread("线程C");
        DeadlocakThread deadD = new DeadlocakThread("线程D");
        Thread ta = new Thread(deadA);
        Thread tb = new Thread(deadB);
        Thread tc = new Thread(deadC);
        Thread td = new Thread(deadD);

        deadA.setSubThread(tb);
        deadB.setSubThread(tc);
        deadC.setSubThread(td);
        deadD.setSubThread(ta);

        ta.start();
        tb.start();
        tc.start();
        td.start();
    }
}
