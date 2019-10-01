package com.example.concurrency.example.sync;

public class SynchronizedYesAndNo6 implements Runnable {

    static SynchronizedYesAndNo6 instance = new SynchronizedYesAndNo6();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }

    public synchronized static void method1() {
        System.out.println(Thread.currentThread().getName() + " 访问 synchronized static mothod1() 开始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 访问 synchronized static mothod1() 结束");
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + " 访问 synchronized mothod2() 开始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 访问 synchronized mothod2() 结束");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();

        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }

}
