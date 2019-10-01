package com.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedStaticMethod {

    public synchronized static void test(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("synchronized static method object {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedStaticMethod example1 = new SynchronizedStaticMethod();
        SynchronizedStaticMethod example2 = new SynchronizedStaticMethod();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            example1.test(1);
        });
        service.execute(() -> {
            example2.test(2);
        });
    }

}
