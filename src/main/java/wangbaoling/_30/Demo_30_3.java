package wangbaoling._30;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Demo_30_3 {

    private static Apple a1;
    private static Apple a2;

    static class SafeDateFormat {

        // 定义 ThreadLocal 变量
        static final ThreadLocal<Apple> tl =
                ThreadLocal.withInitial(
                        ()-> new Apple()
                );

        static Apple get(){
            return tl.get();
        }

    }

    public static void main(String[] args) throws Exception{

        Thread t1 = new Thread(() -> {
            a1 = SafeDateFormat.get();
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            a2 = SafeDateFormat.get();
        });
        t2.start();

        t1.join();
        t2.join();

        System.out.println("a1 == a2 : " + (a1 == a2)); // false

//        Apple apple1 = new Apple();
//        Apple apple2 = new Apple();
//        System.out.println(apple1 == apple2); // false

//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        System.out.println(sdf1 == sdf2); // false

    }

}


class Apple {}