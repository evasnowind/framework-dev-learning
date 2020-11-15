package com.prayerlaputa.assemblebean.autoinjection;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.StandardMethodMetadata;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
public class PetCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        //输出 org.springframework.core.type.StandardMethodMetadata
//        System.out.println(annotatedTypeMetadata.getClass().getName());

        //强转
        StandardMethodMetadata standardMethodMetadata = (StandardMethodMetadata)annotatedTypeMetadata;

        //AppConfig下的dog方法调用，输出dog
        System.out.println(standardMethodMetadata.getMethodName());

        //如果person对象已存在、但cat对象不存在，则返回true
        return conditionContext.getRegistry().containsBeanDefinition("person") && !conditionContext.getRegistry().containsBeanDefinition("cat");

        //conditionContext.getRegistry()获取注册的bean

        //conditionContext.getBeanFactory()获取提供bean definition的解析,注册功能,再对单例来个预加载(解决循环依赖问题).

        //conditionContext.getEnvironment()获取环境配置

        //conditionContext.getResourceLoader()ResourceLoader所加载的资源

        //conditionContext.getClassLoader()获取类加载器

//        annotatedTypeMetadata.
        //annotatedTypeMetadata 被加上注解的源数据信息。比如annotatedTypeMetadata.

    }
}
