package wangbaoling._36;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Demo_36_1 {

    public static void main(String[] args) {

        BlockingQueue bq = new LinkedBlockingDeque(2000);

        List list = new ArrayList<Integer>(10);
        list = new LinkedList();

        ExecutorService s = Executors.newFixedThreadPool(1000);
        s = Executors.newCachedThreadPool();
    }

}
