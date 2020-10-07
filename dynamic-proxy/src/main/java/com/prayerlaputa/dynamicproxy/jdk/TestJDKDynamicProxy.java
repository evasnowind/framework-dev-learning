package com.prayerlaputa.dynamicproxy.jdk;

import com.prayerlaputa.dynamicproxy.ProxyUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class TestJDKDynamicProxy {

    public static void main(String[] args) {
        ElectricCar car = new ElectricCar();
        // 1.获取对应的ClassLoader
        ClassLoader classLoader = car.getClass().getClassLoader();

        // 2.获取ElectricCar 所实现的所有接口
        Class[] interfaces = car.getClass().getInterfaces();
        // 3.设置一个来自代理传过来的方法调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler handler = new ElectricCarInvocationHandler(car);
		/*
		  4.根据上面提供的信息，创建代理对象 在这个过程中，
		    a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
		    b.然后根据相应的字节码转换成对应的class，
            c.然后调用newInstance()创建实例
		 */
        Object o = Proxy.newProxyInstance(classLoader, interfaces, handler);
        Vehicle vehicle = (Vehicle) o;
        vehicle.drive();
        Rechargable rechargeable = (Rechargable) o;
        rechargeable.recharge();

        ProxyUtils.generateClassFile(car.getClass(), "ElectricCarProxy");
    }
}
