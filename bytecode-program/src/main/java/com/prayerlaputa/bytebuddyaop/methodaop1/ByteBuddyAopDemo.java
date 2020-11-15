package com.prayerlaputa.bytebuddyaop.methodaop1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class ByteBuddyAopDemo {

    public static void main(String[] args) {
        try {
            MyService myService = new ByteBuddy()
                    .subclass(MyService.class)
                    .method(ElementMatchers.any())
                    .intercept(Advice.to(LoggerAdvisor.class))
                    .make()
                    .load(MyService.class.getClassLoader())
                    .getLoaded()
                    .newInstance();

            myService.doBusiness("OMG!");
            myService.doBusiness2();


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
