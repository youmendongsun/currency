package wangbaoling._30;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

public class Demo_30_2 {

    private static Long l1;
    private static Long l2;

    static class ThreadId {

        // 定义 ThreadLocal 变量
        static final AtomicLong nextId = new AtomicLong(0);

        static final ThreadLocal<Long> t1 =
                ThreadLocal.withInitial(
                        () -> nextId.getAndIncrement()
                );

        static Long get(){
            return t1.get();
        }

    }

    public static void main(String[] args) throws Exception{

        Thread t1 = new Thread(() -> {
            System.out.println("t1 : " + ThreadId.get());
            System.out.println("t1 : " + ThreadId.get());
            l1 = ThreadId.get();
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println("t2 : " + ThreadId.get());
            l2 = ThreadId.get();
        });
        t2.start();

        t1.join();
        t2.join();

        System.out.println("l1 == l2 : " + (l1 == l2));

    }

}
