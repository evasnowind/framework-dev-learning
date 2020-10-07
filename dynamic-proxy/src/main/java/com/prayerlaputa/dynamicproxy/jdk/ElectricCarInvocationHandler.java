package com.prayerlaputa.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class ElectricCarInvocationHandler implements InvocationHandler {

    private ElectricCar car;

    public ElectricCarInvocationHandler(ElectricCar car) {
        this.car = car;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("You are going to invoke " + method.getName() + " ...");
        method.invoke(car, null);
        System.out.println(method.getName() + " invocation Has Been finished...");
        return null;

    }
}
