package com.prayerlaputa.bytebuddyaop.methodaop2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class MyService2 {
    public String doBusiness(String param1, String param2) {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyService doBusiness2() method:param1=" + param1 + ", param2=" + param2);
        return param2;
    }
}
