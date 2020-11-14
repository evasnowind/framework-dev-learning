package com.prayerlaputa.instrument.demo2;

import com.prayerlaputa.instrument.TransClass;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class TestMainInJar2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TransClass().getNumber());
        int count = 0;
        while (true) {
            Thread.sleep(500);
            count++;
            int number = new TransClass().getNumber();
            System.out.println(number);
            if (3 == number || count >= 10) {
                break;
            }
        }
    }
}
