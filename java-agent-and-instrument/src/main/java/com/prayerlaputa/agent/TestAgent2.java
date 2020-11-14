package com.prayerlaputa.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class TestAgent2 {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("load agent after main run.args=" + agentArgs);

        Class<?>[] classes = inst.getAllLoadedClasses();

        for(Class<?> cls : classes) {
            System.out.println(cls.getName());
        }

        System.out.println("agent run completely!");

    }
}
