package mooccolumn._16;

import java.util.concurrent.ConcurrentHashMap;

public class Demo_16_1 {

    public static void main(String[] args) {

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        map.put("001", 1);
        map.put("001", 2);

        bitCountDemo();

    }

    private static void bitCountDemo() {

        int n = 16;

        System.out.println(n >>> 1); // 8
        System.out.println(n >>> 2); // 4
        System.out.println(n - (n >>> 2)); // 12

    }

}
