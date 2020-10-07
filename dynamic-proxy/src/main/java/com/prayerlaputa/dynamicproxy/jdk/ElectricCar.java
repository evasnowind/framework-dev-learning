package com.prayerlaputa.dynamicproxy.jdk;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class ElectricCar implements Rechargable, Vehicle {
    @Override
    public void drive() {
        System.out.println("Electric Car is Moving silently...");
    }

    @Override
    public void recharge() {
        System.out.println("Electric Car is Recharging...");
    }
}
