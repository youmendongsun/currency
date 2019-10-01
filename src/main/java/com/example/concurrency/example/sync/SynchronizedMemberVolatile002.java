package com.example.concurrency.example.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedMemberVolatile002 {

    int count = 0;

    public synchronized void add() {
        count += 1;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {

        SynchronizedMemberVolatile002 s = new SynchronizedMemberVolatile002();

//        CountDownLatch c = new CountDownLatch(5000);

        ExecutorService service = Executors.newCachedThreadPool();

        for(int i = 0; i < 5000; i++) {
            service.execute(() -> {
                s.add();
//                c.countDown();
            });
        }

//        c.await();

        System.out.println(s.getCount());

        service.shutdownNow();

    }

}
