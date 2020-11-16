package com.prayerlaputa.dynamicproxy.compareproxydemo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Proxy;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class ProxyService {

    /**
     * jdk动态代理
     *
     * @param object 被代理类对象
     * @return 代理实例
     */
    public static Object jdkProxyObject(Object object) {
        //拦截器
        SimpleInterceptor interceptor = new SimpleInterceptor();
        return Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    //拦截器 - 前置处理
                    interceptor.before();
                    Object result = method.invoke(object, args);
                    //拦截器 - 后置处理
                    interceptor.after();
                    return result;
                });
    }

    /**
     * cglib动态代理
     *
     * @param object 被代理类对象
     * @return 代理实例
     */
    public static Object cglibProxyObject(Object object) {
        //模拟拦截器
        SimpleInterceptor interceptor = new SimpleInterceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            //拦截器 - 前置处理
            interceptor.before();
            Object result = method.invoke(object, objects);
            //拦截器 - 后置处理
            interceptor.after();
            return result;
        });
        return enhancer.create();
    }

}

class SimpleInterceptor {

    public void before() {
        System.out.println("-----" + this.getClass().getSimpleName() + "do before" + "-----");
    }

    public void after() {
        System.out.println("-----" + this.getClass().getSimpleName() + "do after" + "-----");
    }

}
