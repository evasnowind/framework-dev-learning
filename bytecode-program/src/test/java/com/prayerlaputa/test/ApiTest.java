package com.prayerlaputa.test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class ApiTest {


    /**
     * 请先了解java agent机制再看这块，需要先打包、获得agent的jar，
     * 在启动时加入agent的jar
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws Exception {

//        testSimpleMonitor();
//        testGcMonitor();

        testTrackMonitor();
    }

    private static void testGcMonitor() throws InterruptedException {
        while(true) {
            List<String> list = new LinkedList<>();
            for (int i = 0; i < 5000; i++) {
                list.add("天地玄黄");
                list.add("宇宙洪荒");
            }

            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    private static void testSimpleMonitor() throws InterruptedException {
        ApiTest apiTest = new ApiTest();
        apiTest.echoHi();
    }

    private static void echoHi() throws InterruptedException {
        System.out.println("hi agent");
        Thread.sleep((long) (Math.random() * 500));
    }

    private static void testTrackMonitor() {
        //线程一
        new Thread(() -> new ApiTest().httpHi1()).start();

        //线程二
        new Thread(() -> {
            new ApiTest().httpHi1();
        }).start();
    }


    public static void httpHi1() {
        System.out.println("测试结果：hi1");
        httpHi2();
    }

    public static void httpHi2() {
        System.out.println("测试结果：hi2");
        httpHi3();
    }

    public static void httpHi3() {
        System.out.println("测试结果：hi3");
    }

}
