package com.prayerlaputa.bytebuddyaop.agent.track;

import net.bytebuddy.asm.Advice;

import java.util.UUID;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class MyMonitorAdvice {


    /**
     *
     * 此处注解的含义参见：https://javadoc.io/doc/net.bytebuddy/byte-buddy/latest/net/bytebuddy/asm/Advice.Origin.html
     * Indicates that the annotated parameter should be mapped to a string representation of the instrumented method,
     * a constant representing the Class declaring the adviced method or a Method, Constructor or
     * java.lang.reflect.Executable representing this method.
     * Note: A constant representing a Method or Constructor is not cached but is recreated for every read.
     *
     * @Advice.Origin 取值如下：

    Returns the pattern the annotated parameter should be assigned. By default, the Annotation.toString() representation of the method is assigned. Alternatively, a pattern can be assigned where:
    #t inserts the method's declaring type.
    #m inserts the name of the method (<init> for constructors and <clinit> for static initializers).
    #d for the method's descriptor.
    #s for the method's signature.
    #r for the method's return type.
    #p for the property's name.
    Any other # character must be escaped by \ which can be escaped by itself. This property is ignored if the annotated parameter is of type Class.

    Returns:
    The pattern the annotated parameter should be assigned.

    Default: ""

    简单说方法进入时 ，将取出当前方法对应的某个值作为参数传进来。
    比如下面这个enter方法，@Advice.Origin("#t") 说的将当前方法的所在的类名映射到className，以便传入enter方法内处理；
     @Advice.Origin("#t") 是将该方法的方法名映射到methodName

     *
     * @param className
     * @param methodName
     */
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        String linkId = TrackManager.getCurrentSpan();
        if (null == linkId) {
            /*
            使用UUID作为spanId。进入方法时生成spanId
             */
            linkId = UUID.randomUUID().toString();
            TrackContext.setLinkId(linkId);
        }
        String entrySpan = TrackManager.createEntrySpan();
        System.out.println("链路追踪：" + entrySpan + " " + className + "." + methodName);

    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        TrackManager.getExitSpan();
    }
}
