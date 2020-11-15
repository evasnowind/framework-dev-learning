package com.prayerlaputa.bytebuddyaop.classandmethodaop;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;

/**
 * @author chenglong.yu
 * created on 2020/11/14
 */
public class ByteBuddyAopDemo3 {

    public static void main(String[] args) {
        //生成含有注解的泛型实现子类
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                //创建子类时加上泛型注解
                .subclass(
                        TypeDescription
                                .Generic
                                .Builder
                                .parameterizedType(Repository.class, String.class).build())
                //加上类名
                .name(Repository.class.getPackage().getName()
                        .concat(".").concat("UserRepository"))
                //匹配要代理的方法
                .method(ElementMatchers.named("queryData"))
                //教给委托类UserRepositoryInterceptor来处理
                .intercept(MethodDelegation.to(UserRepositoryInterceptor.class))
                //加上方法注解
                .annotateMethod(AnnotationDescription.Builder
                        .ofType(RpcGatewayMethod.class)
                        .define("methodName", "dataApi")
                        .define("methodDesc", "查询数据")
                        .build())
                //加上类注解
                .annotateType(AnnotationDescription.Builder
                        .ofType(RpcGatewayClazz.class)
                        .define("alias", "dataApi")
                        .define("clazzDesc", "查询数据信息")
                        .define("timeOut", 350L)
                        .build())
                .make();


        // 输出类信息到目标文件夹下
        try {
            dynamicType.saveIn(new File(ByteBuddyAopDemo3.class.getResource("/").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 从目标文件夹下加载类信息
        try {
            Class<Repository<String>> repositoryClass = (Class<Repository<String>>) Class.forName("com.prayerlaputa.bytebuddyaop.classandmethodaop.UserRepository");
            printRpcAnnotationInfo(repositoryClass);


            // 实例化对象
            Repository<String> repository = repositoryClass.newInstance();

            // 测试输出
            System.out.println(repository.queryData(10001));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static void printRpcAnnotationInfo(Class<Repository<String>> repositoryClass) {

        // 获取类注解
        RpcGatewayClazz rpcGatewayClazz = repositoryClass.getAnnotation(RpcGatewayClazz.class);
        System.out.println("RpcGatewayClazz.clazzDesc：" + rpcGatewayClazz.clazzDesc());
        System.out.println("RpcGatewayClazz.alias：" + rpcGatewayClazz.alias());
        System.out.println("RpcGatewayClazz.timeOut：" + rpcGatewayClazz.timeOut());

        // 获取方法注解
        RpcGatewayMethod rpcGatewayMethod = null;
        try {
            rpcGatewayMethod = repositoryClass.getMethod("queryData", int.class).getAnnotation(RpcGatewayMethod.class);
            System.out.println("RpcGatewayMethod.methodName：" + rpcGatewayMethod.methodName());
            System.out.println("RpcGatewayMethod.methodDesc：" + rpcGatewayMethod.methodDesc());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
