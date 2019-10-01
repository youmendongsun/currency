package com.example.concurrency.example.volatile_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolatileDemo1 {

    public static volatile boolean inited = false;
    public static int var = 0;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2);
                var = -1;
                inited = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            while (!inited) {
                System.out.println(var++);
            }
            System.out.println(var);
        });

        service.shutdown();
    }

}
