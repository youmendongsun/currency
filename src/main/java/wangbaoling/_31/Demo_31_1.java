package wangbaoling._31;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Guarded Suspension 模式
 */
public class Demo_31_1 {

}

class RequestHandler {

    // 处理浏览器发来的请求
    Respond handleWebReq(){

        String id = (new IDGenerator()).get();

        // 创建一消息
        Message msg1 = new Message(id,"{...}");

        /**
         * 线程 A 建立一对 (id, go) 在静态 Map 中；
         */
        GuardedObject<Message> go = GuardedObject.create(id);

        // 发送消息
        send(msg1);

        /**
         * 线程 A 试图从守卫对象 go 中获取被保护的资源 obj；
         * 如果获取不到，线程 A 会进入守卫对象 go 的 condition 队列，
         * 线程 B 收到返回的 msg 后：
         *   1）从静态 Map 中取出这对(id, go)；
         *   2）将 msg 置入它的守卫对象 go.obj；
         *   3）唤醒 go 中等待的线程 A；
         * 线程 A 被唤醒，取出被保护的资源 obj，即 msg；
         */
        Message r = go.get( t -> t != null );

        return new Respond<Message>(r);
    }

    void onMessage(Message msg){
        // 唤醒等待的线程
        GuardedObject.fireEvent(msg.id, msg);
    }

    // 该方法可以发送消息
    void send(Message msg){
        // 省略相关代码
    }

}

class GuardedObject<T>{

    // 受保护的对象
    T obj;

    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 2;

    // 保存所有 GuardedObject
    final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

    // 静态方法创建 GuardedObject
    static <K> GuardedObject create(K key){
        GuardedObject go = new GuardedObject();
        gos.put(key, go);
        return go;
    }

    static <K, T> void fireEvent(K key, T obj){
        GuardedObject go = gos.remove(key);
        if (go != null){
            go.onChanged(obj);
        }
    }

    // 获取受保护对象
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA 管程推荐写法
            while(!p.test(obj)){
                done.await(timeout, TimeUnit.SECONDS);
            }
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
        // 返回非空的受保护对象
        return obj;
    }

    // 事件通知方法
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

}

class IDGenerator {

    public String get() {
        return "";
    }

}

class Message{

    String id;
    String content;

    public Message(String id, String content) {
        this.id = id;
        this.content = content;
    }

}

class Respond<T> {

    private T t;

    public Respond(T t) {
        this.t = t;
    }

}



