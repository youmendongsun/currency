package wangbaoling._30;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Demo_30_1 {

    static class SafeDateFormat {

        // 定义 ThreadLocal 变量
        static final ThreadLocal<DateFormat> tl =
                ThreadLocal.withInitial(()-> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        static DateFormat get(){
            return tl.get();
        }

    }

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            DateFormat df = SafeDateFormat.get();
            System.out.println(df);
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            DateFormat df = SafeDateFormat.get();
            System.out.println(df);
        });
        t2.start();

        DateFormat df = SafeDateFormat.get();
        System.out.println(df);
    }

}
