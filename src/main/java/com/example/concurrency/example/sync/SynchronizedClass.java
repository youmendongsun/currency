package com.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedClass {

    public static void test(int j) {

        synchronized (SynchronizedClass.class) {
            for (int i = 0; i < 10; i++) {
                log.info("synchronized class object {} - {}", j, i);
            }
        }

    }

    public static void main(String[] args) {

        SynchronizedClass example1 = new SynchronizedClass();
        SynchronizedClass example2 = new SynchronizedClass();

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(() -> {
            example1.test(1);
        });

        service.execute(() -> {
            example2.test(2);
        });

    }

}
