package com.example.concurrency.example.sync;

public class SynchronizedReentrant03 {

    public synchronized void doSomething() {
        System.out.println("Super doSomething()");
    }

}

class Test extends SynchronizedReentrant03 {

    @Override
    public synchronized void doSomething() {
        System.out.println("Sub doSomething()");
        super.doSomething();
    }

    public static void main(String[] args) {
        Test instance = new Test();
        instance.doSomething();
    }

}
