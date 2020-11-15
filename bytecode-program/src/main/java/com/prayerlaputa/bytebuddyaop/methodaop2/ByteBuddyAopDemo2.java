package com.prayerlaputa.bytebuddyaop.methodaop2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class ByteBuddyAopDemo2 {

    public static void main(String[] args) {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(MyService2.class)
                .method(ElementMatchers.named("doBusiness"))
                .intercept(MethodDelegation.to(ByteBuddyMonitorDemo.class))
                .make();

        //加载类
        Class<?> clazz = dynamicType.load(ByteBuddyAopDemo2.class.getClassLoader())
                .getLoaded();

        try {
            //反射调用
            clazz.getMethod("doBusiness", String.class, String.class).invoke(clazz.newInstance(), "hello", "world!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
