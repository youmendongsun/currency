package com.example.concurrency.example.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedMemberVolatile001 {

    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v) {
            System.out.println(x);
        }
    }

    public static void main(String[] args) {

        SynchronizedMemberVolatile001 s = new SynchronizedMemberVolatile001();

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(() -> {
            s.writer();
        });

        service.execute(() -> {
            s.reader();
        });

    }

}
