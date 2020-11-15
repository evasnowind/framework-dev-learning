package com.prayerlaputa.bytebuddyaop.methodaop1;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class MyService {

    @MyLog
    public String doBusiness(String param) {
        String val = "doing business service logic";
        System.out.println("MyService doBusiness() method: param=" + param);
        return val;
    }

    public String doBusiness2() {
        String val = "doing business2 service logic";
        System.out.println("MyService doBusiness2() method:" + val);
        return val;
    }
}
