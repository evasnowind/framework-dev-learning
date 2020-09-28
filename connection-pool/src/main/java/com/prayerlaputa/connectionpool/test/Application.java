package com.prayerlaputa.connectionpool.test;


import com.prayerlaputa.connectionpool.test.pool.PrayerConnectionPool;
import com.prayerlaputa.connectionpool.test.pool.PrayerConnectionPoolImpl;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/9/1
 */
public class Application {

    public static void main(String[] args) {
        PrayerConnectionPool connectionPool = new PrayerConnectionPoolImpl();

        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    Connection conn = connectionPool.fetchConnection(0);
                    TimeUnit.SECONDS.sleep(1);
                    connectionPool.releaseConnection(conn);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        }

        try {
            countDownLatch.await();
            System.out.println("-------结束-----------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
