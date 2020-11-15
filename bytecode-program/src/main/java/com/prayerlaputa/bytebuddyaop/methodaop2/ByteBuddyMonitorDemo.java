package com.prayerlaputa.bytebuddyaop.methodaop2;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class ByteBuddyMonitorDemo {

    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @Argument(0) Object arg0,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();

        Object resObj = null;
        try {
            resObj = callable.call();
            return resObj;
        } finally {
            System.out.println("方法名称：" + method.getName());
            System.out.println("入参个数：" + method.getParameterCount());
            System.out.println("入参类型: " + method.getParameterTypes()[0].getTypeName());
            System.out.println("入参内容：" + arg0 + ", " + args[1]);
            System.out.println("出参类型: " + method.getReturnType().getName());
            System.out.println("出参结果: " + resObj);
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start));
        }
    }

}
