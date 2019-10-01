package com.example.concurrency.example.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABA {

    private static AtomicInteger atomicInt
            = new AtomicInteger(100);
    private static AtomicStampedReference atomicStampedRef
            = new AtomicStampedReference(100, 0);

    public static void main(String[] args) throws InterruptedException {

        Thread intABA = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });

        Thread intCAS = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {}
                boolean isUpdated = atomicInt.compareAndSet(100, 101);
                System.out.println("Thread AtomicInteger CAS isUpdated: " + isUpdated); // true
            }
        });

        intABA.start();
        intCAS.start();
        intABA.join();
        intCAS.join();

        Thread refABA = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {}
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
        });

        Thread refCAS = new Thread(() -> {
                int stamp = atomicStampedRef.getStamp(); // 0
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {}
                boolean isUpdated = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println("Thread AtomicStampedReference CAS isUpdated: " + isUpdated); // false
        });

        refABA.start();
        refCAS.start();
    }

}
