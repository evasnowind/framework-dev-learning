package com.prayerlaputa.bytebuddyaop.agent.gc;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class MyGcAgent {

    /**
     * JVM 首先尝试在代理类上调用以下方法
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is my agent：" + agentArgs);

        /*
        注意，P3C不推荐使用Executors.newXXX这种方式创建线程池。
        深入到源码可以看到newScheduledThreadPool内部调用的构造方法是：
            public ScheduledThreadPoolExecutor(int corePoolSize) {
                super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue());
            }

            最大线程数是Integer.MAX_VALUE，是有潜在隐患的。
            此处偷个懒还是继续用这个工具方法吧。
         */
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(() -> {
                    System.out.println();
                    System.out.print("----------------监控分割线----------------------");
                    JvmStack.printMemoryInfo();
                    JvmStack.printGCInfo();
                }, 0, 5000, TimeUnit.MILLISECONDS);
    }


    /**
     * 如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
     *
     * @param agentArgs
     */
    public static void premain(String agentArgs) {
    }
}
