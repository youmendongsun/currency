package wangbaoling._30;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Demo_30_1 {

    private static DateFormat sdf1 = null;
    private static DateFormat sdf2 = null;

    static class SafeDateFormat {

        // 定义 ThreadLocal 变量
        static final ThreadLocal<SimpleDateFormat> tl =
                ThreadLocal.withInitial(
                        ()-> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                );

        static DateFormat get(){
            return tl.get();
        }

    }

    public static void main(String[] args) throws Exception{

        Thread t1 = new Thread(() -> {
            sdf1 = SafeDateFormat.get();
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            sdf2 = SafeDateFormat.get();
        });
        t2.start();

        t1.join();
        t2.join();

        System.out.println("sdf1 == sdf2 : " + (sdf1 == sdf2)); // false
        System.out.println("sdf1.hashCode() : " + sdf1.hashCode()); // 1333195168
        System.out.println("sdf2.hashCode() : " + sdf2.hashCode()); // 1333195168
        System.out.println(sdf1.equals(sdf2)); // true
    }

}
