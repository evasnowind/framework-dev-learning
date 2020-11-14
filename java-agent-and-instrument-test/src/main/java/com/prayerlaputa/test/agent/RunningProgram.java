package com.prayerlaputa.test.agent;

import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class RunningProgram {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("RunningProgram start....");
        while(true) {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("waiting for attach");
        }

    }
}
