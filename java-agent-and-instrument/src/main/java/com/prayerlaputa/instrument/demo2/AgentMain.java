package com.prayerlaputa.instrument.demo2;

import com.prayerlaputa.instrument.TransClass;
import com.prayerlaputa.instrument.demo1.Transformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst)  throws ClassNotFoundException, UnmodifiableClassException, InterruptedException {
        inst.addTransformer(new Transformer(), true);
        inst.retransformClasses(TransClass.class);
        System.out.println("Agent Main Done");
    }
}
