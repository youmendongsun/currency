package wangbaoling._06;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class Demo_06_01 {



}

class Allocator {

    private List<Object> als;

    // 一次性申请所有资源
    synchronized void apply(Object from, Object to){

        // 经典写法
        while(als.contains(from) || als.contains(to)){

            try{
                wait();
            }catch(Exception e){

            }

        }

        als.add(from);
        als.add(to);
    }

    // 归还资源
    synchronized void free(Object from, Object to){

        als.remove(from);
        als.remove(to);

        notifyAll();
    }

}
