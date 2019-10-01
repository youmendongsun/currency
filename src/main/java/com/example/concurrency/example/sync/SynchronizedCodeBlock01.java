package com.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedCodeBlock01 {

    public void test1(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("synchronized block object {} - {}", j, i);
            }
        }
    }

    public static void main(String[] args) {
//        testCodeBlockSync();
        testCodeBlockNotSync();
    }

    private static void testCodeBlockNotSync() {
        SynchronizedCodeBlock01 example1 = new SynchronizedCodeBlock01();
        SynchronizedCodeBlock01 example2 = new SynchronizedCodeBlock01();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            example1.test1(1);
        });
        service.execute(() -> {
            example2.test1(2);
        });
    }

    private static void testCodeBlockSync() {
        SynchronizedCodeBlock01 example1 = new SynchronizedCodeBlock01();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            example1.test1(1);
        });
        service.execute(() -> {
            example1.test1(1);
        });
    }

}
