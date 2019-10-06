package wangbaoling._01;

class Demo_01_01 {

    public long count = 0;

    public void add10K() {

        int idx = 0;

        while(idx++ < 10000) {
            count += 1;
        }

    }

    public static void main(String[] args) throws Exception{

        final Demo_01_01 d = new Demo_01_01();

        Thread th1 = new Thread(()->{
            d.add10K();
        });

        Thread th2 = new Thread(()->{
            d.add10K();
        });

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println(d.count);
    }

}
