package com.prayerlaputa.beforeagent;

import java.lang.instrument.Instrumentation;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class TestAgent {


    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain start");

        System.out.println(agentArgs);
    }
}
