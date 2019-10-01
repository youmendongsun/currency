package com.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedCodeBlock02 implements Runnable{

    static SynchronizedCodeBlock02 instance = new SynchronizedCodeBlock02();

    @Override
    public void run() {
        synchronized (this) {
            log.info("{} start",Thread.currentThread());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{} end",Thread.currentThread());
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();

        while (t1.isAlive() || t2.isAlive()) {

        }
        log.info("{} end", Thread.currentThread());
    }

}
