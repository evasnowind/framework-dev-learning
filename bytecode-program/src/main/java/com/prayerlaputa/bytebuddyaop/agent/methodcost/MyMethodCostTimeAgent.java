package com.prayerlaputa.bytebuddyaop.agent.methodcost;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class MyMethodCostTimeAgent {

    /**
     * JVM 首先尝试在代理类上调用以下方法
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is my agent：" + agentArgs);

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    // 拦截任意方法
                    .method(ElementMatchers.any())
                    // 委托
                    .intercept(MethodDelegation.to(MethodCostTime.class));
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        new AgentBuilder
                .Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("com.prayerlaputa.bytebuddyaop"))
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }


    /**
     * 如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
     *
     * @param agentArgs
     */
    public static void premain(String agentArgs) {
    }
}
