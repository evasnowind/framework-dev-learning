package com.prayerlaputa.dynamicproxy.compareproxydemo;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class TestJDKProxyPerformance {
    public static void main(String[] args) {
        int count = 100000;
        /*----------jdk----------*/
        long jdkStart = System.currentTimeMillis();
        for (int j = 0; j < count; j++) {
            SimpleService service = new SimpleServiceImpl();
            SimpleService proxy = (SimpleService) ProxyService.jdkProxyObject(service);
            proxy.consumer();
        }
        long jdkEnd = System.currentTimeMillis();

        System.out.println("==================================================");
        System.out.println("java.version:" + System.getProperty("java.version"));
        System.out.println("new count:" + count);
        System.out.println("jdk new proxy spend time(ms):" + (jdkEnd - jdkStart));


    }
}
