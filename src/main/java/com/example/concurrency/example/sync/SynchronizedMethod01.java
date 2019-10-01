package com.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedMethod01 {

    public synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("synchronized method object {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
//        testMethodSync();
        testMethodNotSync();
    }

    private static void testMethodSync() {
        SynchronizedMethod01 example1 = new SynchronizedMethod01();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            example1.test2(1);
        });
        service.execute(() -> {
            example1.test2(1);
        });
    }

    private static void testMethodNotSync() {
        SynchronizedMethod01 example1 = new SynchronizedMethod01();
        SynchronizedMethod01 example2 = new SynchronizedMethod01();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            example1.test2(1);
        });
        service.execute(() -> {
            example2.test2(2);
        });
    }

}
