package com.prayerlaputa.dynamicproxy.compareproxydemo;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class TestCGLibProxyPerformance {
    public static void main(String[] args) {
        /*----------cglib----------*/
        int count = 100000;
        long cglibStart = System.currentTimeMillis();
        for (int j = 0; j < count; j++) {
            SimpleService service = new SimpleServiceImpl();
            SimpleService proxy = (SimpleService) ProxyService.cglibProxyObject(service);
            proxy.consumer();
        }
        long cglibEnd = System.currentTimeMillis();

        System.out.println("==================================================");
        System.out.println("java.version:" + System.getProperty("java.version"));
        System.out.println("new count:" + count);
        System.out.println("cglib new proxy spend time(ms):" + (cglibEnd - cglibStart));
    }
}
