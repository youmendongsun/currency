package com.example.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 整个协作过程是线程节点在AQS和condition的等待队列中来回移动实现的；
 * Condition作为一个条件类，很好的维护了condition条件队列；
 * Condition是个在多线程间协调的通信类；等待的线程被唤醒之后重新争夺锁；
 * Condition相对于其他同步组件，使用还是复杂一些；
 * 实际开发中使用的很少；
 */
@Slf4j
public class LockExample6 {

    private static int threadName = -1;

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {
            try {
                /**
                 * 调用lock()后，线程就进入了AQS的等待队列中去了；
                 */
                reentrantLock.lock();
                threadName = 1;
                log.info("wait signal, threadName: {}", threadName); // 1
                /**
                 * 当调用了await()之后，线程就从AQS的等待队列中移除了；
                 * 对应的操作其实是锁的释放；紧接着就加入到了condition
                 * 的等待队列中去；它在等待着一个信号；
                 */
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 线程1被唤醒时的继续执行的输出；
             */
            threadName = 11;
            log.info("get signal,threadName: {}", threadName); // 4
            /**
             * 线程1释放锁，整个过程执行完毕；
             */
            reentrantLock.unlock();
        }).start();


        new Thread(() -> {
            /**
             * 线程2因为线程1释放锁的关系，被唤醒，并判断自己是否可以
             * 取到锁；然后线程2获取锁，也加入到了AQS的等待队列中；
             */
            reentrantLock.lock();
            threadName = 2;
            log.info("get lock, threadName: {}", threadName); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 线程2发送信号，此时condition的等待队列中有线程1的节点，
             * 于是线程1的节点被取出，加入到AQS的队列中；此时线程1没有
             * 被唤醒；
             */
            condition.signalAll();
            log.info("send signal ~, threadName: {}", threadName); // 3
            /**
             * 线程2释放锁，此时AQS队列中只剩下线程1，于是线程1被唤醒；
             */
            reentrantLock.unlock();
        }).start();
    }

}
