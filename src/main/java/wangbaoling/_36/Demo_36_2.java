package wangbaoling._36;

import java.util.concurrent.*;
import java.io.*;

public class Demo_36_2 {
}

class Logger {

    // 任务队列
    final BlockingQueue<LogMsg> bq = new ArrayBlockingQueue<LogMsg>(2000);

    //flush 批量
    static final int batchSize = 500;

    // 只需要一个线程写日志
    ExecutorService es = Executors.newFixedThreadPool(1);

    // 启动写日志线程
    void start() throws Exception{

        File file = File.createTempFile("foo", ".log");

        final FileWriter writer = new FileWriter(file);

        this.es.execute(()->{
            try {
                // 未刷盘日志数量
                int curIdx = 0;
                long preFT=System.currentTimeMillis();

                while (true) {

                    LogMsg log = bq.poll(5, TimeUnit.SECONDS);

                    // 写日志
                    if (log != null) {
                        writer.write(log.toString());
                        ++curIdx;
                    }

                    // 如果不存在未刷盘数据，则无需刷盘
                    if (curIdx <= 0) {
                        continue;
                    }

                    // 根据规则刷盘
                    if (log!=null && log.level == LEVEL.ERROR ||
                            curIdx == batchSize ||
                            System.currentTimeMillis() - preFT > 5000){
                        writer.flush();
                        curIdx = 0;
                        preFT=System.currentTimeMillis();
                    }

                }

            }catch(Exception e){
                e.printStackTrace();
            } finally {
                try {
                    writer.flush();
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    // 写 INFO 级别日志
    void info(String msg) throws Exception {
        bq.put(new LogMsg(LEVEL.INFO, msg));
    }

    // 写 ERROR 级别日志
    void error(String msg) throws Exception {
        bq.put(new LogMsg(LEVEL.ERROR, msg));
    }

}

// 日志级别
enum LEVEL {
    INFO, ERROR
}

class LogMsg {

    LEVEL level;
    String msg;

    // 省略构造函数实现
    LogMsg(LEVEL lvl, String msg){}

    // 省略 toString() 实现
    public String toString(){ return null;}

}

