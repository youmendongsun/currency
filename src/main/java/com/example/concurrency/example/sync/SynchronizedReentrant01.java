package com.example.concurrency.example.sync;

public class SynchronizedReentrant01 {

    int a = 0;

    public static void main(String[] args) {
        SynchronizedReentrant01 instance = new SynchronizedReentrant01();
        instance.method1();
    }

    private synchronized void method1() {
        System.out.println("method1(), a = " + a);
        if (a == 0) {
            a ++;
            method1();
        }
    }

}
