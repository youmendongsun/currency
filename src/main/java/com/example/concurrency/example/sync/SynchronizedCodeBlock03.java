package com.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedCodeBlock03 implements Runnable{

    static SynchronizedCodeBlock03 instance = new SynchronizedCodeBlock03();

    Object lock1 = new Object();
    Object lock2 = new Object();

    @Override
    public void run() {
        synchronized (lock1) {
            log.info("{}", Thread.currentThread().getName() + " 持有 lock1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}", Thread.currentThread().getName() + " 释放 lock1");
        }



        synchronized (lock1) {
            log.info("{}", Thread.currentThread().getName() + " 持有 lock2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}", Thread.currentThread().getName() + " 释放 lock2");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();

        while (t1.isAlive() || t2.isAlive()) {

        }
        log.info("{} end", Thread.currentThread().getName());
    }

}
