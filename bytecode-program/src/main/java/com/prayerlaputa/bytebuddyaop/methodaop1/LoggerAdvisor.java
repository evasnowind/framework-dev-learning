package com.prayerlaputa.bytebuddyaop.methodaop1;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class LoggerAdvisor {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        if (null != method.getAnnotation(MyLog.class)) {
            System.out.println("ByteBuddy AOP Enter " + method.getName() + " with parameters:" + Arrays.toString(arguments));
        }
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments, @Advice.Return Object ret) {
        if (method.getAnnotation(MyLog.class) != null) {
            System.out.println("ByteBuddy Exit " + method.getName() + " with arguments: " + Arrays.toString(arguments) + " return: " + ret);
        }
    }
}
