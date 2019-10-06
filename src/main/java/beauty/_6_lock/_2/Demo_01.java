package beauty._6_lock._2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Demo_01 {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        Semaphore semaphore = new Semaphore(1);

        StampedLock stampedLock = new StampedLock();

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        10,
                        20,
                        60,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(10)
                );
        threadPoolExecutor.submit(() -> {});

    }

}
