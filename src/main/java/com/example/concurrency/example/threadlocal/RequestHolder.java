package com.example.concurrency.example.threadlocal;

public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    /**
     * 将当前线程的ID存储到ThreadLocal中
     * @param id
     */
    public static void add(Long id) {
        requestHolder.set(id);
    }

    public static Long getId() {
        return requestHolder.get();
    }

    /**
     * 在请求处理完成后，从ThreadLocal中将本次请求的相关信息清除，
     * 以免造成内存泄漏；
     */
    public static void remove() {
        requestHolder.remove();
    }

}
