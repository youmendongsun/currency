package com.example.concurrency.example.sync;

public class SynchronizedReentrant02 {

    public static void main(String[] args) {
        SynchronizedReentrant02 instance = new SynchronizedReentrant02();
        instance.method1();
    }

    private synchronized void method1() {
        System.out.println("method1()");
        method2();
    }

    private synchronized void method2() {
        System.out.println("method2()");
    }

}
